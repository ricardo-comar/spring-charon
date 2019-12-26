package com.github.ricardocomar.springcharon.appowner.exception;

public class UnavailableResponseException extends Exception {

	private static final long serialVersionUID = -2167596404229805642L;

	public UnavailableResponseException() {
		super();
	}
	
	public UnavailableResponseException(final String msg) {
		super(msg);
	}

	public UnavailableResponseException(final Throwable cause) {
		super(cause);
	}
	
	public UnavailableResponseException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	
	
	
	
}
