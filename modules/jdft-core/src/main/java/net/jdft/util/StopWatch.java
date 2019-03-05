package net.jdft.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 시간 측정 Utility
 * @author manjin
 *
 */
public class StopWatch {
	
	private AtomicLong time = new AtomicLong(0);
	
	/**
	 * 측정 시작
	 */
	public void start() {
		time.set(System.currentTimeMillis());
	}
	
	/**
	 * 현재까지 측정된 시간을 반환하고 시작시간을 초기화 하지 않는다.
	 * @return
	 */
	public long split() {
		return  System.currentTimeMillis() - time.get();
	}
	
	/**
	 * 현재까지 측정된 시간을 반환하고 시작시간을 초기화 한다.
	 * @return
	 */
	public long stop() {
		{
			long interval = System.currentTimeMillis() - time.get();
			time.set(0);
			return interval;
		}
	}
}
