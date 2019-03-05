package net.jdft.soap.reader.entity;

import java.io.File;
import java.util.Map;

import javax.xml.soap.AttachmentPart;

import net.jdft.config.entity.file.FileFieldConfig;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.soap.util.FileHelper;
import net.jdft.soap.util.SoapHelper;

import com.sun.xml.messaging.saaj.packaging.mime.util.BASE64DecoderStream;

public class FileEntityReader {
	public int read(Map map, Map headerInfo, FileFieldConfig config, AttachmentPart part)
			throws Exception {
		
		byte[] data = getContent(headerInfo, part);
		
		//file 처리
		String fileName = config.getProperty(FieldConfigConstants.FILE_NAME);
		if(fileName == null) {
			//write
			FileHelper.write(new File(fileName), data);
			
			//put 파일 위치만 저장
			map.put(config.getId(), fileName);
		} else {
			//파일 내용 저장
			map.put(config.getId(), data);
		}
		
		return data.length;
	}
	
	private byte[] getContent(Map headerInfo, AttachmentPart part) throws Exception {
		String encoding = (String)headerInfo.get("Content-Transfer-Encoding");
		String content = SoapHelper.getContent(part);
		
		byte[] data = null;
		
		if(encoding.equals("base64")) {
			return BASE64DecoderStream.decode(content.getBytes());
		} else {
			return content.getBytes();
		}
	}
}
