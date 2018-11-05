package com.devindi.robometer.log;

import com.devindi.robometer.core.MeasurementConsumer;
import com.devindi.robometer.core.Measurement;
import com.devindi.robometer.duration.Duration;

import timber.log.Timber;

public class DurationLogger implements MeasurementConsumer {

    private final int priority;

    public DurationLogger(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean isForMeasurement(Measurement measurement) {
        return Duration.TYPE.equals(measurement.getType());
    }

    @Override
    public void consume(Measurement measurement) {
        Duration duration = (Duration) measurement;
        Timber.log(priority, "Measured: \"%s\" for %d %s", duration.getKey(), duration.getValue(), duration.getTimeUnit().toString());
    }
}
