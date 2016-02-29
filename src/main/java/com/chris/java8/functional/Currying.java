package com.chris.java8.functional;

import java.util.function.DoubleUnaryOperator;

public class Currying {
	
	public static void main(String[] args) {
		DoubleUnaryOperator celsiusToFarenheit = curriedConverter(9.0/5, 32);
		DoubleUnaryOperator usdToGbp = curriedConverter(0.6, 0);
		DoubleUnaryOperator kmToMil = curriedConverter(0.6214, 0);
		
		System.out.println("100C to F is=" + celsiusToFarenheit.applyAsDouble(100.0));
		System.out.println("10 USD to GBP is=" + usdToGbp.applyAsDouble(10.0));
		System.out.println("21 Km to Mill is=" + kmToMil.applyAsDouble(21.0));
	}
	
	
	private static DoubleUnaryOperator curriedConverter(double f, double b){
		return (double x) -> x * f + b;
	}
	

}
