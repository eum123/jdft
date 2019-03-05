package net.jdft.soap.writer.entity;

import java.io.File;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.xml.soap.AttachmentPart;

import net.jdft.config.data.DataConfig;
import net.jdft.config.data.SoapAttachDataConfig;
import net.jdft.config.entity.file.FileFieldConfig;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.soap.util.FileHelper;
import net.jdft.soap.util.MimeType;

import com.sun.xml.messaging.saaj.packaging.mime.internet.InternetHeaders;
import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeBodyPart;
import com.sun.xml.messaging.saaj.packaging.mime.internet.MimePartDataSource;
import com.sun.xml.messaging.saaj.util.Base64;

public class FileEntityWriter {
	public void write(Map map, DataConfig dataConfig, FileFieldConfig config, AttachmentPart part)
			throws Exception {

		SoapAttachDataConfig attachConfig = (SoapAttachDataConfig) dataConfig;

		byte[] content = null;
		// file 처리
		String fileName = config.getProperty(FieldConfigConstants.FILE_NAME);
		if (fileName == null) {
			Object data = map.get(config.getId());
			
			if(data == null) {
				return;
			}

			if (data instanceof byte[]) {
				content = (byte[]) data;
			} else if (data instanceof String) {
				content = ((String) data).getBytes();
			} else {
				throw new IllegalArgumentException("파일 write에 지원하지 않는 데이터의 type 임 : "
						+ data.getClass().getName());
			}
		} else {
			// 파일 내용
			content = FileHelper.read(new File(fileName));
		}

		addContent(part, attachConfig, content);

	}

	private void addContent(AttachmentPart part, SoapAttachDataConfig config, byte[] data) {
		InternetHeaders headers = new InternetHeaders();
		headers.addHeader("Content-Type", MimeType.getMimeType(config.getFileExtension()));

		byte[] content = data;

		String encoding = config.getEncoding();
		if (encoding != null) {
			if (encoding.equals("base64")) {
				content = Base64.encode(data);
			}
			headers.addHeader("Content-Transfer-Encoding", encoding);
		}

		MimeBodyPart bodyPart = new MimeBodyPart(headers, content, content.length);
		DataSource dataSource = new MimePartDataSource(bodyPart);
		DataHandler handler = new DataHandler(dataSource);

		part.setDataHandler(handler);
	}

	
}
