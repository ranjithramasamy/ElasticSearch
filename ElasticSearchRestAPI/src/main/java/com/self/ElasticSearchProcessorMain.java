package com.self;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.self.elasticsearch.ElasticSearchDocumentProcessor;
import com.self.elasticsearch.utility.CommonUtils;

public class ElasticSearchProcessorMain {
	private static final Logger logger = LoggerFactory.getLogger(ElasticSearchProcessorMain.class);

	public static void main(String[] args) {
		try {
			String logFileName = new CommonUtils().getQualifiedResourcePathForFileName("1_automation_logs.log");
			ElasticSearchDocumentProcessor esDataProcessor = new ElasticSearchDocumentProcessor();

			esDataProcessor.load(logFileName);
		} catch (FileNotFoundException ex) {
			logger.error(ex.getMessage());
		}
	}
}
