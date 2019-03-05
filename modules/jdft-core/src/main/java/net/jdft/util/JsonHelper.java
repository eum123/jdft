package net.jdft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {
	public static Map convertJSON2Map(JSONObject jsonObj) throws JSONException {

		Map map = new HashMap();
		Iterator iter = jsonObj.keys();

		while (iter.hasNext()) {
			String key = (String) iter.next();
			try {
				Object obj = jsonObj.get(key);
				if (obj instanceof JSONObject) {
					map.put(key, convertJSON2Map((JSONObject) obj));
				} else if (obj instanceof JSONArray) {
					map.put(key, convertJSON2Array((JSONArray) obj));
				} else {
					map.put(key, obj);
				}
			} catch (JSONException e) {
				throw new JSONException("JSON2 Parsing Exception key : [" + key + "]");
			}
		}
		return map;
	}
	
	public static List convertJSON2Array(JSONArray jsonArray) throws JSONException {
		List list = new ArrayList();

		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				Object obj = jsonArray.get(i);
				if (obj instanceof JSONObject) {
					list.add(convertJSON2Map((JSONObject) obj));
				} else if (obj instanceof JSONArray) {
					list.add(convertJSON2Array((JSONArray) obj));
				} else {
					list.add(obj);
				}
			} catch (JSONException e) {
				throw new JSONException("JSON2 Parsing Exception index : [" + i + "]");
			}
		}
		return list;
	}
	
	public static JSONObject convertHashMap2JSON(Map map) throws JSONException {

		JSONObject jsonObj = new JSONObject();
		Iterator iter = map.keySet().iterator();

		while (iter.hasNext()) {
			String key = (String) iter.next();
			try {
				Object obj = map.get(key);
				if (obj instanceof Map) {
					jsonObj.put(key, convertHashMap2JSON((Map)obj));
				} else if (obj instanceof List) {
					jsonObj.put(key, convertArray2JSON((ArrayList) obj));
				} else {
					jsonObj.put(key, obj);
				}
			} catch (JSONException e) {
				throw new JSONException("2JSON Parsing Exception key : [" + key + "]");
			}
		}
		return jsonObj;
	}

	public static JSONArray convertArray2JSON(ArrayList list) throws JSONException {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < list.size(); i++) {
			try {
				Object obj = list.get(i);
				if (obj instanceof Map) {
					jsonArray.put(i, convertHashMap2JSON((Map) obj));
				} else if (obj instanceof JSONArray) {
					jsonArray.put(i, convertArray2JSON((ArrayList) obj));
				} else {
					jsonArray.put(i, obj.toString());
				}
			} catch (JSONException e) {
				throw new JSONException("2JSON Parsing Exception index : [" + i + "]");
			}
		}
		return jsonArray;
	}
}
