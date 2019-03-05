package net.jdft.delimiter.writer.header;

import java.util.Iterator;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.header.DelimiterHeaderConfig;
import net.jdft.converter.writer.WriteConverterContext;
import net.jdft.converter.writer.header.HeaderWriter;
import net.jdft.delimiter.writer.DelimiterWriteConverterContext;
import net.jdft.delimiter.writer.entity.DelimiterEntityWriter;
import net.jdft.delimiter.writer.entity.DelimiterWriterFactory;
import net.jdft.util.BytesUtil;

public class DelimiterHeaderWriter implements HeaderWriter<byte[]> {

	public byte[] write(WriteConverterContext ctx) throws Exception {
		DelimiterWriteConverterContext delimiterContext = (DelimiterWriteConverterContext) ctx;

		DelimiterHeaderConfig config = (DelimiterHeaderConfig) ctx.getHeaderConfig();

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
