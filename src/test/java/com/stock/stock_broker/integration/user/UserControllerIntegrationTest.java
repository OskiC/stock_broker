package com.stock.stock_broker.integration.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.stock_broker.dto.user.BalanceDTO;
import com.stock.stock_broker.dto.user.UserRegistrationDTO;
import com.stock.stock_broker.model.User;
import com.stock.stock_broker.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    // Test data
    private final UserRegistrationDTO testRegistration =
            new UserRegistrationDTO("testuser", "password123");
    private final BalanceDTO testDeposit = new BalanceDTO(null, 500.0);
    private final BalanceDTO testWithdrawal = new BalanceDTO(null, 200.0);

    @Test
    public void registerUser_shouldCreateNewUser() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRegistration)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.balance").value(0.0));
    }

    @Test
    public void registerUser_withDuplicateUsername_shouldFail() throws Exception {
        // First registration
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRegistration)));

        // Second attempt
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRegistration)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deposit_shouldIncreaseBalance() throws Exception {
        User user = registerTestUser();
        testDeposit.setUserId(user.getId());

        mockMvc.perform(post("/api/users/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testDeposit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(testDeposit.getAmount()));
    }

    @Test
    public void withdraw_shouldDecreaseBalance() throws Exception {
        User user = registerTestUser();
        user.setBalance(500.0);
        userRepository.save(user);

        testWithdrawal.setUserId(user.getId());

        mockMvc.perform(post("/api/users/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testWithdrawal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(500.0 - testWithdrawal.getAmount()));
    }

    @Test
    public void getUserById_shouldReturnUser() throws Exception {
        User user = registerTestUser();

        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void getUserById_withInvalidId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/users/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("User not found")));
    }

    private User registerTestUser() throws Exception {
        String response = mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRegistration)))
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(response, User.class);
    }
}