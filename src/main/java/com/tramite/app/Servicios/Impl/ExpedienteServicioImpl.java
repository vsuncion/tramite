package com.tramite.app.Servicios.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tramite.app.Datos.ExpedienteDao;
import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Servicios.ExpedienteServicio;

@Service
public class ExpedienteServicioImpl implements ExpedienteServicio {
	
	@Autowired
	private ExpedienteDao expedienteDao;
	
	@Override
	public List<Bandeja> listarBandeja(Long oficina, Long estadodocumento) { 
		return expedienteDao.listarBandeja(oficina, estadodocumento);
	}

}
