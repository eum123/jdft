package net.jdft.soap.reader.body;

import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.AttachmentPart;

import net.jdft.config.body.BodyConfig;
import net.jdft.config.entity.file.FileFieldConfig;
import net.jdft.soap.reader.entity.FileEntityReader;

public class FileBodyReader {
	public static Map read(BodyConfig config, Map headerInfo, AttachmentPart part) throws Exception {

		Map map = new HashMap();

		if (config.getList().get(0) instanceof FileFieldConfig) {

			FileFieldConfig fieldConfig = (FileFieldConfig) config.getList().get(0);

			new FileEntityReader().read(map, headerInfo, fieldConfig, part);

			return map;
		} else {
			throw new IllegalArgumentException("file을 처리하기 위해서 <file id=.../> 의 설정 값이 필요합니다.");
		}
	}

}
