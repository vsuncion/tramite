package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Usuarios implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long NIDUSUARIOPK;
	private Long NTRABAJADORFK;
	private Long NOFICINAFK;
	private String VUSUARIO;
	
	@NotEmpty(message = "EL CAMPO CLAVE, ES OBLIGATORIO") 
	 @Size(min=4, max=50)
	private String VCLAVE;
	
	
	private int NESTADO;
	private Date DFECREGISTRO;
	private Long NUSUREGISTRA;
	private Date DFECMODIFICA;
	private Long NUSUMODIFICA;
	private Date DFECELIMINA;
	private Long NUSUELIMINA;
	
	private String CAJABUSQUEDA;
	private Long  NCBTIPOCRITERIO;
	private String VNOMBRECOMPLETO;
	private Long NPERFILFK;
	
	private Long   NITEM;
	private String VCORREO;
	private String VPERFIL;
	private String VOFICINA;  
	private Long NIDUSUAPERFILFK;
	
	private String username;
	private String password;
	private int  enabled;
	private String vnombre_persona; 
	private Long nidpersonapk;
	private String fullname;
	
	 

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

 

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	
	

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Long getNITEM() {
		return NITEM;
	}

	public void setNITEM(Long nITEM) {
		NITEM = nITEM;
	}

	public String getVCORREO() {
		return VCORREO;
	}

	public void setVCORREO(String vCORREO) {
		VCORREO = vCORREO;
	}

	public String getVPERFIL() {
		return VPERFIL;
	}

	public void setVPERFIL(String vPERFIL) {
		VPERFIL = vPERFIL;
	}

	public String getVOFICINA() {
		return VOFICINA;
	}

	public void setVOFICINA(String vOFICINA) {
		VOFICINA = vOFICINA;
	}
 

	public Long getNCBTIPOCRITERIO() {
		return NCBTIPOCRITERIO;
	}

	public void setNCBTIPOCRITERIO(Long nCBTIPOCRITERIO) {
		NCBTIPOCRITERIO = nCBTIPOCRITERIO;
	}

	public Usuarios() {
		super();
	}
	
	public Long getNIDUSUARIOPK() {
		return NIDUSUARIOPK;
	}
	public void setNIDUSUARIOPK(Long nIDUSUARIOPK) {
		NIDUSUARIOPK = nIDUSUARIOPK;
	}
	public Long getNTRABAJADORFK() {
		return NTRABAJADORFK;
	}
	public void setNTRABAJADORFK(Long nTRABAJADORFK) {
		NTRABAJADORFK = nTRABAJADORFK;
	}
	public Long getNOFICINAFK() {
		return NOFICINAFK;
	}
	public void setNOFICINAFK(Long nOFICINAFK) {
		NOFICINAFK = nOFICINAFK;
	}
	public String getVUSUARIO() {
		return VUSUARIO;
	}
	public void setVUSUARIO(String vUSUARIO) {
		VUSUARIO = vUSUARIO;
	}
	public String getVCLAVE() {
		return VCLAVE;
	}
	public void setVCLAVE(String vCLAVE) {
		VCLAVE = vCLAVE;
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

	public String getVNOMBRECOMPLETO() {
		return VNOMBRECOMPLETO;
	}

	public void setVNOMBRECOMPLETO(String vNOMBRECOMPLETO) {
		VNOMBRECOMPLETO = vNOMBRECOMPLETO;
	}

	public Long getNPERFILFK() {
		return NPERFILFK;
	}

	public void setNPERFILFK(Long nPERFILFK) {
		NPERFILFK = nPERFILFK;
	}

	public Long getNIDUSUAPERFILFK() {
		return NIDUSUAPERFILFK;
	}

	public void setNIDUSUAPERFILFK(Long nIDUSUAPERFILFK) {
		NIDUSUAPERFILFK = nIDUSUAPERFILFK;
	}

	public String getVnombre_persona() {
		return vnombre_persona;
	}

	public void setVnombre_persona(String vnombre_persona) {
		this.vnombre_persona = vnombre_persona;
	}

	public Long getNidpersonapk() {
		return nidpersonapk;
	}

	public void setNidpersonapk(Long nidpersonapk) {
		this.nidpersonapk = nidpersonapk;
	}
	
	

}
