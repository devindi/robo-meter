package com.devindi.robometer.consumer;

import com.devindi.robometer.core.MeasurementConsumer;
import com.devindi.robometer.core.Measurement;

import timber.log.Timber;

public class LongLogger implements MeasurementConsumer {

    @Override
    public boolean isForMeasurement(Measurement measurement) {
        return true;
    }

    @Override
    public void consume(Measurement measurement) {
        try {
            Thread.sleep(15_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Timber.w("Measured");
    }
}
