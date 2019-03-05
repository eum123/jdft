package net.jdft.xml.writer.body;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.jdft.config.body.XmlBodyConfig;
import net.jdft.config.entity.EntityConfig;
import net.jdft.converter.writer.WriteConverterContext;
import net.jdft.converter.writer.body.BodyWriter;
import net.jdft.util.XMLUtil;
import net.jdft.xml.writer.XmlWriteConverterContext;
import net.jdft.xml.writer.entity.XmlEntityWriter;
import net.jdft.xml.writer.entity.XmlWriterFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlBodyWriter implements BodyWriter<byte[]> {
	private static final transient Logger log = LoggerFactory.getLogger(XmlBodyWriter.class);

	public byte[] write(WriteConverterContext ctx) throws Exception {
		XmlBodyConfig config = (XmlBodyConfig) ctx.getBodyConfig();

		XmlWriteConverterContext writeConverterContext = null;
		if (ctx instanceof XmlWriteConverterContext) {
			writeConverterContext = (XmlWriteConverterContext) ctx;
		} else {
			writeConverterContext = new XmlWriteConverterContext(ctx.getMap(), ctx.getDataConfig());
		}

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
		Document document = new Document(element);
		byte[] xmlBytes = null;
		if(config.getDataEncoding()!= null) {
			
			String temp = XMLUtil.toString(document, config.getEncoding(), config.getDataEncoding() , config.isIndent());
			
			xmlBytes = temp.getBytes(config.getDataEncoding());
		} else {
			xmlBytes = XMLUtil.toString(document, config.getEncoding(), config.getDataEncoding() , config.isIndent()).getBytes();
		}
		

		if (config.getId() == null) {
			log.warn("BodyConfig에 id attribute가 없어 body의 값을 설정 할 수 없음");
		} else {
			// body의 길이를 구할수 있게 body id로 xml 데이터를 저장한다.
			ctx.getMap().put(config.getId(), xmlBytes);
		}
		return xmlBytes;
	}
}
