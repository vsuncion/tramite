package com.tramite.app.Datos;

import java.util.List;

import com.tramite.app.Entidades.ArchivoMovimiento;
import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.HojaRuta;
import com.tramite.app.Entidades.MovimientoExpediente; 

public interface ExpedienteDao {

	List<Bandeja> listarBandeja(Long oficina,Long estadodocumento);
	boolean recibirExpediente(Long idMovimiento);
	Expediente infoExpediente(Long idexpediente);
	MovimientoExpediente infoMovimiento(Long idexpediente,Long idmovimiento);
	boolean responderExpediente(Expediente formexpediente);
	boolean responderExpedienteArchivadoOfinalizado(Expediente formExpediente);
	ArchivoMovimiento infoMovimientoArchivoRespuesta(Long idexpediente,Long idoficina,String nombrearchivo);
	List<HojaRuta> infoHojaRuta(String anio,String codigoExpediente);
	Expediente infoExpedienteCodigo(String anio, String codigoExpediente); 
}
