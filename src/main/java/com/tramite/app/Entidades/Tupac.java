package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class Tupac implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long TUPACPK;
	
	@NotEmpty(message = "EL CAMPO NOMBRE TUPAC, ES OBLIGATORIO") 
	private String VNOMBRE;
	
	private String VDESCRIPCION;
	private int NESTADO;
	private int NORDEN;
	private int NDIASPLAZO;
	private int NTIPODIAS;
	private Long  OFICINAFK;
	private Date  DFECREGISTRO;
	private Long NUSUREGISTRA;
	private Date  DFECMODIFICA;
	private Long NUSUMODIFICA;
	private Date   DFECELIMINA;
	private Long NUSUELIMINA;
	
	private String CAJABUSQUEDA;
	private String VTIPODIAS;
	private String VNOMBREOFICINA;
	private Long  NITEM;
	
	 
	public Tupac() {
		super();
	}
	public Long getTUPACPK() {
		return TUPACPK;
	}
	public void setTUPACPK(Long tUPACPK) {
		TUPACPK = tUPACPK;
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
	public int getNDIASPLAZO() {
		return NDIASPLAZO;
	}
	public void setNDIASPLAZO(int nDIASPLAZO) {
		NDIASPLAZO = nDIASPLAZO;
	}
	public int getNTIPODIAS() {
		return NTIPODIAS;
	}
	public void setNTIPODIAS(int nTIPODIAS) {
		NTIPODIAS = nTIPODIAS;
	}
	public Long getOFICINAFK() {
		return OFICINAFK;
	}
	public void setOFICINAFK(Long oFICINAFK) {
		OFICINAFK = oFICINAFK;
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
	public String getVTIPODIAS() {
		return VTIPODIAS;
	}
	public void setVTIPODIAS(String vTIPODIAS) {
		VTIPODIAS = vTIPODIAS;
	}
	public String getVNOMBREOFICINA() {
		return VNOMBREOFICINA;
	}
	public void setVNOMBREOFICINA(String vNOMBREOFICINA) {
		VNOMBREOFICINA = vNOMBREOFICINA;
	}
	public Long getNITEM() {
		return NITEM;
	}
	public void setNITEM(Long nITEM) {
		NITEM = nITEM;
	}
	
	
	

}
