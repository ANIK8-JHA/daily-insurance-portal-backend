package com.dip.exceptions;

public class AmountExceededLimitException extends Exception {
	private static final long serialVersionUID = 1L;

	public AmountExceededLimitException(String msg) {
		super(msg);
	}

}
