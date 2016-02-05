package com.chris.java8.streams;

import java.util.stream.Stream;

public class IterateFibonacci {
	
	public static void main(String[] args) {
		Stream.iterate(new int[] {0, 1}, ai -> new int[] {ai[1], ai[0] + ai[1]})
		.limit(20)
		.forEach(fa -> System.out.println(String.format("[%d , %d]", fa[0], fa[1])));;
	}

}
