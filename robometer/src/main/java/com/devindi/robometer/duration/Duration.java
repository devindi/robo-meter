package com.devindi.robometer.duration;

import com.devindi.robometer.core.Measurement;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("WeakerAccess")
public class Duration implements Measurement {

    public static final String TYPE = "duration";

    private final String key;
    private final long value;
    private final TimeUnit timeUnit;

    public Duration(String key, long value, TimeUnit timeUnit) {
        this.key = key;
        this.value = value;
        this.timeUnit = timeUnit;
    }

    public String getKey() {
        return key;
    }

    public long getValue() {
        return value;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
