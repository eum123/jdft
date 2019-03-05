package net.jdft.soap.reader.header;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.soap.MimeHeader;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.header.HeaderConfig;
import net.jdft.constants.FieldConfigConstants;

public class MimeHeaderReader {

	public static Map read(HeaderConfig config, Iterator<MimeHeader> mimeHeaders) throws Exception {
		
		Map map = new HashMap();
		while (mimeHeaders.hasNext()) {
			MimeHeader mimeHeader = mimeHeaders.next();
			Iterator<EntityConfig> list = config.getList().iterator();
			while(list.hasNext()) {
				EntityConfig entityConfig = list.next();
				
				
				//멀티 파트일 경우 mimeheader에 동일한 이름을 입력을 할수 있게 name이라는 attribute 추가함
				String name = entityConfig.getProperty(FieldConfigConstants.NAME);
				if(name == null) {
					if (entityConfig.getId().equals(mimeHeader.getName())) {
						map.put(entityConfig.getId(), mimeHeader.getValue());
					}
				} else {
					if (name.equals(mimeHeader.getName())) {
						map.put(entityConfig.getId(), mimeHeader.getValue());
					}
				}
			}			
		}
		
		return map;
	}

}
