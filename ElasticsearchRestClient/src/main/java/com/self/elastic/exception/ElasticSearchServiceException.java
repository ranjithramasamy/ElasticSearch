package com.self.elastic.exception;

/**
 * Custom exception handler for service layer.
 * 
 * @author ranjithr
 *
 */
public class ElasticSearchServiceException extends Exception {
	private static final long serialVersionUID = 6363360588039245579L;

	public ElasticSearchServiceException(String errorMessage) {
	    super(errorMessage);
	}
	
	public ElasticSearchServiceException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
}
