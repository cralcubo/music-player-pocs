package com.chris.java8.optional;

import java.util.Optional;

public class Person {
	private final Optional<Car> car;
	
	public Person(Optional<Car> car) {
		this.car = car;
	}
	
	public Optional<Car> getCar() {
		return car;
	}
}

class Car {
	private final Optional<Insurance> insurance;
	
	public Car(Optional<Insurance> insurance) {
		this.insurance = insurance;
	}
	
	public Optional<Insurance> getInsurance() {
		return insurance;
	}
}

class Insurance {
	private final String name;
	
	public Insurance(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
