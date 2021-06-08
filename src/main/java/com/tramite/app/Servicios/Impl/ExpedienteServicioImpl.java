package com.tramite.app.Servicios.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tramite.app.Datos.ExpedienteDao;
import com.tramite.app.Entidades.ArchivoMovimiento;
import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.HojaRuta;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.MovimientoExpediente;
import com.tramite.app.Servicios.ExpedienteServicio;
import com.tramite.app.utilitarios.Constantes;

@Service
public class ExpedienteServicioImpl implements ExpedienteServicio {
	
	@Autowired
	private ExpedienteDao expedienteDao;
	
	@Override
	public List<Bandeja> listarBandeja(Long oficina, Long estadodocumento) { 
		return expedienteDao.listarBandeja(oficina, estadodocumento);
	}

	@Override
	@Transactional
	public MensajeRespuesta recibirExpediente(Long idMovimiento,Long idOficina,Long idExpediente) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		respuesta = expedienteDao.recibirExpediente(idMovimiento,idOficina,idExpediente);
		
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
	public Expediente infoExpediente(Long idexpediente) { 
		return expedienteDao.infoExpediente(idexpediente);
	}

	@Override
	public MovimientoExpediente infoMovimiento(Long idexpediente, Long idmovimiento) {
		return expedienteDao.infoMovimiento(idexpediente, idmovimiento);
	}

	@Override
	@Transactional
	public MensajeRespuesta responderExpediente(Expediente formExpediente) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
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
	}

	@Override
	public ArchivoMovimiento infoMovimientoArchivoRespuesta(Long idexpediente, Long idoficina,
			String nombrearchivo) { 
		return expedienteDao.infoMovimientoArchivoRespuesta(idexpediente, idoficina, nombrearchivo);
	}

	@Override
	public List<HojaRuta> infoHojaRuta(String anio, String codigoExpediente) { 
		return expedienteDao.infoHojaRuta(anio, codigoExpediente);
	}

	@Override
	public Expediente infoExpedienteCodigo(String anio, String codigoExpediente) { 
		return expedienteDao.infoExpedienteCodigo(anio, codigoExpediente);
	}

	@Override
	public List<HojaRuta> infoHojaRutaIdExpediente(Long idExpediente) { 
		return expedienteDao.infoHojaRutaIdExpediente(idExpediente);
	}

	@Override
	public Expediente infoExpedienteId(Long idExpediente) { 
		return expedienteDao.infoExpedienteId(idExpediente);
	}

	@Override
	public MovimientoExpediente infoMovimientoIdexpediente(Long idMovimiento) { 
		return expedienteDao.infoMovimientoIdexpediente(idMovimiento);
	}

 

	 

}
