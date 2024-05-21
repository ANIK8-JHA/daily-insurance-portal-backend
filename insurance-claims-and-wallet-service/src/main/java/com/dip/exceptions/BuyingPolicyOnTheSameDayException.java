package com.dip.exceptions;

public class BuyingPolicyOnTheSameDayException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public BuyingPolicyOnTheSameDayException(String msg) {
		super(msg);
	}

}
