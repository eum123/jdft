package net.jdft.xml.writer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jdft.config.data.DataConfig;
import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.create.XmlCreateConfig;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.converter.writer.WriteConverterContext;
import net.jdft.util.ObjectHelper;

import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;

public class XmlWriteConverterContext extends WriteConverterContext {
	private Element root = null;
	
	public XmlWriteConverterContext(Map map, DataConfig config) throws Exception {
		super(map, config);
	}
	
	public Element getRootElement() {
		return root;
	}

	public Element getRootElement(List list) throws Exception {
		if(root != null) {
			return root;
		}
		Iterator<EntityConfig> it = list.iterator();
		while (it.hasNext()) {
			EntityConfig entityConfig = it.next();

			String path = entityConfig.getProperty(FieldConfigConstants.XPATH);

			if (path == null) {
				throw new Exception(entityConfig.getId() + " field의 xpath 설정이 없음");
			}
			String firstName = ObjectHelper.removeStartingCharacters(path, '/').split("/")[0];
			if (entityConfig instanceof XmlCreateConfig) {
				XmlCreateConfig createConfig = (XmlCreateConfig) entityConfig;

				Map<String, Namespace> namespaceList = new HashMap<String, Namespace>();
				Iterator<Namespace> nit = createConfig.getNamespaceList().iterator();
				while (nit.hasNext()) {
					Namespace ns = nit.next();
					namespaceList.put(ns.getPrefix(), ns);
				}

				Element element = null;
				if (firstName.indexOf(":") > 0) {
					String prefix = firstName.substring(0, firstName.indexOf(":"));
					String elementName = firstName.substring(firstName.indexOf(":") + 1);

					element = new Element(elementName, namespaceList.get(prefix));
				} else {
					element = new Element(firstName);
				}

				Iterator<Attribute> ait = createConfig.getAttributes().iterator();
				while (ait.hasNext()) {
					Attribute attribute = ait.next();
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
								element.setAttribute(attribute.getName(), attribute.getValue(),
										Namespace.getNamespace(attribute.getNamespacePrefix(),
												attribute.getNamespaceURI()));
							}
						} else {
							element.setAttribute(attribute.getName(), attribute.getValue());
						}
					}
				}

				return root = element;
			} else {
				return root = new Element(firstName);
			}

		}
		return null;
	}
}
