package net.jdft.delimiter.writer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jdft.config.body.BodyConfig;
import net.jdft.config.data.DataConfig;
import net.jdft.config.to.DelimiterToConfig;
import net.jdft.config.to.ToConfig;
import net.jdft.converter.writer.Writer;
import net.jdft.delimiter.writer.body.DelimiterBodyWriter;
import net.jdft.delimiter.writer.header.DelimiterHeaderWriter;
import net.jdft.util.BytesUtil;

public class DelimiterWriter extends AbstractWriter implements Writer<byte[]> {
	public byte[] write(Map map, ToConfig toConfig) throws Exception {
		DelimiterToConfig delimiterToConfig = (DelimiterToConfig) toConfig;

		List<DataConfig> list = toConfig.getDataConfigList();

		byte[] data = {};
		Iterator<DataConfig> it = list.iterator();
		while (it.hasNext()) {
			DataConfig config = it.next();
			validate(config);

			DelimiterWriteConverterContext ctx = new DelimiterWriteConverterContext(map, config,
					delimiterToConfig.getDelimiter());

			BodyConfig bodyConfig = ctx.getBodyConfig();
			byte[] bodyData = new DelimiterBodyWriter().write(ctx);

			//마지막 구분자 삭제 여부를 결정한다.
			if (delimiterToConfig.isRemoveLastDelimiter()) {
				bodyData = BytesUtil.split(bodyData, 0, bodyData.length
						- delimiterToConfig.getDelimiter().getBytes().length);
			}

			// header에서 body의 길이를 필요할 경우 사용하기 위해 body id로 body 데이터를 저장한다.
			map.put(bodyConfig.getId(), bodyData);

			byte[] headerData = new DelimiterHeaderWriter().write(ctx);
			data = BytesUtil.append(data, BytesUtil.append(headerData, bodyData));

		}
		
		if(toConfig.getCharset() != null) {
			data = new String(data, toConfig.getCharset()).getBytes(toConfig.getCharset());
		}
		
		return data;
	}
}
