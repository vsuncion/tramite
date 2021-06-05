package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

public class ArchivoMovimiento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long NIDARCHIVOMOVIMIENTO;
	private Long NEXPEDIENTEFK;
	private Long NMOVIMIENTOFK;
	private Long NOFICINAFK;
	private Date DFECHAREGISTRO;
	private String VNOMBRE_ARCHIVO;
	private String VUBICACION_ARCHIVO;
	private String VEXTENSION;

	public ArchivoMovimiento() {

	}

	public Long getNIDARCHIVOMOVIMIENTO() {
		return NIDARCHIVOMOVIMIENTO;
	}

	public void setNIDARCHIVOMOVIMIENTO(Long nIDARCHIVOMOVIMIENTO) {
		NIDARCHIVOMOVIMIENTO = nIDARCHIVOMOVIMIENTO;
	}

	public Long getNEXPEDIENTEFK() {
		return NEXPEDIENTEFK;
	}

	public void setNEXPEDIENTEFK(Long nEXPEDIENTEFK) {
		NEXPEDIENTEFK = nEXPEDIENTEFK;
	}

	public Long getNMOVIMIENTOFK() {
		return NMOVIMIENTOFK;
	}

	public void setNMOVIMIENTOFK(Long nMOVIMIENTOFK) {
		NMOVIMIENTOFK = nMOVIMIENTOFK;
	}

	public Long getNOFICINAFK() {
		return NOFICINAFK;
	}

	public void setNOFICINAFK(Long nOFICINAFK) {
		NOFICINAFK = nOFICINAFK;
	}

	public Date getDFECHAREGISTRO() {
		return DFECHAREGISTRO;
	}

	public void setDFECHAREGISTRO(Date dFECHAREGISTRO) {
		DFECHAREGISTRO = dFECHAREGISTRO;
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

}
