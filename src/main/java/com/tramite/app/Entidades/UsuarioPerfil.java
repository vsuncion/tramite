package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

public class UsuarioPerfil  implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long  NIDUSUAPERFILPK;
	private Long  NUSUARIOFK;
	private Long  NPERFILFK;
	private int   NESTADO;
	 private Date DFECREGISTRO;
	 private Long NUSUREGISTRA;
	 private Date DFECMODIFICA;
	 private Long NUSUMODIFICA;
	 private Date DFECELIMINA;
	 private Long NUSUELIMINA;
	 
	 private String username;
	 private String rol;
	 
	public UsuarioPerfil() {
		super();
	}

	public Long getNIDUSUAPERFILPK() {
		return NIDUSUAPERFILPK;
	}

	public void setNIDUSUAPERFILPK(Long nIDUSUAPERFILPK) {
		NIDUSUAPERFILPK = nIDUSUAPERFILPK;
	}

	public Long getNUSUARIOFK() {
		return NUSUARIOFK;
	}

	public void setNUSUARIOFK(Long nUSUARIOFK) {
		NUSUARIOFK = nUSUARIOFK;
	}

	public Long getNPERFILFK() {
		return NPERFILFK;
	}

	public void setNPERFILFK(Long nPERFILFK) {
		NPERFILFK = nPERFILFK;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	 
	 
	 
}
