package net.jdft.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import junit.framework.TestCase;
import net.jdft.FormatTransformation;

import org.apache.log4j.BasicConfigurator;

public class FindClassTest extends TestCase {
	static {
		BasicConfigurator.configure();
	}
	
	

	

	public void test2() throws Exception {
		final File f = new File(FormatTransformation.class
				.getProtectionDomain().getCodeSource().getLocation().getPath());
		
		System.out.println(f);
		
		System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation());
		
		System.out.println(FormatTransformation.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	}
	// public void test1() {
	// JarFile jarFile = new JarFile(pathToJar);
	// Enumeration e = jarFile.entries();
	//
	// URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
	// cl = URLClassLoader.newInstance(urls);
	//
	// while (e.hasMoreElements()) {
	// JarEntry je = (JarEntry) e.nextElement();
	// if(je.isDirectory() || !je.getName().endsWith(".class")){
	// continue;
	// }
	// // -6 because of .class
	// String className = je.getName().substring(0,je.getName().length()-6);
	// className = className.replace('/', '.');
	// Class c = cl.loadClass(className);
	//
	// }
	// }

	
	public static final String META_INF_SERVICES = "META-INF/services/net/jdft/fixedlength";
	protected Set<String> visitedURIs = new HashSet<String>();
	
	public void test3() throws Exception {
		
		System.out.println(ObjectHelper.asString(findPackageNames()));
	}
	
	protected String[] findPackageNames() throws IOException {
        Set<String> packages = new HashSet<String>();
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        if (ccl != null) {
            findPackages(packages, ccl);
        }
        findPackages(packages, getClass().getClassLoader());
        
        System.out.println("package : " + packages);
        
        return packages.toArray(new String[packages.size()]);
    }
	protected void findPackages(Set<String> packages, ClassLoader classLoader) throws IOException {
        Enumeration<URL> resources = classLoader.getResources(META_INF_SERVICES);
        
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            
            System.out.println("url : " + url);
            
            String path = url.getPath();
            if (!visitedURIs.contains(path)) {
                // remember we have visited this uri so we wont read it twice
                visitedURIs.add(path);
               
                BufferedReader reader = IOHelper.buffered(new InputStreamReader(url.openStream()));
                try {
                    while (true) {
                        String line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        line = line.trim();
                        if (line.startsWith("#") || line.length() == 0) {
                            continue;
                        }
                        tokenize(packages, line);
                    }
                } finally {
                	if(reader != null) {
                		reader.close();
                	}
                }
            }
        }
    }
	
	/**
     * Tokenizes the line from the META-IN/services file using commas and
     * ignoring whitespace between packages
     */
    private void tokenize(Set<String> packages, String line) {
        StringTokenizer iter = new StringTokenizer(line, ",");
        while (iter.hasMoreTokens()) {
            String name = iter.nextToken().trim();
            if (name.length() > 0) {
                packages.add(name);
            }
        }
    }
}
