package com.tramite.app.Servicios.Impl;

import java.util.ArrayList;
import java.util.List; 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tramite.app.Datos.RecursoDao;
import com.tramite.app.Entidades.Cargo;
import com.tramite.app.Entidades.EstadoDocumento;
import com.tramite.app.Entidades.Oficinas;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.Requisitos;
import com.tramite.app.Entidades.Seleccion;
import com.tramite.app.Entidades.TipoDocumentos;
import com.tramite.app.Entidades.Tupac;
import com.tramite.app.Entidades.Usuarios; 
import com.tramite.app.Servicios.MantenimientoServicio;
import com.tramite.app.Servicios.RecursoServicio;
import com.tramite.app.utilitarios.Constantes;

@Service
public class RecursoServiceImpl implements RecursoServicio {

	//Logger logger = LoggerFactory.getLogger(getClass());
	private static final Logger logger = Logger.getLogger(RecursoServiceImpl.class);

	@Autowired
	private RecursoDao recursoDao;

	@Autowired
	private MantenimientoServicio mantenimientoServicio;

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public List<Seleccion> cbTipoDocuemnto() {
		List<TipoDocumentos> listarTipoDocuemntoCarga = new ArrayList<TipoDocumentos>();
		List<Seleccion> cbListarTipoDocuemnto = new ArrayList<Seleccion>();

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
	@Transactional(readOnly = true)
	public List<Seleccion> cbTupa() {
		List<Tupac> listarTupaCarga = new ArrayList<Tupac>();
		List<Seleccion> cbListarTipoDocuemnto = new ArrayList<Seleccion>();

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
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String numeroExpediente(Long idoficina) {
		String idnumeroExpediente =null;
		try {
			idnumeroExpediente = recursoDao.numeroExpediente(idoficina);
		} catch (Exception e) {
		// TODO: handle exception
		logger.error("ERROR =" + this.getClass().getName()+".numeroExpediente ==Causa==" + e.getCause()+" =Mensage="+e.getMessage());
		}
		return idnumeroExpediente;
	}

	@Override
	@Transactional(readOnly = true)
	public Usuarios infoUsuario(String vcorreo) {
		return recursoDao.infoUsuario(vcorreo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Seleccion> cbOficinasAtender(Long idoficiActual) {
		List<Oficinas> listaOfinas = new ArrayList<Oficinas>();
		List<Seleccion> listaOfinasFinal = new ArrayList<Seleccion>();

		listaOfinas = mantenimientoServicio.listarOficinas();
		for (Oficinas i : listaOfinas) {
			logger.info("==== " + i.getNIDOFICINAPK());
			if (idoficiActual != i.getNIDOFICINAPK()) {
				Seleccion item = new Seleccion();
				item.setCodigo(i.getNIDOFICINAPK());
				item.setEtiqueta(i.getVNOMBRE());
				listaOfinasFinal.add(item);
			}
		}
		return listaOfinasFinal;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Seleccion> cbAccionesAtender() {
		List<EstadoDocumento> listaEstadoDocumento = new ArrayList<EstadoDocumento>();
		List<Seleccion> listaEstadoDocumentoFinal = new ArrayList<Seleccion>();

		listaEstadoDocumento = mantenimientoServicio.listarEstadoDocumento();
		for (EstadoDocumento i : listaEstadoDocumento) {

			if (!Constantes.LETRAS_ESTADO_DOCUMENTO_PENDIENTE.equals(i.getVNOMBRE())) {
				if (!Constantes.LETRAS_ESTADO_DOCUMENTO_RECIBIDO.equals(i.getVNOMBRE())) {
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
	@Transactional(readOnly = true)
	public Oficinas infoOficina(Long idoficina) {
		return mantenimientoServicio.buscarOficina(idoficina);
	}

	@Override
	@Transactional(readOnly = true)
	public EstadoDocumento infoEstadoDocumento(Long idEstadoDocumento) {
		EstadoDocumento estadoDocumento = new EstadoDocumento();
		try {
			estadoDocumento = recursoDao.infoEstadoDocumento(idEstadoDocumento);
		} catch (Exception e) {
		// TODO: handle exception
		logger.error("ERROR =" + this.getClass().getName()+".infoEstadoDocumento ==Causa==" + e.getCause()+" =Mensage="+e.getMessage());
		}
		
		return estadoDocumento;
	}

	@Override
	@Transactional(readOnly = true)
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

	@Override
	@Transactional(readOnly = true)
	public List<Seleccion> listaEstadoDocumentos() {
		List<Seleccion> listaFinal = new ArrayList<Seleccion>();
		List<EstadoDocumento> lista = new ArrayList<EstadoDocumento>();
		lista = recursoDao.listaEstadoDocumentos();
		for (EstadoDocumento i : lista) {
			Seleccion item = new Seleccion();
			item.setCodigo(i.getIDESTADOCUMENTOPK());
			item.setEtiqueta(i.getVNOMBRE());
			listaFinal.add(item);
		}
		return listaFinal;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Seleccion> cbOficinasReportes() {
		List<Oficinas> listaOfinas = new ArrayList<Oficinas>();
		List<Seleccion> listaOfinasFinal = new ArrayList<Seleccion>();

		listaOfinas = mantenimientoServicio.listarOficinas();
		for (Oficinas i : listaOfinas) {
			    logger.info("==== " + i.getNIDOFICINAPK()); 
				Seleccion item = new Seleccion();
				item.setCodigo(i.getNIDOFICINAPK());
				item.setEtiqueta(i.getVNOMBRE());
				listaOfinasFinal.add(item); 
		}
		return listaOfinasFinal;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Seleccion> cbOCargos() {
		List<Cargo> listarCargo = new ArrayList<Cargo>();
		List<Seleccion> listaCargoFinal = new ArrayList<Seleccion>();
		
		listarCargo = recursoDao.cbCargos();
		for (Cargo i : listarCargo) {
			Seleccion item = new Seleccion();
			item.setCodigo(i.getNCARGOPK());
			item.setEtiqueta(i.getVNOMBRECARGO());
			listaCargoFinal.add(item);
		}
		return listaCargoFinal;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Seleccion> cbUsuariosOficina(Long idOficina) {
		List<Persona> listarUsuariosOficina = new ArrayList<Persona>();
		List<Seleccion> listarUsuariosOficinaFinal = new ArrayList<Seleccion>();
		
		listarUsuariosOficina = recursoDao.listaUsuariosOficina(idOficina);
		for (Persona i : listarUsuariosOficina) {
			Seleccion item = new Seleccion();
			item.setCodigo(i.getNUSUREGISTRA());
			item.setEtiqueta(i.getNOMBRE_COMPLETO());
			listarUsuariosOficinaFinal.add(item);
		}
		
		
		return listarUsuariosOficinaFinal;
	}

}
