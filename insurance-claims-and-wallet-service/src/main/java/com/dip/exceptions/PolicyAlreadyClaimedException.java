package com.dip.exceptions;

public class PolicyAlreadyClaimedException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PolicyAlreadyClaimedException(String msg) {
		super(msg);
	}
}
