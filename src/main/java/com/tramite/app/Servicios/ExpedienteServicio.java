package com.tramite.app.Servicios;

import java.util.List;

import com.tramite.app.Entidades.ArchivoMovimiento;
import com.tramite.app.Entidades.Bandeja; 
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.HojaRuta;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.MovimientoExpediente;

public interface ExpedienteServicio {

	List<Bandeja> listarBandeja(Long oficina,Long estadodocumento);
	MensajeRespuesta recibirExpediente(Long idMovimiento,Long idOficina,Long idExpediente);
	Expediente infoExpediente(Long idexpediente);
	MovimientoExpediente infoMovimiento(Long idexpediente,Long idmovimiento);
	MensajeRespuesta responderExpediente(Expediente formExpediente);
	ArchivoMovimiento infoMovimientoArchivoRespuesta(Long idexpediente,Long idoficina,String nombrearchivo);
	List<HojaRuta> infoHojaRuta(String anio,String codigoExpediente);
	Expediente infoExpedienteCodigo(String anio, String codigoExpediente); 
	List<HojaRuta> infoHojaRutaIdExpediente(Long idExpediente);
	Expediente infoExpedienteId(Long idExpediente); 
	MovimientoExpediente infoMovimientoIdexpediente(Long idMovimiento);
}
