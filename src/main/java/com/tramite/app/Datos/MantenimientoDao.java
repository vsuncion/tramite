package com.tramite.app.Datos;

import java.util.List;

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

public interface MantenimientoDao {

	List<Trabajadores> listarTrabajadores();
	List<Trabajadores> buscarPorNombre(String criterio);
	boolean registrarTrabajador(Trabajadores trabajador);
	Trabajadores actualizarTrabajador(Trabajadores trabajador);
	boolean eliminarTrabajador(Long codigo);
	
	
	Informacion informacionMunicipalidad();
	boolean actualizarinformacionMunicipalidad(Informacion informacion);
	
	List<Oficinas> listarOficinas();
	boolean guardarOficina(Oficinas oficina);
	List<Oficinas> buscarOficinas(Oficinas oficinas);
	Oficinas buscarOficinaId(Long id);
	boolean actualizarOficina(Oficinas oficina);
	boolean eliminarOficina(Long id);
	
	
	List<TipoDocumentos> listarTipoDocumento();
	boolean guardarTipoDocumentos(TipoDocumentos tipoDocumentos);
	List<TipoDocumentos> buscarTipoDocumentos(TipoDocumentos tipoDocumentos);
	boolean actualizarTipoDocumento(TipoDocumentos tipoDocumentos);
	boolean eliminarTipoDocumento(Long id);
	TipoDocumentos buscarTipoDocumentoId(Long id);
	
	List<EstadoDocumento> listarEstadoDocumento();
	List<EstadoDocumento> buscarEstadoDocumento(EstadoDocumento estadoDocumento);
	
	
	List<TipoTramite> listarTipoTramite();
	List<TipoTramite> buscarTipoTramite(TipoTramite tipoTramite);
	TipoTramite buscarTipoTramiteId(Long id);
	boolean guardarTipoTramite(TipoTramite tipoTramite);
	boolean eliminarTipoTramite(Long id);
	boolean actualizarTipoTramite(TipoTramite tipoTramite);
	
	
	List<Profesiones> listarProfesiones();
	List<Profesiones> buscarProfesiones(Profesiones profesiones);
	Profesiones buscarProfesionesId(Long id);
	boolean guardarProfesiones(Profesiones profesiones);
	boolean actualizarProfesiones(Profesiones profesiones);
	boolean eliminarProfesiones(Long id);
	
	
	List<Requisitos> listarRequisitos();
	List<Requisitos> buscarRequisitos(Requisitos requisitos);
	Requisitos buscarRequisitosId(Long id);
	boolean guardarRequisitos(Requisitos requisitos);
	boolean actualizarRequisitos(Requisitos requisitos);
	boolean eliminarRequisitos(Long id);
	
	
	List<Tupac> listarTupac();
	List<Tupac> buscarTupac(Tupac tupac);
	Tupac buscarTupacPorId(Long id);
	boolean guardarTupac(Tupac tupac);
	boolean actualizarTupac(Tupac tupac);
	boolean eliminarTupac(Long id);
	
	
	List<RequisitosTupac> listarRequisitosTupacPorIdTupac(Long id);
	RequisitosTupac  buscarPorTupacRequisitos(Long idTupac,Long idRequerimiento);
	boolean guardarRequisitosTupac(RequisitosTupac requisitosTupac); 
	boolean activarRequisitosTupac(Long id);
	boolean eliminarRequisitosTupac(Long id); 
	
	
	
	List<Persona> listarTrabajadorPersona();
	List<Persona> buscarTrabajadorPersona(Persona persona);
	Persona buscarTrabajadorPersonaPorId(Long id);
	boolean guardarTrabajadorPersona(Persona persona);
	boolean actualizarTrabajadorPersona(Persona persona);
	boolean eliminarTrabajadorPersona(Long id);
	Persona infoPersona (Persona persona);
	
	List<Usuarios> listarUsuarioPersona();
	List<Usuarios> buscarUsuarioPersona(Usuarios usuarios);
	Usuarios buscarUsuarioPersonaPorId(Long id);
	boolean guardarUsuarioPersona(Usuarios usuarios);
	boolean actualizarUsuarioPersona(Usuarios usuarios); 
	boolean eliminarUsuarioPersona(Long idUsuario,Long idUsuarioPerfil);
	List<Perfiles>listarPerfiles();
	
	boolean guardarFeriado(Feriados feriados);
	boolean eliminarFeriado(Long idFeriado);
	List<Feriados> listarFeriados();
	List<Feriados> buscarFeriados(Feriados feriados);
}
