package com.brentm5;

import datadog.trace.api.Trace;

public interface FancyRunner {

    @Trace
    void fancify(String message);
}
