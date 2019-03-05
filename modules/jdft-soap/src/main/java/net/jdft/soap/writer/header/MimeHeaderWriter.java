package net.jdft.soap.writer.header;

import java.util.Iterator;
import java.util.Map;

import javax.xml.soap.SOAPPart;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.header.HeaderConfig;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.CastUtil;

public class MimeHeaderWriter {

	public static void write(Map map, HeaderConfig config, SOAPPart part) throws Exception {

		Iterator<EntityConfig> it = config.getList().iterator();
		while (it.hasNext()) {
			EntityConfig entityConfig = it.next();

			String charset = entityConfig.getProperty(FieldConfigConstants.CHARSET);

			Object data = map.get(entityConfig.getId());
			
			//멀티 파트일 경우 mimeheader에 동일한 이름을 입력을 할수 있게 name이라는 attribute 추가함
			String name = entityConfig.getProperty(FieldConfigConstants.NAME);
			if(name == null) {
				part.addMimeHeader(entityConfig.getId(), CastUtil.castString("", data, charset));
			} else {

				part.addMimeHeader(name, CastUtil.castString("", data, charset));
			}

		}
	}

}
