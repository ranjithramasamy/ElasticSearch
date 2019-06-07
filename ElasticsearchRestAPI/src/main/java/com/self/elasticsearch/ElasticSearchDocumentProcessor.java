package com.self.elasticsearch;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.self.elasticsearch.builder.RestClientBuilder;
import com.self.elasticsearch.constants.GlobalConstants;
import com.self.elasticsearch.exception.ElasticSearchDocumentProcessorException;
import com.self.elasticsearch.stream.IndexMappingReader;
import com.self.elasticsearch.stream.LogFileReader;
import com.self.generator.logs.LogsforKibana;

import io.restassured.response.Response;

/**
 * Process automation logs to elastic search for Kibana dashboard visualization
 * 
 * @author ranjithr
 *
 */
public class ElasticSearchDocumentProcessor {
  public void load(String filePath) {
    try {
      if (checkIndexExists() && updateIdxMappingToElasticSearch()) {
        processDocToElasticSearch(filePath);
      } else if (createIdxMappingToElasticSearch()) {
        processDocToElasticSearch(filePath);
      } else {
        LogsforKibana.messages(GlobalConstants.ERROR_MSG_NO_ACTIONS_PERFORMED);
      }
    } catch (ElasticSearchDocumentProcessorException ex) {
      System.out.println(ex.getMessage());
      LogsforKibana.messages(ex.getMessage());
    }
  }

  private boolean createIdxMappingToElasticSearch() throws ElasticSearchDocumentProcessorException {
    Map<String, String> headers = new HashMap<>();
    String loadDocUrl = GlobalConstants.BASE_URI + GlobalConstants.CREATE_NEW_INDEX_URI;
    String reqBody = IndexMappingReader.getAll();

    headers.put(GlobalConstants.KEY_CONTENT_TYPE, GlobalConstants.VAL_CONTENT_TYPE_JSON);
    Response response = RestClientBuilder.put(loadDocUrl, reqBody, headers);

    if (response.statusCode() == GlobalConstants.SUCCESS) {
      LogsforKibana.messages(GlobalConstants.MSG_INDEX_MAPPINGS_CREATED_SUCCESSFULLY_TO_ES);
    } else {
      throw new ElasticSearchDocumentProcessorException(
        String
          .format(
            GlobalConstants.ERROR_MSG_FAILED_TO_CREATE_NEW_INDEX_TO_ES,
            GlobalConstants.INDEX_NAME,
            response.print()));
    }

    return true;
  }

  private boolean updateIdxMappingToElasticSearch() throws ElasticSearchDocumentProcessorException {
    Map<String, String> headers = new HashMap<>();
    String loadDocUrl = GlobalConstants.BASE_URI + GlobalConstants.UPDATE_INDEX_MAPPING_URI;
    String reqBody = IndexMappingReader.getMappingsOnly();

    headers.put(GlobalConstants.KEY_CONTENT_TYPE, GlobalConstants.VAL_CONTENT_TYPE_JSON);
    Response response = RestClientBuilder.put(loadDocUrl, reqBody, headers);

    if (response.statusCode() == GlobalConstants.SUCCESS) {
      LogsforKibana.messages(GlobalConstants.MSG_INDEX_MAPPINGS_UPDATED_SUCCESSFULLY_TO_ES);
    } else {
      throw new ElasticSearchDocumentProcessorException(
        String
          .format(
            GlobalConstants.ERROR_MSG_FAILED_TO_UPDATE_AN_INDEX_TO_ES,
            GlobalConstants.INDEX_NAME,
            response.print()));
    }

    return true;
  }

  private void processDocToElasticSearch(String filePath)
    throws ElasticSearchDocumentProcessorException {
    String loadDocUrl = GlobalConstants.BASE_URI + GlobalConstants.LOAD_BULK_DOC_URI;
    String documents = LogFileReader.get(filePath);
    Map<String, String> headers = new HashMap<>();

    headers.put(GlobalConstants.KEY_CONTENT_TYPE, GlobalConstants.VAL_CONTENT_TYPE_XNDJSON);
    Response response = RestClientBuilder.post(loadDocUrl, documents, headers);

    if (response.statusCode() == GlobalConstants.SUCCESS) {
      Gson gson = new Gson();
      JsonObject jsonObject = gson.fromJson(response.getBody().asString(), JsonObject.class);
      boolean hasErrors = jsonObject.get("errors").getAsBoolean();

      LogsforKibana.messages("Has errors while indexing the documents: " + hasErrors);
      System.out.println("Has errors while indexing the documents: " + hasErrors);

      if (hasErrors) {
        LogsforKibana
          .messages(String.format(GlobalConstants.ERROR_MSG_SOME_DOCS_FAILED_TO_INDEX, filePath));
        System.out.println(response.getBody().asString());
      } else {
        LogsforKibana
          .messages(
            String.format(GlobalConstants.MSG_DOCUMENTS_INDEXED_SUCCESSFULLY_TO_ES, filePath));
      }
    } else {
      throw new ElasticSearchDocumentProcessorException(
        String.format(GlobalConstants.ERROR_MSG_FAILED_TO_INDEX_DOCUMENTS_TO_ES, response.print()));
    }
  }

  private boolean checkIndexExists() throws ElasticSearchDocumentProcessorException {
    String url = GlobalConstants.BASE_URI + GlobalConstants.CHECK_INDEX_EXISTS_URI;

    Response response = RestClientBuilder.head(url);

    return response.statusCode() == GlobalConstants.SUCCESS;
  }
}
