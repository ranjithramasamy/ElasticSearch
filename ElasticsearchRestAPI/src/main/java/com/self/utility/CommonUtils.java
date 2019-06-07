package com.self.utility;

import java.io.FileNotFoundException;
import java.net.URL;

import com.self.constants.GlobalConstants;

/**
 * Common utility
 * 
 * @author ranjithr
 *
 */
public class CommonUtils {
  public String getQualifiedResourcePathForFileName(String fileName) throws FileNotFoundException {
    URL url = getClass().getClassLoader().getResource(fileName);

    if (null == url) {
      throw new FileNotFoundException(
        String.format(GlobalConstants.ERROR_MSG_FILE_NOT_FOUND, fileName));
    }

    return url.getPath();
  }
}
