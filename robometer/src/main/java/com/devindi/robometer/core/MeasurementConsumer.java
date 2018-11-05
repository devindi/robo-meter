package com.devindi.robometer.core;

public interface MeasurementConsumer {

    boolean isForMeasurement(Measurement measurement);

    void consume(Measurement measurement);
}
