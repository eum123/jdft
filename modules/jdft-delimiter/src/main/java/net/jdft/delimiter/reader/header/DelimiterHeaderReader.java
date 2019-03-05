package net.jdft.delimiter.reader.header;

import java.util.Iterator;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.header.DelimiterHeaderConfig;
import net.jdft.converter.reader.ReadConverterContext;
import net.jdft.converter.reader.header.HeaderReader;
import net.jdft.delimiter.reader.DelimiterReadConverterContext;
import net.jdft.delimiter.reader.entity.DelimiterEntityReader;
import net.jdft.delimiter.reader.entity.DelimiterEntityReaderFactory;

public class DelimiterHeaderReader implements HeaderReader<byte[]> {

	public void read(ReadConverterContext ctx) throws Exception {
		DelimiterReadConverterContext delimiterContext = (DelimiterReadConverterContext) ctx;

		DelimiterHeaderConfig config = (DelimiterHeaderConfig) ctx.getHeaderConfig();

		String delimiter = delimiterContext.getDelimiter();
		if (config.getDelimiter() != null) {
			delimiter = config.getDelimiter();
		}

		Iterator<EntityConfig> it = config.getList().iterator();
		while (it.hasNext()) {
			EntityConfig entityConfig = it.next();
			DelimiterEntityReader reader = DelimiterEntityReaderFactory.newInstance(
					config.getFormat(), entityConfig, delimiter);
			int index = reader.read(ctx.getMap(), ctx.getSource(), entityConfig, ctx.getOffset());
			ctx.increaseOffset(index);
		}

	}

}
