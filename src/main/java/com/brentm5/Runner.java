package com.brentm5;

import datadog.trace.api.Trace;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Runner extends AbstractRunner {

    private static Logger logger = LogManager.getLogger(Runner.class);
    private final Tracer tracer = GlobalTracer.get();

    @Trace
    @Override
    protected void consume(String message) {
        String threadId = String.format("Thread: %s", Thread.currentThread().getId());
        System.out.println(String.format("%s - %s", threadId, message));
        try {
            Thread.sleep(getRandomInt(1000));
        } catch (Exception e) {
        }
    }
}
