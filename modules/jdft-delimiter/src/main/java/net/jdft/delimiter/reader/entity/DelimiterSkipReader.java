package net.jdft.delimiter.reader.entity;

import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.delimiter.util.DelimiterHelper;

public class DelimiterSkipReader implements DelimiterEntityReader<byte[]>{
	
	private final String delimiter;
	
	public DelimiterSkipReader(String delimiter) {
		this.delimiter = delimiter;
	}
	
	public int read(Map map, byte[] src, EntityConfig config, int offset)
			throws Exception {

		byte[] data = DelimiterHelper.getFirstData(src, offset, delimiter);
		
		//다음 시작 위치를 정하기 위해 구분자의 길이를 추가하여 반환한다.
				return data.length + delimiter.getBytes().length;
	}
}
