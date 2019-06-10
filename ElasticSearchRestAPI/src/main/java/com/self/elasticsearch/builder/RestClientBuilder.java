package com.self.elasticsearch.builder;

import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.self.elasticsearch.constants.GlobalConstants;
import com.self.elasticsearch.exception.ElasticSearchDocumentProcessorException;

/**
 * Rest-Assure client wrapper
 * 
 * @author ranjithr
 *
 */
public class RestClientBuilder {
	private RestClientBuilder() {
		throw new IllegalStateException(GlobalConstants.ERROR_MSG_ILLEGAL_STATE_INITIALIZATION);
	}

	public static Response post(String url, String reqParams, Map<String, String> headers)
			throws ElasticSearchDocumentProcessorException {
		Response response = null;

		try {
			Client client = ClientBuilder.newClient();
			Builder builder = client.target(url).request();

			for (Map.Entry<String, String> header : headers.entrySet()) {
				builder.header(header.getKey(), header.getValue());
			}

			response = builder.post(Entity.entity(reqParams, GlobalConstants.VAL_CONTENT_TYPE_XNDJSON));
		} catch (Exception ex) {
			throw new ElasticSearchDocumentProcessorException(
					String.format(GlobalConstants.ERROR_MSG_EXCEPTION_OCCURED, ex.getMessage()));
		}

		return response;
	}

	public static Response put(String url, String reqParams, Map<String, String> headers)
			throws ElasticSearchDocumentProcessorException {
		Response response = null;

		try {
			Client client = ClientBuilder.newClient();
			Builder builder = client.target(url).request();

			for (Map.Entry<String, String> header : headers.entrySet()) {
				builder.header(header.getKey(), header.getValue());
			}

			response = builder.put(Entity.entity(reqParams, MediaType.APPLICATION_JSON));
		} catch (Exception ex) {
			throw new ElasticSearchDocumentProcessorException(
					String.format(GlobalConstants.ERROR_MSG_EXCEPTION_OCCURED, ex.getMessage()));
		}

		return response;
	}

	public static Response head(String url) throws ElasticSearchDocumentProcessorException {
		Response response = null;

		try {
			Client client = ClientBuilder.newClient();
			Builder builder = client.target(url).request();

			response = builder.head();
		} catch (Exception ex) {
			throw new ElasticSearchDocumentProcessorException(
					String.format(GlobalConstants.ERROR_MSG_EXCEPTION_OCCURED, ex.getMessage()));
		}

		return response;
	}
}
