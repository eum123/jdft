package net.jdft.xml.reader.entity;

import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.field.XmlFieldConfig;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.field.FieldHelper;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathNotFoundException;
import org.jdom.Attribute;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFieldReader implements XmlEntityReader<JXPathContext> {
	private static final Logger log = LoggerFactory.getLogger(XmlFieldReader.class);

	public int read(Map map, JXPathContext ctx, String parentPath, EntityConfig config)
			throws Exception {

		XmlFieldConfig xmlConfig = (XmlFieldConfig) config;
		xmlConfig.validate();
		
		Object obj = getObject(map, ctx, parentPath, xmlConfig);
		if (obj == null) {
			log.warn("xml에서 " + config.getProperty(FieldConfigConstants.XPATH)
					+ "설정 정보에 맞는 값을 찾을수 없음");
			return 0;
		}

		String value = getValue(obj);
		value = FieldHelper.getDecoratedValue(map, value, xmlConfig);
		map.put(config.getId(), value);

		return value.getBytes().length;
	}

	private Object getObject(Map map, JXPathContext ctx, String parentPath, XmlFieldConfig config)
			throws Exception {

		
		String xmlPath = config.getProperty(FieldConfigConstants.XPATH);

		String newPath = null;
		if(parentPath == null) {
			newPath = xmlPath;
		} else {
			//newPath = parentPath + xmlPath.substring(xmlPath.lastIndexOf("/"));
			newPath = makeNewPath(parentPath, xmlPath);
			//System.out.println(">> : " + newPath);
		}
	
		Object retObj = null;
		try {
			retObj = ctx.selectSingleNode(newPath);
		} catch (JXPathNotFoundException e) {
				
		}
		return retObj;

	}

	private String getValue(Object obj) throws Exception {

		if (obj instanceof Element) {
			return ((Element) obj).getText();
		} else if (obj instanceof Attribute) {
			return ((Attribute) obj).getValue();
		} else {
			throw new Exception("unknown object (" + obj + ")");
		}
	}
	private int findDepth(String str) {
		int count = 0;
		byte[] bArray = str.getBytes();
		
		for (int i=0 ; i < bArray.length ; i++) {
			if( bArray[i] == '/' ) {
				count++;
			}
			
		}
		return count;
	}
	private String customSubStr(String str , int num){
		int count = num;
		int offset = 0;
		byte[] bArray = str.getBytes();
		for(int i=0 ; i < bArray.length ; i++) {
			if( bArray[i] == '/' ) {
				count--;
				if(count < 0 ){
					offset = i;
					break;
				}
			}
		}
		return new String(bArray, offset, bArray.length-offset);
	}
	private String makeNewPath(String parentPath, String xmlPath) throws Exception {
		// parentPath : /request/sequence/entry[1]
		// xmlPath :    /request/sequence/entry/value/string
		// newPath :    /request/sequence/entry[1]/value/string
		String res = "";
		int pDepth = findDepth(parentPath);
		int xDepth = findDepth(xmlPath);
		if(pDepth < xDepth) {
			res = parentPath + customSubStr(xmlPath, pDepth);
		} else if(pDepth == xDepth) { // 반복필드에 값이 있는 경우 해당 조건문 (거의 대부분의 xml은 반복 필드에는 값이 없음)
			res = parentPath;
		}else {
			throw new Exception("Fail to Create NewPath. parentPath : " + parentPath + " , xmlPath : " + xmlPath);
		} 
		return res;
		
	}
}
