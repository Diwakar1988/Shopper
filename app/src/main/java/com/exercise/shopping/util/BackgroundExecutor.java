package com.exercise.shopping.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundExecutor {
	private static final int MAX_THREADS = 3;
	private static BackgroundExecutor instance;
	private ExecutorService service;

	private BackgroundExecutor() {
		service = Executors.newFixedThreadPool(MAX_THREADS);
	}

	public static BackgroundExecutor getInstance() {
		if (instance == null) {
			instance = new BackgroundExecutor();
		}
		return instance;
	}

	public void execute(Runnable runnable) {
		service.submit(runnable);
	}

	public void stop() {
		service.shutdownNow();
	}
}
