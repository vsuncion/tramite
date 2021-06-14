package com.tramite.app.Servicios.Impl;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.tramite.app.Datos.MantenimientoDao;
import com.tramite.app.Entidades.EstadoDocumento;
import com.tramite.app.Entidades.Feriados;
import com.tramite.app.Entidades.Informacion;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.Oficinas;
import com.tramite.app.Entidades.Perfiles;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.Profesiones;
import com.tramite.app.Entidades.Requisitos;
import com.tramite.app.Entidades.RequisitosTupac;
import com.tramite.app.Entidades.Seleccion;
import com.tramite.app.Entidades.TipoDocumentos;
import com.tramite.app.Entidades.TipoTramite;
import com.tramite.app.Entidades.Trabajadores;
import com.tramite.app.Entidades.Tupac;
import com.tramite.app.Entidades.Usuarios;
import com.tramite.app.Servicios.MantenimientoServicio;
import com.tramite.app.utilitarios.Constantes;
import com.tramite.app.utilitarios.Fechas;

@Service
public class MantenimientoServicioImpl implements MantenimientoServicio {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MantenimientoDao mantenimientoDao;

	@Autowired
	private BCryptPasswordEncoder encriptar;

	@Override
	public List<Trabajadores> listarTrabajadores() {
		List<Trabajadores> lista = new ArrayList<Trabajadores>();
		lista = mantenimientoDao.listarTrabajadores();
		return lista;
	}

	@Override
	public Informacion informacionMunicipalidad() {
		Informacion informacion = new Informacion();
		informacion = mantenimientoDao.informacionMunicipalidad();
		return informacion;
	}

	@Override
	public List<Oficinas> listarOficinas() {
		return mantenimientoDao.listarOficinas();
	}

	@Override
	@Transactional
	public MensajeRespuesta guardarOficina(Oficinas oficina) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.guardarOficina(oficina);

		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public List<Oficinas> buscarOficinas(Oficinas oficinas) {
		return mantenimientoDao.buscarOficinas(oficinas);
	}

	@Override
	public List<Seleccion> listarOficinasCombo() {
		List<Oficinas> listarOficinas = new ArrayList<Oficinas>();
		List<Seleccion> listaSeleccion = new ArrayList<Seleccion>();

		listarOficinas = mantenimientoDao.listarOficinas();

		for (Oficinas itemOficinas : listarOficinas) {
			Seleccion seleccion = new Seleccion();
			seleccion.setCodigo(itemOficinas.getNIDOFICINAPK());
			seleccion.setEtiqueta(itemOficinas.getVNOMBRE());
			listaSeleccion.add(seleccion);
		}

		return listaSeleccion;
	}

	@Override
	public Oficinas buscarOficina(Long id) {
		return mantenimientoDao.buscarOficinaId(id);
	}

	@Override
	@Transactional
	public MensajeRespuesta actualizarOficina(Oficinas oficina) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.actualizarOficina(oficina);

		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta eliminarOficina(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;
		respuesta = mantenimientoDao.eliminarOficina(id);

		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public List<Seleccion> listarEstadosRegistro() {
		List<Seleccion> cbEstadosRegistro = new ArrayList<Seleccion>();
		int[] listaEstados = Constantes.listaEstadoRegistro;
		for (int i = 0; i < listaEstados.length; i++) {
			Seleccion seleccion = new Seleccion();
			logger.info("=====" + listaEstados[i]);
			if (listaEstados[i] == 1) {
				seleccion.setCodigo(Long.valueOf(listaEstados[i]));
				seleccion.setEtiqueta(Constantes.estadoActivadoLetras);
			} else {
				seleccion.setCodigo(Long.valueOf(listaEstados[i]));
				seleccion.setEtiqueta(Constantes.estadoDesactivadoLetras);
			}

			cbEstadosRegistro.add(seleccion);
		}
		return cbEstadosRegistro;
	}

	@Override
	public List<TipoDocumentos> listarTipoDocumento() {
		return mantenimientoDao.listarTipoDocumento();
	}

	@Override
	@Transactional
	public MensajeRespuesta guardarTipoDocumentos(TipoDocumentos tipoDocumentos) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;
		respuesta = mantenimientoDao.guardarTipoDocumentos(tipoDocumentos);

		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public List<TipoDocumentos> buscarTipoDocumentos(TipoDocumentos tipoDocumentos) {
		return mantenimientoDao.buscarTipoDocumentos(tipoDocumentos);
	}

	@Override
	@Transactional
	public MensajeRespuesta actualizarTipoDocumento(TipoDocumentos tipoDocumentos) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.actualizarTipoDocumento(tipoDocumentos);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta eliminarTipoDocumento(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;
		respuesta = mantenimientoDao.eliminarTipoDocumento(id);

		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public TipoDocumentos buscarTipoDocumentoId(Long id) {
		return mantenimientoDao.buscarTipoDocumentoId(id);
	}

	@Override
	public List<TipoTramite> listarTipoTramite() {
		return mantenimientoDao.listarTipoTramite();
	}

	@Override
	public List<TipoTramite> buscarTipoTramite(TipoTramite tipoTramite) {
		return mantenimientoDao.buscarTipoTramite(tipoTramite);
	}

	@Override
	public TipoTramite buscarTipoTramiteId(Long id) {
		return mantenimientoDao.buscarTipoTramiteId(id);
	}

	@Override
	@Transactional
	public MensajeRespuesta eliminarTipoTramite(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.eliminarTipoTramite(id);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta actualizarTipoTramite(TipoTramite tipoTramite) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.actualizarTipoTramite(tipoTramite);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta guardarTipoTramite(TipoTramite tipoTramite) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.guardarTipoTramite(tipoTramite);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public List<EstadoDocumento> listarEstadoDocumento() {
		return mantenimientoDao.listarEstadoDocumento();
	}

	@Override
	public List<EstadoDocumento> buscarEstadoDocumento(EstadoDocumento estadoDocumento) {
		return mantenimientoDao.buscarEstadoDocumento(estadoDocumento);
	}

	@Override
	public List<Profesiones> listarProfesiones() {
		return mantenimientoDao.listarProfesiones();
	}

	@Override
	public List<Profesiones> buscarProfesiones(Profesiones profesiones) {
		return mantenimientoDao.buscarProfesiones(profesiones);
	}

	@Override
	public Profesiones buscarProfesionesId(Long id) {
		return mantenimientoDao.buscarProfesionesId(id);
	}

	@Override
	@Transactional
	public MensajeRespuesta guardarProfesiones(Profesiones profesiones) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.guardarProfesiones(profesiones);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta actualizarProfesiones(Profesiones profesiones) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.actualizarProfesiones(profesiones);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta eliminarProfesiones(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;
		respuesta = mantenimientoDao.eliminarProfesiones(id);

		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta actualizarinformacionMunicipalidad(Informacion informacion) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		respuesta = mantenimientoDao.actualizarinformacionMunicipalidad(informacion);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;
	}

	@Override
	public List<Requisitos> listarRequisitos() {
		return mantenimientoDao.listarRequisitos();
	}

	@Override
	public List<Requisitos> buscarRequisitos(Requisitos requisitos) {
		return mantenimientoDao.buscarRequisitos(requisitos);
	}

	@Override
	public Requisitos buscarRequisitosId(Long id) {
		return mantenimientoDao.buscarRequisitosId(id);
	}

	@Override
	@Transactional
	public MensajeRespuesta guardarRequisitos(Requisitos requisitos) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.guardarRequisitos(requisitos);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta actualizarRequisitos(Requisitos requisitos) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;
		respuesta = mantenimientoDao.actualizarRequisitos(requisitos);

		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta eliminarRequisitos(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.eliminarRequisitos(id);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public List<Tupac> listarTupac() {
		return mantenimientoDao.listarTupac();
	}

	@Override
	public List<Tupac> buscarTupac(Tupac tupac) {
		return mantenimientoDao.buscarTupac(tupac);
	}

	@Override
	public Tupac buscarTupacPorId(Long id) {
		return mantenimientoDao.buscarTupacPorId(id);
	}

	@Override
	@Transactional
	public MensajeRespuesta guardarTupac(Tupac tupac) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.guardarTupac(tupac);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta actualizarTupac(Tupac tupac) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.actualizarTupac(tupac);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta eliminarTupac(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.eliminarTupac(id);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public List<Seleccion> listarTipoDiasCombo() {
		List<Seleccion> cbTipoDias = new ArrayList<Seleccion>();
		int[] listaTipoDias = Constantes.listaTipoDias;
		for (int i = 0; i < listaTipoDias.length; i++) {
			Seleccion seleccion = new Seleccion();
			logger.info("=====" + listaTipoDias[i]);
			if (listaTipoDias[i] == 1) {
				seleccion.setCodigo(Long.valueOf(listaTipoDias[i]));
				seleccion.setEtiqueta(Constantes.estadoTipoDiasLaborables);
			} else {
				seleccion.setCodigo(Long.valueOf(listaTipoDias[i]));
				seleccion.setEtiqueta(Constantes.estadoTipoDiasCalendario);
			}

			cbTipoDias.add(seleccion);
		}

		return cbTipoDias;
	}

	@Override
	public List<RequisitosTupac> listarRequisitosTupacPorIdTupac(Long id) {
		return mantenimientoDao.listarRequisitosTupacPorIdTupac(id);
	}

	@Override
	public MensajeRespuesta guardarRequisitosTupac(RequisitosTupac requisitosTupac) {
		RequisitosTupac verificacionRequisitosTupac = new RequisitosTupac();
		MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
		boolean respuesta = false;

		// mensajeRespuesta.setVACCION(Constantes.accionSI);

		verificacionRequisitosTupac = mantenimientoDao.buscarPorTupacRequisitos(requisitosTupac.getTUPACFK(),
				requisitosTupac.getREQUISITOSFK());

		if (verificacionRequisitosTupac.getNREQTUPACPK() == null) {
			respuesta = mantenimientoDao.guardarRequisitosTupac(requisitosTupac);
			if (respuesta == true) {
				mensajeRespuesta.setCodigo(Constantes.transaccionCorrecta);
				mensajeRespuesta.setMensaje(Constantes.transaccionCorrectaTexto);
			} else {
				mensajeRespuesta.setCodigo(Constantes.transaccionIncorrecta);
				mensajeRespuesta.setMensaje(Constantes.transaccionIncorrectaTexto);
			}

		} else {
			mensajeRespuesta.setCodigo(Constantes.transaccionIncorrecta);
			mensajeRespuesta.setMensaje(Constantes.mensajeRegistroDuplicado.replace("$NOMBRE$", verificacionRequisitosTupac.getVNOMBRE()));
		}

		return mensajeRespuesta;
	}

	@Override
	public List<Seleccion> listarRequisitosTupac() {
		List<Requisitos> listaRequisitos = new ArrayList<Requisitos>();
		List<Seleccion> listaRequisitosTupac = new ArrayList<Seleccion>();

		listaRequisitos = mantenimientoDao.listarRequisitos();
		for (Requisitos i : listaRequisitos) {
			if (i.getNESTADO() == 1) {
				Seleccion seleccion = new Seleccion();
				seleccion.setCodigo(i.getREQUISITOSTUPACPK());
				seleccion.setEtiqueta(i.getVNOMBRE());
				listaRequisitosTupac.add(seleccion);
			}

		}

		return listaRequisitosTupac;
	}

	@Override
	@Transactional 
	public MensajeRespuesta activarRequisitosTupac(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;
		respuesta = mantenimientoDao.activarRequisitosTupac(id);

		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta eliminarRequisitosTupac(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.eliminarRequisitosTupac(id);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public List<Seleccion> cbProfesiones() {
		List<Profesiones> listaProfesiones = new ArrayList<Profesiones>();
		List<Seleccion> cbProfesion = new ArrayList<Seleccion>();

		listaProfesiones = mantenimientoDao.listarProfesiones();
		for (Profesiones i : listaProfesiones) {
			Seleccion seleccion = new Seleccion();
			seleccion.setCodigo(i.getNIDPROFESIONPK());
			seleccion.setEtiqueta(i.getVNOMBRE());
			cbProfesion.add(seleccion);
		}
		return cbProfesion;
	}

	@Override
	public List<Seleccion> cbTipoDocumentoRegistro() {
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		int[] listaTipoDocumentoRegistro = Constantes.listaTipoDocumentoRegistro;
		for (int i = 0; i < listaTipoDocumentoRegistro.length; i++) {
			Seleccion seleccion = new Seleccion();
			logger.info("=====" + listaTipoDocumentoRegistro[i]);
			if (listaTipoDocumentoRegistro[i] == 1) {
				seleccion.setCodigo(Long.valueOf(listaTipoDocumentoRegistro[i]));
				seleccion.setEtiqueta(Constantes.TipoDocumentoRegistroDNI);
			} else {
				seleccion.setCodigo(Long.valueOf(listaTipoDocumentoRegistro[i]));
				seleccion.setEtiqueta(Constantes.TipoDocumentoRegistroCarnetExtranjeria);
			}

			cbTipoDocumentoRegistro.add(seleccion);
		}

		return cbTipoDocumentoRegistro;
	}

	@Override
	public List<Persona> listarTrabajadorPersona() {
		return mantenimientoDao.listarTrabajadorPersona();
	}

	@Override
	public List<Persona> buscarTrabajadorPersona(Persona persona) {
		return mantenimientoDao.buscarTrabajadorPersona(persona);
	}

	@Override
	public Persona buscarTrabajadorPersonaPorId(Long id) {
		return mantenimientoDao.buscarTrabajadorPersonaPorId(id);
	}

	@Override
	@Transactional
	public MensajeRespuesta guardarTrabajadorPersona(Persona persona) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.guardarTrabajadorPersona(persona);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta actualizarTrabajadorPersona(Persona persona) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;
		respuesta = mantenimientoDao.actualizarTrabajadorPersona(persona);

		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta eliminarTrabajadorPersona(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.eliminarTrabajadorPersona(id);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public List<Seleccion> cbCriteriosBusquedaTrabajador() {
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		String[] listaTipoDocumentoRegistro = Constantes.listaBusquedaTrabajador;
		for (int i = 0; i < listaTipoDocumentoRegistro.length; i++) {
			Seleccion seleccion = new Seleccion();
			logger.info("=====" + listaTipoDocumentoRegistro[i]);
			if (i == 0) {
				seleccion.setCodigo(Long.valueOf(i));
				seleccion.setEtiqueta(Constantes.listaBusquedaTrabajadorNombre);
			} else if (i == 1) {
				seleccion.setCodigo(Long.valueOf(i));
				seleccion.setEtiqueta(Constantes.listaBusquedaTrabajadorAPELLIDO_PATERNO);
			} else if (i == 2) {
				seleccion.setCodigo(Long.valueOf(i));
				seleccion.setEtiqueta(Constantes.listaBusquedaTrabajadorAPELLIDO_MATERNO);
			} else if (i == 3) {
				seleccion.setCodigo(Long.valueOf(i));
				seleccion.setEtiqueta(Constantes.listaBusquedaTrabajadorDNI);
			}

			cbTipoDocumentoRegistro.add(seleccion);
		}

		return cbTipoDocumentoRegistro;
	}

	@Override
	public List<Usuarios> listarUsuarioPersona() {
		return mantenimientoDao.listarUsuarioPersona();
	}

	@Override
	public List<Usuarios> buscarUsuarioPersona(Usuarios usuarios) {
		return mantenimientoDao.buscarUsuarioPersona(usuarios);
	}

	@Override
	public Usuarios buscarUsuarioPersonaPorId(Long id) {
		return mantenimientoDao.buscarUsuarioPersonaPorId(id);
	}

	@Override
	@Transactional
	public MensajeRespuesta guardarUsuarioPersona(Usuarios usuarios) {
		usuarios.setVCLAVE(encriptar.encode(usuarios.getVCLAVE()));

		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		respuesta = mantenimientoDao.guardarUsuarioPersona(usuarios);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional
	public MensajeRespuesta actualizarUsuarioPersona(Usuarios usuarios) {

		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		if (usuarios.getVCLAVE().length() > 0) {
			usuarios.setVCLAVE(encriptar.encode(usuarios.getVCLAVE()));
		}

		respuesta = mantenimientoDao.actualizarUsuarioPersona(usuarios);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public List<Seleccion> listarPerfiles() {
		List<Perfiles> listarPerfiles = new ArrayList<Perfiles>();
		List<Seleccion> cbPerfiles = new ArrayList<Seleccion>();

		listarPerfiles = mantenimientoDao.listarPerfiles();

		for (Perfiles i : listarPerfiles) {
			Seleccion seleccion = new Seleccion();
			seleccion.setCodigo(i.getNidperfilpk());
			seleccion.setEtiqueta(i.getVnombre());
			cbPerfiles.add(seleccion);
		}

		return cbPerfiles;
	}

	@Override
	@Transactional
	public MensajeRespuesta eliminarUsuarioPersona(Long idUsuario, Long idUsuarioPerfil) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		respuesta = mantenimientoDao.eliminarUsuarioPersona(idUsuario, idUsuarioPerfil);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public MensajeRespuesta guardarFeriado(Feriados feriados) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		feriados.setDFERIADO(Fechas.convertStringToDate(feriados.getVFERIADO()));
		respuesta = mantenimientoDao.guardarFeriado(feriados);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public MensajeRespuesta eliminarFeriado(Long idFeriado) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		respuesta = mantenimientoDao.eliminarFeriado(idFeriado);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;

	}

	@Override
	public List<Feriados> listarFeriados() {
		return mantenimientoDao.listarFeriados();
	}

	@Override
	public List<Feriados> buscarFeriados(Feriados feriados) {
		return mantenimientoDao.buscarFeriados(feriados);
	}

	@Override
	public Persona infoPersona(Persona persona) { 
		return mantenimientoDao.infoPersona(persona);
	}

}
