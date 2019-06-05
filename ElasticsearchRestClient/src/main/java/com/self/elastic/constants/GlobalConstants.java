package com.self.elastic.constants;

/**
 * Global Constants
 * 
 * @author ranjithr
 *
 */
public class GlobalConstants {
	public static final String INDEX_NAME = "platform-test-automation";
	public static final String DOCUMENT_TYPE = "test-case-execution-result";
	public static final String HOST = "localhost";
	public static final int PORT = 9200;
	public static final String PROTOCOL = "http";

	private GlobalConstants() {
		throw new IllegalStateException("Illegal class initialization.");
	}
}
