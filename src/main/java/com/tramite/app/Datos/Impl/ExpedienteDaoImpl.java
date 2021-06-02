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

import com.tramite.app.Datos.ExpedienteDao;
import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.utilitarios.Constantes;

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
			 " 	 T6.VNOMBRE AS VESTADO_DOC \n"+
			 " FROM "+Constantes.tablaMovimiento+" T1 \n"+
			 " 	 INNER JOIN EXPEDIENTE       T2 ON T1.NIDEXPEDIENTEFK=T2.NIDEXPEDIENTEPK \n"+
			 " 	 INNER JOIN PERSONA          T3 ON T2.PERSONAFK=T3.NIDPERSONAPK \n"+
			 " 	 LEFT JOIN PERSONA_NATURAL   T4 ON T3.NIDPERSONAPK=T4.NIDPERSONAFK \n"+
			 " 	 LEFT JOIN PERSONA_JURIDICA  T5 ON T3.NIDPERSONAPK=T5.NIDPERSONAFK \n"+
			 " 	 INNER JOIN ESTADO_DOCUMENTO T6 ON T2.NESTADODOCUMENTOFK=T6.IDESTADOCUMENTOPK \n"+
			 " 	 INNER JOIN OFICINA          T7 ON T1.OFICINA_ORIGENFK=T7.NIDOFICINAPK \n"+
			 " 	 LEFT JOIN OFICINA          T8 ON T1.OFICINA_DESTINOFK=T8.NIDOFICINAPK \n"+
			 " WHERE T1.OFICINA_ORIGENFK= :P_OFICINA_ORIGENFK AND T1.NESTADODOCUMENTOFK= :P_NESTADODOCUMENTOFK \n"+
			 "   AND T1.NESTADOREGISTRO= :P_NESTADOREGISTRO AND T1.NELIMINADO= :P_NELIMINADO \n"+
			 " ORDER BY T1.NIDMOVIMIENTOPK DESC"); 
			
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_OFICINA_ORIGENFK",oficina);
			parametros.addValue("P_NESTADODOCUMENTOFK", estadodocumento);
			parametros.addValue("P_NESTADOREGISTRO",1L);
			parametros.addValue("P_NELIMINADO", Constantes.estadoDesactivado);
			lista = namedParameterJdbcTemplate.query(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Bandeja.class));
		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
		}
		return lista;
	}

	 
}
