package com.tramite.app.Datos.Impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tramite.app.Datos.RecursoDao;
import com.tramite.app.Entidades.Oficinas;
import com.tramite.app.Entidades.TipoDocumentos;
import com.tramite.app.Entidades.Usuarios;
import com.tramite.app.utilitarios.Constantes;

@Repository
public class RecursoDaoImpl implements RecursoDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<TipoDocumentos> listarTipoDocuemnto() {
		StringBuffer sql = new StringBuffer();
		List<TipoDocumentos> lista = new ArrayList<TipoDocumentos>();
		try {
			sql.append(
			  "SELECT "+
			  "  NIDTIPODOCUMENTOPK, \n"+
			  "  VNOMBRE,            \n"+
			  "  VALIAS,             \n"+
			  "  VDESCRIPCION        \n"+
			  "  FROM "+Constantes.tablaTipoDocumentos);
			lista = namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(TipoDocumentos.class));
		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}
	
	
	@Override
	public String numeroExpediente() {
		 
		 String sql;
		 String letraExpediente="E";
		 int numeroExpediente=0;
		 String numeroExpedienteLetra;
		 String numeroFinalExpediente="0";
		try {
			sql="SELECT COUNT(1)+1 FROM "+Constantes.tablaExpediente;
			numeroExpediente=jdbcTemplate.queryForObject(sql,  Integer.class);
			numeroExpedienteLetra = String.valueOf(numeroExpediente);
		
			switch(numeroExpedienteLetra.length()) {
			  case 1:
				  numeroFinalExpediente = letraExpediente+"-00000"+numeroExpedienteLetra;
			   break;
			   
			  case 2:
				  numeroFinalExpediente = letraExpediente+"-0000"+numeroExpedienteLetra;
			   break;
			   
			  case 3:
				  numeroFinalExpediente = letraExpediente+"-000"+numeroExpedienteLetra;
			   break;
			   
			  case 4:
				  numeroFinalExpediente = letraExpediente+"-00"+numeroExpedienteLetra;
			   break;
			   
			  case 5:
				  numeroFinalExpediente = letraExpediente+"-0"+numeroExpedienteLetra;
			   break;
			   
			  default :
				  numeroFinalExpediente = letraExpediente+"-"+numeroExpedienteLetra;  
			}
			
		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
		}
		return numeroFinalExpediente;
	}


	@Override
	public Usuarios infoUsuario(String vcorreo) {
		StringBuffer sql = new StringBuffer();
		Usuarios usuarios = new Usuarios();
		try {
			sql.append(
				"  SELECT \n"+
			    "   NTRABAJADORFK, \n"+
			    "   NOFICINAFK, \n"+
			    "   VUSUARIO \n"+
			    "  FROM "+Constantes.tablaUsuario+" \n"+
			    " WHERE VUSUARIO= :P_VUSUARIO");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_VUSUARIO", vcorreo);
			usuarios = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(Usuarios.class));
				     
		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
		}
		return usuarios;
	}
 
}
