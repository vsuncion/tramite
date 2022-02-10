package com.tramite.app.Servicios.Impl;

import java.util.List;

import org.apache.log4j.Logger;
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

	
	private static final Logger logger = Logger.getLogger(SeguridadServicioImpl.class);
	
	
	@Override
	@Transactional(readOnly = true)
	public Usuarios InformacionUsuarios(String name) { 
		Usuarios usuarios = new Usuarios();
		try {
			usuarios = seguridadDao.InformacionUsuarios(name);
		} catch (Exception e) {
		// TODO: handle exception
			logger.error("ERROR =" +this.getClass().getName()+". ==Causa==" + e.getCause()+" =Mensage="+e.getMessage());
		}
		return usuarios;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioPerfil> perfilesUsuario(String name) { 
		return seguridadDao.perfilesUsuario(name);
	}

}
