package com.chris.java8.streams;

import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;

import org.junit.Test;

import com.chris.java8.streams.Dish.Type;

public class GroupingTest {
	private static final List<Dish> menu = Dish.MENU;
	
	@Test
	public void testSimpleGrouping() {
		// Group by type
		Map<Type, List<Dish>> types = menu.stream().collect(groupingBy(d -> d.getType()));
		System.out.println("TYPES=" + types);
	}
	
	enum FatType {LIGHT, NORMAL, FAT}
	
	@Test
	public void testSubGrouping() {
		Map<Type, Map<FatType, List<Dish>>> superGroup = menu.stream()
				.collect(groupingBy(Dish::getType, groupingBy(d -> {
					if (d.getCalories() <= 400) return FatType.LIGHT;
					else if (d.getCalories() > 400 && d.getCalories() >= 700) return FatType.NORMAL;
					else return FatType.FAT;
				})));
		
		System.out.println(superGroup);
	}
	
	@Test
	public void testMapping() {
		Map<Type, List<String>> types = menu.stream()
				.collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
		System.out.println(".:. " + types);
	}

}
