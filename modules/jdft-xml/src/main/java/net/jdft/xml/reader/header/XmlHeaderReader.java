package net.jdft.xml.reader.header;

import java.util.Iterator;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.header.XmlHeaderConfig;
import net.jdft.converter.reader.ReadConverterContext;
import net.jdft.converter.reader.header.HeaderReader;
import net.jdft.xml.reader.XmlReadConverterContext;
import net.jdft.xml.reader.entity.XmlEntityReader;
import net.jdft.xml.reader.entity.XmlEntityReaderFactory;

public class XmlHeaderReader implements HeaderReader<byte[]> {

	public void read(ReadConverterContext ctx) throws Exception {
		XmlReadConverterContext xmlReadContext = (XmlReadConverterContext) ctx;

		XmlHeaderConfig config = (XmlHeaderConfig) ctx.getHeaderConfig();

		Iterator<EntityConfig> it = config.getList().iterator();
		while (it.hasNext()) {
			EntityConfig entityConfig = it.next();
			XmlEntityReader reader = XmlEntityReaderFactory.newInstance(config.getFormat(),
					entityConfig);

			// 처음 설정이여서 parentPath를 null로 설정한다.
			reader.read(ctx.getMap(), xmlReadContext.getJxPathContext(), null, entityConfig);
		}
		ctx.increaseOffset(0);

	}

}
