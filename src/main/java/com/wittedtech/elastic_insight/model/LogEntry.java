package com.wittedtech.elastic_insight.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "logs")
public class LogEntry {
	@Id
	@JsonProperty("id")
    private String id;
    
    @JsonProperty("level")
    private String level;
    
    @JsonProperty("message")
    private String message;
    
    @JsonProperty("timestamp")
    private Instant timestamp;
    
    @JsonProperty("source")
    private String source;
    
    @JsonProperty("stackTrace")
    private String stackTrace;
	
    public LogEntry() {}

	public LogEntry(String id, String level, String message, Instant timestamp, String source,
			String stackTrace) {
		super();
		this.id = id;
		this.level = level;
		this.message = message;
		this.timestamp = timestamp;
		this.source = source;
		this.stackTrace = stackTrace;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	@Override
	public String toString() {
		return "LogEntry [id=" + id + ", level=" + level + ", message=" + message + ", timestamp=" + timestamp
				+ ", source=" + source + ", stackTrace=" + stackTrace + "]";
	}
    
    
	
}
