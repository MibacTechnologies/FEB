package com.mibactechnologies.fasteventbus.test;

public class CustomEvent2 extends CustomEvent {
    private final long start;

    public CustomEvent2() {
	start = System.nanoTime();
    }

    public long getStart() {
	return start;
    }
}
