package com.tramite.app.Entidades;

import java.io.Serializable;

public class ReporteExpediente implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String VESTADOCUEMNTO;
	private String VNOMBREOFICINA;
	private int NCANTIDAD;
	
 
	public ReporteExpediente() {
		super();
	}


	public String getVESTADOCUEMNTO() {
		return VESTADOCUEMNTO;
	}


	public void setVESTADOCUEMNTO(String vESTADOCUEMNTO) {
		VESTADOCUEMNTO = vESTADOCUEMNTO;
	}


	public String getVNOMBREOFICINA() {
		return VNOMBREOFICINA;
	}


	public void setVNOMBREOFICINA(String vNOMBREOFICINA) {
		VNOMBREOFICINA = vNOMBREOFICINA;
	}


	public int getNCANTIDAD() {
		return NCANTIDAD;
	}


	public void setNCANTIDAD(int nCANTIDAD) {
		NCANTIDAD = nCANTIDAD;
	}
	
	
	 
	
}
