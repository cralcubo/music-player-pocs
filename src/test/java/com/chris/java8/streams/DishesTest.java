package com.chris.java8.streams;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class DishesTest {
	private List<Dish> menu = Dish.MENU;

	@Test
	public void testGetVegetarianDishes() {
		List<Dish> vegetarianMenu = menu.stream()
				.filter(Dish::isVegetarian)
				.collect(Collectors.toList());
		System.out.println("Vegeterian Dishes:");
		vegetarianMenu.forEach(System.out::println);
	}
	
	@Test
	public void testFindingTwoLowCaloriesDishes() {
		List<Dish> lowCalDishes = menu.stream()
				.filter(d -> d.getCalories() <= 500)
				.limit(2)
				.collect(Collectors.toList());
		System.out.println("LowCal Dishes:");
		lowCalDishes.forEach(System.out::println);
	}
	
	@Test
	public void testFindTwoMeatDishes() {
		List<Dish> meatDishes = menu.stream()
				.filter(d -> d.getType() == Dish.Type.MEAT)
				.sorted(Comparator.comparing(Dish::getName))
				.limit(2)
				.collect(Collectors.toList());
		
		System.out.println("Meat Dishes:");
		meatDishes.forEach(System.out::println);
	}

}
