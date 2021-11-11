package com.tramite.app.Servicios.Impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
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
import com.tramite.app.utilitarios.Constantes;

@Service
public class ExpedienteServicioImpl implements ExpedienteServicio {
	
	@Autowired
	private ExpedienteDao expedienteDao;
	
	@Autowired
	private BCryptPasswordEncoder encriptar;
	
	//Logger logger = LoggerFactory.getLogger(getClass());
	private static final Logger logger = Logger.getLogger(ExpedienteServicioImpl.class);
	
	@Override
	@Transactional(readOnly = true)
	public List<Bandeja> listarBandeja(Long oficina, Long estadodocumento) { 
		List<Bandeja> lista = new ArrayList<Bandeja>();
		try {
			lista =expedienteDao.listarBandeja(oficina, estadodocumento);
		} catch (Exception e) {
			// TODO: handle exception
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
			// TODO: handle exception
			logger.error("ERROR ="  + this.getClass().getName()+".recibirExpediente ==Causa==" + e.getCause()+" =Mensage="+e.getMessage());
		}
		
		

		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public Expediente infoExpediente(Long idexpediente) { 
		Expediente expediente = new Expediente();
		try {
			expediente = expedienteDao.infoExpediente(idexpediente);
		} catch (Exception e) {
		// TODO: handle exception
		logger.error("ERROR =" + this.getClass().getName()+".infoExpediente ==Causa==" + e.getCause()+" =Mensage="+e.getMessage());
		}
		return expediente;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MovimientoExpediente infoMovimiento(Long idexpediente, Long idmovimiento) {
		MovimientoExpediente movimientoExpediente = new MovimientoExpediente();
		try {
			movimientoExpediente = expedienteDao.infoMovimiento(idexpediente, idmovimiento);
		} catch (Exception e) {
		// TODO: handle exception
		logger.error("ERROR =" + this.getClass().getName()+".infoMovimiento ==Causa==" + e.getCause()+" =Mensage="+e.getMessage());
		}
		return movimientoExpediente;
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
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

			} else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}

			return mostrarmensaje;
			
		} catch (Exception e) { 
			logger.info("======================= "+"ERROR =" + this.getClass().getName()+" ===> responderExpediente ================"+e.getMessage());
			throw new Exception(e.getMessage()); 
		}
		
		
		
	}

	@Override
	@Transactional(readOnly = true)
	public ArchivoMovimiento infoMovimientoArchivoRespuesta(Long idexpediente, Long idoficina,
			String nombrearchivo) { 
		ArchivoMovimiento archivoMovimiento = new ArchivoMovimiento();
		try {
			archivoMovimiento = expedienteDao.infoMovimientoArchivoRespuesta(idexpediente, idoficina, nombrearchivo);
		} catch (Exception e) {
		// TODO: handle exception
		logger.error("ERROR =" + this.getClass().getName()+".infoMovimientoArchivoRespuesta ==Causa==" + e.getCause()+" =Mensage="+e.getMessage());
		}
		return archivoMovimiento;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HojaRuta> infoHojaRuta(String anio, String codigoExpediente) { 
		return expedienteDao.infoHojaRuta(anio, codigoExpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public Expediente infoExpedienteCodigo(String anio, String codigoExpediente) { 
		Expediente expediente = new Expediente();
		try {
			expediente = expedienteDao.infoExpedienteCodigo(anio, codigoExpediente);
		} catch (Exception e) {
		// TODO: handle exception
		logger.error("ERROR =" + this.getClass().getName()+".infoExpedienteCodigo ==Causa==" + e.getCause()+" =Mensage="+e.getMessage());
		}
		
		return expediente;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HojaRuta> infoHojaRutaIdExpediente(Long idExpediente) { 
		return expedienteDao.infoHojaRutaIdExpediente(idExpediente);
	}

	@Override
	public Expediente infoExpedienteId(Long idExpediente) { 
		Expediente expediente = new Expediente();
		try {
			expediente = expedienteDao.infoExpedienteId(idExpediente);
		} catch (Exception e) {
		// TODO: handle exception
		logger.error("ERROR =" + this.getClass().getName()+".infoExpedienteId ==Causa==" + e.getCause()+" =Mensage="+e.getMessage());
		}

		return expediente;
	}

	@Override
	@Transactional(readOnly = true)
	public MovimientoExpediente infoMovimientoIdexpediente(Long idMovimiento) { 
		MovimientoExpediente movimientoExpediente = new MovimientoExpediente();
		try {
			movimientoExpediente = expedienteDao.infoMovimientoIdexpediente(idMovimiento);
		} catch (Exception e) {
		// TODO: handle exception
		logger.error("ERROR =" + this.getClass().getName()+".infoMovimientoIdexpediente ==Causa==" + e.getCause()+" =Mensage="+e.getMessage());
		}
		return movimientoExpediente;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ArchivoTupac> listarArchivosTupa(Long idexpediente) { 
		return expedienteDao.listarArchivosTupa(idexpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public ArchivoTupac infoArchivoTupa(Long idexpediente, Long idarchivorequisito) { 
		ArchivoTupac archivoTupac  = new ArchivoTupac();
		try {
			archivoTupac=expedienteDao.infoArchivoTupa(idexpediente,idarchivorequisito);
		} catch (Exception e) {
		// TODO: handle exception
		logger.error("ERROR =" + this.getClass().getName()+".infoArchivoTupa ==Causa==" + e.getCause()+" =Mensage="+e.getMessage());
		}
		
		return archivoTupac;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean guardarExpedienteSimpleInterno(Expediente expediente) { 
		return expedienteDao.guardarExpedienteSimpleInterno(expediente);
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
		Expediente expediente = new Expediente();
		try {
			expediente=expedienteDao.infoExpedienteCodigoInterno(anio, codigoExpediente);
		} catch (Exception e) {
		// TODO: handle exception
		logger.error("ERROR =" + this.getClass().getName()+".infoExpedienteCodigoInterno ==Causa==" + e.getCause()+" =Mensage="+e.getMessage());
		}
		
		return expediente;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Expediente> listarExpedientesInternoUsuario(Expediente formexpediente) {
		return expedienteDao.listarExpedientesInternoUsuario(formexpediente);
	}

	 

}
