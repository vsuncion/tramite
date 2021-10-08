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
import com.tramite.app.Entidades.Cargo;
import com.tramite.app.Entidades.Correlativo;
import com.tramite.app.Entidades.EstadoDocumento;
import com.tramite.app.Entidades.Persona;
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
		
			sql.append(
			  "SELECT "+
			  "  NIDTIPODOCUMENTOPK, \n"+
			  "  VNOMBRE,            \n"+
			  "  VALIAS,             \n"+
			  "  VDESCRIPCION        \n"+
			  "  FROM "+Constantes.tablaTipoDocumentos);
			lista = namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(TipoDocumentos.class));
		 
		return lista;
	}
	
	
	@Override
	public String numeroExpediente(Long idoficina) {
		Calendar fecha = Calendar.getInstance();
		 StringBuffer sql = new StringBuffer();
		 String letraExpediente="E-"; 
		 String numeroExpedienteLetra;
		 String numeroFinalExpediente="";
		 String siglaOficina="";
		 Correlativo infoCorrelativo = new Correlativo();
		 int anio = fecha.get(Calendar.YEAR);
		
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
 
		return numeroFinalExpediente;
	}


	@Override
	public Usuarios infoUsuario(String vcorreo) {
		StringBuffer sql = new StringBuffer();
		Usuarios usuarios = new Usuarios();
		
			sql.append(
				"  SELECT \n"+
				"    T1.NIDUSUARIOPK, \n"+
			    "    T1.NTRABAJADORFK, \n"+
			    "    T1.NOFICINAFK, \n"+
			    "    T1.VUSUARIO AS username, \n"+
			    "    T2.NIDPERSONAFK, \n"+
			    "    T3.VNOMBRE, \n"+
			    "    T3.VAPEPATERNO, \n"+
			    "    T3.VAPEMATERNO, \n"+
			    "    T4.VNOMBRE AS VOFICINA, \n"+
			    "    T5.VNOMBRECARGO AS VCARGO \n"+
			    "  FROM "+Constantes.tablaUsuario+" T1 \n"+
			    "  INNER JOIN  "+Constantes.tablaTrabajadores+" T2 ON T1.NTRABAJADORFK=T2.NIDTRABAJADORPK \n"+
			    "  INNER JOIN  "+Constantes.tablaPersona+"      T3 ON T2.NIDPERSONAFK=T3.NIDPERSONAPK \n"+
			    "  INNER JOIN  "+Constantes.tablaOficinas+"     T4 ON T1.NOFICINAFK=T4.NIDOFICINAPK \n"+
			    "  LEFT JOIN  "+Constantes.tablaCargo+"         T5 ON T2.NCARGOFK=T5.NCARGOPK \n"+
			    " WHERE T1.VUSUARIO= :P_VUSUARIO");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_VUSUARIO", vcorreo);
			usuarios = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(Usuarios.class));
 
		return usuarios;
	}


	@Override
	public EstadoDocumento infoEstadoDocumento(Long idEstadoDocumento) {
		StringBuffer sql = new StringBuffer();
		EstadoDocumento info = new EstadoDocumento();
		
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
		 
		return info;
	}


	@Override
	public List<Requisitos> cbRequisitos(Long idTupac) {
		List<Requisitos> lista = new ArrayList<Requisitos>();
		StringBuffer sql = new StringBuffer();
		
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
	 
		return lista;
	}


	@Override
	public List<EstadoDocumento> listaEstadoDocumentos() {
		StringBuffer sql = new StringBuffer();
		List<EstadoDocumento>  lista = new ArrayList<EstadoDocumento>();
		
			sql.append(
			   "SELECT \n"+ 
			   "  IDESTADOCUMENTOPK, \n"+
			   "  VNOMBRE \n"+
			   " FROM "+Constantes.tablaEstadoDocumento+" \n"+
			   " ESTADO_DOCUMENTO ORDER BY VNOMBRE");
			lista = namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(EstadoDocumento.class));
 
		return lista;
	}


	@Override
	public List<Cargo> cbCargos() {
		StringBuffer sql = new StringBuffer();
		List<Cargo> lista = new ArrayList<Cargo>(); 
		
			sql.append(
				"SELECT \n"+
				" NCARGOPK, \n"+
				" VNOMBRECARGO \n"+
				"FROM "+Constantes.tablaCargo+" \n"+
				"ORDER BY VNOMBRECARGO ASC");
			lista = namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(Cargo.class));
 
		return lista;
	}


	@Override
	public List<Persona> listaUsuariosOficina(Long idOficina) {
		StringBuffer sql = new StringBuffer();
		List<Persona> lista = new ArrayList<Persona>();
		
			sql.append(
				"SELECT \n"+
			    " T1.NIDUSUARIOPK AS NUSUREGISTRA, \n" +
			    " CONCAT(T4.VAPEPATERNO,' ',T4.VAPEMATERNO,',',T4.VNOMBRE) AS NOMBRE_COMPLETO \n" +
			    "FROM " +Constantes.tablaUsuario+" T1 \n" +
			    " INNER JOIN "+Constantes.tablaUsuarioPerfil+" T2 ON T1.NIDUSUARIOPK=T2.NUSUARIOFK AND T1.NESTADO=1 AND T2.NESTADO=1 \n" +
			    " INNER JOIN "+Constantes.tablaTrabajadores+"  T3 ON T1.NTRABAJADORFK=T3.NIDTRABAJADORPK AND T3.NESTADO=1 \n" +
			    " INNER JOIN "+Constantes.tablaPersona+"       T4 ON T3.NIDPERSONAFK=T4.NIDPERSONAPK \n" +
			    "WHERE T1.NOFICINAFK= :P_NOFICINAFK AND T2.NPERFILFK!=1");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NOFICINAFK", idOficina);
			lista=namedParameterJdbcTemplate.query(sql.toString(),parametros,BeanPropertyRowMapper.newInstance(Persona.class));
 
		return lista;
	}
 
}
