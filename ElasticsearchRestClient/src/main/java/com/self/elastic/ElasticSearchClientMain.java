package com.self.elastic;

import com.self.elastic.service.ElasticSearchClientService;
import com.self.elastic.service.ElasticSearchClientServiceImpl;

public class ElasticSearchClientMain {

	public static void main(String[] args) {
		ElasticSearchClientService clientService = new ElasticSearchClientServiceImpl();

		clientService.processDocToElasticSearch();
	}
}
