package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

 

public class Oficinas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long NIDOFICINAPK;
	
	@NotEmpty(message = "EL CAMPO NOMBRE OFICINA, ES OBLIGATORIO") 
	private String VNOMBRE;
	
	private String VSIGLA;
	
	@NotEmpty(message = "EL CAMPO VSIGLADOCUMENTO, ES OBLIGATORIO") 
	private String VSIGLADOCUMENTO;
	
	private String VDESCRIPCION;
	private int NESTADO;
	private int NORDEN;
	private Date DFECREGISTRO;
	private Long NUSUREGISTRA;
	private Date DFECMODIFICA;
	private Long NUSUMODIFICA;
	private Date DFECELIMINA;
	private Long NUSUELIMINA;
	private Long  NIDPADRE;
	
	private String CAJABUSQUEDA;
	private Long  NITEM;
 
	public Long getNITEM() {
		return NITEM;
	}

	public void setNITEM(Long nITEM) {
		NITEM = nITEM;
	}

	public Oficinas() {
		super();
	}

	public Long getNIDOFICINAPK() {
		return NIDOFICINAPK;
	}

	public void setNIDOFICINAPK(Long nIDOFICINAPK) {
		NIDOFICINAPK = nIDOFICINAPK;
	}

	public String getVNOMBRE() {
		return VNOMBRE;
	}

	public void setVNOMBRE(String vNOMBRE) {
		VNOMBRE = vNOMBRE;
	}

	public String getVSIGLA() {
		return VSIGLA;
	}

	public void setVSIGLA(String vSIGLA) {
		VSIGLA = vSIGLA;
	}

	public String getVSIGLADOCUMENTO() {
		return VSIGLADOCUMENTO;
	}

	public void setVSIGLADOCUMENTO(String vSIGLADOCUMENTO) {
		VSIGLADOCUMENTO = vSIGLADOCUMENTO;
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

	public Long getNIDPADRE() {
		return NIDPADRE;
	}

	public void setNIDPADRE(Long nIDPADRE) {
		NIDPADRE = nIDPADRE;
	}

	public String getCAJABUSQUEDA() {
		return CAJABUSQUEDA;
	}

	public void setCAJABUSQUEDA(String cAJABUSQUEDA) {
		CAJABUSQUEDA = cAJABUSQUEDA;
	}
 
	 
}
