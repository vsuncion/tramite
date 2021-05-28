package com.tramite.app.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.PrePersona;
import com.tramite.app.Entidades.Seleccion;
import com.tramite.app.Servicios.MantenimientoServicio;
import com.tramite.app.Servicios.PrincipalServicio;
import com.tramite.app.utilitarios.Constantes;

@Controller
@RequestMapping("/")
public class PrinicipalController {
	
	@Autowired
	private MantenimientoServicio  mantenimientoServicio;
	
	@Autowired
	private PrincipalServicio  principalServicio;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping(value = {"/","index"})
	public String hola() {
 
	        logger.info("======================= INFO ================");
		return "index";
	}
	
	
	 
	
	@GetMapping(value = {"/admin/principal"})
	public ModelAndView paginaPrincipal(HttpServletRequest request,HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		pagina.setViewName("admin/principal");
		return pagina;
	}
	
	@GetMapping(value = {"/nuevaPersonaNatural"})
	public ModelAndView nuevaPersonaNatural() {
		ModelAndView pagina = new ModelAndView();
		PrePersona prePersona = new PrePersona();
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		pagina.setViewName("admin/persona/natural/nuevo");
		pagina.addObject("prePersona",prePersona);
		pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	@GetMapping(value = {"/nuevaPersonaJuridica"})
	public ModelAndView nuevaPersonaJuridica() {
		ModelAndView pagina = new ModelAndView();
		PrePersona prePersona = new PrePersona();
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);
		
		pagina.setViewName("admin/persona/juridica/nuevo");
		pagina.addObject("prePersona",prePersona);
		pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	
	@PostMapping(value = {"/guardarPrepersonaNatural"})
	public ModelAndView guardarPrepersonaNatural(@ModelAttribute PrePersona prePersona ,HttpServletRequest request,HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		
		// VERIFICAMOS SI LA PERSONA YA FUE REGISTRADA PREVIAMENTE
		prePersona.setNTIPO_PERSONA(Constantes.tipoPersonaNatural);
		mostrarmensaje = principalServicio.guardarPrePersona(prePersona);
		cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();
		
		pagina.setViewName("admin/persona/natural/nuevo");
		pagina.addObject("prePersona",prePersona);
		pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	
	@PostMapping(value = {"/guardarPrepersonaJuridica"})
	public ModelAndView guardarPrepersonaJuridica(@ModelAttribute PrePersona prePersona ,HttpServletRequest request,HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		
		// VERIFICAMOS SI LA PERSONA YA FUE REGISTRADA PREVIAMENTE
		prePersona.setNTIPO_PERSONA(Constantes.tipoPersonaJuridica);
		mostrarmensaje = principalServicio.guardarPrePersona(prePersona);
		cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();
		
		pagina.setViewName("admin/persona/juridica/nuevo");
		pagina.addObject("prePersona",prePersona);
		pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		pagina.addObject("mostrarmensaje",mostrarmensaje);
		return pagina;
	}
	
	
	@GetMapping(value = {"/confirmacionRegistro"})
	public ModelAndView confirmacionRegistro(@RequestParam String codigo) {
		ModelAndView pagina = new ModelAndView();
		PrePersona prePersona = new PrePersona();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		prePersona.setVCODIGOACTIVACION(codigo);
		mostrarmensaje = principalServicio.confirmacionCodigoActivacion(prePersona.getVCODIGOACTIVACION());
		
		pagina.setViewName("admin/persona/activar");
		pagina.addObject("prePersona",prePersona); 
		return pagina;
	}
	
}
