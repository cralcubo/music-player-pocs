package com.chris.java8.lambdas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@FunctionalInterface
interface Processable {
	String process(BufferedReader br) throws IOException;
}

public class FileProcessor_Lambda {
	private static final String PATH = "src/main/resources/test.txt";

	public static void main(String[] args) throws IOException {
		String oneLine = processFile(new File(PATH), (BufferedReader br) -> br.readLine());
		System.out.println(oneLine);
		
		String onLine_methodRef = processFile(new File(PATH), BufferedReader::readLine);
		System.out.println(onLine_methodRef);

		String twoLines = processFile(new File(PATH), br -> br.readLine() + br.readLine());
		System.out.println(twoLines);
		
	}

	public static String processFile(File f, Processable p) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			return p.process(br);
		}
	}
}
