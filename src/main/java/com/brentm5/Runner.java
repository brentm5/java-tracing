package com.brentm5;

import datadog.trace.api.Trace;
import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.UUID;

public class Runner implements Runnable {

    private static Logger logger = LogManager.getLogger(Runner.class);
    private final Tracer tracer = GlobalTracer.get();

    public void run(){
        while(true) {
            try (Scope scope = tracer.buildSpan("message.process").startActive(true)) {
                String nextMessage = getNextMessage();
                scope.span().setBaggageItem("message.id", nextMessage);
                processMessage(nextMessage);
            }
        }
    }

    @Trace
    private void processMessage(String message) {
        String threadId = String.format("Thread: %s", Thread.currentThread().getId());
        System.out.println(String.format("%s - %s", threadId, message));
        try {
            Thread.sleep(getRandomInt(1000));
        } catch (Exception e) {
        }
    }

    @Trace
    private String getNextMessage() {
        try {
            Thread.sleep(getRandomInt(500));
        } catch (Exception e) {
        }
        return UUID.randomUUID().toString();
    }

    private int getRandomInt(int max) {
        return new Random().nextInt(max);
    }
}
