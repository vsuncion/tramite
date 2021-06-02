package com.tramite.app.utilitarios;

import java.io.File;
import java.util.Calendar;

import org.springframework.web.multipart.MultipartFile;

public class ConstantesArchivos {

	public static int getAnioActual() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public static int getMesActual() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	public static int getDiaActual() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}

	public static String getObtenerRutaCarpetas() {
		return getAnioActual() + System.getProperty("file.separator") + getMesActual()
				+ System.getProperty("file.separator") + getDiaActual() + System.getProperty("file.separator");
	}

	public static boolean isEmptyFile(MultipartFile mpf) {

		if (mpf == null || (mpf != null && (mpf.getSize() == 0 || toCadena(mpf.getOriginalFilename()).equals("")))) {
			return true;
		}
		return false;
	}

	public static boolean existeFileEnRuta(String rutaArchivo) {

		File fichero = new File(rutaArchivo);

		if (!fichero.exists()) {
			return false;
		}
		return true;
	}

	public static String toCadena(Object valor) {
		if (valor == null) {
			return "";
		}

		return valor.toString();

	}

	public static String getCorrelativoArchivo() {
		String codigoFinal;
		Calendar fecha = Calendar.getInstance();
		int anio = fecha.get(Calendar.YEAR);
		int mes = fecha.get(Calendar.MONTH) + 1;
		int dia = fecha.get(Calendar.DAY_OF_MONTH);
		int hora = fecha.get(Calendar.HOUR_OF_DAY);
		int minuto = fecha.get(Calendar.MINUTE);
		int segundo = fecha.get(Calendar.SECOND);

		int codigoAdicional = (int) (100000 * Math.random());
		codigoFinal = String.valueOf(dia)+String.valueOf(mes)+String.valueOf(anio)+String.valueOf(hora)+String.valueOf(minuto)+String.valueOf(segundo)+String.valueOf(codigoAdicional);
		return codigoFinal;
	}

}
