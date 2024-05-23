package com.dip.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dip.classes.Claim;
import com.dip.classes.Insurance;
import com.dip.exceptions.SubmittingClaimOnTheSameDayException;
import com.dip.model.Policy;
import com.dip.model.UserPolicyClaim;
import com.dip.repository.UserPolicyClaimRepository;
 
@ExtendWith(MockitoExtension.class)
public class UserPolicyClaimTest {
 
    @Mock
    private UserPolicyClaimRepository userPolicyClaimRepo;
 
    @Mock
    private WalletService walletService;
 
    @Mock
    private PolicyService policyService;
 
    @InjectMocks
    private UserPolicyClaimService userPolicyClaimService;
 
    @Test
    public void testPurchasePolicySuccess() throws Exception {
        Insurance insurance = new Insurance();
        insurance.setPolicyName("TestPolicy");
        insurance.setPremium(100);
 
        UserPolicyClaim userPolicyClaim = new UserPolicyClaim();
        userPolicyClaim.setUserId(1L);
        userPolicyClaim.setPolicyId(1L);
        userPolicyClaim.setClaimStatus("Not Claimed");
        UserPolicyClaim userPolicyClaim1 = new UserPolicyClaim();
        userPolicyClaim1.setUserId(1L);
        userPolicyClaim1.setPolicyId(1L);
        userPolicyClaim1.setPurchaseDttm("2023-05-01 10:00");
        userPolicyClaim1.setClaimStatus("Not Claimed");
 
        when(userPolicyClaimRepo.save(any(UserPolicyClaim.class))).thenReturn(userPolicyClaim);
        when(walletService.getCurrentWalletBalanceByUsername(anyString())).thenReturn(2000);
        when(policyService.getPolicyIdByName(anyString())).thenReturn(1L);
        when(userPolicyClaimRepo.findLatestByUserId(anyLong())).thenReturn(userPolicyClaim1);
        UserPolicyClaim result = userPolicyClaimService.purchasePolicy(insurance, 1L, "testUser");
 
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals(1L, result.getPolicyId());
        assertEquals("Not Claimed", result.getClaimStatus());
 
        verify(walletService, times(1)).updateBalance(anyString(), anyInt());
    }

    @Test
    public void testNewClaimSuccess() throws Exception {
        Claim claim = new Claim();
        claim.setPolicyName("TestPolicy");
        claim.setClaimAmount(100);
        claim.setPurchaseDttm(LocalDateTime.now().minusDays(1));
 
        Policy policy = new Policy();
        policy.setPolicyName("TestPolicy");
        policy.setPolicyCoverage(200);
        policy.setPolicyPremium(50);
 
        UserPolicyClaim userPolicyClaim = new UserPolicyClaim();
        userPolicyClaim.setUserId(1L);
        userPolicyClaim.setPolicyId(1L);
        userPolicyClaim.setPurchaseDttm("2023-05-01 10:00");
        userPolicyClaim.setClaimStatus("Not Claimed");
 
        when(policyService.getPolicyIdByName(anyString())).thenReturn(1L);
        when(policyService.findByPolicyName(anyString())).thenReturn(policy);
        when(userPolicyClaimRepo.findByPolicyIdAndDop(anyLong(), anyString())).thenReturn(userPolicyClaim);
        when(policyService.isUnderMaxCoverage(anyString(), anyInt())).thenReturn(true);
        when(userPolicyClaimRepo.save(any(UserPolicyClaim.class))).thenReturn(userPolicyClaim);
 
        UserPolicyClaim result = userPolicyClaimService.newClaim(claim, "testUser");
 
        assertNotNull(result);
        assertEquals(1L, result.getUserId());
        assertEquals("Claimed", result.getClaimStatus());
 
        verify(walletService, times(1)).updateClaimBalanceInWallet(anyString(), anyInt());
    }
 
    @Test
    public void testNewClaimThrowsSubmittingClaimOnTheSameDayException() {
        Claim claim = new Claim();
        claim.setPolicyName("TestPolicy");
        claim.setClaimAmount(100);
        claim.setPurchaseDttm(LocalDateTime.now());
 
        UserPolicyClaim userPolicyClaim = new UserPolicyClaim();
        userPolicyClaim.setUserId(1L);
        userPolicyClaim.setPolicyId(1L);
        userPolicyClaim.setPurchaseDttm(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        userPolicyClaim.setClaimStatus("Not Claimed");
 
        when(policyService.getPolicyIdByName(anyString())).thenReturn(1L);
        when(userPolicyClaimRepo.findByPolicyIdAndDop(anyLong(), anyString())).thenReturn(userPolicyClaim);
 
        assertThrows(SubmittingClaimOnTheSameDayException.class, () -> {
            userPolicyClaimService.newClaim(claim, "testUser");
        });
    }
 
    
    @Test
    public void testGetAllUserPolicyClaimsByUserId() {
        UserPolicyClaim userPolicyClaim = new UserPolicyClaim();
        userPolicyClaim.setUserId(1L);
        userPolicyClaim.setPolicyId(1L);
        userPolicyClaim.setPurchaseDttm("2023-05-01 10:00");
        userPolicyClaim.setClaimStatus("Not Claimed");
        UserPolicyClaim userPolicyClaim1 = new UserPolicyClaim();
        userPolicyClaim.setUserId(1L);
        userPolicyClaim.setPolicyId(2L);
        userPolicyClaim.setPurchaseDttm("2023-05-02 10:00");
        userPolicyClaim.setClaimStatus("Not Claimed");
        List<UserPolicyClaim> list = new ArrayList<>();
        list.add(userPolicyClaim1);
        list.add(userPolicyClaim);
 
        when(userPolicyClaimRepo.findByUserId(anyLong())).thenReturn(list);
 
        List<UserPolicyClaim> result = userPolicyClaimService.getAllUserPolicyClaimsByUserId(1L);
 
        assertNotNull(result);
        assertEquals(2, result.size());
    }
 
    @Test
    public void testIsClaimed() {
        UserPolicyClaim userPolicyClaim = new UserPolicyClaim();
        userPolicyClaim.setClaimStatus("Claimed");
 
        Boolean result = userPolicyClaimService.isClaimed(userPolicyClaim);
 
        assertTrue(result);
    }
 
    @Test
    public void testIsBuyingOnSameDay() {
        UserPolicyClaim userPolicyClaim = new UserPolicyClaim();
        userPolicyClaim.setPurchaseDttm(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
 
        when(userPolicyClaimRepo.findLatestByUserId(anyLong())).thenReturn(userPolicyClaim);
 
        Boolean result = userPolicyClaimService.isBuyingOnSameDay(1L);
 
        assertTrue(result);
    }
 
    
    @Test
    public void testIsClaimingOnSameDay() {
        UserPolicyClaim userPolicyClaim = new UserPolicyClaim();
        userPolicyClaim.setPurchaseDttm(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
 
        Boolean result = userPolicyClaimService.isClaimingOnSameDay(userPolicyClaim);
 
        assertTrue(result);
    }
}