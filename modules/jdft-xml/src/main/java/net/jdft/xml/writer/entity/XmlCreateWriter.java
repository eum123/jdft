package net.jdft.xml.writer.entity;

import java.util.Iterator;
import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.create.XmlCreateConfig;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.field.XmlFieldHelper;
import net.jdft.xml.util.XmlHelper;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

public class XmlCreateWriter implements XmlEntityWriter<Object> {

	public Object write(Element parent, String parentPath, Map<String, Namespace> namespaceList,
			Map map, EntityConfig config) throws Exception {

		XmlCreateConfig createConfig = (XmlCreateConfig) config;

		// add namespace
		Iterator<Namespace> it = createConfig.getNamespaceList().iterator();
		while (it.hasNext()) {
			Namespace ns = it.next();
			namespaceList.put(ns.getPrefix(), ns);
		}

		appendChildElement(parent, parentPath, map, createConfig);
		
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
			Map<String, Namespace> namespaceList, XmlCreateConfig config) {

		String[] createNodeList = XmlFieldHelper.getChildNodeList(parentPath,
				config.getProperty(FieldConfigConstants.XPATH));

		Element element = parent;
		for (int i = 0; i < createNodeList.length; i++) {
			if(XmlHelper.isEquals(element, createNodeList[i])) {
				continue;
			}
			
			if (i == createNodeList.length - 1) {
				// last path
				element.addContent(createElement(createNodeList[i], namespaceList, config));
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
			String prefix = name.substring(0, name.indexOf(":"));
			String elementName = name.substring(name.indexOf(":") + 1);

			if(parent.getChild(elementName, namespaceList.get(prefix)) != null) {
				return parent.getChild(elementName, namespaceList.get(prefix));
			} else {
				parent.addContent(new Element(elementName, namespaceList.get(prefix)));

				return parent.getChild(elementName, namespaceList.get(prefix));
			}

		} else {
			parent.addContent(new Element(name));
			return parent.getChild(name);
		}
	}

	/**
	 * 제일 하위 element를 생성하고 namespace, attribute(xsi, schemaLocation 등) 설정 추가
	 * 
	 * @param name
	 * @param namespaceList
	 * @param config
	 * @return
	 */
	private Element createElement(String name, Map<String, Namespace> namespaceList,
			XmlCreateConfig config) {
		Element element = XmlHelper.createElement(name, namespaceList);
		
		Iterator<Attribute> it = config.getAttributes().iterator();
		while (it.hasNext()) {
			Attribute attribute = it.next();
			if (attribute.getName().equals("schemaLocation")) {
				// schemaLocation
				element.setAttribute(attribute.getName(), attribute.getValue(),
						namespaceList.get("xsi"));
			} else {

				if (attribute.getNamespacePrefix() != null
						&& !attribute.getNamespacePrefix().equals("")) {
					// namespace
					if (namespaceList.containsKey(attribute.getNamespacePrefix())) {
						element.setAttribute(attribute.getName(), attribute.getValue(),
								namespaceList.get(attribute.getNamespacePrefix()));
					} else {
						element.setAttribute(
								attribute.getName(),
								attribute.getValue(),
								Namespace.getNamespace(attribute.getNamespacePrefix(),
										attribute.getNamespaceURI()));
					}
				} else {
					element.setAttribute(attribute.getName(), attribute.getValue());
				}
			}
		}

		return element;
	}

}
