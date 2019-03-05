package net.jdft.soap.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileHelper {
	public static void write(File f, byte[] data) throws Exception {
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(f);
			out.write(data);
		} finally {
			if(out != null) {
				out.close();
			}
		}
	}
	
	public static byte[] read(File f) throws Exception {
		BufferedInputStream bi = null;
		byte[] buffer = new byte[(int)f.length()];
		try {
			bi = new BufferedInputStream(new FileInputStream(f));
			bi.read(buffer);
			
			return buffer;
		} finally {
			if(bi != null) {
				bi.close();
			}
		}
	}
}
