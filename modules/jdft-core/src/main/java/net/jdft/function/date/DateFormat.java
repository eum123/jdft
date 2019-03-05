package net.jdft.function.date;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateFormat {
	public String format(String pattern, Date date) {
		return format(pattern, date.getTime());
	}
	
	public String format(String pattern, long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(timestamp);
	}
	
	public String format(String pattern) {
		return format(pattern, System.currentTimeMillis());
	}
}
