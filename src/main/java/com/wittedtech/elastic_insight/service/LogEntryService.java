package com.wittedtech.elastic_insight.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import com.wittedtech.elastic_insight.model.LogEntry;
import com.wittedtech.elastic_insight.repository.LogEntryRepository;

@Service
public class LogEntryService {
	@Autowired
	private LogEntryRepository logEntryRepository;
	
	@Autowired
	private  ElasticsearchOperations elasticsearchOperations;
	
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
    
    public SearchHits<LogEntry> searchLogs(String searchTerm) {
        // Use a simple query string for full-text search
        String searchQuery = String.format("{\"multi_match\": {\"query\": \"%s\", \"fields\": [\"message\", \"stackTrace\"]}}", searchTerm);

        Query query = new StringQuery(searchQuery);

        // Perform the search
        return elasticsearchOperations.search(query, LogEntry.class);
    }
    
}
