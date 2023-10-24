package com.boot.hotel.exception;

public class UserNotFoundException extends RuntimeException {

	//extra properties that we want to manage
	public UserNotFoundException() {
		super("user not found on server...");
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
}
