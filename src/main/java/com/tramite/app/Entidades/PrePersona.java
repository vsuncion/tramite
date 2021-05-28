package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;
 

public class PrePersona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long   NIDPREPERSONAPK; 
	 
	private String VNOMBRE; 
	private String VAPEPATERNO;	 
	private String VAPEMATERNO;	
	private Long   NTIPODOCFK; 
	private String VNUMERODOC; 
	private String VDIRECCION;
	private String  VCORREO;	
	private int     NTIPO_PERSONA;
	private String  VRUC;
	private String  VRAZON_SOCIAL;
	private String  VTELEFONO;	
	private String  VCODIGOACTIVACION;
	private int     NESTADO;	
	private Date    DFECREGISTRO;		  
	private Long    NUSUREGISTRA;		  
	private Date    DFECMODIFICA;		 
	private Long    NUSUMODIFICA;		  
	private Date    DFECELIMINA;			  
	private Long    NUSUELIMINA;	
	private Long    NPROFESIONFK;
	private Long    NOFICINAFK;
	private Date    DFECINGRESO;
	
	public PrePersona() {
		 
	}

	public Long getNIDPREPERSONAPK() {
		return NIDPREPERSONAPK;
	}

	public void setNIDPREPERSONAPK(Long nIDPREPERSONAPK) {
		NIDPREPERSONAPK = nIDPREPERSONAPK;
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

	public int getNTIPO_PERSONA() {
		return NTIPO_PERSONA;
	}

	public void setNTIPO_PERSONA(int nTIPO_PERSONA) {
		NTIPO_PERSONA = nTIPO_PERSONA;
	}

	public String getVRUC() {
		return VRUC;
	}

	public void setVRUC(String vRUC) {
		VRUC = vRUC;
	}

	public String getVRAZON_SOCIAL() {
		return VRAZON_SOCIAL;
	}

	public void setVRAZON_SOCIAL(String vRAZON_SOCIAL) {
		VRAZON_SOCIAL = vRAZON_SOCIAL;
	}

	public String getVTELEFONO() {
		return VTELEFONO;
	}

	public void setVTELEFONO(String vTELEFONO) {
		VTELEFONO = vTELEFONO;
	}

	 

	public String getVCODIGOACTIVACION() {
		return VCODIGOACTIVACION;
	}

	public void setVCODIGOACTIVACION(String vCODIGOACTIVACION) {
		VCODIGOACTIVACION = vCODIGOACTIVACION;
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
	
	
	
 
	
 
	 
}
