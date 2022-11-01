package com.example.demo;
import java.sql.ResultSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class JsonHelper {

	public static JSONArray convertSetToJson(ResultSet resultSet) throws Exception {
		 
	    JSONArray jsonArray = new JSONArray();
	 
	    while (resultSet.next()) {
	 
	        int columns = resultSet.getMetaData().getColumnCount();
	        JSONObject obj = new JSONObject();
	 
	        for (int i = 0; i < columns; i++)
	            obj.put(resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
	 
	        jsonArray.add(obj);
	    }
	    return jsonArray;
	}
}
