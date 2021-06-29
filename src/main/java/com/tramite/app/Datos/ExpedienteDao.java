package com.tramite.app.Datos;

import java.util.List;

import com.tramite.app.Entidades.ArchivoMovimiento;
import com.tramite.app.Entidades.ArchivoTupac;
import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.HojaRuta;
import com.tramite.app.Entidades.MovimientoExpediente;
import com.tramite.app.Entidades.ReporteExpediente;
import com.tramite.app.Entidades.Usuarios; 

public interface ExpedienteDao {

	List<Bandeja> listarBandeja(Long oficina,Long estadodocumento);
	boolean recibirExpediente(Long idMovimiento,Long idOficina,Long idExpediente);
	Expediente infoExpediente(Long idexpediente);
	MovimientoExpediente infoMovimiento(Long idexpediente,Long idmovimiento);
	boolean responderExpediente(Expediente formexpediente);
	boolean responderExpedienteArchivadoOfinalizado(Expediente formExpediente);
	ArchivoMovimiento infoMovimientoArchivoRespuesta(Long idexpediente,Long idoficina,String nombrearchivo);
	List<HojaRuta> infoHojaRuta(String anio,String codigoExpediente);
	Expediente infoExpedienteCodigo(String anio, String codigoExpediente); 
	List<HojaRuta> infoHojaRutaIdExpediente(Long idExpediente);
	Expediente infoExpedienteId(Long idExpediente); 
	MovimientoExpediente infoMovimientoIdexpediente(Long idMovimiento);
	List<ArchivoTupac> listarArchivosTupa(Long idexpediente);
	ArchivoTupac infoArchivoTupa(Long idexpediente,Long idarchivorequisito);
	boolean guardarExpedienteSimpleInterno(Expediente expediente);
	boolean actualizarClave(Usuarios formUsuario);
	List<ReporteExpediente> listaExpedientesPorEstadoDocuemnto(Long idEstadoDocumento);
	List<ReporteExpediente> listaExpedientesPorOficina(Expediente formexpediente);
	List<Expediente>  listarExpedientesInterno(Expediente formexpediente);
	Expediente infoExpedienteCodigoInterno(String anio, String codigoExpediente);
}
