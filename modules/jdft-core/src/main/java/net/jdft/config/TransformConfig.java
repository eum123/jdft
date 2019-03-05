package net.jdft.config;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.jdft.constants.ConfigConstants;
import net.jdft.util.NullCheckUtil;
import net.jdft.util.XMLUtil;

import org.jdom.Element;
import org.jdom.JDOMException;

public class TransformConfig {
	// 일반 저장
	private Map<String, ConversionConfig> conversionGroup = Collections
			.synchronizedMap(new HashMap<String, ConversionConfig>());
	// 정규표현식 ID 저장
	private Map<String, ConversionConfig> regexConversionGroup = Collections
			.synchronizedMap(new HashMap<String, ConversionConfig>());

	public static TransformConfig create(Element element) throws IOException, JDOMException {
		TransformConfig config = new TransformConfig();

		Iterator<Element> childIt = element.getChildren().iterator();
		while (childIt.hasNext()) {
			Element childElement = childIt.next();
			String name = childElement.getName();

			if (name.equals(ConfigConstants.IMPORT)) {
				addImport(config, childElement);
			} else if (name.equals(ConfigConstants.CONVERSION)) {
				ConversionConfig conversionConfig = ConversionConfig.create(childElement);
				putConversion(config, conversionConfig);
			}
		}

		return config;
	}

	private static void addImport(TransformConfig config, Element element) throws IOException,
			JDOMException {
		String resource = element.getAttributeValue(ConfigConstants.RESOURCE);
		NullCheckUtil.isNullThrowException(resource,
				"not found resource attribute in " + element.getName());

		File f = new File(resource);

		if (f.isDirectory()) {
			readDirectory(config, f);
		} else {

			Element conversionElement = XMLUtil.getRootElement(resource);

			ConversionConfig conversionConfig = ConversionConfig.create(conversionElement);
			putConversion(config, conversionConfig);
		}
	}

	private static void readDirectory(TransformConfig config, File file) throws IOException,
			JDOMException {
		String[] files = findFileList(file);
		for (int i = 0; i < files.length; i++) {

			Element conversionElement = XMLUtil.getRootElement(files[i]);

			if (conversionElement.getName().equalsIgnoreCase("conversion")) {
				ConversionConfig conversionConfig = ConversionConfig.create(conversionElement);
				putConversion(config, conversionConfig);
			}
		}
	}

	/**
	 * 디렉토리의 xml파일을 모두 구한다.
	 * 
	 * @param file
	 * @return
	 */
	private static String[] findFileList(File file) {
		String[] files = file.list(new FilenameFilter() {
			public boolean accept(File arg0, String arg1) {

				String extension = arg1.substring(arg1.lastIndexOf(".") + 1);
				if (extension.equalsIgnoreCase("xml")) {
					return true;
				}

				return false;
			}
		});

		if (files == null) {
			return null;
		}

		String[] list = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			list[i] = file.getAbsolutePath() + File.separatorChar + files[i];
		}

		return list;
	}

	private static void putConversion(TransformConfig transformConfig, ConversionConfig config) {
		if (config.isRegex()) {
			transformConfig.regexConversionGroup.put(config.getId(), config);
		} else {
			transformConfig.conversionGroup.put(config.getId(), config);
		}

	}

	public ConversionConfig getConversionConfig(String id) {
		// 일반 우선 검사
		ConversionConfig config = conversionGroup.get(id);

		if (config != null) {
			return config;
		}

		// 정규 표현식 검사
		Iterator<String> it = regexConversionGroup.keySet().iterator();
		while (it.hasNext()) {
			String regex = it.next();

			if (id.matches(regex)) {
				config = regexConversionGroup.get(regex);
				break;
			}
		}

		if (config != null) {
			return config;
		}
		throw new IllegalArgumentException("not found ConversionConfig. id:" + id);
	}
	
	/**
	 * 해당 Conversion id 가 존재하는지 확인하는 함수
	 * 단순하게 getConversionConfig 함수의 로직을 재사용
	 * @param id
	 * @return
	 */
	public boolean isExistConversionConfig(String id) {
		// 일반 우선 검사
		ConversionConfig config = conversionGroup.get(id);
		if (config != null) {
			return true;
		}
		// 정규 표현식 검사
		Iterator<String> it = regexConversionGroup.keySet().iterator();
		while (it.hasNext()) {
			String regex = it.next();
			if (id.matches(regex)) {
				config = regexConversionGroup.get(regex);
				break;
			}
		}
		if (config != null) {
			return true;
		}
		return false;
	}
}
