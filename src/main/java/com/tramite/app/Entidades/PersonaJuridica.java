package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

public class PersonaJuridica implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long NIDPERJURIDICAPK;
	private Long NIDPERSONAFK;
	private String VRAZONSOCIAL;
	private String VRUC;
	private String VDIRECCION;
	private int NESTADO;
	private Date DFECREGISTRO;
	private Long NUSUREGISTRA;
	private Date DFECMODIFICA;
	private Long NUSUMODIFICA;
	private Date DFECELIMINA;
	private Long NUSUELIMINA;
	
	public PersonaJuridica() { 
	}

	public Long getNIDPERJURIDICAPK() {
		return NIDPERJURIDICAPK;
	}

	public void setNIDPERJURIDICAPK(Long nIDPERJURIDICAPK) {
		NIDPERJURIDICAPK = nIDPERJURIDICAPK;
	}

	public Long getNIDPERSONAFK() {
		return NIDPERSONAFK;
	}

	public void setNIDPERSONAFK(Long nIDPERSONAFK) {
		NIDPERSONAFK = nIDPERSONAFK;
	}

	public String getVRAZONSOCIAL() {
		return VRAZONSOCIAL;
	}

	public void setVRAZONSOCIAL(String vRAZONSOCIAL) {
		VRAZONSOCIAL = vRAZONSOCIAL;
	}

	public String getVRUC() {
		return VRUC;
	}

	public void setVRUC(String vRUC) {
		VRUC = vRUC;
	}

	public String getVDIRECCION() {
		return VDIRECCION;
	}

	public void setVDIRECCION(String vDIRECCION) {
		VDIRECCION = vDIRECCION;
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
	
	

}
