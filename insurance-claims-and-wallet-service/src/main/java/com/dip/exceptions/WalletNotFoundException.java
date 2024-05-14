package com.dip.exceptions;

public class WalletNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public WalletNotFoundException(String msg) {
		super(msg);
	}
}
