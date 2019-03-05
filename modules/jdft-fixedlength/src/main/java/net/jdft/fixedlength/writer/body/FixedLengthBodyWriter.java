package net.jdft.fixedlength.writer.body;

import java.util.Iterator;

import net.jdft.config.body.BodyConfig;
import net.jdft.config.entity.EntityConfig;
import net.jdft.converter.writer.WriteConverterContext;
import net.jdft.converter.writer.body.BodyWriter;
import net.jdft.fixedlength.writer.entity.FixedLengthEntityWriter;
import net.jdft.fixedlength.writer.entity.FixedLengthWriterFactory;
import net.jdft.util.BytesUtil;

public class FixedLengthBodyWriter implements BodyWriter<byte[]> {

	public byte[] write(WriteConverterContext ctx) throws Exception {

		BodyConfig config = ctx.getBodyConfig();

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
