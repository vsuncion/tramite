package com.tramite.app.Entidades;

import java.io.Serializable;
import java.util.Date;

public class MovimientoExpediente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long NIDMOVIMIENTOPK;     
	private Long NIDEXPEDIENTEFK;     
	private Long NESTADODOCUMENTOFK;    
	private Long OFICINA_ORIGENFK;    
	private Long OFICINA_DESTINOFK;    
	private Date DFECHAOFICINA;        
	private Date DFECHARECEPCION;      
	private Date DFECHAENVIO;         
	private String VOBSERVACION;         
	private int NADJUNTOARCHIVO;      
	private int NELIMINADO;
	public MovimientoExpediente() {
		super();
	}
	public Long getNIDMOVIMIENTOPK() {
		return NIDMOVIMIENTOPK;
	}
	public void setNIDMOVIMIENTOPK(Long nIDMOVIMIENTOPK) {
		NIDMOVIMIENTOPK = nIDMOVIMIENTOPK;
	}
	public Long getNIDEXPEDIENTEFK() {
		return NIDEXPEDIENTEFK;
	}
	public void setNIDEXPEDIENTEFK(Long nIDEXPEDIENTEFK) {
		NIDEXPEDIENTEFK = nIDEXPEDIENTEFK;
	}
	public Long getNESTADODOCUMENTOFK() {
		return NESTADODOCUMENTOFK;
	}
	public void setNESTADODOCUMENTOFK(Long nESTADODOCUMENTOFK) {
		NESTADODOCUMENTOFK = nESTADODOCUMENTOFK;
	}
	public Long getOFICINA_ORIGENFK() {
		return OFICINA_ORIGENFK;
	}
	public void setOFICINA_ORIGENFK(Long oFICINA_ORIGENFK) {
		OFICINA_ORIGENFK = oFICINA_ORIGENFK;
	}
	public Long getOFICINA_DESTINOFK() {
		return OFICINA_DESTINOFK;
	}
	public void setOFICINA_DESTINOFK(Long oFICINA_DESTINOFK) {
		OFICINA_DESTINOFK = oFICINA_DESTINOFK;
	}
	public Date getDFECHAOFICINA() {
		return DFECHAOFICINA;
	}
	public void setDFECHAOFICINA(Date dFECHAOFICINA) {
		DFECHAOFICINA = dFECHAOFICINA;
	}
	public Date getDFECHARECEPCION() {
		return DFECHARECEPCION;
	}
	public void setDFECHARECEPCION(Date dFECHARECEPCION) {
		DFECHARECEPCION = dFECHARECEPCION;
	}
	public Date getDFECHAENVIO() {
		return DFECHAENVIO;
	}
	public void setDFECHAENVIO(Date dFECHAENVIO) {
		DFECHAENVIO = dFECHAENVIO;
	}
	public String getVOBSERVACION() {
		return VOBSERVACION;
	}
	public void setVOBSERVACION(String vOBSERVACION) {
		VOBSERVACION = vOBSERVACION;
	}
	public int getNADJUNTOARCHIVO() {
		return NADJUNTOARCHIVO;
	}
	public void setNADJUNTOARCHIVO(int nADJUNTOARCHIVO) {
		NADJUNTOARCHIVO = nADJUNTOARCHIVO;
	}
	public int getNELIMINADO() {
		return NELIMINADO;
	}
	public void setNELIMINADO(int nELIMINADO) {
		NELIMINADO = nELIMINADO;
	} 
	
	
}
