package com.mibactechnologies.fasteventbus.test;

import com.mibactechnologies.fasteventbus.EventHandler;
import com.mibactechnologies.fasteventbus.EventPriority;
import com.mibactechnologies.fasteventbus.Listener;

public class listener3 implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onCustomEvent2(final CustomEvent2 e) {
	e.setCancelled(true);
	// Main.log(e.toString() + "<-- to string");
    }
}
