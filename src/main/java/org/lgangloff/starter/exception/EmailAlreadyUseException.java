package org.lgangloff.starter.exception;

public class EmailAlreadyUseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailAlreadyUseException(String email) {
		super(email + " is already use.");
	}

}
