package com.tramite.app.Servicios.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.standard.expression.Each;

import com.tramite.app.Datos.RecursoDao;
import com.tramite.app.Entidades.EstadoDocumento;
import com.tramite.app.Entidades.Oficinas;
import com.tramite.app.Entidades.Requisitos;
import com.tramite.app.Entidades.Seleccion;
import com.tramite.app.Entidades.TipoDocumentos;
import com.tramite.app.Entidades.Tupac;
import com.tramite.app.Entidades.Usuarios;
import com.tramite.app.Servicios.FijaServicio;
import com.tramite.app.Servicios.MantenimientoServicio;
import com.tramite.app.Servicios.RecursoServicio;
import com.tramite.app.utilitarios.Constantes;

@Service
public class RecursoServiceImpl implements RecursoServicio {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RecursoDao recursoDao;
	 
	
	@Autowired
	private MantenimientoServicio  mantenimientoServicio;

	@Override
	public List<Seleccion> cbTipoDocumentoPersona() {
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		int[] listaTipoDocumentoPersona = Constantes.listaTipoDocumentoPersona;
		for (int i = 0; i < listaTipoDocumentoPersona.length; i++) {
			Seleccion seleccion = new Seleccion();
			logger.info("=====" + listaTipoDocumentoPersona[i]);
			if (listaTipoDocumentoPersona[i] == 1) {
				seleccion.setCodigo(Long.valueOf(listaTipoDocumentoPersona[i]));
				seleccion.setEtiqueta(Constantes.TipoDocumentoPersonaDNI);
			} else {
				seleccion.setCodigo(Long.valueOf(listaTipoDocumentoPersona[i]));
				seleccion.setEtiqueta(Constantes.TipoDocumentoPersonaRUC);
			}

			cbTipoDocumentoRegistro.add(seleccion);
		}

		return cbTipoDocumentoRegistro;
	}

	@Override
	public List<Seleccion>  cbTipoDocuemnto() { 
		List<TipoDocumentos> listarTipoDocuemntoCarga  = new ArrayList<TipoDocumentos>();
		List<Seleccion> cbListarTipoDocuemnto  = new ArrayList<Seleccion>();
		
		listarTipoDocuemntoCarga = recursoDao.listarTipoDocuemnto();
		
		for (TipoDocumentos i : listarTipoDocuemntoCarga) {
			Seleccion seleccion = new Seleccion();
			seleccion.setCodigo(i.getNIDTIPODOCUMENTOPK());
			seleccion.setEtiqueta(i.getVNOMBRE());
			cbListarTipoDocuemnto.add(seleccion);
		}
		
		
		return cbListarTipoDocuemnto;
	}

	@Override
	public List<Seleccion> cbTupa() {
		List<Tupac> listarTupaCarga  = new ArrayList<Tupac>();
		List<Seleccion> cbListarTipoDocuemnto  = new ArrayList<Seleccion>();
		
		listarTupaCarga = mantenimientoServicio.listarTupac();
		
		for (Tupac i : listarTupaCarga) {
			Seleccion seleccion = new Seleccion();
			seleccion.setCodigo(i.getTUPACPK());
			seleccion.setEtiqueta(i.getVNOMBRE());
			cbListarTipoDocuemnto.add(seleccion);
		}
		
		
		return cbListarTipoDocuemnto;
	}

	@Override
	public String numeroExpediente() { 
		return recursoDao.numeroExpediente();
	}

	@Override
	public Usuarios infoUsuario(String vcorreo) { 
		return recursoDao.infoUsuario(vcorreo);
	}

	@Override
	public List<Seleccion> cbOficinasAtender(Long idoficiActual) {
		List<Oficinas> listaOfinas = new ArrayList<Oficinas>();
		List<Seleccion> listaOfinasFinal = new ArrayList<Seleccion>();
		
		listaOfinas =  mantenimientoServicio.listarOficinas();
		for (Oficinas i : listaOfinas) {
			if(idoficiActual != i.getNIDOFICINAPK()) {
				Seleccion item = new Seleccion(); 
				item.setCodigo(i.getNIDOFICINAPK());
				item.setEtiqueta(i.getVNOMBRE());
				listaOfinasFinal.add(item);
			}
		}
		return listaOfinasFinal;
	}

	@Override
	public List<Seleccion> cbAccionesAtender() {
		List<EstadoDocumento> listaEstadoDocumento = new ArrayList<EstadoDocumento>();
		List<Seleccion> listaEstadoDocumentoFinal = new ArrayList<Seleccion>();
		
		listaEstadoDocumento = mantenimientoServicio.listarEstadoDocumento();
		for (EstadoDocumento i : listaEstadoDocumento) {
			
			if(!Constantes.LETRAS_ESTADO_DOCUMENTO_PENDIENTE.equals(i.getVNOMBRE())) {
				if(!Constantes.LETRAS_ESTADO_DOCUMENTO_RECIBIDO.equals(i.getVNOMBRE())) {
					Seleccion item = new Seleccion();
					item.setCodigo(i.getIDESTADOCUMENTOPK());
					item.setEtiqueta(i.getVNOMBRE());
					listaEstadoDocumentoFinal.add(item);
				}
			}
			
		}
		
		return listaEstadoDocumentoFinal;
	}

	@Override
	public Oficinas infoOficina(Long idoficina) { 
		return mantenimientoServicio.buscarOficina(idoficina);
	}

	@Override
	public EstadoDocumento infoEstadoDocumento(Long idEstadoDocumento) { 
		return recursoDao.infoEstadoDocumento(idEstadoDocumento);
	}

	@Override
	public List<Seleccion> cbRequisitos(Long idTupac) { 
		List<Requisitos> listaRequisitos = new ArrayList<Requisitos>();
		List<Seleccion> cbRequisito = new ArrayList<Seleccion>();
		listaRequisitos = recursoDao.cbRequisitos(idTupac);
		
		for (Requisitos i : listaRequisitos) {
			Seleccion item = new Seleccion();
			item.setCodigo(i.getREQUISITOSTUPACPK());
			item.setEtiqueta(i.getVNOMBRE());
			cbRequisito.add(item);
		}
		
		return cbRequisito;
	}

 

}
