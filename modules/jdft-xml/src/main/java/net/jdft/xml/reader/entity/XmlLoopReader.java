package net.jdft.xml.reader.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.loop.XmlLoopConfig;
import net.jdft.constants.DataFormatConstants;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.xml.util.XmlHelper;

import org.apache.commons.jxpath.JXPathContext;
import org.jdom.Element;

/**
 * List > Map structure
 * 
 * @author manjin
 * 
 */
public class XmlLoopReader implements XmlEntityReader<JXPathContext> {
	public int read(Map map, JXPathContext ctx, String parentPath, EntityConfig config)
			throws Exception {

		XmlLoopConfig xmlConfig = (XmlLoopConfig) config;
		xmlConfig.validate();
		List<Element> childList = getChildren(ctx, parentPath, xmlConfig);

		int size = childList.size();
		List dataList = new LinkedList();
		
		for (int i = 0; i < size; i++) {

			if (xmlConfig.getEntities().size() == 0) {
				//child element가 없는 경우.
				dataList.add(childList.get(i).getText());
			} else {
				Map subMap = new HashMap();
				Iterator<EntityConfig> it = xmlConfig.getEntities().iterator();

				while (it.hasNext()) {
					EntityConfig entityConfig = it.next();
					XmlEntityReader reader = XmlEntityReaderFactory.newInstance(
							DataFormatConstants.XML, entityConfig);
					String path =createParentPath(i + 1, getPath(parentPath, config));
					// xpath의 반복 부분의 index는 1부터 시작이다. /home/loop[1]/name
					reader.read(subMap, ctx, path ,entityConfig);
				}
				dataList.add(subMap);
			}
		}

		map.put(xmlConfig.getId(), dataList);
		return 0;
	}

	/**
	 * 반복 element를 구한다.
	 * 
	 * @param ctx
	 * @param parentPath
	 * @param config
	 * @return
	 */
	private List<Element> getChildren(JXPathContext ctx, String parentPath, EntityConfig config) {
		String path =  getPath(parentPath, config);
		return ctx.selectNodes(path);
	}

	/**
	 * 현재 path를 만든다
	 * 
	 * @param parentPath
	 * @param config
	 * @return
	 */
	private String getPath(String parentPath, EntityConfig config) {
		String configXPath = config.getProperty(FieldConfigConstants.XPATH);
		if (parentPath == null) {
			return configXPath;
		}
		int depthOfParentPath = XmlHelper.calcDepth(parentPath);
		int offset = XmlHelper.findOffset(configXPath, depthOfParentPath+1);
		
		//return parentPath + configXPath.substring(configXPath.lastIndexOf("/"));
		return parentPath+configXPath.substring(offset, configXPath.length());
	}

	/**
	 * entity에게 넘겨줄 parent path를 구성한다. xpath에 맞게 구성한다.
	 * 
	 * @param index
	 * @param path
	 * @return
	 */
	private String createParentPath(int index, String path) {
		return path + "[" + index + "]";
	}
}
