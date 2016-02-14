package com.chris.concurrency.threads.sync;

import java.util.stream.IntStream;

public class SyncTest {
	
	private volatile int counter;
	
	public static void main(String[] args) throws InterruptedException {
		SyncTest st = new SyncTest();
		st.run();
	}

	private void run() throws InterruptedException {
		Thread t1 = new Thread(() -> IntStream.rangeClosed(1, 10000).forEach(i -> increment()));
		Thread t2 = new Thread(() -> IntStream.rangeClosed(1, 10000).forEach(i -> increment()));
		
		// Start the threads
		t1.start();
		t2.start();
		
		// Print the counter, expected 0 because we are not 
		// waiting for t1 and t2 to complete their job.
		System.out.format("Result=%d\n",counter);
		
		// Wait for all the streams to be completed.
		t1.join();
		t2.join();
		// Print counter
		System.out.format("Result=%d\n",counter);
	}
	
	private void increment() {
		counter++;
	}

}
