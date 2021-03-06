package com.mibactechnologies.fasteventbus;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class EventBus {
	private static final EventBus														DEFAULT			= new EventBus( );
	private static final EventHandlerAnnotation[ ]										EMPTYHANDLERS	= { };

	private final Map< Class< ? extends Event >, Collection< EventHandlerAnnotation >>	bindings;
	private final Set< Listener >														registeredListeners;

	private boolean																		debug			= false;

	class EventCall< T extends Event > implements Runnable {
		private T	event;

		public EventCall( T event ) {
			this.event = event;
		}

		public T getResult( ) {
			return event;
		}

		@Override
		public void run( ) {
			event = fireEvent( event );
		}

	}

	public EventBus( ) {
		bindings = new HashMap< Class< ? extends Event >, Collection< EventHandlerAnnotation >>( );
		registeredListeners = new HashSet< Listener >( );
	}

	public static EventBus getDefault( ) {
		return EventBus.DEFAULT;
	}

	public < T extends Event > T callEvent( final T event ) {
		if ( event == null )
			throw new IllegalArgumentException( "Event is null" );

		EventCall< T > call = new EventCall< T >( event );
		call.run( );

		return call.getResult( );
	}

	public void clearListeners( ) {
		bindings.clear( );
		registeredListeners.clear( );
	}

	private EventHandlerAnnotation createEventHandler( final Listener listener,
			final Method method, final EventHandler annotation ) {
		return new EventHandlerAnnotation( listener, method, annotation );
	}

	private < T extends Event > T fireEvent( final T event ) {
		final EventHandlerAnnotation[ ] handlers = getListenersFor( event
				.getClass( ) );

		if ( handlers == null ) {
			if ( debug )
				System.out.println( "Event "
						+ event.getClass( ).getSimpleName( )
						+ " has no handlers." );
			return event;
		}

		if ( debug )
			System.out.println( "Event " + event.getClass( ).getSimpleName( )
					+ " has " + handlers.length + " handlers." );

		final boolean cancellable = event instanceof Cancellable;

		for ( final EventHandlerAnnotation handler : handlers ) {
			boolean cancelled = cancellable ? ( ( Cancellable ) event )
					.isCancelled( ) : false;

			if ( !cancelled || cancelled
					&& handler.getAnnotation( ).ignoreCancelled( ) )
				handler.execute( event );
		}

		return event;
	}

	public Map< Class< ? extends Event >, Collection< EventHandlerAnnotation >> getBindings( ) {
		return new HashMap< Class< ? extends Event >, Collection< EventHandlerAnnotation >>(
				bindings );
	}

	public EventHandlerAnnotation[ ] getListenersFor(
			final Class< ? extends Event > clazz ) {
		final Collection< EventHandlerAnnotation > handlers = bindings
				.get( clazz );
		if ( handlers == null || handlers.isEmpty( ) )
			return EventBus.EMPTYHANDLERS;

		return handlers
				.toArray( new EventHandlerAnnotation[ handlers.size( ) ] );
	}

	public Set< Listener > getRegisteredListeners( ) {
		return new HashSet< Listener >( registeredListeners );
	}

	public void registerListener( final Listener listener ) {
		if ( debug )
			System.out.println( "Register event listener: " + listener );

		if ( registeredListeners.contains( listener ) )
			throw new IllegalArgumentException( "Listener already registered" );

		if ( listener == null )
			throw new IllegalArgumentException( "Null can't be registered" );

		registeredListeners.add( listener );

		final Method[ ] methods = listener.getClass( ).getDeclaredMethods( );

		for ( int i = 0; i < methods.length; i++ ) {
			final Method method = methods[ i ];

			final EventHandler annotation = method
					.getAnnotation( EventHandler.class );
			if ( annotation == null ) continue;

			final Class< ? >[ ] parameters = method.getParameterTypes( );
			if ( parameters.length != 1 ) // all listener methods should only
											// have
				// one parameter
				continue;

			final Class< ? > param = parameters[ 0 ];

			if ( !method.getReturnType( ).equals( void.class ) ) {
				if ( debug )
					System.out
							.println( "Ignoring method due to non-void return: "
									+ method.getName( ) );
				continue;
			}

			if ( Event.class.isAssignableFrom( param ) ) {
				@SuppressWarnings( "unchecked" )
				// Java doesn't understand that this actually is a safe
				// cast because of the above if-statement
				final Class< ? extends Event > realParam = ( Class< ? extends Event > ) param;

				if ( !bindings.containsKey( realParam ) )
					bindings.put( realParam,
							new TreeSet< EventHandlerAnnotation >( ) );
				final Collection< EventHandlerAnnotation > eventHandlersForEvent = bindings
						.get( realParam );
				if ( debug )
					System.out.println( "Add listener method: "
							+ method.getName( ) + " for event "
							+ realParam.getSimpleName( ) );
				eventHandlersForEvent.add( createEventHandler( listener,
						method, annotation ) );
			}
		}
	}

	public void removeListener( final Listener listener ) {
		for ( final Entry< Class< ? extends Event >, Collection< EventHandlerAnnotation >> ee : bindings
				.entrySet( ) ) {
			final Iterator< EventHandlerAnnotation > it = ee.getValue( )
					.iterator( );
			while ( it.hasNext( ) ) {
				final EventHandlerAnnotation curr = it.next( );
				if ( curr.getListener( ) == listener ) it.remove( );
			}
		}
		registeredListeners.remove( listener );
	}

	protected void setDebug( final boolean debug ) {
		this.debug = debug;
	}
}
