package net.jdft.delimiter.reader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jdft.config.data.DataConfig;
import net.jdft.config.from.DelimiterFromConfig;
import net.jdft.config.from.FromConfig;
import net.jdft.converter.reader.ReadConverterContext;
import net.jdft.converter.reader.Reader;
import net.jdft.delimiter.reader.body.DelimiterBodyReader;
import net.jdft.delimiter.reader.header.DelimiterHeaderReader;

public class DelimiterReader extends AbstractReader implements Reader<byte[]> {
	public Map read(byte[] data, FromConfig fromConfig) throws Exception {
		
		if(fromConfig.getCharset() != null) {
			data = new String((byte[]) data, fromConfig.getCharset()).getBytes();
		}

		DelimiterFromConfig delimiterFromConfig = (DelimiterFromConfig) fromConfig;

		List<DataConfig> list = fromConfig.getDataConfigList();

		Map map = new HashMap();
		Iterator<DataConfig> it = list.iterator();
		while (it.hasNext()) {
			DataConfig config = it.next();
			validate(config);

			ReadConverterContext ctx = new DelimiterReadConverterContext(data, config,
					delimiterFromConfig.getDelimiter());

			new DelimiterHeaderReader().read(ctx);
			

			if (ctx.getBodyConfig() != null) {
				new DelimiterBodyReader().read(ctx);
			}
			map.putAll(ctx.getMap());
		}

		return map;
	}
}
