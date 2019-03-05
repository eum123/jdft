package net.jdft.delimiter.writer.body;

import java.util.Iterator;

import net.jdft.config.body.DelimiterBodyConfig;
import net.jdft.config.entity.EntityConfig;
import net.jdft.converter.writer.WriteConverterContext;
import net.jdft.converter.writer.body.BodyWriter;
import net.jdft.delimiter.writer.DelimiterWriteConverterContext;
import net.jdft.delimiter.writer.entity.DelimiterEntityWriter;
import net.jdft.delimiter.writer.entity.DelimiterWriterFactory;
import net.jdft.util.BytesUtil;

public class DelimiterBodyWriter implements BodyWriter<byte[]> {

	public byte[] write(WriteConverterContext ctx) throws Exception {

		DelimiterBodyConfig config = (DelimiterBodyConfig) ctx.getBodyConfig();

		DelimiterWriteConverterContext delimiterContext = null;
		if (ctx instanceof DelimiterWriteConverterContext) {
			delimiterContext = (DelimiterWriteConverterContext) ctx;
		} else {
			delimiterContext = new DelimiterWriteConverterContext(ctx.getMap(),
					ctx.getDataConfig(), config.getDelimiter());
		}

		String delimiter = delimiterContext.getDelimiter();
		if (config.getDelimiter() != null) {
			delimiter = config.getDelimiter();
		}
		byte[] data = new byte[] {};
		Iterator<EntityConfig> it = config.getList().iterator();
		while (it.hasNext()) {
			EntityConfig entityConfig = it.next();
			DelimiterEntityWriter<byte[]> writer = DelimiterWriterFactory.newInstance(
					config.getFormat(), entityConfig, delimiter);
			data = BytesUtil.append(data, writer.write(ctx.getMap(), entityConfig));
		}
		return data;
	}

}
