package com.chris.concurrency.threads.race.simulator;

public class Race {

	private volatile int rank;

	public synchronized void getReadyToRace() throws InterruptedException {
		this.wait();
	}
	
	public synchronized void startRace() {
		this.notifyAll();
	}

	public int crossFinishLine() {
		return ++rank;
	}

}
