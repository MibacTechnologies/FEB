#FEB (Fast Event Bus)

[![Codeship Status for MibacTechnologies/FEB](https://img.shields.io/codeship/9845fa70-b6f8-0132-291f-76108d3aca64/1.1.svg?style=flat-square)](https://codeship.com/projects/71199)[![Coverage Status](https://img.shields.io/coveralls/MibacTechnologies/FEB/1.1.svg?style=flat-square)](https://coveralls.io/r/MibacTechnologies/FEB)[![License](https://img.shields.io/badge/license-GPL%20v2-brightgreen.svg?style=flat-square)](https://img.shields.io/badge/license-GPL%20v2-brightgreen.svg?style=flat-square)

FEB...

* makes your code simpler
* is fast
* is tiny
* has features like receiving priorities, and **cancellable events**  


How to use ?
------------
###1. Define events:
```java
public class ExampleEvent extends Event {
	/* additional fields if needed */
}
```

###2. Prepare listeners:
```java
// constructor (or any method)
EventBus eventbus = new EventBus( ); // or EventBus.getDefault()
eventbus.registerListener( this );

// another method
@EventHandler
public void receiveEvent( ExampleEvent event ) {
	/* do something */
}
```

###3. Call events:
```java
eventbus.callEvent( new ExampleEvent( ) );
```

Additional features and notes
------------------------------
* **Based on annotations:** You don't have to create new class for every event you want to receive.
* **Performance optimized:** It's one of the fastest event buses.
* **Convenience singleton:** You can get a process wide event bus instance by calling EventBus.getDefault(). You can still call new EventBus() to create any number of local busses.
* **Cancellable events:** This event bus is different! You can **cancel events**!
* **Receiving priorities:** You decide when your listener is invoked.

Comparison
----------
For now, with none other buses.

<table>
	<tr>
		<th></th>
		<th>FastEventBus 1.1</th>
		<th>FastEventBus 1.0</th>
	</tr>
	<tr>
		<th>Event handling methods</th>
		<td>Annotations</td>
		<td>Annotations</td>
	</tr>	
    <tr>
        <th>Asynchronous event delivery</th>
        <td>Yes</td>
        <td>No</td>
    </tr>
	<tr>
		<th>Cancellable events</th>
		<td>Yes</td>
		<td>Yes</td>
	</tr>	
    <tr>
        <th>Receiving priorities</th>
        <td>Yes (enum)</td>
        <td>Yes (enum)</td>
    </tr>
    <tr>
        <th>Default eventbus</th>
        <td>No</td>
        <td>Yes</td>
    </th>
</table>
