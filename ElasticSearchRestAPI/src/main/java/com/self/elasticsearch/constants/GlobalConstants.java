package com.self.elasticsearch.constants;

import com.self.elasticsearch.loader.PropertyLoader;

/**
 * Global constants
 * 
 * @author ranjithr
 *
 */
public class GlobalConstants {
  // Used by "elasticsearch" package.
  public static final String INDEX_NAME = "platform-test-automation";
  public static final String DOCUMENT_TYPE = "test-case-execution-result";
  
  public static final String ES_BASE_URL = PropertyLoader.getValue("elasticsearch.baseurl");
  public static final String KIBANA_URL = PropertyLoader.getValue("kibana.url");
  public static final String CREATE_NEW_INDEX_URI = "/" + INDEX_NAME;
  public static final String LOAD_BULK_DOC_URI = "/" + INDEX_NAME + "/_bulk";
  public static final String UPDATE_INDEX_MAPPING_URI = "/" + INDEX_NAME + "/_mapping/" + DOCUMENT_TYPE;
  public static final String CHECK_INDEX_EXISTS_URI = "/" + INDEX_NAME;
  public static final String INDEX_MAPPING_FILE_NAME = "elasticsearch-mapping.json";
  public static final String CONFIG_PROPERTIES_FILE_NAME = "elasticsearch-config.properties";
  
  // Successful messages
  public static final String MSG_DOCUMENTS_INDEXED_SUCCESSFULLY_TO_ES = "Documents are indexed successfully to elastic search. Processed file name: {}";
  public static final String MSG_INDEX_MAPPINGS_CREATED_SUCCESSFULLY_TO_ES = "Index mappings are created successfully to elastic search.";
  public static final String MSG_INDEX_MAPPINGS_UPDATED_SUCCESSFULLY_TO_ES = "Index mappings are updated successfully to elastic search.";
  // Error messages
  public static final String ERROR_MSG_ILLEGAL_STATE_INITIALIZATION = "Illegal state of class initialization.";
  public static final String ERROR_MSG_INVALID_URL = "Invalid url {} passed.";
  public static final String ERROR_MSG_INVALID_IDX_MAPPING_JSON = "Unable to parse the json file. Reason: {}";
  public static final String ERROR_MSG_MAPPING_JSON_FILE_NOT_FOUND = "Index mapping json file not found. Reason: {}";
  public static final String ERROR_MSG_MAPPING_LOG_FILE_NOT_FOUND = "Log file {} not found. Reason: {}";
  public static final String ERROR_MSG_MAPPING_LOG_FILE_NOT_ABLE_TO_READ = "Unable to read log file {}. Reason: {}";
  public static final String ERROR_MSG_FAILED_TO_CREATE_NEW_INDEX_TO_ES = "Failed to create a new index [%s] to elastic search. Reason: [%s]";
  public static final String ERROR_MSG_FAILED_TO_UPDATE_AN_INDEX_TO_ES = "Failed to update an index mapping [%s] to elastic search. Reason: [%s]";
  public static final String ERROR_MSG_FAILED_TO_INDEX_DOCUMENTS_TO_ES = "Failed to index the documents to elastic search. Reason: [%s]";
  public static final String ERROR_MSG_EXCEPTION_OCCURED = "Error occurred while sending a request to elastic search. Reason: [%s]";
  public static final String ERROR_MSG_NO_ACTIONS_PERFORMED = "There is an issue with index mapping creation or updation. Please try again.";
  public static final String ERROR_MSG_SOME_DOCS_FAILED_TO_INDEX = "Some documents are not indexed to elastic search. Please check the following response details to know more.\n{}";
  public static final String ERROR_MSG_FILE_NOT_FOUND = "File [%s] not found in [resources] folder path.";
  public static final String ERROR_MSG_UNABLE_TO_READ_PROPERTY_FILE = "Unable to read property file {}. Reason: {}";
  public static final String ERROR_MSG_HAS_ERRORS_WHILE_INDEXING_DOCUMENTS = "Has errors while indexing the documents: {}";
  
  // Http headers
  public static final String KEY_CONTENT_TYPE = "Content-Type";
  public static final String VAL_CONTENT_TYPE_JSON = "application/json";
  public static final String VAL_CONTENT_TYPE_XNDJSON = "application/x-ndjson";
  
  // Http response codes
  public static final int SUCCESS = 200;
  
  private GlobalConstants() {
    throw new IllegalStateException(ERROR_MSG_ILLEGAL_STATE_INITIALIZATION);
  }
}