package com.chris.concurrency.threads;

import java.util.Random;

public class Horse implements Runnable {
	private String name;
	private Race race;
	private WaterTrough trough;
	private Random rnd = new Random(System.currentTimeMillis());
	
	public Horse(String name, Race race, WaterTrough trough) {
		super();
		this.name = name;
		this.race = race;
		this.trough = trough;
	}
	
	public long runLap() throws InterruptedException {
		// Horse takes a drink
		long duration = Math.abs(rnd.nextLong()) * 6000;
		Thread.sleep(duration);
		return duration;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	

}
