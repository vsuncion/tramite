package com.tramite.app.Servicios;

import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.PrePersona;

public interface PrincipalServicio {
	
	Persona buscarPersona(int tipoPersona,String vnumero);
	MensajeRespuesta guardarPrePersona(PrePersona prePersona);
	MensajeRespuesta activarRegistroPrePersona(String codigoActivacion);
	MensajeRespuesta confirmacionCodigoActivacion(String codigoActivacion);
}
