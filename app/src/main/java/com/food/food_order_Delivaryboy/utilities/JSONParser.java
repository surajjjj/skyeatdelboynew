package com.food.food_order_Delivaryboy.utilities;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class JSONParser {
	JSONObject jobj;
	InputStream is;

	public String doPostRequest(String urls, HashMap<String, String> map) {
		URL url = null;
		try {
			url=new URL(urls);
		} catch (MalformedURLException e) {
			//e.printStackTrace();
		}
		StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        
        String body = bodyBuilder.toString();
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
//            conn.setConnectTimeout(30000);
//            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            //conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            
            // handle the response
            int status = conn.getResponseCode();
            // If response is not success
            if (status != 200) {
              throw new IOException("Post failed with error code " + status);
            }
            
            if(status==200){
            	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            	StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            	return sb.toString();
            }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
		return null;
	}

	public String doGetRequest(String path){
    	StringBuilder sb=new StringBuilder();
    	HttpURLConnection conn=null;
    	URL url;
		try {
			url = new URL(path);
    	conn=(HttpURLConnection) url.openConnection();
    	//conn.setRequestProperty("USER-AGENT", "Mozilla/5.0");
    	//conn.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
    	InputStreamReader in=new InputStreamReader(conn.getInputStream());
    	int b;
    	while((b=in.read())!=-1){
    		sb.append((char)b);
    	}
    	String str=sb.toString();
    	return str;
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
    }
}