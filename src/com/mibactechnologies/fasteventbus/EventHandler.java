package com.mibactechnologies.fasteventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indication that a method should handle an {@link Event}<br>
 * The method needs to return {@link Void} and expect exactly one parameter of a
 * type that implements {@link IEvent}.<br>
 * Only methods in classes that implements {@link Listener Listener} should use
 * this annotation (doesn't do anything without implementing Listener).
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {
    boolean ignoreCancelled() default false;

    EventPriority priority() default EventPriority.NORMAL;
}