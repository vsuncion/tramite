package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

public class RequisitosTupac  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private Long NREQTUPACPK;  
	private Long TUPACFK;				  
	private Long REQUISITOSFK;	     
	private int NESTADO;				  
	private Date  DFECREGISTRO;		 
	private Long NUSUREGISTRA;		  
	private Date  DFECMODIFICA;		  
	private Long NUSUMODIFICA;		  
	private Date  DFECELIMINA;			 
	private Long NUSUELIMINA;
	
	private String CAJABUSQUEDA;
	private String VESTADO;
	private String VNOMBRE;
	private String VDESCRIPCION;
	private Long NITEM;
	
	public Long getNREQTUPACPK() {
		return NREQTUPACPK;
	}
	public void setNREQTUPACPK(Long nREQTUPACPK) {
		NREQTUPACPK = nREQTUPACPK;
	}
	public Long getTUPACFK() {
		return TUPACFK;
	}
	public void setTUPACFK(Long tUPACFK) {
		TUPACFK = tUPACFK;
	}
	public Long getREQUISITOSFK() {
		return REQUISITOSFK;
	}
	public void setREQUISITOSFK(Long rEQUISITOSFK) {
		REQUISITOSFK = rEQUISITOSFK;
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
	public String getCAJABUSQUEDA() {
		return CAJABUSQUEDA;
	}
	public void setCAJABUSQUEDA(String cAJABUSQUEDA) {
		CAJABUSQUEDA = cAJABUSQUEDA;
	}
	public String getVESTADO() {
		return VESTADO;
	}
	public void setVESTADO(String vESTADO) {
		VESTADO = vESTADO;
	}
	public String getVNOMBRE() {
		return VNOMBRE;
	}
	public void setVNOMBRE(String vNOMBRE) {
		VNOMBRE = vNOMBRE;
	}
	public String getVDESCRIPCION() {
		return VDESCRIPCION;
	}
	public void setVDESCRIPCION(String vDESCRIPCION) {
		VDESCRIPCION = vDESCRIPCION;
	}
	public Long getNITEM() {
		return NITEM;
	}
	public void setNITEM(Long nITEM) {
		NITEM = nITEM;
	}
	 
	

}
