package com.chris.concurrency.threads.race.simulator;

public class HorceRaceMain {
	
	public static void main(String[] args) throws InterruptedException {
		Race race = new Race();
		WaterTrough trough = new WaterTrough();
		
		// Five horses
		new Thread(new Horse("Uno", race, trough)).start();
		new Thread(new Horse("Dos", race, trough)).start();
		new Thread(new Horse("Tres", race, trough)).start();
		new Thread(new Horse("Cuatro", race, trough)).start();
		new Thread(new Horse("Cinco", race, trough)).start();
		
		System.out.println("Get ready...");
		Thread.sleep(200);
		System.out.println("GO!!!");
		race.startRace();
	}
	

}
