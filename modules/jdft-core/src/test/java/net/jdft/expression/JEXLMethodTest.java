package net.jdft.expression;

import junit.framework.TestCase;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

public class JEXLMethodTest extends TestCase {
	public void test() {
		JexlEngine jexl = new JexlEngine();
		String jexlExp = "foo.getName()";
        Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("foo", new JEXLMethodTest() );

        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
        
        
	}
	
	public void testArg() {
		JexlEngine jexl = new JexlEngine();
		String jexlExp = "foo.getAge(2)";
        Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("foo", new JEXLMethodTest() );

        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
        
        
	}
	
	public String getName(){
		return "hong";
	}
	
	public int getAge(int age) {
		return age;
	}
}
