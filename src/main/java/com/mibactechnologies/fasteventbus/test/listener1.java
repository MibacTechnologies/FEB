package com.mibactechnologies.fasteventbus.test;

import com.mibactechnologies.fasteventbus.EventHandler;
import com.mibactechnologies.fasteventbus.Listener;

public class listener1 implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void CustomEventTest1(final CustomEvent e) {
	System.out.println(e.toString());
	System.out.println(e.equals(e) ? "true (ok)" : "false (!ok)");
	// System.out.println(e.getStrangeity() + "<-- strangeity : bool -->"
	// + e.isThisTestWhichProbobalyWontWork());
    }
}
