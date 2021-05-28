package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class TipoDocumentos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long NIDTIPODOCUMENTOPK;
	
	@NotEmpty(message = "EL CAMPO NOMBRE TIPO DOCUMENTO, ES OBLIGATORIO") 
	private String VNOMBRE;
	
	private String VALIAS;
	private String VDESCRIPCION;
	private int NESTADO;
	private int NORDEN;
	private Date DFECREGISTRO;
	private Long NUSUREGISTRA;
	private Date DFECMODIFICA;
	private Long NUSUMODIFICA;
	private Date DFECELIMINA;
	private Long NUSUELIMINA;
	
	private String CAJABUSQUEDA;
	private Long  NITEM;
	
	public Long getNITEM() {
		return NITEM;
	}
	public void setNITEM(Long nITEM) {
		NITEM = nITEM;
	}
	public TipoDocumentos() {
		super();
	}
	public Long getNIDTIPODOCUMENTOPK() {
		return NIDTIPODOCUMENTOPK;
	}
	public void setNIDTIPODOCUMENTOPK(Long nIDTIPODOCUMENTOPK) {
		NIDTIPODOCUMENTOPK = nIDTIPODOCUMENTOPK;
	}
	public String getVNOMBRE() {
		return VNOMBRE;
	}
	public void setVNOMBRE(String vNOMBRE) {
		VNOMBRE = vNOMBRE;
	}
	public String getVALIAS() {
		return VALIAS;
	}
	public void setVALIAS(String vALIAS) {
		VALIAS = vALIAS;
	}
	public String getVDESCRIPCION() {
		return VDESCRIPCION;
	}
	public void setVDESCRIPCION(String vDESCRIPCION) {
		VDESCRIPCION = vDESCRIPCION;
	}
	 
	public int getNESTADO() {
		return NESTADO;
	}
	public void setNESTADO(int nESTADO) {
		NESTADO = nESTADO;
	}
	public int getNORDEN() {
		return NORDEN;
	}
	public void setNORDEN(int nORDEN) {
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
	
	

}
