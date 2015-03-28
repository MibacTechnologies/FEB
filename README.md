#FEB (Fast Event Bus)

[![Codeship Status for MibacTechnologies/FEB](https://codeship.com/projects/9845fa70-b6f8-0132-291f-76108d3aca64/status?branch=1.1)](https://codeship.com/projects/71199)

FEB...

* makes your code simpler
* is fast
* is tiny
* has features like receiving priorities, and **cancellable events**  


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

Additional features and notes
------------------------------
* **Based on annotations:** You don't have to create new class for every event you want to receive.
* **Performance optimized:** It's one of the fastest event buses.
* **Convenience singleton:** You can get a process wide event bus instance by calling EventBus.getDefault(). You can still call new EventBus() to create any number of local busses.
* **Cancellable events:** This event bus is different! You can **cancel events**!
* **Receiving priorities:** You can decide when you want to invoke your listener.

Comparison
----------
For now, with none other buses.

<table>
	<tr>
		<th></th>
		<th>Fast Event Bus</th>
	</tr>
	<tr>
		<th>Event handling methods</th>
		<th>Annotations</th>
	</tr>	
    <tr>
        <th>Asynchronous event delivery</th>
        <th>Yes</th>
    </tr>
	<tr>
		<th>Cancellable events</th>
		<th>Yes</th>
	</tr>	
    <tr>
        <th>Receiving priorities</th>
        <th>Yes (enum)</th>
    </tr>
</table>