package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

public class Correlativo implements Serializable  {

 
	private static final long serialVersionUID = 1L;
	
	private Long NCORRELATIVOPK;
	private Long NOFICINAFK;
	private Long NVALOR_ACTUAL;
	private int  NESTADO;
	private Date  DFECREGISTRO;
	private Long NUSUREGISTRA;
	private Date  DFECMODIFICA;
	private Long NUSUMODIFICA;
	private Date  DFECELIMINA;
	private Long NUSUELIMINA;
	private String CAJABUSQUEDA;
	private Long  NITEM;
	private String  VNOMBRE;
	private String  VVALOR_ACTUAL;
	private String  VSIGLADOCUMENTO;
	
	public Correlativo() {
		super();
	}
	public Long getNCORRELATIVOPK() {
		return NCORRELATIVOPK;
	}
	public void setNCORRELATIVOPK(Long nCORRELATIVOPK) {
		NCORRELATIVOPK = nCORRELATIVOPK;
	}
	public Long getNOFICINAFK() {
		return NOFICINAFK;
	}
	public void setNOFICINAFK(Long nOFICINAFK) {
		NOFICINAFK = nOFICINAFK;
	}
	public Long getNVALOR_ACTUAL() {
		return NVALOR_ACTUAL;
	}
	public void setNVALOR_ACTUAL(Long nVALOR_ACTUAL) {
		NVALOR_ACTUAL = nVALOR_ACTUAL;
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
	public Long getNITEM() {
		return NITEM;
	}
	public void setNITEM(Long nITEM) {
		NITEM = nITEM;
	}
	public String getVNOMBRE() {
		return VNOMBRE;
	}
	public void setVNOMBRE(String vNOMBRE) {
		VNOMBRE = vNOMBRE;
	}
	public String getVVALOR_ACTUAL() {
		return VVALOR_ACTUAL;
	}
	public void setVVALOR_ACTUAL(String vVALOR_ACTUAL) {
		VVALOR_ACTUAL = vVALOR_ACTUAL;
	}
	public String getVSIGLADOCUMENTO() {
		return VSIGLADOCUMENTO;
	}
	public void setVSIGLADOCUMENTO(String vSIGLADOCUMENTO) {
		VSIGLADOCUMENTO = vSIGLADOCUMENTO;
	}
	
	

}
