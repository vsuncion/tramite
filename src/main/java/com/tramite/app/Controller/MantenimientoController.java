package com.tramite.app.Controller;
 

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.tramite.app.Servicios.MantenimientoServicio;
import com.tramite.app.Servicios.RecursoServicio;
import com.tramite.app.utilitarios.Constantes; 
import com.tramite.app.Entidades.Correlativo;
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
import com.tramite.app.Entidades.Tupac;
import com.tramite.app.Entidades.Usuarios;
import com.tramite.app.Entidades.TipoTramite;;

@Secured({"ROLE_ADMINISTRADOR"})
@Controller
@RequestMapping("/admin/mantenimiento")
public class MantenimientoController {
	
	@Autowired
	private MantenimientoServicio  mantenimientoServicio;
	
	@Autowired
	private RecursoServicio  recursoServicio;
	
	@Value("${urlTramite}")
	private String urlTramite;
	
	Logger logger = LoggerFactory.getLogger(getClass());
 
	// ========================== CRUD TRABAJADOR ===================================
	
	@GetMapping(value = { "/listarTranbajador" })
	public ModelAndView tranbajador(HttpServletRequest request,HttpServletResponse res) {
		Persona formTrabajadorPersona = new Persona();
		List<Persona>  listaTrabajadorPersona = new ArrayList<Persona>();
		List<Seleccion> cbCriterioBusqueda = new ArrayList<Seleccion>();
		
		listaTrabajadorPersona= mantenimientoServicio.listarTrabajadorPersona();
		cbCriterioBusqueda= mantenimientoServicio.cbCriteriosBusquedaTrabajador();

		logger.info("======================= INFO  tranbajador================");
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("persona",formTrabajadorPersona);
		pagina.addObject("listaTrabajadorPersona",listaTrabajadorPersona);
		pagina.addObject("cbCriterioBusqueda",cbCriterioBusqueda);
		
		pagina.setViewName("admin/trabajador/listar");
		return pagina;
	}
	
	@PostMapping(value = {"/buscarTrabajadorPersona"})
	public ModelAndView buscarTrabajadorPersona(HttpServletRequest request,HttpServletResponse res,@ModelAttribute Persona formTrabajadorPersona ) {
 
		List<Persona>  listaTrabajadorPersona = new ArrayList<Persona>();
		List<Seleccion> cbCriterioBusqueda = new ArrayList<Seleccion>();
		
		listaTrabajadorPersona= mantenimientoServicio.buscarTrabajadorPersona(formTrabajadorPersona);
		cbCriterioBusqueda= mantenimientoServicio.cbCriteriosBusquedaTrabajador();

		logger.info("======================= INFO  tranbajador================");
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("listaTrabajadorPersona",listaTrabajadorPersona);
		pagina.addObject("cbCriterioBusqueda",cbCriterioBusqueda);
		pagina.addObject("persona",formTrabajadorPersona);
		pagina.setViewName("admin/trabajador/listar");
		return pagina;
		
	}
	
	
	@GetMapping(value = { "/nuevoTranbajador" })
	public ModelAndView nuevoTranbajador(HttpServletRequest request,HttpServletResponse res) {
		
		Persona persona = new  Persona();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		List<Seleccion> cbOficinas = new ArrayList<Seleccion>();
		List<Seleccion> cbProfesiones = new ArrayList<Seleccion>();
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		List<Seleccion> cbCargo = new ArrayList<Seleccion>();
		

		cbOficinas = mantenimientoServicio.listarOficinasCombo();
		cbProfesiones = mantenimientoServicio.cbProfesiones();
		cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();
		cbCargo = recursoServicio.cbOCargos();
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		logger.info("======================= INFO  tranbajador================");
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("persona",persona);
		pagina.addObject("cbOficinas", cbOficinas);
		pagina.addObject("cbProfesiones", cbProfesiones);
		pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		pagina.addObject("cbCargo", cbCargo);
		pagina.setViewName("admin/trabajador/nuevo");
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	
	
	@PostMapping(value = {"/guardarformTrabajador"})
	public ModelAndView guardarformTrabajador(@Valid Persona formPersona,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) {
	    
		List<Seleccion> cbOficinas = new ArrayList<Seleccion>();
		List<Seleccion> cbProfesiones = new ArrayList<Seleccion>();
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		Persona InfoPersona = new Persona();
		
		
		
		cbOficinas = mantenimientoServicio.listarOficinasCombo();
		cbProfesiones = mantenimientoServicio.cbProfesiones();
		cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();
		
		if (null != errors && errors.getErrorCount() > 0) {
			pagina.setViewName("admin/trabajador/nuevo");
			pagina.addObject("persona",formPersona);
			pagina.addObject("cbOficinas", cbOficinas);
			pagina.addObject("cbProfesiones", cbProfesiones);
			pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			return pagina;
		}

		// VERIFICAR SI EL TRABAJA
		InfoPersona = mantenimientoServicio.infoPersona(formPersona);
		
		if(InfoPersona.getVNUMERODOC()!=null) {
			mostrarmensaje.setCodigo(1);
			mostrarmensaje.setMensaje("LA DNI SE ENCUENTRA DUPLICADOS "+InfoPersona.getVNUMERODOC());
		}else {
			mostrarmensaje = mantenimientoServicio.guardarTrabajadorPersona(formPersona); 
		}
	 
		        
  
		pagina.setViewName("admin/trabajador/nuevo");
		//pagina.setViewName("redirect:/admin/mantenimiento/listarTranbajador");
		pagina.addObject("persona",formPersona);
		pagina.addObject("cbOficinas", cbOficinas);
		pagina.addObject("cbProfesiones", cbProfesiones);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		
		return pagina;
		
	}
	
	@GetMapping(value = {"/editarTrabajadorPersona"})
	public ModelAndView editarTrabajadorPersona(HttpServletRequest request,HttpServletResponse res,@RequestParam Long IDTRABAJADOR,@RequestParam Long IDPERSONA) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		Persona formPersona = new  Persona();
		List<Seleccion> cbOficinas = new ArrayList<Seleccion>();
		List<Seleccion> cbProfesiones = new ArrayList<Seleccion>();
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		List<Seleccion> cbCargo = new ArrayList<Seleccion>();
		ModelAndView pagina = new ModelAndView();
		
		formPersona = mantenimientoServicio.buscarTrabajadorPersonaPorId(IDTRABAJADOR);
		formPersona.setNIDPERSONAPK(IDPERSONA);
		cbOficinas = mantenimientoServicio.listarOficinasCombo();
		cbProfesiones = mantenimientoServicio.cbProfesiones();
		cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro();
		cbCargo = recursoServicio.cbOCargos();
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		logger.info("======================= INFO  tranbajador================");
		pagina.setViewName("admin/trabajador/actualizar");
		pagina.addObject("persona",formPersona);
		pagina.addObject("cbOficinas", cbOficinas);
		pagina.addObject("cbProfesiones", cbProfesiones);
		pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		pagina.addObject("cbEstados", listaEstadosRegistro);
		pagina.addObject("cbCargo", cbCargo);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("urltramite",urlTramite);
		return pagina;
	}
	
	
	@PostMapping(value = {"/actualizarformTrabajador"})
	public ModelAndView actualizarTrabajadorPersona(@Valid Persona formPersona,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) {
		List<Seleccion> cbOficinas = new ArrayList<Seleccion>();
		List<Seleccion> cbProfesiones = new ArrayList<Seleccion>();
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		List<Seleccion> cbCargo = new ArrayList<Seleccion>();
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		cbOficinas = mantenimientoServicio.listarOficinasCombo();
		cbProfesiones = mantenimientoServicio.cbProfesiones();
		cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();
		cbCargo = recursoServicio.cbOCargos();
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro();
		
		if (null != errors && errors.getErrorCount() > 0) {
			pagina.setViewName("admin/trabajador/actualizar");
			pagina.addObject("persona",formPersona);
			pagina.addObject("cbOficinas", cbOficinas);
			pagina.addObject("cbProfesiones", cbProfesiones);
			pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
			pagina.addObject("cbEstados", listaEstadosRegistro);
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			pagina.addObject("cbCargo", cbCargo);
			pagina.addObject("urltramite",urlTramite);
			return pagina;
		}
		
		
		mostrarmensaje=mantenimientoServicio.actualizarTrabajadorPersona(formPersona);
		
		//pagina.setViewName("redirect:/admin/mantenimiento/listarTranbajador");
		pagina.setViewName("admin/trabajador/actualizar");
		pagina.addObject("persona",formPersona);
		pagina.addObject("cbOficinas", cbOficinas);
		pagina.addObject("cbProfesiones", cbProfesiones);
		pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		pagina.addObject("cbEstados", listaEstadosRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("cbCargo", cbCargo);
		pagina.addObject("urltramite",urlTramite);
		return pagina;
	}
	
	@GetMapping(value = {"/eliminarTrabajadorPersona"})
	public ModelAndView eliminarTrabajador(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) {
		Persona formTrabajadorPersona = new Persona();
		List<Persona>  listaTrabajadorPersona = new ArrayList<Persona>();
		List<Seleccion> cbCriterioBusqueda = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		ModelAndView pagina = new ModelAndView();
		
		mostrarmensaje =mantenimientoServicio.eliminarTrabajadorPersona(id);
		listaTrabajadorPersona= mantenimientoServicio.listarTrabajadorPersona();
		cbCriterioBusqueda= mantenimientoServicio.cbCriteriosBusquedaTrabajador();

		logger.info("======================= INFO  tranbajador================");
		pagina.setViewName("admin/trabajador/listar");
		pagina.addObject("listaTrabajadorPersona",listaTrabajadorPersona);
		pagina.addObject("cbCriterioBusqueda",cbCriterioBusqueda);
		pagina.addObject("persona",formTrabajadorPersona);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		
		return pagina;
	}
	
	// ==================================================================================
	
	// ======================== ESTADO DEL DOCUMENTO=====================================
	
	@GetMapping(value = { "/listarEstadoDocumento" })
	public ModelAndView estadoDocumento(HttpServletRequest request,HttpServletResponse res) {

		logger.info("======================= INFO 4 estadodocumento================");
		EstadoDocumento  formEstadoDocumento = new EstadoDocumento();
		List<EstadoDocumento> listarEstadoDocumento = new ArrayList<EstadoDocumento>();
		
		listarEstadoDocumento = mantenimientoServicio.listarEstadoDocumento();
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("formEstadoDocumento", formEstadoDocumento);
		pagina.addObject("listarEstadoDocumento", listarEstadoDocumento);
		pagina.setViewName("admin/estadodocumento/listar");
		return pagina;
	}
	
	
	@PostMapping(value = {"/buscarEstadoDocumento"})
	public ModelAndView buscarEstadoDocumento(HttpServletRequest request,HttpServletResponse res,@ModelAttribute EstadoDocumento formEstadoDocumento) {
		List<EstadoDocumento> listarEstadoDocumento = new ArrayList<EstadoDocumento>();
		
		listarEstadoDocumento = mantenimientoServicio.buscarEstadoDocumento(formEstadoDocumento);
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("formEstadoDocumento", formEstadoDocumento);
		pagina.addObject("listarEstadoDocumento", listarEstadoDocumento);
		pagina.setViewName("admin/estadodocumento/listar");
		return pagina;
	}
	
	// ==================================================================================
	
	// ========================== CRUD FERIADO ==============================================

	@GetMapping(value = { "/listarFeriado" })
	public ModelAndView feriado(HttpServletRequest request,HttpServletResponse res) {
      
		logger.info("======================= INFO 5 feriado================");
		Feriados formFeriado = new Feriados();
		List<Feriados> listaFeriados = new ArrayList<Feriados>();
		
		listaFeriados = mantenimientoServicio.listarFeriados();
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("formFeriado", formFeriado);
		pagina.addObject("listaFeriados", listaFeriados);
		pagina.setViewName("admin/feriado/listar");
		return pagina;
	}
	
	@GetMapping(value = { "/nuevoFeriado" })
	public ModelAndView nuevoFeriado() {
		ModelAndView pagina = new ModelAndView();
		Feriados formFeriado = new Feriados();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		pagina.addObject("formFeriado", formFeriado);
		pagina.setViewName("admin/feriado/nuevo");
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;	
	}
	
	
	@PostMapping(value = {"/guardarFeriado"})
	public ModelAndView guardarFeriado(@ModelAttribute Feriados formFeriado, HttpServletRequest request,HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView(); 
		 
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		
		mostrarmensaje = mantenimientoServicio.guardarFeriado(formFeriado);
		
		pagina.addObject("formFeriado", formFeriado);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.setViewName("admin/feriado/nuevo");
		return pagina;
		
	}
	
	@PostMapping(value = {"/buscarFeriado"})
	public ModelAndView buscarFeriado(@ModelAttribute Feriados formFeriado, HttpServletRequest request,HttpServletResponse res) {
		List<Feriados> listaFeriados = new ArrayList<Feriados>();
		ModelAndView pagina = new ModelAndView(); 
		
		listaFeriados = mantenimientoServicio.buscarFeriados(formFeriado);
		pagina.addObject("formFeriado", formFeriado);
		pagina.addObject("listaFeriados", listaFeriados);
		pagina.setViewName("admin/feriado/listar");
		return pagina;	
	}
	
	@GetMapping(value = {"/eliminarFeriado"})
	public ModelAndView eliminarFeriado( HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) {
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();  
		Feriados formFeriado = new Feriados();
		List<Feriados> listaFeriados = new ArrayList<Feriados>();
		
		mostrarmensaje = mantenimientoServicio.eliminarFeriado(id);
		listaFeriados = mantenimientoServicio.listarFeriados();
		
		
		pagina.addObject("formFeriado", formFeriado);
		pagina.addObject("listaFeriados", listaFeriados);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.setViewName("admin/feriado/listar");
		return pagina;
	}
	// ==================================================================================
	
	// ========================== INFORMACION =============================================
	
	@GetMapping(value = { "/infomunicipalidad" })
	public ModelAndView infoMunicipalidad(HttpServletRequest request,HttpServletResponse res) {
        
		logger.info("======================= INFO 6 infomunicipalidad ================");
		
		
		ModelAndView pagina = new ModelAndView();
		Informacion informacion = new Informacion();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		informacion = mantenimientoServicio.informacionMunicipalidad();
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		pagina.addObject("urltramite",urlTramite);
		pagina.addObject("info", informacion);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.setViewName("admin/informacion/actualizar");
		return pagina;
	}
	
	
	@PostMapping(value = {"/actualizarInfo"})
	public ModelAndView actualizarInfo(HttpServletRequest request,HttpServletResponse res,@ModelAttribute Informacion info) {
		
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
	 
		mostrarmensaje = mantenimientoServicio.actualizarinformacionMunicipalidad(info);
		
		ModelAndView pagina = new ModelAndView();
		
		pagina.setViewName("admin/informacion/actualizar");
		pagina.addObject("info", info);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("urltramite",urlTramite);
		return pagina; 
	}
	
	// ==================================================================================
	
	
	// ========================== CRUD OFICINAS  ========================================
	
	@GetMapping(value = { "/listar_oficina" })
	public ModelAndView oficina(HttpServletRequest request,HttpServletResponse res) {
		logger.info("======================= INFO 7 oficina================");
		
		List<Oficinas> listarOficinas = new ArrayList<Oficinas>();
		listarOficinas = mantenimientoServicio.listarOficinas();
		
		Oficinas oficina = new Oficinas();
		ModelAndView pagina = new ModelAndView();
		pagina.setViewName("admin/oficinas/listar");
		pagina.addObject("oficinas", oficina);
		pagina.addObject("lista_oficinas", listarOficinas);
		return pagina;
	}
	
	@PostMapping(value = {"/buscarOficina"})
	public ModelAndView buscar_oficina(@ModelAttribute Oficinas oficina,HttpServletRequest request,HttpServletResponse res) {
		List<Oficinas> listarOficinas = new ArrayList<Oficinas>();
		listarOficinas =mantenimientoServicio.buscarOficinas(oficina);
		
		ModelAndView pagina = new ModelAndView();
		pagina.setViewName("admin/oficinas/listar");
		pagina.addObject("oficinas", oficina);
		pagina.addObject("lista_oficinas", listarOficinas);
		return pagina;  
	}
	
	@GetMapping(value = { "/nueva_oficina" })
	public ModelAndView nuevaOficina(HttpServletRequest request,HttpServletResponse res) {
		logger.info("======================= INFO 7 oficina================");
		List<Seleccion> listaSeleccion = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		listaSeleccion = mantenimientoServicio.listarOficinasCombo();
        mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);

		Oficinas oficina = new Oficinas();
		ModelAndView pagina = new ModelAndView();
		pagina.setViewName("admin/oficinas/nueva");
		pagina.addObject("oficinas", oficina);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("cbOficinas", listaSeleccion);
		pagina.addObject("urltramite",urlTramite);
		return pagina;
	}
	
	
	@PostMapping(value = {"/guardarOficina"}) 
	public ModelAndView guardarOficina(@Valid Oficinas formOficina,BindingResult result, Errors errors,
			HttpServletRequest request,HttpServletResponse res) {
		  
		 List<Seleccion> listaSeleccion = new ArrayList<Seleccion>();
		 ModelAndView pagina = new ModelAndView();
		 MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		 
		 listaSeleccion = mantenimientoServicio.listarOficinasCombo();
 
		if (null != errors && errors.getErrorCount() > 0) {
			 logger.info("ERRORES ============="+errors.getErrorCount());
			pagina.setViewName("admin/oficinas/nueva");
			pagina.addObject("oficinas", formOficina);
			pagina.addObject("cbOficinas", listaSeleccion);
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			return pagina;
		 }
		
		 logger.info("GRABAR =============");
		 mostrarmensaje =mantenimientoServicio.guardarOficina(formOficina);
		
		pagina.setViewName("admin/oficinas/nueva");
		//pagina.setViewName("redirect:/admin/mantenimiento/listar_oficina");	
		pagina.addObject("oficinas", formOficina);
		pagina.addObject("cbOficinas", listaSeleccion);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("urltramite",urlTramite);
		return pagina;
		 
	}
	
	@GetMapping(value = {"/editarOficina"}) 
	public ModelAndView buscarOficinaPorId(HttpServletRequest request,HttpServletResponse res,
			 @RequestParam Long id) {
		Oficinas oficina = new Oficinas();
		List<Seleccion> cbOficinas = new ArrayList<Seleccion>();
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		ModelAndView pagina = new ModelAndView();
		
		oficina = mantenimientoServicio.buscarOficina(id);
		cbOficinas = mantenimientoServicio.listarOficinasCombo();
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro(); 
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		
		pagina.setViewName("admin/oficinas/actualizar");
		pagina.addObject("oficinas", oficina);
		pagina.addObject("cbOficinas", cbOficinas); 
		pagina.addObject("cbEstados", listaEstadosRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("urltramite",urlTramite);
		return pagina;
	}
	
	@PostMapping(value = {"/actualizarOficina"})
	public ModelAndView actualizarOficina(@Valid Oficinas formOficina,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) {
		
		List<Seleccion> listaSeleccion = new ArrayList<Seleccion>();
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		
		listaSeleccion = mantenimientoServicio.listarOficinasCombo();
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro();
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		
		if (null != errors && errors.getErrorCount() > 0) {
			 logger.info("ERRORES ============="+errors.getErrorCount());
			 pagina.setViewName("admin/oficinas/actualizar");
			 pagina.addObject("oficinas", formOficina);
			 pagina.addObject("cbOficinas", listaSeleccion);
			 pagina.addObject("mostrarmensaje",mostrarmensaje);
			 pagina.addObject("urltramite",urlTramite);
			return pagina;
		 }
		
		mostrarmensaje = mantenimientoServicio.actualizarOficina(formOficina);
		formOficina = mantenimientoServicio.buscarOficina(formOficina.getNIDOFICINAPK());
		
		pagina.setViewName("admin/oficinas/actualizar");
		//pagina.setViewName("redirect:/admin/mantenimiento/listar_oficina");	
		pagina.addObject("oficinas", formOficina);
		pagina.addObject("cbOficinas", listaSeleccion); 
		pagina.addObject("cbEstados", listaEstadosRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("urltramite",urlTramite);
		return pagina;
	}
	
	
	@GetMapping(value = {"/eliminarOficina"})
	@ResponseBody
	public ModelAndView eliminarOficina(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) { 
		List<Oficinas> listarOficinas = new ArrayList<Oficinas>();
		Oficinas oficina = new Oficinas();
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje =mantenimientoServicio.eliminarOficina(id);
		listarOficinas = mantenimientoServicio.listarOficinas();
		
		
		pagina.setViewName("admin/oficinas/listar");
		pagina.addObject("oficinas", oficina);
		pagina.addObject("lista_oficinas", listarOficinas);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	
	// ==================================================================================
	
	// ======================= CRUD PROFESION ============================================

	@GetMapping(value = { "/listarProfesion" })
	public ModelAndView profesiones(HttpServletRequest request,HttpServletResponse res) {

		logger.info("======================= INFO 8 profesiones================");
		Profesiones formProfesion = new Profesiones();
		List<Profesiones> listarProfesiones = new ArrayList<Profesiones>();
		listarProfesiones = mantenimientoServicio.listarProfesiones();
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("profesiones",formProfesion);
		pagina.addObject("listarProfesiones",listarProfesiones);
		pagina.setViewName("admin/profesiones/listar");
		return pagina;
	}
	
	
	@PostMapping(value = {"/buscarProfesion"})
	public ModelAndView buscarProfesiones(HttpServletRequest request,HttpServletResponse res,@ModelAttribute  Profesiones formProfesion) {
		
		List<Profesiones> listarProfesiones = new ArrayList<Profesiones>();
		listarProfesiones = mantenimientoServicio.buscarProfesiones(formProfesion);
		
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("profesiones",formProfesion);
		pagina.addObject("listarProfesiones",listarProfesiones);
		pagina.setViewName("admin/profesiones/listar");
		return pagina;
	}
	
	
	@GetMapping(value = {"/nuevoProfesion"})
	public ModelAndView nuevaProfesion(HttpServletRequest request,HttpServletResponse res) {
		Profesiones formProfesion = new Profesiones();
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		pagina.addObject("profesiones",formProfesion); 
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.setViewName("admin/profesiones/nueva");
		return pagina;
	}
	
	@PostMapping(value = {"/guardarProfesion"})
	public ModelAndView guardarProfesion(@Valid  Profesiones formProfesion,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		if (null != errors && errors.getErrorCount() > 0) {
			pagina.addObject("profesiones",formProfesion); 
			pagina.setViewName("admin/profesiones/nueva");
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			return pagina;
		}
		
		mostrarmensaje = mantenimientoServicio.guardarProfesiones(formProfesion);
		  
		//pagina.setViewName("redirect:/admin/mantenimiento/listarProfesion"); 
		pagina.setViewName("admin/profesiones/nueva");
		pagina.addObject("profesiones",formProfesion);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	
	@GetMapping(value = {"/editarProfesion"})
	public ModelAndView buscarProfesionPorId(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) {
		Profesiones formProfesion = new Profesiones();
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		ModelAndView pagina = new ModelAndView();
		
		formProfesion = mantenimientoServicio.buscarProfesionesId(id);
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro(); 
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		pagina.setViewName("admin/profesiones/actualizar");
		pagina.addObject("profesiones",formProfesion); 
		pagina.addObject("cbEstados", listaEstadosRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("urltramite",urlTramite);
		return pagina;
	}
	
	
	@PostMapping(value = {"/actualizarProfesion"})
	public ModelAndView actualizarProfesion(@Valid Profesiones formProfesion,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) {
		
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro(); 
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		if (null != errors && errors.getErrorCount() > 0) {
			pagina.setViewName("admin/profesiones/actualizar");
			pagina.addObject("profesiones",formProfesion); 
			pagina.addObject("cbEstados", listaEstadosRegistro);
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			pagina.addObject("urltramite",urlTramite);
			return pagina;
		}
 
		mostrarmensaje=mantenimientoServicio.actualizarProfesiones(formProfesion);
 
		pagina.setViewName("admin/profesiones/actualizar");
		pagina.addObject("profesiones",formProfesion); 
		pagina.addObject("cbEstados", listaEstadosRegistro); 
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("urltramite",urlTramite);
		return pagina;
		
	}
	
	@GetMapping(value = {"/eliminarProfesion"})
	public ModelAndView eliminarProfesion(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) { 
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		Profesiones formProfesion = new Profesiones();
		List<Profesiones> listarProfesiones = new ArrayList<Profesiones>();
		ModelAndView pagina = new ModelAndView();
		
		mostrarmensaje = mantenimientoServicio.eliminarProfesiones(id);
		listarProfesiones = mantenimientoServicio.listarProfesiones();
		
		
		pagina.addObject("profesiones",formProfesion);
		pagina.addObject("listarProfesiones",listarProfesiones);
		pagina.setViewName("admin/profesiones/listar");
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		return pagina;
	}
	
	
	// ==================================================================================
	
	
	// ====================== CRUD CIUDADANO============================================
	
	
	@GetMapping(value = { "/remitente" })
	public ModelAndView remitente(HttpServletRequest request,HttpServletResponse res) {

		logger.info("======================= INFO 9 remitente================");
		ModelAndView pagina = new ModelAndView();
		pagina.setViewName("admin/remitente");
		return pagina;
	}
	// ==================================================================================
	
	
	// ========================== CRUD TUPAC  ===========================================
		@GetMapping(value = { "/listarTupac" })
		public ModelAndView tupac(HttpServletRequest request,HttpServletResponse res) {
			logger.info("======================= INFO 15 tupac================");
			Tupac formTupac = new Tupac();
			List<Tupac> listaTupac = new  ArrayList<Tupac>();
			listaTupac= mantenimientoServicio.listarTupac();
			
			ModelAndView pagina = new ModelAndView();
			pagina.addObject("tupac",formTupac);
			pagina.addObject("listaTupac",listaTupac);
			pagina.setViewName("admin/tupac/tupac");
			return pagina;
		}
		
		
		@GetMapping(value = {"/nuevoTupac"})
		public ModelAndView nuevoTupac(HttpServletRequest request,HttpServletResponse res) {
			
			Tupac tupac = new Tupac();
			List<Seleccion> listaSeleccion = new ArrayList<Seleccion>();
			List<Seleccion> listaTipoDias = new ArrayList<Seleccion>();
		 
			listaSeleccion = mantenimientoServicio.listarOficinasCombo();
			listaTipoDias = mantenimientoServicio.listarTipoDiasCombo();
			 
			ModelAndView pagina = new ModelAndView();
			pagina.addObject("tupac",tupac); 
			pagina.addObject("cbOficinas", listaSeleccion);
			pagina.addObject("cbTipoDias", listaTipoDias);
			pagina.setViewName("admin/tupac/nuevoTupac");
			return pagina;
		}
		
		
		@PostMapping(value = {"/guardarTupac"})
		public ModelAndView guardarTupac(@Valid Tupac formTupac,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) {
			 
			List<Seleccion> listaSeleccion = new ArrayList<Seleccion>();
			List<Seleccion> listaTipoDias = new ArrayList<Seleccion>();
			ModelAndView pagina = new ModelAndView();
			MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
			
			listaSeleccion = mantenimientoServicio.listarOficinasCombo();
			listaTipoDias = mantenimientoServicio.listarTipoDiasCombo();
			
			
			if (null != errors && errors.getErrorCount() > 0) {
				pagina.addObject("tupac",formTupac); 
				pagina.addObject("cbOficinas", listaSeleccion);
				pagina.addObject("cbTipoDias", listaTipoDias);
				pagina.addObject("mostrarmensaje",mostrarmensaje);
				pagina.setViewName("admin/tupac/nuevoTupac");
				return pagina;
			}
			
			mostrarmensaje = mantenimientoServicio.guardarTupac(formTupac);
			
			pagina.setViewName("admin/tupac/nuevoTupac");
			//pagina.setViewName("redirect:/admin/mantenimiento/listarTupac");
			pagina.addObject("tupac",formTupac); 
			pagina.addObject("cbOficinas", listaSeleccion);
			pagina.addObject("cbTipoDias", listaTipoDias);
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			return pagina;
		}
		
		@PostMapping(value = {"/buscarTupac"})
		public ModelAndView buscarTupac(HttpServletRequest request,HttpServletResponse res,@ModelAttribute Tupac formTupac) {
			 
			List<Tupac> listaTupac = new  ArrayList<Tupac>();
			
			listaTupac = mantenimientoServicio.buscarTupac(formTupac); 
			
			ModelAndView pagina = new ModelAndView();
			pagina.addObject("tupac",new Tupac());
			pagina.addObject("listaTupac",listaTupac);
			pagina.setViewName("admin/tupac/tupac");
			return pagina;
			
		}
		
		
		@GetMapping(value = {"/editarTupac"})
		public ModelAndView editarTupac(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) {
			
			MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
			Tupac formTupac = new Tupac();
			List<Seleccion> listaSeleccion = new ArrayList<Seleccion>();
			List<Seleccion> listaTipoDias = new ArrayList<Seleccion>();
			List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
			ModelAndView pagina = new ModelAndView();
			
			formTupac = mantenimientoServicio.buscarTupacPorId(id);
			listaSeleccion = mantenimientoServicio.listarOficinasCombo();
			listaTipoDias = mantenimientoServicio.listarTipoDiasCombo();
			listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro(); 
			mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
			
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			pagina.addObject("tupac",formTupac); 
			pagina.addObject("cbOficinas", listaSeleccion);
			pagina.addObject("cbTipoDias", listaTipoDias);
			pagina.addObject("cbEstados", listaEstadosRegistro);
			pagina.setViewName("admin/tupac/actualizarTupac");
			return pagina;
			
		}
		
		
		@PostMapping(value = {"/actualizarTupac"})
		public ModelAndView actualizarTupac(@Valid Tupac formTupac,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) {
			 
			ModelAndView pagina = new ModelAndView();
			List<Seleccion> listaSeleccion = new ArrayList<Seleccion>();
			List<Seleccion> listaTipoDias = new ArrayList<Seleccion>();
			MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
			
			listaSeleccion = mantenimientoServicio.listarOficinasCombo();
			listaTipoDias = mantenimientoServicio.listarTipoDiasCombo();
			mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
			
			if (null != errors && errors.getErrorCount() > 0) {
				pagina.addObject("tupac",formTupac); 
				pagina.addObject("cbOficinas", listaSeleccion);
				pagina.addObject("cbTipoDias", listaTipoDias);
				pagina.addObject("mostrarmensaje",mostrarmensaje);
				pagina.setViewName("admin/tupac/nuevoTupac");
				return pagina;
			}
 
			mostrarmensaje = mantenimientoServicio.actualizarTupac(formTupac);
  
			//pagina.setViewName("redirect:/admin/mantenimiento/listarTupac");
			pagina.setViewName("admin/tupac/nuevoTupac");
			pagina.addObject("tupac",formTupac); 
			pagina.addObject("cbOficinas", listaSeleccion);
			pagina.addObject("cbTipoDias", listaTipoDias); 
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			return pagina;
		}
		
		
		@GetMapping(value = {"/eliminarTupac"})
		public ModelAndView eliminarTupac(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) {
			Tupac formTupac = new Tupac();
			List<Tupac> listaTupac = new  ArrayList<Tupac>(); 
			MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
			
			mostrarmensaje = mantenimientoServicio.eliminarTupac(id);
			listaTupac= mantenimientoServicio.listarTupac();
			
			ModelAndView pagina = new ModelAndView();
			pagina.addObject("tupac",formTupac);
			pagina.addObject("listaTupac",listaTupac);
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			pagina.setViewName("admin/tupac/tupac");
			return pagina;
		}
		
		@GetMapping(value = {"/editarRequisitosTupac"})
		public ModelAndView editarRequisitosTupac(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) {
			Tupac infoTupac = new Tupac();
			RequisitosTupac formRequisitosTupac = new RequisitosTupac();
			List<Seleccion> cbRequisitos = new ArrayList<Seleccion>();
			List<RequisitosTupac> listaRequisitosTupac = new ArrayList<RequisitosTupac>();
			MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
			ModelAndView pagina = new ModelAndView();
			
			infoTupac = mantenimientoServicio.buscarTupacPorId(id);
			cbRequisitos = mantenimientoServicio.listarRequisitosTupac();
			listaRequisitosTupac = mantenimientoServicio.listarRequisitosTupacPorIdTupac(infoTupac.getTUPACPK());
			mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
			formRequisitosTupac.setTUPACFK(id);
			
			
			pagina.addObject("requisitosTupac",formRequisitosTupac);
			pagina.addObject("infoTupac",infoTupac);
			pagina.addObject("cbRequisitos",cbRequisitos);
			pagina.addObject("listaRequisitosTupac",listaRequisitosTupac);
			pagina.setViewName("admin/tupac/asignarReqTupac");
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			return pagina;
		}
		
		
		@PostMapping(value = {"/guardarRequisitosTupac"})
		public ModelAndView guardarRequisitosTupac(HttpServletRequest request,HttpServletResponse res,@ModelAttribute RequisitosTupac formRequisitosTupac) {
			Tupac infoTupac = new Tupac(); 
			List<Seleccion> cbRequisitos = new ArrayList<Seleccion>();
			MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
			List<RequisitosTupac> listaRequisitosTupac = new ArrayList<RequisitosTupac>();
			
			infoTupac = mantenimientoServicio.buscarTupacPorId(formRequisitosTupac.getTUPACFK());
			cbRequisitos = mantenimientoServicio.listarRequisitosTupac();
			mostrarmensaje = mantenimientoServicio.guardarRequisitosTupac(formRequisitosTupac);
			listaRequisitosTupac = mantenimientoServicio.listarRequisitosTupacPorIdTupac(infoTupac.getTUPACPK());
	 
			ModelAndView pagina = new ModelAndView();
			pagina.addObject("requisitosTupac",formRequisitosTupac);
			pagina.addObject("infoTupac",infoTupac);
			pagina.addObject("cbRequisitos",cbRequisitos);
			pagina.addObject("cbRequisitos",cbRequisitos);
			pagina.addObject("listaRequisitosTupac",listaRequisitosTupac);
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			pagina.setViewName("admin/tupac/asignarReqTupac");
			return pagina;
			
		}
		
		
		@GetMapping(value = {"/activarRequisitoTupac"})
		public ModelAndView activarRequisitoTupac(HttpServletRequest request,HttpServletResponse res,@RequestParam Long idTupac,@RequestParam Long idRequisito) { 
			Tupac infoTupac = new Tupac();
			RequisitosTupac formRequisitosTupac = new RequisitosTupac();
			List<Seleccion> cbRequisitos = new ArrayList<Seleccion>();
			List<RequisitosTupac> listaRequisitosTupac = new ArrayList<RequisitosTupac>();  
			MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
			
			infoTupac = mantenimientoServicio.buscarTupacPorId(idTupac);
			cbRequisitos = mantenimientoServicio.listarRequisitosTupac();
			mostrarmensaje=mantenimientoServicio.activarRequisitosTupac(idRequisito);
			listaRequisitosTupac = mantenimientoServicio.listarRequisitosTupacPorIdTupac(infoTupac.getTUPACPK());
			
			formRequisitosTupac.setTUPACFK(idTupac);
			
			ModelAndView pagina = new ModelAndView();
			pagina.addObject("requisitosTupac",formRequisitosTupac);
			pagina.addObject("infoTupac",infoTupac);
			pagina.addObject("cbRequisitos",cbRequisitos);
			pagina.addObject("listaRequisitosTupac",listaRequisitosTupac);
			pagina.setViewName("admin/tupac/asignarReqTupac");
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			return pagina;
		}
		
		@GetMapping(value = {"/eliminarRequisitoTupac"})
		public ModelAndView eliminarRequisitoTupac(HttpServletRequest request,HttpServletResponse res,@RequestParam Long idTupac,@RequestParam Long idRequisito) { 
			Tupac infoTupac = new Tupac();
			RequisitosTupac formRequisitosTupac = new RequisitosTupac();
			List<Seleccion> cbRequisitos = new ArrayList<Seleccion>();
			List<RequisitosTupac> listaRequisitosTupac = new ArrayList<RequisitosTupac>();
			 
			MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
			
			infoTupac = mantenimientoServicio.buscarTupacPorId(idTupac);
			cbRequisitos = mantenimientoServicio.listarRequisitosTupac();
			mostrarmensaje=mantenimientoServicio.eliminarRequisitosTupac(idRequisito);
			listaRequisitosTupac = mantenimientoServicio.listarRequisitosTupacPorIdTupac(infoTupac.getTUPACPK());
			
			formRequisitosTupac.setTUPACFK(infoTupac.getTUPACPK());
			
			ModelAndView pagina = new ModelAndView();
			pagina.addObject("requisitosTupac",formRequisitosTupac);
			pagina.addObject("infoTupac",infoTupac);
			pagina.addObject("cbRequisitos",cbRequisitos);
			pagina.addObject("listaRequisitosTupac",listaRequisitosTupac);
			pagina.setViewName("admin/tupac/asignarReqTupac");
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			return pagina;
		}
		
		// ==================================================================================
		
		
	// ==========================  CRUD REQUISITOS  =========================================
	@GetMapping(value = { "/listarRequisitos" })
	public ModelAndView requisitostupac(HttpServletRequest request,HttpServletResponse res) {

		logger.info("======================= INFO 10 requisitostupac================");
		List<Requisitos> listaRequisitos = new ArrayList<Requisitos>();
		Requisitos formRequisitos = new Requisitos();
		listaRequisitos = mantenimientoServicio.listarRequisitos();
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("listaRequisitos",listaRequisitos);
		pagina.addObject("requisitos",formRequisitos);
		pagina.setViewName("admin/tupac/requisitostupac");
		return pagina;
	}
	
	
 
	
	@GetMapping(value = {"/nuevoRequisitos"})
	public ModelAndView nuevoRequisitos(HttpServletRequest request,HttpServletResponse res) {
		Requisitos formRequisitos = new Requisitos();
		ModelAndView pagina = new ModelAndView(); 
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		pagina.setViewName("admin/tupac/nuevoRequisito");
		pagina.addObject("requisitos",formRequisitos);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		
		return pagina;
	}
	
	
	@PostMapping(value = {"/guardarRequisitos"})
	public ModelAndView guardarRequisitos(@Valid Requisitos requisitos,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) {
		
		ModelAndView pagina = new ModelAndView(); 
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		if (null != errors && errors.getErrorCount() > 0) {
			pagina.addObject("requisitos",requisitos);
			pagina.setViewName("admin/tupac/nuevoRequisito");
			return pagina;
		}
		
		mostrarmensaje = mantenimientoServicio.guardarRequisitos(requisitos);
		
		pagina.setViewName("admin/tupac/nuevoRequisito");
		pagina.addObject("requisitosTupac",requisitos); 
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	@PostMapping(value = {"/buscarRequisitos"})
	public ModelAndView buscarRequisitos(HttpServletRequest request,HttpServletResponse res,@ModelAttribute Requisitos formRequisitos) {
		
		List<Requisitos> listaRequisitos = new ArrayList<Requisitos>(); 
		listaRequisitos = mantenimientoServicio.buscarRequisitos(formRequisitos);
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("listaRequisitos",listaRequisitos);
		pagina.addObject("requisitos",formRequisitos);
		pagina.setViewName("admin/tupac/requisitostupac");
		return pagina;
		
	}
	
	@GetMapping(value = {"/editarRequisitos"})
	public ModelAndView editarRequisitos(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) {
		Requisitos formRequisitos = new Requisitos();
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		ModelAndView pagina = new ModelAndView();
		
		formRequisitos = mantenimientoServicio.buscarRequisitosId(id);
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro();
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		pagina.setViewName("admin/tupac/actualizarRequisito");
		pagina.addObject("requisitos",formRequisitos);
		pagina.addObject("cbEstados",listaEstadosRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		
		return pagina;
	}
	
	
	
	@PostMapping(value = {"/actualizarRequisitos"})
	public ModelAndView actualizarRequisitos(@Valid Requisitos requisitos,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) {
		 
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro();
		
		if (null != errors && errors.getErrorCount() > 0) { 
			pagina.setViewName("admin/tupac/actualizarRequisito");
			pagina.addObject("requisitos",requisitos);
			pagina.addObject("cbEstados",listaEstadosRegistro);
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			return pagina;
		}
		
		
		mostrarmensaje =mantenimientoServicio.actualizarRequisitos(requisitos);
		
		
		//pagina.setViewName("redirect:/admin/mantenimiento/listarRequisitos");
		pagina.setViewName("admin/tupac/actualizarRequisito");
		pagina.addObject("requisitos",requisitos);
		pagina.addObject("cbEstados",listaEstadosRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	@GetMapping(value = {"/eliminarRequisitos"})
	public ModelAndView eliminarRequisitos(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) {
		 
		List<Requisitos> listaRequisitos = new ArrayList<Requisitos>();
		Requisitos formRequisitos = new Requisitos();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje = mantenimientoServicio.eliminarRequisitos(id);
		listaRequisitos = mantenimientoServicio.listarRequisitos();
		
		ModelAndView pagina = new ModelAndView();
		pagina.setViewName("admin/tupac/requisitostupac");
		pagina.addObject("listaRequisitos",listaRequisitos);
		pagina.addObject("requisitos",formRequisitos);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}

	// ==================================================================================
	
 
	// ========================== CRUD TIPO DE DOCUMENTO  ============================= 
	@GetMapping(value = { "/listartipodocumento" })
	public ModelAndView tipoDocumento(HttpServletRequest request,HttpServletResponse res) {

		logger.info("======================= INFO 12 tipodocumento===============");
		TipoDocumentos  tipoDocumentos = new TipoDocumentos();
		 List<TipoDocumentos> listaTipoDocumento = new ArrayList<TipoDocumentos>();
		 
		 listaTipoDocumento = mantenimientoServicio.listarTipoDocumento();
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("tipoDocumentos", tipoDocumentos);
		pagina.addObject("listarTipoDocumento",listaTipoDocumento);
		pagina.setViewName("admin/tipodocumento/listar");
		return pagina;
	}
	
	
	
	
	
	@GetMapping(value = {"/nuevo_tipodocumento"})
	public ModelAndView nuevoTipoDocumento(HttpServletRequest request,HttpServletResponse res) {
		TipoDocumentos  tipoDocumentos = new TipoDocumentos();
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		pagina.addObject("tipoDocumentos", tipoDocumentos);
		pagina.setViewName("admin/tipodocumento/nueva");
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("urltramite",urlTramite);
		return pagina;	
	}
	
	
	@PostMapping(value= {"/buscarTipoDocumento"})
	public ModelAndView buscarTipoDocumento(@ModelAttribute TipoDocumentos formTipoDocumento ,HttpServletRequest request,HttpServletResponse res) {
		List<TipoDocumentos> listaTipoDocumento = new ArrayList<TipoDocumentos>();
		listaTipoDocumento = mantenimientoServicio.buscarTipoDocumentos(formTipoDocumento);
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("tipoDocumentos", formTipoDocumento);
		pagina.addObject("listarTipoDocumento",listaTipoDocumento);
		pagina.setViewName("admin/tipodocumento/listar");
		return pagina;	
	}
	
	@PostMapping(value = {"/guardarTipoDocumento"})
	public ModelAndView guardarTipoDocumento(@Valid TipoDocumentos tipoDocumentos,BindingResult result, Errors errors ,HttpServletRequest request,HttpServletResponse res) {
		
		 
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		if (null != errors && errors.getErrorCount() > 0) {
			pagina.addObject("tipoDocumentos", tipoDocumentos);
			pagina.setViewName("admin/tipodocumento/nueva");
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			pagina.addObject("urltramite",urlTramite);
			return pagina;
		}
		
		mostrarmensaje = mantenimientoServicio.guardarTipoDocumentos(tipoDocumentos);
		
		//pagina.setViewName("redirect:/admin/mantenimiento/listartipodocumento");
		pagina.setViewName("admin/tipodocumento/nueva");
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("tipoDocumentos", tipoDocumentos);
		pagina.addObject("urltramite",urlTramite);
		return pagina;	
	}
	
	
	@GetMapping(value = {"/eliminaripoDocumento"})
	@ResponseBody
	public ModelAndView eliminarTipoDocumento(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) {
		logger.info("======================= ELIMINAR TIPO DOCUMENTO ================"+id);
		
		
		TipoDocumentos  tipoDocumentos = new TipoDocumentos();
		List<TipoDocumentos> listaTipoDocumento = new ArrayList<TipoDocumentos>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje = mantenimientoServicio.eliminarTipoDocumento(id);
		listaTipoDocumento = mantenimientoServicio.listarTipoDocumento();
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("tipoDocumentos", tipoDocumentos);
		pagina.addObject("listarTipoDocumento",listaTipoDocumento);
		pagina.setViewName("admin/tipodocumento/listar");
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	
	@GetMapping(value = {"/editarTipoDocumento"})
	public ModelAndView buscarTipoDocumentoPorId(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		TipoDocumentos  tipoDocumentos = new TipoDocumentos();
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		
		tipoDocumentos = mantenimientoServicio.buscarTipoDocumentoId(id);
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro();
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("tipoDocumentos", tipoDocumentos);
		pagina.addObject("cbEstados", listaEstadosRegistro);
		pagina.setViewName("admin/tipodocumento/actualizar");
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("urltramite",urlTramite);
		return pagina;
	}
	
	@PostMapping(value = {"/actualizarTipoDocumento"})
	public ModelAndView actualizarTipoDocumento(@Valid TipoDocumentos tipoDocumentos,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) {
		
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>(); 
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro();
		
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		if (null != errors && errors.getErrorCount() > 0) { 
			pagina.addObject("tipoDocumentos", tipoDocumentos);
			pagina.addObject("cbEstados", listaEstadosRegistro);
			pagina.setViewName("admin/tipodocumento/actualizar");
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			pagina.addObject("urltramite",urlTramite);
			return pagina;
		}
 
		mostrarmensaje = mantenimientoServicio.actualizarTipoDocumento(tipoDocumentos);
		tipoDocumentos = mantenimientoServicio.buscarTipoDocumentoId(tipoDocumentos.getNIDTIPODOCUMENTOPK());
 
		//pagina.setViewName("redirect:/admin/mantenimiento/listartipodocumento");
		pagina.setViewName("admin/tipodocumento/actualizar");
		pagina.addObject("tipoDocumentos", tipoDocumentos);
		pagina.addObject("cbEstados", listaEstadosRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("urltramite",urlTramite);
		return pagina;	
	}
	
	// ==================================================================================
	
	
	// ========================== CRUD TIPO TRAMITE  ===================================
	
	@GetMapping(value = { "/listartipotramite" })
	public ModelAndView tipoTramite(HttpServletRequest request,HttpServletResponse res) {

		logger.info("======================= INFO 13 tipotramite================");
		List<TipoTramite> listarTipoTramite = new ArrayList<TipoTramite>();
		TipoTramite formTipoTramite = new TipoTramite();
		listarTipoTramite = mantenimientoServicio.listarTipoTramite();
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("tipoTramite", formTipoTramite);
		pagina.addObject("listarTipoTramite",listarTipoTramite);
		pagina.setViewName("admin/tipotramite/listar");
		return pagina;
	}
	
	
	@PostMapping(value = {"/buscarTipoTramite"})
	public ModelAndView buscarTipoTramite(HttpServletRequest request,HttpServletResponse res,@ModelAttribute TipoTramite formTipoTramite ) {
		List<TipoTramite> listarTipoTramite = new ArrayList<TipoTramite>();
		listarTipoTramite = mantenimientoServicio.buscarTipoTramite(formTipoTramite);
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("tipoTramite", formTipoTramite);
		pagina.addObject("listarTipoTramite",listarTipoTramite);
		pagina.setViewName("admin/tipotramite/listar");
		return pagina;
	}
	
	@GetMapping(value = {"/editarTipoTramite"}) 
	public ModelAndView buscarTipoTramiteId(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) {
		TipoTramite formTipoTramite = new TipoTramite();
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		ModelAndView pagina = new ModelAndView();
		
		formTipoTramite = mantenimientoServicio.buscarTipoTramiteId(id);
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro();
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		pagina.setViewName("admin/tipotramite/actualizar");
		pagina.addObject("tipoTramite", formTipoTramite);
		pagina.addObject("cbEstados", listaEstadosRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	@GetMapping(value = {"/nuevo_tipotramite"})
	public ModelAndView nuevoTipoTramite(HttpServletRequest request,HttpServletResponse res) {
		TipoTramite formTipoTramite = new TipoTramite();
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		pagina.addObject("tipoTramite", formTipoTramite); 
		pagina.setViewName("admin/tipotramite/nueva");
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	
	@PostMapping(value = {"/guardarformTipoTramite"})
	public ModelAndView grabarTipoTramite(@Valid TipoTramite formTipoTramite,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) { 
		 
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		
		if (null != errors && errors.getErrorCount() > 0) {
			pagina.setViewName("admin/tipotramite/nueva");
			pagina.addObject("tipoTramite", formTipoTramite); 
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			return pagina;
		}
		
		
		mostrarmensaje = mantenimientoServicio.guardarTipoTramite(formTipoTramite); 
		pagina.setViewName("admin/tipotramite/nueva");
		//pagina.setViewName("redirect:/admin/mantenimiento/listartipotramite");
		pagina.addObject("tipoTramite", formTipoTramite); 
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	
	@GetMapping(value = {"/eliminarTipoTramite"})
	@ResponseBody
	public ModelAndView eliminarTipoTramite(HttpServletRequest request,HttpServletResponse res,@RequestParam Long id) {
		TipoTramite formTipoTramite = new TipoTramite();
		List<TipoTramite> listarTipoTramite = new ArrayList<TipoTramite>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje = mantenimientoServicio.eliminarTipoTramite(id);
		listarTipoTramite = mantenimientoServicio.listarTipoTramite();
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("tipoTramite", formTipoTramite); 
		pagina.addObject("listarTipoTramite",listarTipoTramite);
		pagina.setViewName("admin/tipotramite/listar");
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	

	
	@PostMapping(value = {"/actualizarformTipoTramite"})
	public ModelAndView actualizarTipoTramite(@Valid TipoTramite tipoTramite,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) {
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		ModelAndView pagina = new ModelAndView();
		
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro();
		
		if (null != errors && errors.getErrorCount() > 0) {
			pagina.addObject("tipoTramite", tipoTramite);  
			pagina.addObject("cbEstados", listaEstadosRegistro);
			pagina.setViewName("admin/tipotramite/actualizar");
			return pagina;
		}
		
		mostrarmensaje = mantenimientoServicio.actualizarTipoTramite(tipoTramite);
		
		pagina.setViewName("admin/tipotramite/actualizar");
		//pagina.setViewName("redirect:/admin/mantenimiento/listartipotramite");
		pagina.addObject("tipoTramite", tipoTramite);  
		pagina.addObject("cbEstados", listaEstadosRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
		
	}
	
	// ==================================================================================

	// ========================  CRUD USUARIO ===========================================
	
	@GetMapping(value = {"/listarUsuario"})
	public ModelAndView listarUsuarios(HttpServletRequest request,HttpServletResponse res) {
		Usuarios formUsuarioPersona = new Usuarios();
		List<Seleccion> cbCriterioBusqueda = new ArrayList<Seleccion>();
		List<Usuarios> listarUsuarioPersona = new ArrayList<Usuarios>();
		
		cbCriterioBusqueda= mantenimientoServicio.cbCriteriosBusquedaTrabajador();
		listarUsuarioPersona = mantenimientoServicio.listarUsuarioPersona();
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("usuarios",formUsuarioPersona);
		pagina.addObject("cbCriterioBusqueda",cbCriterioBusqueda);
		pagina.addObject("listarUsuarioPersona",listarUsuarioPersona);
		pagina.setViewName("admin/usuario/listar");
		return pagina;
	}
	
	@PostMapping(value = {"/buscarUsuariosPersona"})
	public ModelAndView buscarUsuariosPersona(HttpServletRequest request,HttpServletResponse res,@ModelAttribute Usuarios formUsuarioPersona) {
		 
		List<Usuarios> listaUsuariosPersona = new ArrayList<Usuarios>();
		List<Seleccion> cbCriterioBusqueda = new ArrayList<Seleccion>();
		
		cbCriterioBusqueda= mantenimientoServicio.cbCriteriosBusquedaTrabajador();
		listaUsuariosPersona = mantenimientoServicio.buscarUsuarioPersona(formUsuarioPersona);
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("usuarios",formUsuarioPersona);
		pagina.addObject("cbCriterioBusqueda",cbCriterioBusqueda);
		pagina.addObject("listarUsuarioPersona",listaUsuariosPersona);
		pagina.setViewName("admin/usuario/listar");
		return pagina;
	}
	
	
	@GetMapping(value = {"/nuevoUsuario"})
	public ModelAndView nuevoUsuario(HttpServletRequest request,HttpServletResponse res,@RequestParam Long IDTRABAJADOR) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		List<Seleccion> cbOficinas = new ArrayList<Seleccion>();
		Usuarios formUsuarioPersona = new Usuarios();
		List<Seleccion> cbPerfiles = new ArrayList<Seleccion>(); 
		Persona infoPersona = new Persona();
		ModelAndView pagina = new ModelAndView();
		
		cbOficinas = mantenimientoServicio.listarOficinasCombo();
		cbPerfiles = mantenimientoServicio.listarPerfiles(); 
		infoPersona = mantenimientoServicio.buscarTrabajadorPersonaPorId(IDTRABAJADOR);
		formUsuarioPersona.setNTRABAJADORFK(IDTRABAJADOR);
		formUsuarioPersona.setVNOMBRECOMPLETO(infoPersona.getVAPEPATERNO().concat(" ").concat(infoPersona.getVAPEMATERNO().concat(" ").concat(infoPersona.getVNOMBRE())));
		formUsuarioPersona.setVCORREO(infoPersona.getVCORREO());
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		
		pagina.addObject("usuarios",formUsuarioPersona);
		pagina.addObject("cbOficinas", cbOficinas);
		pagina.addObject("cbPerfiles", cbPerfiles); 
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.setViewName("admin/usuario/nuevo");
		return pagina;
	}
	
	
	@PostMapping(value = {"/guardarformUsuarioPersona"})
	public ModelAndView guardarformUsuarioPersona(@Valid Usuarios formUsuarioPersona,BindingResult result, Errors errors,HttpServletRequest request,HttpServletResponse res) {
		
		
		List<Seleccion> cbOficinas = new ArrayList<Seleccion>(); 
		List<Seleccion> cbPerfiles = new ArrayList<Seleccion>(); 
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		cbOficinas = mantenimientoServicio.listarOficinasCombo();
		cbPerfiles = mantenimientoServicio.listarPerfiles();
		
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		if (null != errors && errors.getErrorCount() > 0) {
			pagina.addObject("usuarios",formUsuarioPersona);
			pagina.addObject("cbOficinas", cbOficinas);
			pagina.addObject("cbPerfiles", cbPerfiles); 
			pagina.setViewName("admin/usuario/nuevo");
			pagina.addObject("mostrarmensaje",mostrarmensaje);
			return pagina;
		}
		
		
		mostrarmensaje = mantenimientoServicio.guardarUsuarioPersona(formUsuarioPersona);
		//infoPersona = mantenimientoServicio.buscarTrabajadorPersonaPorId(formUsuarioPersona.getNTRABAJADORFK());
		
		//pagina.setViewName("redirect:/admin/mantenimiento/listarUsuario");
		pagina.setViewName("admin/usuario/nuevo");
		pagina.addObject("usuarios",formUsuarioPersona);
		pagina.addObject("cbOficinas", cbOficinas);
		pagina.addObject("cbPerfiles", cbPerfiles); 
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	@GetMapping(value = {"/editarUsuarioPersona"})
	public ModelAndView editarUsuarioPersona(HttpServletRequest request,HttpServletResponse res,@RequestParam Long IDUSUARIO) {
		
		Usuarios  formUsuarioPersona = new Usuarios();
		List<Seleccion> cbOficinas = new ArrayList<Seleccion>(); 
		List<Seleccion> cbPerfiles = new ArrayList<Seleccion>(); 
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		
		 
		 formUsuarioPersona = mantenimientoServicio.buscarUsuarioPersonaPorId(IDUSUARIO);
		 cbOficinas = mantenimientoServicio.listarOficinasCombo();
		 cbPerfiles = mantenimientoServicio.listarPerfiles(); 
		 listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro();
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("usuarios",formUsuarioPersona);
		pagina.addObject("cbOficinas", cbOficinas);
		pagina.addObject("cbPerfiles", cbPerfiles); 
		pagina.addObject("cbEstados", listaEstadosRegistro);
		pagina.setViewName("admin/usuario/actualizar");
		return pagina;
	}
	
	@PostMapping(value = {"/actualizarformUsuarioPersona"})
	public ModelAndView actualizarformUsuarioPersona(HttpServletRequest request,HttpServletResponse res,@ModelAttribute Usuarios formUsuarioPersona) {
		
		
		List<Seleccion> cbOficinas = new ArrayList<Seleccion>(); 
		List<Seleccion> cbPerfiles = new ArrayList<Seleccion>();
		List<Seleccion> listaEstadosRegistro = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		mostrarmensaje = mantenimientoServicio.actualizarUsuarioPersona(formUsuarioPersona);
		cbOficinas = mantenimientoServicio.listarOficinasCombo();
		cbPerfiles = mantenimientoServicio.listarPerfiles(); 
		listaEstadosRegistro = mantenimientoServicio.listarEstadosRegistro();
		
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("cbOficinas", cbOficinas);
		pagina.addObject("cbPerfiles", cbPerfiles);
		pagina.addObject("cbEstados", listaEstadosRegistro);
		pagina.addObject("usuarios",formUsuarioPersona);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.setViewName("admin/usuario/actualizar");
		return pagina;
	}
	
	@GetMapping(value = {"/eliminarUsuarioPersona"})
	public ModelAndView eliminarUsuarioPersona(HttpServletRequest request,HttpServletResponse res,@RequestParam Long IDUSUARIO,@RequestParam Long IDUSUARIOPERFIL) {
		Persona formUsuarioPersona = new Persona();
		List<Seleccion> cbCriterioBusqueda = new ArrayList<Seleccion>();
		List<Usuarios> listarUsuarioPersona = new ArrayList<Usuarios>();
		
		mantenimientoServicio.eliminarUsuarioPersona(IDUSUARIO,IDUSUARIOPERFIL);
		cbCriterioBusqueda= mantenimientoServicio.cbCriteriosBusquedaTrabajador();
		listarUsuarioPersona = mantenimientoServicio.listarUsuarioPersona();
		
		ModelAndView pagina = new ModelAndView();
		pagina.addObject("usuarios",formUsuarioPersona);
		pagina.addObject("cbCriterioBusqueda",cbCriterioBusqueda);
		pagina.addObject("listarUsuarioPersona",listarUsuarioPersona);
		pagina.setViewName("admin/usuario/listar");
		return pagina;
	}
	
	// ==================================================================================
	
	
	@GetMapping(value = {"/nuevo_interno_simple"})
	public ModelAndView nuevoExternoSimple(HttpServletRequest request,HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		pagina.setViewName("admin/tramite/einterno/simple");
		return pagina;
	}
	
	
	
	@GetMapping(value = {"/listarCorrelativos"})
	public ModelAndView listarCorrelativos(HttpServletRequest request,HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView(); 
		Correlativo formCorrelativo = new Correlativo();
		List<Correlativo> listarCorrelativos = new ArrayList<Correlativo>();
		
		listarCorrelativos = mantenimientoServicio.listarCorrelativos(formCorrelativo);
		
		pagina.addObject("listarCorrelativos",listarCorrelativos);
		pagina.addObject("formCorrelativo",formCorrelativo); 
		pagina.setViewName("admin/correlativos/listar");
		return pagina;
	}
	
	@PostMapping(value= {"/buscarCorrelativo"})
	public ModelAndView buscarCorrelativo(HttpServletRequest request,HttpServletResponse res,@ModelAttribute Correlativo  formCorrelativo) {
		ModelAndView pagina = new ModelAndView(); 
		List<Correlativo> listarCorrelativos = new ArrayList<Correlativo>();
		
		listarCorrelativos = mantenimientoServicio.listarCorrelativos(formCorrelativo);
		
		pagina.addObject("listarCorrelativos",listarCorrelativos); 
		pagina.addObject("formCorrelativo",formCorrelativo); 
		pagina.setViewName("admin/correlativos/listar");
		return pagina;
	}
	
	@GetMapping(value = {"/editarCorrelativo"})
	public ModelAndView EditarCorrelativo(HttpServletRequest request,HttpServletResponse res,@RequestParam Long  idcorrelativo) {
		ModelAndView pagina = new ModelAndView(); 
		Correlativo formCorrelativo = new Correlativo();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		formCorrelativo = mantenimientoServicio.infoCorrelativo(idcorrelativo);
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		pagina.addObject("urltramite",urlTramite);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("formCorrelativo",formCorrelativo);
		pagina.setViewName("admin/correlativos/actualizar");
		return pagina;
	}
	
	@PostMapping(value = {"/actualziarCorrelativo"})
	public ModelAndView ActualizarCorrelativo(HttpServletRequest request,HttpServletResponse res,@ModelAttribute Correlativo formCorrelativo) {
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		mostrarmensaje = mantenimientoServicio.actualizarCorrelativo(formCorrelativo);
		
		pagina.addObject("urltramite",urlTramite);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		pagina.addObject("formCorrelativo",formCorrelativo);
		pagina.setViewName("admin/correlativos/actualizar");
		return pagina;
	}
	

}
