package net.jdft.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converter를 구현한 class를 생성하여 등록한다.
 * 
 * @author manjin
 * 
 */
public class ConverterRegistry {
	private static final transient Logger log = LoggerFactory
			.getLogger(ConverterRegistry.class);

	/*
	public static final String META_INF_SERVICES = "META-INF" + File.separator
			+ "services" + File.separator + "net" + File.separator + "jdft"
			+ File.separator + "converter";
	*/
	public static final String META_INF_SERVICES = "META-INF/services/net/jdft/converter";

	private Map<String, Converter> converterMap = new ConcurrentHashMap<String, Converter>();
	
	/** META_INF_SERVICES 가 포함되어 있는 file 정보 */
	private List<String> loadedResources = new ArrayList<String>();
	
	public List<String> getLoadedResources() {
		return loadedResources;
	}

	public Converter getConverter(String format) {
		return converterMap.get(format);
	}

	public void setConverter(String key, Converter converter) {
		// test 용
		converterMap.put(key, converter);
	}

	public void load() throws Exception {

		loadGeneralClassPath();
		loadUserDir();
		loadJDFTDir();
		
		loadCurClasspathDir();
	}

	private void loadGeneralClassPath() throws Exception {
		loadPathInClassPath();
		loadJarInClassPath();
	}

	private void loadPathInClassPath() throws Exception {
		String[] path = PathLoader.load("java.class.path");
		for (int i = 0; i < path.length; i++) {

			File file = new File(path[i] + File.separator + META_INF_SERVICES);
			if (file.isDirectory()) {
				continue;
			}
			
			//설정 정보가 있는 file 정보 저장
			loadedResources.add(path[i]);

			Properties properties = new Properties();
			FileInputStream input = null;

			try {
				input = new FileInputStream(file);
				properties.load(input);

				addConverter(properties, file.getAbsolutePath());
			} catch (FileNotFoundException e) {
				continue;
			} finally {
				if (input != null) {
					input.close();
				}
			}
		}
	}

	private void loadJarInClassPath() throws Exception {
		String[] jars = JarLoader.load("java.class.path");

		if (jars.length <= 0) {
			log.debug("not found jar in general class path(java.class.path)");
		}

		loadJar(jars);
	}
	
	private void loadCustomClassPath(String path) throws Exception {
		File f = new File(path);
		String[] files = f.list(new FilenameFilter() {

			public boolean accept(File arg0, String arg1) {
				if (arg1.lastIndexOf(".jar") >= 0) {
					return true;
				}
				return false;
			}

		});

		if (files == null || files.length <= 0) {
			return;
		}
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < files.length; i++) {
			list.add(path + File.separator + files[i]);
		}

		loadJar((String[]) list.toArray(new String[files.length]));
	}

	/**
	 * Launcher.jar에서 추가한 lib 설정에 등록되어 있는 library를 검사한다.
	 * 
	 * @throws Exception
	 */
	private void loadUserDir() throws Exception {
		String currentDir = System.getProperty("user.dir");
		String libDirectory = (new File(currentDir)).getParent()
				+ File.separator + "lib";
		
		loadCustomClassPath(libDirectory);		
	}
	
	private void loadJDFTDir() throws Exception {
		String currentDir = System.getProperty("jdft.dir");
		if(currentDir != null) {
			loadCustomClassPath(currentDir);
		} else {
			log.warn("not found System Property(\"jdft.dir\")");
		}
	}
	
	private void loadCurClasspathDir() throws Exception {
		ClassLoader classLoaderToUse = getDefaultClassLoader();
		Enumeration<URL> urls = (classLoaderToUse != null ? classLoaderToUse.getResources(META_INF_SERVICES)
				: ClassLoader.getSystemResources(META_INF_SERVICES));
				
		while (urls.hasMoreElements()) {
			Properties props = new Properties();
			URL url = urls.nextElement();
			URLConnection con = url.openConnection();
			useCachesIfNecessary(con);
			InputStream is = con.getInputStream();
			try {
				props.load(is);
				addConverter(props, url.getPath());
			} finally {
				is.close();
			}
		}
	}
	private ClassLoader getDefaultClassLoader() {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = this.getClass().getClassLoader();
			if (cl == null) {
				// getClassLoader() returning null indicates the bootstrap
				// ClassLoader
				try {
					cl = ClassLoader.getSystemClassLoader();
				} catch (Throwable ex) {
					// Cannot access system ClassLoader - oh well, maybe the
					// caller can live with null...
				}
			}
		}
		return cl;
	}
	private static void useCachesIfNecessary(URLConnection con) {
		con.setUseCaches(con.getClass().getSimpleName().startsWith("JNLP"));
	}

	private void loadJar(String[] jars) throws Exception {
		for (int i = 0; i < jars.length; i++) {
			Properties properties = ResourceLoader.load(jars[i]);
			if (properties != null) {
				//설정 정보가 있는 jar 정보 저장
				loadedResources.add(jars[i]);
				
				addConverter(properties, jars[i]);
			} 
		}
	}

	private void addConverter(Properties properties, String path)
			throws Exception {
		String className = properties.getProperty("class");
		Converter converter = (Converter) Class.forName(className)
				.newInstance();
		converterMap.put(converter.getFormat(), converter);

		log.info("Found Converter : foramt({}) class({}) in {}", new String[] {
				converter.getFormat(), converter.toString(), path });
	}
}
