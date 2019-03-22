package controller;
import java.math.*;
public class Utilitary {
public static double round(double value, int places) {//zaokruhlenie double na dve desatinne miesta
	    
		BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
