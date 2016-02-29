package com.chris.java8.functional.lazy;

public class TestLists {
	
	public static void main(String[] args) {
		TestLists tl = new TestLists();
		tl.test();
	}

	private void test() {
		// Normal List
		MyLinkedList<Integer> mllTail = new MyLinkedList<>(new Integer(11), new Emty<Integer>());
		MyLinkedList<Integer> mll = new MyLinkedList<>(new Integer(10), mllTail);
		System.out.println("Normal List");
		workList(mll);
		
		// Lazy List
		LazyList<Integer> ll = new LazyList<Integer>(new Integer(15), () -> mllTail );
		System.out.println("Lazy List");
		workList(ll);
		
		// Infinite List
		LazyList<Integer> il = from(5);
		System.out.println("Infinite Lazy List");
		System.out.printf("%d | %d | %d", il.head(), il.tail().head(), il.tail().tail().head());
	}

	private void workList(MyList<Integer> list) {
		System.out.printf("%d|",list.head());
		if(!(list.tail() instanceof Emty)) workList(list.tail());
	}
	
	private LazyList<Integer> from(int n) {
		return new LazyList<Integer>(n, () -> from(n + 1));
	}

}
