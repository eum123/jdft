package net.jdft.util;

import org.jdom.IllegalDataException;

public class NullCheckUtil {
	
	/**
	 * obj 값이 null이면 IllegalDataException을 throw 한다.
	 * @param obj null인가를 확인할 object
	 * @param message Exception에 추가할 오류 메시지
	 */
	public static void isNullThrowException(Object obj, String message) {
		if(obj == null) {
			throw new IllegalDataException(message);
		}
	}
	
	
}
