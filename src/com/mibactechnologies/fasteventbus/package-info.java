/**
 * Based on http://codereview.stackexchange.com/questions/36153/my-event-handling-system<br>
 * Modifications: 
 * <li> EventPriority (enumeration) instead of priority (integer)
 * <li> Cancellable events
 * <li> EventHandler mentioned in URL above
 * <li> EventExecutor (1st part) mentioned in URL above
 * <li> EventHandlerAnnotation with default access modifier (was public)
 * <li> Can contain other changes not described here
 */
package com.mibactechnologies.fasteventbus;