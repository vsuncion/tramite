package com.tramite.app.Datos;

import java.util.List;

import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.MovimientoExpediente; 

public interface ExpedienteDao {

	List<Bandeja> listarBandeja(Long oficina,Long estadodocumento);
	boolean recibirExpediente(Long idMovimiento);
	Expediente infoExpediente(Long idexpediente);
	MovimientoExpediente infoMovimiento(Long idexpediente,Long idmovimiento);
	boolean responderExpediente(Expediente formexpediente);
}
