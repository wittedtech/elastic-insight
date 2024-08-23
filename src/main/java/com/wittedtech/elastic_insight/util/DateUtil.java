package com.wittedtech.elastic_insight.util;

import java.time.Instant;

public class DateUtil {
	public static long toEpochMillis(Instant instant) {
        return instant.toEpochMilli();
    }
}
