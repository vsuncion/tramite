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
	private String VOFICINA;
	private String  VESTADO_DOCUMENTO;
	private String  VFECHARECEPCION;
	private String  VFECHAENVIO;
	private String  VFECHAOFICINA;
	
	private String  VNOMBRE_ARCHIVO;
	private String  VUBICACION_ARCHIVO;
	private String  VEXTENSION;
	
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
	public String getVOFICINA() {
		return VOFICINA;
	}
	public void setVOFICINA(String vOFICINA) {
		VOFICINA = vOFICINA;
	}
	public String getVESTADO_DOCUMENTO() {
		return VESTADO_DOCUMENTO;
	}
	public void setVESTADO_DOCUMENTO(String vESTADO_DOCUMENTO) {
		VESTADO_DOCUMENTO = vESTADO_DOCUMENTO;
	}
	public String getVFECHARECEPCION() {
		return VFECHARECEPCION;
	}
	public void setVFECHARECEPCION(String vFECHARECEPCION) {
		VFECHARECEPCION = vFECHARECEPCION;
	}
	public String getVFECHAENVIO() {
		return VFECHAENVIO;
	}
	public void setVFECHAENVIO(String vFECHAENVIO) {
		VFECHAENVIO = vFECHAENVIO;
	}
	public String getVFECHAOFICINA() {
		return VFECHAOFICINA;
	}
	public void setVFECHAOFICINA(String vFECHAOFICINA) {
		VFECHAOFICINA = vFECHAOFICINA;
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
	
	public String obtenerRutaAbsoluArchivo() {
		 
		return this.getVUBICACION_ARCHIVO() + this.getVNOMBRE_ARCHIVO() + "." + this.getVEXTENSION();
	}
}
