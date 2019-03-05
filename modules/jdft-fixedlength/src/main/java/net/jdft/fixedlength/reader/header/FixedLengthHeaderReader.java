package net.jdft.fixedlength.reader.header;

import java.util.Iterator;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.header.HeaderConfig;
import net.jdft.converter.reader.ReadConverterContext;
import net.jdft.converter.reader.header.HeaderReader;
import net.jdft.fixedlength.reader.entity.FixedLengthEntityReader;
import net.jdft.fixedlength.reader.entity.FixedLengthEntityReaderFactory;

public class FixedLengthHeaderReader implements HeaderReader<byte[]> {

	public void read(ReadConverterContext ctx) throws Exception {
		HeaderConfig config = ctx.getHeaderConfig();

		int totalLength = 0;
		Iterator<EntityConfig> it = config.getList().iterator();
		while (it.hasNext()) {
			EntityConfig entityConfig = it.next();
			FixedLengthEntityReader reader = FixedLengthEntityReaderFactory.newInstance(
					config.getFormat(), entityConfig);
			int index = reader.read(ctx.getMap(), ctx.getSource(),
					entityConfig, ctx.getOffset());
			ctx.increaseOffset(index);
		}

	}

}
