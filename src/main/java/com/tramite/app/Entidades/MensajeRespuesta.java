package com.tramite.app.Entidades;

import java.io.Serializable;

public class MensajeRespuesta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	private int codigo;
	private String mensaje;
	public MensajeRespuesta() {
		super();
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	
	
}
