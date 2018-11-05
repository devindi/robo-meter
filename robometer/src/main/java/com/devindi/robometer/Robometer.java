package com.devindi.robometer;

import android.util.Log;

import com.devindi.robometer.core.CompositeConsumer;
import com.devindi.robometer.core.Measurement;
import com.devindi.robometer.core.MeasurementConsumer;
import com.devindi.robometer.duration.StopwatchTracker;
import com.devindi.robometer.duration.SimpleTracker;
import com.devindi.robometer.log.DurationLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Robometer {

    private static Robometer INSTANCE;

    public static Robometer getInstance() {
        return INSTANCE;
    }

    public static void init() {
        init(builder().defaultTrackers().defaultConsumers().build());
    }

    public static void init(Robometer robometer) {
        INSTANCE = robometer;
    }

    public static Builder builder() {
        return new Builder();
    }

    private final Map<Class, Object> trackerMap;
    private final MeasurementConsumer rootConsumer;

    private Robometer(List<Object> trackers, MeasurementConsumer rootConsumer) {
        this.rootConsumer = rootConsumer;
        trackerMap = new HashMap<>();
        for (Object tracker : trackers) {
            trackerMap.put(tracker.getClass(), tracker);
        }
    }

    public void sendMeasurement(Measurement measurement) {
        rootConsumer.consume(measurement);
    }

    public <T> T getTracker(Class<T> clazz) {
        //noinspection unchecked
        return (T) trackerMap.get(clazz);
    }

    static class Builder {

        private List<MeasurementConsumer> measurementConsumers = new ArrayList<>();
        private List<Object> trackers = new ArrayList<>();
        private ExecutorService executor = Executors.newSingleThreadExecutor();

        Builder trackers(Object... trackers) {
            Collections.addAll(this.trackers, trackers);
            return this;
        }

        Builder consumers(MeasurementConsumer... consumers) {
            Collections.addAll(measurementConsumers, consumers);
            return this;
        }

        Builder executor(ExecutorService executor) {
            this.executor = executor;
            return this;
        }

        Builder defaultConsumers() {
            measurementConsumers.add(new DurationLogger(Log.INFO));
            return this;
        }

        Builder defaultTrackers() {
            trackers.add(new SimpleTracker());
            trackers.add(new StopwatchTracker());
            return this;
        }

        Robometer build() {
            CompositeConsumer compositeConsumer = new CompositeConsumer(measurementConsumers, executor);
            return new Robometer(trackers, compositeConsumer);
        }
    }
}
