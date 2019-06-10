package com.self.elasticsearch;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.self.elasticsearch.builder.RestClientBuilder;
import com.self.elasticsearch.constants.GlobalConstants;
import com.self.elasticsearch.exception.ElasticSearchDocumentProcessorException;
import com.self.elasticsearch.stream.IndexMappingReader;
import com.self.elasticsearch.stream.LogFileReader;

/**
 * Process automation logs to elastic search for Kibana dashboard visualization
 * 
 * @author ranjithr
 *
 */
public class ElasticSearchDocumentProcessor {
	private static final Logger logger = LoggerFactory.getLogger(ElasticSearchDocumentProcessor.class);

	public void load(String filePath) {
		try {
			if (checkIndexExists() && updateIdxMappingToElasticSearch()) {
				processDocToElasticSearch(filePath);
			} else if (createIdxMappingToElasticSearch()) {
				processDocToElasticSearch(filePath);
			} else {
				logger.error(GlobalConstants.ERROR_MSG_NO_ACTIONS_PERFORMED);
			}
		} catch (ElasticSearchDocumentProcessorException ex) {
			logger.error(ex.getMessage());
		}
	}

	private boolean createIdxMappingToElasticSearch() throws ElasticSearchDocumentProcessorException {
		Map<String, String> headers = new HashMap<>();
		String loadDocUrl = GlobalConstants.ES_BASE_URL + GlobalConstants.CREATE_NEW_INDEX_URI;
		String reqBody = IndexMappingReader.getAll();

		headers.put(GlobalConstants.KEY_CONTENT_TYPE, GlobalConstants.VAL_CONTENT_TYPE_JSON);
		Response response = RestClientBuilder.put(loadDocUrl, reqBody, headers);

		if (response.getStatus() == GlobalConstants.SUCCESS) {
			logger.info(GlobalConstants.MSG_INDEX_MAPPINGS_CREATED_SUCCESSFULLY_TO_ES);
		} else {
			throw new ElasticSearchDocumentProcessorException(
					String.format(GlobalConstants.ERROR_MSG_FAILED_TO_CREATE_NEW_INDEX_TO_ES,
							GlobalConstants.INDEX_NAME, response.readEntity(String.class)));
		}

		return true;
	}

	private boolean updateIdxMappingToElasticSearch() throws ElasticSearchDocumentProcessorException {
		Map<String, String> headers = new HashMap<>();
		String loadDocUrl = GlobalConstants.ES_BASE_URL + GlobalConstants.UPDATE_INDEX_MAPPING_URI;
		String reqBody = IndexMappingReader.getMappingsOnly();

		headers.put(GlobalConstants.KEY_CONTENT_TYPE, GlobalConstants.VAL_CONTENT_TYPE_JSON);
		Response response = RestClientBuilder.put(loadDocUrl, reqBody, headers);

		if (response.getStatus() == GlobalConstants.SUCCESS) {
			logger.info(GlobalConstants.MSG_INDEX_MAPPINGS_UPDATED_SUCCESSFULLY_TO_ES);
		} else {
			throw new ElasticSearchDocumentProcessorException(
					String.format(GlobalConstants.ERROR_MSG_FAILED_TO_UPDATE_AN_INDEX_TO_ES, GlobalConstants.INDEX_NAME,
							response.readEntity(String.class)));
		}

		return true;
	}

	private void processDocToElasticSearch(String filePath) throws ElasticSearchDocumentProcessorException {
		String loadDocUrl = GlobalConstants.ES_BASE_URL + GlobalConstants.LOAD_BULK_DOC_URI;
		String documents = LogFileReader.get(filePath);
		Map<String, String> headers = new HashMap<>();

		headers.put(GlobalConstants.KEY_CONTENT_TYPE, GlobalConstants.VAL_CONTENT_TYPE_XNDJSON);
		Response response = RestClientBuilder.post(loadDocUrl, documents, headers);

		if (response.getStatus() == GlobalConstants.SUCCESS) {
			Gson gson = new Gson();
			JsonObject jsonObject = gson.fromJson(response.readEntity(String.class), JsonObject.class);
			boolean hasErrors = jsonObject.get("errors").getAsBoolean();

			logger.info(GlobalConstants.ERROR_MSG_HAS_ERRORS_WHILE_INDEXING_DOCUMENTS, hasErrors);

			if (hasErrors) {
				logger.error(GlobalConstants.ERROR_MSG_SOME_DOCS_FAILED_TO_INDEX, jsonObject.getAsString());
			} else {
				logger.info(GlobalConstants.MSG_DOCUMENTS_INDEXED_SUCCESSFULLY_TO_ES, filePath);
			}
		} else {
			throw new ElasticSearchDocumentProcessorException(String.format(
					GlobalConstants.ERROR_MSG_FAILED_TO_INDEX_DOCUMENTS_TO_ES, response.readEntity(String.class)));
		}
	}

	private boolean checkIndexExists() throws ElasticSearchDocumentProcessorException {
		String url = GlobalConstants.ES_BASE_URL + GlobalConstants.CHECK_INDEX_EXISTS_URI;

		Response response = RestClientBuilder.head(url);

		return response.getStatus() == GlobalConstants.SUCCESS;
	}
}
