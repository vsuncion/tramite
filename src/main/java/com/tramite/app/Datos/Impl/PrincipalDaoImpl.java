package com.tramite.app.Datos.Impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.jdbc.core.BeanPropertyRowMapper; 
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository; 

import com.tramite.app.Datos.PrincipalDao;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.PersonaJuridica;
import com.tramite.app.Entidades.PrePersona;
import com.tramite.app.Entidades.PreRequisitoTupa;
import com.tramite.app.Entidades.Tupac;
import com.tramite.app.utilitarios.Constantes;
import com.tramite.app.utilitarios.Fechas;

@Repository
public class PrincipalDaoImpl implements PrincipalDao {

	//Logger logger = LoggerFactory.getLogger(getClass());
	private static final Logger logger = Logger.getLogger(PrincipalDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Persona buscarPersona(int tipoPersona, String vnumero) {
		StringBuffer sql = new StringBuffer();
		Persona infoPersona = new Persona();
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		

			switch (tipoPersona) {
			case Constantes.tipoPersonaNatural:

				sql.append(
			    	"SELECT           \n" +
				    "   VNOMBRE,	  \n" +
			    	"   VAPEPATERNO,  \n" + 
				    "	VAPEMATERNO,  \n" +
			    	"	NTIPODOCFK,   \n" +
				    "	VNUMERODOC,   \n" + 
			    	"	VDIRECCION,   \n" +
				    "	VCORREO,      \n"	+
			    	"	VTELEFONO,    \n" +
				    "	NIDPERSONAPK  \n" + 
			    	" FROM " + Constantes.tablaPersona+" \n"+
			    	" WHERE VNUMERODOC = :P_VNUMERODOC");
				parametros.addValue("P_VNUMERODOC", vnumero);
				infoPersona = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros,
						BeanPropertyRowMapper.newInstance(Persona.class));
				break;

			case Constantes.tipoPersonaJuridica:
				break;
			}
 
		return infoPersona;
	}

	@Override
	public boolean guardarPrePersona(PrePersona prePersona) {
		String sql = "";
		boolean respuesta = false;
		
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
          parametros.addValue("P_VNUMERODOC", prePersona.getVNUMERODOC().trim());
          parametros.addValue("P_VDIRECCION", prePersona.getVDIRECCION());
          parametros.addValue("P_VCORREO", prePersona.getVCORREO());
          parametros.addValue("P_VCODIGOACTIVACION", prePersona.getVCODIGOACTIVACION());
          parametros.addValue("P_VTELEFONO", prePersona.getVTELEFONO());
          KeyHolder keyHolder = new GeneratedKeyHolder();
          namedParameterJdbcTemplate.update(sql, parametros, keyHolder,new String[] {"NIDPREPERSONAPK"}); 
          logger.info("++"+keyHolder.getKey().longValue()); 
          respuesta = true;
	 
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
		StringBuffer sqlActualizarEstadoPrepersona= new StringBuffer();
		Long idPersona = 0L;
		
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
			sqlActualizarEstadoPrepersona.append(
					"UPDATE "+Constantes.tablaPrePersona+" SET \n"+
			        " NESTADO = :P_NESTADO \n"+
					"WHERE VCODIGOACTIVACION= :P_VCODIGOACTIVACION");
			 MapSqlParameterSource parametrosactprepersona = new MapSqlParameterSource();
			 parametrosactprepersona.addValue("P_NESTADO", Constantes.estadoActivado);
			 parametrosactprepersona.addValue("P_VCODIGOACTIVACION", codigoActivacion);
			 namedParameterJdbcTemplate.update(sqlActualizarEstadoPrepersona.toString(), parametrosactprepersona);
			respuesta = true;
		 
		return respuesta;
	}

	@Override
	public Persona busquedaSolicitante(Expediente expediente) {
		StringBuffer sql  = new StringBuffer();
		Persona persona = new Persona(); 
		
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
			
 
		return persona;
	}

	@Override 
	public boolean guardarExpedienteSimple(Expediente expediente) {
		String sql ="";
		Long idExpediente =0L;
		StringBuffer sql3 = new StringBuffer();
		StringBuffer sql4 = new StringBuffer();
		boolean respuesta = false;
		
		   sql=
			 "INSERT INTO "+Constantes.tablaExpediente+" ( \n"+
		     "   VCODIGO_EXPEDIENTE,  \n"+
		     "   TIPO_DOCUMENTOFK,  \n"+
		     "   VNUMERODOCUMENTO,  \n"+
		     "   VNUMEROFOLIO,  \n"+
		     "   VASUNTO,  \n"+
		     "   NORIGEN,  \n"+
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
		     "   :P_NORIGEN,  \n"+
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
		parametros.addValue("P_NORIGEN", Constantes.ORIGEN_TIPO_EXTERNO);
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
		       parametros2.addValue("P_OFICINA_ORIGENFK",Constantes.OficinaMesaPartePk); 
		       parametros2.addValue("P_OFICINA_DESTINOFK",Constantes.OficinaMesaPartePk); 
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
	   			parametros3.addValue("P_NOFICINAFK", Constantes.OficinaMesaPartePk);
	   			parametros3.addValue("P_NIDEXPEDIENTEPK", idExpediente);
	   			namedParameterJdbcTemplate.update(sql3.toString(), parametros3);
		   
	   			//AGREGAMOS LOS ARCHIVOS TUPAC
	   			
	   			if(idExpediente>0) {
	   				sql4.append(
   				    "INSERT INTO "+Constantes.tablaArchivosTupa+" \n"+
   					"(EXPEDIENTEFK,TUPAFK,REQUISITOFK,VNOMBRE_ARCHIVO,VUBICACION_ARCHIVO,VEXTENSION) \n"+
   					"SELECT :P_NIDEXPEDIENTEPK,TUPACFK,REQUISITOFK,VNOMBRE_ARCHIVO,VUBICACION_ARCHIVO,VEXTENSION \n"+
   					" FROM "+Constantes.tablaPreRequisitosTupac+" WHERE IDPREEXPEDIENTEFK= :P_IDPREEXPEDIENTEFK  AND NESTADO= :P_NESTADO");
   					MapSqlParameterSource parametros4 = new MapSqlParameterSource();
   					parametros4.addValue("P_NIDEXPEDIENTEPK", idExpediente);
   					parametros4.addValue("P_IDPREEXPEDIENTEFK", expediente.getIDPREEXPEDIENTEPK());
   					parametros4.addValue("P_NESTADO", Constantes.estadoActivado);
   					namedParameterJdbcTemplate.update(sql4.toString(), parametros4);
	   			}
		
		respuesta =true;
		 
		return respuesta;
	}

	@Override
	public Long guardarPreTupac(Expediente expediente) {
		StringBuffer sql = new StringBuffer();
		Long idpretupacexpediente = 0L;
		
			sql.append(
			  "INSERT INTO "+Constantes.tablaPrExpediente+" ( \n"+
			  "   TIPOPERSONAFK,      \n"+
			  "   PERSONAFK,          \n"+
			  "   TUPACFK,            \n"+
			  "   TIPODOCUMENTOFK,    \n"+
			  "   VNUMERODOCUMENTO,   \n"+
			  "   VNUMEROFOLIO,       \n"+
			  "   DFECHADOCUMENTO,    \n"+
			  "   VASUNTO,            \n"+
			  "   VNOMBRE_ARCHIVO,    \n"+
			  "   VUBICACION_ARCHIVO, \n"+
			  "   VEXTENSION          \n"+
			  ") VALUES ( \n"+
			  "   :P_TIPOPERSONAFK,      \n"+
			  "   :P_PERSONAFK,          \n"+
			  "   :P_TUPACFK,            \n"+
			  "   :P_TIPODOCUMENTOFK,    \n"+
			  "   :P_VNUMERODOCUMENTO,   \n"+
			  "   :P_VNUMEROFOLIO,       \n"+
			  "   :P_DFECHADOCUMENTO,    \n"+
			  "   :P_VASUNTO,            \n"+
			  "   :P_VNOMBRE_ARCHIVO,    \n"+
			  "   :P_VUBICACION_ARCHIVO, \n"+
			  "   :P_VEXTENSION)         ");
			
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_TIPOPERSONAFK", expediente.getNTIPOPERSONA());
			parametros.addValue("P_PERSONAFK", expediente.getPERSONAFK());
			parametros.addValue("P_TUPACFK", expediente.getTUPACFK());
			parametros.addValue("P_TIPODOCUMENTOFK", expediente.getTIPO_DOCUMENTOFK());
			parametros.addValue("P_VNUMERODOCUMENTO", expediente.getVNUMERODOCUMENTO());
			parametros.addValue("P_VNUMEROFOLIO", expediente.getVNUMEROFOLIO());
			parametros.addValue("P_DFECHADOCUMENTO", Fechas.fechaActual());
			parametros.addValue("P_VASUNTO", expediente.getVASUNTO());
			parametros.addValue("P_VNOMBRE_ARCHIVO", expediente.getVNOMBRE_ARCHIVO());
			parametros.addValue("P_VUBICACION_ARCHIVO", expediente.getVUBICACION_ARCHIVO());
			parametros.addValue("P_VEXTENSION", expediente.getVEXTENSION());
            KeyHolder  keyHolder = new GeneratedKeyHolder();
			namedParameterJdbcTemplate.update(sql.toString(), parametros,keyHolder,new String[] {"IDPREEXPEDIENTEPK"});
			idpretupacexpediente = keyHolder.getKey().longValue();
 
		return idpretupacexpediente;
	}

	@Override
	public Expediente preTupacExpediente(Long idprexpediente) {
		StringBuffer sql = new StringBuffer();
		Expediente infoExpediente = new Expediente();
		
			sql.append(
				"SELECT \n"+
				"   T1.IDPREEXPEDIENTEPK, \n"+
				"   CASE T1.TIPOPERSONAFK WHEN 1 THEN CONCAT(T2.VAPEPATERNO,' ',T2.VAPEMATERNO,','+T2.VNOMBRE)  \n"+
				"   WHEN 2 THEN T5.VRAZONSOCIAL  END   VREMITENTE, \n"+
				"   T4.VNOMBRE AS VTIPO_DOCUMENTOS, \n"+
				"   T1.TUPACFK, \n"+
				"   T3.VNOMBRE AS VNOMBRETUPAC, \n"+
				"   T1.VNUMERODOCUMENTO, \n"+
				"   T1.VNUMEROFOLIO, \n"+
				"   CONVERT(varchar,T1.DFECHADOCUMENTO,22) AS VDFECREGISTRO, \n"+
				"   T1.VNOMBRE_ARCHIVO, \n"+
				"   T1.VUBICACION_ARCHIVO, \n"+
				"   T1.VEXTENSION, \n"+
				"   T1.TIPODOCUMENTOFK AS TIPO_DOCUMENTOFK, \n"+
				"   T1.TIPOPERSONAFK AS NTIPOPERSONA, \n"+
				"   T1.PERSONAFK, \n"+
				"   T1.VASUNTO \n"+
				" FROM "+Constantes.tablaPrExpediente+"  		T1  \n"+
				"  INNER JOIN "+Constantes.tablaPersona+" 		T2 ON T1.PERSONAFK=T2.NIDPERSONAPK \n"+
				"  INNER JOIN "+Constantes.tablaTupac+"   		T3 ON T1.TUPACFK=T3.TUPACPK \n"+
				"  INNER JOIN "+Constantes.tablaTipoDocumentos+" T4 ON T1.TIPODOCUMENTOFK=T4.NIDTIPODOCUMENTOPK \n"+
				"  LEFT JOIN "+Constantes.tablaPersonaJuridica+" T5 ON T1.PERSONAFK=T5.NIDPERSONAFK \n"+
				" WHERE  T1.IDPREEXPEDIENTEPK= :P_IDPREEXPEDIENTEPK ");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_IDPREEXPEDIENTEPK", idprexpediente);
			infoExpediente=namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Expediente.class));
 
		return infoExpediente;
	}

	@Override
	public boolean guardarPreRequisito(PreRequisitoTupa preRequisitoTupa) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		
			sql.append(
			 "INSERT INTO "+Constantes.tablaPreRequisitosTupac+" ( \n"+
			 "   IDPREEXPEDIENTEFK,  \n"+
			 "   TUPACFK,            \n"+
			 "   REQUISITOFK,        \n"+
			 "   VNOMBRE_ARCHIVO,    \n"+
			 "   VUBICACION_ARCHIVO,  \n"+
			 "   VEXTENSION )        \n"+
			 " VALUES( \n"+
			 "   :P_IDPREEXPEDIENTEFK,  \n"+
			 "   :P_TUPACFK,            \n"+
			 "   :P_REQUISITOFK,        \n"+
			 "   :P_VNOMBRE_ARCHIVO,    \n"+
			 "   :P_VUBICACION_ARCHIVO, \n"+
			 "   :P_VEXTENSION)");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_IDPREEXPEDIENTEFK", preRequisitoTupa.getIDPREEXPEDIENTEFK());
			parametros.addValue("P_TUPACFK", preRequisitoTupa.getTUPACFK());
			parametros.addValue("P_REQUISITOFK", preRequisitoTupa.getREQUISITOFK());
			parametros.addValue("P_VNOMBRE_ARCHIVO", preRequisitoTupa.getVNOMBRE_ARCHIVO());
			parametros.addValue("P_VUBICACION_ARCHIVO", preRequisitoTupa.getVUBICACION_ARCHIVO());
			parametros.addValue("P_VEXTENSION", preRequisitoTupa.getVEXTENSION());
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
			respuesta = true;
		 
		return respuesta;
	}

	@Override
	public List<PreRequisitoTupa> listaPreRequisitos(Long idprexpediente) {
		List<PreRequisitoTupa>  lista = new ArrayList<PreRequisitoTupa>();
		StringBuffer sql = new StringBuffer();
				
		
			sql.append(
			" SELECT \n"+ 
			"  T1.IDPREREQUISITOPK, \n"+
			"  T1.IDPREEXPEDIENTEFK, \n"+
			"  T2.VNOMBRE \n"+
			" FROM "+Constantes.tablaPreRequisitosTupac+" T1  \n"+
			"  INNER JOIN "+Constantes.tablaRequisitos+" T2 ON T1.REQUISITOFK=T2.REQUISITOSTUPACPK \n"+
			" WHERE T1.IDPREEXPEDIENTEFK= :P_IDPREEXPEDIENTEFK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_IDPREEXPEDIENTEFK", idprexpediente);
			lista = namedParameterJdbcTemplate.query(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(PreRequisitoTupa.class));
	 
		return lista;
	}

	@Override
	public List<Tupac> listasTupacRequisitos() {
		StringBuffer sql = new StringBuffer();
		List<Tupac> lista = new ArrayList<Tupac>();
		 
			sql.append(
			" SELECT \n"+
			"  DISTINCT T2.TUPACPK, \n"+
			"  T2.VNOMBRE  \n"+
			" FROM "+Constantes.tablaRequisitosTupac+" T1  \n"+
			"  INNER JOIN "+Constantes.tablaTupac+" T2 ON T1.TUPACFK=T2.TUPACPK  \n"+
			" WHERE T2.NESTADO= :P_NESTADO");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NESTADO", Constantes.estadoActivado);
			lista = namedParameterJdbcTemplate.query(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(Tupac.class));
 
		return lista;
	}

	@Override
	public PreRequisitoTupa infoPreRequisitoTupa(PreRequisitoTupa preRequisitoTupa) {
		StringBuffer sql = new StringBuffer();
		PreRequisitoTupa info = new PreRequisitoTupa();
		
			sql.append(
			 "SELECT \n"+
			 " IDPREREQUISITOPK, \n"+
			 " IDPREEXPEDIENTEFK, \n"+
			 " TUPACFK, \n"+
			 " REQUISITOFK \n"+
			 "FROM "+Constantes.tablaPreRequisitosTupac+" \n"+
			 "WHERE IDPREEXPEDIENTEFK= :P_IDPREEXPEDIENTEFK AND TUPACFK = :P_TUPACFK AND REQUISITOFK= :P_REQUISITOFK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_IDPREEXPEDIENTEFK", preRequisitoTupa.getIDPREEXPEDIENTEFK());
			parametros.addValue("P_TUPACFK", preRequisitoTupa.getTUPACFK());
			parametros.addValue("P_REQUISITOFK", preRequisitoTupa.getREQUISITOFK());
			info = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(PreRequisitoTupa.class));
	 
		return info;
	}

	@Override
	public void guardarDetalleArchivosExpedienteTupa(Expediente formExpediente) {
		/*
		StringBuffer sql = new StringBuffer();
		
			sql.append(
			   "INSERT INTO "+Constantes.tablaArchivosTupa+" \n"+
			   "(EXPEDIENTEFK,TUPAFK,REQUISITOFK,VNOMBRE_ARCHIVO,VUBICACION_ARCHIVO,VEXTENSION) \n"+
			   "SELECT :P_NIDEXPEDIENTEPK ,TUPACFK,REQUISITOFK,VNOMBRE_ARCHIVO,VUBICACION_ARCHIVO,VEXTENSION \n"+
			   " FROM "+Constantes.tablaPreRequisitosTupac+" WHERE IDPREEXPEDIENTEFK= :P_IDPREEXPEDIENTEFK  AND NESTADO= :P_NESTADO");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDEXPEDIENTEPK", formExpediente.getNIDEXPEDIENTEPK());
			parametros.addValue("P_IDPREEXPEDIENTEFK", formExpediente.getIDPREEXPEDIENTEPK());
			parametros.addValue("P_NESTADO", Constantes.estadoActivado);
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
		
			// TODO: handle exception
		}
		*/
	}

	@Override
	public void eliminarArchivoRequerimeinto(Long idprexpediente, Long idrequisito) {
		StringBuffer sql = new StringBuffer();
		
			sql.append("DELETE FROM "+Constantes.tablaPreRequisitosTupac+" WHERE IDPREEXPEDIENTEFK= :P_IDPREEXPEDIENTEFK AND IDPREREQUISITOPK= :P_IDPREREQUISITOPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_IDPREEXPEDIENTEFK", idprexpediente);
			parametros.addValue("P_IDPREREQUISITOPK", idrequisito);
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
		 
		
	}

	@Override
	public PersonaJuridica buscarPersonaJuridicaDuplicada(PrePersona prePersona) {
		StringBuffer sql = new StringBuffer();
		PersonaJuridica personaJuridica = new PersonaJuridica();
		
			sql.append(
			    "SELECT  \n"+
	    		"	NIDPERJURIDICAPK	,  \n"+
	    		"	NIDPERSONAFK		,  \n"+
	    		"	VRAZONSOCIAL		,  \n"+
	    		"	VRUC				,  \n"+
	    		"	VDIRECCION			,  \n"+
	    		"	NESTADO				,  \n"+
	    		"	DFECREGISTRO		,  \n"+
	    		"	NUSUREGISTRA		,  \n"+
	    		"	DFECMODIFICA		,  \n"+
	    		"	NUSUMODIFICA		,  \n"+
	    		"	DFECELIMINA			, \n"+
	    		"	NUSUELIMINA  		\n"+
	    		"FROM  "+Constantes.tablaPersonaJuridica+" \n"+
	    		"WHERE VRUC= :P_VRUC");
			MapSqlParameterSource parametro = new MapSqlParameterSource();
			parametro.addValue("P_VRUC", prePersona.getVRUC());
			personaJuridica = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametro, BeanPropertyRowMapper.newInstance(PersonaJuridica.class));
  
		return personaJuridica;
	}

	@Override
	public PrePersona buscarPrepersona(PrePersona prePersona) {
		StringBuffer sql = new StringBuffer();
		PrePersona infoPrePersona = new PrePersona();
		
			sql.append(
			   " SELECT "+
			   "	NIDPREPERSONAPK, \n"+
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
			   " WHERE  VCODIGOACTIVACION= :P_VCODIGOACTIVACION AND NESTADO= :P_NESTADO");
				MapSqlParameterSource parametrosConsulta = new MapSqlParameterSource();
				parametrosConsulta.addValue("P_VCODIGOACTIVACION", prePersona.getVCODIGOACTIVACION());
				parametrosConsulta.addValue("P_NESTADO", Constantes.estadoDesactivado);
				infoPrePersona = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametrosConsulta,BeanPropertyRowMapper.newInstance(PrePersona.class));
	 
		return infoPrePersona;
	}

}
