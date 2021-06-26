package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class Persona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long   NIDPERSONAPK; 
	
	@NotEmpty(message = "EL CAMPO NOMBRE TRABAJADOR, ES OBLIGATORIO") 
	private String VNOMBRE; 
	
	@NotEmpty(message = "EL CAMPO APELLIDO PATERNO, ES OBLIGATORIO") 
	private String VAPEPATERNO;	
	
	@NotEmpty(message = "EL CAMPO APELLIDO MATERNO, ES OBLIGATORIO") 
	private String VAPEMATERNO;	
	
	private Long   NTIPODOCFK; 
	
	@NotEmpty(message = "EL CAMPO NUMERO DOCUMENTO, ES OBLIGATORIO") 
	private String VNUMERODOC; 
	
	private String VDIRECCION;
	
	@NotEmpty(message = "EL CAMPO CORREO, ES OBLIGATORIO") 
	private String VCORREO;	
	
	private String VTELEFONO;		      
	private int    NESTADO;				  
	private Date   DFECREGISTRO;		  
	private Long   NUSUREGISTRA;		  
	private Date   DFECMODIFICA;		 
	private Long   NUSUMODIFICA;		  
	private Date   DFECELIMINA;			  
	private Long   NUSUELIMINA;	
	private Long   NPROFESIONFK;
	private Long   NOFICINAFK;
	private Date   DFECINGRESO;
	
	private String TIPOCRITERIO;
	private String CAJABUSQUEDA;
	private String NOMBRE_COMPLETO;
	private String VTIPO_DOC_REGISTRO;
	private String VNOMBRE_PROFESION;
	private String VNOMBRE_OFICINA;
	private String VESTADO;
	private Long   NCBTIPOCRITERIO;
	private Long NIDTRABAJADORPK;
	private Long  NITEM;
	private String VRUC;
	private String VRAZONSOCIAL;
	private Long NCARGOFK;
	private String  VNOMBRECARGO;
	

	public String getVRUC() {
		return VRUC;
	}



	public void setVRUC(String vRUC) {
		VRUC = vRUC;
	}



	public String getVRAZONSOCIAL() {
		return VRAZONSOCIAL;
	}



	public void setVRAZONSOCIAL(String vRAZONSOCIAL) {
		VRAZONSOCIAL = vRAZONSOCIAL;
	}



	public Long getNIDTRABAJADORPK() {
		return NIDTRABAJADORPK;
	}



	public void setNIDTRABAJADORPK(Long nIDTRABAJADORPK) {
		NIDTRABAJADORPK = nIDTRABAJADORPK;
	}



	public Persona() {
		super();
	}



	public Long getNIDPERSONAPK() {
		return NIDPERSONAPK;
	}



	public void setNIDPERSONAPK(Long nIDPERSONAPK) {
		NIDPERSONAPK = nIDPERSONAPK;
	}



	public String getVNOMBRE() {
		return VNOMBRE;
	}



	public void setVNOMBRE(String vNOMBRE) {
		VNOMBRE = vNOMBRE;
	}



	public String getVAPEPATERNO() {
		return VAPEPATERNO;
	}



	public void setVAPEPATERNO(String vAPEPATERNO) {
		VAPEPATERNO = vAPEPATERNO;
	}



	public String getVAPEMATERNO() {
		return VAPEMATERNO;
	}



	public void setVAPEMATERNO(String vAPEMATERNO) {
		VAPEMATERNO = vAPEMATERNO;
	}



	public Long getNTIPODOCFK() {
		return NTIPODOCFK;
	}



	public void setNTIPODOCFK(Long nTIPODOCFK) {
		NTIPODOCFK = nTIPODOCFK;
	}



	public String getVNUMERODOC() {
		return VNUMERODOC;
	}



	public void setVNUMERODOC(String vNUMERODOC) {
		VNUMERODOC = vNUMERODOC;
	}



	public String getVDIRECCION() {
		return VDIRECCION;
	}



	public void setVDIRECCION(String vDIRECCION) {
		VDIRECCION = vDIRECCION;
	}



	public String getVCORREO() {
		return VCORREO;
	}



	public void setVCORREO(String vCORREO) {
		VCORREO = vCORREO;
	}



	public String getVTELEFONO() {
		return VTELEFONO;
	}



	public void setVTELEFONO(String vTELEFONO) {
		VTELEFONO = vTELEFONO;
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



	public String getTIPOCRITERIO() {
		return TIPOCRITERIO;
	}



	public void setTIPOCRITERIO(String tIPOCRITERIO) {
		TIPOCRITERIO = tIPOCRITERIO;
	}



	public String getCAJABUSQUEDA() {
		return CAJABUSQUEDA;
	}



	public void setCAJABUSQUEDA(String cAJABUSQUEDA) {
		CAJABUSQUEDA = cAJABUSQUEDA;
	}



	public String getNOMBRE_COMPLETO() {
		return NOMBRE_COMPLETO;
	}



	public void setNOMBRE_COMPLETO(String nOMBRE_COMPLETO) {
		NOMBRE_COMPLETO = nOMBRE_COMPLETO;
	}



	public String getVTIPO_DOC_REGISTRO() {
		return VTIPO_DOC_REGISTRO;
	}



	public void setVTIPO_DOC_REGISTRO(String vTIPO_DOC_REGISTRO) {
		VTIPO_DOC_REGISTRO = vTIPO_DOC_REGISTRO;
	}



	public String getVNOMBRE_PROFESION() {
		return VNOMBRE_PROFESION;
	}



	public void setVNOMBRE_PROFESION(String vNOMBRE_PROFESION) {
		VNOMBRE_PROFESION = vNOMBRE_PROFESION;
	}



	public String getVNOMBRE_OFICINA() {
		return VNOMBRE_OFICINA;
	}



	public void setVNOMBRE_OFICINA(String vNOMBRE_OFICINA) {
		VNOMBRE_OFICINA = vNOMBRE_OFICINA;
	}



	public String getVESTADO() {
		return VESTADO;
	}



	public void setVESTADO(String vESTADO) {
		VESTADO = vESTADO;
	}



	public Long getNCBTIPOCRITERIO() {
		return NCBTIPOCRITERIO;
	}



	public void setNCBTIPOCRITERIO(Long nCBTIPOCRITERIO) {
		NCBTIPOCRITERIO = nCBTIPOCRITERIO;
	}



	public Long getNITEM() {
		return NITEM;
	}



	public void setNITEM(Long nITEM) {
		NITEM = nITEM;
	}



	public Long getNCARGOFK() {
		return NCARGOFK;
	}



	public void setNCARGOFK(Long nCARGOFK) {
		NCARGOFK = nCARGOFK;
	}



	public String getVNOMBRECARGO() {
		return VNOMBRECARGO;
	}



	public void setVNOMBRECARGO(String vNOMBRECARGO) {
		VNOMBRECARGO = vNOMBRECARGO;
	}

  
	 
}
