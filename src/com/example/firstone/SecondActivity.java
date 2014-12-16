package com.example.firstone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.firstone.adapter.Sliding_menu_left_listview_adapter;
import com.example.firstone.sliding_lib.SlidingMenu;

    

public class SecondActivity extends Activity{
	 ListView list;
	 ProgressBar progress;
	 boolean Slider_open=false;
	 Sliding_menu_left_listview_adapter adapter;
	 @Override
	   protected void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.second_activity_layout);
	       
	       final SlidingMenu menu = new SlidingMenu(this);
	        menu.setMode(SlidingMenu.LEFT);
	        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	        menu.setShadowWidthRes(R.dimen.shadow_width);
	        menu.setShadowDrawable(R.drawable.shadow);
	        menu.setBehindWidthRes(R.dimen.slidingmenu_offset);
	        menu.setFadeDegree(0.35f);
	        menu.setFadeEnabled(true);
	        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
	        menu.setMenu(R.layout.sliding_menu_leftbar_layout);
	        
	        list=(ListView)menu.findViewById(R.id.sliding_menu_left_listview);
	        progress=(ProgressBar)menu.findViewById(R.id.progressBar_slidingmenu_left);
	        adapter=new Sliding_menu_left_listview_adapter(getApplicationContext(),getLayoutInflater());
	        list.setAdapter(adapter);
	        new LoadOtherUsers_connections(getApplicationContext(),this).execute("http://buyhatke.com/dealsAPI/homePage.php");
	        
	        ImageView button_for_sliding_menu=(ImageView)findViewById(R.id.slidingmenu_left_draw_button_secondactivity);
	        button_for_sliding_menu.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(Slider_open==true){
						menu.showContent();
					}else{
						menu.showMenu();
					}
						
				}
			});
	        
	 }
	public void gotresult(String entries) {
		// TODO Auto-generated method stub
		try{
			JSONArray array=new JSONArray(entries);
			adapter.upDateEntries(array.getJSONObject(0).getJSONArray("topDeals"));
			progress.setVisibility(View.GONE);
			
		}catch(Exception e){
			Log.e("error", e.getLocalizedMessage());
		}
	}
}

class LoadOtherUsers_connections extends AsyncTask<String, String, String> {

	Context myContext;
	SecondActivity HF;
   String mUrl="";
   boolean show_no_net=false;
    LoadOtherUsers_connections(Context hai,SecondActivity h){
      
        myContext=hai;
        HF=h;
    }



    @Override
    protected String doInBackground(String... urls) {

        return downloadUrl(urls[0]);
    }
    private String downloadUrl(String myUrl) {
        // TODO Auto-generated method stub
    	mUrl=myUrl;
        if(hasActiveInternetConnection(myContext)==true) {
        	//the year data to send
        	InputStream is =null;
        	//http post
        	try{
        	        HttpClient httpclient = new DefaultHttpClient();
        	        HttpGet httppost = new HttpGet(mUrl);
        	        httppost.setHeader("Content-Type", "application/json");
        	        httppost.setHeader("Accept", "JSON");
        	        HttpResponse response = httpclient.execute(httppost);
        	        HttpEntity entity = response.getEntity();
        	        is= entity.getContent();
        	}catch(Exception e){
        	        //Log.e("log_tag", "Error in http connection "+e.toString());
        	}
        	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        	StringBuilder sb = new StringBuilder();

            String line = null;
               try {
				while ((line = reader.readLine()) != null) {
					sb.append(line+"\n");
				   }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
               line=sb.toString();
               return line;
        }
                	
    	
        return "[]";
    }
   
    
    @Override
    protected void onPostExecute(String entries) {
     HF.gotresult(entries);
    }

    public static boolean hasActiveInternetConnection(Context myContext) {
        if (isNetworkAvailable(myContext)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.co.in").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
            }
        } else {
        }
        return false;
    }

    private static boolean isNetworkAvailable(Context myContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
