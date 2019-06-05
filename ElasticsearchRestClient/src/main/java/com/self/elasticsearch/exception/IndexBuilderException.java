package com.self.elasticsearch.exception;

/**
 * Custom exception for Index builder.
 * 
 * @author ranjithr
 *
 */
public class IndexBuilderException extends Exception {
	private static final long serialVersionUID = 6255296415316961466L;

	public IndexBuilderException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
