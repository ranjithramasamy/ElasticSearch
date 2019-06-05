package com.self.elastic.client.builder;

import java.io.IOException;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.self.elastic.constants.GlobalConstants;
import com.self.elastic.exception.IndexBuilderException;

/**
 * Schema definition of index
 * 
 * @author ranjithr
 *
 */
public class IndexBuilder {
	private IndexBuilder() {
		throw new IllegalStateException("Illegal state of class initialization.");
	}

	public static XContentBuilder getMapping() throws IndexBuilderException {
		XContentBuilder builder = null;

		try {
			builder = XContentFactory.jsonBuilder();

			builder.startObject();
			{
				builder.startObject(GlobalConstants.DOCUMENT_TYPE);
				{
					builder.startObject("properties");
					{
						builder.startObject("message");
						{
							builder.field("type", "text");
						}

						builder.endObject();
					}
					
					builder.endObject();
				}

				builder.endObject();
			}

			builder.endObject();
		} catch (IOException ex) {
			throw new IndexBuilderException("Exception occurred while building an index mapping.", ex);
		}

		return builder;
	}

	public static Builder getSettings() {
		return Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 1);
	}
}
