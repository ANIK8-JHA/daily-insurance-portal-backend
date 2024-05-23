package com.dip.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.dip.model.Wallets;
import com.dip.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private WalletService walletService;
 
    @Test
    public void testGetCurrentWalletBalance() throws Exception {
        when(walletService.getCurrentWalletBalanceByUsername(anyString())).thenReturn(100);
 
        mockMvc.perform(get("/dip/wallet/get-balance/testUser")
                .header("Authorization", "Bearer testToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(100)));
    }
 
    @Test
    public void testAddWalletBalanceSuccess() throws Exception {
        Wallets wallet = new Wallets();
        wallet.setAddedBalance(100);
 
        Wallets newWallet = new Wallets();
        newWallet.setWalletBalance(200);
 
        when(walletService.addWalletBalance(any(Wallets.class), anyString())).thenReturn(newWallet);
 
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(wallet);
 
        mockMvc.perform(post("/dip/wallet/add-balance/testUser")
                .header("Authorization", "Bearer testToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.walletBalance", is(200)));
    }
}