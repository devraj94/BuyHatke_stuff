package com.example.firstone;

import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class ChatHeadService extends Service {

	  private WindowManager windowManager;
	  private ImageView chatHead;
	  boolean msg_open=false;
	  String recent="";
	  ArrayList<Integer> lister=new ArrayList<Integer>();

	  
	  @Override public IBinder onBind(Intent intent) {
	    // Not used
	    return null;
	  }

	  @Override
	    public int onStartCommand(Intent intent, int flags, int startId) {

		  
		  ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
			// The first in the list of RunningTasks is always the foreground task.
			RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
			String foregroundTaskPackageName = foregroundTaskInfo .topActivity.getPackageName();
			if(foregroundTaskPackageName.contains("flipkart") && msg_open==false && !recent.contains("flipkart")){
				show_message("flipkart");
				Toast.makeText(getApplicationContext(), "Flipkart", Toast.LENGTH_LONG).show();
			}else if(foregroundTaskPackageName.contains("amazon") && msg_open==false && !recent.contains("amazon")){
				show_message("amazon");
				Toast.makeText(getApplicationContext(), "Amazon", Toast.LENGTH_LONG).show();
			}else if(foregroundTaskPackageName.contains("myntra") && msg_open==false && !recent.contains("myntra")){
				show_message("myntra");
				Toast.makeText(getApplicationContext(), "Myntra", Toast.LENGTH_LONG).show();
			} else if(foregroundTaskPackageName.contains("snapdeal") && msg_open==false && !recent.contains("snapdeal")){
				show_message("snapdeal");
				Toast.makeText(getApplicationContext(), "SnapDeal", Toast.LENGTH_LONG).show();
			} 
		  check_running_apps();
	    Log.e("hello", "hello");
	    windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

	    chatHead = new ImageView(this);
	    chatHead.setImageResource(R.drawable.ic_profile);

	    final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
	        WindowManager.LayoutParams.WRAP_CONTENT,
	        WindowManager.LayoutParams.WRAP_CONTENT,
	        WindowManager.LayoutParams.TYPE_PHONE,
	        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
	        PixelFormat.TRANSLUCENT);

	    params.gravity = Gravity.TOP | Gravity.LEFT;
	    params.x = 0;
	    params.y = 100;

	    windowManager.addView(chatHead, params);
	    
	    chatHead.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				windowManager.removeView(chatHead);
				LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
				final View item=inflater.inflate(R.layout.flipper_one, null);
				final ViewFlipper mViewFlipper=(ViewFlipper)item.findViewById(R.id.viewFlipper_one);
				
				RelativeLayout prev=(RelativeLayout)item.findViewById(R.id.previous_arrow);
				RelativeLayout next=(RelativeLayout)item.findViewById(R.id.next_arrow);
				ImageView minimize=(ImageView)item.findViewById(R.id.minimize_this);
				
				minimize.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						windowManager.removeView(item);
						windowManager.addView(chatHead, params);
					}
				});
				
				prev.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mViewFlipper.showPrevious();
					}
				});
				
				next.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mViewFlipper.showNext();
					}
				});
				
				lister.add(R.drawable.nyc_pics2);
				lister.add(R.drawable.nyc_pics3);
				lister.add(R.drawable.nyc_pics4);
				
				final GestureDetector detector = new GestureDetector(new SwipeGestureDetector(mViewFlipper));
				
				mViewFlipper.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(final View view, final MotionEvent event) {
						detector.onTouchEvent(event);
						return true;
					}
				});
				
				for(int i=0;i<lister.size();i++){
					View vr=inflater.inflate(R.layout.listview_item, null);
					ImageView im=(ImageView)vr.findViewById(R.id.imageview_item_listview);
					TextView tv1=(TextView)vr.findViewById(R.id.textview_item_name);
					TextView tv2=(TextView)vr.findViewById(R.id.textview_item_somedetails);
					TextView tv3=(TextView)vr.findViewById(R.id.textview_item_moredetails);
					
					im.setImageResource(lister.get(i));
					tv1.setText("Image "+i);
					tv2.setText("Some Details "+i);
					tv3.setText("More Details "+i);
					mViewFlipper.addView(vr);
				}
				
				
				windowManager.addView(item, params);
			}
		});
	    
	    chatHead.setOnTouchListener(new View.OnTouchListener() {
	    	  private int initialX;
	    	  private int initialY;
	    	  private float initialTouchX;
	    	  private float initialTouchY;
	    	  boolean clicked=false;

	    	  @Override public boolean onTouch(View v, MotionEvent event) {
	    	    switch (event.getAction()) {
	    	      case MotionEvent.ACTION_DOWN:
	    	        initialX = params.x;
	    	        initialY = params.y;
	    	        initialTouchX = event.getRawX();
	    	        initialTouchY = event.getRawY();
	    	        return true;
	    	      case MotionEvent.ACTION_UP:
	    	    	  if(clicked==false){
	    	    		  v.performClick();
	    	    		  Log.e("hello", "Hello");
	    	    	  }
	    	    	  clicked=false;
	    	        return true;
	    	      case MotionEvent.ACTION_MOVE:
	    	    	  clicked=true;
	    	        params.x = initialX + (int) (event.getRawX() - initialTouchX);
	    	        params.y = initialY + (int) (event.getRawY() - initialTouchY);
	    	        windowManager.updateViewLayout(chatHead, params);
	    	        return true;
	    	    }
	    	    return false;
	    	  }
	    	}); 
		return START_STICKY; 
	    
	  }

	  @Override
	  public void onDestroy() {
	    super.onDestroy();
	    if (chatHead != null) windowManager.removeView(chatHead);
	  }
	  
	  public void check_running_apps(){
		  Handler handler=new Handler();
		  handler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
				// The first in the list of RunningTasks is always the foreground task.
				RunningTaskInfo foregroundTaskInfo = am.getRunningTasks(1).get(0);
				String foregroundTaskPackageName = foregroundTaskInfo .topActivity.getPackageName();
				if(foregroundTaskPackageName.contains("flipkart") && msg_open==false && !recent.contains("flipkart")){
					show_message("flipkart");
					Toast.makeText(getApplicationContext(), "Flipkart", Toast.LENGTH_LONG).show();
				}else if(foregroundTaskPackageName.contains("amazon") && msg_open==false && !recent.contains("amazon")){
					show_message("amazon");
					Toast.makeText(getApplicationContext(), "Amazon", Toast.LENGTH_LONG).show();
				}else if(foregroundTaskPackageName.contains("myntra") && msg_open==false && !recent.contains("myntra")){
					show_message("myntra");
					Toast.makeText(getApplicationContext(), "Myntra", Toast.LENGTH_LONG).show();
				} else if(foregroundTaskPackageName.contains("snapdeal") && msg_open==false && !recent.contains("snapdeal")){
					show_message("snapdeal");
					Toast.makeText(getApplicationContext(), "SnapDeal", Toast.LENGTH_LONG).show();
				} 
				check_running_apps();
			}
			  
		  }, 15000);
		
	  }
	  
	  
	  public void show_message(final String appname){
		 Handler handler=new Handler();
		 handler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				recent=appname;
				 windowManager.removeView(chatHead);
				  
				  final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
					        WindowManager.LayoutParams.WRAP_CONTENT,
					        WindowManager.LayoutParams.WRAP_CONTENT,
					        WindowManager.LayoutParams.TYPE_PHONE,
					        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					        PixelFormat.TRANSLUCENT);
				  params.gravity = Gravity.TOP | Gravity.LEFT;
				  params.x=0;
				  params.y=100;
				  
					LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
					final View item=inflater.inflate(R.layout.show_message_item, null);
				    
					ImageView minimize=(ImageView)item.findViewById(R.id.minimize_this_show_message_item);
					Button clickme=(Button)item.findViewById(R.id.button_to_open_specific_app);
					
					minimize.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							windowManager.removeView(item);
							windowManager.addView(chatHead, params);
							msg_open=false;
						}
					});
					
					clickme.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent dialogIntent = new Intent(getBaseContext(), MainActivity.class);
							dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							dialogIntent.putExtra("name", appname);
							getApplication().startActivity(dialogIntent);
							windowManager.removeView(item);
							windowManager.addView(chatHead, params);
							
						}
					});
					
					windowManager.addView(item, params);
					msg_open=true;
			}
			 
		 }, 1000);
	  }
	  
	  
	}

class SwipeGestureDetector extends SimpleOnGestureListener {
	
	ViewFlipper mViewFlipper;
	
	SwipeGestureDetector(ViewFlipper fp){
		mViewFlipper =fp;
	}
	
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		try {
			// right to left swipe
			if (e1.getX() - e2.getX() > 10 && Math.abs(velocityX) > 100) {
			mViewFlipper.showNext();
				return true;
			} else if (e2.getX() - e1.getX() > 10 && Math.abs(velocityX) > 100) {
				mViewFlipper.showPrevious();
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
