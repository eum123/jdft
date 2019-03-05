package net.jdft.expression;

import org.junit.Test;

public class JEXLExpressionTest {
	@Test
	public void sequenceFunction() {
		JEXLExpression jexl = new JEXLExpression();
		
		System.out.println(jexl.execute(null, "sequence:nextValue(5, 5)"));
		System.out.println(jexl.execute(null, "sequence:nextValue(5, 5)"));
	}
}
