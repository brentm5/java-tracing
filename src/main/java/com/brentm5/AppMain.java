package com.brentm5;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppMain {

    private static Logger logger = LogManager.getLogger(AppMain.class);

    public static void main(String[] args) {
        System.out.println("Starting threads");
        Thread newThread = new Thread(new Runner());
        newThread.start();
    }
}
