package com.devindi.robometer.core;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CompositeConsumer implements MeasurementConsumer {

    private final List<MeasurementConsumer> consumers;
    private final ExecutorService executor;

    public CompositeConsumer(List<MeasurementConsumer> consumers, ExecutorService executor) {
        this.consumers = consumers;
        this.executor = executor;
    }

    public void addConsumer(MeasurementConsumer measurementConsumer) {
        consumers.add(measurementConsumer);
    }

    @Override
    public boolean isForMeasurement(Measurement measurement) {
        return true;
    }

    @Override
    public void consume(final Measurement measurement) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                for (MeasurementConsumer consumer : consumers) {
                    if (consumer.isForMeasurement(measurement)) {
                        consumer.consume(measurement);
                    }
                }
            }
        });
    }
}
