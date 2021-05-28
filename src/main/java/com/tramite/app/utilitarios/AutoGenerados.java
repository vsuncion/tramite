package com.tramite.app.utilitarios;

import java.util.Calendar;

public class AutoGenerados {

	public static final String getCorrelativoArchivo() {
		String codigoFinal;
        Calendar fecha = Calendar.getInstance();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        int codigoAdicional=(int)(100000  * Math.random());

        codigoFinal = String.valueOf(dia)+String.valueOf(mes)+String.valueOf(anio)+String.valueOf(hora)+String.valueOf(minuto)+String.valueOf(segundo)+String.valueOf(codigoAdicional);
		return codigoFinal;
    }
	
	
}
