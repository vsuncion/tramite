package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

public class Trabajadores implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long NIDTRABAJADORPK;
	private Long NIDPERSONAFK;
	private Long NPROFESIONFK;
	private Long NOFICINAFK;
	private Date DFECINGRESO;
	private int NESTADO;
	private Date DFECREGISTRO;
	private Long NUSUREGISTRA;
	private Date DFECMODIFICA;
	private Long NUSUMODIFICA;
	private Date DFECELIMINA;
	private Long NUSUELIMINA;
	
	
	
	public Trabajadores() {
		super();
	}
	
	
	
	
	public Long getNIDTRABAJADORPK() {
		return NIDTRABAJADORPK;
	}




	public void setNIDTRABAJADORPK(Long nIDTRABAJADORPK) {
		NIDTRABAJADORPK = nIDTRABAJADORPK;
	}




	public Long getNIDPERSONAFK() {
		return NIDPERSONAFK;
	}
	public void setNIDPERSONAFK(Long nIDPERSONAFK) {
		NIDPERSONAFK = nIDPERSONAFK;
	}
	public Long getNPROFESIONFK() {
		return NPROFESIONFK;
	}
	public void setNPROFESIONFK(Long nPROFESIONFK) {
		NPROFESIONFK = nPROFESIONFK;
	}
	public Long getNOFICINAFK() {
		return NOFICINAFK;
	}
	public void setNOFICINAFK(Long nOFICINAFK) {
		NOFICINAFK = nOFICINAFK;
	}
	public Date getDFECINGRESO() {
		return DFECINGRESO;
	}
	public void setDFECINGRESO(Date dFECINGRESO) {
		DFECINGRESO = dFECINGRESO;
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

	@Override
	public String toString() {
		return "Trabajadores [NIDPERSONAFK=" + NIDPERSONAFK + ", NPROFESIONFK=" + NPROFESIONFK + ", NOFICINAFK="
				+ NOFICINAFK + ", DFECINGRESO=" + DFECINGRESO + ", NESTADO=" + NESTADO + ", DFECREGISTRO="
				+ DFECREGISTRO + ", NUSUREGISTRA=" + NUSUREGISTRA + ", DFECMODIFICA=" + DFECMODIFICA + ", NUSUMODIFICA="
				+ NUSUMODIFICA + ", DFECELIMINA=" + DFECELIMINA + ", NUSUELIMINA=" + NUSUELIMINA + "]";
	}
	
	
	
}
