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
    /**
     * -- work in progress --<br>
     * <br>
     * If handler should receive child events, set to true
     * 
     */
    boolean allowChilds() default false;

    /**
     * If handler should receive cancelled events, set to true
     */
    boolean ignoreCancelled() default false;

    /**
     * Priority of event receiving<br>
     * <br>
     * ({@link EventPriority#LOWEST lowest} >> {@link EventPriority#LOW low} >>
     * {@link EventPriority#NORMAL normal} >> {@link EventPriority#HIGH high} >>
     * {@link EventPriority#HIGHEST highest} >> {@link EventPriority#MONITOR
     * monitor})
     */
    EventPriority priority() default EventPriority.NORMAL;
}