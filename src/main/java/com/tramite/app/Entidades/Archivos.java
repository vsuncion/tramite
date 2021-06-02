package com.tramite.app.Entidades;

import java.io.Serializable;

public class Archivos implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String ruta;
	private String extension;   
	private String nombreExtension;
	private String rutacompleta;
	private String rutaNombreArchivo;
	private boolean verificarCarga;
	private boolean verificarUbicacion;
	private String mensaje;
	
	public Archivos() { 
	}

	 

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	 

	public String getNombreExtension() {
		return nombreExtension;
	}

	public void setNombreExtension(String nombreExtension) {
		this.nombreExtension = nombreExtension;
	}

	public String getRutacompleta() {
		return rutacompleta;
	}

	public void setRutacompleta(String rutacompleta) {
		this.rutacompleta = rutacompleta;
	}

	public String getRutaNombreArchivo() {
		return rutaNombreArchivo;
	}

	public void setRutaNombreArchivo(String rutaNombreArchivo) {
		this.rutaNombreArchivo = rutaNombreArchivo;
	}

	public boolean isVerificarCarga() {
		return verificarCarga;
	}

	public void setVerificarCarga(boolean verificarCarga) {
		this.verificarCarga = verificarCarga;
	}

	public boolean isVerificarUbicacion() {
		return verificarUbicacion;
	}

	public void setVerificarUbicacion(boolean verificarUbicacion) {
		this.verificarUbicacion = verificarUbicacion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	 
}

