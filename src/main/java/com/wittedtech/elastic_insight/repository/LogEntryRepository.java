package com.wittedtech.elastic_insight.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.wittedtech.elastic_insight.model.LogEntry;

public interface LogEntryRepository extends ElasticsearchRepository<LogEntry, String> {
	List<LogEntry> findByLevel(String level);

    List<LogEntry> findBySource(String source);

    List<LogEntry> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
