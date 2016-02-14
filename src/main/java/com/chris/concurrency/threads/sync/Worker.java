package com.chris.concurrency.threads.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {

	private List<Integer> l1 = new ArrayList<>();
	private List<Integer> l2 = new ArrayList<>();

	private Random rnd = new Random();

	// Locks
	Object lock1 = new Object();
	Object lock2 = new Object();

	public static void main(String[] args) throws InterruptedException {
		Worker w = new Worker();
		System.out.println("Running...");
		w.run();
	}

	private void run() throws InterruptedException {
		long start = System.currentTimeMillis();
		// 2 Threads to run
		Thread t1 = new Thread(() -> process());
		Thread t2 = new Thread(() -> process());

		t1.start();
		t2.start();

		// Wait
		t1.join();
		t2.join();

		long end = System.currentTimeMillis();

		System.out.println("Total Time=" + (end - start));
		System.out.format("L1=%d | L2=%d", l1.size(), l2.size());
	}

	private void process() {
		for (int i = 0; i < 1000; i++) {
			try {
				stage1();
				stage2();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void stage2() throws InterruptedException {
		synchronized (lock1) {
			Thread.sleep(1);
			l2.add(rnd.nextInt());
		}
	}

	private void stage1() throws InterruptedException {
		synchronized (lock2) {
			Thread.sleep(1);
			l1.add(rnd.nextInt());
		}
	}

}
