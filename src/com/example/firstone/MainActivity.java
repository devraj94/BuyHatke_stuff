package com.example.firstone;

import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.firstone.adapter.SearchAdapter;

/**
* A simple launcher activity containing a summary sample description, sample log and a custom
* {@link android.support.v4.app.Fragment} which can display a view.
* <p>
* For devices with displays with a width of 720dp or greater, the sample log is always visible,
* on other devices it's visibility is controlled by an item on the Action Bar.
*/
public class MainActivity extends ActionBarActivity implements OnQueryTextListener{

   public static final String TAG = "MainActivity";

   int present_shared_pref=-1;
   ArrayList<String> lister=new ArrayList<String>();
   // Whether the Log Fragment is currently shown
   private boolean mLogShown;
   SearchAdapter adapter;
   SearchView searchView;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       
       getSupportActionBar().setTitle("hello");
       
       if (savedInstanceState == null) {
           FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
           SlidingTabsBasicFragment fragment = new SlidingTabsBasicFragment();
           transaction.replace(R.id.sample_content_fragment, fragment);
           transaction.commit();
       }

	   Log.e("hju", String.valueOf("kl"));
       for(int i=0;i<10;i++){
    	   SharedPreferences prefs = getSharedPreferences("shared_prefs_firstone"+i, Context.MODE_PRIVATE);
           
        	  String hj= prefs.getString("name", null);
        	  if(hj!=null){
        		  lister.add(hj);
        		  present_shared_pref=i;
        	  }else{
        		  Log.e("error", String.valueOf(i));
        		  break;
        	  }
           
       }
       
       
      
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
	   MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);
	    // Associate searchable configuration with the SearchView
	    SearchManager searchManager =
	           (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    MenuItem item=menu.findItem(R.id.search);
	     searchView =
	    		(SearchView) MenuItemCompat.getActionView(item);
	    searchView.setOnQueryTextListener( MainActivity.this);

	    
	    
	    
	     return true;

   }

   @Override
   protected void onNewIntent(Intent intent) {
       handleIntent(intent);
   }

   private void handleIntent(Intent intent) {

	   Log.e("hello", "dude");
       if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
           String query = intent.getStringExtra(SearchManager.QUERY);
           Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();
       }
   }

@Override
public boolean onQueryTextChange(String arg0) {
	// TODO Auto-generated method stub

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
    	 
    	  // Load data from list to cursor
        String[] columns = new String[] { "_id", "text" };
        Object[] temp = new Object[] { 0, "default" };
 
        MatrixCursor cursor = new MatrixCursor(columns);
 
      
 
        // Alternatively load data from database
        //Cursor cursor = db.rawQuery("SELECT * FROM table_name", null);
 
      	ArrayList<String> liy=new ArrayList<String>();
      	int m=0;
        	for(int iu=0;iu<lister.size();iu++){
        		String hj=lister.get(iu).toLowerCase().replaceAll("\\s+", "");
        		if(hj.contains(arg0.toLowerCase().replaceAll("\\s+", ""))){
        			liy.add(lister.get(iu)); 
        				  
        		            temp[0] = m;
        		            temp[1] = lister.get(iu);
        		 
        		            cursor.addRow(temp);
        		            m++;
        		 
        		}
        	}
        		searchView.setSuggestionsAdapter(new SearchAdapter(this, cursor, liy,MainActivity.this));
              
       
 
    }
 
	return false;
}

@Override
public boolean onQueryTextSubmit(String arg0) {
	// TODO Auto-generated method stub
	
	if(!lister.contains(arg0)){
		if(lister.size()>9){
			lister.remove(lister.size()-1);
			lister.add(0,arg0);
			
		}else{
			lister.add(0,arg0);
		}
		for(int i=0;i<lister.size();i++){
			Log.e("save", String.valueOf(i));
		 SharedPreferences prefs = getSharedPreferences("shared_prefs_firstone"+i, Context.MODE_PRIVATE);
	        
	     	Editor editor=prefs.edit();
	     	editor.putString("name", lister.get(i));
	     	editor.commit();
	     	
	    }
		
		//Toast.makeText(getApplicationContext(), lister.toString(), Toast.LENGTH_LONG).show();
	}
	return false;
}

public void updatesearchone(String text) {
	// TODO Auto-generated method stub
	searchView.setQuery(text, true);
}


  
}
	/*
	 * 	int local_service_check=0;
	   		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	   	  for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	   	    if (ChatHeadService.class.getName().equals(service.service.getClassName())) {
	   	    	local_service_check=1;
	   	    }
	   	  }
	   	  if(local_service_check==0){
	  	    Log.e("hello", "hello1");
	   		Intent intent = new Intent(this, ChatHeadService.class);
	        startService(intent);
	   	  }
	 */
