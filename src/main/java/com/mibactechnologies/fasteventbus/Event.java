package com.mibactechnologies.fasteventbus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Event {
    private final String name;

    protected Event() {
	this(null);
    }

    protected Event(final String name) {
	this.name = name;
    }

    @Override
    public boolean equals(final Object obj) {
	if (obj == this)
	    return true;

	if (!(obj instanceof Event))
	    return false;

	if (((Event) obj).toString() == toString())
	    return true;

	return false;
    }

    /**
     * Convenience method for providing a user-friendly identifier. By default,
     * it is the event's class's {@linkplain Class#getSimpleName() simple name}.
     *
     * @return name of this event
     */
    public String getEventName() {
	return name == null ? getClass().getSimpleName() : name;
    }

    @Override
    public String toString() {
	final List<Field> fields = new ArrayList<Field>();

	Class<?> current = this.getClass();
	while (current.getSuperclass() != null) {
	    fields.addAll(Arrays.asList(current.getDeclaredFields()));
	    current = current.getSuperclass();
	}

	final StringBuilder result = new StringBuilder();
	System.getProperty("line.separator");

	result.append(this.getClass().getName());
	result.append("{");

	for (int i = 0; i < fields.size(); i++) {
	    final Field f = fields.get(i);

	    f.setAccessible(true);

	    final String name = f.getName();

	    result.append(name);
	    result.append("=");

	    if (name.equals("name")) {
		result.append("\"" + getEventName() + "\"");
		continue;
	    }

	    try {
		String s = null;

		if (f.getType() == String.class)
		    s = "\"" + f.get(this) + "\"";
		if (f.getType() == char.class)
		    s = "'" + f.get(this) + "'";

		result.append(s == null ? f.get(this) : s);
	    } catch (final Exception e) {
		e.printStackTrace();
	    }

	    if (i + 1 != fields.size())
		result.append(", ");

	    // f.setAccessible(accessible); Not required due to
	    // http://stackoverflow.com/a/10638943/4389344
	}

	result.append("}");

	return result.toString();
    }
}
