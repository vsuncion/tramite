package com.tramite.app.Servicios.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.tramite.app.Datos.ExpedienteDao;
import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Entidades.Expediente;
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
	public MensajeRespuesta recibirExpediente(Long idMovimiento) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		respuesta = expedienteDao.recibirExpediente(idMovimiento);
		
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
	public MensajeRespuesta responderExpediente(Expediente formExpediente) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		respuesta = expedienteDao.responderExpediente(formExpediente);
		if (respuesta == true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);

		} else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}

		return mostrarmensaje;
	}

	 

}
