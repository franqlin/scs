package org.shortlets.simplescalculo;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Sobre extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sobre);


	}



}
