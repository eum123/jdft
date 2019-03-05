package net.jdft.converter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PathLoader {
	public static String[] load(String id) {
		String classPath = System.getProperty(id);

		List list = new ArrayList();
		String[] jars = classPath.split(File.pathSeparator);
		for (int i = 0; i < jars.length; i++) {
			if (jars[i].indexOf(".jar") > 0) {
				continue;
			} else {
				list.add(jars[i]);
			}
		}
		return (String[])list.toArray(new String[list.size()]);
	}
}
