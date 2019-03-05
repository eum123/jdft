package net.jdft.fixedlength.xml;

import java.io.IOException;

import net.jdft.util.XMLUtil;

import org.jdom.Element;
import org.junit.Test;

public class ElementTest {
	@Test
	public void addElementTest() {
		Element home = new Element("home");
		home.addContent(new Element("father").addContent(new Element("my")));
		
		home.getChild("father").addContent(new Element("m1"));
		
		try {
			System.out.println(XMLUtil.toString(home));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void addSameElementTest() {
		Element home = new Element("home");
		
		Element e1 = new Element("father").addContent(new Element("my"));
		Element e2 = new Element("father").addContent(new Element("my1"));
		
		home.addContent(e1);
		home.addContent(e2);
		
		try {
			System.out.println(XMLUtil.toString(home));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void addReferenceElementTest() {
		Element home = new Element("home");
		
		Element e1 = new Element("father");
		
		home.addContent(e1);
		
		e1.setText("hi");
		
		
		try {
			System.out.println(XMLUtil.toString(home));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
