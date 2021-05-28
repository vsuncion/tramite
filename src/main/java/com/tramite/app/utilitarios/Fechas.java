package com.tramite.app.utilitarios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Fechas {
	
	public static Date convertStringToDate(String fecha){
		DateFormat  formatoFecha = new SimpleDateFormat("yyyy-MM-dd");	 
		 Date fechaDate = null ;
		 try {			
			 fechaDate = formatoFecha.parse(fecha);		    
		} catch (Exception e) {			
		}	
		return fechaDate;
	}
	
	
	public static String convertDateToString(Date fecha){
		DateFormat   formatoFecha = new SimpleDateFormat("dd-MM-yyyy");	 
		String fechaDate = null;
		 try {			
			   fechaDate = formatoFecha.format(fecha) ;	    
		} catch (Exception e) {			
		}	
		return fechaDate;
	}
	
	public static Date fechaActual() {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		format.setCalendar(cal);
		return cal.getTime();
	}
	 

}
