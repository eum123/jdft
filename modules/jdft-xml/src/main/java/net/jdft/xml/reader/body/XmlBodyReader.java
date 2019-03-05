package net.jdft.xml.reader.body;

import java.util.Iterator;

import net.jdft.config.body.XmlBodyConfig;
import net.jdft.config.entity.EntityConfig;
import net.jdft.converter.reader.ReadConverterContext;
import net.jdft.converter.reader.body.BodyReader;
import net.jdft.util.BytesUtil;
import net.jdft.util.field.FieldHelper;
import net.jdft.xml.reader.XmlReadConverterContext;
import net.jdft.xml.reader.entity.XmlEntityReader;
import net.jdft.xml.reader.entity.XmlEntityReaderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlBodyReader implements BodyReader<byte[]> {
	private static final Logger log = LoggerFactory.getLogger(XmlBodyReader.class);

	public void read(ReadConverterContext ctx) throws Exception {
		
		XmlBodyConfig config = (XmlBodyConfig) ctx.getBodyConfig();
		
		XmlReadConverterContext xmlReadContext = null;
		if (ctx instanceof XmlReadConverterContext) {
			xmlReadContext = (XmlReadConverterContext) ctx;
		} else {
			//전체 부분에서 xml 부분만 추출해서 저장한다.
			int startIndex = ctx.getOffset();
			int size = FieldHelper.getSize(ctx.getMap(),  config.getSize());
			byte[] xmlBytes = BytesUtil.split((byte[])ctx.getSource(), startIndex, size);
			xmlReadContext = new XmlReadConverterContext((byte[]) xmlBytes,
					ctx.getDataConfig());
		}


		Iterator<EntityConfig> it = config.getList().iterator();
		while (it.hasNext()) {
			EntityConfig entityConfig = it.next();
			XmlEntityReader reader = XmlEntityReaderFactory.newInstance(config.getFormat(),
					entityConfig);

			// 처음 설정이여서 parentPath를 null로 설정한다.
			reader.read(ctx.getMap(), xmlReadContext.getJxPathContext(), null, entityConfig);
		}

		if (config.getSize() == null) {
			// TODO 사이즈가 null인데 설정이 필요하면 이부분을 구현한다.
		} else {
			int size = FieldHelper.getSize(ctx.getMap(), config.getSize());
			ctx.increaseOffset(size);
		}
		
		ctx.getMap().putAll(xmlReadContext.getMap());
	}
}
