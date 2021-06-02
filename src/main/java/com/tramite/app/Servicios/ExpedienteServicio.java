package com.tramite.app.Servicios;

import java.util.List;

import com.tramite.app.Entidades.Bandeja;

public interface ExpedienteServicio {

	List<Bandeja> listarBandeja(Long oficina,Long estadodocumento);
}
