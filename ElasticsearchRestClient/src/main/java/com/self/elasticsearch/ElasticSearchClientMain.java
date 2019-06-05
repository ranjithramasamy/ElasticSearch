package com.self.elasticsearch;

import com.self.elasticsearch.service.ElasticSearchClientService;
import com.self.elasticsearch.service.ElasticSearchClientServiceImpl;

public class ElasticSearchClientMain {

	public static void main(String[] args) {
		ElasticSearchClientService clientService = new ElasticSearchClientServiceImpl();

		clientService.processDocToElasticSearch();
	}
}
