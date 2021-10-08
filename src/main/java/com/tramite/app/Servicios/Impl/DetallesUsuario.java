package com.tramite.app.Servicios.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tramite.app.Datos.SeguridadDao;
import com.tramite.app.Entidades.UsuarioPerfil;
import com.tramite.app.Entidades.Usuarios;

@Service("detallesUsuario")
public class DetallesUsuario implements UserDetailsService {
	
	@Autowired
	private SeguridadDao seguridadDao;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuarios usuario = new Usuarios();
		List<GrantedAuthority> perfiles = new ArrayList<GrantedAuthority>();
		List<UsuarioPerfil> listaPerfiles = new ArrayList<UsuarioPerfil>();
		
		try {
			
			usuario = seguridadDao.InformacionUsuarios(username);
			
			if(usuario == null) {
				logger.info("ERROR NO EXISTE EL SUSUARIO");
				throw new UsernameNotFoundException("Usuario no existe");
			}
			
			
			
			listaPerfiles = seguridadDao.perfilesUsuario(username);
			
			if(listaPerfiles.isEmpty()) {
				logger.info("NO TIENE ROLES");
				throw new UsernameNotFoundException("roles no existe");
			}
			
			for (UsuarioPerfil i : listaPerfiles) {
				perfiles.add(new SimpleGrantedAuthority(i.getRol()));
				logger.info("==="+i.getRol());
			}
			
		} catch (Exception e) {
			logger.error("ERROR : DetallesUsuario loadUserByUsername " + e.getMessage() + "---" + e.getClass());
		}
		

		
		return new User(usuario.getUsername(), usuario.getPassword(), perfiles);
	}

}
