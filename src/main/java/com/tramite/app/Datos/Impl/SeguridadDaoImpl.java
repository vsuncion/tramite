package com.tramite.app.Datos.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper; 
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tramite.app.Datos.SeguridadDao;
import com.tramite.app.Entidades.UsuarioPerfil;
import com.tramite.app.Entidades.Usuarios;
import com.tramite.app.utilitarios.Constantes;

@Repository
public class SeguridadDaoImpl implements SeguridadDao {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	/*
	@Autowired
	private JdbcTemplate jdbcTemplate;*/
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Usuarios InformacionUsuarios(String name) {
		Usuarios usuario = new Usuarios();
		StringBuilder sql = new StringBuilder();
		try {
			sql.append(
			   "SELECT \n"+
			   "  VUSUARIO AS username,     \n"+
			   "  VCLAVE   AS password, \n"+
			   "  NESTADO  AS enabled   \n"+
			   " FROM "+Constantes.tablaUsuario+ " \n"+
			   " WHERE NESTADO= :P_NESTADO AND VUSUARIO= :P_VUSUARIO");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_VUSUARIO", name);
			parametros.addValue("P_NESTADO", Constantes.estadoActivado);
			usuario = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(Usuarios.class));
		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
		}
		return usuario;
	}

	@Override
	public List<UsuarioPerfil> perfilesUsuario(String name) {
		List<UsuarioPerfil>  lista = new ArrayList<UsuarioPerfil>();
		StringBuilder sql = new StringBuilder();
		try {
			sql.append(
			  " SELECT \n"+
			  "  T2.VUSUARIO AS username,T3.VNOMBRE AS rol \n"+
			  " FROM "+Constantes.tablaUsuarioPerfil+"  T1 \n"+
			  "  INNER JOIN "+Constantes.tablaUsuario+" T2 ON T1.NUSUARIOFK=T2.NIDUSUARIOPK \n"+
			  "  INNER JOIN "+Constantes.tablaPerfil+"  T3 ON T1.NPERFILFK=T3.NIDPERFILPK \n"+
			  " WHERE T2.VUSUARIO= :P_VUSUARIO");
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_VUSUARIO", name);
			 lista = namedParameterJdbcTemplate.query(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(UsuarioPerfil.class));
			  
		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}

}
