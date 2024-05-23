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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dip.classes.Claim;
import com.dip.model.UserPolicyClaim;
import com.dip.service.UserPolicyClaimService;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@WebMvcTest(ClaimsController.class)
public class ClaimsControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private UserPolicyClaimService claimsService;
 
    @Autowired
    private ObjectMapper objectMapper;
 
    @Test
    public void testSubmitClaimSuccess() throws Exception {
        UserPolicyClaim claim = new UserPolicyClaim();
        claim.setUserId(1L);
        claim.setClaimAmount(1000);
 
        when(claimsService.newClaim(any(Claim.class), anyString())).thenReturn(claim);
 
        Claim request = new Claim();
        request.setClaimAmount(1000);
        request.setPolicyName("Test Policy");
 
        mockMvc.perform(post("/dip/claims/submit-claim/testUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.claimAmount").value(1000));
    }
 
    @Test
    public void testGetAllUserPolicyClaimsByUserIdSuccess() throws Exception {
        UserPolicyClaim claim1 = new UserPolicyClaim();
        claim1.setUserId(1L);
        claim1.setClaimAmount(1000);
 
        UserPolicyClaim claim2 = new UserPolicyClaim();
        claim2.setUserId(1L);
        claim2.setClaimAmount(2000);
 
        when(claimsService.getAllUserPolicyClaimsByUserId(anyLong())).thenReturn(Arrays.asList(claim1, claim2));
 
        mockMvc.perform(get("/dip/claims/get-all-by/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].claimAmount").value(1000))
                .andExpect(jsonPath("$[1].claimAmount").value(2000));
    }
 
    @Test
    public void testGetAllUserPolicyClaimsByUserIdEmpty() throws Exception {
        when(claimsService.getAllUserPolicyClaimsByUserId(anyLong())).thenReturn(Arrays.asList());
 
        mockMvc.perform(get("/dip/claims/get-all-by/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}