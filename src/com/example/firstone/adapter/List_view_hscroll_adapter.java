package com.example.firstone.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.firstone.R;


public class List_view_hscroll_adapter extends BaseAdapter {
	 
    private Context mContext;

    private LayoutInflater mLayoutInflater;   

    public ArrayList<ArrayList<String>> mEntries = new ArrayList<ArrayList<String>>();   
   // private final ImageDownloader mImageDownloader;    

    public List_view_hscroll_adapter(Context context,LayoutInflater inflater) {                        
       mContext = context;
       mLayoutInflater = (LayoutInflater)inflater;
      // mImageDownloader = new ImageDownloader(context);
    }

    @Override
    public int getCount() {
       return mEntries.size();
    }

    @Override
    public Object getItem(int position) {
       return mEntries.get(position);
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
                   R.layout.hs_scroll_listview_item, parent, false);

       } else {
          itemView = (RelativeLayout) convertView;
       }

      LinearLayout layout=(LinearLayout)itemView.findViewById(R.id.list_item_layout_linear);
      final ArrayList<String> list=mEntries.get(position);
      for(int i=0;i<list.size();i++){
    	  View view=mLayoutInflater.inflate(R.layout.hs_scroll_listview_scroll_item, null);
    	  View view1=mLayoutInflater.inflate(R.layout.hs_scroll_separater, null);
    	  layout.addView(view);
    	  layout.addView(view1);
    	  final int y=i;
    	  view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			  Toast.makeText(mContext, list.get(y), Toast.LENGTH_SHORT).show();	
			}
		});
    	  
      }
  
       return itemView;
    }

    public void upDateEntries(ArrayList<ArrayList<String>> entries) {
       mEntries = entries;
       notifyDataSetChanged();
    }

	public void update_entries_requested(int position) {
		// TODO Auto-generated method stub
	    mEntries.remove(position);
		notifyDataSetChanged();
	}
	}




