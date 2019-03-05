package net.jdft.delimiter.reader.body;

import java.util.Iterator;

import net.jdft.config.body.DelimiterBodyConfig;
import net.jdft.config.entity.EntityConfig;
import net.jdft.converter.reader.ReadConverterContext;
import net.jdft.converter.reader.body.BodyReader;
import net.jdft.delimiter.reader.DelimiterReadConverterContext;
import net.jdft.delimiter.reader.entity.DelimiterEntityReader;
import net.jdft.delimiter.reader.entity.DelimiterEntityReaderFactory;

public class DelimiterBodyReader implements BodyReader<String>{

	public void read(ReadConverterContext ctx) throws Exception {

		DelimiterBodyConfig config = (DelimiterBodyConfig) ctx.getBodyConfig();
		
		DelimiterReadConverterContext delimiterContext = null;
		if (ctx instanceof DelimiterReadConverterContext) {
			delimiterContext = (DelimiterReadConverterContext) ctx;
		} else {
			delimiterContext = new DelimiterReadConverterContext(ctx.getMap(),
					ctx.getDataConfig(), config.getDelimiter());
		}

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
