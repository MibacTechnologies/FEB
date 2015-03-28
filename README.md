#FEB (Fast Event Bus)

[![Codeship Status for MibacTechnologies/FEB](https://codeship.com/projects/9845fa70-b6f8-0132-291f-76108d3aca64/status?branch=1.1)](https://codeship.com/projects/71199)

FEB...

* makes your code simpler
* is fast
* is tiny
* has features like receiving priorities

How to use ?
------------
###1. Define events:
```java
public class ExampleEvent {
	/* additional fields if needed */
}
```

###2. Prepare listeners:
```java
// constructor (or any method)
EventExecutor eventExecutor = new EventExecutor( );
eventExecutor.registerListener( this );

// another method
@EventHandler
public void IReceiveEvents( ExampleEvent event ) {
	/* do something */
}
```

###3. Call events:
```java
eventExecutor.callEvent( new ExampleEvent( ) );
```
