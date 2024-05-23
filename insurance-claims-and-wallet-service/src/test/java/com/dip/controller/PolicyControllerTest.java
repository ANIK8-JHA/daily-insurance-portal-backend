package com.dip.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.dip.model.Policy;
import com.dip.service.PolicyService;
 
@WebMvcTest(PolicyController.class)
@ExtendWith(MockitoExtension.class)
public class PolicyControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @MockBean
    private PolicyService policyService;
 
    @Test
    public void testGetAllPolicies() throws Exception {
        Policy policy1 = new Policy();
        policy1.setPolicyId(1L);
        policy1.setPolicyName("Policy 1");
 
        Policy policy2 = new Policy();
        policy2.setPolicyId(2L);
        policy2.setPolicyName("Policy 2");
 
        List<Policy> policies = Arrays.asList(policy1, policy2);
 
        when(policyService.getAllPolicies()).thenReturn(policies);
 
        mockMvc.perform(get("/dip/policy/get-all-policies")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].policyId").value(1L))
                .andExpect(jsonPath("$[0].policyName").value("Policy 1"))
                .andExpect(jsonPath("$[1].policyId").value(2L))
                .andExpect(jsonPath("$[1].policyName").value("Policy 2"));
    }
 
    @Test
    public void testGetPolicyNameById() throws Exception {
        when(policyService.getPolicyNameById(anyLong())).thenReturn("Policy 1");
 
        mockMvc.perform(get("/dip/policy/get-policy-name/1")
                .header("Authorization", "Bearer token")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Policy 1"));
    }
}