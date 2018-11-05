package com.devindi.robometer;

import android.app.Activity;
import android.os.Bundle;

import com.devindi.robometer.consumer.LongLogger;
import com.devindi.robometer.duration.SimpleTracker;
import com.devindi.robometer.duration.StopwatchTracker;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timber.plant(new Timber.DebugTree());

        Robometer.init(
                Robometer.builder()
                        .defaultConsumers()
                        .defaultTrackers()
                        .consumers(new LongLogger())
                        .build()
        );

        Robometer.getInstance().getTracker(SimpleTracker.class).track("sample1", 4, TimeUnit.SECONDS);
        Robometer.getInstance().getTracker(SimpleTracker.class).track("sample3", 4, TimeUnit.SECONDS);
        Robometer.getInstance().getTracker(StopwatchTracker.class).start("new_thread");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Robometer.getInstance().getTracker(StopwatchTracker.class).stop("new_thread");
            }
        }).start();
    }
}
