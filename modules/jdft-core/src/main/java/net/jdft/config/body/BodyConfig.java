package net.jdft.config.body;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.jdft.config.FormatInfo;
import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.EntityConfigFactory;
import net.jdft.config.entity.resource.ImportConfig;
import net.jdft.constants.ConfigConstants;

import org.jdom.Element;

public class BodyConfig {
	private String id = null;
	private String format = null;
	private List<EntityConfig> list = new LinkedList<EntityConfig>();
	
	protected BodyConfig(FormatInfo format, Element element) {
		FormatInfo newFormat = format;
		id = element.getAttributeValue(ConfigConstants.ID);
		this.format = element.getAttributeValue(ConfigConstants.FORMAT);
		if(this.format == null) {
			this.format = format.format;			
		} else {
			newFormat = new FormatInfo();
			newFormat.format = this.format;
			newFormat.charset = format.charset;
		}
		
		Iterator<Element> it = element.getChildren().iterator();
		while (it.hasNext()) {
			EntityConfig config = EntityConfigFactory.newInstance(newFormat, it.next());
			if(config instanceof ImportConfig) {
				//import 일때 전체 entiticonfig를 저장한다.
				ImportConfig importConfig = (ImportConfig)config;
				list.addAll(importConfig.getEntities());
			} else {
				list.add(config);
			}
		}
	}
	
	public String getFormat() {
		return format;
	}

	public List<EntityConfig> getList() {
		return list;
	}

	public String getId() {
		return id;
	}
	
	
}
