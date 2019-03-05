package net.jdft.fixedlength.reader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jdft.config.data.DataConfig;
import net.jdft.config.from.FromConfig;
import net.jdft.config.from.GeneralFromConfig;
import net.jdft.converter.reader.ReadConverterContext;
import net.jdft.converter.reader.Reader;
import net.jdft.fixedlength.reader.body.BodyReaderFactory;
import net.jdft.fixedlength.reader.header.FixedLengthHeaderReader;

public class FixedLengthReader extends AbstractReader implements Reader<byte[]> {
	public Map read(byte[] data, FromConfig fromConfig) throws Exception {
		
		// TODO  오류 발생 부분 수정필요
		/*
		if(fromConfig.getCharset() != null) {
			data = new String((byte[]) data, fromConfig.getCharset()).getBytes();
		}
		*/
		List<DataConfig> list = fromConfig.getDataConfigList();
		
		Map map = new HashMap();
		Iterator<DataConfig> it = list.iterator();
		while (it.hasNext()) {
			DataConfig config = it.next();
			validate(config);

			ReadConverterContext ctx = new ReadConverterContext(data, config);

			new FixedLengthHeaderReader().read(ctx);;
		
			if (ctx.getBodyConfig() != null) {
				BodyReaderFactory.newInstance(ctx.getBodyConfig()).read(ctx);
			}
			map.putAll(ctx.getMap());
		}
		
		return map;
	}

	public Map read(String data, FromConfig fromConfig) throws Exception {
		return read(data.getBytes(), fromConfig);
	}

	public Map read(String data, String charsetName, FromConfig fromConfig) throws Exception {
		return read(data.getBytes(charsetName), fromConfig);
	}
}
