package net.jdft.xml.writer.header;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.header.XmlHeaderConfig;
import net.jdft.converter.writer.WriteConverterContext;
import net.jdft.converter.writer.header.HeaderWriter;
import net.jdft.util.XMLUtil;
import net.jdft.xml.writer.XmlWriteConverterContext;
import net.jdft.xml.writer.entity.XmlEntityWriter;
import net.jdft.xml.writer.entity.XmlWriterFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlHeaderWriter implements HeaderWriter<byte[]> {
	private static final transient Logger log = LoggerFactory.getLogger(XmlHeaderWriter.class);

	public byte[] write(WriteConverterContext ctx) throws Exception {
		
		XmlWriteConverterContext writeConverterContext = (XmlWriteConverterContext)ctx;
		XmlHeaderConfig config = (XmlHeaderConfig) ctx.getHeaderConfig();

		Element element = writeConverterContext.getRootElement(config.getList());
		if (element == null) {
			return new byte[] {};
		}

		Map<String, Namespace> list = new HashMap<String, Namespace>();

		Iterator<EntityConfig> it = config.getList().iterator();

		while (it.hasNext()) {
			EntityConfig entityConfig = it.next();
			XmlEntityWriter writer = XmlWriterFactory.newInstance(config.getFormat(), entityConfig);

			writer.write(element, "", list, ctx.getMap(), entityConfig);

		}

		Document document = new Document();
		document.setRootElement(element);

		byte[] xmlBytes = XMLUtil.toString(document, config.getEncoding(), config.isIndent())
				.getBytes();

		if (config.getId() == null) {
			log.warn("body 설정에 id attribute가 없어 body의 length을 설정 할 수 없음");
		} else {
			//body의 길이를 구할수 있게 body id로 xml 데이터를 저장한다.
			ctx.getMap().put(config.getId(), xmlBytes);
		}
		return xmlBytes;
	}

	

}
