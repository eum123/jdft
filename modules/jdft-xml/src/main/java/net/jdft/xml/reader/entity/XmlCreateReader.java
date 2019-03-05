package net.jdft.xml.reader.entity;

import java.util.Iterator;
import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.create.XmlCreateConfig;

import org.apache.commons.jxpath.JXPathContext;
import org.jdom.Namespace;

public class XmlCreateReader implements XmlEntityReader<JXPathContext> {
	public int read(Map map, JXPathContext ctx, String parentPath, EntityConfig config)
			throws Exception {

		XmlCreateConfig xmlConfig = (XmlCreateConfig) config;
		Iterator<Namespace> it = xmlConfig.getNamespaceList().iterator();
		while (it.hasNext()) {
			Namespace ns = it.next();
			ctx.registerNamespace(ns.getPrefix(), ns.getURI());
		}

		return 0;
	}
}
