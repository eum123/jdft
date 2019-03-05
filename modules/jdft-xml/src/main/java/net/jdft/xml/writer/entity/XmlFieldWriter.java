package net.jdft.xml.writer.entity;

import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.field.XmlFieldConfig;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.field.FieldHelper;
import net.jdft.util.field.XmlFieldHelper;
import net.jdft.xml.util.XmlHelper;

import org.jdom.Element;
import org.jdom.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlFieldWriter implements XmlEntityWriter<Object> {
	
	private static final Logger log = LoggerFactory.getLogger(XmlFieldWriter.class);

	public Object write(Element parent, String parentPath, Map<String, Namespace> namespaceList,
			Map map, EntityConfig config) throws Exception {
				
		XmlFieldConfig xmlConfig = (XmlFieldConfig) config;
		xmlConfig.validate();

		String value = getValue(map, xmlConfig);
		
		String ignoreStr = xmlConfig.getProperty("ignore");
		boolean ignoreFlag = false;
		if (ignoreStr == null || ignoreStr.equalsIgnoreCase("false")) {
			ignoreFlag = false;
		} else if(ignoreStr.equalsIgnoreCase("true")){
			ignoreFlag = true;
		} else {
			log.warn("xml에서 " + config.getId()+" 의 ignore 설정 정보가 잘 못 설정되었음(ex. true/false) : " + ignoreStr );
		}
		
		if(ignoreFlag) { // ignore="true"
			if(value == null) {
				return null;
			}	
		} else { // ignore="false"
			// value가 없으면 기본 값으로 설정한다.
			if (value == null) {
				value = "";
			}
		}	
		value = FieldHelper.getDecoratedValue(map, value, xmlConfig);
		appendChildElement(parent, parentPath, map, xmlConfig, value);
		return null;
	}

	/**
	 * xpath에서 parent 부분을 제외한 element를 생성한다.
	 * 
	 * @param parentPath
	 * @param namespaceList
	 * @param config
	 * @return
	 */
	private Element appendChildElement(Element parent, String parentPath,
			Map<String, Namespace> namespaceList, XmlFieldConfig config, String value) {
		String[] createNodeList = XmlFieldHelper.getChildNodeList(parentPath,
				config.getProperty(FieldConfigConstants.XPATH));
		Element element = parent;
		for (int i = 0; i < createNodeList.length; i++) {
			
			if(XmlHelper.isEquals(element, createNodeList[i])) {
				continue;
			}
			if (i == createNodeList.length - 1) {
				// last path
							
				if(createNodeList[i].indexOf("@") == 0){ //if : attribute
					if(value.equals("")){
						continue;
					} else {
						String attrName = createNodeList[i].substring(1, createNodeList[i].length());
						element.setAttribute(attrName, value);
					}
				} else { // if : element
					element.addContent(XmlHelper.createElement(createNodeList[i], namespaceList, value));
				}
			} else {
				if (parentPath.equals("") && element.getName().equals(createNodeList[i])) {
					continue;
				}
				element = createAndAppendElement(element, createNodeList[i], namespaceList);
			}
		}

		return element;
	}

	private Element createAndAppendElement(Element parent, String name,
			Map<String, Namespace> namespaceList) {
		if (name.indexOf(":") > 0) {
			String prefix = name.substring(0, name.indexOf(":") - 1);
			String elementName = name.substring(name.indexOf(":") + 1);

			if (parent.getChild(elementName, namespaceList.get(prefix)) != null) {
				return parent.getChild(elementName, namespaceList.get(prefix));
			} else {
				parent.addContent(new Element(elementName, namespaceList.get(prefix)));

				return parent.getChild(elementName, namespaceList.get(prefix));
			}

		} else {
			if(parent.getChild(name) == null) {
				parent.addContent(new Element(name));
			}
			return parent.getChild(name);
		}
	}

	private String getValue(Map map, XmlFieldConfig config) throws Exception {

		Object obj = map.get(config.getId());

		if (obj == null) {
			return null;
		}

		if (obj instanceof String) {
			return (String) obj;
		} else if (obj instanceof Long) {
			return ((Long) obj).toString();
		} else if (obj instanceof Integer) {
			return ((Integer) obj).toString();
		} else {
			throw new Exception(config.getId() + " unsupport type : " + obj);
		}

	}
}
