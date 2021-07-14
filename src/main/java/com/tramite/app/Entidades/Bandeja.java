package com.tramite.app.Entidades;

import java.io.Serializable;

public class Bandeja implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long NIDMOVIMIENTOPK;
	private String VFECHA_OFICINA;
	private String VREMITENTE;
	private String VASUNTO;
	private String VOFICINA_ORIGEN;
	private String VOFICINA_DESTINO;
	private String VESTADO_DOC;
	private Long NIDEXPEDIENTEFK;
	private String  VCODIGO_EXPEDIENTE;
	private Long NIDEXPEDIENTEPK;
	private Long NITEM;
	private String  VCOLOR;
	
	public String getVCOLOR() {
		return VCOLOR;
	}

	public void setVCOLOR(String vCOLOR) {
		VCOLOR = vCOLOR;
	}

	public Bandeja() {
	}

	public Long getNIDMOVIMIENTOPK() {
		return NIDMOVIMIENTOPK;
	}

	public void setNIDMOVIMIENTOPK(Long nIDMOVIMIENTOPK) {
		NIDMOVIMIENTOPK = nIDMOVIMIENTOPK;
	}

	public String getVFECHA_OFICINA() {
		return VFECHA_OFICINA;
	}

	public void setVFECHA_OFICINA(String vFECHA_OFICINA) {
		VFECHA_OFICINA = vFECHA_OFICINA;
	}

	public String getVREMITENTE() {
		return VREMITENTE;
	}

	public void setVREMITENTE(String vREMITENTE) {
		VREMITENTE = vREMITENTE;
	}

	public String getVASUNTO() {
		return VASUNTO;
	}

	public void setVASUNTO(String vASUNTO) {
		VASUNTO = vASUNTO;
	}

	public String getVOFICINA_ORIGEN() {
		return VOFICINA_ORIGEN;
	}

	public void setVOFICINA_ORIGEN(String vOFICINA_ORIGEN) {
		VOFICINA_ORIGEN = vOFICINA_ORIGEN;
	}

	public String getVOFICINA_DESTINO() {
		return VOFICINA_DESTINO;
	}

	public void setVOFICINA_DESTINO(String vOFICINA_DESTINO) {
		VOFICINA_DESTINO = vOFICINA_DESTINO;
	}

	public String getVESTADO_DOC() {
		return VESTADO_DOC;
	}

	public void setVESTADO_DOC(String vESTADO_DOC) {
		VESTADO_DOC = vESTADO_DOC;
	}

	public Long getNIDEXPEDIENTEFK() {
		return NIDEXPEDIENTEFK;
	}

	public void setNIDEXPEDIENTEFK(Long nIDEXPEDIENTEFK) {
		NIDEXPEDIENTEFK = nIDEXPEDIENTEFK;
	}

	public String getVCODIGO_EXPEDIENTE() {
		return VCODIGO_EXPEDIENTE;
	}

	public void setVCODIGO_EXPEDIENTE(String vCODIGO_EXPEDIENTE) {
		VCODIGO_EXPEDIENTE = vCODIGO_EXPEDIENTE;
	}

	public Long getNIDEXPEDIENTEPK() {
		return NIDEXPEDIENTEPK;
	}

	public void setNIDEXPEDIENTEPK(Long nIDEXPEDIENTEPK) {
		NIDEXPEDIENTEPK = nIDEXPEDIENTEPK;
	}

	public Long getNITEM() {
		return NITEM;
	}

	public void setNITEM(Long nITEM) {
		NITEM = nITEM;
	}
 	
}
