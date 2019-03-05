package net.jdft.function.sequence;

import org.junit.Test;

public class IntSequenceTest {
	@Test
	public void test() {
		Sequence seq = new IntSequenceGenerator();
		System.out.println(seq.nextValue(10, 10));
		System.out.println(seq.nextValue(10, 10));
	}
}
