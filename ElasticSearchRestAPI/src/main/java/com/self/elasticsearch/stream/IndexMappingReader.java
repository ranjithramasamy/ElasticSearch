package com.self.elasticsearch.stream;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.self.elasticsearch.constants.GlobalConstants;
import com.self.elasticsearch.utility.CommonUtils;

/**
 * Index mapping reader
 * 
 * @author ranjithr
 *
 */
public class IndexMappingReader {
	private static final Logger logger = LoggerFactory.getLogger(LogFileReader.class);

	private IndexMappingReader() {
		throw new IllegalStateException(GlobalConstants.ERROR_MSG_ILLEGAL_STATE_INITIALIZATION);
	}

	public static String getAll() {
		JsonObject jsonObject = getJsonObjectFromFile();

		if (null == jsonObject) {
			return "";
		}

		return jsonObject.toString();
	}

	public static String getMappingsOnly() {
		JsonObject jsonObject = getJsonObjectFromFile();

		if (null == jsonObject) {
			return "";
		}

		return jsonObject.getAsJsonObject("mappings").getAsJsonObject(GlobalConstants.DOCUMENT_TYPE).toString();
	}

	private static JsonObject getJsonObjectFromFile() {
		Gson gson = new Gson();
		JsonObject jsonObject = null;

		try {
			logger.info(new CommonUtils().getQualifiedResourcePathForFileName(GlobalConstants.INDEX_MAPPING_FILE_NAME));

			jsonObject = gson.fromJson(
					new FileReader(new CommonUtils()
							.getQualifiedResourcePathForFileName(GlobalConstants.INDEX_MAPPING_FILE_NAME)),
					JsonObject.class);
		} catch (JsonSyntaxException | JsonIOException ex) {
			logger.error(GlobalConstants.ERROR_MSG_INVALID_IDX_MAPPING_JSON, ex.getMessage());
		} catch (FileNotFoundException ex) {
			logger.error(GlobalConstants.ERROR_MSG_MAPPING_JSON_FILE_NOT_FOUND, ex.getMessage());
		}

		return jsonObject;
	}
}
