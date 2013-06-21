package org.shortlets.simplescalculo;

import org.shortlets.simplescalculo.calculadora.Calculadora;
import org.shortlets.simplescalculo.calculadora.Periodo;
import org.shortlets.simplescalculo.calculadora.TipoCalculo;
import org.shortlets.simplescalculo.exceptions.ViewException;
import org.shortlets.simplescalculo.util.ViewUtil;
import org.shortlets.simplescalculo.util.ViewFormatacaoUtil;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.ActionBarSherlock.OnCreateOptionsMenuListener;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Juros extends Activity implements OnCreateOptionsMenuListener {

	private double dValorPresent;
	private double dPercentual;
	private double valorFinal=0.0;
	private int iTempo;
	
	ActionBarSherlock mSherlock = ActionBarSherlock.wrap(this);
	private EditText valorPresente;
	private EditText taxa;
	private EditText tempo;
	private EditText valorFuturo;
	private Spinner  txPeriodo;
	private Spinner  tipoPeriodo;
    private TipoCalculo  tipoCalculo= TipoCalculo.SIMPLES; 
    private TextView respValorFuturo;
    
    Periodo txPeriodo_ = Periodo.DIA; 
    Periodo tpPeriodo_ = Periodo.DIA ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_juros);
		
        mSherlock.setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
        mSherlock.setContentView(R.layout.activity_juros);
        
		if(savedInstanceState ==null){
			dValorPresent =0.0;
			dValorPresent=0.0;
			iTempo=0;
		}else{
			
		}
		// valor presente e seus eventos
		valorPresente = (EditText) findViewById(R.id.idValorPresente);
		valorPresente.addTextChangedListener(formatarEcalcular(Tipo.VALORPRESENTE));
		// taxa(%)
		taxa = (EditText) findViewById(R.id.idValorPercentual);
		taxa.addTextChangedListener(formatarEcalcular(Tipo.TAXA));
		//tempo (D,M,A)
		tempo = (EditText) findViewById(R.id.idTempo);
		tempo.addTextChangedListener(formatarEcalcular(Tipo.TEMPO));
		
		txPeriodo =(Spinner) findViewById(R.id.idPeriodoTx);
		txPeriodo.setOnItemSelectedListener(onItemSelectedListener);
		
		tipoPeriodo=(Spinner) findViewById(R.id.idTipoPeriodo);
		tipoPeriodo.setOnItemSelectedListener(onItemSelectedListener);
		
		valorFuturo = (EditText) findViewById(R.id.idTotalDescontoFinal);
	
		
		respValorFuturo  = (TextView) findViewById(R.id.labelResp1);  
		respValorFuturo.setText("Valor Futuro");
		//respValorFuturo.setTextColor(Color.CYAN);
	}
   
	private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		         
			calcular();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 return mSherlock.dispatchCreateOptionsMenu(menu);
	}
	
	private final int SALVAR_ID = Menu.FIRST;
	private final int BUSCAR_ID = Menu.FIRST + 1;
	private final int ATUALIZAR_ID = Menu.FIRST + 2;
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        
		menu.add(Menu.NONE, SALVAR_ID, Menu.NONE,getString(R.string.enviar))
        .setIcon( R.drawable.ic_title_share_default)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

	menu.add(Menu.NONE, BUSCAR_ID, Menu.NONE,getString(R.string.visualizar))
        .setIcon(R.drawable.ic_search)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

	menu.add(Menu.NONE, ATUALIZAR_ID, Menu.NONE,getString(R.string.limpar))
        .setIcon( R.drawable.ic_refresh)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		// TODO Auto-generated method stub
        String titulo = tipoCalculo.equals(TipoCalculo.SIMPLES)? "Juros Simples":"Juros Composto";
        
        
        String txPeriodoStr =this.txPeriodo_.toString().toLowerCase();
        String tpPeriodoStr =this.tpPeriodo_.toString().toLowerCase()+"(s)";
		switch (item.getItemId()) {
		case SALVAR_ID:
			 String msg= titulo+"\n"+String.format( getString(R.string.respostajuros), dValorPresent,dPercentual,"%",txPeriodoStr,iTempo,tpPeriodoStr,valorFinal);
			 ViewUtil.enviarMensagem(msg, this);
			break;

		case BUSCAR_ID:
		
			       
            AlertDialog.Builder builder =  new AlertDialog.Builder(this);         
            LayoutInflater inflater =  this.getLayoutInflater(); 
            View alertaView =inflater.inflate(R.layout.alert, null);
            ViewUtil alertaBuilder = new ViewUtil(alertaView);
            
            try {
				//Valor Presente
            	alertaBuilder.builderTextView(R.id.label1, getString(R.string.valor_presente));
				alertaBuilder.builderTextView(R.id.resp1,  String.format("%.02f", dValorPresent));
				//Taxa
            	alertaBuilder.builderTextView(R.id.label2, getString(R.string.taxa));
				alertaBuilder.builderTextView(R.id.resp2, String.format("%.02f", dPercentual)+"% ao "+ txPeriodoStr);
				//Tempo
            	alertaBuilder.builderTextView(R.id.label3, getString(R.string.tempo));
				alertaBuilder.builderTextView(R.id.resp3, String.format(iTempo +" "+ tpPeriodoStr));
				// Valor Futuro
            	alertaBuilder.builderTextView(R.id.label4, getString(R.string.valor_futuro));
				alertaBuilder.builderTextView(R.id.resp4,  getString(R.string.dinheiro)+" " +String.format("%.02f", valorFinal));
				
				
			} catch (ViewException e) {
				e.printStackTrace();
			}
            
           builder.setTitle(titulo);
           builder.setIcon(R.drawable.alerta);
           builder.setView(alertaView);
           builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	dialog.cancel();
          
                  }
                });
                           
      
         
           AlertDialog choicesDialog = builder.create();
           choicesDialog.show(); // show the Dialog            
         return true;
 
		case ATUALIZAR_ID:
	          AlertDialog.Builder limparTela = 
	            new AlertDialog.Builder(this);
	            limparTela.setIcon(R.drawable.alerta);
	            limparTela.setTitle("Aviso");
	            limparTela.setMessage(getString(R.string.limpartela));
	            limparTela.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	      	          Intent intent = new Intent(getApplicationContext(),Juros.class);
	      	          Juros.this.finish();  
	      	          startActivity(intent);
	                    dialog.cancel();  
	          
	                  }
	                });
	            
	            limparTela.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	      	
	                    dialog.cancel();  
	          
	                  }
	                });

	           AlertDialog choicesDialogAtlza = limparTela.create();
	           choicesDialogAtlza.show();             
         return true;
		}
     	
		
		return super.onOptionsItemSelected(item);
	}

	
	private TextWatcher formatarEcalcular(final Tipo  tipo){
		TextWatcher formatarEcalcular = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				switch (tipo) {
				case VALORPRESENTE:
				    dValorPresent = ViewFormatacaoUtil.EditTextToDouble(s);
				    Log.i("VALOR_PRESENTE::", "::"+dValorPresent);
					break;
				case TAXA:
					dPercentual = ViewFormatacaoUtil.EditTextToDouble(s);
					 Log.i("VALOR_TAXA::", "::"+dPercentual);
					break;
				case TEMPO:
					iTempo =  ViewFormatacaoUtil.EditTextToInteiro(s);
					Log.i("TEMPO::", "::"+iTempo);
					break;
				default:
					break;
				}
				calcular();
			}


			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
			return formatarEcalcular;
	}
	
	private void calcular() {
		
		txPeriodo_ = txPeriodo.getSelectedItem().toString().equals(Periodo.DIA.valor(getResources()))? Periodo.DIA 
				: txPeriodo.getSelectedItem().toString().equals(Periodo.MES.valor(getResources()))? 
			    Periodo.MES:Periodo.ANO;
		tpPeriodo_ = tipoPeriodo.getSelectedItem().toString().equals(Periodo.DIA.valor(getResources()))? Periodo.DIA 
				: tipoPeriodo.getSelectedItem().toString().equals(Periodo.MES.valor(getResources()))? 
				Periodo.MES:Periodo.ANO;
		
		switch (tipoCalculo) {
		case SIMPLES:
			 valorFinal=Calculadora.calcularJurosSimples(dValorPresent, dPercentual, iTempo, tpPeriodo_,txPeriodo_);
			break;
  
		case COMPOSTO:
			 valorFinal=Calculadora.calcularJurosCompostos(dValorPresent, dPercentual, iTempo, tpPeriodo_,txPeriodo_);
			break;
		}
		
		
		valorFuturo.setText(String.format("%.02f", valorFinal));
	}
	
	public void onRadioButtonClicked(View view) {
	   
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    
	    switch(view.getId()) {
	        case R.id.idDjurosSimplesRadio:
	            if (checked)
	            	tipoCalculo= TipoCalculo.SIMPLES; 
	                calcular();
	            break;
	        case R.id.idDjurosComposto:
	            if (checked)
	            	tipoCalculo= TipoCalculo.COMPOSTO; 
	            	calcular();
	            break;
	    }
	}


}
