package com.chris.java8.functional.lazy;

public interface MyList<T> {
	T head();
	MyList<T> tail();
	
	default boolean isEmpty() {return true;}
}
