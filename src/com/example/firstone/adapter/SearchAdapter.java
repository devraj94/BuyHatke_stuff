package com.example.firstone.adapter;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firstone.MainActivity;
import com.example.firstone.R;

public class SearchAdapter extends CursorAdapter {

    private List<String> items;

    private TextView text;
    MainActivity parent_act;

    public SearchAdapter(Context context, Cursor cursor, List<String> items,MainActivity a) {

        super(context, cursor, false);

        parent_act=a;
        this.items = items;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        text.setText(items.get(cursor.getPosition()));

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item, parent, false); 

        text = (TextView) view.findViewById(R.id.itemg);

        view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			  parent_act.updatesearchone(text.getText().toString());	
			}
		});
        
        return view;

    }

}