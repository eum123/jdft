package net.jdft.fixedlength.writer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jdft.config.body.BodyConfig;
import net.jdft.config.data.DataConfig;
import net.jdft.config.to.ToConfig;
import net.jdft.converter.writer.WriteConverterContext;
import net.jdft.converter.writer.Writer;
import net.jdft.fixedlength.writer.body.BodyWriterFactory;
import net.jdft.fixedlength.writer.header.FixedLengthHeaderWriter;
import net.jdft.util.BytesUtil;

public class FixedLengthWriter extends AbstractWriter implements Writer<byte[]> {
	public byte[] write(Map map, ToConfig toConfig) throws Exception {
		
		List<DataConfig> list = toConfig.getDataConfigList();

		byte[] data = {};
		Iterator<DataConfig> it = list.iterator();
		while (it.hasNext()) {
			DataConfig config = it.next();
			validate(config);

			WriteConverterContext ctx = new WriteConverterContext(map, config);

			BodyConfig bodyConfig = ctx.getBodyConfig();
			byte[] bodyData = (byte[]) BodyWriterFactory.newInstance(bodyConfig).write(ctx);

			// header에서 body의 길이를 필요할 경우 사용하기 위해 body id로 body 데이터를 저장한다.
			map.put(bodyConfig.getId(), bodyData);

			byte[] headerData = new FixedLengthHeaderWriter().write(ctx);
			
			data = BytesUtil.append(data, BytesUtil.append(headerData, bodyData));
		}
		// TODO  오류 발생 부분 수정필요
		/*
		if(toConfig.getCharset() != null) {
			data = new String(data, toConfig.getCharset()).getBytes(toConfig.getCharset());
		}
		*/
		return data;
	}
}
