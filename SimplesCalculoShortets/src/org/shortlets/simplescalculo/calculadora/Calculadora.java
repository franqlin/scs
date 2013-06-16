package org.shortlets.simplescalculo.calculadora;

import org.joda.time.DateTime;
import org.joda.time.Days;

import android.util.Log;

public class Calculadora {
	/**
	 * C‡lculo dos Juros Simples J=VP*j*t
	 * @param valorPresente
	 * @param txJuros
	 * @param tempo
	 * @param periodo
	 * @param taxaAoPeriodo
	 * @return  Valor Futuro VF= VP+J
	 */
	public static double calcularJurosSimples(double valorPresente,double txJuros, double tempo, Periodo periodo,Periodo taxaAoPeriodo) {
		
		txJuros = txJuros * .01;
		TaxaTempo taxaTempo = new TaxaTempo();
		taxaTempo.calcularSimples(txJuros, tempo, periodo, taxaAoPeriodo);
		
		return valorPresente  + (valorPresente * taxaTempo.taxa * taxaTempo.tempo);
	}
	/**
	 * VF =VP(1+j)^t
	 * @param valorPresente
	 * @param txJuros
	 * @param tempo
	 * @param periodo
	 * @param taxaAoPeriodo
	 * @return valorFuturo
	 */

	public static double calcularJurosCompostos(double valorPresente,double txJuros, double tempo, Periodo periodo,Periodo taxaAoPeriodo) {
		
		txJuros = txJuros * .01;
		TaxaTempo taxaTempo = new TaxaTempo();
		taxaTempo.calcularComposto(txJuros, tempo, periodo, taxaAoPeriodo);
		
		return valorPresente * (Math.pow(1 + taxaTempo.taxa, taxaTempo.tempo));
	}
    /*
     * Classe para calcular taxa de Juros em fun‹o do Tempo
     * @author franqlin
     *
     */
	public static class TaxaTempo {
		protected double tempo;
		protected double taxa;

		public void calcularSimples(double _txJuros, double _tempo,Periodo periodo, Periodo taxaAoPeriodo) {
			switch (periodo) {
			case ANO:
				_tempo = _tempo * 12;
				_txJuros = taxaAoPeriodo.equals(Periodo.ANO) ? _txJuros / 12
						: taxaAoPeriodo.equals(Periodo.DIA) ? _txJuros * 365 / 12
								: _txJuros;
				break;

			case MES:
				_tempo = taxaAoPeriodo.equals(Periodo.ANO) ? _tempo / 12
						: taxaAoPeriodo.equals(Periodo.DIA) ? _tempo / 12
								: _tempo;
				_txJuros = taxaAoPeriodo.equals(Periodo.DIA) ? _txJuros * 360
						: _txJuros;
				break;

			case DIA:
				_tempo = taxaAoPeriodo.equals(Periodo.ANO) ? _tempo / 365
						: taxaAoPeriodo.equals(Periodo.MES) ? _tempo / 360
								: _tempo;
				_txJuros = taxaAoPeriodo.equals(Periodo.MES) ? _txJuros * 12
						: _txJuros;
				break;
			}
			this.tempo = _tempo;
			this.taxa = _txJuros;
		}

		/**
		 * Converter taxa de juros Anual em Mensal=( ( 1 + i )^ ( 1/12 ) )
		 * Converter taxa de juros Mensal para di‡ria=( ( 1 + i )^ ( 1/30 )
		 * Converter taxa de juros mensal em anual=( ( 1 + i )^ 12 ) - 1)
		 * @param _txJuros
		 * @param _tempo
		 * @param periodo
		 * @param taxaAoPeriodo
		 */
		public void calcularComposto(double _txJuros, double _tempo,Periodo periodo, Periodo taxaAoPeriodo) {


			switch (periodo) {
			
			case ANO:
				_txJuros = taxaAoPeriodo.equals(Periodo.ANO) ? _txJuros
						: taxaAoPeriodo.equals(Periodo.DIA) ? (Math
								.pow(1 + _txJuros, 365)) - 1 : (Math.pow(
								1 + _txJuros, 12)) - 1;
				break;

			case MES:
				_tempo = taxaAoPeriodo.equals(Periodo.ANO) ? _tempo / 12
						: taxaAoPeriodo.equals(Periodo.DIA) ? _tempo
								: _tempo;

				_txJuros = taxaAoPeriodo.equals(Periodo.DIA) ? (Math
						.pow(1 + _txJuros, 30)) - 1 : _txJuros;
				break;

			case DIA:
				_tempo = taxaAoPeriodo.equals(Periodo.ANO) ? _tempo / 365
						: taxaAoPeriodo.equals(Periodo.MES) ? _tempo / 360
								: _tempo;
				_txJuros = taxaAoPeriodo.equals(Periodo.MES) ? (Math
						.pow(1 + _txJuros, 12)) - 1 : _txJuros;
				Log.i("juros::", "calcularComposto::tempo" + _tempo);
				Log.i("juros::", "calcularComposto::taxa" + _txJuros);
				break;
			}
			this.tempo = _tempo;
			this.taxa = _txJuros;
		}

	}
	
	public static  int calcularDiferencaEntreDatas(DateTime d1,DateTime d2){
	
		d1 = (d1==null)?DateTime.now():d1;
		d2 = (d2==null)?DateTime.now():d2;

		return Days.daysBetween(d1, d2).getDays();
	}
	
public static double calcularBoleto(double valorAtual,double multa,double juros,DateTime dtVcto,DateTime dtPgto,TipoTaxa tpMulta,TipoTaxa tpJuros){

	   double ValorAtualizado=0.0;	
	   if (valorAtual == 0){
			return ValorAtualizado;
	   }else{
		   ValorAtualizado =valorAtual;
	   }
		int dias = calcularDiferencaEntreDatas(dtVcto, dtPgto);

		if (dias > 0) {
			if (tpMulta.equals(TipoTaxa.PERCENTUAL)) {

				multa = valorAtual * multa * .01;

			}// fim tx PERCENTUAL
			if (tpJuros.equals(TipoTaxa.DINHEIRO)) {		
				juros = dias * juros;
			} else {
				juros = juros*.01 * dias * valorAtual;
				//Log.i("Calcular juros percentual", "Calcular =" + juros);
			}
			
			ValorAtualizado =valorAtual + multa + juros;
		}// fim if dias>0
		Log.i("__________TOTAL DE DIAS", " " + dias);
		return ValorAtualizado;
}

}
