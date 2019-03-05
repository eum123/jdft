package net.jdft.fixedlength.expression;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.jdft.fixedlength.expression.UserClass;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;
import org.apache.log4j.BasicConfigurator;

public class JEXLTest extends TestCase {
	
	static {
		BasicConfigurator.configure();
	}
	private static final JexlEngine jexl = new JexlEngine();
	static {
        jexl.setCache(512);
        jexl.setLenient(false);
        jexl.setSilent(false);
     }
	
	public void testOperation() {
		String jexlExp = "1 + 2";
		Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
   

        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	
	public void testReferenceOperation() {
		String jexlExp = "A + 2";
		Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("A", 1 );

        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	
	public void testSize() {
		String jexlExp = "A.value.size()";
		Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("A.value", "111" );

        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	public void testKoreaSize() throws UnsupportedEncodingException {
		String jexlExp = "A.size()";
		Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("A", "함글".getBytes("EUC-KR") );

        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	
	public void testKoreaBytesSize() throws UnsupportedEncodingException {
		String jexlExp = "A.getBytes('EUC-KR').size()";
		Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("A", "함글" );

        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	
	public void testBytesSize() {
		String jexlExp = "A.size()";
		Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("A", "1111".getBytes() );
        
        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	
	public void testSubstring() {
		String jexlExp = "A.substring(1)";
		Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("A", "1111" );
        
        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	
	public void testjexl() {
		String jexlExp = "A";
		Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("A", "1111" );
        
        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	
	public void testnot() {
		String jexlExp = "$A";
		Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("$A", "1111" );
        
        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	
//	public void testString() {
//		String jexlExp = "A | '22'";
//		Expression e = jexl.createExpression( jexlExp );
//
//        // Create a context and add data
//        JexlContext jc = new MapContext();
//        jc.set("A", "1111".getBytes() );
//
//        // Now evaluate the expression, getting the result
//        System.out.println(e.evaluate(jc));
//	}
	
	public void testCurrentTime() {
		String jexlExp = "System.currentTimeMillis()";
		Expression e = jexl.createExpression( jexlExp );
		
        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("System", java.lang.System.class);
        
        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	
	public void testParseIntString() {
		String jexlExp = "Integer.parseInt(A) + '22'";
		Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("A", "1111" );
        jc.set("Integer", java.lang.Integer.class );

        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	
	public void testFormat() {
		
		System.out.println(String.format("%05d", 1));
		
		String jexlExp = "String.format('%05d', A)";
		Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("A", 1 );
        jc.set("String", java.lang.String.class );
        
        

        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	
	public void testUserMethod() {
		
		Map functionsMap = new HashMap();
		functionsMap.put("my", new UserClass());
		
		jexl.setFunctions(functionsMap);
		
		String jexlExp = "my:merge(A, '22')";
		Expression e = jexl.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = new MapContext();
        jc.set("A", "1111" );
       
        
        

        // Now evaluate the expression, getting the result
        System.out.println(e.evaluate(jc));
	}
	
	
	
	 
}


