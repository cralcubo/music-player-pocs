package com.chris.java8.interfaces;

public interface StaticInterface {
	static int MAGIC_NUMBER = 17;

	static int getMagicNumber() {
		return 42;
	}
	
	static double getRandomNumber(){
		return Math.random() * 100;
	}
}
