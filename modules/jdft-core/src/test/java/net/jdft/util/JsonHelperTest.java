package net.jdft.util;

import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class JsonHelperTest {
	@Test
	public void test() {
		String data ="{\"list\":[{\"trace\":[{\"timestamp\":\"20141113171258\",\"elapsed\":\"0\",\"node\":\"process\",\"routeId\":\"temp1\"},{\"timestamp\":\"20141113171258\",\"elapsed\":\"1003\",\"node\":\"to\",\"routeId\":\"temp1\"},{\"timestamp\":\"20141113171258\",\"elapsed\":\"1003\",\"node\":\"delay\",\"routeId\":\"temp2\"},{\"timestamp\":\"20141113171259\",\"elapsed\":\"0\",\"node\":\"process\",\"routeId\":\"temp1\"}],\"messageId\":\"ID-jin-PC-13450-1415862105051-0-14\"}],\"baseTime\":\"20141113171300\"}";
		
		try {
			JSONObject obj = new JSONObject(data);
			
			Map m = JsonHelper.convertJSON2Map(obj);
			List list = (List)m.get("list");
			
			System.out.println("size : " + list.size());
			for(int i=0 ;i<list.size();i++) {
				Map listMap = (Map)list.get(i);
				
				System.out.println("---" + listMap);
				
				List trace = (List)listMap.get("trace");
				
				for(int j=0 ;j<trace.size();j++) {
					Map traceMap = (Map)trace.get(j);
					
					System.out.println("====" + traceMap);
					
					System.out.println("====>" + traceMap.get("node"));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
