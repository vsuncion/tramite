package com.tramite.app.Servicios;

import java.util.List;

import com.tramite.app.Entidades.EstadoDocumento;
import com.tramite.app.Entidades.Oficinas;
import com.tramite.app.Entidades.Requisitos;
import com.tramite.app.Entidades.Seleccion;
import com.tramite.app.Entidades.Usuarios; 

public interface RecursoServicio {

	List<Seleccion> cbTipoDocumentoPersona();

	List<Seleccion> cbTipoDocuemnto();
	List<Seleccion> cbTupa();
	public String numeroExpediente(Long idoficina);
	 Usuarios infoUsuario(String vcorreo);
	 List<Seleccion> cbOficinasAtender(Long idoficiActual);
	 List<Seleccion> cbAccionesAtender();
	 Oficinas infoOficina (Long idoficina);
	 EstadoDocumento infoEstadoDocumento(Long idEstadoDocumento);
	 List<Seleccion> cbRequisitos(Long idTupac);
	 List<Seleccion> listaEstadoDocumentos();
	 List<Seleccion>  cbOficinasReportes();
}
