package com.mibactechnologies.fasteventbus;

import java.security.InvalidParameterException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.Parameterized.Parameter;

public class EventBusTest extends TestCase implements Listener {
	@Rule
	public Timeout		globalTimeout	= new Timeout( 10 * 1000 );
	private EventBus	eventbus;
	private long		eventReceived;

	@Before
	public void setUp( ) {
		eventbus = new EventBus( );
	}

	@EventHandler
	public void eventListener( final Event e ) {
		eventReceived = System.nanoTime( );
	}

	@Test
	public void testNullEventCall( ) {
		try {
			eventbus.callEvent( null );
			fail( "Expected InvalidParameterException to be thrown" );
		} catch ( InvalidParameterException e ) {}
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

		eventbus.removeListener( this );
		assertFalse( "removeListener didn't work as expected", eventbus
				.getRegisteredListeners( ).contains( this ) );
	}

	@Test
	public void testListenersMethods( ) {
		eventbus.registerListener( this );
		eventbus.clearListeners( );
		assertTrue( eventbus.getRegisteredListeners( ).isEmpty( ) );
	}

	@Test
	public void testDefault( ) {
		assertNotNull( "Default EventBus is null", EventBus.getDefault( ) );
	}
}
