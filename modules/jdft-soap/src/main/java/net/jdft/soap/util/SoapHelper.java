package net.jdft.soap.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;

import net.jdft.util.BytesUtil;

import com.sun.xml.messaging.saaj.packaging.mime.internet.ContentType;

public class SoapHelper {
	
	
	public static boolean containContentLength(Iterator<MimeHeader> it) {
		
		while (it.hasNext()) {
			MimeHeader header = it.next();
			if (header.getName().equals("Content-Length")) {
				return true;
			}
		}
		return false;
	}
	
	
	public static ContentType getContentType(Iterator<MimeHeader> it) throws Exception {
		ContentType contentType = null;
		while (it.hasNext()) {
			MimeHeader header = it.next();
			if (header.getName().equals("Content-Type")) {
				contentType = new ContentType(header.getValue());
			}
		}
		return contentType;
	}

	public static ContentType getContentType(MimeHeaders headers) throws Exception {
		return getContentType(headers.getAllHeaders());
	}

	public static SOAPMessage createSOAPMessage(byte[] buffer) throws Exception {

		ByteArrayInputStream input = new ByteArrayInputStream(buffer);

		try {
			SOAPMessage soap = MessageFactory.newInstance().createMessage(getMimeHeader(buffer),
					input);
			soap.saveChanges();
			return soap;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static MimeHeaders getMimeHeader(byte[] buffer) throws Exception {
		BufferedReader reader = null;
		MimeHeaders mimeHeaders = new MimeHeaders();
		try {
			reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0) {
					break;
				} else {

					if (line.indexOf("POST") >= 0 || line.indexOf("HTTP") >= 0) {
						continue;
					}

					String key = "";
					String value = "";
					if (line.indexOf(":") >= 0) {
						key = line.substring(0, line.indexOf(":"));
						value = line.substring(line.indexOf(":") + 2);

						mimeHeaders.addHeader(key, value);

					}
				}
			}

			return mimeHeaders;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public static byte[] getContentToBytes(SOAPPart part) throws IOException, SOAPException {
		StreamSource source = (StreamSource) part.getContent();
		BufferedInputStream bis = null;

		byte[] lineSeperator = ("\r\n\r\n").getBytes();

		try {
			bis = new BufferedInputStream(source.getInputStream());
			byte[] buffer = new byte[bis.available()];
			bis.read(buffer);

			int index = 0;
			for (int i = 0; i < buffer.length - lineSeperator.length; i++) {
				boolean isMatch = true;
				for (int j = 0; j < lineSeperator.length; j++) {
					isMatch &= buffer[i + j] == lineSeperator[j];

					if (!isMatch) {
						continue;
					}
				}

				if (isMatch) {
					index = i;
					break;
				}
			}

			return BytesUtil.split(buffer, index + lineSeperator.length, buffer.length
					- (index + lineSeperator.length));
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
	}

	public static String getContent(SOAPPart part) throws IOException, SOAPException {

		StreamSource source = (StreamSource) part.getContent();

		StringBuffer buffer = new StringBuffer();
		StringBuffer temp = new StringBuffer();
		BufferedReader reader = null;
		boolean isMimeHeader = true;
		try {
			reader = new BufferedReader(new InputStreamReader(source.getInputStream()));
			String line = "";

			while ((line = reader.readLine()) != null) {
				temp.append(line + "\r\n");

				if (line.trim().length() == 0) {
					isMimeHeader = false;
					continue;
				}
				if (!isMimeHeader) {
					buffer.append(line + "\r\n");
				}

			}

			if (!isMimeHeader) {
				return buffer.toString();
			} else {
				return temp.toString();
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public static String getContent(AttachmentPart part) throws IOException, SOAPException {
		if (part.getContent() instanceof StreamSource) {
			// 단순 text(xml 포함)
			return new String(part.getRawContentBytes());
		}
		if (part.getContent() instanceof String) {
			return (String) part.getContent();
		}
		// 첨부파일
		ByteArrayInputStream source = (ByteArrayInputStream) part.getContent();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		boolean isMimeHeader = true;
		boolean isData = false;
		try {
			reader = new BufferedReader(new InputStreamReader(source));
			String line = "";
			while ((line = reader.readLine()) != null) {
				if (isMimeHeader) {
					if (line.indexOf(": ") > 0) {
						continue;
					} else {
						isMimeHeader = false;
						isData = true;
					}
				}
				if (line.trim().length() == 0) {
					isMimeHeader = false;
					isData = true;
					continue;
				}
				if (isData) {
					buffer.append(line + "\r\n");
				}
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return buffer.toString();
	}

	public static byte[] convert(SOAPMessage soap) throws Exception {
		ByteArrayOutputStream byteOutput = null;
		try {
			byteOutput = new ByteArrayOutputStream();
			soap.writeTo(byteOutput);

			return byteOutput.toByteArray();
		} finally {
			if (byteOutput != null) {
				byteOutput.close();
			}
		}
	}
}
