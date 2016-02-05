package com.chris.java8.lambdas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Apple_MethodReference {

	public static void main(String[] args) {
		Apple_MethodReference mr = new Apple_MethodReference();
//		mr.run();
		mr.sortApples();
	}

	public void run() {
		List<Integer> weights = Arrays.asList(10, 25, 33, 44);
		// Similar: map(weights, w -> new Apple(w));
		List<Apple> apples = map(weights, Apple::new);
		apples.forEach(System.out::println);
	}
	
	public void sortApples() {
		List<Apple> apples = map(Arrays.asList(1, 81, 15), Apple::new);
		// Lambdas:
		// apples.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
		// Method Ref:
		apples.sort(Comparator.comparing(Apple::getWeight));
		System.out.println(apples);
	}

	private List<Apple> map(List<Integer> weights, Function<Integer, Apple> f) {
		List<Apple> apples = new ArrayList<>();
		weights.forEach(w -> apples.add(f.apply(w)));
		return apples;
	}

	class Apple {
		private Integer w;

		public Apple(Integer weigth) {
			this.w = weigth;
		}
		
		public Integer getWeight() {
			return w;
		}
		
		@Override
		public String toString() {
			return String.format("Apple[%d]", w.intValue());
		}
	}
}
