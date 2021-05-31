package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

public class Expediente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long   NIDEXPEDIENTEPK;        
	private String VCODIGO_EXPEDIENTE;  
	private Long   TIPO_DOCUMENTOFK;	  
	private String VNUMERODOCUMENTO;	   
	private String VNUMEROFOLIO;		  
	private String VASUNTO;			 
	private Date   DFECHADOCUMENTO;		  
	private String VNOMBRE_ARCHIVO;		  
	private String VUBICACION_ARCHIVO;     
	private String VEXTENSION;			 
	private Long   TUPACFK;				  
	private int    NTIPOPERSONA;		   
	private Long   PERSONAFK;			 
	private Date   DFECHATERMINO;		   
	private int    NDIASPLAZO;			   
	private int    NDIASRESTANTES;		  
	private Long   NESTADODOCUMENTOFK;    
	private int    NESTADO;				  
	private int    NORDEN;				  
	private Date   DFECREGISTRO;		   
	private Long   NUSUREGISTRA;		  
	private Date   DFECMODIFICA;		   
	private Long   NUSUMODIFICA;		  
	private Date   DFECELIMINA;		   
	private Long   NUSUELIMINA;
	private Long NIDPERSONAPK;
	
	private int TIPODOCUMENTOBUSCAR;
	private String CAJABUSQUEDA;
    
	public Long getNIDEXPEDIENTEPK() {
		return NIDEXPEDIENTEPK;
	}


	public void setNIDEXPEDIENTEPK(Long nIDEXPEDIENTEPK) {
		NIDEXPEDIENTEPK = nIDEXPEDIENTEPK;
	}


	public String getVCODIGO_EXPEDIENTE() {
		return VCODIGO_EXPEDIENTE;
	}


	public void setVCODIGO_EXPEDIENTE(String vCODIGO_EXPEDIENTE) {
		VCODIGO_EXPEDIENTE = vCODIGO_EXPEDIENTE;
	}


	public Long getTIPO_DOCUMENTOFK() {
		return TIPO_DOCUMENTOFK;
	}


	public void setTIPO_DOCUMENTOFK(Long tIPO_DOCUMENTOFK) {
		TIPO_DOCUMENTOFK = tIPO_DOCUMENTOFK;
	}


	public String getVNUMERODOCUMENTO() {
		return VNUMERODOCUMENTO;
	}


	public void setVNUMERODOCUMENTO(String vNUMERODOCUMENTO) {
		VNUMERODOCUMENTO = vNUMERODOCUMENTO;
	}


	public String getVNUMEROFOLIO() {
		return VNUMEROFOLIO;
	}


	public void setVNUMEROFOLIO(String vNUMEROFOLIO) {
		VNUMEROFOLIO = vNUMEROFOLIO;
	}


	public String getVASUNTO() {
		return VASUNTO;
	}


	public void setVASUNTO(String vASUNTO) {
		VASUNTO = vASUNTO;
	}


	public Date getDFECHADOCUMENTO() {
		return DFECHADOCUMENTO;
	}


	public void setDFECHADOCUMENTO(Date dFECHADOCUMENTO) {
		DFECHADOCUMENTO = dFECHADOCUMENTO;
	}


	public String getVNOMBRE_ARCHIVO() {
		return VNOMBRE_ARCHIVO;
	}


	public void setVNOMBRE_ARCHIVO(String vNOMBRE_ARCHIVO) {
		VNOMBRE_ARCHIVO = vNOMBRE_ARCHIVO;
	}


	public String getVUBICACION_ARCHIVO() {
		return VUBICACION_ARCHIVO;
	}


	public void setVUBICACION_ARCHIVO(String vUBICACION_ARCHIVO) {
		VUBICACION_ARCHIVO = vUBICACION_ARCHIVO;
	}


	public String getVEXTENSION() {
		return VEXTENSION;
	}


	public void setVEXTENSION(String vEXTENSION) {
		VEXTENSION = vEXTENSION;
	}


	public Long getTUPACFK() {
		return TUPACFK;
	}


	public void setTUPACFK(Long tUPACFK) {
		TUPACFK = tUPACFK;
	}


	public int getNTIPOPERSONA() {
		return NTIPOPERSONA;
	}


	public void setNTIPOPERSONA(int nTIPOPERSONA) {
		NTIPOPERSONA = nTIPOPERSONA;
	}


	public Long getPERSONAFK() {
		return PERSONAFK;
	}


	public void setPERSONAFK(Long pERSONAFK) {
		PERSONAFK = pERSONAFK;
	}


	public Date getDFECHATERMINO() {
		return DFECHATERMINO;
	}


	public void setDFECHATERMINO(Date dFECHATERMINO) {
		DFECHATERMINO = dFECHATERMINO;
	}


	public int getNDIASPLAZO() {
		return NDIASPLAZO;
	}


	public void setNDIASPLAZO(int nDIASPLAZO) {
		NDIASPLAZO = nDIASPLAZO;
	}


	public int getNDIASRESTANTES() {
		return NDIASRESTANTES;
	}


	public void setNDIASRESTANTES(int nDIASRESTANTES) {
		NDIASRESTANTES = nDIASRESTANTES;
	}


	public Long getNESTADODOCUMENTOFK() {
		return NESTADODOCUMENTOFK;
	}


	public void setNESTADODOCUMENTOFK(Long nESTADODOCUMENTOFK) {
		NESTADODOCUMENTOFK = nESTADODOCUMENTOFK;
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


	public Long getNIDPERSONAPK() {
		return NIDPERSONAPK;
	}


	public void setNIDPERSONAPK(Long nIDPERSONAPK) {
		NIDPERSONAPK = nIDPERSONAPK;
	}


	public int getTIPODOCUMENTOBUSCAR() {
		return TIPODOCUMENTOBUSCAR;
	}


	public void setTIPODOCUMENTOBUSCAR(int tIPODOCUMENTOBUSCAR) {
		TIPODOCUMENTOBUSCAR = tIPODOCUMENTOBUSCAR;
	}


	public String getCAJABUSQUEDA() {
		return CAJABUSQUEDA;
	}


	public void setCAJABUSQUEDA(String cAJABUSQUEDA) {
		CAJABUSQUEDA = cAJABUSQUEDA;
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


	public String getVNOMBRE() {
		return VNOMBRE;
	}


	public void setVNOMBRE(String vNOMBRE) {
		VNOMBRE = vNOMBRE;
	}


	public String getVAPELLIDO_PATERNO() {
		return VAPELLIDO_PATERNO;
	}


	public void setVAPELLIDO_PATERNO(String vAPELLIDO_PATERNO) {
		VAPELLIDO_PATERNO = vAPELLIDO_PATERNO;
	}


	public String getVAPELLIDO_MATERNO() {
		return VAPELLIDO_MATERNO;
	}


	public void setVAPELLIDO_MATERNO(String vAPELLIDO_MATERNO) {
		VAPELLIDO_MATERNO = vAPELLIDO_MATERNO;
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


	public String getVFECHADOCUMENTO() {
		return VFECHADOCUMENTO;
	}


	public void setVFECHADOCUMENTO(String vFECHADOCUMENTO) {
		VFECHADOCUMENTO = vFECHADOCUMENTO;
	}


	private  String VRUC;
	private String VRAZON_SOCIAL;
	private String VNOMBRE;
	private String VAPELLIDO_PATERNO;
	private String VAPELLIDO_MATERNO;
	private String VDIRECCION;
	private String VCORREO;
	private String VTELEFONO;
	private	String VFECHADOCUMENTO;
    
    
	public Expediente() {
	}

 
 

}
