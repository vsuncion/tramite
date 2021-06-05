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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tramite.app.Entidades.Archivos;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.PrePersona;
import com.tramite.app.Entidades.Seleccion;
import com.tramite.app.Entidades.TipoDocumentos;
import com.tramite.app.Servicios.ArchivoUtilitarioServicio;
import com.tramite.app.Servicios.FijaServicio;
import com.tramite.app.Servicios.MantenimientoServicio;
import com.tramite.app.Servicios.PrincipalServicio;
import com.tramite.app.Servicios.RecursoServicio;
import com.tramite.app.utilitarios.Constantes;
import com.tramite.app.utilitarios.ConstantesArchivos;

@Controller
@RequestMapping("/")
public class PrinicipalController {

	@Autowired
	private MantenimientoServicio mantenimientoServicio;

	@Autowired
	private RecursoServicio recursoServicio;

	@Autowired
	private PrincipalServicio principalServicio;

	@Autowired
	private ArchivoUtilitarioServicio archivoUtilitarioServicio;

	Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping(value = { "/", "index" })
	public ModelAndView hola() {
		logger.info("======================= INFO ================");
		ModelAndView pagina = new ModelAndView();
		pagina.setViewName("index");
		return pagina;
	}

	@GetMapping(value = { "/admin/principal" })
	public ModelAndView paginaPrincipal(HttpServletRequest request, HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		pagina.setViewName("admin/principal");
		return pagina;
	}

	@GetMapping(value = { "/nuevaPersonaNatural" })
	public ModelAndView nuevaPersonaNatural() {
		ModelAndView pagina = new ModelAndView();
		PrePersona prePersona = new PrePersona();
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);

		pagina.setViewName("admin/persona/natural/nuevo");
		pagina.addObject("prePersona", prePersona);
		pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		pagina.addObject("mostrarmensaje", mostrarmensaje);
		return pagina;
	}

	@GetMapping(value = { "/nuevaPersonaJuridica" })
	public ModelAndView nuevaPersonaJuridica() {
		ModelAndView pagina = new ModelAndView();
		PrePersona prePersona = new PrePersona();
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();
		mostrarmensaje.setCodigo(Constantes.transaccionSinAccion);

		pagina.setViewName("admin/persona/juridica/nuevo");
		pagina.addObject("prePersona", prePersona);
		pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		pagina.addObject("mostrarmensaje", mostrarmensaje);
		return pagina;
	}

	@PostMapping(value = { "/guardarPrepersonaNatural" })
	public ModelAndView guardarPrepersonaNatural(@ModelAttribute PrePersona prePersona, HttpServletRequest request,
			HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();

		// VERIFICAMOS SI LA PERSONA YA FUE REGISTRADA PREVIAMENTE
		prePersona.setNTIPO_PERSONA(Constantes.tipoPersonaNatural);
		mostrarmensaje = principalServicio.guardarPrePersona(prePersona);
		cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();

		pagina.setViewName("admin/persona/natural/nuevo");
		pagina.addObject("prePersona", prePersona);
		pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		pagina.addObject("mostrarmensaje", mostrarmensaje);
		return pagina;
	}

	@PostMapping(value = { "/guardarPrepersonaJuridica" })
	public ModelAndView guardarPrepersonaJuridica(@ModelAttribute PrePersona prePersona, HttpServletRequest request,
			HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();

		// VERIFICAMOS SI LA PERSONA YA FUE REGISTRADA PREVIAMENTE
		prePersona.setNTIPO_PERSONA(Constantes.tipoPersonaJuridica);
		mostrarmensaje = principalServicio.guardarPrePersona(prePersona);
		cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();

		pagina.setViewName("admin/persona/juridica/nuevo");
		pagina.addObject("prePersona", prePersona);
		pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		pagina.addObject("mostrarmensaje", mostrarmensaje);
		return pagina;
	}

	@GetMapping(value = { "/confirmacionRegistro" })
	public ModelAndView confirmacionRegistro(HttpServletRequest request, HttpServletResponse res,
			@RequestParam String codigo) {
		ModelAndView pagina = new ModelAndView();
		PrePersona prePersona = new PrePersona();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		prePersona.setVCODIGOACTIVACION(codigo);
		mostrarmensaje = principalServicio.confirmacionCodigoActivacion(prePersona.getVCODIGOACTIVACION());

		pagina.setViewName("admin/persona/activar");
		pagina.addObject("prePersona", prePersona);
		return pagina;
	}

	@GetMapping(value = { "/nueva_busqueda_simple" })
	public ModelAndView buscarSolicitante() {
		ModelAndView pagina = new ModelAndView();
		Expediente formExpediente = new Expediente();
		List<Seleccion> cbTipoDocumentoPersona = new ArrayList<Seleccion>();

		cbTipoDocumentoPersona = recursoServicio.cbTipoDocumentoPersona();

		pagina.setViewName("admin/tramite/buscarSimple");
		pagina.addObject("formExpediente", formExpediente);
		pagina.addObject("cbTipoDocumentoPersona", cbTipoDocumentoPersona);
		return pagina;
	}

	@PostMapping(value = { "/buscarSolicitante" })
	public ModelAndView buscarCidadano(@ModelAttribute Expediente formExpediente, HttpServletRequest request,
			HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		List<Seleccion> cbTipoDocumentoPersona = new ArrayList<Seleccion>();
		Persona persona = new Persona();

		persona = principalServicio.busquedaSolicitante(formExpediente);
		cbTipoDocumentoPersona = recursoServicio.cbTipoDocumentoPersona();

		if (persona != null) {
			pagina.setViewName("redirect:/nuevo_tramite_simple?tipopersona=" + formExpediente.getTIPODOCUMENTOBUSCAR()
					+ "&numero=" + formExpediente.getCAJABUSQUEDA().trim());
		} else {
			pagina.setViewName("admin/tramite/buscarSimple");
		}

		pagina.addObject("formExpediente", formExpediente);
		pagina.addObject("cbTipoDocumentoPersona", cbTipoDocumentoPersona);
		return pagina;
	}

	@GetMapping(value = { "/nuevo_tramite_simple" })
	public ModelAndView nuevoExternoSimple(HttpServletRequest request, HttpServletResponse res,
			@RequestParam int tipopersona, @RequestParam String numero) {
		Expediente formExpediente = new Expediente();
		ModelAndView pagina = new ModelAndView();
		List<Seleccion> cbTipoDocumentoPersona = new ArrayList<Seleccion>();
		List<Seleccion> cbTipoDocumento = new ArrayList<Seleccion>();
		Persona persona = new Persona();

		cbTipoDocumentoPersona = recursoServicio.cbTipoDocumentoPersona();
		cbTipoDocumento = recursoServicio.cbTipoDocuemnto();
		formExpediente.setTIPODOCUMENTOBUSCAR(tipopersona);
		formExpediente.setCAJABUSQUEDA(numero);
		formExpediente.setNTIPOPERSONA(tipopersona);

		persona = principalServicio.busquedaSolicitante(formExpediente);

		if (tipopersona == Constantes.tipoPersonaJuridica) {
			formExpediente.setVRUC(persona.getVRUC());
			formExpediente.setVRAZON_SOCIAL(persona.getVRAZONSOCIAL());
		}

		formExpediente.setNTIPOPERSONA(tipopersona);
		formExpediente.setPERSONAFK(persona.getNIDPERSONAPK());
		formExpediente.setVNOMBRE(persona.getVNOMBRE());
		formExpediente.setVAPELLIDO_PATERNO(persona.getVAPEPATERNO());
		formExpediente.setVAPELLIDO_MATERNO(persona.getVAPEMATERNO());
		formExpediente.setVNUMERODOCUMENTO(persona.getVNUMERODOC());
		formExpediente.setVCORREO(persona.getVCORREO());
		formExpediente.setVDIRECCION(persona.getVDIRECCION());
		formExpediente.setVTELEFONO(persona.getVTELEFONO());
		formExpediente.setVNUMERODOCUMENTO("");

		pagina.setViewName("admin/tramite/externo/simple");
		pagina.addObject("formExpediente", formExpediente);
		pagina.addObject("cbTipoDocumentoPersona", cbTipoDocumentoPersona);
		pagina.addObject("cbTipoDocumento", cbTipoDocumento);
		return pagina;
	}

	@PostMapping(value = { "/grabar_tramite_simple" })
	public ModelAndView grabarTramiteSimple(@ModelAttribute Expediente formExpediente,
			@RequestParam("varchivosubida") MultipartFile farchvio, HttpServletRequest request,
			HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		boolean respuesta = false;

		// SUBIMOS EL DOCUMENTO
		if (farchvio != null && farchvio.getSize() > 0) {
			Archivos archivo = new Archivos();

			archivo = archivoUtilitarioServicio.cargarArchivo(farchvio, ConstantesArchivos.getCorrelativoArchivo());

			if (archivo.isVerificarCarga() == true) {
				logger.info("ingresi el archivo");
				formExpediente.setVUBICACION_ARCHIVO(archivo.getRuta());
				formExpediente.setVNOMBRE_ARCHIVO(archivo.getNombre());
				formExpediente.setVEXTENSION(archivo.getExtension());
			}
		}

		// OBTENEOS EL NUMERO DE EXPEDIENTE
		String correlativoExpediente = recursoServicio.numeroExpediente();
		formExpediente.setVCODIGO_EXPEDIENTE(correlativoExpediente);

		respuesta = principalServicio.guardarExpedienteSimple(formExpediente);

		pagina.setViewName("admin/tramite/externo/respuesta_simple");
		return pagina;
	}

	@GetMapping(value = { "/nueva_busqueda_tupa" })
	public ModelAndView buscarSolicitanteTupa() {
		ModelAndView pagina = new ModelAndView();
		Expediente formExpediente = new Expediente();
		List<Seleccion> cbTipoDocumentoPersona = new ArrayList<Seleccion>();

		cbTipoDocumentoPersona = recursoServicio.cbTipoDocumentoPersona();

		pagina.setViewName("admin/tramite/buscarTupa");
		pagina.addObject("formExpediente", formExpediente);
		pagina.addObject("cbTipoDocumentoPersona", cbTipoDocumentoPersona);
		return pagina;
	}

	@PostMapping(value = { "/buscarSolicitanteTupa" })
	public ModelAndView buscarCiudadanoTupa(@ModelAttribute Expediente formExpediente, HttpServletRequest request,
			HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		List<Seleccion> cbTipoDocumentoPersona = new ArrayList<Seleccion>();
		Persona persona = new Persona();

		persona = principalServicio.busquedaSolicitante(formExpediente);
		cbTipoDocumentoPersona = recursoServicio.cbTipoDocumentoPersona();

		if (persona != null) {
			pagina.setViewName("redirect:/nuevo_tramite_tupa?tipopersona=" + formExpediente.getTIPODOCUMENTOBUSCAR()
					+ "&numero=" + formExpediente.getCAJABUSQUEDA().trim());
		} else {
			pagina.setViewName("admin/tramite/buscarTupa");
		}

		pagina.addObject("formExpediente", formExpediente);
		pagina.addObject("cbTipoDocumentoPersona", cbTipoDocumentoPersona);
		return pagina;
	}

	@GetMapping(value = { "/nuevo_tramite_tupa" })
	public ModelAndView nuevoExternoTupa(HttpServletRequest request, HttpServletResponse res,
			@RequestParam int tipopersona, @RequestParam String numero) {
		Expediente formExpediente = new Expediente();
		ModelAndView pagina = new ModelAndView();
		List<Seleccion> cbTipoDocumentoPersona = new ArrayList<Seleccion>();
		List<Seleccion> cbTipoDocumento = new ArrayList<Seleccion>();
		List<Seleccion> cbTupa = new ArrayList<Seleccion>();
		Persona persona = new Persona();
		String numeroExpediente = "";

		cbTipoDocumentoPersona = recursoServicio.cbTipoDocumentoPersona();
		cbTipoDocumento = recursoServicio.cbTipoDocuemnto();
		cbTupa = recursoServicio.cbTupa();
		formExpediente.setTIPODOCUMENTOBUSCAR(tipopersona);
		formExpediente.setCAJABUSQUEDA(numero);
		formExpediente.setNTIPOPERSONA(tipopersona);

		persona = principalServicio.busquedaSolicitante(formExpediente);

		if (tipopersona == Constantes.tipoPersonaJuridica) {
			formExpediente.setVRUC(persona.getVRUC());
			formExpediente.setVRAZON_SOCIAL(persona.getVRAZONSOCIAL());
		}

		// SUBIRMOS EL ARCHIVO

		formExpediente.setNTIPOPERSONA(tipopersona);
		formExpediente.setPERSONAFK(persona.getNIDPERSONAPK());
		formExpediente.setVNOMBRE(persona.getVNOMBRE());
		formExpediente.setVAPELLIDO_PATERNO(persona.getVAPEPATERNO());
		formExpediente.setVAPELLIDO_MATERNO(persona.getVAPEMATERNO());
		formExpediente.setVNUMERODOCUMENTO(persona.getVNUMERODOC());
		formExpediente.setVCORREO(persona.getVCORREO());
		formExpediente.setVDIRECCION(persona.getVDIRECCION());
		formExpediente.setVTELEFONO(persona.getVTELEFONO());
		formExpediente.setVNUMERODOCUMENTO("");

		pagina.setViewName("admin/tramite/externo/tupa");
		pagina.addObject("formExpediente", formExpediente);
		pagina.addObject("cbTipoDocumentoPersona", cbTipoDocumentoPersona);
		pagina.addObject("cbTipoDocumento", cbTipoDocumento);
		pagina.addObject("cbTupa", cbTupa);
		return pagina;
	}

	@PostMapping(value = { "/grabar_tramite_tupa" })
	public ModelAndView grabarTramiteTupa(@ModelAttribute Expediente formExpediente,
			@RequestParam("varchivosubida") MultipartFile farchvio, HttpServletRequest request,
			HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		boolean respuesta = false;

		// OBTENEOS EL NUMERO DE EXPEDIENTE
		String correlativoExpediente = recursoServicio.numeroExpediente();
		formExpediente.setVCODIGO_EXPEDIENTE(correlativoExpediente);

		// respuesta = principalServicio.guardarExpedienteSimple(formExpediente);

		pagina.setViewName("admin/tramite/externo/respuesta_simple");
		return pagina;
	}

}
