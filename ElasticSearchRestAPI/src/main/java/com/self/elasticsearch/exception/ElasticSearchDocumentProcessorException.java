package com.self.elasticsearch.exception;

/**
 * Custom exception handler for elastic search document processor.
 * 
 * @author ranjithr
 *
 */
public class ElasticSearchDocumentProcessorException extends Exception {
	private static final long serialVersionUID = 6363360588039245579L;

	public ElasticSearchDocumentProcessorException(String errorMessage) {
	    super(errorMessage);
	}
	
	public ElasticSearchDocumentProcessorException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
}
