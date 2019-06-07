package com.self;

import java.io.FileNotFoundException;

import com.self.elasticsearch.ElasticSearchDocumentProcessor;
import com.self.elasticsearch.utility.CommonUtils;

public class ElasticSearchProcessorMain {

  public static void main(String[] args) {
    try {
      String logFileName = new CommonUtils().getQualifiedResourcePathForFileName("1_automation_logs.log");
      ElasticSearchDocumentProcessor esDataProcessor = new ElasticSearchDocumentProcessor();
      
      esDataProcessor.load(logFileName);
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
  }
}
