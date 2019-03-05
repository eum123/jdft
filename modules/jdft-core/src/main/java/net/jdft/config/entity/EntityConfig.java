package net.jdft.config.entity;

public interface EntityConfig {
	public String getId();

	public String getName();
	
	/**
	 * 추가 설정 값을 구한다.
	 * @param key FieldConfigConstants의 static 변수
	 * @return
	 */
	public String getProperty(String key);
	
	public void validate();
}
