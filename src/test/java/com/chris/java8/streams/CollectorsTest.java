package com.chris.java8.streams;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;

public class CollectorsTest {
	private static final List<Dish> menu = Dish.MENU;

	@Test
	public void testSumarizations() {
		// Find the Average calories of the menu
		IntSummaryStatistics iss = menu.stream().collect(java.util.stream.Collectors.summarizingInt(Dish::getCalories));
		System.out.println(iss);

		// Find the dish with most calories
		// Using reduce:
		Optional<Integer> maxCals = menu.stream().map(Dish::getCalories).reduce(Integer::max);
		maxCals.ifPresent(c -> System.out.println("Max Cals=" + c));
		// Using collect
		Optional<Dish> maxCalDish = menu.stream().collect(Collectors.maxBy(Comparator.comparing(Dish::getCalories)));
		maxCalDish.ifPresent(md -> System.out.println("Max Cal Dish=" + md));
		// Using collect and reducing
		menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (d1, d2) -> d1 + d2));
	}

}
