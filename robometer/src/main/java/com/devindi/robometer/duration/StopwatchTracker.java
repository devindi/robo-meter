package com.devindi.robometer.duration;

import com.devindi.robometer.Robometer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class StopwatchTracker {

    private final Map<String, Long> measurementsMap;

    public StopwatchTracker() {
        measurementsMap = new HashMap<>();
    }

    public boolean start(String key) {
        long start = System.nanoTime();
        if (measurementsMap.containsKey(key)) {
            return false;
        }
        measurementsMap.put(key, start);
        return true;
    }

    public boolean stop(String key) {
        long stop = System.nanoTime();
        Long start = measurementsMap.remove(key);
        if (start == null) {
            return false;
        }
        long diff = stop - start;
        Robometer.getInstance().sendMeasurement(new Duration(key, diff, TimeUnit.NANOSECONDS));
        return true;
    }

    public boolean cancel(String key) {
        return measurementsMap.remove(key) != null;
    }
}
