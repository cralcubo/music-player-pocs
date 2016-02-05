package com.chris.java8.streams;

import static java.util.stream.Collectors.*;

import java.util.IntSummaryStatistics;
import java.util.List;

import org.junit.Test;

public class CollectorsTest {
	
	private final static List<Dish> menu = Dish.MENU;
	
	@Test
	public void testGeneralCollector () {
		// Find the sum of all the calories of dishes
		int totalCal = menu.stream().collect(summingInt(d -> d.getCalories()));
		// Find average cals
		double avCals = menu.stream().collect(averagingInt(Dish::getCalories));
		// Sumarizing
		IntSummaryStatistics menuStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
		System.out.printf("Total Cals=%d | Average cals=%.2f \n", totalCal, avCals);
		System.out.println("Statistics=" + menuStatistics);
	}
	
	@Test
	public void testUsingReduce() {
//		int totalCals = menu.stream().collect(reducing((d1, d2) -> d1.getCalories() + d2.getCalories()));
	}

}
