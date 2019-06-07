package com.self.elasticsearch.builder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.self.constants.GlobalConstants;
import com.self.elasticsearch.exception.ElasticSearchDocumentProcessorException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

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
      URL urlObj = new URL(url);
      RequestSpecBuilder builder = new RequestSpecBuilder();

      for (Map.Entry<String, String> header : headers.entrySet()) {
        builder.addHeader(header.getKey(), header.getValue());
      }

      builder.setBody(reqParams);
      RequestSpecification reqSpec = builder.build();
      response =
        RestAssured
          .given(reqSpec)
          .config(
            RestAssured
              .config()
              .encoderConfig(
                new EncoderConfig()
                  .encodeContentTypeAs("application/x-ndjson", ContentType.TEXT)
                  .appendDefaultContentCharsetToContentTypeIfUndefined(false)))
          .post(urlObj);
    } catch (MalformedURLException ex) {
      throw new ElasticSearchDocumentProcessorException(
        String.format(GlobalConstants.ERROR_MSG_INVALID_URL, url));
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
      URL urlObj = new URL(url);
      RequestSpecification request = RestAssured.given().headers(headers).body(reqParams);

      response = request.put(urlObj);
    } catch (MalformedURLException ex) {
      throw new ElasticSearchDocumentProcessorException(
        String.format(GlobalConstants.ERROR_MSG_INVALID_URL, url));
    } catch (Exception ex) {
      throw new ElasticSearchDocumentProcessorException(
        String.format(GlobalConstants.ERROR_MSG_EXCEPTION_OCCURED, ex.getMessage()));
    }

    return response;
  }

  public static Response head(String url) throws ElasticSearchDocumentProcessorException {
    Response response = null;

    try {
      URL urlObj = new URL(url);
      RequestSpecification request = RestAssured.given();

      response = request.head(urlObj);
    } catch (MalformedURLException ex) {
      throw new ElasticSearchDocumentProcessorException(
        String.format(GlobalConstants.ERROR_MSG_INVALID_URL, url));
    } catch (Exception ex) {
      throw new ElasticSearchDocumentProcessorException(
        String.format(GlobalConstants.ERROR_MSG_EXCEPTION_OCCURED, ex.getMessage()));
    }

    return response;
  }
}
