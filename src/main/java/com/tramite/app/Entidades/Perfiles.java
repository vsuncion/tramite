package com.tramite.app.Entidades;

import java.io.Serializable;

public class Perfiles implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long nidperfilpk;
	private String vnombre;
	private String  vdescripcion;
	private Long  NITEM;
	
	public Perfiles() {
		super();
	}

	 

	public Long getNidperfilpk() {
		return nidperfilpk;
	}



	public void setNidperfilpk(Long nidperfilpk) {
		this.nidperfilpk = nidperfilpk;
	}



	public String getVnombre() {
		return vnombre;
	}

	public void setVnombre(String vnombre) {
		this.vnombre = vnombre;
	}

	public String getVdescripcion() {
		return vdescripcion;
	}

	public void setVdescripcion(String vdescripcion) {
		this.vdescripcion = vdescripcion;
	}



	public Long getNITEM() {
		return NITEM;
	}



	public void setNITEM(Long nITEM) {
		NITEM = nITEM;
	}

	 


}
