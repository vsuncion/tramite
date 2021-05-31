package com.tramite.app.Servicios.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tramite.app.Datos.RecursoDao;
import com.tramite.app.Entidades.Seleccion;
import com.tramite.app.Entidades.TipoDocumentos;
import com.tramite.app.Entidades.Tupac;
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

}
