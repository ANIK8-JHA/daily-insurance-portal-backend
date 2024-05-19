package com.dip.suite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.dip.controller.ClaimsControllerTest;
import com.dip.controller.InsuranceControllerTest;
import com.dip.controller.PolicyControllerTest;
import com.dip.controller.WalletControllerTest;
import com.dip.model.UserPolicyClaim;
import com.dip.service.PolicyServiceTest;
import com.dip.service.UserPolicyClaimTest;
import com.dip.service.WalletServiceTest;

@Suite
@SelectClasses({
	PolicyServiceTest.class, 
	WalletServiceTest.class, 
	UserPolicyClaimTest.class, 
	ClaimsControllerTest.class,
	InsuranceControllerTest.class,
	PolicyControllerTest.class,
	WalletControllerTest.class
	})
public class TestSuite {

}
