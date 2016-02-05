package com.chris.java8.lambdas;

import org.junit.Test;

public class LambdaTest {
	
	@Test
	public void testMethodWithLambdaAsArgument() {
		process(() -> System.out.println("Hello Lambda"));
	}
	
	private void process(Runnable r) {
		r.run();
	}

}
