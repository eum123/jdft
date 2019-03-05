package net.jdft.xml.writer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jdft.config.body.BodyConfig;
import net.jdft.config.data.DataConfig;
import net.jdft.config.to.ToConfig;
import net.jdft.config.to.XmlToConfig;
import net.jdft.converter.writer.Writer;
import net.jdft.util.XMLUtil;
import net.jdft.xml.writer.body.XmlBodyWriter;
import net.jdft.xml.writer.header.XmlHeaderWriter;

import org.jdom.Document;
import org.jdom.Element;

public class XmlWriter extends AbstractWriter implements Writer<byte[]> {
	public byte[] write(Map map, ToConfig toConfig) throws Exception {
		XmlToConfig xmlToConfig = (XmlToConfig) toConfig;
		List<DataConfig> list = xmlToConfig.getDataConfigList();

		byte[] data = {};
		Iterator<DataConfig> it = list.iterator();
		while (it.hasNext()) {
			DataConfig config = it.next();
			validate(config);

			XmlWriteConverterContext ctx = new XmlWriteConverterContext(map, config);

			new XmlHeaderWriter().write(ctx);

			BodyConfig bodyConfig = ctx.getBodyConfig();
			new XmlBodyWriter().write(ctx);

			Element root = ctx.getRootElement();
			Document document = new Document();
			document.setRootElement(root);

			if(xmlToConfig.getDataEncoding() != null) {
				String temp = XMLUtil.toString(document, xmlToConfig.getEncoding(), xmlToConfig.getDataEncoding() , xmlToConfig.isIndent());
				data = temp.getBytes(xmlToConfig.getDataEncoding());
			} else {
				data = XMLUtil.toString(document, xmlToConfig.getEncoding(), xmlToConfig.isIndent()).getBytes();
			}

		}
		return data;
	}
}
