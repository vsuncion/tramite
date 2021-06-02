package com.tramite.app.Servicios;

public interface FijaServicio {

		
	public String cuerpoCorreo(String nombreSolicitante,String codigoValidacion);
	public void enviarCorreo(String correoDestino,String cuerpo);
	
}
