package com.mibactechnologies.fasteventbus;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class EventBusTest extends TestCase implements Listener {
	@Rule
	public Timeout			globalTimeout	= new Timeout( 10 * 1000 );
	private EventBus		eventbus;
	private long			eventReceived;
	private List< Integer >	received;

	class Event extends com.mibactechnologies.fasteventbus.Event implements
			Cancellable {
		private boolean	cancelled;

		@Override
		public boolean isCancelled( ) {
			return cancelled;
		}

		@Override
		public void setCancelled( boolean cancelled ) {
			this.cancelled = cancelled;
		}
	}

	@Before
	public void setUp( ) {
		eventbus = new EventBus( );
		received = new ArrayList< Integer >( );
	}

	@EventHandler( priority = EventPriority.LOW )
	public void eventListener( final Event e ) {
		eventReceived = System.nanoTime( );
		received.add( 1 );
		e.setCancelled( true );
	}

	@EventHandler( priority = EventPriority.NORMAL, ignoreCancelled = true )
	public void eventListener2( Event e ) {
		received.add( 2 );
	}

	@EventHandler( priority = EventPriority.HIGH )
	public void eventListener3( Event e ) {
		received.add( -1 );
	}

	@EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = true )
	public void eventListener4( Event e ) {
		received.add( 3 );
	}

	@Test
	public void testNullCallEvent( ) {
		try {
			eventbus.callEvent( null );
			fail( "Expected IllegalArgumentException to be thrown" );
		} catch ( IllegalArgumentException e ) {}
	}

	@Test
	public void testNullListenerRegister( ) {
		try {
			eventbus.registerListener( null );
			fail( "Expected IllegalArgumentException to be thrown" );
		} catch ( IllegalArgumentException e ) {}
	}

	@Test
	public void testDuplicateListenerRegister( ) {
		try {
			eventbus.registerListener( this );
			eventbus.registerListener( this );
			fail( "Expected IllegalArgumentException to be thrown" );
		} catch ( IllegalArgumentException e ) {}
	}

	@Test
	public void testCallEvent( ) {
		eventbus.registerListener( this );
		assertTrue( "registerListener didn't work as expected", eventbus
				.getRegisteredListeners( ).contains( this ) );

		assertNotNull( "callEvent returned null when it shouldn't",
				eventbus.callEvent( new Event( ) ) );

		System.out.println( "Event call took "
				+ ( System.nanoTime( ) - eventReceived ) + " ns" );

		assertFalse( "Handler invoked when shouldn't be",
				received.contains( -1 ) );

		eventbus.removeListener( this );
		assertFalse( "removeListener didn't work as expected", eventbus
				.getRegisteredListeners( ).contains( this ) );
	}

	@Test
	public void testListenerRelated( ) {
		eventbus.registerListener( this );
		assertTrue( eventbus.getRegisteredListeners( ).contains( this ) );

		eventbus.removeListener( this );
		assertFalse( eventbus.getRegisteredListeners( ).contains( this ) );

		eventbus.registerListener( this );
		eventbus.clearListeners( );
		assertTrue( eventbus.getRegisteredListeners( ).isEmpty( ) );
	}

	@Test
	public void testDefault( ) {
		assertNotNull( "Default EventBus is null", EventBus.getDefault( ) );
	}
}
