package com.wisekrakr.util;

public class Time {
    public static double timeStarted = System.nanoTime();

    /**
     * Gets the time in seconds
     * @return time in seconds
     */
    public static double getTime(){return (System.nanoTime() - timeStarted) * 1E-9;}
}
