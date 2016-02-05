package com.chris.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class FlatMapTest {
	
	@Test
	public void testFlatingStreams() {
		List<String> strings = Arrays.asList("Hello", "World");
		List<String> words = strings.stream()
				.map(s -> s.split(""))             // returns Stream<String[]>
				.flatMap(as -> Arrays.stream(as)) // Map returns Stream<Stream<String>> | FlatMap returns Stream<String> 
				.collect(Collectors.toList());
		
		System.out.println("Words: ");
		System.out.println(words);
	}
	
	@Test
	public void testCreatePairNumbers() {
		List<Integer> a = Arrays.asList(1,2,3);
		List<Integer> b = Arrays.asList(3,4);
		List<List<Integer>> pairs = a.stream()
				.flatMap(x -> 
								b.stream()
								 .filter(y -> (x + y) % 3 == 0)
								 .map(y -> Arrays.asList(x,y)))
				.collect(Collectors.toList());
		
		System.out.println("Pairs divisible by 3:");
		pairs.forEach(System.out::println);
	}

}
