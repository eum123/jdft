package net.jdft.soap.writer.body;

import java.util.Map;

import javax.xml.soap.AttachmentPart;

import net.jdft.config.data.DataConfig;
import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.file.FileFieldConfig;
import net.jdft.soap.writer.entity.FileEntityWriter;

public class FileBodyWriter {
	public static void write(Map data, DataConfig config, AttachmentPart part) throws Exception {

		EntityConfig fieldConfig = config.getBodyConfig().getList().get(0);

		if (fieldConfig instanceof FileFieldConfig) {

			new FileEntityWriter().write(data, config, (FileFieldConfig) fieldConfig, part);

		} else {
			throw new IllegalArgumentException("file을 처리하기 위해서 <file id=.../> 의 설정 값이 필요합니다.");
		}
	}
}
