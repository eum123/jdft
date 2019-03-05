package net.jdft.delimiter.util;

import net.jdft.util.BytesUtil;

public class DelimiterHelper {
	/**
	 * byte[]에서 첫번째 구분자 값을 구한다.
	 * 
	 * @param src
	 * @param start
	 * @param delimiter
	 * @return
	 */
	public static byte[] getFirstData(byte[] src, int start, String delimiter) {
		byte[] delimBytes = delimiter.getBytes();

		boolean isMatch = true;
		int delimIndex = -1; // isMatch기본 값이 true이므로 index정보로 match 여부를 재 확인
		
		if(src.length < start) {
			return null;
		}

		for (int i = start; i < src.length; i++) {
			if (src[i] == delimBytes[0]) {
				// 전체 길이 체크
				if (src.length < i + delimBytes.length) {
					continue;
				}

				for (int j = 0; j < delimBytes.length; j++) {
					isMatch = isMatch && src[i + j] == delimBytes[j];
				}

				if (isMatch) {
					// 구분자에 일지하는 위치를 구하여 데이터를 저장하고 종료한다.
					delimIndex = i;
					break;
				}
			}
		}
		if (isMatch && delimIndex >= 0) {
			return BytesUtil.split(src, start, delimIndex - start);
		} else {
			return BytesUtil.split(src, start, src.length - start);
		}

	}
}
