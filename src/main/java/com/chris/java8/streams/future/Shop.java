package com.chris.java8.streams.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Shop {
	private Random random = new Random();
	
	private final String name;
	
	public Shop(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public double getPrice(String product) {
		System.out.println(".:. calculating price");
		return calculatePrice(product);
	}

	public Future<Double> getPriceAsyncOld(final String product) {
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		new Thread(() -> {
			try {
				double price = calculatePrice(product);
				futurePrice.complete(price);
			} catch (Exception e) {
				futurePrice.completeExceptionally(e);
			}
		}).start();

		return futurePrice;
	}
	
	public Future<Double> getPriceAsync(final String product) {
		return CompletableFuture.supplyAsync(() -> calculatePrice(product));
	}

	public void delay() {
		try {
			Thread.sleep(random.nextInt(500) + 500);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String toString() {
		return "Shop [name=" + name + "]";
	}

	private double calculatePrice(String product) {
		delay();
		return random.nextDouble() * product.charAt(0) + product.charAt(1);
	}

	/** test it **/
	public static void main(String[] args) {
		Shop shop = new Shop("Test Shop");
		long start = System.nanoTime();
		Future<Double> futurePrice = shop.getPriceAsync("Canon Camera");
		long invocationTime = (System.nanoTime() - start) / 1_000_000;
		System.out.printf("Invocation returned after %s ms.%n", invocationTime);
		
		// Meanwhile the other thread is still calculating, do something else.
		doSomethingElse();
		// Now get the price
		try {
			System.out.println("\nGet the calculated price:");
			System.out.printf("Price is %.2f%n", futurePrice.get());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		long retrievalTime = (System.nanoTime() - start) / 1_000_000;
		System.out.printf("Price returned after %s ms.%n", retrievalTime);

	}

	private static void doSomethingElse() {
		IntStream.rangeClosed(1, 10).forEach(x -> {
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			System.err.print(".");
		});
	}

}
