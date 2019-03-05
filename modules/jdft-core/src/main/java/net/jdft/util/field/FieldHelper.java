package net.jdft.util.field;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import net.jdft.config.ConversionConfig;
import net.jdft.config.entity.EntityConfig;
import net.jdft.constants.AlignmentConstants;
import net.jdft.constants.EncodingConstants;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.expression.JEXLExpression;
import net.jdft.util.BytesUtil;
import net.jdft.util.CastUtil;
import net.jdft.util.ObjectHelper;

/**
 * field 공통 utility
 * 
 * @author manjin
 * 
 */
public class FieldHelper {
	
	public static boolean hasContainSize(EntityConfig config) {
		return config.getProperty(FieldConfigConstants.SIZE) == null ? true : false;
	}

	/**
	 * size를 구한다.<br>
	 * 수식 연산 가능함.
	 * 
	 * @param map
	 * @param config
	 * @return
	 */
	public static int getSize(Map map, EntityConfig config) {
		String size = config.getProperty(FieldConfigConstants.SIZE).trim();
		
		int len = getSize(map, size);
		
		if(len < 0 ) {
			throw new IllegalArgumentException(config.getId() + "의 size가 0보다 작음. " + map);
		}
		
		return len;
	}
	
	public static int getSize(Map map, EntityConfig config, int defaultSize) {
		String size = config.getProperty(FieldConfigConstants.SIZE);
		if(size == null) {
			return defaultSize;
		} else {
			return getSize(map, size.trim());
		}
	}

	/**
	 * 설정 정보(깅이, 정렬)에 맞게 데이터를 가공하여 반환한다.
	 * default value가 있으면 이를 이용하여 가용한다.
	 * @param map
	 * @param value
	 *            가공할 값
	 * @param config
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getDecoratedValue(Map map, String value, EntityConfig config)
			throws UnsupportedEncodingException {

		String defaultValue = getDefaultValue(map, config);
		if (defaultValue == null) {
			return decorate(value, map, config);
		} else {
			return decorate(defaultValue, map, config);
		}

	}

	/**
	 * 설정 정보(길이, 정렬)에 맞게 데이터를 가공하여 반환한다.
	 * default value가 있으면 이를 이용하여 가용한다.
	 * @param map
	 * @param value
	 *            가공할 값
	 * @param config
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getDecoratedValue(Map map, byte[] value, EntityConfig config)
			throws UnsupportedEncodingException {
		
		String defaultValue = getDefaultValue(map, config);
		if (defaultValue == null) {
			return decorate(value, map, config);
		} else {
			return decorate(defaultValue.getBytes(), map, config);
		}
	}

	/**
	 * 데이터를 깅이에 맞게 변환 한다.
	 * 
	 * @param data
	 * @param map
	 * @param config
	 * @return
	 */
	public static byte[] decorate(byte[] data, Map map, EntityConfig config) {
		int size = 0;
		if(data != null)
		{
			size = getSize(map, config, data.length);
		}
		else
		{
			size = getSize(map, config);
			String encoding = config.getProperty(FieldConfigConstants.ENCODING);
			if (encoding.equals(EncodingConstants.CHARACTER)) {
				return BytesUtil.fillLeft(new byte[]{}, size, ' ');
			} else {
				return BytesUtil.fillLeft(new byte[]{}, size, (char) 0);
			}
		}

		String encoding = config.getProperty(FieldConfigConstants.ENCODING);
		String alignement = config.getProperty(FieldConfigConstants.ALIGNMENT);
		if (encoding.equals(EncodingConstants.CHARACTER)) {
			if (alignement.equals(AlignmentConstants.LEFT)) {
				// 왼쪽 정렬
				if (data.length > size) {
					return BytesUtil.split(data, 0, size);
				} else if (data.length < size) {
					return BytesUtil.fillRight(data, size, ' ');
				} else {
					return data;
				}
			} else {
				// 오른쪽 정렬

				if (data.length > size) {
					return BytesUtil.split(data, data.length - size, size);
				} else if (data.length < size) {
					return BytesUtil.fillLeft(data, size, ' ');
				} else {
					return data;
				}
			}
		} else {
			return BytesUtil.split(data, 0, size);
		}
	}

	/**
	 * 데이터를 깅이에 맞게 변환 한다.
	 * 
	 * TODO:charset 설정에 따라 추가 구현 필요
	 * 
	 * @param data
	 * @param map
	 * @param config
	 * @return
	 */
	public static String decorate(String data, Map map, EntityConfig config) {
		//int size = getSize(map, config, data.getBytes().length);

		// TODO:charset 설정에 따라 추가 구현 필요
		byte[] newData = null;
		if(data == null) {
			newData = decorate(new byte[]{}, map, config);
		} else {
			newData = decorate(data.getBytes(), map, config);
		}
		return new String(newData);
	}

	public static int getSize(Map map, String size) {
		if (containReservedKeywork(size)) {

			Object obj = JEXLExpression.execute(map, size);
			if (obj instanceof Integer) {
				return (Integer) obj;
			} else if (obj instanceof String) {
				if(((String)obj).equals("")) {
					return 0;
				}
				return Integer.parseInt((String) obj);
			} else {
				throw new IllegalAccessError("unknown return type : " + obj + ". expect Integer ");
			}
		} else {
			return Integer.parseInt(size.trim());
		}
	}

	/**
	 * 기본값을 구한다.
	 * 
	 * @param map
	 * @param config
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getDefaultValue(Map map, EntityConfig config)
			throws UnsupportedEncodingException {
		String defaultValue = config.getProperty(FieldConfigConstants.VALUE);
		if (defaultValue == null) {
			return defaultValue;
		} else {
			if (containReservedKeywork(defaultValue)) {
				Object obj = JEXLExpression.execute(map, defaultValue);

				return CastUtil.castString(EncodingConstants.CHARACTER, obj, null);
			} else {
				return defaultValue;
			}
		}
	}

	/**
	 * 키워드가 포함되어 있는가를 검사
	 * 
	 * @param data
	 * @return
	 */
	private static boolean containReservedKeywork(String data) {
		int size = ConversionConfig.RESERVED_WORD.length;
		for (int i = 0; i < size; i++) {
			if (data.indexOf(ConversionConfig.RESERVED_WORD[i]) >= 0) {
				return true;
			}
		}
		return false;
	}
}
