package com.devindi.robometer.duration;

import com.devindi.robometer.Robometer;

import java.util.concurrent.TimeUnit;

public class SimpleTracker {

    public void track(String key, long duration, TimeUnit timeUnit) {
        Robometer.getInstance().sendMeasurement(new Duration(key, duration, timeUnit));
    }
}
