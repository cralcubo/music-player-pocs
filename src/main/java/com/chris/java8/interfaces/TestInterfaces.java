package com.chris.java8.interfaces;

public class TestInterfaces implements StaticInterface, DefaultInterface {
	
	public static void main(String[] args) {
		TestInterfaces ti = new TestInterfaces();
		ti.test();
	}

	private void test() {
		System.out.println("Static property=" + StaticInterface.MAGIC_NUMBER);
		System.out.println("Static method=" + StaticInterface.getMagicNumber());
		System.out.println("Static random=" + StaticInterface.getRandomNumber());
		
		System.out.println("Inheriting default methodA=" + getANumber());
		System.out.println("Inheritance default 10 + 1=" + addOne(10) );
	}

}
