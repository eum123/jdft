package net.jdft.fixedlength.writer.header;

import java.util.Iterator;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.header.HeaderConfig;
import net.jdft.converter.writer.WriteConverterContext;
import net.jdft.converter.writer.header.HeaderWriter;
import net.jdft.fixedlength.writer.entity.FixedLengthEntityWriter;
import net.jdft.fixedlength.writer.entity.FixedLengthWriterFactory;
import net.jdft.util.BytesUtil;

public class FixedLengthHeaderWriter implements HeaderWriter<byte[]> {

	public byte[] write(WriteConverterContext ctx) throws Exception {

		HeaderConfig config = ctx.getHeaderConfig();

		byte[] data = new byte[] {};

		Iterator<EntityConfig> it = config.getList().iterator();
		while (it.hasNext()) {
			EntityConfig entityConfig = it.next();
			FixedLengthEntityWriter<byte[]> writer = FixedLengthWriterFactory
					.newInstance(config.getFormat(), entityConfig);
			data = BytesUtil.append(data,
					writer.write(ctx.getMap(), entityConfig));
		}

		return data;
	}

}
