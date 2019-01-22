package com.brentm5;

import datadog.trace.api.Trace;
import datadog.trace.api.interceptor.MutableSpan;
import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.UUID;

abstract class AbstractRunner implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(AbstractRunner.class);
    private final Tracer tracer = GlobalTracer.get();

    public void run(){
        while(true) {
            try (Scope scope = tracer.buildSpan("message.process").startActive(true)) {
                String nextMessage = getNextMessage();
                scope.span().setBaggageItem("message.id", nextMessage);
                handleMessage(nextMessage);
            }
        }
    }

    @Trace
    protected abstract void consume(String message);

    @Trace
    private void handleMessage(String message) {
        try {
            consume(message);
            if (tracer != null && tracer.activeSpan() != null) {
                ((MutableSpan)tracer.activeSpan()).getRootSpan().setError(true);
            }
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

    protected int getRandomInt(int max) {
        return new Random().nextInt(max);
    }
}
