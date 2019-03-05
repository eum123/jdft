package net.jdft.xml.util;

import java.util.Map;

import org.jdom.Element;
import org.jdom.Namespace;

public class XmlHelper {
	
	/**
	 * xpath에 맞게 element를 생성
	 * @param name
	 * @param namespaces
	 * @return
	 */
	public static Element createElement(String name, Map<String, Namespace> namespaces) {
		if (name.indexOf(":") > 0) {
			String prefix = name.substring(0, name.indexOf(":"));
			
			String elementName = name.substring(name.indexOf(":") + 1);
						
			return new Element(elementName, namespaces.get(prefix));
		} else {
			return new Element(name);
		}
	}
	
	/**
	 * xpath에 맞게 element를 생성하고 값 추가
	 * @param name
	 * @param namespaces
	 * @param value
	 * @return
	 */
	public static Element createElement(String name, Map<String, Namespace> namespaces, String value) {
		// element 인 경우
		return XmlHelper.createElement(name,  namespaces).setText(value);
	}
	
	/**
	 * element이름과 비교.
	 * @param source
	 * @param target
	 * @return 같으면 true, 그렇지 않으면 false반환
	 */
	public static boolean isEquals(Element source, String target) {
		if (target.indexOf(":") > 0) {
			String prefix = target.substring(0, target.indexOf(":"));
			String elementName = target.substring(target.indexOf(":") + 1);
			
			return source.getName().equals(elementName);
		} else {
			return source.getName().equals(target);
		}

	}
	/**
	 * '/' 문자값을 기준으로 Depth 계산
	 * @param Depth를 계산 하고자하는 문자열
	 * @return Depth
	 */
	public static int calcDepth(String data){
		int count = 0;
		int d_length= data.length();
		for(int i= 0 ;i < d_length ; i++) {
			if(data.charAt(i)=='/') {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * '/' 문자값이 num 수 만큼 나온 지점(offset)을 계산함 
	 * @param 연산하고자 하는 문자열
	 * @param '/' 의 횟수
	 * @return offset
	 */
	public static int findOffset(String data,int num){
		int count = 0;
		int offset = 0;
		int d_length= data.length();
		for(int i= 0 ;i < d_length ; i++) {
			if(data.charAt(i)=='/') {
				count++;
				if(count==num){
					offset = i;
				}
			}
		}		
		return offset;
	}
}
