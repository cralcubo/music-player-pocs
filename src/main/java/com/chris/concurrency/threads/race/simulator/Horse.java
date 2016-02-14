package com.chris.concurrency.threads.race.simulator;

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
		long duration = Math.abs(rnd.nextLong() % 40) + 20;
		Thread.sleep(duration);
		return duration;
	}

	@Override
	public void run() {
		try {
			race.getReadyToRace();
			System.out.format("%s is off and running!\n", name);
			// three laps
			for (int i = 1; i <= 3; i++) {
				long time = runLap();
				System.out.format("%s completes lap %d in %d\n", name, i, time);
				// pit stop
				time = trough.getDrink();
				System.out.format("%s drinks for %d\n", name, time);
			}
			
			int place = race.crossFinishLine();
			System.out.format(">>> %s finishes in position %s! <<<\n", name, place );
			
		} catch (Exception e) {
			System.out.format("%s broke a leg :(\n",name);
		}

	}

}
