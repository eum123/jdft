package net.jdft.expression;

import java.util.HashMap;
import java.util.Map;

import net.jdft.function.date.DateFormat;
import net.jdft.function.sequence.IntSequenceGenerator;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

/**
 * JEXL을 이용하여 수식 처리<br>
 * <br>
 * 주의 사항 : 참조 이름 size 사용불가
 * 
 */
public class JEXLExpression {
	private static final JexlEngine jexl = new JexlEngine();
	static {
		jexl.setCache(512);
		jexl.setLenient(false);
		jexl.setSilent(false);

		Map functionsMap = new HashMap();
		functionsMap.put("sequence", new IntSequenceGenerator());
		functionsMap.put("dateFormat", new DateFormat());

		jexl.setFunctions(functionsMap);
	}

	public static Object execute(Map map, String exl) {

		// 예약 문자를 삭제
		Expression e = jexl.createExpression(exl.replaceAll("\\$", ""));

		// Create a context and add data
		JexlContext jc = new MapContext(map);
		jc.set("System", java.lang.System.class);
		jc.set("Integer", java.lang.Integer.class);
		jc.set("Long", java.lang.Long.class);
		jc.set("String", java.lang.String.class);
		jc.set("List", java.util.List.class);
		jc.set("Map", java.util.Map.class);
		jc.set("BigDecimal", java.math.BigDecimal.class);
		
		return e.evaluate(jc);
	}
}
