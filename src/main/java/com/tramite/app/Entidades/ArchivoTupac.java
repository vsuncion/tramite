package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

public class ArchivoTupac implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long  NITEM;
	private Long NARCHIVOTUPAPK;
	private Long EXPEDIENTEFK;
	private Long TUPAFK;
	private Long REQUISITOFK;
	private String VNOMBRE_ARCHIVO;
	private String VUBICACION_ARCHIVO;
	private String VEXTENSION;
	int NESTADO;
	private Date DFECREGISTRO;
	private Long NUSUREGISTRA;
	private Date DFECMODIFICA;
	private Long NUSUMODIFICA;
	private Date DFECELIMINA;
	private Long NUSUELIMINA;
	private String VNOMBRE;
	
	public ArchivoTupac() {
		super();
	}

	
	public Long getNITEM() {
		return NITEM;
	}


	public void setNITEM(Long nITEM) {
		NITEM = nITEM;
	}


	public Long getNARCHIVOTUPAPK() {
		return NARCHIVOTUPAPK;
	}

	public void setNARCHIVOTUPAPK(Long nARCHIVOTUPAPK) {
		NARCHIVOTUPAPK = nARCHIVOTUPAPK;
	}

	public Long getEXPEDIENTEFK() {
		return EXPEDIENTEFK;
	}

	public void setEXPEDIENTEFK(Long eXPEDIENTEFK) {
		EXPEDIENTEFK = eXPEDIENTEFK;
	}

	public Long getTUPAFK() {
		return TUPAFK;
	}

	public void setTUPAFK(Long tUPAFK) {
		TUPAFK = tUPAFK;
	}

	public Long getREQUISITOFK() {
		return REQUISITOFK;
	}

	public void setREQUISITOFK(Long rEQUISITOFK) {
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
	public int getNESTADO() {
		return NESTADO;
	}

	public void setNESTADO(int nESTADO) {
		NESTADO = nESTADO;
	}

	public Date getDFECREGISTRO() {
		return DFECREGISTRO;
	}

	public void setDFECREGISTRO(Date dFECREGISTRO) {
		DFECREGISTRO = dFECREGISTRO;
	}

	public Long getNUSUREGISTRA() {
		return NUSUREGISTRA;
	}

	public void setNUSUREGISTRA(Long nUSUREGISTRA) {
		NUSUREGISTRA = nUSUREGISTRA;
	}

	public Date getDFECMODIFICA() {
		return DFECMODIFICA;
	}

	public void setDFECMODIFICA(Date dFECMODIFICA) {
		DFECMODIFICA = dFECMODIFICA;
	}

	public Long getNUSUMODIFICA() {
		return NUSUMODIFICA;
	}

	public void setNUSUMODIFICA(Long nUSUMODIFICA) {
		NUSUMODIFICA = nUSUMODIFICA;
	}

	public Date getDFECELIMINA() {
		return DFECELIMINA;
	}

	public void setDFECELIMINA(Date dFECELIMINA) {
		DFECELIMINA = dFECELIMINA;
	}

	public Long getNUSUELIMINA() {
		return NUSUELIMINA;
	}

	public void setNUSUELIMINA(Long nUSUELIMINA) {
		NUSUELIMINA = nUSUELIMINA;
	}

	public String getVNOMBRE() {
		return VNOMBRE;
	}

	public void setVNOMBRE(String vNOMBRE) {
		VNOMBRE = vNOMBRE;
	}
	
	

}
