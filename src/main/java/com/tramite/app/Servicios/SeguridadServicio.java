package com.tramite.app.Servicios;

import com.tramite.app.Entidades.Usuarios;

import java.util.List;

import com.tramite.app.Entidades.UsuarioPerfil;

public interface SeguridadServicio {
	
   Usuarios  InformacionUsuarios(String name);
   List<UsuarioPerfil> perfilesUsuario(String name);
}
