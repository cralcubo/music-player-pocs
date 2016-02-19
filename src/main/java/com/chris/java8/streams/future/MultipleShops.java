package com.chris.java8.streams.future;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultipleShops {
	
	private static final List<Shop> SHOPS = Arrays.asList(new Shop("S1"), new Shop("S2"), new Shop("slow"), new Shop("S4"), new Shop("S5"));
	
	public static void main(String[] args) {
		String prodName = "Canon Camera";
		MultipleShops multipleShops = new MultipleShops();
		
		System.out.println(".:.Testing CompletableFuture:");
		long start = System.currentTimeMillis();
		System.out.printf("Prices found for %s in the shops are: %s%n", prodName, multipleShops.findPricesAsync(SHOPS, prodName));
		System.out.printf(">>>Total time=%d ms.<<<%n", (System.currentTimeMillis() - start));
		
//		System.out.println(".:.Testing Parallel Streams:");
//		start = System.currentTimeMillis();
//		System.out.printf("Prices found for %s in the shops are: %s%n", prodName, multipleShops.findPricesParalell(SHOPS, prodName));
//		System.out.printf(">>>Total time=%d ms.<<<%n", (System.currentTimeMillis() - start));
//		
//		System.out.println(".:.Testing Sequential Stream:");
//		start = System.currentTimeMillis();
//		System.out.printf("Prices found for %s in the shops are: %s%n", prodName, multipleShops.findPrices(SHOPS, prodName));
//		System.out.printf(">>>Total time=%d ms.<<<%n", (System.currentTimeMillis() - start));
		
		System.out.println(".:. Prices Relative:");
		start = System.currentTimeMillis();
		System.out.printf("Prices found for %s in the shops are: %s%n", prodName, multipleShops.findPricesReactive(SHOPS, prodName));
		System.out.printf(">>>Total time=%d ms.<<<", (System.currentTimeMillis() - start));
	}
	
	public List<String> findPricesAsync(List<Shop> shops, String product) {
		final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), (Runnable r) -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		});
		
		List<CompletableFuture<String>> priceFures = shops.stream()
				.map(s -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", s.getName(), s.getPrice(product)), executor))
				.collect(Collectors.toList());

		return priceFures.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList());
	}
	
	public List<String> findPricesParalell(List<Shop> shops, String product) {
		return shops.parallelStream()
				.map(s -> String.format("%s price is %.2f", s.getName(), s.getPrice(product)))
				.collect(Collectors.toList());
	}
	
	public List<String> findPrices(List<Shop> shops, String product) {
		return shops.stream()
				.map(s -> String.format("%s price is %.2f", s.getName(), s.getPrice(product)))
				.collect(Collectors.toList());
	}
	
	public List<String> findPricesReactive(List<Shop> shops, String product) {
		final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100), (Runnable r) -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		});
		
		Stream<CompletableFuture<String>> futurePrices = shops.stream()
					.map(s -> CompletableFuture.supplyAsync(() -> String.format("%s price is %.2f", s.getName(), s.getPrice(product), executor)));
		
		try {
			System.out.println(".:. Waiting.....");
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Register an action with thenAccept()
		CompletableFuture[] futures = futurePrices.map(cf -> cf.thenAccept(System.out::println))
				.toArray(size -> new CompletableFuture[size]);
		
		CompletableFuture.allOf(futures).join();
		
		return new ArrayList<>();
	}

}
