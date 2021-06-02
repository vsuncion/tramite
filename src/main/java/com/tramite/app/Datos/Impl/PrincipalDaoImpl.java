package com.tramite.app.Datos.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper; 
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tramite.app.Datos.PrincipalDao;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.PrePersona;
import com.tramite.app.utilitarios.Constantes;
import com.tramite.app.utilitarios.Fechas;

@Repository
public class PrincipalDaoImpl implements PrincipalDao {

	Logger logger = LoggerFactory.getLogger(getClass());
 

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Persona buscarPersona(int tipoPersona, String vnumero) {
		StringBuffer sql = new StringBuffer();
		Persona infoPersona = new Persona();
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		try {

			switch (tipoPersona) {
			case Constantes.tipoPersonaNatural:

				sql.append(" SELECT " + "	  VNOMBRE,	    \n" + "	  VAPEPATERNO,  \n" + "	  VAPEMATERNO,  \n"
						+ "	  NTIPODOCFK,   \n" + "	  VNUMERODOC,   \n" + "	  VDIRECCION,   \n" + "	  VCORREO,      \n"
						+ "	  VTELEFONO,    \n" + "	  NIDPERSONAPK \n" + " FROM " + Constantes.tablaPersona
						+ " WHERE VNUMERODOC = :P_VNUMERODOC");
				parametros.addValue("P_VNUMERODOC", vnumero);
				infoPersona = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros,
						BeanPropertyRowMapper.newInstance(Persona.class));
				break;

			case Constantes.tipoPersonaJuridica:
				break;
			}

		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
		}
		return infoPersona;
	}

	@Override
	public boolean guardarPrePersona(PrePersona prePersona) {
		String sql = "";
		boolean respuesta = false;
		try {
          sql=
        	" INSERT INTO "+Constantes.tablaPrePersona+" ( \n" +
            "   NTIPO_PERSONA,       \n"+
            "   VRUC,                \n" +
            "   VRAZON_SOCIAL,       \n"+
            "   VNOMBRE, 		     \n"+
            "   VAPEPATERNO, 		 \n"+
            "   VAPEMATERNO, 		 \n"+
            "   NTIPODOCFK, 		 \n"+
            "   VNUMERODOC, 		 \n"+
            "   VDIRECCION, 		 \n"+
            "   VCORREO, 			 \n"+
            "   VCODIGOACTIVACION,   \n"+
            "   VTELEFONO) 		     \n"+
            " VALUES ( 				 \n"+
            "  :P_NTIPO_PERSONA,     \n"+
            "  :P_VRUC,              \n"+
            "  :P_VRAZON_SOCIAL,     \n"+
            "  :P_VNOMBRE, 			 \n"+
            "  :P_VAPEPATERNO, 		 \n"+
            "  :P_VAPEMATERNO, 		 \n"+
            "  :P_NTIPODOCFK, 		 \n"+
            "  :P_VNUMERODOC, 		 \n"+
            "  :P_VDIRECCION, 		 \n"+
            "  :P_VCORREO, 		     \n"+
            "  :P_VCODIGOACTIVACION, \n"+
            "  :P_VTELEFONO) ";
          MapSqlParameterSource parametros = new MapSqlParameterSource();
          parametros.addValue("P_NTIPO_PERSONA", prePersona.getNTIPO_PERSONA());
          parametros.addValue("P_VRUC", prePersona.getVRUC());
          parametros.addValue("P_VRAZON_SOCIAL", prePersona.getVRAZON_SOCIAL());
          parametros.addValue("P_VNOMBRE", prePersona.getVNOMBRE());
          parametros.addValue("P_VAPEPATERNO", prePersona.getVAPEPATERNO());
          parametros.addValue("P_VAPEMATERNO", prePersona.getVAPEMATERNO());
          parametros.addValue("P_NTIPODOCFK", prePersona.getNTIPODOCFK());
          parametros.addValue("P_VNUMERODOC", prePersona.getVNUMERODOC());
          parametros.addValue("P_VDIRECCION", prePersona.getVDIRECCION());
          parametros.addValue("P_VCORREO", prePersona.getVCORREO());
          parametros.addValue("P_VCODIGOACTIVACION", prePersona.getVCODIGOACTIVACION());
          parametros.addValue("P_VTELEFONO", prePersona.getVTELEFONO());
          KeyHolder keyHolder = new GeneratedKeyHolder();
          namedParameterJdbcTemplate.update(sql, parametros, keyHolder,new String[] {"NIDPREPERSONAPK"}); 
          logger.info("++"+keyHolder.getKey().longValue()); 
          respuesta = true;
		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
			respuesta= false;
		}
		return respuesta;
	}

	@Override
	public boolean activarRegistroPrePersona(String codigoActivacion) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean confirmacionCodigoActivacion(String codigoActivacion) {
		boolean respuesta = false;
		StringBuffer sqlConsultar = new StringBuffer();
		PrePersona  prePersona = new PrePersona();
		String sqlInserccionPersona = "";
		String sqlInserccionPersonaNatural = "";
		String sqlInserccionPersonaJuridica = "";
		Long idPersona = 0L;
		try {
			sqlConsultar.append(
			   " SELECT "+
			   "    NTIPO_PERSONA, \n"+
			   "    VRUC, \n"+
			   "    VRAZON_SOCIAL, \n"+
			   "    VNOMBRE, \n"+
			   "    VAPEPATERNO, \n"+
			   "    VAPEMATERNO, \n"+
			   "    NTIPODOCFK, \n"+
			   "    VNUMERODOC, \n"+
			   "    VDIRECCION, \n"+
			   "    VCORREO, \n"+
			   "    VTELEFONO, \n"+
			   "    NESTADO, \n"+
			   "    VCODIGOACTIVACION FROM "+Constantes.tablaPrePersona+" \n"+
			   " WHERE  VCODIGOACTIVACION= :P_VCODIGOACTIVACION");
			MapSqlParameterSource parametrosConsulta = new MapSqlParameterSource();
			parametrosConsulta.addValue("P_VCODIGOACTIVACION", codigoActivacion);
			prePersona = namedParameterJdbcTemplate.queryForObject(sqlConsultar.toString(), parametrosConsulta,BeanPropertyRowMapper.newInstance(PrePersona.class));
			
			 //INSERTAMOS EN PERSONA 
			  sqlInserccionPersona=
					"INSERT INTO "+Constantes.tablaPersona+" ( \n"+
			        "	 VNOMBRE,	  \n"+
			        "	 VAPEPATERNO,  \n"+
			        "	 VAPEMATERNO,  \n"+
			        "	 NTIPODOCFK,   \n"+
			        "	 VNUMERODOC ,  \n"+
			        "	 VDIRECCION ,  \n"+
			        "	 VCORREO ,     \n"+
			        "	 VTELEFONO)    \n" +
			        " VALUES( \n"+
			        "	:P_VNOMBRE,     \n"+
			        "	:P_VAPEPATERNO, \n"+
			        "	:P_VAPEMATERNO, \n"+
			        "	:P_NTIPODOCFK,  \n"+
			        "	:P_VNUMERODOC,  \n"+
			        "	:P_VDIRECCION,  \n"+
			        "	:P_VCORREO,     \n"+
			        "	:P_VTELEFONO)";
			  MapSqlParameterSource parametrosPersona = new MapSqlParameterSource();
			  parametrosPersona.addValue("P_VNOMBRE", prePersona.getVNOMBRE());
			  parametrosPersona.addValue("P_VAPEPATERNO", prePersona.getVAPEPATERNO());
			  parametrosPersona.addValue("P_VAPEMATERNO", prePersona.getVAPEMATERNO());
			  parametrosPersona.addValue("P_NTIPODOCFK", prePersona.getNTIPODOCFK());
			  parametrosPersona.addValue("P_VNUMERODOC", prePersona.getVNUMERODOC());
			  parametrosPersona.addValue("P_VDIRECCION", prePersona.getVDIRECCION());
			  parametrosPersona.addValue("P_VCORREO", prePersona.getVCORREO());
			  parametrosPersona.addValue("P_VTELEFONO", prePersona.getVTELEFONO()); 
			  KeyHolder  keyHolder = new GeneratedKeyHolder();
			  namedParameterJdbcTemplate.update(sqlInserccionPersona, parametrosPersona,keyHolder, new String[] {"NIDPERSONAPK"});
			  idPersona = keyHolder.getKey().longValue();
			  logger.info("++"+keyHolder.getKey().longValue());
			  
			switch(prePersona.getNTIPO_PERSONA()) {
			  case Constantes.tipoPersonaNatural:
				 
				  //INSERTAMOS EN PERSONA NATURA
				  sqlInserccionPersonaNatural=
				    " INSERT INTO "+Constantes.tablaPersonaNatural+" ( \n"+
				    "   NIDPERSONAFK) \n"+
				    " VALUES ( \n"+
				    "   :P_NIDPERSONAFK)";
				  MapSqlParameterSource parametrosPersonaNatural =new  MapSqlParameterSource();
				  parametrosPersonaNatural.addValue("P_NIDPERSONAFK", idPersona);
				  KeyHolder keyHolder2 = new GeneratedKeyHolder();
				  namedParameterJdbcTemplate.update(sqlInserccionPersonaNatural, parametrosPersonaNatural,keyHolder2, new String[] {"NIDPERNATURALPK"});
				  logger.info("++"+keyHolder2.getKey().longValue());
				  break;
				  
				  
			  case 	  Constantes.tipoPersonaJuridica: 
				  //INSERTAMOS EN PERSONA NATURA
				  sqlInserccionPersonaJuridica = 
				  " INSERT INTO "+Constantes.tablaPersonaJuridica+" ( \n"+
				  "   NIDPERSONAFK, \n"+
				  "   VRAZONSOCIAL, \n"+
				  "   VRUC, \n"+
				  "   VDIRECCION ) \n"+
				  " VALUES ( \n"+
				  "  :P_NIDPERSONAFK, \n"+
				  "  :P_VRAZONSOCIAL, \n"+
				  "  :P_VRUC, \n"+
				  "  :P_VDIRECCION )";
				  MapSqlParameterSource parametrosPersonaJuridica =new  MapSqlParameterSource();
				  parametrosPersonaJuridica.addValue("P_NIDPERSONAFK", idPersona);
				  parametrosPersonaJuridica.addValue("P_VRAZONSOCIAL", prePersona.getVRAZON_SOCIAL());
				  parametrosPersonaJuridica.addValue("P_VRUC", prePersona.getVRUC());
				  parametrosPersonaJuridica.addValue("P_VDIRECCION", prePersona.getVDIRECCION());
				  KeyHolder keyHolder3 = new GeneratedKeyHolder();
				  namedParameterJdbcTemplate.update(sqlInserccionPersonaJuridica, parametrosPersonaJuridica,keyHolder3, new String[] {"NIDPERJURIDICAPK"});
				  logger.info("++"+keyHolder3.getKey().longValue());
				  break;
			   default:
				   
			}
			
			// SI TODO OK - ACTUALIZAMOS EL CODIGO DE VALIDACION
			 
			respuesta = true;
			
		} catch (Exception e) { 
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
			respuesta = false;
		}
		return respuesta;
	}

	@Override
	public Persona busquedaSolicitante(Expediente expediente) {
		StringBuffer sql  = new StringBuffer();
		Persona persona = new Persona(); 
		try {
			sql.append(
			    "SELECT  \n"+
	    		" T2.NIDPERSONAPK, \n"+
	    		" T2.VNOMBRE, \n"+
	    		" T2.VAPEPATERNO, \n"+
	    		" T2.VAPEMATERNO, \n"+
	    		" T2.NTIPODOCFK, \n"+
	    		" T2.VNUMERODOC, \n"+
	    		" T2.VCORREO, \n"+
	    		" T2.VDIRECCION ");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			if(expediente.getTIPODOCUMENTOBUSCAR()==Constantes.tipoPersonaNatural) {
				sql.append(
				 "  FROM "+Constantes.tablaPersonaNatural+" T1 INNER JOIN "+Constantes.tablaPersona+" T2 ON T1.NIDPERSONAFK=T2.NIDPERSONAPK \n"+
				 " WHERE T2.VNUMERODOC= :P_VNUMERODOC AND T2.NESTADO= :P_NESTADO");
				parametros.addValue("P_VNUMERODOC", expediente.getCAJABUSQUEDA());
				parametros.addValue("P_NESTADO", Constantes.estadoActivado);
				persona=namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Persona.class));
				 
			}else if(expediente.getTIPODOCUMENTOBUSCAR()==Constantes.tipoPersonaJuridica) {
				sql.append(
				"  ,T1.VRUC, \n"+
				"  T1.VRAZONSOCIAL \n"+
				" FROM "+Constantes.tablaPersonaJuridica+" T1 INNER JOIN "+Constantes.tablaPersona+" T2 ON T1.NIDPERSONAFK=T2.NIDPERSONAPK \n"+	
				" WHERE T1.VRUC= :P_VRUC AND T2.NESTADO= :P_NESTADO");
				parametros.addValue("P_VRUC", expediente.getCAJABUSQUEDA());
				parametros.addValue("P_NESTADO", Constantes.estadoActivado);
				persona=namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Persona.class));
				 
			}else {
				persona = null;
			}
			
		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
			persona = null;
		}
		return persona;
	}

	@Override
	@Transactional
	public boolean guardarExpedienteSimple(Expediente expediente) {
		String sql ="";
		Long idExpediente =0L;
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
		parametros.addValue("P_TUPACFK", null);
		parametros.addValue("P_NTIPOPERSONA", expediente.getNTIPOPERSONA());
		parametros.addValue("P_PERSONAFK", expediente.getPERSONAFK());
		//parametros.addValue("P_DFECHATERMINO", expediente.getdf);
		parametros.addValue("P_NDIASPLAZO", Constantes.estadoDesactivado);
		parametros.addValue("P_NESTADODOCUMENTOFK", Constantes.estadoPendiente );
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql.toString(),parametros, keyHolder,new String[] {"NIDEXPEDIENTEPK"} );        
		idExpediente =  keyHolder.getKey().longValue();  	  
		logger.info("NIDEXPEDIENTEPK++"+keyHolder.getKey().longValue()); 
		
		
		// PROCEDEMOS A INSERTAR EL MOVIMIENTO
		 sql="INSERT INTO "+Constantes.tablaMovimiento+" ( \n"+
			     " NIDEXPEDIENTEFK, \n"+
			     " NESTADODOCUMENTOFK, \n"+
			     " OFICINA_ORIGENFK, \n"+ 
			     " DFECHAOFICINA,\n"+  
			     " VOBSERVACION  ) \n"+
		     " VALUES ( \n"+     
			     " :P_NIDEXPEDIENTEFK,    \n"+   
			     " :P_NESTADODOCUMENTOFK, \n"+  
			     " :P_OFICINA_ORIGENFK,   \n"+  
			     " :P_DFECHAOFICINA,      \n"+    
			     " :P_VOBSERVACION     )";
		 
		       MapSqlParameterSource parametros2 = new MapSqlParameterSource();
		       parametros2.addValue("P_NIDEXPEDIENTEFK", idExpediente);
		       parametros2.addValue("P_NESTADODOCUMENTOFK", Constantes.EstadoDocumentoRegistrado);
		       parametros2.addValue("P_OFICINA_ORIGENFK",Constantes.OficinaMesaPartePk); 
		       parametros2.addValue("P_DFECHAOFICINA", Fechas.fechaActual()); 
		       parametros2.addValue("P_VOBSERVACION", expediente.getVASUNTO()); 
		       KeyHolder keyHolder2= new GeneratedKeyHolder();
		       namedParameterJdbcTemplate.update(sql, parametros2,keyHolder2, new String[] {"NIDMOVIMIENTOPK"});
		       logger.info("NIDMOVIMIENTOPK++"+keyHolder2.getKey().longValue());
		
		respuesta =true;
		} catch (Exception e) {
			logger.error("ERROR : " + e.getMessage() + "---" + e.getClass());
			respuesta =false;
		}
		return respuesta;
	}

}
