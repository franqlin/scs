package org.shortlets.simplescalculo.util;

import org.joda.time.DateTime;

import android.annotation.SuppressLint;

public class ViewFormatacaoUtil {

	/**
	 * Convert de CharSequence para Double
	 * @param 
	 * @return
	 */
	@SuppressLint("UseValueOf")
	public static Double EditTextToDouble(CharSequence s){
		Double val;
		try{
			val = Double.parseDouble(s.toString());
		}catch (NumberFormatException e) {
			val = new Double(0.0);
		}
		
		return val;
	}
	/**
	 * Converte de CharSequence para Inteiro
	 * @param s
	 * @return
	 */
	public static Integer EditTextToInteiro(CharSequence s){
		Integer val;
		try{
			val = Integer.parseInt(s.toString());
		}catch (NumberFormatException e) {
			val = new Integer(0);
		}
		
		return val;
	}
	   public static DateTime createDateTime(String data){
		   if (data==null||data.equals("")) return DateTime.now();
		  
		   String[] str = data.split("/");
		   int dia =  Integer.valueOf(str[0]).intValue(); 
		   int mes = Integer.valueOf(str[1]).intValue();
		   int ano = Integer.valueOf(str[2]).intValue();;
		   
		   return new DateTime(ano,mes,dia,12,0,0,0);
		   
	   }

}
