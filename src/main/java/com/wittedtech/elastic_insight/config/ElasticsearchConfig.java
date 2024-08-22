package com.wittedtech.elastic_insight.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

@Configuration
public class ElasticsearchConfig {

    @Bean
    public ElasticsearchCustomConversions customConversions() {
        return new ElasticsearchCustomConversions(
                List.of(new InstantToLocalDateTimeConverter(), new LocalDateTimeToInstantConverter())
        );
    }

    @ReadingConverter
    public static class InstantToLocalDateTimeConverter implements Converter<Instant, LocalDateTime> {
        @Override
        public LocalDateTime convert(Instant source) {
            return LocalDateTime.ofInstant(source, ZoneId.systemDefault());
        }
    }

    @WritingConverter
    public static class LocalDateTimeToInstantConverter implements Converter<LocalDateTime, Instant> {
        @Override
        public Instant convert(LocalDateTime source) {
            return source.atZone(ZoneId.systemDefault()).toInstant();
        }
    }
}
