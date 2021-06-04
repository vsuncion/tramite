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
import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.MovimientoExpediente;
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
			 " 	 T1.NIDMOVIMIENTOPK, \n"+
			 " 	 CONVERT(varchar,DFECHAOFICINA,22) AS VFECHA_OFICINA, \n"+
			 " 	 CASE T2.NTIPOPERSONA WHEN 1 THEN CONCAT(T3.VAPEPATERNO,' ',T3.VAPEMATERNO,','+T3.VNOMBRE)  \n"+
			 " 	 WHEN 2 THEN T5.VRAZONSOCIAL  END   VREMITENTE, \n"+
			 " 	 CONCAT(SUBSTRING(T2.VASUNTO,0,15),'...') AS VASUNTO, \n"+
			 " 	 T7.VNOMBRE AS VOFICINA_ORIGEN, \n"+
			 " 	 T8.VNOMBRE AS VOFICINA_DESTINO, \n"+
			 " 	 T6.VNOMBRE AS VESTADO_DOC, \n"+
			 " 	 T1.NIDEXPEDIENTEFK \n"+
			 " FROM "+Constantes.tablaMovimiento+" T1 \n"+
			 " 	 INNER JOIN EXPEDIENTE       T2 ON T1.NIDEXPEDIENTEFK=T2.NIDEXPEDIENTEPK \n"+
			 " 	 INNER JOIN PERSONA          T3 ON T2.PERSONAFK=T3.NIDPERSONAPK \n"+
			 " 	 LEFT JOIN PERSONA_NATURAL   T4 ON T3.NIDPERSONAPK=T4.NIDPERSONAFK \n"+
			 " 	 LEFT JOIN PERSONA_JURIDICA  T5 ON T3.NIDPERSONAPK=T5.NIDPERSONAFK \n"+
			 " 	 INNER JOIN ESTADO_DOCUMENTO T6 ON T2.NESTADODOCUMENTOFK=T6.IDESTADOCUMENTOPK \n"+
			 " 	 INNER JOIN OFICINA          T7 ON T1.OFICINA_ORIGENFK=T7.NIDOFICINAPK \n"+
			 " 	 LEFT JOIN OFICINA          T8 ON T1.OFICINA_DESTINOFK=T8.NIDOFICINAPK \n"+
			 " WHERE T1.OFICINA_DESTINOFK= :P_OFICINA_DESTINOFK AND T1.NESTADODOCUMENTOFK= :P_NESTADODOCUMENTOFK \n"+
			 "   AND T1.NESTADOREGISTRO= :P_NESTADOREGISTRO AND T1.NELIMINADO= :P_NELIMINADO \n"+
			 " ORDER BY T1.NIDMOVIMIENTOPK DESC"); 
			
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_OFICINA_DESTINOFK",oficina);
			parametros.addValue("P_NESTADODOCUMENTOFK", estadodocumento);
			parametros.addValue("P_NESTADOREGISTRO",Constantes.estadoActivado);
			parametros.addValue("P_NELIMINADO", Constantes.estadoDesactivado);
			lista = namedParameterJdbcTemplate.query(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Bandeja.class));
		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}

	@Override
	public boolean recibirExpediente(Long idMovimiento) {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
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
			respuesta = true;
		} catch (Exception e) {
			respuesta = false;
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
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
				"    T1.VNUMERODOCUMENTO, \n"+
				"    T1.VNUMEROFOLIO, \n"+
				"    T1.VASUNTO, \n"+
				"    T6.VNOMBRE AS ESTADODOCUMENTO, \n"+
				"    CASE T1.NTIPOPERSONA WHEN 1 THEN CONCAT(T3.VAPEPATERNO,' ',T3.VAPEMATERNO,','+T3.VNOMBRE)  \n"+
				"    WHEN 2 THEN T5.VRAZONSOCIAL  END   VREMITENTE, \n"+
				"    T1.VNUMERODOCUMENTO, \n"+
				"    T1.VUBICACION_ARCHIVO, \n"+
				"    T1.VEXTENSION \n"+
				" FROM        "+Constantes.tablaExpediente+"       T1  \n"+
				"  INNER JOIN "+Constantes.tablaTipoDocumentos+"   T2 ON T1.TIPO_DOCUMENTOFK=T2.NIDTIPODOCUMENTOPK  \n"+
				"  INNER JOIN "+Constantes.tablaPersona+"          T3 ON T1.PERSONAFK=T3.NIDPERSONAPK   \n"+
				"  LEFT JOIN " +Constantes.tablaPersonaNatural+"   T4 ON T3.NIDPERSONAPK=T4.NIDPERSONAFK  \n"+
				"  LEFT JOIN " +Constantes.tablaPersonaJuridica+"  T5 ON T3.NIDPERSONAPK=T5.NIDPERSONAFK  \n"+
				" INNER JOIN " +Constantes.tablaEstadoDocumento+"  T6 ON T1.NESTADODOCUMENTOFK=T6.IDESTADOCUMENTOPK \n"+
				" WHERE NIDEXPEDIENTEPK= :P_NIDEXPEDIENTEPK");
		      MapSqlParameterSource parametros = new MapSqlParameterSource();
		      parametros.addValue("P_NIDEXPEDIENTEPK", idexpediente);
		      info = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(Expediente.class));
		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
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
			"   T2.VNOMBRE AS VOFICINA, \n"+
			"   T3.VNOMBRE AS VESTADO_DOCUMENTO, \n"+
			"   T1.VOBSERVACION \n"+
			" FROM "+Constantes.tablaMovimiento+" T1 \n"+
			"  INNER JOIN "+Constantes.tablaOficinas+" T2 ON T1.OFICINA_ORIGENFK=T2.NIDOFICINAPK  \n"+
			"  INNER JOIN "+Constantes.tablaEstadoDocumento+" T3 ON T1.NESTADODOCUMENTOFK=T3.IDESTADOCUMENTOPK \n"+
			" WHERE T1.NIDEXPEDIENTEFK= :P_NIDEXPEDIENTEFK AND NIDMOVIMIENTOPK= :P_NIDMOVIMIENTOPK AND T1.NESTADOREGISTRO= :P_NESTADOREGISTRO");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDEXPEDIENTEFK", idexpediente);
			parametros.addValue("P_NIDMOVIMIENTOPK", idmovimiento);
			parametros.addValue("P_NESTADOREGISTRO", Constantes.estadoActivado);
			info = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(MovimientoExpediente.class));
		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
		}
		return info;
	}

	@Override
	public boolean responderExpediente(Expediente formexpediente) {
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
			    "	:P_NIDEXPEDIENTEFK, \n"+
			    "	:P_NESTADODOCUMENTOFK, \n"+
			    "	:P_OFICINA_ORIGENFK, \n"+
			    "	:P_OFICINA_DESTINOFK, \n"+
			    "	DFECHAOFICINA, \n"+
			    "	:P_DFECHARECEPCION, \n"+
			    "	:P_VOBSERVACION \n"+
			    " FROM "+Constantes.tablaMovimiento+" \n"+
			    " WHERE NIDMOVIMIENTOPK= :P_NIDMOVIMIENTOPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDEXPEDIENTEFK", formexpediente.getNIDEXPEDIENTEPK());
			parametros.addValue("P_NESTADODOCUMENTOFK",(formexpediente.getNESTADODOCUMENTOFK()==Constantes.ESTADO_DOCUMENTO_DERIVADO)? Constantes.ESTADO_DOCUMENTO_PENDIENTE : formexpediente.getNESTADODOCUMENTOFK());
			parametros.addValue("P_OFICINA_ORIGENFK",formexpediente.getOFICINA_ORIGENFK());
			parametros.addValue("P_OFICINA_DESTINOFK",formexpediente.getOFICINA_DESTINOFK());
			parametros.addValue("P_DFECHARECEPCION", Fechas.fechaActual());
			parametros.addValue("P_VOBSERVACION", formexpediente.getVOBSERVACION());
			parametros.addValue("P_NIDMOVIMIENTOPK", formexpediente.getNIDMOVIMIENTOFK());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			namedParameterJdbcTemplate.update(sql.toString(), parametros,keyHolder, new String[] {"NIDMOVIMIENTOPK"});	
			logger.info("++"+keyHolder.getKey().longValue()); 
			
			//INSERTAMOS UNA COPIA EN LA MISMA OFICINA
			sql2.append(
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
			    "	:P_OFICINA_DESTINOFK, \n"+
			    "	DFECHAOFICINA, \n"+
			    "	:P_DFECHARECEPCION, \n"+
			    "	:P_VOBSERVACION \n"+
			    " FROM "+Constantes.tablaMovimiento+" \n"+
			    " WHERE NIDMOVIMIENTOPK= :P_NIDMOVIMIENTOPK");
			MapSqlParameterSource parametros2 = new MapSqlParameterSource();
			parametros2.addValue("P_NESTADODOCUMENTOFK", formexpediente.getNESTADODOCUMENTOFK());
			parametros2.addValue("P_OFICINA_DESTINOFK",formexpediente.getOFICINA_DESTINOFK());
			parametros2.addValue("P_DFECHARECEPCION", Fechas.fechaActual());
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
			
			// PROCEDEMOS A SUBIR EL ARCHIVO
			
			respuesta = true;
		} catch (Exception e) {
			respuesta = false;
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
		}
		return respuesta;
	}

	 
}
