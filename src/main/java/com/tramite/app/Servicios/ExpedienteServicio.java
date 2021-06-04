package com.tramite.app.Servicios;

import java.util.List;

import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.MovimientoExpediente;

public interface ExpedienteServicio {

	List<Bandeja> listarBandeja(Long oficina,Long estadodocumento);
	MensajeRespuesta recibirExpediente(Long idMovimiento);
	Expediente infoExpediente(Long idexpediente);
	MovimientoExpediente infoMovimiento(Long idexpediente,Long idmovimiento);
	MensajeRespuesta responderExpediente(Expediente formExpediente);
}
