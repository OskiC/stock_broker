package com.stock.stock_broker.service;

import com.stock.stock_broker.dto.transaction.HistoryResponseDTO;
import com.stock.stock_broker.model.Stock;
import com.stock.stock_broker.model.User;
import com.stock.stock_broker.model.Transaction;
import com.stock.stock_broker.repository.StockRepository;
import com.stock.stock_broker.repository.TransactionRepository;
import com.stock.stock_broker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private StockService stockService;

    public Transaction openTransaction(Long userId, Long stockId, Double openPrice, Integer quantity){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Stock stock = stockRepository.findById(stockId).orElseThrow(() -> new IllegalArgumentException("Stock not found"));

        if(user.getBalance() < openPrice * quantity){
            throw new IllegalArgumentException("Not enough balance!");
        }

        Transaction transaction = new Transaction(user, stock, openPrice, quantity);
        return transactionRepository.save(transaction);
    }

    public void sellStock(Long userId, Long stockId, Integer quantityToSell){
        User user = userService.getUserById(userId);
        Stock stock = stockService.getStockById(stockId);
        Double currentPrice = stock.getPrice();

        List<Transaction> openTransactions = transactionRepository.findByUserAndStockAndClosePriceIsNull(user, stock);

        int remainingQuantity = quantityToSell;
        double totalProfitOrLoss = 0.0;

        for(Transaction t : openTransactions){
            int availableQty = t.getQuantity();

            if(remainingQuantity <= 0) break;

            if(availableQty <= remainingQuantity){
                t.setClosePrice(currentPrice);
                double pnl = (currentPrice - t.getOpenPrice()) * availableQty;
                t.setProfitAndLoss(pnl);
                totalProfitOrLoss += pnl;
                remainingQuantity -= availableQty;
            }
            else{
                Transaction partialSell = new Transaction();
                partialSell.setUser(user);
                partialSell.setStock(stock);
                partialSell.setOpenPrice(t.getOpenPrice());
                partialSell.setClosePrice(currentPrice);
                partialSell.setQuantity(remainingQuantity);
                double pnl = (currentPrice - t.getOpenPrice()) * remainingQuantity;
                partialSell.setProfitAndLoss(pnl);
                transactionRepository.save(partialSell);

                t.setQuantity(availableQty - remainingQuantity);
                totalProfitOrLoss += pnl;
                remainingQuantity = 0;
            }
        }

        if(remainingQuantity > 0){
            throw new IllegalArgumentException("Not enough stock to sell");
        }

        user.setBalance(user.getBalance() + (quantityToSell* currentPrice));
        userRepository.save(user);
    }

    public Transaction getTransactionById(Long id){
        return transactionRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Transaction with that ID not found!"));
    }

    public List<Transaction> getClosedTransactions(Long userId){
        return transactionRepository.findByStatus(Transaction.TransactionStatus.CLOSED);
    }

    public List<HistoryResponseDTO> getTransactionsByUser(Long userId){
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        List<HistoryResponseDTO> transactionsFormatted = new ArrayList<>();

        for(var t : transactions){
            HistoryResponseDTO temp = new HistoryResponseDTO();
            temp.setStatus(t.getStatus());
            temp.setStockName(t.getStock().getStockName());
            temp.setStockSymbol(t.getStock().getCodeName());
            if(temp.getStatus() == Transaction.TransactionStatus.CLOSED){
                temp.setPNL(t.getProfitAndLoss());
                temp.setTimestamp(t.getCloseDate());
            }
            else{
                temp.setPNL(t.getOpenPrice());
                temp.setTimestamp(t.getOpenDate());
            }
            transactionsFormatted.add(temp);
        }
        return transactionsFormatted;
    }

    public List<Transaction> getTransactionByUser(Long userId){
        return transactionRepository.findByUserId(userId);
    }
}
