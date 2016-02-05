package com.chris.java8.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class FilesStream {
	
	
	private static final String SOURCE = "src/main/resources/test.txt";

	public static void main(String[] args) {
		// Print all the distinct letters of a text
		try {
			Files.lines(Paths.get(SOURCE))
			.flatMap(l -> Arrays.stream(l.split("")))
			.map(String::toUpperCase)
			.distinct()
			.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
