package com.tramite.app.Servicios;


import java.util.List;

import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.PrePersona;
import com.tramite.app.Entidades.PreRequisitoTupa;

public interface PrincipalServicio {
	
	Persona buscarPersona(int tipoPersona,String vnumero);
	MensajeRespuesta guardarPrePersona(PrePersona prePersona);
	MensajeRespuesta activarRegistroPrePersona(String codigoActivacion);
	MensajeRespuesta confirmacionCodigoActivacion(String codigoActivacion); 
	Persona busquedaSolicitante(Expediente expediente);
	boolean guardarExpedienteSimple(Expediente expediente);
	Long guardarPreTupac(Expediente expediente);
	Expediente preTupacExpediente(Long idprexpediente);
	MensajeRespuesta guardarPreRequisito(PreRequisitoTupa preRequisitoTupa);
	List<PreRequisitoTupa> listaPreRequisitos(Long idprexpediente);
}
