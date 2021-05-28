package com.tramite.app.Datos;

import java.util.List;

import com.tramite.app.Entidades.UsuarioPerfil;
import com.tramite.app.Entidades.Usuarios;

public interface SeguridadDao {
	
	Usuarios  InformacionUsuarios(String name);
	List<UsuarioPerfil> perfilesUsuario(String name);

}
