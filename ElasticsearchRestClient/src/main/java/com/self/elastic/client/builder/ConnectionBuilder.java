package com.self.elastic.client.builder;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import com.self.elastic.constants.GlobalConstants;
import com.self.elastic.exception.ConnectionBuilderException;

/**
 * Elastic search client connection
 * 
 * @author ranjithr
 *
 */
public class ConnectionBuilder {
	private static RestHighLevelClient client = null;

	private ConnectionBuilder() {
		throw new IllegalStateException("Illegal state of class initialization.");
	}

	public static RestHighLevelClient getConnection() {
		if (null == client) {
			client = new RestHighLevelClient(RestClient
					.builder(new HttpHost(GlobalConstants.HOST, GlobalConstants.PORT, GlobalConstants.PROTOCOL)));
		}

		return client;
	}

	public static void close() throws ConnectionBuilderException {
		if (null == client) {
			return;
		}

		try {
			client.close();
		} catch (IOException ex) {
			throw new ConnectionBuilderException(
					"Exception occurred while establishing a connection to elastic search.", ex);
		} finally {
			client = null;
		}
	}
}
