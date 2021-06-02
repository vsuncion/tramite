package com.tramite.app.Datos;

 
import java.util.List;

import com.tramite.app.Entidades.TipoDocumentos;

public interface RecursoDao {
	
	List<TipoDocumentos> listarTipoDocuemnto();
	public String numeroExpediente();
}
