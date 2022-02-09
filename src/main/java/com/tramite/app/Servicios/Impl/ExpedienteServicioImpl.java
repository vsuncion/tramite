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

import com.tramite.app.Datos.ExpedienteDao;
import com.tramite.app.Entidades.ArchivoMovimiento;
import com.tramite.app.Entidades.ArchivoTupac;
import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.HojaRuta;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.MovimientoExpediente;
import com.tramite.app.Entidades.ReporteExpediente;
import com.tramite.app.Entidades.Usuarios;
import com.tramite.app.Servicios.ExpedienteServicio;
import com.tramite.app.excepciones.ResultadoException;
import com.tramite.app.utilitarios.Constantes;

@Service
public class ExpedienteServicioImpl implements ExpedienteServicio {
	
	@Autowired
	private ExpedienteDao expedienteDao;
	
	@Autowired
	private BCryptPasswordEncoder encriptar;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	@Transactional(readOnly = true)
	public List<Bandeja> listarBandeja(Long oficina, Long estadodocumento) { 
		List<Bandeja> lista = new ArrayList<>();
		try {
			lista =expedienteDao.listarBandeja(oficina, estadodocumento);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("NO SE ENCONTRARON REGISTROS");
		}
		return lista;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta recibirExpediente(Long idMovimiento,Long idOficina,Long idExpediente,Long idUsuario) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		try {
			respuesta = expedienteDao.recibirExpediente(idMovimiento,idOficina,idExpediente,idUsuario);
			
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTextoRecibirExpediente);

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
	public Expediente infoExpediente(Long idexpediente) {
		Expediente buscarInfoExpediente = new Expediente();
		try {
			buscarInfoExpediente = expedienteDao.infoExpediente(idexpediente);
		} catch (Exception e) {
			logger.info("NO SE ENCONTRO RESULTADOS infoExpediente");
		}
		return buscarInfoExpediente;
	}

	@Override
	@Transactional(readOnly = true)
	public MovimientoExpediente infoMovimiento(Long idexpediente, Long idmovimiento) {
		MovimientoExpediente buscarMovimientoExpediente = new MovimientoExpediente();
		try {
			buscarMovimientoExpediente = expedienteDao.infoMovimiento(idexpediente, idmovimiento);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buscarMovimientoExpediente;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta responderExpediente(Expediente formExpediente) throws Exception{
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		try {
			
			if(Constantes.ESTADO_DOCUMENTO_FINALIZADO==formExpediente.getNESTADODOCUMENTOFK() 
					|| Constantes.ESTADO_DOCUMENTO_ARCHIVADO==formExpediente.getNESTADODOCUMENTOFK() ) {
				
				respuesta = expedienteDao.responderExpedienteArchivadoOfinalizado(formExpediente);
				
			}else {
				respuesta = expedienteDao.responderExpediente(formExpediente);
			}
			
			
			if (respuesta == true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionDerivacionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}

			
		} catch (Exception e) { 
			logger.info("======================= "+this.getClass().getName()+" ===> responderExpediente ================"+e.getMessage());
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public ArchivoMovimiento infoMovimientoArchivoRespuesta(Long idexpediente, Long idoficina,
			String nombrearchivo) {
		ArchivoMovimiento buscarArchivoMovimiento = new ArchivoMovimiento();
		try {
			buscarArchivoMovimiento = expedienteDao.infoMovimientoArchivoRespuesta(idexpediente, idoficina, nombrearchivo);
		} catch (Exception e) {
			logger.info("ERROR infoMovimientoArchivoRespuesta ="+e.getMessage());
		}
		return buscarArchivoMovimiento;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HojaRuta> infoHojaRuta(String anio, String codigoExpediente) {
		return expedienteDao.infoHojaRuta(anio, codigoExpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public Expediente infoExpedienteCodigo(String anio, String codigoExpediente) {
		Expediente buscarExpediente= new Expediente();
		try {
			buscarExpediente=expedienteDao.infoExpedienteCodigo(anio, codigoExpediente);
		} catch (Exception e) {
			// buscarExpediente=buscarExpediente;
		}
		return buscarExpediente;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HojaRuta> infoHojaRutaIdExpediente(Long idExpediente) { 
		return expedienteDao.infoHojaRutaIdExpediente(idExpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public Expediente infoExpedienteId(Long idExpediente) {
		Expediente buscarExpediente = new Expediente();
		try {
			buscarExpediente= expedienteDao.infoExpedienteId(idExpediente);
		} catch (Exception e) {
			logger.info("error infoExpedienteId"+e.getMessage());
		}
		return buscarExpediente;
	}

	@Override
	@Transactional(readOnly = true)
	public MovimientoExpediente infoMovimientoIdexpediente(Long idMovimiento) {
		MovimientoExpediente buscarMovimientoExpediente = new MovimientoExpediente();
		try {
			buscarMovimientoExpediente = expedienteDao.infoMovimientoIdexpediente(idMovimiento);
		} catch (Exception e) {
			logger.info("error infoMovimientoIdexpediente ="+e.getMessage());
		}
		return buscarMovimientoExpediente;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ArchivoTupac> listarArchivosTupa(Long idexpediente) { 
		return expedienteDao.listarArchivosTupa(idexpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public ArchivoTupac infoArchivoTupa(Long idexpediente, Long idarchivorequisito) {
		ArchivoTupac buscarArchivoTupac = new ArchivoTupac();
		try {
			buscarArchivoTupac = expedienteDao.infoArchivoTupa(idexpediente,idarchivorequisito);
		} catch (Exception e) {
			logger.info("erro infoArchivoTupa="+e.getMessage());
		}
		return buscarArchivoTupac;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean guardarExpedienteSimpleInterno(Expediente expediente) {
		boolean respuesta = false;
		try {
			respuesta =expedienteDao.guardarExpedienteSimpleInterno(expediente);
		} catch (Exception e) {
			respuesta = false;
		}
		return respuesta;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean actualizarClave(Usuarios formUsuario) {
		boolean rpta = false;
		try {
			formUsuario.setVCLAVE(encriptar.encode(formUsuario.getVCLAVE()));
			rpta = expedienteDao.actualizarClave(formUsuario);
		} catch (Exception e) {
			rpta = false;
		}
		
		return rpta;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReporteExpediente> listaExpedientesPorEstadoDocuemnto(Long idEstadoDocumento) { 
		return expedienteDao.listaExpedientesPorEstadoDocuemnto(idEstadoDocumento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReporteExpediente> listaExpedientesPorOficina(Expediente formexpediente) { 
		return expedienteDao.listaExpedientesPorOficina(formexpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Expediente> listarExpedientesInterno(Expediente formexpediente) { 
		return expedienteDao.listarExpedientesInterno(formexpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public Expediente infoExpedienteCodigoInterno(String anio, String codigoExpediente) {
		Expediente buscarExpediente = new Expediente();
		try {
			buscarExpediente = expedienteDao.infoExpedienteCodigoInterno(anio, codigoExpediente);
		} catch (Exception e) {
			logger.info("error infoExpedienteCodigoInterno"+e.getMessage());
		}
		return buscarExpediente;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Expediente> listarExpedientesInternoUsuario(Expediente formexpediente) {
		return expedienteDao.listarExpedientesInternoUsuario(formexpediente);
	}


}
