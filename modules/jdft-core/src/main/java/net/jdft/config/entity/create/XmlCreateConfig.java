package net.jdft.config.entity.create;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import net.jdft.config.entity.EntityConfig;
import net.jdft.constants.ConfigConstants;
import net.jdft.constants.FieldConfigConstants;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

public class XmlCreateConfig implements EntityConfig {
	public static final String NAME = "create";
	private Properties properties = new Properties();
	private List<Namespace> namespaceList = new ArrayList<Namespace>();
	private List<Attribute> attributes = new ArrayList();

	public XmlCreateConfig(Element element) {
		Iterator<Namespace> it = element.getAdditionalNamespaces().iterator();
		while (it.hasNext()) {
			Namespace ns = it.next();
			if (ns.getPrefix().equals("") && ns.getURI().equals("")) {
				continue;
			}

			namespaceList.add(ns);
		}

		// xsi:schemaLocation
		List<Attribute> attList = element.getAttributes();
		if (attList != null) {
			Iterator<Attribute> ait = attList.iterator();
			while (ait.hasNext()) {
				Attribute att = ait.next();
				if (att.getName().equals(FieldConfigConstants.XPATH)) {
					//xpath 설정을 제외 한다.
					continue;
				}
				attributes.add(att);
			}
		}

		// xpath만 저장한다.(write에서 사용함)
		Iterator<Attribute> ait = element.getAttributes().iterator();
		while (ait.hasNext()) {
			Attribute attribute = ait.next();
			if (attribute.getName().equals(FieldConfigConstants.XPATH)) {
				setProperty(attribute.getName(), attribute.getValue());
			}
		}
	}

	public String getId() {
		return "create";
	}

	public String getName() {
		return NAME;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	public void validate() {

	}

	public List<Namespace> getNamespaceList() {
		return namespaceList;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

}
