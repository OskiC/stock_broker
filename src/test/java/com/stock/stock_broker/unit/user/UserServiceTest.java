package com.stock.stock_broker.unit.user;

import com.stock.stock_broker.model.User;
import com.stock.stock_broker.repository.UserRepository;
import com.stock.stock_broker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setBalance(1000.0);
    }

    @Test
    public void testDepositSuccess(){
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User result = userService.deposit(1L, 500.0);

        assertEquals(1500.0, result.getBalance());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    public void testWithdrawSuccess() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User result = userService.withdraw(1L, 300.0);

        assertEquals(700.0, result.getBalance());
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(testUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.withdraw(1L, 1500.0);
        });

        assertEquals("Insufficient balance for withdrawal", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testDepositNegativeAmount() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(testUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deposit(1L, -100.0);
        });

        assertEquals("Value must be greater than zero", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}
