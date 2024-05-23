package com.dip.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dip.classes.Insurance;
import com.dip.model.UserPolicyClaim;
import com.dip.service.UserPolicyClaimService;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@WebMvcTest(InsuranceController.class)
public class InsuranceControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private UserPolicyClaimService insuranceService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    @Test
    public void testBuyInsuranceSuccess() throws Exception {
        UserPolicyClaim insurance = new UserPolicyClaim();
        insurance.setUserId(1L);
        insurance.setPolicyId(101L);
 
        when(insuranceService.purchasePolicy(any(Insurance.class), anyLong(), anyString())).thenReturn(insurance);
 
        Insurance request = new Insurance();
        request.setPolicyName("Test Policy");
        request.setPremium(500);
 
        mockMvc.perform(post("/dip/insurance/buy-insurance/testUser/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.policyId").value(101L));
    }
 
    @Test
    public void testGetUserPolicyClaimHistorySuccess() throws Exception {
        UserPolicyClaim claim1 = new UserPolicyClaim();
        claim1.setUserId(1L);
        claim1.setPolicyId(101L);
 
        UserPolicyClaim claim2 = new UserPolicyClaim();
        claim2.setUserId(1L);
        claim2.setPolicyId(102L);
 
        List<UserPolicyClaim> claimsList = Arrays.asList(claim1, claim2);
 
        when(insuranceService.getAllUserPolicyClaimsByUserId(anyLong())).thenReturn(claimsList);
 
        mockMvc.perform(get("/dip/insurance/get-insurances/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].policyId").value(101L))
                .andExpect(jsonPath("$[1].policyId").value(102L));
    }
 
    @Test
    public void testGetUserPolicyClaimHistoryEmpty() throws Exception {
        when(insuranceService.getAllUserPolicyClaimsByUserId(anyLong())).thenReturn(Arrays.asList());
 
        mockMvc.perform(get("/dip/insurance/get-insurances/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}