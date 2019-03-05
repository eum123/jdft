package net.jdft.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import net.jdft.constants.DataTypeConstants;
import net.jdft.constants.EncodingConstants;

public class CastUtil {
	public static Object cast(String encoding, String dataType, Object data)
			throws UnsupportedEncodingException {

		return cast(encoding, dataType, data, null);
	}

	/**
	 * 지정한 dataType으로 형변환 한다.
	 * @param encoding
	 * @param dataType
	 * @param data
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Object cast(String encoding, String dataType, Object data,
			String charset) throws UnsupportedEncodingException {
		if (dataType.equals(DataTypeConstants.STRING)) {
			return castString(encoding, data, charset);
		} else if (dataType.equals(DataTypeConstants.INTEGER)) {
			return castInteger(encoding, data);
		} else if (dataType.equals(DataTypeConstants.LONG)) {
			return castLong(encoding, data);
		} else if (dataType.equals(DataTypeConstants.BYTES)) {
			return castBytes(encoding, data, charset);
		} else if( dataType.equals(DataTypeConstants.BIGDECIMAL)) {
			return castBigDecimal(encoding, data);
		} else {
			throw new IllegalArgumentException("not supported type : "
					+ dataType);
		}
	}

	/**
	 * String으로 형변환 한다
	 * @param encoding
	 * @param data
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String castString(String encoding, Object data, String charset)
			throws UnsupportedEncodingException {
		if (data instanceof byte[]) {
			if (charset == null) {
				return new String((byte[]) data);
			} else {
				return new String((byte[]) data, charset);
			}
		} else if (data instanceof Integer) {
			return String.valueOf(((Integer) data).intValue());
		} else if (data instanceof Long) {
			return String.valueOf(((Long) data).longValue());
		} else if (data instanceof String) {
			return (String) data;
		} else {
			throw new IllegalArgumentException("unknown data format : " + data);
		}
	}

	public static Integer castInteger(String encoding, Object data)
			throws UnsupportedEncodingException {
		if (data instanceof byte[]) {
			if(encoding.equals(EncodingConstants.BINARY)) {
				return BytesUtil.toInt((byte[])data);
			}
			// byte[]을 integer로 변경하기 위해 shift 연산을 해야 할지 String으로 변환 해야 할지 알수 없어
			// 지원하지 않는다.
			throw new IllegalArgumentException("not supperted data format : "
					+ data);
		} else if (data instanceof Integer) {
			return (Integer) data;
		} else if (data instanceof Long) {
			return ((Long) data).intValue();
		} else if (data instanceof String) {
			return Integer.parseInt(((String) data).trim());
		} else {
			throw new IllegalArgumentException("unknown data format : " + data);
		}
	}

	public static Long castLong(String encoding, Object data)
			throws UnsupportedEncodingException {
		if (data instanceof byte[]) {
			if(encoding.equals(EncodingConstants.BINARY)) {
				return BytesUtil.toLong((byte[])data);
			}
			
			// byte[]을 Long로 변경하기 위해 shift 연산을 해야 할지 String으로 변환 해야 할지 알수 없어
			// 지원하지 않는다.
			throw new IllegalArgumentException("not supperted data format : "
					+ data);
		} else if (data instanceof Integer) {
			return new Long((Integer) data);
		} else if (data instanceof Long) {
			return (Long) data;
		} else if (data instanceof String) {
			return Long.parseLong(((String) data).trim());
		} else {
			throw new IllegalArgumentException("unknown data format : " + data);
		}
	}
	
	public static BigDecimal castBigDecimal(String encoding, Object data)
			throws UnsupportedEncodingException {
		if (data instanceof byte[]) {
			return new BigDecimal(new String((byte[])data).trim());
		} else if (data instanceof Integer) {
			return new BigDecimal((Integer) data);
		} else if (data instanceof Long) {
			return new BigDecimal((Long) data);
		} else if (data instanceof String) {
			return new BigDecimal(((String) data).trim());
		} else {
			throw new IllegalArgumentException("unknown data format : " + data);
		}
	}

	public static byte[] castBytes(String encoding, Object data, String charset)
			throws UnsupportedEncodingException {
		if (data instanceof byte[]) {

			return (byte[]) data;

		} else if (data instanceof Integer) {
			if(encoding.equals(EncodingConstants.BINARY)) {
				return BytesUtil.toBytes((Integer)data);
			}
			// byte[]을 Long로 변경하기 위해 shift 연산을 해야 할지 String으로 변환 해야 할지 알수 없어
			// 지원하지 않는다.
			throw new IllegalArgumentException("not supperted data format : "
					+ data);
		} else if (data instanceof Long) {
			if(encoding.equals(EncodingConstants.BINARY)) {
				return BytesUtil.toBytes((Long)data);
			}
			// byte[]을 Long로 변경하기 위해 shift 연산을 해야 할지 String으로 변환 해야 할지 알수 없어
			// 지원하지 않는다.
			throw new IllegalArgumentException("not supperted data format : "
					+ data);
		} else if (data instanceof String) {
			return BytesUtil.toBytes((String)data, charset);
		} else {
			throw new IllegalArgumentException("unknown data format : " + data.getClass());
		}
	}
}
