package com.tramite.app.Datos.Impl;

import java.util.ArrayList;
import java.util.Calendar;
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
import com.tramite.app.Entidades.Correlativo;
import com.tramite.app.Entidades.EstadoDocumento; 
import com.tramite.app.Entidades.Requisitos;
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
			logger.error("ERROR : RecursoDaoImpl listarTipoDocuemnto " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}
	
	
	@Override
	public String numeroExpediente(Long idoficina) {
		Calendar fecha = Calendar.getInstance();
		 StringBuffer sql = new StringBuffer();
		 String letraExpediente="E"; 
		 String numeroExpedienteLetra;
		 String numeroFinalExpediente="";
		 String siglaOficina="";
		 Correlativo infoCorrelativo = new Correlativo();
		 int anio = fecha.get(Calendar.YEAR);
		try {
			sql.append(
			   "SELECT \n"+
			   " T1.NCORRELATIVOPK,\n"+
			   " CONVERT(NVARCHAR,T1.NVALOR_ACTUAL+1) AS VVALOR_ACTUAL, \n"+
			   " T2.VSIGLADOCUMENTO \n"+
			   "FROM "+Constantes.tablaCorrelativos+" T1 \n"+
			   "INNER JOIN "+Constantes.tablaOficinas+" T2 ON T1.NOFICINAFK=T2.NIDOFICINAPK AND T1.NESTADO = :P_NESTADO  \n"+
			   "WHERE T1.NOFICINAFK= :P_NOFICINAFK ");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NOFICINAFK", idoficina);
			parametros.addValue("P_NESTADO", Constantes.estadoActivado);
			infoCorrelativo = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Correlativo.class));
			numeroExpedienteLetra = infoCorrelativo.getVVALOR_ACTUAL();
			siglaOficina = infoCorrelativo.getVSIGLADOCUMENTO();
			switch(numeroExpedienteLetra.length()) {
			 case 1:
				 numeroFinalExpediente = letraExpediente+"00000"+numeroExpedienteLetra+"-"+anio+"-"+siglaOficina;
				 break;
			 case 2:
				 numeroFinalExpediente = letraExpediente+"0000"+numeroExpedienteLetra+"-"+anio+"-"+siglaOficina;
				 break;
			 case 3:
				 numeroFinalExpediente = letraExpediente+"000"+numeroExpedienteLetra+"-"+anio+"-"+siglaOficina;
				 break;
			 case 4:
				 numeroFinalExpediente = letraExpediente+"00"+numeroExpedienteLetra+"-"+anio+"-"+siglaOficina;
				 break;
			 case 5:
				 numeroFinalExpediente = letraExpediente+"0"+numeroExpedienteLetra+"-"+anio+"-"+siglaOficina;
				 break;
			  default:
				  numeroFinalExpediente = letraExpediente+numeroExpedienteLetra+"-"+anio+"-"+siglaOficina;
			}
			
			//ACTUALIZAMOS EL CORRELATIVO
			StringBuffer sql2 = new StringBuffer();
			sql2.append(
			  "UPDATE "+Constantes.tablaCorrelativos+ " SET \n"+
			  " NVALOR_ACTUAL = :P_NVALOR_ACTUAL \n"+
			  "WHERE NCORRELATIVOPK= :P_NCORRELATIVOPK AND NOFICINAFK= :P_NOFICINAFK");
			MapSqlParameterSource parametros2 = new MapSqlParameterSource();
			parametros2.addValue("P_NVALOR_ACTUAL", Long.parseLong(numeroExpedienteLetra));
			parametros2.addValue("P_NCORRELATIVOPK", infoCorrelativo.getNCORRELATIVOPK());
			parametros2.addValue("P_NOFICINAFK", idoficina);
			namedParameterJdbcTemplate.update(sql2.toString(), parametros2);
			
			/*
			sql="SELECT COUNT(1)+1 FROM "+Constantes.tablaExpediente;
			numeroExpediente=jdbcTemplate.queryForObject(sql,  Integer.class);
			numeroExpedienteLetra = String.valueOf(numeroExpediente);
		
			switch(numeroExpedienteLetra.length()) {
			  case 1:
				  numeroFinalExpediente = letraExpediente+"-"+anio+"-00000"+numeroExpedienteLetra;
			   break;
			   
			  case 2:
				  numeroFinalExpediente = letraExpediente+"-"+anio+"-0000"+numeroExpedienteLetra;
			   break;
			   
			  case 3:
				  numeroFinalExpediente = letraExpediente+"-"+anio+"-000"+numeroExpedienteLetra;
			   break;
			   
			  case 4:
				  numeroFinalExpediente = letraExpediente+"-"+anio+"-00"+numeroExpedienteLetra;
			   break;
			   
			  case 5:
				  numeroFinalExpediente = letraExpediente+"-"+anio+"-0"+numeroExpedienteLetra;
			   break;
			   
			  default :
				  numeroFinalExpediente = letraExpediente+"-"+anio+"-"+numeroExpedienteLetra;  
			}*/
			
		} catch (Exception e) {
			logger.error("ERROR : RecursoDaoImpl numeroExpediente " + e.getMessage() + "---" + e.getClass());
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
			    "    T1.NTRABAJADORFK, \n"+
			    "    T1.NOFICINAFK, \n"+
			    "    T1.VUSUARIO, \n"+
			    "    T2.NIDPERSONAFK, \n"+
			    "    T3.VNOMBRE, \n"+
			    "    T3.VAPEPATERNO, \n"+
			    "    T3.VAPEMATERNO, \n"+
			    "    T4.VNOMBRE AS VOFICINA \n"+
			    "  FROM "+Constantes.tablaUsuario+" T1 \n"+
			    "  INNER JOIN  "+Constantes.tablaTrabajadores+" T2 ON T1.NTRABAJADORFK=T2.NIDTRABAJADORPK \n"+
			    "  INNER JOIN  "+Constantes.tablaPersona+"      T3 ON T2.NIDPERSONAFK=T3.NIDPERSONAPK \n"+
			    "  INNER JOIN  "+Constantes.tablaOficinas+"     T4 ON T1.NOFICINAFK=T4.NIDOFICINAPK \n"+
			    " WHERE T1.VUSUARIO= :P_VUSUARIO");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_VUSUARIO", vcorreo);
			usuarios = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(Usuarios.class));
				     
		} catch (Exception e) {
			logger.error("ERROR : RecursoDaoImpl infoUsuario " + e.getMessage() + "---" + e.getClass());
		}
		return usuarios;
	}


	@Override
	public EstadoDocumento infoEstadoDocumento(Long idEstadoDocumento) {
		StringBuffer sql = new StringBuffer();
		EstadoDocumento info = new EstadoDocumento();
		try {
			sql.append(
					"SELECT "+
					"  ROW_NUMBER() OVER (ORDER BY IDESTADOCUMENTOPK) AS NITEM ,  \n"+
				    "  IDESTADOCUMENTOPK,"+
				    "  VNOMBRE,          "+
				    "  VDESCRIPCION,     "+
				    "  NESTADO,           "+
				    "  DFECREGISTRO "+
				    " FROM "+Constantes.tablaEstadoDocumento+" \n"+
				    "WHERE IDESTADOCUMENTOPK= :P_IDESTADOCUMENTOPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_IDESTADOCUMENTOPK", idEstadoDocumento);
			info = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(EstadoDocumento.class));
		} catch (Exception e) {
			logger.error("ERROR : RecursoDaoImpl infoEstadoDocumento " + e.getMessage() + "---" + e.getClass());
		}
		return info;
	}


	@Override
	public List<Requisitos> cbRequisitos(Long idTupac) {
		List<Requisitos> lista = new ArrayList<Requisitos>();
		StringBuffer sql = new StringBuffer();
		try {
			sql.append(
				"SELECT \n"+
				"  T2.REQUISITOSTUPACPK, \n"+
				"  T2.VNOMBRE\n"+
				" FROM "+Constantes.tablaRequisitosTupac+" T1 \n"+
				"  INNER JOIN "+Constantes.tablaRequisitos+" T2 ON T1.REQUISITOSFK=T2.REQUISITOSTUPACPK   \n"+
				" WHERE TUPACFK= :P_TUPACFK  AND T1.NESTADO= :P_NESTADO");
			  MapSqlParameterSource parametros = new MapSqlParameterSource();
			  parametros.addValue("P_TUPACFK", idTupac);
			  parametros.addValue("P_NESTADO", Constantes.estadoActivado);
			  lista = namedParameterJdbcTemplate.query(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(Requisitos.class));
			  
		} catch (Exception e) {
			logger.error("ERROR : RecursoDaoImpl cbRequisitos " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}


	@Override
	public List<EstadoDocumento> listaEstadoDocumentos() {
		StringBuffer sql = new StringBuffer();
		List<EstadoDocumento>  lista = new ArrayList<EstadoDocumento>();
		try {
			sql.append(
			   "SELECT \n"+ 
			   "  IDESTADOCUMENTOPK, \n"+
			   "  VNOMBRE \n"+
			   " FROM "+Constantes.tablaEstadoDocumento+" \n"+
			   " ESTADO_DOCUMENTO ORDER BY VNOMBRE");
			lista = namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(EstadoDocumento.class));
		} catch (Exception e) {
			logger.error("ERROR : RecursoDaoImpl listaEstadoDocumentos " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}
 
}
