package com.self.elastic.exception;

/**
 * Custom exception for elastic search connection builder.
 * 
 * @author ranjithr
 *
 */
public class ConnectionBuilderException extends Exception {
	private static final long serialVersionUID = 8932622907876388984L;

	public ConnectionBuilderException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

}
