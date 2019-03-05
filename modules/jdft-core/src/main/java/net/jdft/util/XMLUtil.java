package net.jdft.util;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.Format.TextMode;
import org.jdom.output.XMLOutputter;

public class XMLUtil {

	/**
	 * file의 내용일 읽어서 root element를 구한다.
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static Element getRootElement(String filePath) throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document xmlDoc = builder.build(filePath);

		return xmlDoc.getRootElement();
	}

	public static Element getRootElement(byte[] xmlContent) throws IOException, JDOMException {
		ByteArrayInputStream is = null;

		try {
			is = new ByteArrayInputStream(xmlContent);

			return getRootElement(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}

	}
	
	public static Document getDocument(byte[] xmlContent) throws IOException, JDOMException {
		ByteArrayInputStream is = null;

		try {
			is = new ByteArrayInputStream(xmlContent);

			return getDocument(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	public static Document getDocument(InputStream stream) throws IOException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		return builder.build(stream);
	}

	public static Element getRootElement(InputStream stream) throws IOException, JDOMException {

		SAXBuilder builder = new SAXBuilder();
		Document xmlDoc = builder.build(stream);

		return xmlDoc.getRootElement();

	}
	
	public static org.w3c.dom.Document getW3CDocument(InputStream inputSource)
			throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(inputSource);
	}

	public static org.w3c.dom.Document getW3CDocument(byte[] xmlContent) throws Exception {
		ByteArrayInputStream is = null;

		try {
			is = new ByteArrayInputStream(xmlContent);

			return getW3CDocument(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
	
	public static String toString(Element xml) throws IOException {
		return toString(xml, "euc-kr", null, false);
	}

	public static String toString(Element xml, boolean indent)
			throws IOException {
		return toString(xml, "euc-kr", null, indent);
	}

	public static String toString(Element xml, String encoding)
			throws IOException {
		return toString(xml, encoding, null, false);
	}

	public static String toString(Document doc) throws IOException {
		return toString(doc, "euc-kr", null, false);
	}

	public static String toString(Document doc, boolean indent)
			throws IOException {
		return toString(doc, "euc-kr", null, indent);
	}

	public static String toString(Document doc, String encoding)
			throws IOException {
		return toString(doc, encoding, null, false);
	}

	public static String toString(Document doc, String encoding, boolean indent)
			throws IOException {
		return toString(doc, encoding, null, indent);
	}

	public static String toString(Document document, String encoding,
			String dataEncoding, boolean indent, boolean expandEmptyElements)
			throws IOException {
		Format format = Format.getRawFormat().setTextMode(TextMode.PRESERVE);
		format.setExpandEmptyElements(expandEmptyElements);

		if (encoding != null) {
			format = format.setEncoding(encoding); 
		}

		if (indent) {
			format = format.setIndent(" ");
		}

		XMLOutputter out = new XMLOutputter(format);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		BufferedWriter writer = null;

		
		if (dataEncoding == null) {
			writer = new BufferedWriter(new OutputStreamWriter(stream));
		} else {
			writer = new BufferedWriter(
					new OutputStreamWriter(stream, dataEncoding));
		}

		try {
			out.output(document, writer); 
			if (dataEncoding == null) {
				return stream.toString();
			} else {
				return stream.toString(dataEncoding);
			}			
			
			
		} finally {
			document.getRootElement().detach();

			try {
				writer.close();
			} catch (IOException e) {
				throw e;
			}
		}
	}

	public static String toString(Document document, String encoding,
			String dataEncoding, boolean indent) throws IOException {
		return toString(document, encoding, dataEncoding, indent, false);
	}

	public static String toString(Element xml, String encoding,
			String dataEncoding, boolean indent) throws IOException {
		Document document = new Document(xml);

		return toString(document, encoding, dataEncoding, indent);
	}

	public static String toString(Element element, String encoding,
			String dataEncoding, boolean indent, boolean expandEmptyElements)
			throws IOException {
		Format format = Format.getRawFormat().setTextMode(TextMode.PRESERVE);
		format.setExpandEmptyElements(expandEmptyElements);

		if (encoding != null) {
			format = format.setEncoding(encoding); // encoding�� euc-kr��
													// �����Ѵ�.
		}

		if (indent) {
			format = format.setIndent(" ");
		}

		XMLOutputter out = new XMLOutputter(format);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		BufferedWriter writer = null;

		if (dataEncoding == null) {
			writer = new BufferedWriter(new OutputStreamWriter(stream));
		} else {
			writer = new BufferedWriter(
					new OutputStreamWriter(stream, encoding));
		}

		try {
			out.output(element, writer); // xml �ۼ�
			return stream.toString();
		} finally {
			// element�� parent node remove. ������ ���� �߻���.
			element.detach();

			try {
				writer.close();
			} catch (IOException e) {
				throw e;
			}
		}
	}
}
