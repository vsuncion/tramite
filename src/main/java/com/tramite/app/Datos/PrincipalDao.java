package com.tramite.app.Datos;

 
import java.util.List;

import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.PrePersona;
import com.tramite.app.Entidades.PreRequisitoTupa;

public interface PrincipalDao {
	
	Persona buscarPersona(int tipoPersona,String vnumero);
	boolean guardarPrePersona(PrePersona prePersona);
	boolean activarRegistroPrePersona(String codigoActivacion);
	boolean confirmacionCodigoActivacion(String codigoActivacion);
	Persona busquedaSolicitante(Expediente expediente);
	boolean guardarExpedienteSimple(Expediente expediente);
	Long guardarPreTupac(Expediente expediente);
	Expediente preTupacExpediente(Long idprexpediente);
	boolean guardarPreRequisito(PreRequisitoTupa preRequisitoTupa);
	List<PreRequisitoTupa> listaPreRequisitos(Long idprexpediente);
}
