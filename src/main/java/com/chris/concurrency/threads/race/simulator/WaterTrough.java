package com.chris.concurrency.threads.race.simulator;

import java.util.Random;

public class WaterTrough {
	private Random rnd = new Random(System.currentTimeMillis());
	
	public long getDrink() throws InterruptedException {
		long duration = Math.abs(rnd.nextLong() % 20) + 10;
		Thread.sleep(duration);
		
		return duration ;
	}

}
