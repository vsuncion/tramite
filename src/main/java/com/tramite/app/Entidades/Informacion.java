package com.tramite.app.Entidades;

import java.io.Serializable;

public class Informacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String RAZONSOCIAL;
	private String RUC;
	private String TITULOALTERNO;
	private String SIGLAS;
	private String DIRECCION;
	private String PAGINAWEB;
	private String CORREO;
	private String TELEFONO;
	
	public Informacion() {
		super();
	}

	public String getRAZONSOCIAL() {
		return RAZONSOCIAL;
	}

	public void setRAZONSOCIAL(String rAZONSOCIAL) {
		RAZONSOCIAL = rAZONSOCIAL;
	}

	public String getRUC() {
		return RUC;
	}

	public void setRUC(String rUC) {
		RUC = rUC;
	}

	public String getTITULOALTERNO() {
		return TITULOALTERNO;
	}

	public void setTITULOALTERNO(String tITULOALTERNO) {
		TITULOALTERNO = tITULOALTERNO;
	}

	public String getSIGLAS() {
		return SIGLAS;
	}

	public void setSIGLAS(String sIGLAS) {
		SIGLAS = sIGLAS;
	}

	public String getDIRECCION() {
		return DIRECCION;
	}

	public void setDIRECCION(String dIRECCION) {
		DIRECCION = dIRECCION;
	}

	public String getPAGINAWEB() {
		return PAGINAWEB;
	}

	public void setPAGINAWEB(String pAGINAWEB) {
		PAGINAWEB = pAGINAWEB;
	}

	public String getCORREO() {
		return CORREO;
	}

	public void setCORREO(String cORREO) {
		CORREO = cORREO;
	}

	public String getTELEFONO() {
		return TELEFONO;
	}

	public void setTELEFONO(String tELEFONO) {
		TELEFONO = tELEFONO;
	}

	@Override
	public String toString() {
		return "Informacion [RAZONSOCIAL=" + RAZONSOCIAL + ", RUC=" + RUC + ", TITULOALTERNO=" + TITULOALTERNO
				+ ", SIGLAS=" + SIGLAS + ", DIRECCION=" + DIRECCION + ", PAGINAWEB=" + PAGINAWEB + ", CORREO=" + CORREO
				+ ", TELEFONO=" + TELEFONO + "]";
	} 
	
	
	
}
