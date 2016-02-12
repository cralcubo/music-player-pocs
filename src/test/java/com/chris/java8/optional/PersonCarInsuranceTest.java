package com.chris.java8.optional;

import static org.junit.Assert.*;

import java.util.Optional;
import java.util.Properties;

import org.junit.Test;

public class PersonCarInsuranceTest {
	
	@Test
	public void testOptionalMap() {
		Optional<Insurance> optInsurance = Optional.ofNullable(new Insurance("NLD"));
		//Get the name
		assertEquals("NLD", optInsurance.map(Insurance::getName).get());
	}
	
	@Test
	public void testNullableOptional() {
		Car car = null;
		Person p = new Person(Optional.ofNullable(car));
		
		assertEquals(Optional.empty(), p.getCar());

		// Get the name of the insurance
		Optional<String> name = Optional.of(p)
				.flatMap(Person::getCar)
				.flatMap(Car::getInsurance)
				.map(Insurance::getName);
		assertEquals("Unknown", name.orElse("Unknown"));
	}
	
	@Test
	public void testMapping() {
		Insurance insurance = new Insurance("NLD");
		Car car = new Car(Optional.of(insurance));
		Person p = new Person(Optional.of(car));
		
		// Get the name of the insurance
		Optional<String> name = Optional.of(p)
										.flatMap(Person::getCar)
										.flatMap(Car::getInsurance)
										.map(Insurance::getName);
		assertEquals("NLD", name.orElse("Unknown"));
		
		// No Car
		Person noCarPerson = new Person(Optional.empty());
		
		Optional<String> name2 = Optional.of(noCarPerson)
				.flatMap(Person::getCar)
				.flatMap(Car::getInsurance)
				.map(Insurance::getName);
		
		assertEquals("Unknown", name2.orElse("Unknown"));
		
		// No Insurance
		Car noInsCar = new Car(Optional.empty());
		Person person = new Person(Optional.of(noInsCar));
		
		Optional<String> name3 = Optional.of(person)
				.flatMap(Person::getCar)
				.flatMap(Car::getInsurance)
				.map(Insurance::getName);
		
		assertEquals("Unknown", name3.orElse("Unknown"));
	}
	
	@Test
	public void findCheapestInsuranceTest() {
		Person p = new Person(Optional.empty());
		Car c = new Car(Optional.empty());
		Optional<Insurance> oi = findCheapestInsurance(p, c);
		assertEquals("Cheap_Insurance", oi.map(Insurance::getName).orElse("Unknown"));
		
		// Null Car
		Optional<Insurance> oi2 = findCheapestInsurance(p, null);
		assertEquals("Unknown", oi2.map(Insurance::getName).orElse("Unknown"));
		
		// Null Person
		Optional<Insurance> oi3 = findCheapestInsurance(null, c);
		assertEquals("Unknown", oi3.map(Insurance::getName).orElse("Unknown"));
	}
	
	@Test
	public void testFiltering() {
		Properties props = new Properties();
		props.setProperty("a", "5");
		props.setProperty("b", "true");
		props.setProperty("c", "-3");
		
		assertEquals(5, readDuration(props, "a"));
		assertEquals(0, readDuration(props, "b"));
		assertEquals(0, readDuration(props, "c"));
		assertEquals(0, readDuration(props, "d"));
		
	}
	
	private int readDuration(Properties props, String name) {
		Optional<String> opStrInt = Optional.ofNullable(props.getProperty(name));
		try{
			return opStrInt.map(Integer::parseInt)
					   .filter(i -> i > 0)
					   .orElse(0);
		}
		catch(NumberFormatException e)
		{
			return 0;
		}
	} 
	
	private Optional<Insurance> findCheapestInsurance(Person person, Car car) {
		Optional<Person> op = Optional.ofNullable(person);
		Optional<Car> oc = Optional.ofNullable(car);
		
		return op.flatMap(p -> oc.map(c -> getInsurance(p, c)));
	}
	
	private Insurance getInsurance(Person p , Car c){
		return new Insurance("Cheap_Insurance");
	}

}
