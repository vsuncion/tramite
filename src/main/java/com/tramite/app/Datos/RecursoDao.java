package com.tramite.app.Datos;

 
import java.util.List;

import com.tramite.app.Entidades.EstadoDocumento;
import com.tramite.app.Entidades.Requisitos;
import com.tramite.app.Entidades.TipoDocumentos;
import com.tramite.app.Entidades.Usuarios;

public interface RecursoDao {
	
	List<TipoDocumentos> listarTipoDocuemnto();
	public String numeroExpediente();
	 Usuarios infoUsuario(String vcorreo);
	 EstadoDocumento infoEstadoDocumento(Long idEstadoDocumento);
	 List<Requisitos> cbRequisitos(Long idTupac);
}
