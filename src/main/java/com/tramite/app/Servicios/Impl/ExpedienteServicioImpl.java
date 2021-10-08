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
		}
		
		

		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public Expediente infoExpediente(Long idexpediente) { 
		return expedienteDao.infoExpediente(idexpediente);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MovimientoExpediente infoMovimiento(Long idexpediente, Long idmovimiento) {
		return expedienteDao.infoMovimiento(idexpediente, idmovimiento);
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
			logger.info("======================= "+this.getClass().getName()+" ===> responderExpediente ================"+e.getMessage());
			throw new Exception(e.getMessage()); 
		}
		
		
		
	}

	@Override
	@Transactional(readOnly = true)
	public ArchivoMovimiento infoMovimientoArchivoRespuesta(Long idexpediente, Long idoficina,
			String nombrearchivo) { 
		return expedienteDao.infoMovimientoArchivoRespuesta(idexpediente, idoficina, nombrearchivo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<HojaRuta> infoHojaRuta(String anio, String codigoExpediente) { 
		return expedienteDao.infoHojaRuta(anio, codigoExpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public Expediente infoExpedienteCodigo(String anio, String codigoExpediente) { 
		return expedienteDao.infoExpedienteCodigo(anio, codigoExpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<HojaRuta> infoHojaRutaIdExpediente(Long idExpediente) { 
		return expedienteDao.infoHojaRutaIdExpediente(idExpediente);
	}

	@Override
	public Expediente infoExpedienteId(Long idExpediente) { 
		return expedienteDao.infoExpedienteId(idExpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public MovimientoExpediente infoMovimientoIdexpediente(Long idMovimiento) { 
		return expedienteDao.infoMovimientoIdexpediente(idMovimiento);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ArchivoTupac> listarArchivosTupa(Long idexpediente) { 
		return expedienteDao.listarArchivosTupa(idexpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public ArchivoTupac infoArchivoTupa(Long idexpediente, Long idarchivorequisito) { 
		return expedienteDao.infoArchivoTupa(idexpediente,idarchivorequisito);
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
		return expedienteDao.infoExpedienteCodigoInterno(anio, codigoExpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Expediente> listarExpedientesInternoUsuario(Expediente formexpediente) {
		return expedienteDao.listarExpedientesInternoUsuario(formexpediente);
	}

	 

}
