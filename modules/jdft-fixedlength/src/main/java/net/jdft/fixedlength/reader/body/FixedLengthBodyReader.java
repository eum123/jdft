package net.jdft.fixedlength.reader.body;

import java.util.Iterator;

import net.jdft.config.body.BodyConfig;
import net.jdft.config.entity.EntityConfig;
import net.jdft.converter.reader.ReadConverterContext;
import net.jdft.converter.reader.body.BodyReader;
import net.jdft.fixedlength.reader.entity.FixedLengthEntityReader;
import net.jdft.fixedlength.reader.entity.FixedLengthEntityReaderFactory;

public class FixedLengthBodyReader implements BodyReader<byte[]> {

	public void read(ReadConverterContext ctx) throws Exception {
		BodyConfig config = ctx.getBodyConfig();
		
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
