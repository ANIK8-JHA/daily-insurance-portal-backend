package com.dip.exceptions;

public class SubmittingClaimOnTheSameDayException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public SubmittingClaimOnTheSameDayException(String msg) {
		super(msg);
	}

}
