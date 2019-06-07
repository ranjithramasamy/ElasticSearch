package com.self.elasticsearch.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.self.constants.GlobalConstants;
import com.self.utility.CommonUtils;

public class PropertyLoader {
  private static Properties property = null;

  private PropertyLoader() {
  }

  private static Properties getPropertyInstance() {
    if (property == null) {
      try (
        InputStream propStream =
          new FileInputStream(
            new CommonUtils()
              .getQualifiedResourcePathForFileName(GlobalConstants.CONFIG_PROPERTIES_FILE_NAME))) {
        property = new Properties();

        property.load(propStream);
      } catch (IOException ex) {
        System.out
          .println(
            String
              .format(
                GlobalConstants.ERROR_MSG_UNABLE_TO_READ_PROPERTY_FILE,
                GlobalConstants.CONFIG_PROPERTIES_FILE_NAME,
                ex.getMessage()));
      }
    }

    return property;
  }

  public static String getValue(String key) {
    if (null == getPropertyInstance()) {
      return "";
    }

    return getPropertyInstance().getProperty(key);
  }
}
