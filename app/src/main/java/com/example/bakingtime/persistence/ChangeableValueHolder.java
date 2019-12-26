package com.example.bakingtime.persistence;

public interface ChangeableValueHolder<V> {

	V getValue();

	void setValue(V value);

}
