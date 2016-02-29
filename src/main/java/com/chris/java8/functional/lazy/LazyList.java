package com.chris.java8.functional.lazy;

import java.util.function.Supplier;

public class LazyList<T> implements MyList<T>{
	private final T head;
	private final Supplier<MyList<T>> tail;

	public LazyList(T head, Supplier<MyList<T>> tail) {
		this.head = head;
		this.tail = tail;
	}

	@Override
	public T head() { return head; }

	@Override
	public MyList<T> tail() { return tail.get(); }
}

/* Other types of Lists */
class MyLinkedList<T> implements MyList<T> {
	private final T head;
	private final MyList<T> tail;
	
	public MyLinkedList(T head, MyList<T> tail) {
		this.head = head;
		this.tail = tail;
	}

	@Override
	public T head() {return head;}

	@Override
	public MyList<T> tail() {return tail;}
	
	@Override
	public boolean isEmpty() { return false;}
}

class Emty<T> implements MyList<T>{
	@Override
	public T head() { throw new UnsupportedOperationException(); }
	@Override
	public MyList<T> tail() { throw new UnsupportedOperationException(); } 
}

