package net.jdft.xml.writer.entity;

import java.util.Map;

import net.jdft.config.entity.EntityConfig;

import org.jdom.Element;
import org.jdom.Namespace;

public interface XmlEntityWriter<T> {

	public T write(Element parent, String parentPath, Map<String, Namespace> namespaceList, Map map,
			EntityConfig config) throws Exception;
}
