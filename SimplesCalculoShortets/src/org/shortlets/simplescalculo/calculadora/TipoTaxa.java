package org.shortlets.simplescalculo.calculadora;
import org.shortlets.simplescalculo.R;

import android.content.res.Resources;



public enum TipoTaxa {
  PERCENTUAL(R.string.txPerc)
 ,DINHEIRO(R.string.dinheiro);
 
 private int resourcesId=-1;
  TipoTaxa(int idRes) {
	resourcesId = idRes;
}
 public String valor(Resources r) {
     if (-1 != resourcesId) return (r.getString(resourcesId));
     return (this.toString());
 }
}

