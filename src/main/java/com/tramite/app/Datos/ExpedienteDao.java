package com.tramite.app.Datos;

import java.util.List;

import com.tramite.app.Entidades.Bandeja;

public interface ExpedienteDao {

	List<Bandeja> listarBandeja(Long oficina,Long estadodocumento);
}
