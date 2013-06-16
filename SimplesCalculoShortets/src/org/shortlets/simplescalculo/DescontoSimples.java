package org.shortlets.simplescalculo;



import org.shortlets.simplescalculo.calculadora.TipoCalculo;
import org.shortlets.simplescalculo.exceptions.ViewException;
import org.shortlets.simplescalculo.util.ViewFormatacaoUtil;
import org.shortlets.simplescalculo.util.ViewUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.ActionBarSherlock.OnCreateOptionsMenuListener;
import com.actionbarsherlock.view.MenuItem;

public class DescontoSimples extends Activity implements OnCreateOptionsMenuListener{


	ActionBarSherlock mSherlock = ActionBarSherlock.wrap(this);
	private TipoCalculo  tipoCalculo= TipoCalculo.DESCONTO;
    private EditText valorPresente;
    private EditText taxa;
    private EditText resp;
    private TextView labelResp;
    private double dValorPresente;
    private double dTaxa;
    private double valorfinal=0.0;
    private String alertResposta;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
        mSherlock.setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
        mSherlock.setContentView(R.layout.activity_desconto_simples);
		
        valorPresente = (EditText) findViewById(R.id.idValorPresente);
		valorPresente.addTextChangedListener(formatarEcalcular(Tipo.VALORPRESENTE));

		taxa = (EditText) findViewById(R.id.idPorcentagem);
		taxa.addTextChangedListener(formatarEcalcular(Tipo.TAXA));  
        
		resp = (EditText) findViewById(R.id.idTotalDescontoFinal);
		labelResp = (TextView) findViewById(R.id.labelResp1);
		alertResposta = getString(R.string.labelRespDesconto);

	}

	private TextWatcher formatarEcalcular(final Tipo tipoCalculo) {
		
		TextWatcher tw = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				switch (tipoCalculo) {
				case VALORPRESENTE:
					dValorPresente = ViewFormatacaoUtil.EditTextToDouble(s);
					break;

				case TAXA:
					dTaxa = ViewFormatacaoUtil.EditTextToDouble(s);
					break;
				default:
					break;
				}
				calcular();
			}


			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {}
		};
			return tw;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 return mSherlock.dispatchCreateOptionsMenu(menu);
	}
	
	

	private void calcular() {
       
       
       switch (tipoCalculo) {
	case DESCONTO:
		alertResposta = getString(R.string.labelRespDesconto);
		
		valorfinal = dValorPresente - (dValorPresente * dTaxa * .01);
		break;

	case ACRESCIMO:
		alertResposta = getString(R.string.labelRespAcrescimo);
		
		valorfinal = dValorPresente + (dValorPresente * dTaxa * .01);
		break;
	default:
		break;
	}
       labelResp.setText(alertResposta);
       resp.setText(String.format("%.02f", valorfinal));  
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		//outState.putDouble(DESC_TOTAL, valorDeEntrada);
		//outState.putDouble(PERCENTUAL, percentualCorrente);
	}

	public void escolherTipoCalculo(View view) {
		   
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    
	    switch(view.getId()) {
	        case R.id.idRadioDesconto:
	            if (checked)
	            	tipoCalculo= TipoCalculo.DESCONTO; 
	                calcular();
	            break;
	        case R.id.idRadioAcrescimo:
	            if (checked)
	            	tipoCalculo= TipoCalculo.ACRESCIMO; 
	            	calcular();
	            break;
	    }
	}
	private final int SALVAR_ID = Menu.FIRST;
	private final int BUSCAR_ID = Menu.FIRST + 1;
	private final int ATUALIZAR_ID = Menu.FIRST + 2;
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        

		menu.add(Menu.NONE, SALVAR_ID, Menu.NONE,getString(R.string.enviar))
            .setIcon( R.drawable.compartilhar)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add(Menu.NONE, BUSCAR_ID, Menu.NONE,getString(R.string.visualizar))
            .setIcon(R.drawable.visualizar)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add(Menu.NONE, ATUALIZAR_ID, Menu.NONE,getString(R.string.limpar))
            .setIcon( R.drawable.atualizar)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		// TODO Auto-generated method stub
	
		switch (item.getItemId()) {
		case SALVAR_ID:
             
			String msg =alertResposta+"\n"+ String.format(getString(R.string.respostadesconto),dValorPresente,dTaxa,valorfinal );
			ViewUtil.enviarMensagem(msg, this);
			
		break;

		case BUSCAR_ID:
		
			       
            AlertDialog.Builder alertaBuscar = 
            new AlertDialog.Builder(this);         
           
            LayoutInflater inflater =  this.getLayoutInflater();
            View alertaView =inflater.inflate(R.layout.alert, null);
            ViewUtil alertaBuilder = new ViewUtil(alertaView);
            
            try {
				//Valor Presente
            	alertaBuilder.builderTextView(R.id.label1, getString(R.string.valor_presente));
				alertaBuilder.builderTextView(R.id.resp1,  String.format("%.02f", dValorPresente));
				//Taxa
            	alertaBuilder.builderTextView(R.id.label2, getString(R.string.taxa));
				alertaBuilder.builderTextView(R.id.resp2, String.format("%.02f", dTaxa)+"%");
				// Valor Futuro
            	alertaBuilder.builderTextView(R.id.label3, getString(R.string.valor_futuro));
				alertaBuilder.builderTextView(R.id.resp3,  getString(R.string.dinheiro)+" " +String.format("%.02f", valorfinal));
				
				
			} catch (ViewException e) {
				e.printStackTrace();
			}
            
            alertaBuscar.setTitle(alertResposta);
            alertaBuscar.setView(alertaView);
            alertaBuscar.setIcon(R.drawable.alerta);
            
            alertaBuscar.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                 // String value = input.getText().toString();
                 // Do something with value!
                 //You will get input data in this variable. 
                	dialog.cancel();
          
                  }
                });
           
           AlertDialog choicesDialog = alertaBuscar.create();
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
	      	          Intent intent = new Intent(getApplicationContext(),DescontoSimples.class);
	      	        DescontoSimples.this.finish();  
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
	Point p;
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

	   int[] location = new int[2];
	  // Button button = (Button) findViewById(R.id.show_popup);

	   // Get the x, y location and store it in the location[] array
	   // location[0] = x, location[1] = y.
	   //button.getLocationOnScreen(location);

	   //Initialize the Point with x, and y positions
	   p = new Point();
	   p.x = location[0];
	   p.y = location[1];
	}


	
}
