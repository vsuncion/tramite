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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tramite.app.Datos.ExpedienteDao;
import com.tramite.app.Entidades.ArchivoMovimiento;
import com.tramite.app.Entidades.ArchivoTupac;
import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.HojaRuta;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.MovimientoExpediente;
import com.tramite.app.Entidades.ReporteExpediente;
import com.tramite.app.Entidades.Usuarios;
import com.tramite.app.utilitarios.Constantes;
import com.tramite.app.utilitarios.Fechas;

@Repository
public class ExpedienteDaoImpl implements ExpedienteDao {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Bandeja> listarBandeja(Long oficina, Long estadodocumento) {
		StringBuffer sql = new StringBuffer();
		List<Bandeja> lista = new ArrayList<Bandeja>();
		try {
			sql.append(
			 "SELECT  \n"+
			 " 	 ROW_NUMBER() OVER ( ORDER BY T1.NIDMOVIMIENTOPK DESC)  AS NITEM,  \n"+
			 " 	 T1.NIDMOVIMIENTOPK, \n"+
			 " 	 CONVERT(varchar,DFECHAOFICINA,22) AS VFECHA_OFICINA, \n"+
			 " 	 CASE T2.NTIPOPERSONA WHEN 1 THEN CONCAT(T3.VAPEPATERNO,' ',T3.VAPEMATERNO,','+T3.VNOMBRE)  \n"+
			 " 	 WHEN 2 THEN T5.VRAZONSOCIAL  END   VREMITENTE, \n"+
			 " 	 CONCAT(SUBSTRING(T2.VASUNTO,0,15),'...') AS VASUNTO, \n"+
			 " 	 T7.VNOMBRE AS VOFICINA_ORIGEN, \n"+
			 " 	 T8.VNOMBRE AS VOFICINA_DESTINO, \n"+
			 " 	 T6.VNOMBRE AS VESTADO_DOC, \n"+
			 " 	 T1.NIDEXPEDIENTEFK, \n"+
			 " 	 T2.VCODIGO_EXPEDIENTE, \n"+
			 " 	 T2.NIDEXPEDIENTEPK \n"+
			 " FROM "+Constantes.tablaMovimiento+"              T1 \n"+
			 " 	 INNER JOIN "+Constantes.tablaExpediente+"      T2 ON T1.NIDEXPEDIENTEFK=T2.NIDEXPEDIENTEPK \n"+
			 " 	 INNER JOIN "+Constantes.tablaPersona+"         T3 ON T2.PERSONAFK=T3.NIDPERSONAPK \n"+
			 " 	 LEFT JOIN  "+Constantes.tablaPersonaNatural+"  T4 ON T3.NIDPERSONAPK=T4.NIDPERSONAFK \n"+
			 " 	 LEFT JOIN  "+Constantes.tablaPersonaJuridica+" T5 ON T3.NIDPERSONAPK=T5.NIDPERSONAFK \n"+
			 " 	 INNER JOIN "+Constantes.tablaEstadoDocumento+" T6 ON T1.NESTADODOCUMENTOFK=T6.IDESTADOCUMENTOPK \n"+
			 " 	 INNER JOIN "+Constantes.tablaOficinas+"        T7 ON T1.OFICINA_ORIGENFK=T7.NIDOFICINAPK \n"+
			 " 	 LEFT JOIN  "+Constantes.tablaOficinas+"        T8 ON T1.OFICINA_DESTINOFK=T8.NIDOFICINAPK \n");
			
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			if(estadodocumento==1 || estadodocumento==2) {
				sql.append(" WHERE T1.OFICINA_DESTINOFK= :P_OFICINA_DESTINOFK  AND T1.NESTADOREGISTRO= :P_NESTADOREGISTRO AND T1.NELIMINADO= :P_NELIMINADO \n");
				parametros.addValue("P_NESTADOREGISTRO",Constantes.estadoActivado);
				
			}else {
				sql.append(" WHERE T1.OFICINA_ORIGENFK= :P_OFICINA_DESTINOFK  AND T1.NESTADOREGISTRO= :P_NESTADOREGISTRO AND T1.NELIMINADO= :P_NELIMINADO \n");
				parametros.addValue("P_NESTADOREGISTRO",Constantes.estadoDesactivado);
			}
			
			sql.append(" AND T1.NESTADODOCUMENTOFK= :P_NESTADODOCUMENTOFK  ORDER BY T1.NIDMOVIMIENTOPK DESC");
			
			
			parametros.addValue("P_OFICINA_DESTINOFK",oficina);
			parametros.addValue("P_NESTADODOCUMENTOFK", estadodocumento);
			
			parametros.addValue("P_NELIMINADO", Constantes.estadoDesactivado);
			lista = namedParameterJdbcTemplate.query(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Bandeja.class));
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl listarBandeja " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}

	@Override
	public boolean recibirExpediente(Long idMovimiento,Long idOficina,Long idExpediente) {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		StringBuffer sql3 = new StringBuffer();
		boolean respuesta = false;
		
		try {
			
			//INSERTAMOS EL NUEVO MANTENIMIENTO 
			sql.append(
				"INSERT INTO "+Constantes.tablaMovimiento+" ( \n"+
			    "	NIDEXPEDIENTEFK, \n"+
			    "	NESTADODOCUMENTOFK, \n"+
			    "	OFICINA_ORIGENFK, \n"+
			    "	OFICINA_DESTINOFK, \n"+
			    "	DFECHAOFICINA, \n"+
			    "	DFECHARECEPCION, \n"+
			    "	VOBSERVACION ) \n"+
			    "SELECT  \n"+
			    "	NIDEXPEDIENTEFK, \n"+
			    "	:P_NESTADODOCUMENTOFK, \n"+
			    "	OFICINA_ORIGENFK, \n"+
			    "	OFICINA_DESTINOFK, \n"+
			    "	DFECHAOFICINA, \n"+
			    "	:P_DFECHARECEPCION, \n"+
			    "	VOBSERVACION \n"+
			    " FROM "+Constantes.tablaMovimiento+" \n"+
			    " WHERE NIDMOVIMIENTOPK= :P_NIDMOVIMIENTOPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NESTADODOCUMENTOFK", Constantes.ESTADO_DOCUMENTO_RECIBIDO);
			parametros.addValue("P_DFECHARECEPCION", Fechas.fechaActual());
			parametros.addValue("P_NIDMOVIMIENTOPK", idMovimiento);
			namedParameterJdbcTemplate.update(sql.toString(), parametros);	 
			
			//APAGAMOS EL QUE ESTA EN ESTADO PENDIENTE
			sql2.append(
				"UPDATE "+Constantes.tablaMovimiento+"  \n"+
			    " SET  NESTADOREGISTRO = :P_NESTADOREGISTRO \n"+
				"WHERE NIDMOVIMIENTOPK= :P_NIDMOVIMIENTOPK");
			MapSqlParameterSource parametros2 = new MapSqlParameterSource();
			parametros2.addValue("P_NESTADOREGISTRO",  Constantes.estadoDesactivado);
			parametros2.addValue("P_NIDMOVIMIENTOPK", idMovimiento);
			namedParameterJdbcTemplate.update(sql2.toString(), parametros2);
			
			
			// ACTUALIZAMOS EL EXPEDIENTE
			 
			sql3.append(
			" UPDATE "+Constantes.tablaExpediente+
			"  SET \n"+
			"  NESTADODOCUMENTOFK= :P_NESTADODOCUMENTOFK, \n"+
			"  NOFICINAFK= :P_NOFICINAFK \n"+
			" WHERE NIDEXPEDIENTEPK= :P_NIDEXPEDIENTEPK");
			MapSqlParameterSource parametros3 = new MapSqlParameterSource();
			parametros3.addValue("P_NESTADODOCUMENTOFK",  Constantes.ESTADO_DOCUMENTO_RECIBIDO);
			parametros3.addValue("P_NOFICINAFK", idOficina);
			parametros3.addValue("P_NIDEXPEDIENTEPK", idExpediente);
			namedParameterJdbcTemplate.update(sql3.toString(), parametros3);
			
			respuesta = true;
		} catch (Exception e) {
			respuesta = false;
			logger.error("ERROR : ExpedienteDaoImpl recibirExpediente " + e.getMessage() + "---" + e.getClass());
		}
		return respuesta;
	}

	@Override
	public Expediente infoExpediente(Long idexpediente) {
		StringBuffer sql = new StringBuffer();
		Expediente info = new Expediente();
		try {
			sql.append(
				"SELECT  \n"+
				"    T1.NIDEXPEDIENTEPK, \n"+
				"    T1.VCODIGO_EXPEDIENTE, \n"+
				"    T2.VNOMBRE, \n"+
				"    T1.TUPACFK, \n"+
				"    T7.VNOMBRE AS VNOMBRETUPAC, \n"+
				"    T1.VNUMERODOCUMENTO, \n"+
				"    T1.VNUMEROFOLIO, \n"+
				"    T1.VASUNTO, \n"+
				"    T3.VCORREO, \n"+
				"    CONVERT(varchar,T1.DFECREGISTRO,22) AS VDFECREGISTRO, \n"+
				"    T6.VNOMBRE AS ESTADODOCUMENTO, \n"+
				"    CASE T1.NTIPOPERSONA WHEN 1 THEN CONCAT(T3.VAPEPATERNO,' ',T3.VAPEMATERNO,','+T3.VNOMBRE)  \n"+
				"    WHEN 2 THEN T5.VRAZONSOCIAL  END   VREMITENTE, \n"+ 
				"    T1.VNOMBRE_ARCHIVO,    \n"+
				"    T1.VUBICACION_ARCHIVO, \n"+
				"    T1.VEXTENSION \n"+
				" FROM        "+Constantes.tablaExpediente+"       T1  \n"+
				"  INNER JOIN "+Constantes.tablaTipoDocumentos+"   T2 ON T1.TIPO_DOCUMENTOFK=T2.NIDTIPODOCUMENTOPK  \n"+
				"  INNER JOIN "+Constantes.tablaPersona+"          T3 ON T1.PERSONAFK=T3.NIDPERSONAPK   \n"+
				"  LEFT JOIN " +Constantes.tablaPersonaNatural+"   T4 ON T1.PERSONAFK=T4.NIDPERSONAFK  \n"+
				"  LEFT JOIN " +Constantes.tablaPersonaJuridica+"  T5 ON T3.NIDPERSONAPK=T5.NIDPERSONAFK  \n"+
				"  INNER JOIN " +Constantes.tablaEstadoDocumento+" T6 ON T1.NESTADODOCUMENTOFK=T6.IDESTADOCUMENTOPK \n"+
				"  LEFT JOIN " +Constantes.tablaTupac+"  		   T7 ON T1.TUPACFK=T7.TUPACPK \n"+ 
				" WHERE NIDEXPEDIENTEPK= :P_NIDEXPEDIENTEPK");
		      MapSqlParameterSource parametros = new MapSqlParameterSource();
		      parametros.addValue("P_NIDEXPEDIENTEPK", idexpediente);
		      info = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(Expediente.class));
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl infoExpediente " + e.getMessage() + "---" + e.getClass());
		}
		return info;
	}

	@Override
	public MovimientoExpediente infoMovimiento(Long idexpediente, Long idmovimiento) {
		StringBuffer sql =new StringBuffer();
		MovimientoExpediente info = new MovimientoExpediente();
		try {
			sql.append(
			" SELECT  \n"+
			"   T1.NIDMOVIMIENTOPK, \n"+
			"   T1.NIDEXPEDIENTEFK, \n"+
			"   T2.VNOMBRE AS VOFICINA, \n"+
			"   T3.VNOMBRE AS VESTADO_DOCUMENTO, \n"+
			"   T1.VOBSERVACION, \n"+
			"   T1.OFICINA_ORIGENFK, \n"+ 
			"   CONVERT(varchar,T1.DFECHAOFICINA,22) AS VFECHAOFICINA, \n"+
			"   T1.NESTADODOCUMENTOFK, \n"+
			"   T4.VNOMBRE_ARCHIVO, \n"+
			"   T4.VUBICACION_ARCHIVO, \n"+
			"   T4.VEXTENSION \n"+
			" FROM "+Constantes.tablaMovimiento+" T1 \n"+
			"  INNER JOIN "+Constantes.tablaOficinas+" T2 ON T1.OFICINA_ORIGENFK=T2.NIDOFICINAPK  \n"+
			"  INNER JOIN "+Constantes.tablaEstadoDocumento+" T3 ON T1.NESTADODOCUMENTOFK=T3.IDESTADOCUMENTOPK \n"+
			"  LEFT JOIN  "+Constantes.tablaArchivoMovimiento+" T4   ON T4.NEXPEDIENTEFK=T1.NIDEXPEDIENTEFK  AND T4.NOFICINAFK=T1.OFICINA_ORIGENFK AND T4.NESTADOREGISTRO=1 \n"+
			" WHERE T1.NIDEXPEDIENTEFK= :P_NIDEXPEDIENTEFK AND T1.NIDMOVIMIENTOPK= :P_NIDMOVIMIENTOPK AND T1.NESTADOREGISTRO= :P_NESTADOREGISTRO");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDEXPEDIENTEFK", idexpediente);
			parametros.addValue("P_NIDMOVIMIENTOPK", idmovimiento);
			parametros.addValue("P_NESTADOREGISTRO", Constantes.estadoActivado);
			info = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(MovimientoExpediente.class));
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl infoMovimiento " + e.getMessage() + "---" + e.getClass());
		}
		return info;
	}

	@Override
	public boolean responderExpediente(Expediente formexpediente) {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		StringBuffer sql3 = new StringBuffer();
		StringBuffer sql4 = new StringBuffer();
		StringBuffer sql5 = new StringBuffer();
		StringBuffer sql6 = new StringBuffer();
		boolean respuesta = false;
		
		try {
			
			//INSERTAMOS UNA COPIA EN LA MISMA OFICINA
			sql2.append(
				"INSERT INTO "+Constantes.tablaMovimiento+" ( \n"+
			    "	NIDEXPEDIENTEFK, \n"+
			    "	NESTADODOCUMENTOFK, \n"+
			    "	OFICINA_ORIGENFK, \n"+
			    "	OFICINA_DESTINOFK, \n"+
			    "	DFECHAOFICINA, \n"+
			    "	DFECHAENVIO, \n"+
			    "	NESTADOREGISTRO, \n"+
			    "	VOBSERVACION ) \n"+
			    "SELECT  \n"+
			    "	NIDEXPEDIENTEFK, \n"+
			    "	:P_NESTADODOCUMENTOFK, \n"+
			    "	:P_OFICINA_ORIGENFK, \n"+
			    "	:P_OFICINA_DESTINOFK, \n"+
			    "	:P_DFECHAOFICINA, \n"+
			    "	:P_DFECHAENVIO, \n"+
			    "	:P_NESTADOREGISTRO, \n"+
			    "	:P_VOBSERVACION \n"+
			    " FROM "+Constantes.tablaMovimiento+" \n"+
			    " WHERE NIDMOVIMIENTOPK= :P_NIDMOVIMIENTOPK");
			MapSqlParameterSource parametros2 = new MapSqlParameterSource();
			parametros2.addValue("P_NESTADODOCUMENTOFK", formexpediente.getNESTADODOCUMENTOFK());
			parametros2.addValue("P_OFICINA_DESTINOFK",formexpediente.getOFICINA_DESTINOFK());
			parametros2.addValue("P_OFICINA_ORIGENFK",formexpediente.getOFICINA_ORIGENFK());
			parametros2.addValue("P_DFECHAOFICINA", Fechas.fechaActual());
			parametros2.addValue("P_DFECHAENVIO", Fechas.fechaActual());
			parametros2.addValue("P_NESTADOREGISTRO", Constantes.estadoDesactivado);
			parametros2.addValue("P_VOBSERVACION", formexpediente.getVOBSERVACION());
			parametros2.addValue("P_NIDMOVIMIENTOPK", formexpediente.getNIDMOVIMIENTOFK());
			namedParameterJdbcTemplate.update(sql2.toString(), parametros2);	 
 
			
			//APAGAMOS EL QUE ESTA EN ESTADO PENDIENTE
			sql3.append(
				"UPDATE "+Constantes.tablaMovimiento+"  \n"+
			    " SET  NESTADOREGISTRO = :P_NESTADOREGISTRO \n"+
				"WHERE NIDMOVIMIENTOPK= :P_NIDMOVIMIENTOPK");
			MapSqlParameterSource parametros3 = new MapSqlParameterSource();
			parametros3.addValue("P_NESTADOREGISTRO",  Constantes.estadoDesactivado);
			parametros3.addValue("P_NIDMOVIMIENTOPK", formexpediente.getNIDMOVIMIENTOFK());
			namedParameterJdbcTemplate.update(sql3.toString(), parametros3);
			
			//INSERTAMOS EL NUEVO MANTENIMIENTO 
			sql.append(
				"INSERT INTO "+Constantes.tablaMovimiento+" ( \n"+
			    "	NIDEXPEDIENTEFK, \n"+
			    "	NESTADODOCUMENTOFK, \n"+
			    "	OFICINA_ORIGENFK, \n"+
			    "	OFICINA_DESTINOFK, \n"+
			    "	DFECHAOFICINA, \n"+
			    "	DFECHAENVIO, \n"+
			    "	VOBSERVACION ) \n"+
			    "SELECT  \n"+
			    "	:P_NIDEXPEDIENTEFK, \n"+
			    "	:P_NESTADODOCUMENTOFK, \n"+
			    "	:P_OFICINA_ORIGENFK, \n"+
			    "	:P_OFICINA_DESTINOFK, \n"+
			    "	:P_DFECHAOFICINA, \n"+
			    "	:P_DFECHAENVIO, \n"+
			    "	:P_VOBSERVACION \n"+
			    " FROM "+Constantes.tablaMovimiento+" \n"+
			    " WHERE NIDMOVIMIENTOPK= :P_NIDMOVIMIENTOPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDEXPEDIENTEFK", formexpediente.getNIDEXPEDIENTEPK());
			parametros.addValue("P_NESTADODOCUMENTOFK",(formexpediente.getNESTADODOCUMENTOFK()==Constantes.ESTADO_DOCUMENTO_DERIVADO)? Constantes.ESTADO_DOCUMENTO_PENDIENTE : formexpediente.getNESTADODOCUMENTOFK());
			parametros.addValue("P_OFICINA_ORIGENFK",formexpediente.getOFICINA_ORIGENFK());
			parametros.addValue("P_OFICINA_DESTINOFK",formexpediente.getOFICINA_DESTINOFK());
			parametros.addValue("P_DFECHAOFICINA", Fechas.fechaActual());
			parametros.addValue("P_DFECHAENVIO", Fechas.fechaActual());
			parametros.addValue("P_VOBSERVACION", formexpediente.getVOBSERVACION());
			parametros.addValue("P_NIDMOVIMIENTOPK", formexpediente.getNIDMOVIMIENTOFK());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			namedParameterJdbcTemplate.update(sql.toString(), parametros,keyHolder, new String[] {"NIDMOVIMIENTOPK"});	
			logger.info("++"+keyHolder.getKey().longValue()); 
	       Long idMovimientoNuevo = keyHolder.getKey().longValue();
	     
	    	if(formexpediente.getVNOMBRE_ARCHIVO()!=null) {
	    	   
	    	//APAGAMOS LOS DEMAS ARCHIVOS DEL EXPEDIENTE
	    	   sql6.append(
	    		   " UPDATE "+Constantes.tablaArchivoMovimiento+" SET \n"+
	    	       "   NESTADOREGISTRO = :P_DESACTIVO \n"+
	    		   " WHERE NEXPEDIENTEFK = :P_NEXPEDIENTEFK");
	    	   MapSqlParameterSource parametros6 = new MapSqlParameterSource();
	    	   parametros6.addValue("P_NEXPEDIENTEFK", formexpediente.getNIDEXPEDIENTEPK());
	    	   parametros6.addValue("P_DESACTIVO", Constantes.estadoDesactivado);
	    	   namedParameterJdbcTemplate.update(sql6.toString(), parametros6);
	    	   
	    	// PROCEDEMOS A SUBIR EL ARCHIVO
				sql4.append(
				  " INSERT INTO "+Constantes.tablaArchivoMovimiento+" ( \n"+
				  "   NEXPEDIENTEFK,      \n"+ 
				  "   NOFICINAFK,         \n"+
				  "   DFECHAREGISTRO,     \n"+
				  "   VNOMBRE_ARCHIVO,    \n"+		 
				  "   VUBICACION_ARCHIVO, \n"+  
				  "   VEXTENSION )        \n"+
				  " VALUES (            \n"+
				  "   :P_NEXPEDIENTEFK,      \n"+ 
				  "   :P_NOFICINAFK,         \n"+
				  "   :P_DFECHAREGISTRO,     \n"+
				  "   :P_VNOMBRE_ARCHIVO,    \n"+		 
				  "   :P_VUBICACION_ARCHIVO, \n"+  
				  "   :P_VEXTENSION )        ");
				MapSqlParameterSource parametros4 = new MapSqlParameterSource();
				parametros4.addValue("P_NEXPEDIENTEFK", formexpediente.getNIDEXPEDIENTEPK()); 
				parametros4.addValue("P_NOFICINAFK", formexpediente.getOFICINA_ORIGENFK());
				parametros4.addValue("P_DFECHAREGISTRO", Fechas.fechaActual());
				parametros4.addValue("P_VNOMBRE_ARCHIVO", formexpediente.getVNOMBRE_ARCHIVO());
				parametros4.addValue("P_VUBICACION_ARCHIVO", formexpediente.getVUBICACION_ARCHIVO());
				parametros4.addValue("P_VEXTENSION", formexpediente.getVEXTENSION());
				namedParameterJdbcTemplate.update(sql4.toString(), parametros4);
	    	   
	       }
	       
	       
			
			sql5.append(
			" UPDATE "+Constantes.tablaExpediente+
			"  SET \n"+
			"  NESTADODOCUMENTOFK= :P_NESTADODOCUMENTOFK, \n"+
			"  NOFICINAFK= :P_NOFICINAFK \n"+
			" WHERE NIDEXPEDIENTEPK= :P_NIDEXPEDIENTEPK");
			MapSqlParameterSource parametros5 = new MapSqlParameterSource();
			parametros5.addValue("P_NESTADODOCUMENTOFK",  Constantes.ESTADO_DOCUMENTO_PENDIENTE);
			parametros5.addValue("P_NOFICINAFK", formexpediente.getOFICINA_DESTINOFK());
			parametros5.addValue("P_NIDEXPEDIENTEPK", formexpediente.getNIDEXPEDIENTEPK());
			namedParameterJdbcTemplate.update(sql5.toString(), parametros5);
			
			
			respuesta = true;
		} catch (Exception e) {
			respuesta = false;
			logger.error("ERROR : ExpedienteDaoImpl responderExpediente " + e.getMessage() + "---" + e.getClass());
		}
		return respuesta;
	}

	@Override
	public boolean responderExpedienteArchivadoOfinalizado(Expediente formExpediente) {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		StringBuffer sql3 = new StringBuffer();
		boolean respuesta = false;
		
		try {
			
			//INSERTAMOS EL NUEVO MANTENIMIENTO 
			sql.append(
				"INSERT INTO "+Constantes.tablaMovimiento+" ( \n"+
			    "	NIDEXPEDIENTEFK, \n"+
			    "	NESTADODOCUMENTOFK, \n"+
			    "	OFICINA_ORIGENFK, \n"+
			    "	OFICINA_DESTINOFK, \n"+
			    "	DFECHAOFICINA, \n"+ 
			    "	DFECHAENVIO, \n"+
			    "	NESTADOREGISTRO, \n"+
			    "	VOBSERVACION ) \n"+
			    "SELECT  \n"+
			    "	NIDEXPEDIENTEFK, \n"+
			    "	:P_NESTADODOCUMENTOFK, \n"+
			    "	:P_OFICINA_ORIGENFK, \n"+
			    "	:P_OFICINA_DESTINOFK, \n"+
			    "	:P_DFECHAOFICINA, \n"+
			    "	:P_DFECHAENVIO, \n"+
			    "	:P_NESTADOREGISTRO, \n"+
			    "	:P_VOBSERVACION \n"+
			    " FROM "+Constantes.tablaMovimiento+" \n"+
			    " WHERE NIDMOVIMIENTOPK= :P_NIDMOVIMIENTOPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NESTADODOCUMENTOFK", formExpediente.getNESTADODOCUMENTOFK());
			parametros.addValue("P_OFICINA_ORIGENFK", formExpediente.getOFICINA_ORIGENFK());
			parametros.addValue("P_OFICINA_DESTINOFK", formExpediente.getOFICINA_ORIGENFK());
			parametros.addValue("P_DFECHAOFICINA", Fechas.fechaActual());
			parametros.addValue("P_DFECHAENVIO", Fechas.fechaActual());
			parametros.addValue("P_NESTADOREGISTRO",  Constantes.estadoDesactivado);
			parametros.addValue("P_VOBSERVACION",  formExpediente.getVOBSERVACION());
			parametros.addValue("P_NIDMOVIMIENTOPK", formExpediente.getNIDMOVIMIENTOFK());
			namedParameterJdbcTemplate.update(sql.toString(), parametros);	 
			
			//APAGAMOS EL QUE ESTA EN ESTADO PENDIENTE
			sql2.append(
				"UPDATE "+Constantes.tablaMovimiento+"  \n"+
			    " SET  NESTADOREGISTRO = :P_NESTADOREGISTRO \n"+
				"WHERE NIDMOVIMIENTOPK= :P_NIDMOVIMIENTOPK");
			MapSqlParameterSource parametros2 = new MapSqlParameterSource();
			parametros2.addValue("P_NESTADOREGISTRO",  Constantes.estadoDesactivado);
			parametros2.addValue("P_NIDMOVIMIENTOPK", formExpediente.getNIDMOVIMIENTOFK());
			namedParameterJdbcTemplate.update(sql2.toString(), parametros2);
			
			
			sql3.append(
			" UPDATE "+Constantes.tablaExpediente+
			"  SET \n"+
			"  NESTADODOCUMENTOFK= :P_NESTADODOCUMENTOFK, \n"+
			"  NOFICINAFK= :P_NOFICINAFK \n"+
			" WHERE NIDEXPEDIENTEPK= :P_NIDEXPEDIENTEPK");
			MapSqlParameterSource parametros3 = new MapSqlParameterSource();
			parametros3.addValue("P_NESTADODOCUMENTOFK", formExpediente.getNESTADODOCUMENTOFK());
			parametros3.addValue("P_NOFICINAFK", formExpediente.getOFICINA_ORIGENFK());
			parametros3.addValue("P_NIDEXPEDIENTEPK", formExpediente.getNIDEXPEDIENTEPK());
			namedParameterJdbcTemplate.update(sql3.toString(), parametros3);
			
			
			respuesta = true;
		} catch (Exception e) {
			respuesta = false;
			logger.error("ERROR : ExpedienteDaoImpl responderExpedienteArchivadoOfinalizado " + e.getMessage() + "---" + e.getClass());
		}
		return respuesta;
	}

	@Override
	public ArchivoMovimiento infoMovimientoArchivoRespuesta(Long idexpediente, Long idoficina,String nombrearchivo) {
		StringBuffer sql = new StringBuffer();
		ArchivoMovimiento archivoMovimiento = new ArchivoMovimiento();
		try {
			sql.append(
			  " SELECT \n"+
			  "	   NIDARCHIVOMOVIMIENTO, \n"+
			  "	   NEXPEDIENTEFK, \n"+
			  "	   NOFICINAFK, \n"+
			  "	   DFECHAREGISTRO, \n"+
			  "	   VNOMBRE_ARCHIVO, \n"+
			  "	   VUBICACION_ARCHIVO, \n"+
			  "	   VEXTENSION \n"+
			  "  FROM "+Constantes.tablaArchivoMovimiento+" \n"+
			  " WHERE NEXPEDIENTEFK= :P_NEXPEDIENTEFK AND NOFICINAFK = :P_NOFICINAFK  AND VNOMBRE_ARCHIVO= :P_VNOMBRE_ARCHIVO");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NEXPEDIENTEFK", idexpediente);
			parametros.addValue("P_NOFICINAFK", idoficina);
			parametros.addValue("P_VNOMBRE_ARCHIVO", nombrearchivo);
			archivoMovimiento = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(ArchivoMovimiento.class));
			
			 
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl infoMovimientoArchivoRespuesta " + e.getMessage() + "---" + e.getClass());
		}
		return archivoMovimiento;
	}

	@Override
	public List<HojaRuta> infoHojaRuta(String anio, String codigoExpediente) {
		StringBuffer sql = new StringBuffer();
		List<HojaRuta> lista = new ArrayList<HojaRuta>();
		 try {
			 sql.append(
				 "SELECT \n"+
			     "    ROW_NUMBER() OVER ( ORDER BY T1.NIDMOVIMIENTOPK )  AS NITEM,  \n"+
			     "    T6.VNOMBRE AS TIPO_DOCUMENTO, \n"+
			     "    CONVERT(varchar,T1.DFECHAOFICINA,22) AS VFECHAOFICINA, \n"+
			     "    T3.VNOMBRE AS OFICINA_ORIGEN, \n"+
			     "    T4.VNOMBRE AS OFICINA_DESTINO, \n"+
			     "    T5.VNOMBRE AS ESTADO_DOCUMENTO, \n"+
			     "    CONVERT(varchar,T1.DFECHARECEPCION,22) AS VFECHARECEPCION, \n"+
			     "    T1.VOBSERVACION \n"+
			     " FROM "+Constantes.tablaMovimiento+"             T1  \n"+
			     "  INNER JOIN "+Constantes.tablaExpediente+"      T2 ON T1.NIDEXPEDIENTEFK=T2.NIDEXPEDIENTEPK  \n"+ 
			     "  LEFT  JOIN "+Constantes.tablaOficinas+"        T3 ON T1.OFICINA_ORIGENFK=T3.NIDOFICINAPK \n"+ 
			     "  LEFT  JOIN "+Constantes.tablaOficinas+"        T4 ON T1.OFICINA_DESTINOFK=T4.NIDOFICINAPK \n"+
			     "  INNER JOIN "+Constantes.tablaEstadoDocumento+" T5 ON T1.NESTADODOCUMENTOFK=T5.IDESTADOCUMENTOPK \n"+
			     "  INNER JOIN "+Constantes.tablaTipoDocumentos+"  T6 ON T6.NIDTIPODOCUMENTOPK=T2.TIPO_DOCUMENTOFK \n"+
			     " WHERE  SUBSTRING(VCODIGO_EXPEDIENTE,3,4)= :P_ANIO \n"+
			     "  AND   SUBSTRING(VCODIGO_EXPEDIENTE,8,LEN(VCODIGO_EXPEDIENTE))= :P_VCODIGO_EXPEDIENTE  \n"+
			     "  AND T1.NELIMINADO= :P_NELIMINADO ORDER BY T1.NIDMOVIMIENTOPK ASC");
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_ANIO", anio);
			 parametros.addValue("P_VCODIGO_EXPEDIENTE", codigoExpediente);
			 parametros.addValue("P_NELIMINADO", Constantes.estadoDesactivado);
			 lista = namedParameterJdbcTemplate.query(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(HojaRuta.class));
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl infoHojaRuta " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}

	@Override
	public Expediente infoExpedienteCodigo(String anio, String codigoExpediente) {
		StringBuffer sql = new StringBuffer();
		Expediente info = new Expediente();
		try {
			sql.append(
				"SELECT  \n"+
				"    T1.NIDEXPEDIENTEPK, \n"+
				"    T1.VCODIGO_EXPEDIENTE, \n"+
				"    T2.VNOMBRE, \n"+
				"    T1.VNUMERODOCUMENTO, \n"+
				"    T1.VNUMEROFOLIO, \n"+
				"    T1.VASUNTO, \n"+
				"    CONVERT(varchar,T1.DFECREGISTRO,22) AS VDFECREGISTRO, \n"+
				"    T6.VNOMBRE AS ESTADODOCUMENTO, \n"+
				"    CASE T1.NTIPOPERSONA WHEN 1 THEN CONCAT(T3.VAPEPATERNO,' ',T3.VAPEMATERNO,','+T3.VNOMBRE)  \n"+
				"    WHEN 2 THEN T5.VRAZONSOCIAL  END   VREMITENTE, \n"+ 
				"    CASE T1.NTIPOPERSONA WHEN 1 THEN T3.VDIRECCION  \n"+
				"    WHEN 2 THEN T5.VDIRECCION  END   VDIRECCION_SOLICITANTE, \n"+ 
				"    T1.VNOMBRE_ARCHIVO,    \n"+
				"    T1.VUBICACION_ARCHIVO, \n"+
				"    T1.VEXTENSION \n"+
				" FROM        "+Constantes.tablaExpediente+"       T1  \n"+
				"  INNER JOIN "+Constantes.tablaTipoDocumentos+"   T2 ON T1.TIPO_DOCUMENTOFK=T2.NIDTIPODOCUMENTOPK  \n"+
				"  INNER JOIN "+Constantes.tablaPersona+"          T3 ON T1.PERSONAFK=T3.NIDPERSONAPK   \n"+
				"  LEFT JOIN " +Constantes.tablaPersonaNatural+"   T4 ON T3.NIDPERSONAPK=T4.NIDPERSONAFK  \n"+
				"  LEFT JOIN " +Constantes.tablaPersonaJuridica+"  T5 ON T3.NIDPERSONAPK=T5.NIDPERSONAFK  \n"+
				" INNER JOIN " +Constantes.tablaEstadoDocumento+"  T6 ON T1.NESTADODOCUMENTOFK=T6.IDESTADOCUMENTOPK \n"+
				 " WHERE  SUBSTRING(T1.VCODIGO_EXPEDIENTE,3,4)= :P_ANIO \n"+
			     "  AND   SUBSTRING(T1.VCODIGO_EXPEDIENTE,8,LEN(VCODIGO_EXPEDIENTE))= :P_VCODIGO_EXPEDIENTE");
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_ANIO", anio);
			 parametros.addValue("P_VCODIGO_EXPEDIENTE", codigoExpediente); 
		      info = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(Expediente.class));
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl infoExpedienteCodigo " + e.getMessage() + "---" + e.getClass());
			 
		}
		return info;
	}

	@Override
	public List<HojaRuta> infoHojaRutaIdExpediente(Long idExpediente) {
		StringBuffer sql = new StringBuffer();
		List<HojaRuta> lista = new ArrayList<HojaRuta>();
		 try {
			 sql.append(
				 "SELECT \n"+
			     "    ROW_NUMBER() OVER ( ORDER BY T1.NIDMOVIMIENTOPK )  AS NITEM,  \n"+
			     "    T6.VNOMBRE AS TIPO_DOCUMENTO, \n"+
			     "    CONVERT(varchar,T1.DFECHAOFICINA,22) AS VFECHAOFICINA, \n"+
			     "    T3.VNOMBRE AS OFICINA_ORIGEN, \n"+
			     "    T4.VNOMBRE AS OFICINA_DESTINO, \n"+
			     "    T5.VNOMBRE AS ESTADO_DOCUMENTO, \n"+
			     "    CONVERT(varchar,T1.DFECHARECEPCION,22) AS VFECHARECEPCION, \n"+
			     "    T1.VOBSERVACION \n"+
			     " FROM "+Constantes.tablaMovimiento+"             T1  \n"+
			     "  INNER JOIN "+Constantes.tablaExpediente+"      T2 ON T1.NIDEXPEDIENTEFK=T2.NIDEXPEDIENTEPK  \n"+ 
			     "  LEFT  JOIN "+Constantes.tablaOficinas+"        T3 ON T1.OFICINA_ORIGENFK=T3.NIDOFICINAPK \n"+ 
			     "  LEFT  JOIN "+Constantes.tablaOficinas+"        T4 ON T1.OFICINA_DESTINOFK=T4.NIDOFICINAPK \n"+
			     "  INNER JOIN "+Constantes.tablaEstadoDocumento+" T5 ON T1.NESTADODOCUMENTOFK=T5.IDESTADOCUMENTOPK \n"+
			     "  INNER JOIN "+Constantes.tablaTipoDocumentos+"  T6 ON T6.NIDTIPODOCUMENTOPK=T2.TIPO_DOCUMENTOFK \n"+
			     " WHERE   T2.NIDEXPEDIENTEPK= :P_NIDEXPEDIENTEPK  \n"+
			     "  AND T1.NELIMINADO= :P_NELIMINADO ORDER BY T1.NIDMOVIMIENTOPK ASC");
			 MapSqlParameterSource parametros = new MapSqlParameterSource(); 
			 parametros.addValue("P_NIDEXPEDIENTEPK", idExpediente);
			 parametros.addValue("P_NELIMINADO", Constantes.estadoDesactivado);
			 lista = namedParameterJdbcTemplate.query(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(HojaRuta.class));
		} catch (Exception e) {
			logger.error("ERROR :ExpedienteDaoImpl  infoHojaRutaIdExpediente  " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}

	@Override
	public Expediente infoExpedienteId(Long idExpediente) {
		StringBuffer sql = new StringBuffer();
		Expediente info = new Expediente();
		try {
			sql.append(
				"SELECT  \n"+
				"    T1.NIDEXPEDIENTEPK, \n"+
				"    T1.VCODIGO_EXPEDIENTE, \n"+
				"    T2.VNOMBRE, \n"+
				"    T1.TUPACFK, \n"+
				"    T7.VNOMBRE AS VNOMBRETUPAC, \n"+
				"    T1.VNUMERODOCUMENTO, \n"+
				"    T1.VNUMEROFOLIO, \n"+
				"    T1.VASUNTO, \n"+
				"    CONVERT(varchar,T1.DFECREGISTRO,22) AS VDFECREGISTRO, \n"+
				"    T6.VNOMBRE AS ESTADODOCUMENTO, \n"+
				"    CASE T1.NTIPOPERSONA WHEN 1 THEN CONCAT(T3.VAPEPATERNO,' ',T3.VAPEMATERNO,','+T3.VNOMBRE)  \n"+
				"    WHEN 2 THEN T5.VRAZONSOCIAL  END   VREMITENTE, \n"+ 
				"    CASE T1.NTIPOPERSONA WHEN 1 THEN T3.VDIRECCION  \n"+
				"    WHEN 2 THEN T5.VDIRECCION  END   VDIRECCION_SOLICITANTE, \n"+ 
				"    T1.VNOMBRE_ARCHIVO,    \n"+
				"    T1.VUBICACION_ARCHIVO, \n"+
				"    T1.VEXTENSION \n"+
				" FROM        "+Constantes.tablaExpediente+"        T1  \n"+
				"  INNER JOIN "+Constantes.tablaTipoDocumentos+"    T2 ON T1.TIPO_DOCUMENTOFK=T2.NIDTIPODOCUMENTOPK  \n"+
				"  INNER JOIN "+Constantes.tablaPersona+"           T3 ON T1.PERSONAFK=T3.NIDPERSONAPK   \n"+
				"  LEFT JOIN " +Constantes.tablaPersonaNatural+"    T4 ON T3.NIDPERSONAPK=T4.NIDPERSONAFK  \n"+
				"  LEFT JOIN " +Constantes.tablaPersonaJuridica+"   T5 ON T3.NIDPERSONAPK=T5.NIDPERSONAFK  \n"+
				"  INNER JOIN " +Constantes.tablaEstadoDocumento+"  T6 ON T1.NESTADODOCUMENTOFK=T6.IDESTADOCUMENTOPK \n"+
				"  LEFT JOIN " +Constantes.tablaTupac+"  			T7 ON T1.TUPACFK=T7.TUPACPK \n"+
				 " WHERE NIDEXPEDIENTEPK = :P_NIDEXPEDIENTEPK " );
			 MapSqlParameterSource parametros = new MapSqlParameterSource(); 
			  parametros.addValue("P_NIDEXPEDIENTEPK", idExpediente); 
		      info = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(Expediente.class));
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl infoExpedienteId " + e.getMessage() + "---" + e.getClass());
			 
		}
		return info;
	}

	@Override
	public MovimientoExpediente infoMovimientoIdexpediente(Long idMovimiento) {
		MovimientoExpediente info = new MovimientoExpediente();
		StringBuffer sql = new StringBuffer();
		try {
			sql.append(
			    " SELECT \n"+
			    "   NIDMOVIMIENTOPK, \n"+
			    "   NIDEXPEDIENTEFK, \n"+
			    "   NESTADODOCUMENTOFK, \n"+
			    "   OFICINA_ORIGENFK, \n"+
			    "   OFICINA_DESTINOFK \n"+
			    " FROM "+Constantes.tablaMovimiento+" \n"+
			    " WHERE NIDMOVIMIENTOPK = :P_NIDMOVIMIENTOPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDMOVIMIENTOPK", idMovimiento); 
			info = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(MovimientoExpediente.class));
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl infoMovimientoIdexpediente " + e.getMessage() + "---" + e.getClass());
		}
		return info;
	}

	@Override
	public List<ArchivoTupac> listarArchivosTupa(Long idexpediente) {
		StringBuffer sql = new StringBuffer();
		List<ArchivoTupac> lista = new ArrayList<ArchivoTupac>();
		 try {
			sql.append(
			  " SELECT \n"+
			  "   ROW_NUMBER() OVER ( ORDER BY T1.EXPEDIENTEFK)  AS NITEM, \n"+
			  "   T1.NARCHIVOTUPAPK, \n"+
			  "   T1.EXPEDIENTEFK, \n"+
			  "   T1.TUPAFK,\n"+
			  "   T1.REQUISITOFK, \n"+
			  "   T3.VNOMBRE, \n"+
			  "   T1.VNOMBRE_ARCHIVO, \n"+
			  "   T1.VUBICACION_ARCHIVO, \n"+
			  "   T1.VEXTENSION \n"+
			  " FROM  "+Constantes.tablaArchivosTupa+" T1 \n"+ 
			  " INNER JOIN "+Constantes.tablaExpediente+" T2 ON T1.EXPEDIENTEFK=T2.NIDEXPEDIENTEPK  \n"+
			  " INNER JOIN "+Constantes.tablaRequisitos+" T3 ON T1.REQUISITOFK=T3.REQUISITOSTUPACPK  \n"+
			  " WHERE T1.EXPEDIENTEFK= :P_EXPEDIENTEFK");
		 MapSqlParameterSource parametros = new MapSqlParameterSource();
		 parametros.addValue("P_EXPEDIENTEFK", idexpediente);
		 lista= namedParameterJdbcTemplate.query(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(ArchivoTupac.class));
		  
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl listarArchivosTupa " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}

	@Override
	public ArchivoTupac infoArchivoTupa(Long idexpediente, Long idarchivorequisito) {
		StringBuffer sql = new StringBuffer();
		ArchivoTupac info = new ArchivoTupac();
		
		try {
			sql.append(
					  " SELECT \n"+
					  "   ROW_NUMBER() OVER ( ORDER BY T1.NARCHIVOTUPAPK)  AS NITEM, \n"+
					  "   T1.NARCHIVOTUPAPK, \n"+
					  "   T1.EXPEDIENTEFK, \n"+
					  "   T1.TUPAFK,\n"+
					  "   T1.REQUISITOFK, \n"+
					  "   T3.VNOMBRE, \n"+
					  "   T1.VNOMBRE_ARCHIVO, \n"+
					  "   T1.VUBICACION_ARCHIVO, \n"+
					  "   T1.VEXTENSION \n"+
					  " FROM  "+Constantes.tablaArchivosTupa+" T1 \n"+ 
					  " INNER JOIN "+Constantes.tablaExpediente+" T2 ON T1.EXPEDIENTEFK=T2.NIDEXPEDIENTEPK  \n"+
					  " INNER JOIN "+Constantes.tablaRequisitos+" T3 ON T1.REQUISITOFK=T3.REQUISITOSTUPACPK  \n"+
					  " WHERE T1.NARCHIVOTUPAPK= :P_NARCHIVOTUPAPK AND T1.EXPEDIENTEFK= :P_EXPEDIENTEFK");
				 MapSqlParameterSource parametros = new MapSqlParameterSource();
				 parametros.addValue("P_NARCHIVOTUPAPK", idarchivorequisito);
				 parametros.addValue("P_EXPEDIENTEFK", idexpediente );	
				 info= namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(ArchivoTupac.class));
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl infoArchivoTupa " + e.getMessage() + "---" + e.getClass());
		}
		return info;
	}

	@Override
	public boolean guardarExpedienteSimpleInterno(Expediente expediente) {
		String sql ="";
		StringBuffer sql2 = new StringBuffer();
		Long idExpediente =0L;
		StringBuffer sql3 = new StringBuffer();   
		StringBuffer sql4 = new StringBuffer(); 
		Long idMovimiento;
		boolean respuesta = false;
		try {
		   sql=
			 "INSERT INTO "+Constantes.tablaExpediente+" ( \n"+
		     "   VCODIGO_EXPEDIENTE,  \n"+
		     "   TIPO_DOCUMENTOFK,  \n"+
		     "   VNUMERODOCUMENTO,  \n"+
		     "   VNUMEROFOLIO,  \n"+
		     "   VASUNTO,  \n"+
		    // "   DFECHADOCUMENTO,  \n"+
		     "   VNOMBRE_ARCHIVO,  \n"+
		     "   VUBICACION_ARCHIVO,  \n"+
		     "   VEXTENSION,  \n"+
		     "   TUPACFK,  \n"+
		     "   NTIPOPERSONA,  \n"+
		     "   PERSONAFK,  \n"+
		    // "   DFECHATERMINO,  \n"+
		     "   NDIASPLAZO,  \n"+
		     "   NESTADODOCUMENTOFK )"+
		     " VALUES ( \n"+
		     "   :P_VCODIGO_EXPEDIENTE,  \n"+
		     "   :P_TIPO_DOCUMENTOFK,  \n"+
		     "   :P_VNUMERODOCUMENTO,  \n"+
		     "   :P_VNUMEROFOLIO,  \n"+
		     "   :P_VASUNTO,  \n"+
		    // "   :P_DFECHADOCUMENTO,  \n"+
		     "   :P_VNOMBRE_ARCHIVO,  \n"+
		     "   :P_VUBICACION_ARCHIVO,  \n"+
		     "   :P_VEXTENSION,  \n"+
		     "   :P_TUPACFK,  \n"+
		     "   :P_NTIPOPERSONA,  \n"+
		     "   :P_PERSONAFK,  \n"+
		    // "   :P_DFECHATERMINO,  \n"+
		     "   :P_NDIASPLAZO,  \n"+
		     "   :P_NESTADODOCUMENTOFK )";
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		parametros.addValue("P_VCODIGO_EXPEDIENTE", expediente.getVCODIGO_EXPEDIENTE());
		parametros.addValue("P_TIPO_DOCUMENTOFK", expediente.getTIPO_DOCUMENTOFK());
		parametros.addValue("P_VNUMERODOCUMENTO", expediente.getVNUMERODOCUMENTO());
		parametros.addValue("P_VNUMEROFOLIO", expediente.getVNUMEROFOLIO());
		parametros.addValue("P_VASUNTO", expediente.getVASUNTO());
		//parametros.addValue("P_DFECHADOCUMENTO", expediente.getDFECHADOCUMENTO());
		parametros.addValue("P_VNOMBRE_ARCHIVO", expediente.getVNOMBRE_ARCHIVO());
		parametros.addValue("P_VUBICACION_ARCHIVO", expediente.getVUBICACION_ARCHIVO());
		parametros.addValue("P_VEXTENSION", expediente.getVEXTENSION());
		parametros.addValue("P_TUPACFK", expediente.getTUPACFK());
		parametros.addValue("P_NTIPOPERSONA", expediente.getNTIPOPERSONA());
		parametros.addValue("P_PERSONAFK", expediente.getPERSONAFK());
		//parametros.addValue("P_DFECHATERMINO", expediente.getdf);
		parametros.addValue("P_NDIASPLAZO", Constantes.estadoDesactivado);
		parametros.addValue("P_NESTADODOCUMENTOFK", Constantes.ESTADO_DOCUMENTO_PENDIENTE );
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql.toString(),parametros, keyHolder,new String[] {"NIDEXPEDIENTEPK"} );        
		idExpediente =  keyHolder.getKey().longValue();  	  
		logger.info("NIDEXPEDIENTEPK++"+keyHolder.getKey().longValue()); 
		
		
		// INSERTAMOS MOVIMIENTO CON ESTADO DERIVADO EN LA MISMA BANDEJA
		sql2.append(
			"INSERT INTO "+Constantes.tablaMovimiento+" ( \n"+
		     "   NIDEXPEDIENTEFK, \n"+
		     "   NESTADODOCUMENTOFK, \n"+
		     "   OFICINA_ORIGENFK, \n"+ 
		     "   OFICINA_DESTINOFK, \n"+ 
		     "   DFECHAOFICINA,\n"+  
		     "   VOBSERVACION  ) \n"+
	         " VALUES ( \n"+     
		     "   :P_NIDEXPEDIENTEFK,    \n"+   
		     "   :P_NESTADODOCUMENTOFK, \n"+  
		     "   :P_OFICINA_ORIGENFK,   \n"+  
		     "   :P_OFICINA_DESTINOFK,   \n"+ 
		     "   :P_DFECHAOFICINA,      \n"+    
		     "   :P_VOBSERVACION     )");
		MapSqlParameterSource parametrobandejaenvio = new MapSqlParameterSource(); 
		parametrobandejaenvio.addValue("P_NIDEXPEDIENTEFK", idExpediente);
		parametrobandejaenvio.addValue("P_NESTADODOCUMENTOFK", Constantes.ESTADO_DOCUMENTO_DERIVADO);
		parametrobandejaenvio.addValue("P_OFICINA_ORIGENFK",expediente.getOFICINA_ORIGENFK()); 
		parametrobandejaenvio.addValue("P_OFICINA_DESTINOFK",expediente.getOFICINA_DESTINOFK()); 
		parametrobandejaenvio.addValue("P_DFECHAOFICINA", Fechas.fechaActual()); 
		parametrobandejaenvio.addValue("P_VOBSERVACION", expediente.getVASUNTO()); 
	    KeyHolder keyHolderenvio= new GeneratedKeyHolder();
	    namedParameterJdbcTemplate.update(sql2.toString(), parametrobandejaenvio,keyHolderenvio, new String[] {"NIDMOVIMIENTOPK"});
	    logger.info("NIDMOVIMIENTOPK++"+keyHolderenvio.getKey().longValue());
	    idMovimiento = keyHolderenvio.getKey().longValue();
	    
	    
	 // APAGAMOS EL MOVIMIENTO
	    sql4.append(
			"UPDATE "+Constantes.tablaMovimiento+"  \n"+
			" SET  NESTADOREGISTRO = :P_NESTADOREGISTRO \n"+
		    "WHERE NIDMOVIMIENTOPK= :P_NIDMOVIMIENTOPK");
			MapSqlParameterSource parametros4 = new MapSqlParameterSource();
			parametros4.addValue("P_NESTADOREGISTRO",  Constantes.estadoDesactivado);
			parametros4.addValue("P_NIDMOVIMIENTOPK", idMovimiento);
			namedParameterJdbcTemplate.update(sql4.toString(), parametros4);
		
 
		// PROCEDEMOS A INSERTAR EL MOVIMIENTO
		 sql="INSERT INTO "+Constantes.tablaMovimiento+" ( \n"+
			     " NIDEXPEDIENTEFK, \n"+
			     " NESTADODOCUMENTOFK, \n"+
			     " OFICINA_ORIGENFK, \n"+ 
			     " OFICINA_DESTINOFK, \n"+ 
			     " DFECHAOFICINA,\n"+  
			     " VOBSERVACION  ) \n"+
		     " VALUES ( \n"+     
			     " :P_NIDEXPEDIENTEFK,    \n"+   
			     " :P_NESTADODOCUMENTOFK, \n"+  
			     " :P_OFICINA_ORIGENFK,   \n"+  
			     " :P_OFICINA_DESTINOFK,   \n"+ 
			     " :P_DFECHAOFICINA,      \n"+    
			     " :P_VOBSERVACION     )";
		 
		       MapSqlParameterSource parametros2 = new MapSqlParameterSource();
		       parametros2.addValue("P_NIDEXPEDIENTEFK", idExpediente);
		       parametros2.addValue("P_NESTADODOCUMENTOFK", Constantes.ESTADO_DOCUMENTO_PENDIENTE);
		       parametros2.addValue("P_OFICINA_ORIGENFK",expediente.getOFICINA_ORIGENFK()); 
		       parametros2.addValue("P_OFICINA_DESTINOFK",expediente.getOFICINA_DESTINOFK()); 
		       parametros2.addValue("P_DFECHAOFICINA", Fechas.fechaActual()); 
		       parametros2.addValue("P_VOBSERVACION", expediente.getVASUNTO()); 
		       KeyHolder keyHolder2= new GeneratedKeyHolder();
		       namedParameterJdbcTemplate.update(sql, parametros2,keyHolder2, new String[] {"NIDMOVIMIENTOPK"});
		       logger.info("NIDMOVIMIENTOPK++"+keyHolder2.getKey().longValue());
		       
		        sql3.append(
	   			" UPDATE "+Constantes.tablaExpediente+
	   			"  SET \n"+
	   			"  NESTADODOCUMENTOFK= :P_NESTADODOCUMENTOFK, \n"+
	   			"  NOFICINAFK= :P_NOFICINAFK \n"+
	   			" WHERE NIDEXPEDIENTEPK= :P_NIDEXPEDIENTEPK");
	   			MapSqlParameterSource parametros3 = new MapSqlParameterSource();
	   			parametros3.addValue("P_NESTADODOCUMENTOFK",  Constantes.ESTADO_DOCUMENTO_PENDIENTE);
	   			parametros3.addValue("P_NOFICINAFK",expediente.getOFICINA_DESTINOFK());
	   			parametros3.addValue("P_NIDEXPEDIENTEPK", idExpediente);
	   			namedParameterJdbcTemplate.update(sql3.toString(), parametros3);
 
		
		respuesta =true;
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl guardarExpedienteSimpleInterno " + e.getMessage() + "---" + e.getClass());
			respuesta =false;
		}
		return respuesta;
	}

	@Override
	public boolean actualizarClave(Usuarios formUsuario) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		try {
			sql.append(
			"UPDATE "+Constantes.tablaUsuario+" SET VCLAVE= :P_VCLAVE WHERE VUSUARIO = :P_VUSUARIO");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_VCLAVE", formUsuario.getVCLAVE());
			parametros.addValue("P_VUSUARIO", formUsuario.getVUSUARIO());
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
			respuesta = true;
			
		} catch (Exception e) {
			 respuesta = false;
			 logger.error("ERROR : ExpedienteDaoImpl actualizarClave " + e.getMessage() + "---" + e.getClass());
		}
		return respuesta;
	}

	@Override
	public List<ReporteExpediente> listaExpedientesPorEstadoDocuemnto(Long idEstadoDocumento) {
		StringBuffer sql = new StringBuffer();
		List<ReporteExpediente> lista = new ArrayList<ReporteExpediente>();
		try {
			sql.append(
			 " SELECT \n"+
			 "    ROW_NUMBER() OVER ( ORDER BY T2.VNOMBRE )  AS NITEM," +
			 "    T2.VNOMBRE AS VESTADOCUEMNTO, \n"+
			 "    COUNT(1) AS NCANTIDAD    \n"+
			 "  FROM "+Constantes.tablaExpediente+" T1    \n"+
			 "  INNER JOIN "+Constantes.tablaEstadoDocumento+" T2 ON T1.NESTADODOCUMENTOFK=T2.IDESTADOCUMENTOPK    \n");
			
			if(idEstadoDocumento>0) {
				MapSqlParameterSource parametros = new MapSqlParameterSource();
				parametros.addValue("P_NESTADODOCUMENTOFK", idEstadoDocumento);
				sql.append(" WHERE T1.NESTADODOCUMENTOFK= :P_NESTADODOCUMENTOFK \n");
				sql.append("  GROUP BY VNOMBRE"); 
				lista = namedParameterJdbcTemplate.query(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(ReporteExpediente.class));
				
			}else {
				sql.append("  GROUP BY VNOMBRE"); 
				lista = namedParameterJdbcTemplate.query(sql.toString(),BeanPropertyRowMapper.newInstance(ReporteExpediente.class));
			}
			
 
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl listaExpedientesPorEstadoDocuemnto " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}

	@Override
	public List<ReporteExpediente> listaExpedientesPorOficina(Expediente formexpediente) {
		List<ReporteExpediente>  lista = new ArrayList<ReporteExpediente>();
		StringBuffer sql = new StringBuffer();
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		try {
			sql.append(
			  "SELECT \n"+
			  "  ROW_NUMBER() OVER ( ORDER BY T3.VNOMBRE )  AS NITEM, \n"+
			  "  T3.VNOMBRE AS VNOMBREOFICINA, \n"+
			  "  T2.VNOMBRE AS VESTADOCUEMNTO, \n"+
			  "  COUNT(1) AS NCANTIDAD \n"+
			  "FROM "+Constantes.tablaExpediente+" T1 \n"+
			  "INNER JOIN "+Constantes.tablaEstadoDocumento+" T2 ON T1.NESTADODOCUMENTOFK=T2.IDESTADOCUMENTOPK \n"+
			  "INNER JOIN "+Constantes.tablaOficinas+" T3 ON T1.NOFICINAFK=T3.NIDOFICINAPK \n");
			if(formexpediente.getOFICINA_ORIGENFK()>0) {
				
				
				if(formexpediente.getDFECHAINICIO() !=null && formexpediente.getDFECHAFIN()!=null) {
					sql.append(" WHERE T1.NOFICINAFK= :P_NOFICINAFK AND \n"+
				               " CONVERT(date,T1.DFECREGISTRO) BETWEEN :P_FECHAINICIO AND :P_FECHAFIN \n"+
							   "GROUP BY T2.VNOMBRE,T3.VNOMBRE ORDER BY T3.VNOMBRE");
					 parametros.addValue("P_NOFICINAFK", formexpediente.getOFICINA_ORIGENFK());
					 parametros.addValue("P_FECHAINICIO", formexpediente.getDFECHAINICIO());
					 parametros.addValue("P_FECHAFIN", formexpediente.getDFECHAFIN());
				}else {
					sql.append(" WHERE T1.NOFICINAFK= :P_NOFICINAFK \n"+
							   "GROUP BY T2.VNOMBRE,T3.VNOMBRE ORDER BY T3.VNOMBRE");
					 parametros.addValue("P_NOFICINAFK", formexpediente.getOFICINA_ORIGENFK());
				}
				
				lista=namedParameterJdbcTemplate.query(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(ReporteExpediente.class));
			}else {
				
				if(formexpediente.getDFECHAINICIO() !=null && formexpediente.getDFECHAFIN()!=null) {
					sql.append( " WHERE CONVERT(date,T1.DFECREGISTRO) BETWEEN :P_FECHAINICIO AND :P_FECHAFIN \n"+
					             "GROUP BY T2.VNOMBRE,T3.VNOMBRE ORDER BY T3.VNOMBRE");
					 parametros.addValue("P_FECHAINICIO", formexpediente.getDFECHAINICIO());
					 parametros.addValue("P_FECHAFIN", formexpediente.getDFECHAFIN());
					lista=namedParameterJdbcTemplate.query(sql.toString(),parametros,BeanPropertyRowMapper.newInstance(ReporteExpediente.class));
				}else {
					sql.append("GROUP BY T2.VNOMBRE,T3.VNOMBRE ORDER BY T3.VNOMBRE");
					lista=namedParameterJdbcTemplate.query(sql.toString(),BeanPropertyRowMapper.newInstance(ReporteExpediente.class));
				}
				
			}
			
		} catch (Exception e) {
			logger.error("ERROR : ExpedienteDaoImpl listaExpedientesPorOficina " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}

	 
}
