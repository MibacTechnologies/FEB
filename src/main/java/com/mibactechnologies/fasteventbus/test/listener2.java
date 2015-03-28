package com.mibactechnologies.fasteventbus.test;

import com.mibactechnologies.fasteventbus.EventHandler;
import com.mibactechnologies.fasteventbus.EventPriority;
import com.mibactechnologies.fasteventbus.Listener;

public class listener2 implements Listener {
    @EventHandler(priority = EventPriority.NORMAL)
    public void CustomEventTest1(final CustomEvent e) {
	// /e.setCancelled(true);
	// Main.log(e.isCancelled() + "!");
    }
}
