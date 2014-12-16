package com.example.firstone.adapter;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.firstone.R;


public class Sliding_menu_left_listview_adapter extends BaseAdapter {
	 
    private Context mContext;

    private LayoutInflater mLayoutInflater;   

    public JSONArray mEntries = new JSONArray();   
   // private final ImageDownloader mImageDownloader;    

    public Sliding_menu_left_listview_adapter(Context context,LayoutInflater inflater) {                        
       mContext = context;
       mLayoutInflater = (LayoutInflater)inflater;
      // mImageDownloader = new ImageDownloader(context);
    }

    @Override
    public int getCount() {
       return mEntries.length();
    }

    @Override
    public JSONObject getItem(int position) {
       try {
		return mEntries.getJSONObject(position);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       return null;
    }

    @Override
    public long getItemId(int position) {
       return position;
    }

    @Override
    public View getView(final int position, View convertView,
          ViewGroup parent) {                                           
       RelativeLayout itemView;
       if (convertView == null) {                                        
          itemView = (RelativeLayout) mLayoutInflater.inflate(
                   R.layout.sliding_menu_left_listview_cell, parent, false);

       } else {
          itemView = (RelativeLayout) convertView;
       }

      try{
    	  JSONObject json=mEntries.getJSONObject(position);
    	  ImageView image=(ImageView)itemView.findViewById(R.id.imageView_listview_slidingmenu_left);
    	  TextView name=(TextView)itemView.findViewById(R.id.name_object_slidingmenu_left_cell);
    	  TextView oldprice=(TextView)itemView.findViewById(R.id.price_object_slidingmenu_left_cell);
    	  TextView currentprice=(TextView)itemView.findViewById(R.id.currentprice_object_slidingmenu_left_cell);
    	  new DownloadImageTask_othersprofile(image).execute(json.getString("image"));
    	  name.setText(json.getString("prod"));
    	  oldprice.setText(json.getString("MRP"));
    	  currentprice.setText(json.getString("price"));
      }catch(Exception e){
    		Log.e("error", e.getLocalizedMessage());
    	  }
      
  
       return itemView;
    }

    public void upDateEntries(JSONArray entries) {
       mEntries = entries;
       notifyDataSetChanged();
    }

	
	}

class DownloadImageTask_othersprofile extends AsyncTask<String, Void, Bitmap> {
	  ImageView bmImage;
	  
   
   String email="";
	  public DownloadImageTask_othersprofile(ImageView bmImage) {
	      this.bmImage = bmImage;
	     
	  }

	  protected Bitmap doInBackground(String... urls) {
	      String urldisplay = urls[0];
	      Bitmap mIcon11 = null;
	      try {
	        InputStream in = new java.net.URL(urldisplay).openStream();
	        mIcon11 = BitmapFactory.decodeStream(in);
	      } catch (Exception e) {
	          //Log.e("Error", e.getMessage());
	          e.printStackTrace();
	      }
	      return mIcon11;
	  }

	  protected void onPostExecute(Bitmap result) {
	      bmImage.setImageBitmap(result);
	     
	     
	  }
	}


