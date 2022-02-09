package com.tramite.app.Servicios;


import java.util.List;

import com.tramite.app.Entidades.*;

public interface PrincipalServicio {
	
	Persona buscarPersona(PrePersona prePersona);
	MensajeRespuesta guardarPrePersona(PrePersona prePersona);
	MensajeRespuesta activarRegistroPrePersona(String codigoActivacion);
	MensajeRespuesta confirmacionCodigoActivacion(String codigoActivacion); 
	Persona busquedaSolicitante(Expediente expediente);
	boolean guardarExpedienteSimple(Expediente expediente);
	Long guardarPreTupac(Expediente expediente);
	Expediente preTupacExpediente(Long idprexpediente);
	MensajeRespuesta guardarPreRequisito(PreRequisitoTupa preRequisitoTupa);
	List<PreRequisitoTupa> listaPreRequisitos(Long idprexpediente);
	List<Seleccion> listasTupacRequisitos();
	PreRequisitoTupa infoPreRequisitoTupa(PreRequisitoTupa preRequisitoTupa);
	void guardarDetalleArchivosExpedienteTupa(Expediente formExpediente);
	void eliminarArchivoRequerimeinto(Long idprexpediente,Long idrequisito);
	List<RequisitosTupac> listaRequerimientosTupac(Long idtupac);
	PersonaJuridica buscarPersonaJuridicaDuplicada(PrePersona prePersona);
	PrePersona buscarPrepersona(PrePersona prePersona);
	PrePersona buscarPrepersonaDuplicada(PrePersona prePersona);
}
