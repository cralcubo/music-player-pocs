package com.chris.java8.interfaces;

public interface DefaultInterface {
	int aNumber = 17;

	default int addOne(int n) {
		return n + 1;
	}

	default int getANumber() {
		return aNumber;
	}

}
