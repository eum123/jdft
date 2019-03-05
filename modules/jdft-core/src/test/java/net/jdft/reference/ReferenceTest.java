package net.jdft.reference;

import junit.framework.TestCase;

public class ReferenceTest extends TestCase {
	public void test() {
		Instance i = new Instance("1");
		
		Dummy dummy = new Dummy(i);
		
		assertEquals(dummy.getInstanceName(), "1");
		
		i.setName("3");
		
		assertEquals(dummy.getInstanceName(), "3");
		
		i = new Instance("2");
		
		assertEquals(dummy.getInstanceName(), "3");
	}
	
	class Instance {
		private String name = null;
		public Instance(String name) {
			this.name = name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}
	
	class Dummy {
		private Instance instance = null;
		
		public Dummy(Instance instance) {
			this.instance = instance;
		}
		
		public String getInstanceName(){
			return instance.getName();
		}
	}
}
