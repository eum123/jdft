package net.jdft.util.field;

import net.jdft.util.ObjectHelper;

import org.junit.Assert;
import org.junit.Test;

public class XmlFieldHelperTest {
	@Test
	public void getAddNodeListTest() {
		String parentPath = "/home/persion";
		
		String path = "/home/persion/my/name";
		
		System.out.println(ObjectHelper.after(path, parentPath));
		
		
		String[] nodes = ObjectHelper.removeStartingCharacters(ObjectHelper.after(path, parentPath), '/').split("/");
		for(int i=0 ; i<nodes.length ;i++) {
			System.out.println("node : " + nodes[i]);
		}
	}
	
	@Test
	public void test() {
		String path = "home";
		
		String[] nodes = path.split("/");
		for(int i=0 ; i<nodes.length ;i++) {
			System.out.println("node : " + nodes[i]);
		}
	}
	
	@Test
	public void test1() {
		String path = "";
		
		String[] nodes = path.split("/");
		for(int i=0 ; i<nodes.length ;i++) {
			System.out.println("node : " + nodes[i]);
		}
	}
}
