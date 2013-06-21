package org.shortlets.simplescalculo;

import org.shortlets.simplescalculo.util.ViewUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.CalendarContract;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.ActionBarSherlock.OnCreateOptionsMenuListener;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

public class Principal extends Activity implements OnCreateOptionsMenuListener {

	
	ActionBarSherlock mSherlock = ActionBarSherlock.wrap(this);
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mSherlock.setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
        mSherlock.setContentView(R.layout.activity_principal);
		//setContentView(R.layout.activity_principal);
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
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/DynoBold.ttf");
        
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
		
		 return mSherlock.dispatchCreateOptionsMenu(menu);
	}
	
	private final int SALVAR_ID = Menu.FIRST;
	private final int BUSCAR_ID = Menu.FIRST + 1;
	private final int ATUALIZAR_ID = Menu.FIRST + 2;
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        
		menu.add(Menu.NONE, BUSCAR_ID, Menu.NONE,getString(R.string.ajuda))
            .setIcon(R.drawable.help)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		

        return true;
	}


}
