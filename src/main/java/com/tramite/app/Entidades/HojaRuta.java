package com.tramite.app.Entidades;

import java.io.Serializable;

public class HojaRuta implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private String NITEM;
	private String TIPO_DOCUMENTO;
	private String VFECHAOFICINA;
	private String OFICINA_ORIGEN;
	private String OFICINA_DESTINO;
	private String ESTADO_DOCUMENTO;
	private String VFECHARECEPCION;
	private String VOBSERVACION;
	private String ANIO;
	private String VCODIGOEXPEDIENTE;
	
	public HojaRuta() {
		 
	}

	public String getNITEM() {
		return NITEM;
	}

	public void setNITEM(String nITEM) {
		NITEM = nITEM;
	}

	public String getTIPO_DOCUMENTO() {
		return TIPO_DOCUMENTO;
	}

	public void setTIPO_DOCUMENTO(String tIPO_DOCUMENTO) {
		TIPO_DOCUMENTO = tIPO_DOCUMENTO;
	}

	public String getVFECHAOFICINA() {
		return VFECHAOFICINA;
	}

	public void setVFECHAOFICINA(String vFECHAOFICINA) {
		VFECHAOFICINA = vFECHAOFICINA;
	}

	public String getOFICINA_ORIGEN() {
		return OFICINA_ORIGEN;
	}

	public void setOFICINA_ORIGEN(String oFICINA_ORIGEN) {
		OFICINA_ORIGEN = oFICINA_ORIGEN;
	}

	public String getOFICINA_DESTINO() {
		return OFICINA_DESTINO;
	}

	public void setOFICINA_DESTINO(String oFICINA_DESTINO) {
		OFICINA_DESTINO = oFICINA_DESTINO;
	}

	public String getESTADO_DOCUMENTO() {
		return ESTADO_DOCUMENTO;
	}

	public void setESTADO_DOCUMENTO(String eSTADO_DOCUMENTO) {
		ESTADO_DOCUMENTO = eSTADO_DOCUMENTO;
	}

	public String getVFECHARECEPCION() {
		return VFECHARECEPCION;
	}

	public void setVFECHARECEPCION(String vFECHARECEPCION) {
		VFECHARECEPCION = vFECHARECEPCION;
	}

	public String getVOBSERVACION() {
		return VOBSERVACION;
	}

	public void setVOBSERVACION(String vOBSERVACION) {
		VOBSERVACION = vOBSERVACION;
	}

	public String getANIO() {
		return ANIO;
	}

	public void setANIO(String aNIO) {
		ANIO = aNIO;
	}

	public String getVCODIGOEXPEDIENTE() {
		return VCODIGOEXPEDIENTE;
	}

	public void setVCODIGOEXPEDIENTE(String vCODIGOEXPEDIENTE) {
		VCODIGOEXPEDIENTE = vCODIGOEXPEDIENTE;
	}

	 
}
