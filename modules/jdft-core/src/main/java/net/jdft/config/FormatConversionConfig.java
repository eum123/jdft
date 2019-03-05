package net.jdft.config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import net.jdft.constants.ConfigConstants;
import net.jdft.replace.PropertyPlaceholderConfigurer;
import net.jdft.util.NullCheckUtil;
import net.jdft.util.XMLUtil;

import org.jdom.Element;

public class FormatConversionConfig {

	private PoolConfig poolConfig = null;
	private ReloadConfig reloadConfig = null;
	private TransformConfig groupConfig = null;

	public static FormatConversionConfig create(String path)
			throws Exception {
		
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(path));
			return create(in);
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	public static FormatConversionConfig create(InputStream stream)
			throws Exception {
		FormatConversionConfig config = new FormatConversionConfig();
		
		byte[] buffer = new byte[stream.available()];
		stream.read(buffer);

		Element root = XMLUtil.getRootElement(buffer);
		
		//placeholder
		Element placeHolderElement = root.getChild(ConfigConstants.PLACEHOLDER);
		if(placeHolderElement != null) {
			
			PropertyPlaceholderConfigurer configurer = new PropertyPlaceholderConfigurer();
			configurer.setLocation(placeHolderElement.getChild("location").getText());
			String replaceData = configurer.replacePlaceholders(new String(buffer));
			
			root = XMLUtil.getRootElement(replaceData.getBytes());
		}
		
		config.poolConfig = createPoolConfig(root.getChild(ConfigConstants.POOL));
		config.reloadConfig = createReloadConfig(root.getChild(ConfigConstants.RELOAD));
		config.groupConfig = createTransformConfig(root);
				

		return config;
	}
	
	private static PoolConfig createPoolConfig(Element element) {
		if (element == null) {
			return PoolConfig.create();
		} else {
			return PoolConfig.create(element);
		}
	}
	
	private static ReloadConfig createReloadConfig(Element element) {
		if (element == null) {
			return ReloadConfig.create();
		} else {
			return ReloadConfig.create(element);
		}
	}
	
	private static TransformConfig createTransformConfig(Element root) throws Exception {
		Element transformElement = root.getChild(ConfigConstants.TRANSFORM);
		NullCheckUtil.isNullThrowException(transformElement,
				"not found group element in " + root.getName());
		
		return TransformConfig.create(transformElement);
	}

	public PoolConfig getPoolConfig() {
		return poolConfig;
	}

	public ReloadConfig getReloadConfig() {
		return reloadConfig;
	}

	public TransformConfig getGroupConfig() {
		return groupConfig;
	}
}
