package net.jdft.util.field;

import net.jdft.util.ObjectHelper;

public class XmlFieldHelper {
	public static String[] getChildNodeList(String parentPath, String path) {
		return ObjectHelper.removeStartingCharacters(ObjectHelper.after(path, parentPath), '/')
				.split("/");
	}
	
}
