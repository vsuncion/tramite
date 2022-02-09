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
import com.tramite.app.Entidades.Correlativo;
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
	@Transactional(readOnly = true)
	public List<Trabajadores> listarTrabajadores() {
		return  mantenimientoDao.listarTrabajadores();
	}

	@Override
	@Transactional(readOnly = true)
	public Informacion informacionMunicipalidad() {
		Informacion informacion = new Informacion();
		try {
			informacion = mantenimientoDao.informacionMunicipalidad();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return informacion;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Oficinas> listarOficinas() {
		return mantenimientoDao.listarOficinas();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta guardarOficina(Oficinas oficina) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.guardarOficina(oficina);

			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}

		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}

		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Oficinas> buscarOficinas(Oficinas oficinas) {
		return mantenimientoDao.buscarOficinas(oficinas);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Seleccion> listarOficinasCombo() {
		List<Seleccion> listaSeleccion = new ArrayList<>();

		List<Oficinas>  listarOficinas = mantenimientoDao.listarOficinas();

		for (Oficinas itemOficinas : listarOficinas) {
			Seleccion seleccion = new Seleccion();
			seleccion.setCodigo(itemOficinas.getNIDOFICINAPK());
			seleccion.setEtiqueta(itemOficinas.getVNOMBRE());
			listaSeleccion.add(seleccion);
		}

		return listaSeleccion;
	}

	@Override
	@Transactional(readOnly = true)
	public Oficinas buscarOficina(Long id) {
		Oficinas buscarOficinas = new Oficinas();
		try {
			buscarOficinas = mantenimientoDao.buscarOficinaId(id);
		} catch (Exception e) {
			logger.info("error buscarOficina ="+e.getMessage());
		}
		return buscarOficinas;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta actualizarOficina(Oficinas oficina) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.actualizarOficina(oficina);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}

		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta eliminarOficina(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;
		try {
			respuesta = mantenimientoDao.eliminarOficina(id);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}

		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public List<TipoDocumentos> listarTipoDocumento() {
		return mantenimientoDao.listarTipoDocumento();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta guardarTipoDocumentos(TipoDocumentos tipoDocumentos) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.guardarTipoDocumentos(tipoDocumentos);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoDocumentos> buscarTipoDocumentos(TipoDocumentos tipoDocumentos) {
		return mantenimientoDao.buscarTipoDocumentos(tipoDocumentos);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta actualizarTipoDocumento(TipoDocumentos tipoDocumentos) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.actualizarTipoDocumento(tipoDocumentos);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta eliminarTipoDocumento(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.eliminarTipoDocumento(id);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public TipoDocumentos buscarTipoDocumentoId(Long id) {
		TipoDocumentos busquedaTipoDocumentos = new TipoDocumentos();
		try {
			busquedaTipoDocumentos = mantenimientoDao.buscarTipoDocumentoId(id);
		} catch (Exception e) {
			logger.info("error buscarTipoDocumentoId="+e.getMessage());
		}
		return busquedaTipoDocumentos;
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoTramite> listarTipoTramite() {
		return mantenimientoDao.listarTipoTramite();
	}

	@Override
	@Transactional(readOnly = true)
	public List<TipoTramite> buscarTipoTramite(TipoTramite tipoTramite) {
		return mantenimientoDao.buscarTipoTramite(tipoTramite);
	}

	@Override
	@Transactional(readOnly = true)
	public TipoTramite buscarTipoTramiteId(Long id) {
		TipoTramite buscarTipoTramite = new TipoTramite();
		try {
			buscarTipoTramite = mantenimientoDao.buscarTipoTramiteId(id);
		} catch (Exception e) {
			logger.info("error buscarTipoTramiteId = "+e.getMessage());
		}
		return buscarTipoTramite;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta eliminarTipoTramite(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.eliminarTipoTramite(id);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}

		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}

		return mostrarmensaje;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta actualizarTipoTramite(TipoTramite tipoTramite) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.actualizarTipoTramite(tipoTramite);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta guardarTipoTramite(TipoTramite tipoTramite) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.guardarTipoTramite(tipoTramite);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EstadoDocumento> listarEstadoDocumento() {
		return mantenimientoDao.listarEstadoDocumento();
	}

	@Override
	@Transactional(readOnly = true)
	public List<EstadoDocumento> buscarEstadoDocumento(EstadoDocumento estadoDocumento) {
		return mantenimientoDao.buscarEstadoDocumento(estadoDocumento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Profesiones> listarProfesiones() {
		return mantenimientoDao.listarProfesiones();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Profesiones> buscarProfesiones(Profesiones profesiones) {
		return mantenimientoDao.buscarProfesiones(profesiones);
	}

	@Override
	@Transactional(readOnly = true)
	public Profesiones buscarProfesionesId(Long id) {
		Profesiones buscarProfesiones = new Profesiones();
		try {
			buscarProfesiones = mantenimientoDao.buscarProfesionesId(id);
		} catch (Exception e) {
			logger.info("error buscarProfesionesId = "+e.getMessage());
		}
		return buscarProfesiones;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta guardarProfesiones(Profesiones profesiones) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.guardarProfesiones(profesiones);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta actualizarProfesiones(Profesiones profesiones) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.actualizarProfesiones(profesiones);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}

		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta eliminarProfesiones(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.eliminarProfesiones(id);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta actualizarinformacionMunicipalidad(Informacion informacion) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		try {
			respuesta = mantenimientoDao.actualizarinformacionMunicipalidad(informacion);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			respuesta = false;
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
			logger.info("======================= "+this.getClass().getName()+" ===> actualizarinformacionMunicipalidad ================"+e.getMessage());
		}

		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Requisitos> listarRequisitos() {
		return mantenimientoDao.listarRequisitos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Requisitos> buscarRequisitos(Requisitos requisitos) {
		return mantenimientoDao.buscarRequisitos(requisitos);
	}

	@Override
	@Transactional(readOnly = true)
	public Requisitos buscarRequisitosId(Long id) {
		Requisitos buscarRequisitos = new Requisitos();
		try {
			buscarRequisitos = mantenimientoDao.buscarRequisitosId(id);
		} catch (Exception e) {
			logger.info("error buscarRequisitosId =  "+e.getMessage());
		}
		return buscarRequisitos;
	}

	@Override
	@Transactional
	public MensajeRespuesta guardarRequisitos(Requisitos requisitos) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.guardarRequisitos(requisitos);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional
	public MensajeRespuesta actualizarRequisitos(Requisitos requisitos) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.actualizarRequisitos(requisitos);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional
	public MensajeRespuesta eliminarRequisitos(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.eliminarRequisitos(id);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tupac> listarTupac() {
		return mantenimientoDao.listarTupac();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tupac> buscarTupac(Tupac tupac) {
		return mantenimientoDao.buscarTupac(tupac);
	}

	@Override
	@Transactional(readOnly = true)
	public Tupac buscarTupacPorId(Long id) {
		Tupac buscarTupac = new Tupac();
		try {
			buscarTupac = mantenimientoDao.buscarTupacPorId(id);
		} catch (Exception e) {
			logger.info("error buscarTupacPorId = "+e.getMessage());
		}
		return buscarTupac;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta guardarTupac(Tupac tupac) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.guardarTupac(tupac);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta actualizarTupac(Tupac tupac) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.actualizarTupac(tupac);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta eliminarTupac(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.eliminarTupac(id);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public List<RequisitosTupac> listarRequisitosTupacPorIdTupac(Long id) {
		return mantenimientoDao.listarRequisitosTupacPorIdTupac(id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta guardarRequisitosTupac(RequisitosTupac requisitosTupac) {
		RequisitosTupac verificacionRequisitosTupac = new RequisitosTupac();
		MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
		boolean respuesta = false;

		try {
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

		} catch (Exception e) {
			mensajeRespuesta.setCodigo(Constantes.transaccionIncorrecta);
			mensajeRespuesta.setMensaje(e.getMessage());
		}
		return mensajeRespuesta;
	}

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class) 
	public MensajeRespuesta activarRequisitosTupac(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;
		try {
			respuesta = mantenimientoDao.activarRequisitosTupac(id);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta eliminarRequisitosTupac(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;
		try {
			respuesta = mantenimientoDao.eliminarRequisitosTupac(id);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Seleccion> cbProfesiones() {
		List<Seleccion> cbProfesion = new ArrayList<>();
		List<Profesiones> listaProfesiones = mantenimientoDao.listarProfesiones();
		for (Profesiones i : listaProfesiones) {
			Seleccion seleccion = new Seleccion();
			seleccion.setCodigo(i.getNIDPROFESIONPK());
			seleccion.setEtiqueta(i.getVNOMBRE());
			cbProfesion.add(seleccion);
		}
		return cbProfesion;
	}

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public List<Persona> listarTrabajadorPersona() {
		return mantenimientoDao.listarTrabajadorPersona();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Persona> buscarTrabajadorPersona(Persona persona) {
		return mantenimientoDao.buscarTrabajadorPersona(persona);
	}

	@Override
	@Transactional(readOnly = true)
	public Persona buscarTrabajadorPersonaPorId(Long id) {
		Persona buscarPersona = new Persona();
		try {
			buscarPersona = mantenimientoDao.buscarTrabajadorPersonaPorId(id);
		} catch (Exception e) {
			logger.info("error buscarTrabajadorPersonaPorId = "+e.getMessage());
		}
		return buscarPersona;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta guardarTrabajadorPersona(Persona persona) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;
		try {
			respuesta = mantenimientoDao.guardarTrabajadorPersona(persona);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta actualizarTrabajadorPersona(Persona persona) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.actualizarTrabajadorPersona(persona);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta eliminarTrabajadorPersona(Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.eliminarTrabajadorPersona(id);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Seleccion> cbCriteriosBusquedaTrabajador() {
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<>();
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
			}else if (i == 4) {
				seleccion.setCodigo(Long.valueOf(i));
				seleccion.setEtiqueta(Constantes.listaBusquedaTrabajadorOFICINA);
			}

			cbTipoDocumentoRegistro.add(seleccion);
		}

		return cbTipoDocumentoRegistro;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuarios> listarUsuarioPersona() {
		return mantenimientoDao.listarUsuarioPersona();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuarios> buscarUsuarioPersona(Usuarios usuarios) {
		return mantenimientoDao.buscarUsuarioPersona(usuarios);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuarios buscarUsuarioPersonaPorId(Long id) {
		Usuarios buscarUsuarios = new Usuarios();
		try {
			buscarUsuarios = mantenimientoDao.buscarUsuarioPersonaPorId(id);
		} catch (Exception e) {
			logger.info("error buscarUsuarioPersonaPorId ="+e.getMessage());
		}
		return buscarUsuarios;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta guardarUsuarioPersona(Usuarios usuarios) {
		usuarios.setVCLAVE(encriptar.encode(usuarios.getVCLAVE()));

		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = mantenimientoDao.guardarUsuarioPersona(usuarios);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta actualizarUsuarioPersona(Usuarios usuarios) {

		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		if (usuarios.getVCLAVE().length() > 0) {
			usuarios.setVCLAVE(encriptar.encode(usuarios.getVCLAVE()));
		}

		try {
			respuesta = mantenimientoDao.actualizarUsuarioPersona(usuarios);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);
			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Seleccion> listarPerfiles() {
		List<Seleccion> cbPerfiles = new ArrayList<>();
		List<Perfiles> listarPerfiles = mantenimientoDao.listarPerfiles();

		for (Perfiles i : listarPerfiles) {
			Seleccion seleccion = new Seleccion();
			seleccion.setCodigo(i.getNidperfilpk());
			seleccion.setEtiqueta(i.getVnombre());
			cbPerfiles.add(seleccion);
		}

		return cbPerfiles;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta eliminarUsuarioPersona(Long idUsuario, Long idUsuarioPerfil) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		try {
			respuesta = mantenimientoDao.eliminarUsuarioPersona(idUsuario, idUsuarioPerfil);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta guardarFeriado(Feriados feriados) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		feriados.setDFERIADO(Fechas.convertStringToDate(feriados.getVFERIADO()));
		try {
			respuesta = mantenimientoDao.guardarFeriado(feriados);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta eliminarFeriado(Long idFeriado) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		try {
			respuesta = mantenimientoDao.eliminarFeriado(idFeriado);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;

	}

	@Override
	@Transactional(readOnly = true)
	public List<Feriados> listarFeriados() {
		return mantenimientoDao.listarFeriados();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Feriados> buscarFeriados(Feriados feriados) {
		return mantenimientoDao.buscarFeriados(feriados);
	}

	@Override
	@Transactional(readOnly = true)
	public Persona infoPersona(Persona persona) {
		Persona buscarPersona = new Persona();
		try {
			buscarPersona = mantenimientoDao.infoPersona(persona);
		} catch (Exception e) {
			logger.info("error infoPersona ="+e.getMessage());
		}
		return buscarPersona;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Correlativo> listarCorrelativos(Correlativo formCorrelativo) { 
		return mantenimientoDao.listarCorrelativos(formCorrelativo);
	}

	@Override
	@Transactional(readOnly = true)
	public Correlativo infoCorrelativo(Long idcorrelativo) {
		Correlativo buscarCorrelativo = new Correlativo();
		try {
			buscarCorrelativo = mantenimientoDao.infoCorrelativo(idcorrelativo);
		} catch (Exception e) {
			logger.info("error infoCorrelativo ="+e.getMessage());
		}
		return buscarCorrelativo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta actualizarCorrelativo(Correlativo formCorrelativo) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		boolean respuesta = false;
		try {
			respuesta = mantenimientoDao.actualizarCorrelativo(formCorrelativo);
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje; 
	}

	 
}
