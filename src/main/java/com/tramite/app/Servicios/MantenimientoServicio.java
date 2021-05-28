package com.tramite.app.Servicios;

import java.util.List;

import com.tramite.app.Entidades.EstadoDocumento;
import com.tramite.app.Entidades.Feriados;
import com.tramite.app.Entidades.Informacion;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.Oficinas; 
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.Profesiones;
import com.tramite.app.Entidades.Requisitos;
import com.tramite.app.Entidades.RequisitosTupac;
import com.tramite.app.Entidades.Seleccion;
import com.tramite.app.Entidades.TipoDocumentos;
import com.tramite.app.Entidades.TipoTramite;
import com.tramite.app.Entidades.Trabajadores;
import com.tramite.app.Entidades.Tupac;
import com.tramite.app.Entidades.Usuarios;

public interface MantenimientoServicio {
	
	List<Trabajadores> listarTrabajadores();
	
	Informacion informacionMunicipalidad();
	MensajeRespuesta actualizarinformacionMunicipalidad(Informacion informacion);
	
	List<Oficinas> listarOficinas();
	MensajeRespuesta  guardarOficina(Oficinas oficina);
	List<Oficinas> buscarOficinas(Oficinas oficinas);
	List<Seleccion>listarOficinasCombo();
	Oficinas buscarOficina(Long id);
	MensajeRespuesta  actualizarOficina(Oficinas oficina);
	MensajeRespuesta  eliminarOficina(Long id);
	List<Seleccion>listarEstadosRegistro();
	
	List<TipoDocumentos> listarTipoDocumento();
	MensajeRespuesta  guardarTipoDocumentos(TipoDocumentos tipoDocumentos);
	List<TipoDocumentos> buscarTipoDocumentos(TipoDocumentos tipoDocumentos);
	MensajeRespuesta  actualizarTipoDocumento(TipoDocumentos tipoDocumentos);
	MensajeRespuesta  eliminarTipoDocumento(Long id);
	TipoDocumentos buscarTipoDocumentoId(Long id);
	
	
	List<TipoTramite> listarTipoTramite();
	List<TipoTramite> buscarTipoTramite(TipoTramite tipoTramite);
	TipoTramite buscarTipoTramiteId(Long id);
	MensajeRespuesta  guardarTipoTramite(TipoTramite tipoTramite);
	MensajeRespuesta  eliminarTipoTramite(Long id);
	MensajeRespuesta  actualizarTipoTramite(TipoTramite tipoTramite);
	
	List<EstadoDocumento> listarEstadoDocumento();
	List<EstadoDocumento> buscarEstadoDocumento(EstadoDocumento estadoDocumento);
	
	
	List<Profesiones> listarProfesiones();
	List<Profesiones> buscarProfesiones(Profesiones profesiones);
	Profesiones buscarProfesionesId(Long id);
	MensajeRespuesta  guardarProfesiones(Profesiones profesiones);
	MensajeRespuesta  actualizarProfesiones(Profesiones profesiones);
	MensajeRespuesta  eliminarProfesiones(Long id);
	
	
	List<Requisitos> listarRequisitos();
	List<Requisitos> buscarRequisitos(Requisitos requisitos);
	Requisitos buscarRequisitosId(Long id);
	MensajeRespuesta  guardarRequisitos(Requisitos requisitos);
	MensajeRespuesta  actualizarRequisitos(Requisitos requisitos);
	MensajeRespuesta  eliminarRequisitos(Long id);
	
	
	List<Tupac> listarTupac();
	List<Tupac> buscarTupac(Tupac tupac);
	Tupac buscarTupacPorId(Long id);
	MensajeRespuesta  guardarTupac(Tupac tupac);
	MensajeRespuesta  actualizarTupac(Tupac tupac);
	MensajeRespuesta  eliminarTupac(Long id);
	List<Seleccion>listarTipoDiasCombo();
	
	List<RequisitosTupac> listarRequisitosTupacPorIdTupac(Long id);
	MensajeRespuesta guardarRequisitosTupac(RequisitosTupac requisitosTupac);
	List<Seleccion>listarRequisitosTupac();
	//List<RequisitosTupac> listarRequisitosTupacPorIdTupacRequerimiento(Long idTupac,Long idRequerimiento);
	MensajeRespuesta  activarRequisitosTupac(Long id);
	MensajeRespuesta  eliminarRequisitosTupac(Long id);
	
	List<Seleccion>cbProfesiones();
	List<Seleccion>cbTipoDocumentoRegistro();
	List<Seleccion>cbCriteriosBusquedaTrabajador();
	
	List<Persona> listarTrabajadorPersona();
	List<Persona> buscarTrabajadorPersona(Persona persona);
	Persona buscarTrabajadorPersonaPorId(Long id);
	MensajeRespuesta  guardarTrabajadorPersona(Persona persona);
	MensajeRespuesta  actualizarTrabajadorPersona(Persona persona);
	MensajeRespuesta  eliminarTrabajadorPersona(Long id);
	
	
	List<Usuarios> listarUsuarioPersona();
	List<Usuarios> buscarUsuarioPersona(Usuarios usuarios);
	Usuarios buscarUsuarioPersonaPorId(Long id);
	MensajeRespuesta  guardarUsuarioPersona(Usuarios usuarios);
	MensajeRespuesta  actualizarUsuarioPersona(Usuarios usuarios);
	MensajeRespuesta  eliminarUsuarioPersona(Long idUsuario,Long idUsuarioPerfil);
	
	List<Seleccion>listarPerfiles();
	
	MensajeRespuesta  guardarFeriado(Feriados feriados);
	MensajeRespuesta  eliminarFeriado(Long idFeriado);
	List<Feriados> listarFeriados();
	List<Feriados> buscarFeriados(Feriados feriados);
	
}
