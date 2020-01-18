package sajo.study.common.core.util;

import lombok.Data;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Data
public class StompResult<T> {
	private final CountDownLatch latch;
	private T result;

	public StompResult() {
		latch = new CountDownLatch(1);
	}

	public void countDown(T result) {
		this.result = result;
		latch.countDown();
	}

	public void await() throws InterruptedException {
		latch.await();
	}

	public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
		return latch.await(timeout, unit);
	}


}
