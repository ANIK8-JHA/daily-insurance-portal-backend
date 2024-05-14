package com.dip.exceptions;

public class NotEnoughBalanceException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotEnoughBalanceException(String msg) {
		super(msg);
	}

}
