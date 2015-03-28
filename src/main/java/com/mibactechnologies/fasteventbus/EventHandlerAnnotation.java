package com.mibactechnologies.fasteventbus;

import java.lang.reflect.Method;

class EventHandlerAnnotation implements Comparable<EventHandlerAnnotation> {
    private final Listener listener;
    private final Method method;
    private final EventHandler annotation;

    public EventHandlerAnnotation(final Listener listener, final Method method,
	    final EventHandler annotation) {
	this.listener = listener;
	this.method = method;
	this.annotation = annotation;
    }

    public boolean allowChilds() {
	return annotation.allowChilds();
    }

    @Override
    public int compareTo(final EventHandlerAnnotation other) {
	final int diff = annotation.priority().getIndex()
		- other.annotation.priority().getIndex();
	return diff == 0 ? 1 : diff;
    }

    public void execute(final Event event) {
	try {
	    method.invoke(listener, event);
	} catch (final Exception e) {
	    System.out.println("Exception when performing EventHandler "
		    + listener + " for event " + event.toString());
	    e.printStackTrace();
	    throw new IllegalStateException("Unable to call " + method, e);
	}
    }

    public EventHandler getAnnotation() {
	return annotation;
    }

    public Listener getListener() {
	return listener;
    }

    public Method getMethod() {
	return method;
    }

    public EventPriority getPriority() {
	return annotation.priority();
    }

    public boolean isIgnoringCancelled() {
	return annotation.ignoreCancelled();
    }

    @Override
    public String toString() {
	return "(EventHandler " + listener + ": " + method.getName() + ")";
    }
}