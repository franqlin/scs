package org.shortlets.simplescalculo.util;

import org.shortlets.simplescalculo.R;
import org.shortlets.simplescalculo.exceptions.ViewException;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.sax.StartElementListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


public class ViewUtil{
	/**
	 * Retorna um Listerner para um clik longo 
	 * @param msg
	 * @return onLongClickListener
	 */
	
	private View view;
	
	public ViewUtil(View newView){
		this.view = newView;
	}
	public void builderTextView(int id,String msg) throws ViewException{
    if(view!=null){	
	TextView tv =(TextView) view.findViewById(id);
	         tv.setText(msg);
    }else{
    	throw new ViewException("A view não foi carregada corretamente!");
    }
	}
 	
	
 	/**
 	 * Configura cores no Cor para o evento Touch
 	 * @param _actionDownColor
 	 * @param _actionUpColor
 	 * @return OnTouchListener
 	 */
	public static OnTouchListener getOnTouchListenerColor(final int   _actionDownColor,final  int _actionUpColor){
		OnTouchListener  onTouchButaoListener = new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(_actionDownColor);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(_actionUpColor);
					break;
				}
				return false;
			}
		}; 
		return onTouchButaoListener;
	}
	
	public static OnClickListener getOnClickListenerIntent(final Context context,final Class activityClassIntent,final Activity activityAtual){
		
		 OnClickListener listerner = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
	          Intent intent = new Intent(context,activityClassIntent);
	          activityAtual.startActivity(intent);
	          
			}
			
		};
		return listerner;
	}
	/**
	 * Define um intent
	 * @param actionInsert
	 * @param uri
	 * @param activityAtual
	 * @return OnClickListener(intent)
	 */
	public static OnClickListener getOnClickListenerIntent(final String actionInsert,final Uri uri,final Activity activityAtual){
		
		 OnClickListener listener = new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
	          Intent intent = new Intent(actionInsert);
	          intent.setData(uri);
	          activityAtual.startActivity(intent);
	          
			}
		};
		return listener;
	}
	
	
	public static void  enviarMensagem(final String msg,final Activity activityAtual){
		
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
		sendIntent.setType("text/plain");
		activityAtual.startActivity(sendIntent);
	}
	
	/**
	 * Evento para chamada do Calendario
	 * @param activityAtual
	 * @return OnClickListener (intent:: calendar)
	 */
	public static OnClickListener openCaledar(final Activity activityAtual){
		
		 OnClickListener listener = new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClassName("com.android.calendar","com.android.calendar.LaunchActivity");
				activityAtual.startActivity(i);
	          
	          
			}
		};
		return listener;
	}


}
