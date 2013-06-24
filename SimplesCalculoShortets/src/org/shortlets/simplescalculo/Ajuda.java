package org.shortlets.simplescalculo;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Ajuda extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajuda);
		// getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ab_));

		StringBuilder builder = new StringBuilder();
		for (String dialog : DIALOGUE) {
			builder.append(dialog).append("\n\n");
		}

		TextView bunchOfText = (TextView) findViewById(R.id.bunch_of_text);
		bunchOfText.setText(builder.toString());
	}

	public static final String[] DIALOGUE = new String[] {
			"Quando expressamos que 20%(vinte por cento) do valor total de  um valor presente"
					+ "estamos afirmando  que existe 20 num universo total de 100 ou que a razão é "
					+ "de 20 para 100. A porcentagem é muito utilizado no mercado financeiro, seja na "
					+ "hora de calcular um lucro ou obter um desconto, ou medir taxas."
					+ "Exemplo:"
					+ "Calcular desconto/lucro"
					+ "225 x 5%"
					+ "A quantidade ou valor de:225"
					+ "Porcentagem de:5%"
					+ "corresponde à:11.25"
					+ " com desconto:	213.75"
					+ " com acréscimo: 236.25", " ", " "

	};

}
