package com.wittedtech.elastic_insight.healthcheck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wittedtech.elastic_insight.model.LogEntry;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@RestController
public class ElasticSearchTestController {
	
	@Autowired
	private ElasticsearchOperations elasticsearchOperations;
	
	@GetMapping("/test-elasticsearch")
	public String testElasticsearchConnection() {
		boolean isNodeAvailable = elasticsearchOperations.indexOps(LogEntry.class).exists();
		return isNodeAvailable ? "Elasticsearch is connected!" : "Failed to connect to Elasticsearch.";
	}
	
}
