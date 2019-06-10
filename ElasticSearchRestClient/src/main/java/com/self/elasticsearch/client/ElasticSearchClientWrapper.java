package com.self.elasticsearch.client;

import java.io.IOException;
import java.util.UUID;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;

import com.self.elasticsearch.client.builder.ConnectionBuilder;
import com.self.elasticsearch.client.builder.IndexBuilder;
import com.self.elasticsearch.constants.GlobalConstants;
import com.self.elasticsearch.exception.ConnectionBuilderException;
import com.self.elasticsearch.exception.ElasticSearchServiceException;
import com.self.elasticsearch.exception.IndexBuilderException;

/**
 * Elastic search actions such as create, read and update documents
 * 
 * @author ranjithr
 *
 */
public class ElasticSearchClientWrapper {
	private ElasticSearchClientWrapper() {
		throw new IllegalStateException("Illegal state of class initialization.");
	}

	public static boolean createIndex() throws ElasticSearchServiceException {
		CreateIndexRequest request = null;

		try {
			request = new CreateIndexRequest(GlobalConstants.INDEX_NAME);
			request.mapping(GlobalConstants.DOCUMENT_TYPE, IndexBuilder.getMapping());

			request.settings(IndexBuilder.getSettings());
			CreateIndexResponse createIndexResponse = ConnectionBuilder.getConnection().indices().create(request,
					RequestOptions.DEFAULT);

			if (!createIndexResponse.isAcknowledged()) {
				throw new ElasticSearchServiceException(
						String.format("Failed to create a new index [%s] to elastic search. Please try after sometime.", GlobalConstants.INDEX_NAME));
			}
		} catch (IndexBuilderException | IOException ex) {
			throw new ElasticSearchServiceException("Exception occurred while creating an index to elastic search.",
					ex);
		}

		return true;
	}

	public static boolean checkIndexExists() throws ElasticSearchServiceException {
		GetIndexRequest request = null;

		try {
			request = new GetIndexRequest();
			request.indices(GlobalConstants.INDEX_NAME);

			return ConnectionBuilder.getConnection().indices().exists(request, RequestOptions.DEFAULT);
		} catch (IOException ex) {
			throw new ElasticSearchServiceException(
					String.format("Exception occurred while checking the given index [%s] exists in elastic search.", GlobalConstants.INDEX_NAME), ex);
		}
	}

	public static boolean indexBulkDocuments() throws ElasticSearchServiceException {
		String uniqueID = UUID.randomUUID().toString();
		BulkRequest request = new BulkRequest();
		
		request.add(new IndexRequest(GlobalConstants.INDEX_NAME, GlobalConstants.DOCUMENT_TYPE, uniqueID)
				.source(XContentType.JSON, "message", "trying out Elasticsearch 10155"));

		try {
			BulkResponse bulkResponse = ConnectionBuilder.getConnection().bulk(request, RequestOptions.DEFAULT);

			if (bulkResponse.hasFailures()) {
				throw new ElasticSearchServiceException(
						"Failed to index new document(s) to elastic search. Please try after sometime.");
			}

		} catch (IOException ex) {
			throw new ElasticSearchServiceException(
					"Exception occurred while indexing bulk document(s) to elastic search.", ex);
		}

		return true;
	}

	public static void closeConnection() {
		try {
			ConnectionBuilder.close();
		} catch (ConnectionBuilderException ex) {
			ex.printStackTrace();
		}
	}
}
