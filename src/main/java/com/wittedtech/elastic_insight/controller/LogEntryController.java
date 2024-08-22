package com.wittedtech.elastic_insight.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wittedtech.elastic_insight.model.LogEntry;
import com.wittedtech.elastic_insight.service.LogEntryService;

@RestController
@RequestMapping("/logs")
public class LogEntryController {

    @Autowired
    private LogEntryService logEntryService;

    @PostMapping
    public LogEntry createLogEntry(@RequestBody LogEntry logEntry) {
        return logEntryService.saveLogEntry(logEntry);
    }

    @GetMapping("/level/{level}")
    public List<LogEntry> getLogsByLevel(@PathVariable String level) {
        return logEntryService.findLogsByLevel(level);
    }

    @GetMapping("/source/{source}")
    public List<LogEntry> getLogsBySource(@PathVariable String source) {
        return logEntryService.findLogsBySource(source);
    }

    @GetMapping("/range")
    public List<LogEntry> getLogsInRange(@RequestParam String start, @RequestParam String end) {
        LocalDateTime startTime = LocalDateTime.parse(start);
        LocalDateTime endTime = LocalDateTime.parse(end);
        return logEntryService.findLogsInTimeRange(startTime, endTime);
    }
    
    @GetMapping("/search")
    public SearchHits<LogEntry> searchLogs(@RequestParam String query) {
        return logEntryService.searchLogs(query);
    }
}
