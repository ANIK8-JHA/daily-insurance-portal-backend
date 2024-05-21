package com.dip.exceptions;

public class ClaimAmountGreaterThanCoverageAmountException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ClaimAmountGreaterThanCoverageAmountException(String msg) {
		super(msg);
	}
}
