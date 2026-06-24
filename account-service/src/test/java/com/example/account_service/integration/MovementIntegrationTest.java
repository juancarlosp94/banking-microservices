package com.example.account_service.integration;

import com.example.account_service.entity.Account;
import com.example.account_service.repository.AccountRepository;
import com.example.account_service.repository.MovementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MovementIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementRepository movementRepository;

    @BeforeEach
    void setup() {
        movementRepository.deleteAll();
        accountRepository.deleteAll();

        Account account = new Account();
        account.setAccountNumber("478758");
        account.setAccountType("Ahorros");
        account.setInitialBalance(new BigDecimal("2000"));
        account.setAvailableBalance(new BigDecimal("2000"));
        account.setActive(true);
        account.setCustomerId(1L);

        accountRepository.save(account);
    }

    @Test
    void shouldCreateMovementAndUpdateAccountBalance() throws Exception {
        mockMvc.perform(post("/api/movements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "accountNumber": "478758",
                                  "amount": -575
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movementType", is("RETIRO")))
                .andExpect(jsonPath("$.amount", is(-575)))
                .andExpect(jsonPath("$.balanceAfterMovement", is(1425.0)));

        Account updatedAccount = accountRepository.findByAccountNumber("478758")
                .orElseThrow();

        assertEquals(new BigDecimal("1425.00"), updatedAccount.getAvailableBalance());
        assertEquals(1, movementRepository.findAll().size());
    }
}