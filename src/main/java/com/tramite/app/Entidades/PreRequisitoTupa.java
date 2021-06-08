package com.tramite.app.Entidades;

import java.io.Serializable;

public class PreRequisitoTupa implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long IDPREREQUISITOPK;
	private long IDPREEXPEDIENTEFK;
	private long TUPACFK;
	private long REQUISITOFK;
	private String VNOMBRE_ARCHIVO;
	private String VUBICACION_ARCHIVO;
	private String VEXTENSION;
	private String  VNOMBRE;
	
	public PreRequisitoTupa() {
	
	}
	public long getIDPREREQUISITOPK() {
		return IDPREREQUISITOPK;
	}
	public void setIDPREREQUISITOPK(long iDPREREQUISITOPK) {
		IDPREREQUISITOPK = iDPREREQUISITOPK;
	}
	public long getIDPREEXPEDIENTEFK() {
		return IDPREEXPEDIENTEFK;
	}
	public void setIDPREEXPEDIENTEFK(long iDPREEXPEDIENTEFK) {
		IDPREEXPEDIENTEFK = iDPREEXPEDIENTEFK;
	}
	public long getTUPACFK() {
		return TUPACFK;
	}
	public void setTUPACFK(long tUPACFK) {
		TUPACFK = tUPACFK;
	}
	public long getREQUISITOFK() {
		return REQUISITOFK;
	}
	public void setREQUISITOFK(long rEQUISITOFK) {
		REQUISITOFK = rEQUISITOFK;
	}
	public String getVNOMBRE_ARCHIVO() {
		return VNOMBRE_ARCHIVO;
	}
	public void setVNOMBRE_ARCHIVO(String vNOMBRE_ARCHIVO) {
		VNOMBRE_ARCHIVO = vNOMBRE_ARCHIVO;
	}
	public String getVUBICACION_ARCHIVO() {
		return VUBICACION_ARCHIVO;
	}
	public void setVUBICACION_ARCHIVO(String vUBICACION_ARCHIVO) {
		VUBICACION_ARCHIVO = vUBICACION_ARCHIVO;
	}
	public String getVEXTENSION() {
		return VEXTENSION;
	}
	public void setVEXTENSION(String vEXTENSION) {
		VEXTENSION = vEXTENSION;
	}
	public String obtenerRutaAbsoluArchivo() {

		return this.getVUBICACION_ARCHIVO() + this.getVNOMBRE_ARCHIVO() + "." + this.getVEXTENSION();
	}
	public String getVNOMBRE() {
		return VNOMBRE;
	}
	public void setVNOMBRE(String vNOMBRE) {
		VNOMBRE = vNOMBRE;
	}
	
}
