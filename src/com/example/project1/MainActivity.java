package com.example.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	String result="";
	ArrayAdapter<String> adapter;
	ArrayList<String> list = new ArrayList<String>();
	Handler handler = new Handler(){ 
    	public void handleMessage(android.os.Message msg) {
    		JSONArray jsonCategory;
			try {
				jsonCategory = new JSONArray(result);
				for(int i=0;i<jsonCategory.length();i++)
				{
					JSONArray jsonScend = jsonCategory.getJSONArray(i);
					JSONObject json = jsonScend.getJSONObject(3);
					Log.i("ttt", json.getString("中文名稱")+"");
					list.add(json.getString("中文名稱")+"");
					
				}
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ListView lv = (ListView)findViewById(R.id.listView1);
			lv.setAdapter(adapter);
			
    		
    	};
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyThread myThread = new MyThread();
        myThread.start();
        String[] str ={"abc","ddd","as"};
      //  ListView lv = (ListView)findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
       // lv.setAdapter(adapter);
    }
    class MyThread extends Thread
    {
    	public void run() {
    		try {
				URL url = new URL("http://data.fda.gov.tw/cacheData/11_3.json");
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				if(connection.getResponseCode()==HttpURLConnection.HTTP_OK)
				{
					BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
					String str;
					while((str=reader.readLine())!=null){
						result += str;
					}
					reader.close();
				}
				connection.disconnect();
				Message msg = handler.obtainMessage();
				msg.what = 0;
				handler.sendMessage(msg);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	};
    	
    }
}
