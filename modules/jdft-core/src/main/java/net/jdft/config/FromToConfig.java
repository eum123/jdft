package net.jdft.config;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.jdft.config.data.DataConfig;
import net.jdft.config.data.DataConfigFactory;
import net.jdft.constants.ConfigConstants;

import org.jdom.Element;

public class FromToConfig {
	private String format;
	private String charset;
	
	private List<DataConfig> list = new LinkedList<DataConfig>();
	
	public FromToConfig(Element element) {
		FormatInfo fInfo = new FormatInfo();
		format = element.getAttributeValue(ConfigConstants.FORMAT);
		
		if(format == null) {
			throw new IllegalArgumentException("format attribute는 필수임");
		}
		
		charset = element.getAttributeValue(ConfigConstants.CHARSET);
		
		fInfo.format = format;
		fInfo.charset = charset;
		
		Iterator<Element> it = element.getChildren(ConfigConstants.DATA).iterator();
		while(it.hasNext()) {
			list.add(DataConfigFactory.newInstance(fInfo, it.next()));
		}
		
	}

	public List<DataConfig> getDataConfigList() {
		return list;
	}
	
	public DataConfig getFirstDataConfig() {
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	public String getFormat() {
		return format;
	}

	public String getCharset() {
		return charset;
	}

}
