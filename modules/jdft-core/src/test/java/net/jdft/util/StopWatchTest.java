package net.jdft.util;

import org.junit.Test;

public class StopWatchTest {
	private StopWatch stopWatch  = new StopWatch();
	@Test
	public void test() throws InterruptedException {
		
		stopWatch.start();
		Thread.sleep(1000);
		System.out.println(stopWatch.stop());
		
		stopWatch.start();
		Thread.sleep(1000);
		System.out.println(stopWatch.stop());
	}
	
	
}
