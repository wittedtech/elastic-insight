package com.wittedtech.elastic_insight.service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import com.wittedtech.elastic_insight.model.LogEntry;
import com.wittedtech.elastic_insight.repository.LogEntryRepository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HighlightField;
import co.elastic.clients.elasticsearch.core.search.Hit;

@Service
public class LogEntryService {
	@Autowired
	private LogEntryRepository logEntryRepository;
	
	@Autowired
	private  ElasticsearchClient elasticsearchClient;
	
	// ============== Basic CRUD Operations ===================
	
	public LogEntry saveLogEntry(LogEntry logEntry) {
        logEntry.setTimestamp(Instant.now());
        return logEntryRepository.save(logEntry);
    }

    public List<LogEntry> findLogsByLevel(String level) {
        return logEntryRepository.findByLevel(level);
    }

    public List<LogEntry> findLogsBySource(String source) {
        return logEntryRepository.findBySource(source);
    }

    public List<LogEntry> findLogsInTimeRange(LocalDateTime start, LocalDateTime end) {
        return logEntryRepository.findByTimestampBetween(start, end);
    }
    
    //================== FULL TEXT SEARCH FUNCTIONALITY =====================
    
    public List<LogEntry> searchLogs(String searchTerm) throws IOException {
    	// Build the search request
        SearchRequest searchRequest = new SearchRequest.Builder()
            .index("logs")
            .query(q -> q
                .multiMatch(m -> m
                    .query(searchTerm)
                    .fields("level", "message", "source", "stackTrace")
                )
            )
            .highlight(h -> h
                .fields("message", f -> f
                    .preTags("<em>")
                    .postTags("</em>")
                )
            )
            .build();

        // Execute the search request
        SearchResponse<LogEntry> searchResponse = elasticsearchClient.search(searchRequest, LogEntry.class);

        // Handle the response
        
        List<LogEntry> result = searchResponse.hits().hits().stream()
        .map(hit -> {
            // Extract highlights if needed
            // Optional: You can extract highlights from the response if needed
            return hit.source();
        })
        .collect(Collectors.toList());
        
        return result;
    }
    
}
