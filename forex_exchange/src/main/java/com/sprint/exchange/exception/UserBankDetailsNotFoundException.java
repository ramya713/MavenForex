package com.sprint.exchange.exception;

public class UserBankDetailsNotFoundException extends RuntimeException {
	
	public UserBankDetailsNotFoundException (String msg)
	{
		super(msg);
	}
}
