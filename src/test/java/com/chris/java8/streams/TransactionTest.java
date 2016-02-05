package com.chris.java8.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

import org.junit.Test;

public class TransactionTest {
	
	private final List<Transaction> transactions = Transaction.TRANSACTIONS; 
	
	/**
	 * 1 Find all transactions in the year 2011 and sort them by value (small to high).
	 */
	@Test
	public void testFindTransAndSort() {
		List<Transaction> sortedTransactions = transactions.stream()
				.filter(t -> t.getYear() == 2011)
				.sorted(Comparator.comparing(Transaction::getValue))
				.collect(toList());
		System.out.println("1 Find all transactions in the year 2011 and sort them by value (small to high):");
		printList(sortedTransactions);
	}
	
	/**
	 * 2 What are all the unique cities where the traders work?
	 */
	@Test
	public void testFindUniqueCities() {
		List<String> uniqueCities = transactions.stream()
				.map(t -> t.getTrader().getCity())
				.distinct()
				.collect(toList());
		
		System.out.println("2 What are all the unique cities where the traders work?");
		printList(uniqueCities);
	}
	
	/**
	 * 3 Find all traders from Cambridge and sort them by name.
	 */
	@Test
	public void testFindTraders() {
		List<Trader> traders = transactions.stream()
				.map(Transaction::getTrader)
				.filter(t -> t.getCity().equals("Cambridge"))
				.sorted(Comparator.comparing(Trader::getName))
				.collect(toList());
		System.out.println("3 Find all traders from Cambridge and sort them by name.");
		printList(traders);
	}
	
	/**
	 * 4 Return a string of all traders’ names sorted alphabetically.
	 */
	@Test
	public void testTradersNames() {
		String bigName = transactions.stream()
				.map(t -> t.getTrader().getName())
				.sorted()
				.reduce("", (s1, s2) -> s1 + s2);
		
		System.out.println("4 Return a string of all traders’ names sorted alphabetically.");
		System.out.println(bigName);
	}
	
	/**
	 * 5 Are any traders based in Milan?
	 */
	@Test
	public void testTradersInMilan()
	{
		Optional<Trader> traderInMilan = transactions.stream()
				.map(Transaction::getTrader)
				.filter(t -> t.getCity().equals("Milan"))
				.findAny();
		System.out.println("5 Are any traders based in Milan?");
		traderInMilan.ifPresent(System.out::println);
	}
	
	/**
	 * 6 Print all transactions’ values from the traders living in Cambridge.
	 */
	@Test
	public void testTransactionsCambridge() {
		List<Transaction> transCamb = transactions.stream()
				.filter(tr -> tr.getTrader().getCity().equals("Cambridge"))
				.collect(toList());
		System.out.println("6 Print all transactions’ values from the traders living in Cambridge.");
		printList(transCamb);
	}
	/**
	 * 7 What’s the highest value of all the transactions?
	 */
	@Test
	public void testHighestTransaction() {
		Optional<Integer> maxVal = transactions.stream()
				.map(Transaction::getValue)
				.reduce(Integer::max);
		
		System.out.println("7 What’s the highest value of all the transactions?");
		maxVal.ifPresent(System.out::println);
	}
	
	/**
	 * 8 Find the transaction with the smallest value.
	 */
	@Test
	public void testLowestTransVal() {
		Optional<Integer> minVal = transactions.stream()
				.map(Transaction::getValue)
				.reduce(Integer::min);
		
		System.out.println("8 Find the transaction with the smallest value.");
		minVal.ifPresent(System.out::println);
	}
	
	private void printList(List<?> objects)
	{
		objects.forEach(System.out::println);
	}

}
