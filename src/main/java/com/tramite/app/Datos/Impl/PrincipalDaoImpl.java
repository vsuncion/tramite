package com.tramite.app.Datos.Impl;

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

import com.tramite.app.Datos.PrincipalDao;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.PrePersona;
import com.tramite.app.utilitarios.Constantes;

@Repository
public class PrincipalDaoImpl implements PrincipalDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

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
			// TODO: handle exception
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
			respuesta = false;
		}
		return respuesta;
	}

}
