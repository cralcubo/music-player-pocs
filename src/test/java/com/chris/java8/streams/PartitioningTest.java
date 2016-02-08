package com.chris.java8.streams;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

public class PartitioningTest {
	
	@Test
	public void testPartitioningByDishType() {
		Map<Boolean, List<Dish>> vegetarians = Dish.MENU.stream().collect(partitioningBy(Dish::isVegetarian));
		System.out.println("Vegetarians");
		vegetarians.forEach((k, v) -> System.out.println(k + ":" + v));
	}
	
	@Test
	public void testPartitioningGrouping() {
		Map<Boolean, Dish> maxVeggy = Dish.MENU.stream()
				.collect(
						partitioningBy(Dish::isVegetarian, 
								collectingAndThen(
										maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));
		
		System.out.println("Max veggy:");
		maxVeggy.forEach((k,v) -> System.out.println(k + ": " + v));
	}

}
