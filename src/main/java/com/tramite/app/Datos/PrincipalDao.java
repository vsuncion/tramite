package com.tramite.app.Datos;

 
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.PrePersona;

public interface PrincipalDao {
	
	Persona buscarPersona(int tipoPersona,String vnumero);
	boolean guardarPrePersona(PrePersona prePersona);
	boolean activarRegistroPrePersona(String codigoActivacion);
	boolean confirmacionCodigoActivacion(String codigoActivacion);
	Persona busquedaSolicitante(Expediente expediente);
	boolean guardarExpedienteSimple(Expediente expediente);
}
