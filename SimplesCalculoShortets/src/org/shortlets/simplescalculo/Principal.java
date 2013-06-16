package org.shortlets.simplescalculo;

import org.shortlets.simplescalculo.util.ViewUtil;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends Activity {
	private ImageView bntDescSimples;
	private ShareActionProvider mShareActionProvider;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_principal);
        
		

       
		configFontTypeFace();
		configImageButtons();
		
		
	}

	private void configImageButtons() {
		
		RelativeLayout clickPorcentagem = (RelativeLayout)findViewById(R.id.idBntDescSimples);
		clickPorcentagem.setOnClickListener(ViewUtil.getOnClickListenerIntent(getApplicationContext(), DescontoSimples.class, this));
	
		// Botao juros
		RelativeLayout clickJuros =(RelativeLayout) findViewById(R.id.idBntJuros);
        clickJuros.setOnClickListener(ViewUtil.getOnClickListenerIntent(getApplicationContext(), Juros.class, this));
        
        //idBntMeuBoleto
        RelativeLayout clickBoleto =(RelativeLayout) findViewById(R.id.idBntMeuBoleto);
		clickBoleto.setOnClickListener(ViewUtil.getOnClickListenerIntent(getApplicationContext(), MeuBoleto.class, this));
        
		//Calendario 
		RelativeLayout clickAgendamento = (RelativeLayout) findViewById(R.id.idBntgCalc);
		clickAgendamento.setOnClickListener(ViewUtil.getOnClickListenerIntent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI, this));
		
		RelativeLayout clickVerCalendrio = (RelativeLayout) findViewById(R.id.idBntCalendar);
		clickVerCalendrio.setOnClickListener(ViewUtil.openCaledar(this));

	}

	private void configFontTypeFace() {
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/MarketingScript.ttf");
       
        TextView tvPorcentagem = (TextView) findViewById(R.id.tvPorcentagem);
        tvPorcentagem.setTypeface(tf);
		TextView tvJuros = (TextView) findViewById(R.id.tvJuros);
        tvJuros.setTypeface(tf);
        TextView tvBoleto = (TextView) findViewById(R.id.tvBoleto);
        tvBoleto.setTypeface(tf);
        TextView tvAgenda = (TextView) findViewById(R.id.tvAgenda);   
        tvAgenda.setTypeface(tf);
        TextView tvCalendario = (TextView) findViewById(R.id.tvCalendario);
        tvCalendario.setTypeface(tf);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		// Locate MenuItem with ShareActionProvider
	    MenuItem item = menu.findItem(R.id.menu_share);
	    // Fetch and store ShareActionProvider
	    mShareActionProvider = (ShareActionProvider) item.getActionProvider();
		
		return true;
	}
	
	 @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    Toast.makeText(this, "Just a test", Toast.LENGTH_SHORT).show();
	    return true;
	  }



}
