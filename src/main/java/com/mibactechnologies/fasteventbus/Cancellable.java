package com.mibactechnologies.fasteventbus;

/**
 * Interface made for creating cancellable events - they can be cancelled (it's
 * better to implement if your event is cancellable, otherwise it [system
 * (exactly {@link EventBus EventExecutor})] won't support
 * <code>@EventHandler(ignoreCancelled)</code>)
 */
public interface Cancellable {
    /**
     * Gets the cancellation state of this event. A cancelled event will not be
     * executed in the server, but will still pass to other plugins
     *
     * @return true if this event is cancelled
     */
    public boolean isCancelled();

    /**
     * Sets the cancellation state of this event. A cancelled event will not be
     * executed in the server, but will still pass to other plugins.
     *
     * @param cancelled
     *            true if you wish to cancel this event
     */
    public void setCancelled(final boolean cancelled);
}
