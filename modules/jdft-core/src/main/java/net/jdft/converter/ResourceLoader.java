package net.jdft.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceLoader {
	
	private static final transient Logger log = LoggerFactory
			.getLogger(ResourceLoader.class);
	
	
	public static Properties load(String uri) throws Exception {
		File file = new File(uri);
		ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
		try {
			ZipEntry entry = zis.getNextEntry();
			
			while (entry != null) {
				String name = entry.getName();
				if (name.equals(ConverterRegistry.META_INF_SERVICES)) {
					return readFile(file, entry);
				}
				entry = zis.getNextEntry();
			}
			
			return null;
		} finally {
			if (zis != null) {
				zis.close();
			}
		}
	}
	
	private static Properties readFile(File file, ZipEntry entry) throws Exception {
		JarFile jarFile = new JarFile(file);
		InputStream in = null;
		try {
			in = jarFile.getInputStream(entry);
			Properties properties = new Properties();
			properties.load(in);
			
			return properties;
		} finally {
			if(in != null) {
				in.close();
			}
		}
	}
}
