package com.mibactechnologies.fasteventbus;

public class EventBusTest implements Listener {
    private final EventBus eventbus;

    public EventBusTest() {
	eventbus = new EventBus();
    }

    @EventHandler
    public void eventListener(final Event e) {
	e.getEventName();
	e.toString();
    }

    public void testCallEvent() {
	eventbus.callEvent(new Event());
    }

    public void testCallEvent2() {
	eventbus.registerListener(this);
	eventbus.callEvent(new Event());
	eventbus.removeListener(this);
    }

    public void testClearListeners() {
	eventbus.clearListeners();
    }

    public void testClearListeners2() {
	eventbus.registerListener(this);
	eventbus.clearListeners();
    }

    public void testDefault() {
	EventBus.getDefault();
    }

    public void testRegisterUnregister() {
	eventbus.registerListener(this);
	eventbus.removeListener(this);
    }
}
