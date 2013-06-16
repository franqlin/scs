package org.shortlets.simplescalculo.util;

import java.util.Calendar;

import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

public class ViewSelectDatePopUp extends DialogFragment implements DatePickerDialog.OnDateSetListener{

	 private Activity activity;
	 private EditText campoData;
	 private int idCampo;
	 private DateTime dataTime;
	 
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	final Calendar calendar = Calendar.getInstance();
	int aa = calendar.get(Calendar.YEAR);
	int mm = calendar.get(Calendar.MONTH);
	int dd = calendar.get(Calendar.DAY_OF_MONTH);
	return new DatePickerDialog(getActivity(), this, aa, mm, dd);
	}
	@Override
	public void onDateSet(DatePicker view, int ano, int mes,int dia) {
		pupularCampoData(ano, mes, dia);
		
	}
	public void  assinarActivity(final Activity _activity){
		this.activity = _activity;
	}
	public void setCampoData(int id){
		this.idCampo =id;
	}
    private void pupularCampoData( int ano, int mes,int dia){
		campoData = (EditText) activity.findViewById(idCampo);
		campoData.setText(dia + "/" + (mes+1) + "/" + ano);
		/*try {
		
					dataTime = new DateTime(ano_, mes_, dia_, 12, 0, 0, 0);
					Log.i(" Config DATA_TIME","  "+dataTime);
		
			
		} catch (IllegalFieldValueException e) {
			Log.i(" ","Erro setando nova data "+dia + "/" + (mes+1) + "/" + ano);
			Log.println(Log.ERROR, "Erro polulando data", e.getMessage());
		}*/
	}
    public DateTime getDateTime() {
		return dataTime==null? DateTime.now(): this.dataTime;
	}
    
    
}
