package com.brentm5;

import datadog.trace.api.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Runner extends AbstractRunner implements FancyRunner {

    private static Logger logger = LoggerFactory.getLogger(Runner.class);

    @Trace
    @Override
    protected void consume(String message) {
        try {
            Thread.sleep(getRandomInt(1000));
            fancify(message);
        } catch (Exception e) {
            notifyError(e);
        }
    }

    @Override
    public void fancify(String message) {
        logger.info(message);
    }

    private void notifyError(Exception e) {
        logger.error("Error with message", e);

    }
}
