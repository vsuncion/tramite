package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

public class PreTupacExpediente implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Long IDPREEXPEDIENTEPK;  
	Long TIPOPERSONAFK;
	Long PERSONAFK; 
	Long TUPACFK; 
	Long TIPODOCUMENTOFK; 
	String VNUMERODOCUMENTO;  
	String VNUMEROFOLIO; 
	Date DFECHADOCUMENTO; 
	String VASUNTO; 
	String VNOMBRE_ARCHIVO;		 
	String VUBICACION_ARCHIVO;  
	String VEXTENSION;
 
	public PreTupacExpediente() { 
	}
	
	public Long getIDPREEXPEDIENTEPK() {
		return IDPREEXPEDIENTEPK;
	}
	public void setIDPREEXPEDIENTEPK(Long iDPREEXPEDIENTEPK) {
		IDPREEXPEDIENTEPK = iDPREEXPEDIENTEPK;
	}
	public Long getTIPOPERSONAFK() {
		return TIPOPERSONAFK;
	}
	public void setTIPOPERSONAFK(Long tIPOPERSONAFK) {
		TIPOPERSONAFK = tIPOPERSONAFK;
	}
	public Long getPERSONAFK() {
		return PERSONAFK;
	}
	public void setPERSONAFK(Long pERSONAFK) {
		PERSONAFK = pERSONAFK;
	}
	public Long getTUPACFK() {
		return TUPACFK;
	}
	public void setTUPACFK(Long tUPACFK) {
		TUPACFK = tUPACFK;
	}
	public Long getTIPODOCUMENTOFK() {
		return TIPODOCUMENTOFK;
	}
	public void setTIPODOCUMENTOFK(Long tIPODOCUMENTOFK) {
		TIPODOCUMENTOFK = tIPODOCUMENTOFK;
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
	public Date getDFECHADOCUMENTO() {
		return DFECHADOCUMENTO;
	}
	public void setDFECHADOCUMENTO(Date dFECHADOCUMENTO) {
		DFECHADOCUMENTO = dFECHADOCUMENTO;
	}
	public String getVASUNTO() {
		return VASUNTO;
	}
	public void setVASUNTO(String vASUNTO) {
		VASUNTO = vASUNTO;
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
	
	
}
