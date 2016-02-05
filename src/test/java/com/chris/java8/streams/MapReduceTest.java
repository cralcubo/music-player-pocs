package com.chris.java8.streams;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class MapReduceTest {
	
	@Test
	public void testCountNumberOfDishes() {
		List<Dish> menu = Dish.MENU;
		// Convert the dishes to 1s and add them up
		Integer counter = menu.stream().map(d -> 1).reduce(0, (a,b) -> a + b);
		
		assertEquals(menu.size(), counter.intValue());
	}

}
