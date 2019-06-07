package com.self.elasticsearch.stream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.self.constants.GlobalConstants;
import com.self.generator.logs.LogsforKibana;

/**
 * Automation log file reader.
 * 
 * @author ranjithr
 *
 */
public class LogFileReader {
  private LogFileReader() {
    throw new IllegalStateException(GlobalConstants.ERROR_MSG_ILLEGAL_STATE_INITIALIZATION);
  }
  
  public static String get(String filePath) {
    StringBuilder builder = new StringBuilder();
    
    try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
      String currItem = "";
      
      // Skipping first line as it is not required.
      reader.readLine();
      
      while ((currItem = reader.readLine()) != null) {
        builder.append(currItem).append(System.getProperty("line.separator"));
      }
    } catch (FileNotFoundException ex) {
      LogsforKibana
      .messages(
        String.format(GlobalConstants.ERROR_MSG_MAPPING_LOG_FILE_NOT_FOUND, filePath, ex.getMessage()));
    } catch (IOException ex) {
      LogsforKibana
      .messages(
        String.format(GlobalConstants.ERROR_MSG_MAPPING_LOG_FILE_NOT_ABLE_TO_READ, filePath, ex.getMessage()));
    }    
    
    return builder.toString();    
  }
}
