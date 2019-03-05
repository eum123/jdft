package net.jdft.xml.reader;

import java.util.Map;

import net.jdft.config.data.DataConfig;
import net.jdft.converter.reader.ReadConverterContext;
import net.jdft.util.XMLUtil;

import org.apache.commons.jxpath.JXPathContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlReadConverterContext extends ReadConverterContext {

	private static final Logger log = LoggerFactory.getLogger(XmlReadConverterContext.class);

	private JXPathContext jxpathContext = null;

	public XmlReadConverterContext(Map map, byte[] src, DataConfig config) throws Exception {
		super(map, src, config);
		jxpathContext = createJxPathContext();
	}
	
	public XmlReadConverterContext( byte[] src, DataConfig config) throws Exception {
		super(src, config);
		jxpathContext = createJxPathContext();
	}

	public Object getJxPathContext() {
		return jxpathContext;

	}

	private JXPathContext createJxPathContext() throws Exception {
		Document doc = null;
		byte[] xml = (byte[]) super.getSource();

		try {
			doc = XMLUtil.getDocument(xml);
		} catch (Exception e) {
			log.warn("namespace가 없거나 다른 오류로 jdom을 이용하여 Document를 생성할수 없음. DOM을 이용함. : "
					+ new String(xml));
			org.w3c.dom.Document w3cDocument = XMLUtil.getW3CDocument(xml);
			DOMBuilder builder = new DOMBuilder();
			doc = builder.build(w3cDocument);
		}

		Element root = doc.getRootElement();

		return JXPathContext.newContext(doc);
	}
}
