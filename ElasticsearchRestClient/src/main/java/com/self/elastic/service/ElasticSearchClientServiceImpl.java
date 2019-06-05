package com.self.elastic.service;

import com.self.elastic.client.ElasticSearchClientWrapper;
import com.self.elastic.exception.ElasticSearchServiceException;

/**
 * Elastic search client service implementation.
 * 
 * @author ranjithr
 *
 */
public class ElasticSearchClientServiceImpl implements ElasticSearchClientService {
	public void processDocToElasticSearch() {
		try {
			if (ElasticSearchClientWrapper.checkIndexExists()) {
				ElasticSearchClientWrapper.indexBulkDocuments();
			} else {
				if (ElasticSearchClientWrapper.createIndex()) {
					ElasticSearchClientWrapper.indexBulkDocuments();
				} else {
					throw new ElasticSearchServiceException(
							"Failed to index the document(s) to elastic search. Please try after sometime.");
				}
			}
		} catch (ElasticSearchServiceException ex) {
			ex.printStackTrace();
		}finally {
			ElasticSearchClientWrapper.closeConnection();
		}
	}
}
