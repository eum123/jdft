package net.jdft.xml.writer.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.loop.XmlLoopConfig;
import net.jdft.constants.DataFormatConstants;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.field.FieldHelper;
import net.jdft.util.field.XmlFieldHelper;
import net.jdft.xml.util.XmlHelper;

import org.jdom.Element;
import org.jdom.Namespace;

/**
 * 
 * @author manjin
 * 
 */
public class XmlLoopWriter implements XmlEntityWriter<Object> {
	public Object write(Element parent, String parentPath, Map<String, Namespace> namespaceList,
			Map map, EntityConfig config) throws Exception {

		XmlLoopConfig loopConfig = (XmlLoopConfig) config;
		loopConfig.validate();

		appendChildElement(parent, map, parentPath, namespaceList, loopConfig);

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
	private Object appendChildElement(Element parent, Map map, String parentPath,
			Map<String, Namespace> namespaceList, XmlLoopConfig config) throws Exception {

		String[] createNodeList = XmlFieldHelper.getChildNodeList(parentPath,
				config.getProperty(FieldConfigConstants.XPATH));
		Element element = parent;

		for (int i = 0; i < createNodeList.length; i++) {
			if (XmlHelper.isEquals(element, createNodeList[i])) {
				continue;
			}
			if (i == createNodeList.length - 1) {
				element.addContent(createElement(map, createNodeList[i], namespaceList, config));
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

			if (parent.getChild(elementName, namespaceList.get(prefix)) != null) {
				return parent.getChild(elementName, namespaceList.get(prefix));
			} else {
				parent.addContent(new Element(elementName, namespaceList.get(prefix)));

				return parent.getChild(elementName, namespaceList.get(prefix));
			}

		} else {
			if(parent.getChild(name) != null ) {
				return parent.getChild(name);
			} else {
				parent.addContent(new Element(name));
				return parent.getChild(name);	
			}			
		}
	}

	/**
	 * 제일 하위 element를 생성하고 값 추가
	 * 
	 * @param name
	 * @param namespaceList
	 * @param config
	 * @return
	 */
	private List<Element> createElement(Map map, String name, Map<String, Namespace> namespaceList,
			XmlLoopConfig config) throws Exception {

		List<Element> list = new LinkedList<Element>();
		int size = FieldHelper.getSize(map, config);

		List<?> datalist = (List<?>) map.get(config.getId());

		for (int i = 0; i < size; i++) {

			Element element = XmlHelper.createElement(name, namespaceList);

			if (config.getEntities().size() == 0) {
				if (datalist.get(i) instanceof String) {
					element.setText((String) datalist.get(i));
				} else if (datalist.get(i) instanceof byte[]) {
					// default charset
					element.setText(new String((byte[]) datalist.get(i)));
				} else if (datalist.get(i) instanceof Integer) {
					// default charset
					element.setText(String.valueOf((Integer) datalist.get(i)));
				} else if (datalist.get(i) instanceof Long) {
					// default charset
					element.setText(String.valueOf((Long) datalist.get(i)));
				}
			} else {

				Iterator<EntityConfig> it = config.getEntities().iterator();
				while (it.hasNext()) {
					EntityConfig entityConfig = it.next();
					XmlEntityWriter<Object> writer = XmlWriterFactory.newInstance(
							DataFormatConstants.XML, entityConfig);
					if (i + 1 > datalist.size()) {
						// 데이터보다 size가 클 경우 공백으로 xml을 만든다.
						writer.write(element, config.getProperty(FieldConfigConstants.XPATH),
								namespaceList, new HashMap(), entityConfig);
					} else {
						writer.write(element, config.getProperty(FieldConfigConstants.XPATH),
								namespaceList, (Map) datalist.get(i), entityConfig);
					}
				}
			}

			list.add(element);
		}
		return list;
	}
}
