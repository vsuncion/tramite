package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

public class Feriados implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long NIDFERIADOPK;         
	private Date DFERIADO;		             
	private String VDESCRIPCION;	    
	private Long NESTADO;				 
	private Long NORDEN;				 
	private Date DFECREGISTRO; 
	private Long NUSUREGISTRA;         
	private Date DFECMODIFICA; 
	private Long NUSUMODIFICA;         
	private Date DFECELIMINA;  
	private Long NUSUELIMINA;
	
	private String VFERIADO;	
	private String CAJABUSQUEDA;
	private Long NITEM;
 
	public Feriados() { 
	}
	public Long getNIDFERIADOPK() {
		return NIDFERIADOPK;
	}
	public void setNIDFERIADOPK(Long nIDFERIADOPK) {
		NIDFERIADOPK = nIDFERIADOPK;
	}
	public Date getDFERIADO() {
		return DFERIADO;
	}
	public void setDFERIADO(Date dFERIADO) {
		DFERIADO = dFERIADO;
	}
	public String getVDESCRIPCION() {
		return VDESCRIPCION;
	}
	public void setVDESCRIPCION(String vDESCRIPCION) {
		VDESCRIPCION = vDESCRIPCION;
	}
	public Long getNESTADO() {
		return NESTADO;
	}
	public void setNESTADO(Long nESTADO) {
		NESTADO = nESTADO;
	}
	public Long getNORDEN() {
		return NORDEN;
	}
	public void setNORDEN(Long nORDEN) {
		NORDEN = nORDEN;
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
	public String getCAJABUSQUEDA() {
		return CAJABUSQUEDA;
	}
	public void setCAJABUSQUEDA(String cAJABUSQUEDA) {
		CAJABUSQUEDA = cAJABUSQUEDA;
	}
	public String getVFERIADO() {
		return VFERIADO;
	}
	public void setVFERIADO(String vFERIADO) {
		VFERIADO = vFERIADO;
	}
	public Long getNITEM() {
		return NITEM;
	}
	public void setNITEM(Long nITEM) {
		NITEM = nITEM;
	}	
	
	

}
