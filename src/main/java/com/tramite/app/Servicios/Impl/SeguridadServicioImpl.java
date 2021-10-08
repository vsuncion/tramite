package com.tramite.app.Servicios.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tramite.app.Datos.SeguridadDao;
import com.tramite.app.Entidades.UsuarioPerfil;
import com.tramite.app.Entidades.Usuarios;
import com.tramite.app.Servicios.SeguridadServicio;

@Service
public class SeguridadServicioImpl implements SeguridadServicio {
	
	@Autowired
	private SeguridadDao seguridadDao;

	@Override
	@Transactional(readOnly = true)
	public Usuarios InformacionUsuarios(String name) { 
		return seguridadDao.InformacionUsuarios(name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioPerfil> perfilesUsuario(String name) { 
		return seguridadDao.perfilesUsuario(name);
	}

}
