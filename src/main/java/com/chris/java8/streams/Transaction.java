package com.chris.java8.streams;

import java.util.Arrays;
import java.util.List;

public class Transaction {
	
	static Trader raoul = new Trader("Raoul", "Cambridge");
	static Trader mario = new Trader("Mario", "Milan");
	static Trader alan = new Trader("Alan", "Cambridge");
	static Trader brian = new Trader("Brian", "Cambridge");
	
	public static final List<Transaction> TRANSACTIONS = Arrays.asList(new Transaction(brian, 2011, 300),
			new Transaction(raoul, 2012, 1000), new Transaction(raoul, 2011, 400), new Transaction(mario, 2012, 710),
			new Transaction(mario, 2012, 700), new Transaction(alan, 2012, 950));

	private final Trader trader;
	private final int year;
	private final int value;

	public Transaction(Trader trader, int year, int value) {
		super();
		this.trader = trader;
		this.year = year;
		this.value = value;
	}

	public Trader getTrader() {
		return trader;
	}

	public int getYear() {
		return year;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Transactions [trader=" + trader + ", year=" + year + ", value=" + value + "]";
	}

}
