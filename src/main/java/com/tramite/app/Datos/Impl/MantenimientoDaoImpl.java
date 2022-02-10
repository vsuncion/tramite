package com.tramite.app.Datos.Impl;

import java.util.ArrayList; 
import java.util.List; 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository; 
import com.tramite.app.Datos.MantenimientoDao;
import com.tramite.app.Entidades.Correlativo;
import com.tramite.app.Entidades.EstadoDocumento;
import com.tramite.app.Entidades.Feriados;
import com.tramite.app.Entidades.Informacion;
import com.tramite.app.Entidades.Oficinas;
import com.tramite.app.Entidades.Perfiles;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.Profesiones;
import com.tramite.app.Entidades.Requisitos;
import com.tramite.app.Entidades.RequisitosTupac;
import com.tramite.app.Entidades.TipoDocumentos;
import com.tramite.app.Entidades.TipoTramite;
import com.tramite.app.Entidades.Trabajadores;
import com.tramite.app.Entidades.Tupac;
import com.tramite.app.Entidades.Usuarios;
import com.tramite.app.utilitarios.Constantes;
import com.tramite.app.utilitarios.Fechas; 

@Repository
public class MantenimientoDaoImpl implements MantenimientoDao {

	//Logger logger = LoggerFactory.getLogger(getClass());
	private static final Logger logger = Logger.getLogger(MantenimientoDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
 
	@Override
	public List<Trabajadores> listarTrabajadores() {
		StringBuilder stringBuilder = new StringBuilder();
		List<Trabajadores> lsTrabajadores = new ArrayList<>();
		
			stringBuilder.append("SELECT * FROM "+Constantes.tablaTrabajadores);
			lsTrabajadores = namedParameterJdbcTemplate.query(stringBuilder.toString(),BeanPropertyRowMapper.newInstance(Trabajadores.class));
	 
		return lsTrabajadores;
	}

	@Override
	public List<Trabajadores> buscarPorNombre(String criterio) {
		final StringBuffer sql = new StringBuffer();
		
			sql.append("");
		 
		return null;
	}
	  
		       
	@Override 
	public boolean registrarTrabajador(Trabajadores trabajador) { 
		 boolean respuesta=false;
		 
		return respuesta;
	}

	@Override 
	public Trabajadores actualizarTrabajador(Trabajadores trabajador) {
		StringBuilder stringBuilder = new StringBuilder();
		Trabajadores nuevoTrabajador = new Trabajadores();
		
			
			// INSERTAMOS 
			stringBuilder.append("");
			
			// CONSULTAMOS
			
	 
		return nuevoTrabajador;
	}

	@Override
	public boolean eliminarTrabajador(Long codigo) {
		StringBuilder stringBuilder = new StringBuilder();
		 boolean respuesta=false;
		
			stringBuilder.append("");
	 
		return respuesta;
	}

	@Override
	public Informacion informacionMunicipalidad() {
		StringBuilder stringBuilder = new StringBuilder();
		 Informacion  informacion = new Informacion();
		
		 
			 stringBuilder.append(
				  " SELECT "+
			      "   RAZONSOCIAL,  \n"+
			      "   RUC,  \n"+
			      "   TITULOALTERNO,  \n"+
			      "   SIGLAS,  \n"+
			      "   DIRECCION,  \n"+
			      "   PAGINAWEB,  \n"+
			      "   CORREO,  \n"+
			      "   TELEFONO  \n"+
	              " FROM "+Constantes.tablaInformacion);
			 informacion=jdbcTemplate.queryForObject(stringBuilder.toString(),BeanPropertyRowMapper.newInstance(Informacion.class));
			 
		return informacion;
	}

	@Override
	public List<Oficinas> listarOficinas() {
		StringBuilder stringBuilder = new StringBuilder();
		List<Oficinas> listarOficinas = new ArrayList<Oficinas>();
		 
			 stringBuilder.append(
			  " SELECT "+
			  "    ROW_NUMBER() OVER (ORDER BY NIDOFICINAPK) AS NITEM ,  \n"+
		      "    NIDOFICINAPK,  \n"+
		      "    VNOMBRE,  \n"+
		      "    VSIGLA,  \n"+
		      "    VSIGLADOCUMENTO,  \n"+
		      "    VDESCRIPCION,  \n"+
		      "    DFECREGISTRO,  \n"+
		      "	   NESTADO  \n"+ 
		      " FROM "+Constantes.tablaOficinas+" ORDER BY VNOMBRE ASC");
			 listarOficinas=namedParameterJdbcTemplate.query(stringBuilder.toString(),BeanPropertyRowMapper.newInstance(Oficinas.class));
		 
		return listarOficinas;
	}

	@Override 
	public boolean guardarOficina(Oficinas oficina) {
		boolean respuesta = false;
		String sql = "";
		StringBuffer sqlcorrelativo = new StringBuffer();
		Long idOficina = 0L;
		 
			 sql=
			   "INSERT INTO "+Constantes.tablaOficinas+"(\n"+
			      "VNOMBRE,  \n"+
			      "VSIGLA,  \n"+
			      "VSIGLADOCUMENTO,  \n"+
			      "NIDPADRE,  \n"+
			      "VDESCRIPCION )  \n"+
			  " VALUES(:P_VNOMBRE, :P_SIGLA, :P_SIGLADOCUMENTO, :P_NIDPADRE, :P_VDESCRIPCION )";
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_VNOMBRE", oficina.getVNOMBRE());
			 parametros.addValue("P_SIGLA", oficina.getVSIGLA());
			 parametros.addValue("P_SIGLADOCUMENTO", oficina.getVSIGLADOCUMENTO());
			 parametros.addValue("P_NIDPADRE", oficina.getNIDPADRE());
			 parametros.addValue("P_VDESCRIPCION", oficina.getVDESCRIPCION()); 
			 KeyHolder keyHolder = new GeneratedKeyHolder();
			 namedParameterJdbcTemplate.update(sql,parametros,keyHolder, new String[] {"NIDOFICINAPK"});
			 logger.info("++"+keyHolder.getKey().longValue()); 
			 idOficina = keyHolder.getKey().longValue();
			 
			 //INSERTAMOS EL CORRELATIVO DE LA OFICINA
			 sqlcorrelativo.append(
			    "INSERT INTO  "+Constantes.tablaCorrelativos+" (NOFICINAFK,NVALOR_ACTUAL) \n"+
			    "VALUES(:P_NOFICINAFK, :P_NVALOR_ACTUAL)");
			 MapSqlParameterSource parametros2 = new MapSqlParameterSource();
			 parametros2.addValue("P_NOFICINAFK", idOficina);
			 parametros2.addValue("P_NVALOR_ACTUAL", Constantes.valorCorrelativoInicial);
			 namedParameterJdbcTemplate.update(sqlcorrelativo.toString(), parametros2);
			 
			 respuesta = true;
		 
		return respuesta;
	}

	@Override
	public List<Oficinas> buscarOficinas(Oficinas oficinas) {
		final StringBuffer sql = new StringBuffer();
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		List<Oficinas> listarOficinas = new ArrayList<Oficinas>();
		 
			 sql.append(
			  "SELECT "+
			  "   ROW_NUMBER() OVER (ORDER BY NIDOFICINAPK) AS NITEM ,  \n"+
		      "   NIDOFICINAPK,  \n"+
		      "   VNOMBRE,  \n"+
		      "   VSIGLA,  \n"+
		      "   VSIGLADOCUMENTO,  \n"+
		      "   VDESCRIPCION,  \n"+
		      "   DFECREGISTRO,  \n"+
		      "   NESTADO  \n"+ 
		      " FROM "+Constantes.tablaOficinas+" WHERE 1=1");
			 if(oficinas.getCAJABUSQUEDA() !=null && oficinas.getCAJABUSQUEDA().trim().length()>0) {
				 sql.append(" AND VNOMBRE LIKE :P_VNOMBRE");
				 parametros.addValue("P_VNOMBRE", "%"+oficinas.getCAJABUSQUEDA()+"%");
			 } 
			 
			 listarOficinas=namedParameterJdbcTemplate.query(sql.toString(),parametros,BeanPropertyRowMapper.newInstance(Oficinas.class));
		 
		return listarOficinas;
	}

	@Override
	public Oficinas buscarOficinaId(Long id) {
		final StringBuffer sql = new StringBuffer();
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		 Oficinas  oficina = new Oficinas();
		
			 sql.append(
			  " SELECT "+
		      "   NIDOFICINAPK,  \n"+
		      "   VNOMBRE,  \n"+
		      "   VSIGLA,  \n"+
		      "   VSIGLADOCUMENTO,  \n"+
		      "   VDESCRIPCION,  \n"+
		      "   DFECREGISTRO,  \n"+
		      "   NIDPADRE,  \n"+ 
		      "   NESTADO \n"+ 
		      " FROM "+Constantes.tablaOficinas+" WHERE NIDOFICINAPK=:codigo");
			 parametros.addValue("codigo", id);
			 oficina = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Oficinas.class));
		 
		return oficina;
	}

	@Override 
	public boolean actualizarOficina(Oficinas oficina) {
		boolean respuesta =false; 
		final StringBuffer sql = new StringBuffer();
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		
			sql.append(
				"UPDATE "+Constantes.tablaOficinas+
				 " SET   VNOMBRE		 = :P_VNOMBRE,          \n"+
				 "       VSIGLA			 = :P_VSIGLA,           \n"+
				 "       VSIGLADOCUMENTO = :P_VSIGLADOCUMENTO,  \n"+
				 "       VDESCRIPCION	 = :P_VDESCRIPCION,     \n"+ 
				 "       NESTADO	 	 = :P_NESTADO,          \n"+ 
				 "       NIDPADRE	 	 = :P_NIDPADRE,         \n"+ 
				 "       DFECMODIFICA	 = :P_DFECMODIFICA      \n"+
				 " WHERE NIDOFICINAPK	 = :P_NIDOFICINAPK       ");
				 
 			 parametros.addValue("P_NIDOFICINAPK", oficina.getNIDOFICINAPK());
			 parametros.addValue("P_VNOMBRE", oficina.getVNOMBRE());
			 parametros.addValue("P_VSIGLA", oficina.getVSIGLA());
			 parametros.addValue("P_VSIGLADOCUMENTO", oficina.getVSIGLADOCUMENTO());
			 parametros.addValue("P_VDESCRIPCION", oficina.getVDESCRIPCION()); 
			 parametros.addValue("P_NESTADO", oficina.getNESTADO()); 
			 parametros.addValue("P_NIDPADRE", oficina.getNIDPADRE()); 
			 parametros.addValue("P_DFECMODIFICA", Fechas.fechaActual());
			 namedParameterJdbcTemplate.update(sql.toString(), parametros);
			 respuesta = true;
		 
		return respuesta;
	}

	@Override
	public boolean eliminarOficina(Long id) {
		boolean respuesta =false; 
		final StringBuffer sql = new StringBuffer();
		MapSqlParameterSource parametros = new MapSqlParameterSource();
		
			sql.append(
					"UPDATE "+Constantes.tablaOficinas+
					 " SET   NESTADO		 = :P_NESTADO,     \n"+ 
					 "       DFECELIMINA	 = :P_DFECELIMINA  \n"+
					 " WHERE NIDOFICINAPK	 = :P_NIDOFICINAPK ");
			 parametros.addValue("P_NIDOFICINAPK", id);
			 parametros.addValue("P_NESTADO", Constantes.estadoDesactivado);
			 parametros.addValue("P_DFECELIMINA",  Fechas.fechaActual());
			 namedParameterJdbcTemplate.update(sql.toString(), parametros);
			 respuesta =true;
		 
		return respuesta;
	}

	@Override
	public List<TipoDocumentos> listarTipoDocumento() {
		final StringBuffer sql = new StringBuffer();
		 List<TipoDocumentos> listaTipoDocumento = new ArrayList<TipoDocumentos>();
		
			sql.append(
				"SELECT \n"+
				   "ROW_NUMBER() OVER (ORDER BY NIDTIPODOCUMENTOPK) AS NITEM ,  \n"+
			       "NIDTIPODOCUMENTOPK, \n"+
			       "VNOMBRE, \n"+
			       "VALIAS, \n"+
			       "VDESCRIPCION, \n"+
			       "NESTADO, \n"+
			       "DFECREGISTRO \n"+
			    " FROM "+Constantes.tablaTipoDocumentos);
			listaTipoDocumento = namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(TipoDocumentos.class));
	 
		return listaTipoDocumento;
	}

	@Override 
	public boolean guardarTipoDocumentos(TipoDocumentos tipoDocumentos) {
		boolean respuesta = false;
		String sql = "";
		 
			     sql = 
						"INSERT INTO "+Constantes.tablaTipoDocumentos+"(\n"+
					     "  VNOMBRE, \n"+
						 "  VALIAS,  \n"+
						 "  VDESCRIPCION ) \n"+
						 " VALUES(:P_VNOMBRE, :P_VALIAS, :P_VDESCRIPCION)";
					MapSqlParameterSource parametros = new MapSqlParameterSource();
					parametros.addValue("P_VNOMBRE", tipoDocumentos.getVNOMBRE());
					parametros.addValue("P_VALIAS", tipoDocumentos.getVALIAS());
					parametros.addValue("P_VDESCRIPCION", tipoDocumentos.getVDESCRIPCION()); 
					KeyHolder keyHolder = new GeneratedKeyHolder();
					namedParameterJdbcTemplate.update(sql, parametros,keyHolder, new String[] {"NIDTIPODOCUMENTOPK"});
					logger.info("=== CODIGO ="+keyHolder.getKey().longValue());
					respuesta = true;
	 
		return respuesta;
	}

	@Override
	public List<TipoDocumentos> buscarTipoDocumentos(TipoDocumentos tipoDocumentos) {
		List<TipoDocumentos> buscarTipoDocumentos = new ArrayList<TipoDocumentos>();
		StringBuffer sql = new StringBuffer(); 
		
			sql.append(
					"SELECT \n"+
					  " ROW_NUMBER() OVER (ORDER BY NIDTIPODOCUMENTOPK) AS NITEM ,  \n"+
				       "NIDTIPODOCUMENTOPK, \n"+
				       "VNOMBRE, \n"+
				       "VALIAS, \n"+
				       "VDESCRIPCION, \n"+
				       "NESTADO, \n"+
				       "DFECREGISTRO \n"+
				    "  FROM "+Constantes.tablaTipoDocumentos+" \n"+
				    " WHERE 1=1");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			if(tipoDocumentos.getCAJABUSQUEDA()!=null && tipoDocumentos.getCAJABUSQUEDA().trim().length()>0) {
				sql.append(" AND VNOMBRE LIKE :P_VNOMBRE");
				parametros.addValue("P_VNOMBRE","%"+tipoDocumentos.getCAJABUSQUEDA()+"%");
			}
			
			buscarTipoDocumentos = namedParameterJdbcTemplate.query(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(TipoDocumentos.class));
	 
		return buscarTipoDocumentos;
	}

	@Override
	public boolean actualizarTipoDocumento(TipoDocumentos tipoDocumentos) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		
			 sql.append(
					  "UPDATE \n"+
				      "  "+Constantes.tablaTipoDocumentos+" SET \n"+ 
				       "VNOMBRE= :P_VNOMBRE, \n"+
				       "VALIAS= :P_VALIAS, \n"+
				       "VDESCRIPCION= :P_VDESCRIPCION, \n"+
				       "NESTADO= :P_NESTADO, \n"+
				       "DFECMODIFICA= :P_DFECMODIFICA \n"+ 
				      "WHERE NIDTIPODOCUMENTOPK= :P_NIDTIPODOCUMENTOPK"
				 ); 
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_NIDTIPODOCUMENTOPK", tipoDocumentos.getNIDTIPODOCUMENTOPK());
			 parametros.addValue("P_VNOMBRE", tipoDocumentos.getVNOMBRE());
			 parametros.addValue("P_VALIAS", tipoDocumentos.getVALIAS());
			 parametros.addValue("P_VDESCRIPCION", tipoDocumentos.getVDESCRIPCION());
			 parametros.addValue("P_NESTADO", tipoDocumentos.getNESTADO());
			 parametros.addValue("P_DFECMODIFICA", Fechas.fechaActual());
			 namedParameterJdbcTemplate.update(sql.toString(), parametros);
			 respuesta = true;
	 
		return respuesta;
	}

	@Override
	public boolean eliminarTipoDocumento(Long id) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		 
			 sql.append(
				  "UPDATE \n"+
			      "  "+Constantes.tablaTipoDocumentos+" SET \n"+
				  " NESTADO= :P_NESTADO "+
			      "WHERE NIDTIPODOCUMENTOPK= :P_NIDTIPODOCUMENTOPK"
			 ); 
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_NIDTIPODOCUMENTOPK",id);
			 parametros.addValue("P_NESTADO", Constantes.estadoDesactivado);
			 namedParameterJdbcTemplate.update(sql.toString(), parametros);
			 respuesta = true;
	 
		return respuesta;
	}

	@Override
	public TipoDocumentos buscarTipoDocumentoId(Long id) {
		StringBuffer sql = new StringBuffer();
		 TipoDocumentos tipoDocumentos = new TipoDocumentos();
		 
			 sql.append(
						"SELECT \n"+
					       "NIDTIPODOCUMENTOPK, \n"+
					       "VNOMBRE, \n"+
					       "VALIAS, \n"+
					       "VDESCRIPCION, \n"+
					       "NESTADO, \n"+
					       "DFECREGISTRO \n"+
					    "  FROM "+Constantes.tablaTipoDocumentos+" \n"+
					    " WHERE NIDTIPODOCUMENTOPK= :P_NIDTIPODOCUMENTOPK");
		 MapSqlParameterSource parametros = new MapSqlParameterSource();
		 parametros.addValue("P_NIDTIPODOCUMENTOPK", id);
		 tipoDocumentos=namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros,  BeanPropertyRowMapper.newInstance(TipoDocumentos.class));
	 
		return tipoDocumentos;
	}

	@Override
	public List<TipoTramite> listarTipoTramite() {
		StringBuffer sql = new StringBuffer();
		List<TipoTramite> listar = new ArrayList<TipoTramite>();
		
			sql.append(
				"SELECT "+
				" ROW_NUMBER() OVER (ORDER BY IDTIPOTRAMITEPK) AS NITEM ,  \n"+
			    "  IDTIPOTRAMITEPK, \n" + 
			    "  VNOMBRE, \n" + 
			    "  VDESCRIPCION, \n" + 
			    "  NESTADO, \n" +   
			    "  DFECREGISTRO   "+
			    " FROM "+Constantes.tablaTipoTramite
		        );
			listar = namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(TipoTramite.class));
	 
		return listar;
	}

	@Override
	public List<TipoTramite> buscarTipoTramite(TipoTramite tipoTramite) {
		List<TipoTramite> listarTipoTramite = new ArrayList<TipoTramite>();
		StringBuffer sql = new StringBuffer();
		
			sql.append(
					"SELECT "+
					" ROW_NUMBER() OVER (ORDER BY IDTIPOTRAMITEPK) AS NITEM ,  \n"+
				    "    IDTIPOTRAMITEPK, \n" + 
				    "    VNOMBRE, \n" + 
				    "    VDESCRIPCION, \n" + 
				    "    NESTADO, \n" +   
				    "    DFECREGISTRO   "+
				    "  FROM "+Constantes.tablaTipoTramite+" \n"+
				    " WHERE 1=1 "
			        );
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			 if(tipoTramite.getCAJABUSQUEDA()!=null && tipoTramite.getCAJABUSQUEDA().trim().length()>0) {
				 sql.append(" AND VNOMBRE LIKE :P_VNOMBRE");
				 parametros.addValue("P_VNOMBRE","%"+tipoTramite.getCAJABUSQUEDA()+"%");
			 }
			 
			 listarTipoTramite = namedParameterJdbcTemplate.query(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(TipoTramite.class));
	 
		return listarTipoTramite;
	}

	@Override
	public TipoTramite buscarTipoTramiteId(Long id) {
		StringBuffer sql = new StringBuffer();
		TipoTramite tipoTramite = new TipoTramite();
		 
			 sql.append(
					"SELECT "+
				    "    IDTIPOTRAMITEPK, \n" + 
				    "    VNOMBRE, \n" + 
				    "    VDESCRIPCION, \n" + 
				    "    NESTADO, \n" +   
				    "    DFECREGISTRO   "+
				    "  FROM "+Constantes.tablaTipoTramite+" \n"+
				    " WHERE  IDTIPOTRAMITEPK = :P_IDTIPOTRAMITEPK"
			        );
				MapSqlParameterSource parametros = new MapSqlParameterSource();
				parametros.addValue("P_IDTIPOTRAMITEPK", id);
				tipoTramite = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(TipoTramite.class));
	 
		 
		return  tipoTramite;
	}

	 
	@Override
	public boolean eliminarTipoTramite(Long id) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		
			sql.append(
			  "UPDATE "+Constantes.tablaTipoTramite+" SET \n"+
		      "  NESTADO="+Constantes.estadoDesactivado+" \n"+
			  " WHERE IDTIPOTRAMITEPK= :P_IDTIPOTRAMITEPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_IDTIPOTRAMITEPK", id);
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
			respuesta = true;
	 
		return respuesta;
	}

	 
	@Override
	public boolean actualizarTipoTramite(TipoTramite tipoTramite) {
		String sql ="";
		boolean respuesta = false;
		 
			 sql =
			   "UPDATE "+Constantes.tablaTipoTramite+" SET \n"+
			   "  VNOMBRE      = :P_VNOMBRE, \n" + 
			   "  VDESCRIPCION = :P_VDESCRIPCION, \n"+
			   "  NESTADO      = :P_NESTADO, \n" +   
			   "  DFECMODIFICA = :P_DFECMODIFICA \n"+
			   " WHERE IDTIPOTRAMITEPK= :P_IDTIPOTRAMITEPK";
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_IDTIPOTRAMITEPK", tipoTramite.getIDTIPOTRAMITEPK());
			 parametros.addValue("P_VNOMBRE", tipoTramite.getVNOMBRE());
			 parametros.addValue("P_NESTADO", tipoTramite.getNESTADO());
			 parametros.addValue("P_VDESCRIPCION", tipoTramite.getVDESCRIPCION());
			 parametros.addValue("P_DFECMODIFICA", Fechas.fechaActual()); 
			 namedParameterJdbcTemplate.update(sql, parametros); 
			 respuesta = true;
	 
		return respuesta;
	}

	 
	@Override
	public boolean guardarTipoTramite(TipoTramite tipoTramite) {
		boolean respuesta = false;
		String sql ="";
		
			sql=
			   " INSERT INTO "+Constantes.tablaTipoTramite+" ( \n"+
			   "   VNOMBRE, \n" + 
			   "   VDESCRIPCION) \n" + 
			   "  VALUES( :P_VNOMBRE, :P_VDESCRIPCION)";
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_VNOMBRE", tipoTramite.getVNOMBRE());
			parametros.addValue("P_VDESCRIPCION", tipoTramite.getVDESCRIPCION());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			namedParameterJdbcTemplate.update(sql.toString(), parametros,keyHolder , new String[] {"IDTIPOTRAMITEPK"});
			respuesta = true;
 
		return respuesta;
	}

	@Override
	public List<EstadoDocumento> listarEstadoDocumento() {
		StringBuffer sql = new StringBuffer();
		List<EstadoDocumento>  lista = new ArrayList<EstadoDocumento>();
		
			sql.append(
				"SELECT "+
				"  ROW_NUMBER() OVER (ORDER BY IDESTADOCUMENTOPK) AS NITEM ,  \n"+
			    "  IDESTADOCUMENTOPK,"+
			    "  VNOMBRE,          "+
			    "  VDESCRIPCION,     "+
			    "  NESTADO,           "+
			    "  DFECREGISTRO "+
			    " FROM "+Constantes.tablaEstadoDocumento);
			lista = namedParameterJdbcTemplate.query(sql.toString(),BeanPropertyRowMapper.newInstance(EstadoDocumento.class));
 
		return lista;
	}

	@Override
	public List<EstadoDocumento> buscarEstadoDocumento(EstadoDocumento estadoDocumento) {
		StringBuffer sql = new StringBuffer();
		List<EstadoDocumento>  lista = new ArrayList<EstadoDocumento>();
		
			sql.append(
				"SELECT "+
				" ROW_NUMBER() OVER (ORDER BY IDESTADOCUMENTOPK) AS NITEM ,  \n"+
			    "    IDESTADOCUMENTOPK,"+
			    "    VNOMBRE,          "+
			    "    VDESCRIPCION,     "+
			    "    NESTADO,           "+
			    "    DFECREGISTRO "+
			    "  FROM "+Constantes.tablaEstadoDocumento+
			    " WHERE 1=1 ");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			if(estadoDocumento.getCAJABUSQUEDA()!=null && estadoDocumento.getCAJABUSQUEDA().trim().length()>0) {
				sql.append(" AND VNOMBRE LIKE :P_VNOMBRE");
				parametros.addValue("P_VNOMBRE","%"+estadoDocumento.getCAJABUSQUEDA()+"%");
			}
			lista = namedParameterJdbcTemplate.query(sql.toString(),parametros,BeanPropertyRowMapper.newInstance(EstadoDocumento.class));
		
	 
		return lista;
	}

	@Override
	public List<Profesiones> listarProfesiones() {
		StringBuffer sql = new StringBuffer();
		List<Profesiones> lista = new ArrayList<Profesiones>();
		
			sql.append(
				"SELECT "+
					" ROW_NUMBER() OVER (ORDER BY NIDPROFESIONPK) AS NITEM ,  \n"+
			       " NIDPROFESIONPK," +
			       " VNOMBRE," +
			       " VDESCRIPCION," +
			       " NESTADO," +
			       " DFECREGISTRO" +
			       " FROM " +Constantes.tablaProfesion + " \n"+
			       "ORDER BY VNOMBRE ASC");
			lista=namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(Profesiones.class));
	 
		return lista;
	}

	@Override
	public List<Profesiones> buscarProfesiones(Profesiones profesiones) {
		StringBuffer sql =new StringBuffer();
		List<Profesiones> lista = new ArrayList<Profesiones>();
		
		
			sql.append(
					" SELECT "+
				    " ROW_NUMBER() OVER (ORDER BY NIDPROFESIONPK) AS NITEM ,  \n"+
				    "   NIDPROFESIONPK," +
				    "   VNOMBRE," +
				    "   VDESCRIPCION," +
				    "   NESTADO," +
				    "   DFECREGISTRO" +
				    " FROM " +Constantes.tablaProfesion+
				    " WHERE 1=1 ");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			if(profesiones.getCAJABUSQUEDA()!=null && profesiones.getCAJABUSQUEDA().trim().length()>0) {
				sql.append(" AND VNOMBRE LIKE :P_VNOMBRE");
				parametros.addValue("P_VNOMBRE", "%"+profesiones.getCAJABUSQUEDA()+"%");
			}
			lista=namedParameterJdbcTemplate.query(sql.toString(),parametros,BeanPropertyRowMapper.newInstance(Profesiones.class));
	 
		return lista;
	}

	@Override
	public Profesiones buscarProfesionesId(Long id) {
		StringBuffer sql =new StringBuffer();
		Profesiones profesiones = new Profesiones();
		 
			 sql.append(
						" SELECT "+
					    "   NIDPROFESIONPK," +
					    "   VNOMBRE," +
					    "   VDESCRIPCION," +
					    "   NESTADO," +
					    "   DFECREGISTRO" +
					    " FROM " +Constantes.tablaProfesion+
					    " WHERE NIDPROFESIONPK =  :P_NIDPROFESIONPK");
				MapSqlParameterSource parametros = new MapSqlParameterSource();
				parametros.addValue("P_NIDPROFESIONPK", id);
				profesiones = namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Profesiones.class));
	 
		return profesiones;
	}

	@Override
	public boolean guardarProfesiones(Profesiones profesiones) {
		boolean respuesta = false;
		String sql ="";
		 
			 sql=
			  "INSERT INTO "+Constantes.tablaProfesion+" ( \n"+
			  "  VNOMBRE, \n"+
			  "  VDESCRIPCION) \n"+
			  " VALUES(:P_VNOMBRE,:P_VDESCRIPCION)";
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_VNOMBRE",  profesiones.getVNOMBRE());
			 parametros.addValue("P_VDESCRIPCION",  profesiones.getVDESCRIPCION());
			 KeyHolder keyHolder = new GeneratedKeyHolder();
			 namedParameterJdbcTemplate.update(sql, parametros,keyHolder, new String[] {"NIDPROFESIONPK"} );
			 logger.info("==="+keyHolder.getKey().longValue());
			 respuesta = true;
	 
		return respuesta;
	}

	@Override
	public boolean actualizarProfesiones(Profesiones profesiones) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		
			sql.append(
				" UPDATE "+Constantes.tablaProfesion+" SET \n"+
			    "    VNOMBRE = :P_VNOMBRE, \n"+
				"    VDESCRIPCION = :P_VDESCRIPCION, \n"+
				"    NESTADO = :P_NESTADO \n"+
			    " WHERE NIDPROFESIONPK = :P_NIDPROFESIONPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDPROFESIONPK", profesiones.getNIDPROFESIONPK());
			parametros.addValue("P_VNOMBRE", profesiones.getVNOMBRE());
			parametros.addValue("P_VDESCRIPCION", profesiones.getVDESCRIPCION());
			parametros.addValue("P_NESTADO", profesiones.getNESTADO());
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
			respuesta = true;
	 
		return respuesta;
	}

	@Override
	public boolean eliminarProfesiones(Long id) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		
			sql.append(
				" UPDATE "+Constantes.tablaProfesion+" SET \n"+
			    "    NESTADO = :P_NESTADO"+ 
			    " WHERE NIDPROFESIONPK = :P_NIDPROFESIONPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDPROFESIONPK",id);
			parametros.addValue("P_NESTADO",Constantes.estadoDesactivado);
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
			respuesta = true;
 
		return respuesta;
	}

	@Override
	public boolean actualizarinformacionMunicipalidad(Informacion informacion) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		 
			sql.append(
			   " UPDATE "+Constantes.tablaInformacion+" SET \n"+
			   "   RAZONSOCIAL = :P_RAZONSOCIAL,  \n"+
			   "   RUC = :P_RUC, \n"+
			   "   TITULOALTERNO  = :P_TITULOALTERNO, \n"+
			   "   SIGLAS = :P_SIGLAS, \n"+
			   "   DIRECCION  = :P_DIRECCION, \n"+
			   "   PAGINAWEB = :P_PAGINAWEB, \n"+
			   "   CORREO = :P_CORREO, \n"+
			   "   TELEFONO = :P_TELEFONO");
			MapSqlParameterSource parametros = new MapSqlParameterSource(); 
			parametros.addValue("P_RAZONSOCIAL", informacion.getRAZONSOCIAL());
			parametros.addValue("P_RUC", informacion.getRUC());
			parametros.addValue("P_TITULOALTERNO", informacion.getTITULOALTERNO());
			parametros.addValue("P_SIGLAS", informacion.getSIGLAS());
			parametros.addValue("P_DIRECCION", informacion.getDIRECCION());
			parametros.addValue("P_PAGINAWEB", informacion.getPAGINAWEB());
			parametros.addValue("P_CORREO", informacion.getCORREO());
			parametros.addValue("P_TELEFONO", informacion.getTELEFONO());
	          namedParameterJdbcTemplate.update(sql.toString(), parametros);
	          respuesta = true;
 
		return respuesta;
	}

	@Override
	public List<Requisitos> listarRequisitos() {
		StringBuffer sql = new StringBuffer();
		 List<Requisitos> lista = new ArrayList<Requisitos>();
		 
			 sql.append(
				 " SELECT "+
				 "   ROW_NUMBER() OVER (ORDER BY REQUISITOSTUPACPK) AS NITEM ,  \n"+
				 "   REQUISITOSTUPACPK, \n"+
				 "   VNOMBRE, \n"+
				 "   VDESCRIPCION, \n"+
				 "   NESTADO, \n"+ 
				 "   DFECREGISTRO, \n"+
				 "   NUSUREGISTRA, \n"+
				 "   DFECMODIFICA, \n"+
				 "   NUSUMODIFICA, \n"+
				 "   DFECELIMINA, \n"+
				 "   NUSUELIMINA \n"+
				 " FROM "+Constantes.tablaRequisitos);
			 lista= namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(Requisitos.class));
 
		return lista;
	}

	@Override
	public List<Requisitos> buscarRequisitos(Requisitos requisitos) {
		StringBuffer sql = new StringBuffer();
		 List<Requisitos> lista = new ArrayList<Requisitos>();
		 
			 sql.append(
					 " SELECT \n"+
					 "    ROW_NUMBER() OVER (ORDER BY REQUISITOSTUPACPK) AS NITEM ,  \n"+
					 "    REQUISITOSTUPACPK, \n"+
					 "    VNOMBRE, \n"+
					 "    VDESCRIPCION, \n"+
					 "    NESTADO, \n"+ 
					 "    DFECREGISTRO, \n"+
					 "    NUSUREGISTRA, \n"+
					 "    DFECMODIFICA, \n"+
					 "    NUSUMODIFICA, \n"+
					 "    DFECELIMINA, \n"+
					 "    NUSUELIMINA \n"+
					 "  FROM "+Constantes.tablaRequisitos+" \n"+
					 " WHERE 1=1 ");
			 
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 if(requisitos.getCAJABUSQUEDA()!=null && requisitos.getCAJABUSQUEDA().trim().length()>0) {
				 sql.append(" AND VNOMBRE LIKE :P_VNOMBRE");
				 parametros.addValue("P_VNOMBRE","%"+requisitos.getCAJABUSQUEDA()+"%");
			 }
			 lista=namedParameterJdbcTemplate.query(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(Requisitos.class));
	 
		return lista;
	}

	@Override
	public Requisitos buscarRequisitosId(Long id) {
		StringBuffer sql = new StringBuffer();
		Requisitos requisitos = new Requisitos();
		 
			 
			 sql.append(
					 " SELECT "+
					 "    REQUISITOSTUPACPK, \n"+
					 "    VNOMBRE, \n"+
					 "    VDESCRIPCION, \n"+
					 "    NESTADO, \n"+ 
					 "    DFECREGISTRO, \n"+
					 "    NUSUREGISTRA, \n"+
					 "    DFECMODIFICA, \n"+
					 "    NUSUMODIFICA, \n"+
					 "    DFECELIMINA, \n"+
					 "    NUSUELIMINA \n"+
					 "  FROM "+Constantes.tablaRequisitos+" \n"+
					 " WHERE REQUISITOSTUPACPK = :P_REQUISITOSTUPACPK");
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_REQUISITOSTUPACPK", id);
			 requisitos=namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Requisitos.class));
 
		return requisitos;
	}

	@Override
	public boolean guardarRequisitos(Requisitos requisitos) {
		boolean respuesta = false;
		String sql ="";
		 
			 sql=" INSERT INTO "+Constantes.tablaRequisitos+" ( \n"+ 
				 "    VNOMBRE, \n"+
				 "    VDESCRIPCION) \n"+  
				 " VALUES(:P_VNOMBRE, :P_VDESCRIPCION)";
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_VNOMBRE", requisitos.getVNOMBRE());
			 parametros.addValue("P_VDESCRIPCION", requisitos.getVDESCRIPCION());
			 KeyHolder keyHolder = new GeneratedKeyHolder();
			 namedParameterJdbcTemplate.update(sql, parametros,keyHolder, new String[] {"NIDOFICINAPK"}); 
			 respuesta = true; 
			 logger.info("++"+keyHolder.getKey().longValue());
 
		return respuesta;
	}

	@Override
	public boolean actualizarRequisitos(Requisitos requisitos) {
		boolean respuesta = false;
		StringBuffer sql = new StringBuffer();
		 
			 sql.append(
					 " UPDATE "+Constantes.tablaRequisitos+" SET \n"+ 
					 "    VNOMBRE = :P_VNOMBRE, \n"+
					 "    VDESCRIPCION = :P_VDESCRIPCION, \n"+
					 "    NESTADO = :P_NESTADO, \n"+
					 "    DFECMODIFICA = :P_DFECMODIFICA \n"+
					 " WHERE REQUISITOSTUPACPK = :P_REQUISITOSTUPACPK");
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_REQUISITOSTUPACPK", requisitos.getREQUISITOSTUPACPK());
			 parametros.addValue("P_VNOMBRE", requisitos.getVNOMBRE());
			 parametros.addValue("P_VDESCRIPCION", requisitos.getVDESCRIPCION());
			 parametros.addValue("P_NESTADO", requisitos.getNESTADO());
			 parametros.addValue("P_DFECMODIFICA", Fechas.fechaActual());
			 
			 namedParameterJdbcTemplate.update(sql.toString(), parametros);
			 respuesta = true;
 
		return respuesta;
	}

	@Override
	public boolean eliminarRequisitos(Long id) {
		boolean respuesta = false;
		StringBuffer sql = new StringBuffer();
		 
			 sql.append(
					 " UPDATE "+Constantes.tablaRequisitos+" SET \n"+  
					 "    NESTADO = :P_NESTADO, \n"+
					 "    DFECELIMINA = :P_DFECELIMINA \n"+
					 " WHERE REQUISITOSTUPACPK = :P_REQUISITOSTUPACPK");
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_REQUISITOSTUPACPK", id); 
			 parametros.addValue("P_NESTADO", Constantes.estadoDesactivado);
			 parametros.addValue("P_DFECELIMINA", Fechas.fechaActual());
			 
			 namedParameterJdbcTemplate.update(sql.toString(), parametros);
			 respuesta = true;
 
		return respuesta;
	}

	@Override
	public List<Tupac> listarTupac() {
		StringBuffer sql = new StringBuffer();
		List<Tupac> lista = new ArrayList<Tupac>();
		
			sql.append(
			   " SELECT T1.TUPACPK AS TUPACPK,"+ 
			   "    ROW_NUMBER() OVER (ORDER BY T1.TUPACPK) AS NITEM ,  \n"+
			   " 	T1.VNOMBRE AS VNOMBRE,"+ 
			   " 	T1.NTIPODIAS AS NTIPODIAS,"+ 
			   "    CASE T1.NTIPODIAS  WHEN 1 THEN 'DIAS LABORABLES' WHEN 2 THEN 'DIAS CALENDARIO' END AS VTIPODIAS, "+
			   " 	T1.NDIASPLAZO AS NDIASPLAZO,"+ 
			   " 	T1.NESTADO AS NESTADO,"+ 
			   "    T1.VDESCRIPCION AS VDESCRIPCION, "+
			   "    T1.DFECREGISTRO AS DFECREGISTRO, "+
			   " 	T2.VNOMBRE AS VNOMBREOFICINA " +
			   " FROM  "+Constantes.tablaTupac+" T1 LEFT JOIN "+Constantes.tablaOficinas+" T2 ON T1.OFICINAFK=T2.NIDOFICINAPK " 
			);

			lista=namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(Tupac.class));
 
		return lista;
	}

	@Override
	public List<Tupac> buscarTupac(Tupac tupac) {
		StringBuffer sql = new StringBuffer();
		List<Tupac> lista = new ArrayList<Tupac>();
		
			sql.append(
			   " SELECT \n"+
			   "    ROW_NUMBER() OVER (ORDER BY T1.TUPACPK) AS NITEM ,  \n"+
			   "    T1.TUPACPK AS TUPACPK,"+ 
			   " 	T1.VNOMBRE AS VNOMBRE,"+ 
			   " 	T1.NTIPODIAS AS NTIPODIAS,"+ 
			   " 	T1.NDIASPLAZO AS NDIASPLAZO,"+ 
			   " 	T1.NESTADO AS NESTADO,"+ 
			   " 	T2.VNOMBRE AS VNOMBREOFICINA "+ 
			   " FROM  "+Constantes.tablaTupac+" T1 LEFT JOIN "+Constantes.tablaOficinas+" T2 ON T1.OFICINAFK=T2.NIDOFICINAPK " +
			   " WHERE 1=1 ");

			MapSqlParameterSource parametros = new MapSqlParameterSource();
            if(tupac.getCAJABUSQUEDA()!=null && tupac.getCAJABUSQUEDA().trim().length()>0) {
            	sql.append(" AND T1.VNOMBRE LIKE :P_VNOMBRE");
            	parametros.addValue("P_VNOMBRE", "%"+tupac.getCAJABUSQUEDA()+"%");
            }
            
            lista=namedParameterJdbcTemplate.query(sql.toString(),parametros, BeanPropertyRowMapper.newInstance(Tupac.class));
 
		return lista;
	}

	@Override
	public Tupac buscarTupacPorId(Long id) {
		StringBuffer sql = new StringBuffer();
		 Tupac  tupac = new Tupac(); 
		 
		
			sql.append(
				   " SELECT T1.TUPACPK AS TUPACPK,"+ 
				   " 	T1.VNOMBRE AS VNOMBRE,"+ 
				   " 	T1.NTIPODIAS AS NTIPODIAS,"+ 
				   " 	T1.NDIASPLAZO AS NDIASPLAZO,"+ 
				   " 	T1.VDESCRIPCION AS VDESCRIPCION,"+ 
				   " 	T1.NESTADO AS NESTADO,"+ 
				   " 	T1.OFICINAFK AS OFICINAFK,"+ 
				   " 	T2.VNOMBRE AS VNOMBREOFICINA "+ 
				   " FROM  "+Constantes.tablaTupac+" T1 LEFT JOIN "+Constantes.tablaOficinas+" T2 ON T1.OFICINAFK=T2.NIDOFICINAPK " +
				   " WHERE T1.TUPACPK = :P_TUPACPK");
			
			 MapSqlParameterSource parametros = new MapSqlParameterSource();  
             parametros.addValue("P_TUPACPK",id);
            tupac=namedParameterJdbcTemplate.queryForObject(sql.toString(),parametros, BeanPropertyRowMapper.newInstance(Tupac.class));
  
		return tupac;
	}

	@Override
	public boolean guardarTupac(Tupac tupac) {
		String sql ="";
		boolean respuesta = false;
		
			 sql =
				"INSERT INTO "+Constantes.tablaTupac+" ( \n"+
				"	 VNOMBRE, 		\n" +
				"	 VDESCRIPCION,  \n" +
				"	 NDIASPLAZO, 	\n" +
				"	 NTIPODIAS, 	\n" +
				"	 OFICINAFK )    \n" +
				" VALUES ( \n" +
				"    :P_VNOMBRE, 	 \n"+
				"    :P_VDESCRIPCION, \n"+
				"    :P_NDIASPLAZO, 	 \n"+
				"    :P_NTIPODIAS, 	 \n"+
				"    :P_OFICINAFK)";
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_VNOMBRE", tupac.getVNOMBRE());
			parametros.addValue("P_VDESCRIPCION", tupac.getVDESCRIPCION());
			parametros.addValue("P_NDIASPLAZO", tupac.getNDIASPLAZO());
			parametros.addValue("P_NTIPODIAS", tupac.getNTIPODIAS());
			parametros.addValue("P_OFICINAFK", tupac.getOFICINAFK()); 
			KeyHolder keyHolder = new GeneratedKeyHolder();
			namedParameterJdbcTemplate.update(sql, parametros,keyHolder,new String[] {"TUPACPK"});
			respuesta =true;
			logger.info("++"+keyHolder.getKey().longValue());
 
		return respuesta;
	}

	@Override 
	public boolean actualizarTupac(Tupac tupac) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		
			sql.append(
			   " UPDATE "+Constantes.tablaTupac+" SET \n"+
			   "    VNOMBRE      = :P_VNOMBRE, 	  \n" +
			   "    VDESCRIPCION = :P_VDESCRIPCION, \n" +
			   "    NDIASPLAZO   = :P_NDIASPLAZO,   \n" +
			   "    NTIPODIAS    = :P_NTIPODIAS, 	  \n" +
			   "    OFICINAFK    = :P_OFICINAFK,     \n" +
			   "    NESTADO   	 = :P_NESTADO,     \n" +
			   "    DFECMODIFICA = :P_DFECMODIFICA     \n" +
			   " WHERE  TUPACPK = :P_TUPACPK");
			MapSqlParameterSource parametros =  new MapSqlParameterSource();
			parametros.addValue("P_TUPACPK", tupac.getTUPACPK());
			parametros.addValue("P_VNOMBRE", tupac.getVNOMBRE());
			parametros.addValue("P_VDESCRIPCION", tupac.getVDESCRIPCION());
			parametros.addValue("P_NDIASPLAZO", tupac.getNDIASPLAZO());
			parametros.addValue("P_NTIPODIAS", tupac.getNTIPODIAS());
			parametros.addValue("P_OFICINAFK", tupac.getOFICINAFK());
			parametros.addValue("P_NESTADO", tupac.getNESTADO());
			parametros.addValue("P_DFECMODIFICA", Fechas.fechaActual());
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
			respuesta = true;
	 
		return respuesta;
	}

	@Override 
	public boolean eliminarTupac(Long id) {
		boolean respuesta = false;
		StringBuffer sql = new StringBuffer();
		
			sql.append(
			  " UPDATE "+Constantes.tablaTupac+" SET \n"+
			  "    NESTADO          = :P_NESTADO, \n" +
			  "    DFECMODIFICA     = :P_DFECMODIFICA" +
			  " WHERE  TUPACPK = :P_TUPACPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();		
			parametros.addValue("P_TUPACPK", id);
			parametros.addValue("P_NESTADO", Constantes.estadoDesactivado);
			parametros.addValue("P_DFECMODIFICA", Fechas.fechaActual());
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
			respuesta =true;
 
		return respuesta;
	}

	@Override
	public List<RequisitosTupac> listarRequisitosTupacPorIdTupac(Long id) {
		StringBuffer sql = new StringBuffer();
		List<RequisitosTupac> lista = new ArrayList<RequisitosTupac>();
		
			 sql.append(
			   " SELECT "+
			   "    ROW_NUMBER() OVER (ORDER BY T1.NREQTUPACPK) AS NITEM ,  \n"+
			   "    T1.NREQTUPACPK, \n"+
			   "    T2.VNOMBRE, \n"+
			   "    T2.VDESCRIPCION, \n "+
			   "    T1.DFECREGISTRO, \n "+
			   "    T1.TUPACFK, \n "+
			   "    T1.NESTADO, \n "+
			   "    CASE T1.NESTADO WHEN 1 THEN 'ACTIVO' ELSE 'INACTIVO' END VESTADO FROM "+Constantes.tablaRequisitosTupac+ " T1 \n"+
			   "  INNER JOIN "+Constantes.tablaRequisitos+" T2 ON T1.REQUISITOSFK=T2.REQUISITOSTUPACPK \n"+
			   " WHERE T1.TUPACFK= :P_TUPACFK");
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_TUPACFK", id);
			 lista = namedParameterJdbcTemplate.query(sql.toString(), parametros,BeanPropertyRowMapper.newInstance(RequisitosTupac.class));
	 
		return lista;
	}

	@Override 
	public boolean guardarRequisitosTupac(RequisitosTupac requisitosTupac) {
		String sql ="";
		boolean respuesta = false;
		
			sql=
			  " INSERT INTO "+Constantes.tablaRequisitosTupac+" ( \n"+
			  "   TUPACFK, \n"+
			  "   REQUISITOSFK ) \n"+
			  " VALUES(:P_TUPACFK,:P_REQUISITOSFK)";
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_TUPACFK", requisitosTupac.getTUPACFK());
			parametros.addValue("P_REQUISITOSFK", requisitosTupac.getREQUISITOSFK());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			namedParameterJdbcTemplate.update(sql, parametros,keyHolder, new String[] {"NREQTUPACPK"});
			logger.info("++"+keyHolder.getKey().longValue());	
			respuesta = true;
 
		return respuesta;
	}

	@Override 
	public boolean activarRequisitosTupac(Long id) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		
			sql.append(
			   " UPDATE "+Constantes.tablaRequisitosTupac+" SET \n"+
			   "    NESTADO= :P_NESTADO, \n" +
			   "    DFECMODIFICA= :P_DFECMODIFICA \n" +
			   " WHERE NREQTUPACPK= :P_NREQTUPACPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NREQTUPACPK", id);
			parametros.addValue("P_NESTADO", Constantes.estadoActivado);
			parametros.addValue("P_DFECMODIFICA", Fechas.fechaActual());
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
			respuesta = true;
 
		return respuesta;
	}

	@Override 
	public boolean eliminarRequisitosTupac(Long id) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		
			sql.append(
			   " UPDATE "+Constantes.tablaRequisitosTupac+" SET \n"+
			   "    NESTADO= :P_NESTADO, \n" +
			   "    DFECMODIFICA= :P_DFECMODIFICA \n" +
			   " WHERE NREQTUPACPK= :P_NREQTUPACPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NREQTUPACPK", id);
			parametros.addValue("P_NESTADO", Constantes.estadoDesactivado);
			parametros.addValue("P_DFECMODIFICA", Fechas.fechaActual());
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
			respuesta = true;
 
		return respuesta;
	}

	@Override
	public RequisitosTupac buscarPorTupacRequisitos(Long idTupac, Long idRequerimiento) {
		 StringBuffer sql = new StringBuffer();
		 RequisitosTupac requisitosTupac = new  RequisitosTupac();
		 List<RequisitosTupac> listaRequisitosTupac = new  ArrayList<RequisitosTupac>();
		 
			 sql.append(
					   " SELECT "+
					   "    T1.NREQTUPACPK, \n"+
					   "    T2.VNOMBRE, \n"+
					   "    T2.VDESCRIPCION, \n "+
					   "    T1.DFECREGISTRO, \n "+
					   "    T1.TUPACFK, \n "+
					   "    T1.NESTADO, \n "+
					   "    CASE T1.NESTADO WHEN 1 THEN 'ACTIVO' ELSE 'INACTIVO' END VESTADO FROM "+Constantes.tablaRequisitosTupac+ " T1 \n"+
					   "  INNER JOIN "+Constantes.tablaRequisitos+" T2 ON T1.REQUISITOSFK=T2.REQUISITOSTUPACPK \n"+
					   " WHERE T1.TUPACFK= :P_TUPACFK AND T1.REQUISITOSFK= :P_REQUISITOSFK");
			 MapSqlParameterSource parametros = new MapSqlParameterSource();
			 parametros.addValue("P_TUPACFK", idTupac);
			 parametros.addValue("P_REQUISITOSFK", idRequerimiento);

			// requisitosTupac=namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(RequisitosTupac.class));
			 listaRequisitosTupac = namedParameterJdbcTemplate.query(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(RequisitosTupac.class));
			 if(listaRequisitosTupac.size()>0) {
				 requisitosTupac = listaRequisitosTupac.get(0);
			 }
 
		return requisitosTupac;
	}

	@Override
	public List<Persona> listarTrabajadorPersona() {
		List<Persona>  listaPersonas = new ArrayList<Persona>();
		StringBuffer sql = new StringBuffer();
		
			sql.append(
			 " SELECT \n"+ 
			 "    ROW_NUMBER() OVER (ORDER BY T1.NIDPERSONAPK) AS NITEM ,  \n"+
			 "    T1.NIDPERSONAPK, \n"+ 
			 "	  CONCAT(T1.VAPEPATERNO,' ',T1.VAPEMATERNO,' ',T1.VNOMBRE) AS NOMBRE_COMPLETO, \n"+ 
			 "	  CASE T1.NTIPODOCFK WHEN 1 THEN 'DNI'  WHEN 2 THEN 'CARNET EXTRANJERIA' END VTIPO_DOC_REGISTRO, \n"+ 
			 "	  T1.VNUMERODOC,\n"+ 
			 "	  T2.DFECINGRESO,\n"+ 
			 "	  T2.NIDTRABAJADORPK,\n"+ 
			 "    CASE T2.NESTADO WHEN 1 THEN 'ACTIVO' WHEN 0 THEN 'INACTIVO' END VESTADO, \n"+
			 "	  T3.VNOMBRE AS VNOMBRE_PROFESION , \n"+ 
			 "	  T4.VNOMBRE AS VNOMBRE_OFICINA, \n"+ 
			 "	  T5.VNOMBRECARGO \n"+ 
			 "  FROM "+Constantes.tablaPersona+" T1 \n"+ 
			 " INNER JOIN "+Constantes.tablaTrabajadores+" T2 ON T1.NIDPERSONAPK=T2.NIDPERSONAFK  \n"+ 
			 " INNER JOIN "+Constantes.tablaProfesion+"    T3 ON T2.NPROFESIONFK=T3.NIDPROFESIONPK \n"+ 
			 " INNER JOIN "+Constantes.tablaOficinas+"     T4 ON T2.NOFICINAFK=T4.NIDOFICINAPK \n"+
			 " LEFT JOIN "+Constantes.tablaCargo+"         T5 ON T2.NCARGOFK=T5.NCARGOPK");
			listaPersonas = namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(Persona.class));
	 
		return listaPersonas;
	}

	@Override
	public List<Persona> buscarTrabajadorPersona(Persona persona) {
		List<Persona>  listaPersonas = new ArrayList<Persona>();
		StringBuffer sql = new StringBuffer();
		
			sql.append(
					 " SELECT \n"+ 
					 " ROW_NUMBER() OVER (ORDER BY T1.NIDPERSONAPK) AS NITEM ,  \n"+
					 "    T1.NIDPERSONAPK, \n"+ 
					 "	  CONCAT(T1.VAPEPATERNO,' ',T1.VAPEMATERNO,' ',T1.VNOMBRE) AS NOMBRE_COMPLETO, \n"+ 
					 "	  CASE T1.NTIPODOCFK WHEN 1 THEN 'DNI'  WHEN 2 THEN 'CARNET EXTRANJERIA' END VTIPO_DOC_REGISTRO, \n"+ 
					 "	  T1.VNUMERODOC,\n"+ 
					 "	  T2.DFECINGRESO,\n"+ 
					 "	  T2.NIDTRABAJADORPK,\n"+ 
					 "    CASE T2.NESTADO WHEN 1 THEN 'ACTIVO' WHEN 0 THEN 'INACTIVO' END VESTADO, \n"+
					 "	  T3.VNOMBRE AS VNOMBRE_PROFESION , \n"+ 
					 "	  T4.VNOMBRE AS VNOMBRE_OFICINA \n"+ 
					 "  FROM PERSONA T1 \n"+ 
					 " INNER JOIN TRABAJADOR T2 ON T1.NIDPERSONAPK=T2.NIDPERSONAFK  \n"+ 
					 " INNER JOIN PROFESION T3 ON T2.NPROFESIONFK=T3.NIDPROFESIONPK \n"+ 
					 " INNER JOIN OFICINA T4 ON T2.NOFICINAFK=T4.NIDOFICINAPK \n");
			
			        MapSqlParameterSource parametros = new MapSqlParameterSource();
			        
			        if(persona.getCAJABUSQUEDA()!=null && persona.getCAJABUSQUEDA().trim().length()>0) {
			        	 if(persona.getNCBTIPOCRITERIO()==0) {
			        		 sql.append(" WHERE T1.VNOMBRE LIKE :P_VNOMBRE");
				        	 parametros.addValue("P_VNOMBRE", "%"+persona.getCAJABUSQUEDA()+"%");
				        	 
				         }else if(persona.getNCBTIPOCRITERIO()==1) {
				        	 sql.append(" WHERE T1.VAPEPATERNO LIKE :P_VAPEPATERNO");
				        	 parametros.addValue("P_VAPEPATERNO","%"+ persona.getCAJABUSQUEDA()+"%");
				        	 
				         }else if(persona.getNCBTIPOCRITERIO()==2) {
				        	 sql.append(" WHERE T1.VAPEMATERNO LIKE :P_VAPEMATERNO");
				        	 parametros.addValue("P_VAPEMATERNO","%"+ persona.getCAJABUSQUEDA()+"%");
				        	 
				         }else if(persona.getNCBTIPOCRITERIO()==3) {
				        	 sql.append(" WHERE T1.VNUMERODOC = :P_VNUMERODOC");
				        	 parametros.addValue("P_VNUMERODOC", persona.getCAJABUSQUEDA());
				         } 
		        	 }
		 
					listaPersonas = namedParameterJdbcTemplate.query(sql.toString(),parametros,BeanPropertyRowMapper.newInstance(Persona.class));
		
	 
		return listaPersonas;
	}

	@Override
	public Persona buscarTrabajadorPersonaPorId(Long id) {
		Persona persona = new Persona();
		StringBuffer sql = new StringBuffer();
		
			sql.append(
			  " SELECT "+
		      "	  T1.VNOMBRE,	      \n"+
	          "	  T1.VAPEPATERNO,     \n"+
	          "	  T1.VAPEMATERNO,     \n"+
	          "	  T1.NTIPODOCFK,      \n"+
	          "	  T1.VNUMERODOC,     \n"+
	          "	  T1.VDIRECCION,     \n"+
	          "	  T1.VCORREO,        \n"+
	          "	  T1.VTELEFONO,        \n"+
	          "	  T2.NIDPERSONAFK,    \n"+
			  "	  T2.NPROFESIONFK,    \n"+
			  "	  T2.NOFICINAFK,       \n"+
			  "	  T1.NIDPERSONAPK,     \n"+
			  "	  T2.NESTADO,  			\n"+
			  "	  T2.NCARGOFK,  		\n"+
			  "	  T2.NIDTRABAJADORPK  \n"+
			  "  FROM "+Constantes.tablaPersona+" T1 INNER JOIN "+Constantes.tablaTrabajadores+" T2 ON T1.NIDPERSONAPK=T2.NIDPERSONAFK \n"+
			  " WHERE T2.NIDTRABAJADORPK= :P_NIDTRABAJADORPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDTRABAJADORPK", id);
			persona=namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Persona.class));
	 
		return persona;
	}

	@Override 
	public boolean guardarTrabajadorPersona(Persona persona) {
		String sql=""; 
		Long idPersona;
		boolean respuesta = false;
		
		
			sql="INSERT INTO "+Constantes.tablaPersona+" ( \n"+
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
			MapSqlParameterSource parametros = new MapSqlParameterSource(); 
			parametros.addValue("P_VNOMBRE" , persona.getVNOMBRE());
			parametros.addValue("P_VAPEPATERNO" , persona.getVAPEPATERNO());
			parametros.addValue("P_VAPEMATERNO" , persona.getVAPEMATERNO());
			parametros.addValue("P_NTIPODOCFK" , persona.getNTIPODOCFK());
			parametros.addValue("P_VNUMERODOC" , persona.getVNUMERODOC());
			parametros.addValue("P_VDIRECCION", persona.getVDIRECCION());
			parametros.addValue("P_VCORREO",persona.getVCORREO());
			parametros.addValue("P_VTELEFONO",persona.getVTELEFONO());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			namedParameterJdbcTemplate.update(sql, parametros,keyHolder, new String[] {"NIDPERSONAPK"}); 
			logger.info("++"+keyHolder.getKey().longValue());
			idPersona = keyHolder.getKey().longValue();
			sql="INSERT INTO "+Constantes.tablaTrabajadores+" ( \n"+
				"	 NIDPERSONAFK,  \n"+
				"	 NPROFESIONFK,  \n"+
			    "	 NOFICINAFK,  \n"+
			    "	 NCARGOFK,  \n"+
				"	 DFECINGRESO)  \n"+
				" VALUES( \n"+
				"	 :P_NIDPERSONAFK,  \n"+
				"	 :P_NPROFESIONFK,  \n"+
			    "	 :P_NOFICINAFK,  \n"+
			    "	 :P_NCARGOFK,  \n"+
				"	 :P_DFECINGRESO)  \n";
			KeyHolder keyHolder2 = new GeneratedKeyHolder(); 
			MapSqlParameterSource parametros2 = new MapSqlParameterSource(); 
			parametros2.addValue("P_NIDPERSONAFK" , idPersona);
			parametros2.addValue("P_NPROFESIONFK" , persona.getNPROFESIONFK());
			parametros2.addValue("P_NOFICINAFK" , persona.getNOFICINAFK());
			parametros2.addValue("P_NCARGOFK" , persona.getNCARGOFK());
			parametros2.addValue("P_DFECINGRESO" , Fechas.fechaActual());
			namedParameterJdbcTemplate.update(sql, parametros2,keyHolder2, new String[] {"NIDTRABAJADORPK"}); 
			logger.info("++"+keyHolder2.getKey().longValue());
			respuesta = true;
 
		return respuesta;
	}

	@Override 
	public boolean actualizarTrabajadorPersona(Persona persona) {
		
		boolean respuesta = false;
		StringBuffer sql =  new StringBuffer();
		StringBuffer sql1 =  new StringBuffer();
		StringBuffer sql2 =  new StringBuffer();
		
		
			sql.append(
				" UPDATE "+Constantes.tablaPersona+" SET \n" +
			    "	  VNOMBRE        = :P_VNOMBRE,     \n"+
		        "	  VAPEPATERNO    = :P_VAPEPATERNO, \n"+
		        "	  VAPEMATERNO    = :P_VAPEMATERNO, \n"+
		        "	  NTIPODOCFK     = :P_NTIPODOCFK,  \n"+
		        "	  VNUMERODOC     = :P_VNUMERODOC,  \n"+
		        "	  VDIRECCION     = :P_VDIRECCION,  \n"+
		        "	  VCORREO        = :P_VCORREO,     \n"+
		        "	  VTELEFONO      = :P_VTELEFONO   \n"+
		        " WHERE NIDPERSONAPK = :P_NIDPERSONAPK ");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDPERSONAPK", persona.getNIDPERSONAPK());
			parametros.addValue("P_VNOMBRE", persona.getVNOMBRE());
			parametros.addValue("P_VAPEPATERNO", persona.getVAPEPATERNO());
			parametros.addValue("P_VAPEMATERNO", persona.getVAPEMATERNO());
			parametros.addValue("P_NTIPODOCFK", persona.getNTIPODOCFK());
			parametros.addValue("P_VNUMERODOC", persona.getVNUMERODOC());
			parametros.addValue("P_VDIRECCION", persona.getVDIRECCION());
			parametros.addValue("P_VCORREO", persona.getVCORREO());
			parametros.addValue("P_VTELEFONO", persona.getVTELEFONO());
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
			
			sql1.append(
			  " UPDATE "+Constantes.tablaTrabajadores+" SET "+
			  "   NPROFESIONFK = :P_NPROFESIONFK, \n"+
			  "   NOFICINAFK   = :P_NOFICINAFK, \n"+
			  "   NCARGOFK     = :P_NCARGOFK, \n"+
			  "   NESTADO      = :P_NESTADO \n"+
			  "  WHERE   NIDTRABAJADORPK= :P_NIDTRABAJADORPK");
			
			MapSqlParameterSource parametros2 = new MapSqlParameterSource();
			parametros2.addValue("P_NIDTRABAJADORPK", persona.getNIDTRABAJADORPK());
			parametros2.addValue("P_NPROFESIONFK", persona.getNPROFESIONFK());
			parametros2.addValue("P_NESTADO", persona.getNESTADO());
			parametros2.addValue("P_NOFICINAFK", persona.getNOFICINAFK());
			parametros2.addValue("P_NCARGOFK" , persona.getNCARGOFK());
			namedParameterJdbcTemplate.update(sql1.toString(), parametros2);
			
			
			//ACTUALIZAMOS EL USUARIO 
			sql2.append(
				"UPDATE "+Constantes.tablaUsuario+" SET \n"+
			    "   VUSUARIO= :P_VUSUARIO \n"+
				" WHERE NTRABAJADORFK= :P_NTRABAJADORFK");
			
			MapSqlParameterSource parametros3 = new MapSqlParameterSource();
			parametros3.addValue("P_VUSUARIO", persona.getVCORREO());
			parametros3.addValue("P_NTRABAJADORFK", persona.getNIDTRABAJADORPK());
			namedParameterJdbcTemplate.update(sql2.toString(), parametros3);
			
			respuesta = true;
 
		return respuesta;
	}

	@Override
	public boolean eliminarTrabajadorPersona(Long id) {
		boolean respuesta = false;
		StringBuffer sql = new StringBuffer();
				
		
			sql.append(
			  " UPDATE "+Constantes.tablaTrabajadores+" SET "+
			  "   NESTADO= :P_NESTADO \n"+ 
			  "  WHERE   NIDTRABAJADORPK= :P_NIDTRABAJADORPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDTRABAJADORPK", id);
			parametros.addValue("P_NESTADO", Constantes.estadoDesactivado);
			namedParameterJdbcTemplate.update(sql.toString(),parametros);
			respuesta = true;
 
		return respuesta;
	}

	@Override
	public List<Usuarios> listarUsuarioPersona() {
		List<Usuarios> lsUsuarioPersona = new ArrayList<Usuarios>();
		StringBuffer sql = new StringBuffer();
		
			sql.append(
			 " SELECT \n"+ 
			 "    ROW_NUMBER() OVER ( ORDER BY T1.NIDTRABAJADORPK )  AS NITEM, \n"+
			 "    T2.VCORREO, \n"+
			 "    CONCAT(T2.VAPEPATERNO,' ',T2.VAPEMATERNO,' ',T2.VNOMBRE) VNOMBRECOMPLETO, \n"+
			 "    T6.VNOMBRE AS VPERFIL, \n"+
			 "    T5.VNOMBRE AS VOFICINA, \n"+
			 "    T3.NIDUSUARIOPK, \n"+
			 "    t4.NIDUSUAPERFILPK AS NIDUSUAPERFILFK, \n"+
			 "    T1.NIDTRABAJADORPK AS NTRABAJADORFK \n"+
			 "  FROM  TRABAJADOR T1  \n"+
			 " INNER JOIN PERSONA T2 ON T1.NIDPERSONAFK=T2.NIDPERSONAPK \n"+
			 " LEFT JOIN  USUARIO T3 ON T1.NIDTRABAJADORPK=T3.NTRABAJADORFK \n"+
			 " LEFT JOIN USUARIO_PERFIL T4 ON T3.NIDUSUARIOPK=T4.NUSUARIOFK \n"+
			 " LEFT JOIN OFICINA T5 ON T3.NOFICINAFK=T5.NIDOFICINAPK \n"+
			 " LEFT JOIN PERFIL T6 ON T4.NPERFILFK=T6.NIDPERFILPK \n");
			lsUsuarioPersona=namedParameterJdbcTemplate.query(sql.toString(),BeanPropertyRowMapper.newInstance(Usuarios.class));
 
		return lsUsuarioPersona;
	}

	@Override
	public List<Usuarios> buscarUsuarioPersona(Usuarios usuarios) {
		List<Usuarios> lsUsuarioPersona = new ArrayList<Usuarios>();
		StringBuffer sql = new StringBuffer();
	 
			
				sql.append(
				 " SELECT \n"+ 
				 "     ROW_NUMBER() OVER ( ORDER BY T1.NIDTRABAJADORPK )  AS NITEM, \n"+
				 "     T2.VCORREO, \n"+
				 "     CONCAT(T2.VAPEPATERNO,' ',T2.VAPEMATERNO,' ',T2.VNOMBRE) VNOMBRECOMPLETO, \n"+
				 "     T6.VNOMBRE AS VPERFIL, \n"+
				 "     T5.VNOMBRE AS VOFICINA, \n"+
				 "     T3.NIDUSUARIOPK, \n"+
				 "     t4.NIDUSUAPERFILPK AS NIDUSUAPERFILFK, \n"+
				 "     T1.NIDTRABAJADORPK AS NTRABAJADORFK \n"+

				 " FROM "+Constantes.tablaTrabajadores+" T1  \n"+
				 "  INNER JOIN "+Constantes.tablaPersona+" T2 ON T1.NIDPERSONAFK=T2.NIDPERSONAPK \n"+
				 "  LEFT JOIN  "+Constantes.tablaUsuario+" T3 ON T1.NIDTRABAJADORPK=T3.NTRABAJADORFK \n"+
				 "  LEFT JOIN "+Constantes.tablaUsuarioPerfil+" T4 ON T3.NIDUSUARIOPK=T4.NUSUARIOFK \n"+
				 "  LEFT JOIN "+Constantes.tablaOficinas+" T5 ON T3.NOFICINAFK=T5.NIDOFICINAPK \n"+
				 "  LEFT JOIN "+Constantes.tablaPerfil+" T6 ON T4.NPERFILFK=T6.NIDPERFILPK ");

				MapSqlParameterSource parametros = new MapSqlParameterSource();
				 if(usuarios.getCAJABUSQUEDA()!=null && usuarios.getCAJABUSQUEDA().trim().length()>0) {
		        	 if(usuarios.getNCBTIPOCRITERIO()==0) {
		        		 sql.append(" WHERE T2.VNOMBRE LIKE :P_VNOMBRE");
			        	 parametros.addValue("P_VNOMBRE", "%"+usuarios.getCAJABUSQUEDA()+"%");
			        	 
			         }else if(usuarios.getNCBTIPOCRITERIO()==1) {
			        	 sql.append(" WHERE T2.VAPEPATERNO LIKE :P_VAPEPATERNO");
			        	 parametros.addValue("P_VAPEPATERNO","%"+ usuarios.getCAJABUSQUEDA()+"%");
			        	 
			         }else if(usuarios.getNCBTIPOCRITERIO()==2) {
			        	 sql.append(" WHERE T2.VAPEMATERNO LIKE :P_VAPEMATERNO");
			        	 parametros.addValue("P_VAPEMATERNO","%"+ usuarios.getCAJABUSQUEDA()+"%");
			        	 
			         }else if(usuarios.getNCBTIPOCRITERIO()==3) {
			        	 sql.append(" WHERE T2.VNUMERODOC = :P_VNUMERODOC");
			        	 parametros.addValue("P_VNUMERODOC", usuarios.getCAJABUSQUEDA());
			         } else if(usuarios.getNCBTIPOCRITERIO()==4) {
						 sql.append(" WHERE T5.VNOMBRE LIKE :P_VNOMBRE_OFICINA");
						 parametros.addValue("P_VNOMBRE_OFICINA", "%"+usuarios.getCAJABUSQUEDA()+"%");
					 }
	        	 }
				
				 lsUsuarioPersona=namedParameterJdbcTemplate.query(sql.toString(),parametros,BeanPropertyRowMapper.newInstance(Usuarios.class));
	 
		return lsUsuarioPersona;
	}

	@Override
	public Usuarios buscarUsuarioPersonaPorId(Long id) {
		StringBuffer sql = new StringBuffer();
		Usuarios usuarios = new Usuarios();
		
			sql.append(
			" SELECT \n"+
			"      ROW_NUMBER() OVER ( ORDER BY T1.NIDTRABAJADORPK )  AS NITEM,\n"+
			"      T2.VCORREO,\n"+
			"      CONCAT(T2.VAPEPATERNO,' ',T2.VAPEMATERNO,' ',T2.VNOMBRE) VNOMBRECOMPLETO,\n"+
			"      T6.VNOMBRE AS VPERFIL,\n"+
			"      T5.VNOMBRE AS VOFICINA,\n"+
			"      T3.NIDUSUARIOPK,\n"+
			"      T1.NIDTRABAJADORPK AS NTRABAJADORFK,\n"+
			"	   T4.NIDUSUAPERFILPK AS NIDUSUAPERFILFK,\n"+
			"	   T3.NOFICINAFK, \n"+
			"	   T4.NPERFILFK, \n"+
			"	   T3.NESTADO \n"+
			" FROM  TRABAJADOR T1   \n"+
			"   INNER JOIN PERSONA T2 ON T1.NIDPERSONAFK=T2.NIDPERSONAPK \n"+
			"   INNER JOIN  USUARIO T3 ON T1.NIDTRABAJADORPK=T3.NTRABAJADORFK \n"+
			"   INNER JOIN USUARIO_PERFIL T4 ON T3.NIDUSUARIOPK=T4.NUSUARIOFK \n"+
			"   INNER JOIN OFICINA T5 ON T3.NOFICINAFK=T5.NIDOFICINAPK \n"+
			"   INNER JOIN PERFIL T6 ON T4.NPERFILFK=T6.NIDPERFILPK \n"+
			" WHERE T3.NIDUSUARIOPK= :P_NIDUSUARIOPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDUSUARIOPK", id);
			usuarios=namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Usuarios.class));
 
		return usuarios;
	}

	@Override
	public boolean guardarUsuarioPersona(Usuarios usuarios) {
		boolean respuesta = false;
		Long idUsuario ;		 
		
			String sql = 
					" INSERT INTO "+Constantes.tablaUsuario+" ( \n"+
				    "   NTRABAJADORFK, \n"+
				    "   NOFICINAFK, \n"+
				    "   VUSUARIO,    \n"+
				    "   VCLAVE) \n"+
				    " VALUES ( \n"+
				    "  :P_NTRABAJADORFK, \n"+
				    "  :P_NOFICINAFK, \n"+
				    "  :P_VUSUARIO, \n"+
				    "  :P_VCLAVE)";
				MapSqlParameterSource parametros = new MapSqlParameterSource();
				parametros.addValue("P_NTRABAJADORFK", usuarios.getNTRABAJADORFK());
				parametros.addValue("P_NOFICINAFK", usuarios.getNOFICINAFK());
				parametros.addValue("P_VUSUARIO", usuarios.getVCORREO());
				parametros.addValue("P_VCLAVE", usuarios.getVCLAVE());
				KeyHolder keyHolder = new GeneratedKeyHolder();
				namedParameterJdbcTemplate.update(sql, parametros, keyHolder , new String[] {"NIDUSUARIOPK"} );
				logger.info("++"+keyHolder.getKey().longValue()); 
				idUsuario = keyHolder.getKey().longValue();
				// INSERTAMOS LA RELACION CON 
				 sql=
				 " INSERT INTO "+Constantes.tablaUsuarioPerfil+" ( \n"+
				 "   NUSUARIOFK, "+
				 "   NPERFILFK)"+
				 " VALUES("+
				 "	 :P_NUSUARIOFK, \n"+
				 "	 :P_NPERFILFK)";
				 KeyHolder keyHolder2 = new GeneratedKeyHolder();
				 MapSqlParameterSource parametros2 = new MapSqlParameterSource();
				 parametros2.addValue("P_NUSUARIOFK", idUsuario);
				 parametros2.addValue("P_NPERFILFK", usuarios.getNPERFILFK());
				 namedParameterJdbcTemplate.update(sql, parametros2, keyHolder2 , new String[] {"NIDUSUAPERFILPK"} );
				 logger.info("++"+keyHolder2.getKey().longValue()); 
				respuesta = true;
 
		return respuesta;
	}

	@Override
	public boolean actualizarUsuarioPersona(Usuarios usuarios) {
		boolean respuesta = false;
		StringBuffer sqlUsuario = new StringBuffer();
		StringBuffer sqlUsuarioPerfil = new StringBuffer();
		
			sqlUsuario.append(
			" UPDATE "+Constantes.tablaUsuario+" SET \n"+
			"   NOFICINAFK   = :P_NOFICINAFK, \n"+
			"   NESTADO      = :P_NESTADO, \n"+
			"   DFECMODIFICA = :P_DFECMODIFICA");	
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NOFICINAFK", usuarios.getNOFICINAFK());
			parametros.addValue("P_NESTADO", usuarios.getNESTADO());
			parametros.addValue("P_DFECMODIFICA", Fechas.fechaActual());
			parametros.addValue("P_NIDUSUARIOPK", usuarios.getNIDUSUARIOPK());
			if(usuarios.getVCLAVE()!=null && usuarios.getVCLAVE().trim().length()>0) {
				sqlUsuario.append(",VCLAVE = :P_VCLAVE \n");
				parametros.addValue("P_VCLAVE", usuarios.getVCLAVE());
			}
			sqlUsuario.append(" WHERE NIDUSUARIOPK= :P_NIDUSUARIOPK");
			namedParameterJdbcTemplate.update(sqlUsuario.toString(), parametros);
			
			sqlUsuarioPerfil.append(
			   " UPDATE "+Constantes.tablaUsuarioPerfil+" SET \n"+
			   "   NPERFILFK= :P_NPERFILFK, \n"+ 
			   "   NESTADO = :P_NESTADO, \n"+
			   "   DFECMODIFICA = :P_DFECMODIFICA \n"+
			   " WHERE NIDUSUAPERFILPK = :P_NIDUSUAPERFILPK");
			MapSqlParameterSource parametros2 = new MapSqlParameterSource();
			parametros2.addValue("P_NPERFILFK", usuarios.getNPERFILFK());
			parametros2.addValue("P_NESTADO", usuarios.getNESTADO());
			parametros2.addValue("P_DFECMODIFICA",  Fechas.fechaActual());
			parametros2.addValue("P_NIDUSUAPERFILPK", usuarios.getNIDUSUAPERFILFK());
			namedParameterJdbcTemplate.update(sqlUsuarioPerfil.toString(), parametros2);
			respuesta = true;
 
		return respuesta;
	}

	@Override
	public boolean eliminarUsuarioPersona(Long idUsuario, Long idUsuarioPerfil) {
		boolean respuesta = false;
		StringBuffer sqlUsuario =new StringBuffer();
		StringBuffer sqlUsuarioPerfil =new StringBuffer();
		
		
			sqlUsuario.append("UPDATE "+Constantes.tablaUsuario+" SET NESTADO= :P_NESTADO WHERE NIDUSUARIOPK= :P_NIDUSUARIOPK");
			MapSqlParameterSource parametros= new MapSqlParameterSource();
			parametros.addValue("P_NESTADO", Constantes.estadoDesactivado);
			parametros.addValue("P_NIDUSUARIOPK", idUsuario);
			namedParameterJdbcTemplate.update(sqlUsuario.toString(), parametros);
			
			sqlUsuarioPerfil.append("UPDATE "+Constantes.tablaUsuarioPerfil+"  SET NESTADO= :P_NESTADO WHERE NIDUSUAPERFILPK = :P_NIDUSUAPERFILPK");
			MapSqlParameterSource parametros2= new MapSqlParameterSource();
			parametros2.addValue("P_NESTADO", Constantes.estadoDesactivado);
			parametros2.addValue("P_NIDUSUAPERFILPK", idUsuarioPerfil);
			namedParameterJdbcTemplate.update(sqlUsuarioPerfil.toString(), parametros2);
			respuesta = true;
 
		return respuesta;
	}

	@Override
	public List<Perfiles> listarPerfiles() {
		List<Perfiles> lsPerfiles = new ArrayList<Perfiles>();
		StringBuffer sql = new StringBuffer();
		 
			 sql.append(
			   " SELECT \n"+
			   " ROW_NUMBER() OVER (ORDER BY NIDPERFILPK) AS NITEM ,  \n"+
			   " 	NIDPERFILPK, \n"+
			   " 	VNOMBRE, \n"+
			   " 	VDESCRIPCION, \n"+
			   " 	NESTADO \n" +
			   " FROM "+Constantes.tablaPerfil);
			 lsPerfiles=namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(Perfiles.class));
	 
		return lsPerfiles;
	}

	@Override
	public boolean guardarFeriado(Feriados feriados) {
		String sql = "";
		boolean respuesta = false;
		
			sql=" INSERT INTO "+Constantes.tablaFeriados+" ( \n"+
		        "   DFERIADO, \n"+
		        "   VDESCRIPCION ) \n"+
		        " VALUES ( \n"+
		        "   :P_DFERIADO, \n"+
		        "   :P_VDESCRIPCION)";
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_DFERIADO", feriados.getDFERIADO());
			parametros.addValue("P_VDESCRIPCION",feriados.getVDESCRIPCION());
			KeyHolder keyHolder = new GeneratedKeyHolder();
			namedParameterJdbcTemplate.update(sql, parametros,keyHolder,new String[] {"NIDFERIADOPK"});
			 logger.info("++"+keyHolder.getKey().longValue()); 
			 respuesta = true;
 
		return respuesta;
	}

	@Override
	public boolean eliminarFeriado(Long idFeriado) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		
			sql.append(
				"UPDATE "+Constantes.tablaFeriados+
				" SET NESTADO = :P_NESTADO \n"+
			    "WHERE NIDFERIADOPK = :P_NIDFERIADOPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NIDFERIADOPK", idFeriado);
			parametros.addValue("P_NESTADO", Constantes.estadoDesactivado);
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
			respuesta = true;
 
		return respuesta;
	}

	@Override
	public List<Feriados> listarFeriados() {
		StringBuffer sql = new StringBuffer();
		List<Feriados> lista = new ArrayList<Feriados>();
		
			sql.append(
			  " SELECT \n"+
			  "	 ROW_NUMBER() OVER ( ORDER BY NIDFERIADOPK )  AS NITEM, \n"+
			  "  NIDFERIADOPK, \n"+
			  "  CONVERT(varchar,DFERIADO,103) AS VFERIADO , \n"+
			  "  VDESCRIPCION, \n"+
			  "  NESTADO \n"+
			  " FROM "+Constantes.tablaFeriados+" \n"+ 
			  " ORDER BY NIDFERIADOPK DESC");
			lista = namedParameterJdbcTemplate.query(sql.toString(), BeanPropertyRowMapper.newInstance(Feriados.class));
 
		return lista;
	}

	@Override
	public List<Feriados> buscarFeriados(Feriados feriados) {
		StringBuffer sql = new StringBuffer();
		List<Feriados> lista = new ArrayList<Feriados>();
		
			sql.append(
			  " SELECT \n"+
			  "	 ROW_NUMBER() OVER ( ORDER BY NIDFERIADOPK )  AS NITEM, \n"+
			  "  NIDFERIADOPK, \n"+
			  "  CONVERT(varchar,DFERIADO,103) AS VFERIADO , \n"+
			  "  VDESCRIPCION, \n"+
			  "  NESTADO \n"+
			  " FROM "+Constantes.tablaFeriados+" \n"+ 
			  " WHERE  VDESCRIPCION LIKE :P_VDESCRIPCION \n"+
			  " ORDER BY NIDFERIADOPK DESC");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_VDESCRIPCION", "%"+feriados.getCAJABUSQUEDA()+"%");
			lista = namedParameterJdbcTemplate.query(sql.toString(),parametros, BeanPropertyRowMapper.newInstance(Feriados.class));
	 
		return lista;
	}

	@Override
	public Persona infoPersona(Persona persona) {
		StringBuffer sql = new StringBuffer();
		List<Persona> listaInfoPersona = new ArrayList<Persona>();  
		 Persona  infoPersona =  new Persona();
			sql.append(
				"SELECT              \n"+
				" T2.VNOMBRE,        \n"+
				" T2.VAPEPATERNO,    \n"+
				" T2.VAPEMATERNO,    \n"+
				" T2.VNUMERODOC      \n"+
				"FROM "+Constantes.tablaTrabajadores+" T1  \n"+
				" INNER JOIN "+Constantes.tablaPersona+" T2 ON T1.NIDPERSONAFK=T2.NIDPERSONAPK \n"+
				"WHERE T2.VNUMERODOC= :P_VNUMERODOC");
			
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_VNUMERODOC", persona.getVNUMERODOC());
			listaInfoPersona = namedParameterJdbcTemplate.query(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Persona.class));
			if(listaInfoPersona.size()>0) {
				infoPersona = listaInfoPersona.get(0);
			}
 
		return infoPersona;
	}

	@Override
	public List<Correlativo> listarCorrelativos(Correlativo formCorrelativo) {
		List<Correlativo>  lista = new ArrayList<Correlativo>();
		StringBuffer sql = new StringBuffer();
		 MapSqlParameterSource parametros = new MapSqlParameterSource(); 
		
			sql.append(
				"SELECT \n" +
				"  ROW_NUMBER() OVER ( ORDER BY T1.NCORRELATIVOPK )  AS NITEM, \n" +
				"  T1.NCORRELATIVOPK, \n" +
				"  T2.VNOMBRE, \n" +
				"  T1.NVALOR_ACTUAL \n" +
				"FROM "+Constantes.tablaCorrelativos+" T1 \n" +
				"INNER JOIN "+Constantes.tablaOficinas+" T2 ON T1.NOFICINAFK=T2.NIDOFICINAPK AND T1.NESTADO= :P_NESTADO");
			if(formCorrelativo.getCAJABUSQUEDA()!=null) {
				 sql.append(" AND T2.VNOMBRE LIKE :P_VNOMBRE");
				 parametros.addValue("P_VNOMBRE", "%"+formCorrelativo.getCAJABUSQUEDA()+"%");
			} 
			 parametros.addValue("P_NESTADO", Constantes.estadoActivado);
			 lista = namedParameterJdbcTemplate.query(sql.toString(),parametros,BeanPropertyRowMapper.newInstance(Correlativo.class));
	 
		return lista;
	}

	@Override
	public Correlativo infoCorrelativo(Long idcorrelativo) {
		StringBuffer sql = new StringBuffer();
		Correlativo info = new Correlativo();
		
			sql.append(
				 "SELECT \n"+
				 "  ROW_NUMBER() OVER ( ORDER BY T1.NCORRELATIVOPK )  AS NITEM, \n"+
				 "  T1.NCORRELATIVOPK, \n" +
				 "  T2.VNOMBRE, \n"+
				 "  T1.NVALOR_ACTUAL \n"+
				 "FROM "+Constantes.tablaCorrelativos+" T1 \n"+
				 "INNER JOIN "+Constantes.tablaOficinas+" T2 ON T1.NOFICINAFK=T2.NIDOFICINAPK AND T1.NESTADO= :P_NESTADO \n"+
				 "WHERE T1.NCORRELATIVOPK= :P_NCORRELATIVOPK ");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NESTADO",  Constantes.estadoActivado);
			parametros.addValue("P_NCORRELATIVOPK", idcorrelativo); 
			info =namedParameterJdbcTemplate.queryForObject(sql.toString(), parametros, BeanPropertyRowMapper.newInstance(Correlativo.class));
  
		return info;
	}

	@Override
	public boolean actualizarCorrelativo(Correlativo formCorrelativo) {
		StringBuffer sql = new StringBuffer();
		boolean respuesta = false;
		
			sql.append(
				"UPDATE "+Constantes.tablaCorrelativos+" SET \n"+
			    " NVALOR_ACTUAL = :P_NVALOR_ACTUAL \n"+
				"WHERE NCORRELATIVOPK = :P_NCORRELATIVOPK");
			MapSqlParameterSource parametros = new MapSqlParameterSource();
			parametros.addValue("P_NVALOR_ACTUAL", formCorrelativo.getNVALOR_ACTUAL());
			parametros.addValue("P_NCORRELATIVOPK", formCorrelativo.getNCORRELATIVOPK());
			namedParameterJdbcTemplate.update(sql.toString(), parametros);
			respuesta = true;
		 
		return respuesta;
	}	

}
