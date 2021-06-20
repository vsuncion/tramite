package com.tramite.app.Controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.tramite.app.Entidades.Archivos;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.HojaRuta;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.Persona; 
import com.tramite.app.Entidades.PrePersona;
import com.tramite.app.Entidades.PreRequisitoTupa;
import com.tramite.app.Entidades.RequisitosTupac;
import com.tramite.app.Entidades.Seleccion; 
import com.tramite.app.Servicios.ArchivoUtilitarioServicio;
import com.tramite.app.Servicios.ExpedienteServicio;
import com.tramite.app.Servicios.MantenimientoServicio;
import com.tramite.app.Servicios.PrincipalServicio;
import com.tramite.app.Servicios.RecursoServicio;
import com.tramite.app.utilitarios.Constantes;
import com.tramite.app.utilitarios.ConstantesArchivos;
import com.tramite.app.utilitarios.GenerarExcel;

@Controller
@RequestMapping("/")
public class PrinicipalController {

	@Autowired
	private MantenimientoServicio mantenimientoServicio;
	
	@Autowired
	private ExpedienteServicio  expedienteServicio;

	@Autowired
	private RecursoServicio recursoServicio;

	@Autowired
	private PrincipalServicio principalServicio;

	@Autowired
	private ArchivoUtilitarioServicio archivoUtilitarioServicio;
	
	@Autowired
	private GenerarExcel  generarExcel;

	Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping(value = { "/", "index" })
	public ModelAndView hola(HttpServletRequest request, HttpServletResponse res) {
		logger.info("======================= INFO ================");
		ModelAndView pagina = new ModelAndView();
		List<HojaRuta> listaHoja = new ArrayList<HojaRuta>(); 
		HojaRuta formHojaRuta = new HojaRuta();
		Expediente infoExpediente = new Expediente();
		String mensajeRespuesta ="";
		
		pagina.addObject("mensajerespuesta",mensajeRespuesta);
		pagina.addObject("infoExpediente",infoExpediente);
		pagina.addObject("listaHoja",listaHoja);
		pagina.addObject("formHojaRuta",formHojaRuta);
		pagina.setViewName("index");
		return pagina;
	}
	
	@PostMapping(value = {"/buscarexpediente"})
	public ModelAndView buscarExpediente(HttpServletRequest request, HttpServletResponse res,@ModelAttribute HojaRuta formHojaRuta) {
		logger.info("======================= INFO ================");
		ModelAndView pagina = new ModelAndView();
		List<HojaRuta> listaHoja = new ArrayList<HojaRuta>();  
		Expediente infoExpediente = new Expediente();
		String mensajeRespuesta =Constantes.MENSAJE_BUSCAR_EXPEDIENTE;
		
		infoExpediente = expedienteServicio.infoExpedienteCodigo(formHojaRuta.getANIO(), formHojaRuta.getVCODIGOEXPEDIENTE());
		if(infoExpediente.getVCODIGO_EXPEDIENTE()!=null) {
			listaHoja = expedienteServicio.infoHojaRuta(formHojaRuta.getANIO(), formHojaRuta.getVCODIGOEXPEDIENTE());
			mensajeRespuesta="";
		} 
		
		pagina.addObject("mensajerespuesta",mensajeRespuesta);
		pagina.addObject("infoExpediente",infoExpediente);
		pagina.addObject("listaHoja",listaHoja);
		pagina.addObject("formHojaRuta",formHojaRuta);
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
	public ModelAndView nuevaPersonaNatural(HttpServletRequest request, HttpServletResponse res) {
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
		pagina.addObject("estadobotton", Constantes.habilitadoboton);
		return pagina;
	}

	@GetMapping(value = { "/nuevaPersonaJuridica" })
	public ModelAndView nuevaPersonaJuridica(HttpServletRequest request, HttpServletResponse res) {
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
		//List<Seleccion> cbTipoDocumentoRegistro = new ArrayList<Seleccion>();
		Persona personaDupliciedad = new Persona();
		

		// VERIFICAMOS SI LA PERSONA YA FUE REGISTRADA PREVIAMENTE
		prePersona.setNTIPO_PERSONA(Constantes.tipoPersonaNatural);
		
		//VERIFICAMOS SI LA PERSONA YA EXISTE
		personaDupliciedad = principalServicio.buscarPersona(prePersona.getNTIPO_PERSONA(), prePersona.getVNUMERODOC());
			
		if(personaDupliciedad.getVNUMERODOC()==null) {
			mostrarmensaje = principalServicio.guardarPrePersona(prePersona);
		}else {
			mostrarmensaje.setCodigo(0);
			mostrarmensaje.setMensaje(Constantes.MENSAJE_DUPLICIDAD_PERSONA.replace("$VNUMERO$", personaDupliciedad.getVNUMERODOC()));
		}
		
		//cbTipoDocumentoRegistro = mantenimientoServicio.cbTipoDocumentoRegistro();

		pagina.setViewName("admin/persona/mensajeinformacion");
		//pagina.addObject("prePersona", prePersona);
		//pagina.addObject("cbTipoDocumentoRegistro", cbTipoDocumentoRegistro);
		pagina.addObject("mostrarmensaje", mostrarmensaje);
		//pagina.addObject("estadobotton", Constantes.deshabilitadoboton);
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
		
		mostrarmensaje = principalServicio.buscarPersonaJuridicaDuplicada(prePersona);
		
		if(mostrarmensaje.getCodigo()==0) {
			mostrarmensaje = principalServicio.guardarPrePersona(prePersona);
		} 
		
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
		pagina.addObject("mostrarmensaje", mostrarmensaje);
		return pagina;
	}

	@GetMapping(value = { "/nueva_busqueda_simple" })
	public ModelAndView buscarSolicitante(HttpServletRequest request, HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		Expediente formExpediente = new Expediente();
		List<Seleccion> cbTipoDocumentoPersona = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		cbTipoDocumentoPersona = recursoServicio.cbTipoDocumentoPersona();

		
		pagina.setViewName("admin/tramite/buscarSimple");
		pagina.addObject("formExpediente", formExpediente);
		pagina.addObject("cbTipoDocumentoPersona", cbTipoDocumentoPersona);
		pagina.addObject("mostrarmensaje", mostrarmensaje);
		return pagina;
	}

	@PostMapping(value = { "/buscarSolicitante" })
	public ModelAndView buscarCidadano(@ModelAttribute Expediente formExpediente, HttpServletRequest request,
			HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		List<Seleccion> cbTipoDocumentoPersona = new ArrayList<Seleccion>();
		Persona persona = new Persona();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		persona = principalServicio.busquedaSolicitante(formExpediente);
		cbTipoDocumentoPersona = recursoServicio.cbTipoDocumentoPersona();

		if (persona != null) {
			pagina.setViewName("redirect:/nuevo_tramite_simple?tipopersona=" + formExpediente.getTIPODOCUMENTOBUSCAR()
					+ "&numero=" + formExpediente.getCAJABUSQUEDA().trim());
		} else {
			pagina.setViewName("admin/tramite/buscarSimple");
			mostrarmensaje.setCodigo(0);
			mostrarmensaje.setMensaje("NO SE ENCONTRARON RESULTADOS");
		}

		pagina.addObject("mostrarmensaje", mostrarmensaje);
		pagina.addObject("formExpediente", formExpediente);
		pagina.addObject("cbTipoDocumentoPersona", cbTipoDocumentoPersona);
		return pagina;
	}

	@GetMapping(value = { "/nuevo_tramite_simple" })
	public ModelAndView nuevoExternoSimple(HttpServletRequest request, HttpServletResponse res,
			@RequestParam Long tipopersona, @RequestParam String numero) {
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
		Long idOficina = Constantes.OficinaMesaPartePk;
		String correlativoExpediente = recursoServicio.numeroExpediente(idOficina);
		formExpediente.setVCODIGO_EXPEDIENTE(correlativoExpediente);

		respuesta = principalServicio.guardarExpedienteSimple(formExpediente);
		
		 
		pagina.addObject("codigoexpediente",correlativoExpediente);
		pagina.setViewName("admin/tramite/externo/respuesta_simple");
		return pagina;
	}

	@GetMapping(value = { "/nueva_busqueda_tupa" })
	public ModelAndView buscarSolicitanteTupa(HttpServletRequest request, HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		Expediente formExpediente = new Expediente();
		List<Seleccion> cbTipoDocumentoPersona = new ArrayList<Seleccion>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		cbTipoDocumentoPersona = recursoServicio.cbTipoDocumentoPersona();

		pagina.setViewName("admin/tramite/buscarTupa");
		pagina.addObject("formExpediente", formExpediente);
		pagina.addObject("cbTipoDocumentoPersona", cbTipoDocumentoPersona);
		pagina.addObject("mostrarmensaje", mostrarmensaje);
		return pagina;
	}

	@PostMapping(value = { "/buscarSolicitanteTupa" })
	public ModelAndView buscarCiudadanoTupa(@ModelAttribute Expediente formExpediente, HttpServletRequest request,
			HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		List<Seleccion> cbTipoDocumentoPersona = new ArrayList<Seleccion>();
		Persona persona = new Persona();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		persona = principalServicio.busquedaSolicitante(formExpediente);
		cbTipoDocumentoPersona = recursoServicio.cbTipoDocumentoPersona();

		if (persona != null) {
			pagina.setViewName("redirect:/nuevo_tramite_tupa?tipopersona=" + formExpediente.getTIPODOCUMENTOBUSCAR()
					+ "&numero=" + formExpediente.getCAJABUSQUEDA().trim());
		} else {
			pagina.setViewName("admin/tramite/buscarTupa");
			mostrarmensaje.setMensaje("NO SE ENCONTRARON RESULTADOS");
		}

		pagina.addObject("mostrarmensaje", mostrarmensaje);
		pagina.addObject("formExpediente", formExpediente);
		pagina.addObject("cbTipoDocumentoPersona", cbTipoDocumentoPersona);
		return pagina;
	}
	
	
	@GetMapping(value = {"/buscarexpediente/exportarhojaruta"})
	public void exportarHojaRuta(HttpServletRequest request, HttpServletResponse res,@RequestParam String anio,String codigoexpediente) {
		Expediente infoExpediente = new Expediente();
		XSSFWorkbook libro = new XSSFWorkbook();
		List<HojaRuta> listaHojaRuta = new ArrayList<HojaRuta>();
		
		try {
			
			infoExpediente = expedienteServicio.infoExpedienteCodigo(anio, codigoexpediente);
			if(infoExpediente!=null) {
				listaHojaRuta = expedienteServicio.infoHojaRuta(anio,codigoexpediente);
				if(listaHojaRuta.size()>0) {
					//GENERAMOS LA HOJA DE RUTA
					generarExcel.reporteHojaRuta(libro, infoExpediente, listaHojaRuta);
					String nombreReporte = "Hoja_Ruta_" + infoExpediente.getVCODIGO_EXPEDIENTE() + ".xlsx";
					 res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
					 res.setHeader("Content-Disposition", "attachment; filename=" + nombreReporte);
					 
					 ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
					 libro.write(outByteStream);
					 byte[] outArray = outByteStream.toByteArray();
					 OutputStream outStream = res.getOutputStream();
					 outStream.write(outArray);
					 outStream.flush();
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
 

	@GetMapping(value = { "/nuevo_tramite_tupa" })
	public ModelAndView nuevoExternoTupa(HttpServletRequest request, HttpServletResponse res,
			@RequestParam Long tipopersona, @RequestParam String numero) {
		Expediente formExpediente = new Expediente();
		ModelAndView pagina = new ModelAndView();
		List<Seleccion> cbTipoDocumentoPersona = new ArrayList<Seleccion>();
		List<Seleccion> cbTipoDocumento = new ArrayList<Seleccion>();
		List<Seleccion> cbTupa = new ArrayList<Seleccion>();
		Persona persona = new Persona();
		//String numeroExpediente = "";

		cbTipoDocumentoPersona = recursoServicio.cbTipoDocumentoPersona();
		cbTipoDocumento = recursoServicio.cbTipoDocuemnto();
		cbTupa = principalServicio.listasTupacRequisitos();
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
		List<Seleccion> cbRequisitos = new ArrayList<>();
		List<PreRequisitoTupa> listarPreRequisito = new ArrayList<PreRequisitoTupa>();
        Long idPreTramiteTupac = 0L;
       
        
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
 
		
		idPreTramiteTupac = principalServicio.guardarPreTupac(formExpediente);
		
		//OBTENEMOS LA INFORMACION DEL PRE-REGISTRO 
		formExpediente = principalServicio.preTupacExpediente(idPreTramiteTupac);
		
		//BUSCAMOS LOS REQUSITOS TUPAC
		cbRequisitos = recursoServicio.cbRequisitos(formExpediente.getTUPACFK());
		
 
		pagina.addObject("listarPreRequisito", listarPreRequisito);
		pagina.addObject("cbrequisitos", cbRequisitos);
		pagina.addObject("formExpediente", formExpediente);
		pagina.setViewName("admin/tramite/externo/prerequisitostupac");
		return pagina;
	}
	
	
	@GetMapping(value = {"/prerequisitos"})
	public ModelAndView prerequisitos(HttpServletRequest request, HttpServletResponse res,@RequestParam Long idpretupac) {
		ModelAndView pagina = new ModelAndView();
		Expediente formExpediente = new Expediente();
		List<Seleccion> cbRequisitos = new ArrayList<>();
		List<PreRequisitoTupa> listarPreRequisito = new ArrayList<PreRequisitoTupa>();
		
		
		//OBTENEMOS LA INFORMACION DEL PRE-REGISTRO 
		formExpediente = principalServicio.preTupacExpediente(idpretupac);
		
		//BUSCAMOS LOS REQUSITOS TUPAC
		cbRequisitos = recursoServicio.cbRequisitos(formExpediente.getTUPACFK());
		
		
		
		pagina.addObject("listarPreRequisito", listarPreRequisito);
		pagina.addObject("cbrequisitos", cbRequisitos);
		pagina.addObject("formExpediente", formExpediente);
		pagina.setViewName("admin/tramite/externo/prerequisitostupac");
		return pagina;
	}
	
	
	@PostMapping(value = {"/grabar_tramite_requisito_tupa"})
	public ModelAndView grabar_tramite_requisito_tupa(HttpServletRequest request, HttpServletResponse res,@ModelAttribute Expediente formExpediente,@RequestParam("varchivosubida") MultipartFile farchvio) {
		ModelAndView pagina = new ModelAndView(); 
		List<Seleccion> cbRequisitos = new ArrayList<>();
		PreRequisitoTupa  preRequisitoTupa = new PreRequisitoTupa();
		PreRequisitoTupa  buscarPreRequisitoTupa = new PreRequisitoTupa();
		List<PreRequisitoTupa> listarPreRequisito = new ArrayList<PreRequisitoTupa>();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta(); 
		boolean respuesta = false; 
		
		preRequisitoTupa.setIDPREEXPEDIENTEFK(formExpediente.getIDPREEXPEDIENTEPK());
		preRequisitoTupa.setTUPACFK(formExpediente.getTUPACFK());
		preRequisitoTupa.setREQUISITOFK(formExpediente.getVREQUISITO());
		
	 
		//OBTENEMOS LA INFORMACION DEL PRE-REGISTRO
		formExpediente = principalServicio.preTupacExpediente(preRequisitoTupa.getIDPREEXPEDIENTEFK());
		formExpediente.setVREQUISITO(preRequisitoTupa.getREQUISITOFK());
 
		
		//VERIFICAMOS QUE NO ESTE DUPLICADO
		buscarPreRequisitoTupa = principalServicio.infoPreRequisitoTupa(preRequisitoTupa);
		
		if(buscarPreRequisitoTupa.getIDPREREQUISITOPK()==0) {
			
			// SUBIMOS EL DOCUMENTO
			if (farchvio != null && farchvio.getSize() > 0) {
				Archivos archivo = new Archivos();

				archivo = archivoUtilitarioServicio.cargarArchivo(farchvio, ConstantesArchivos.getCorrelativoArchivo());

				if (archivo.isVerificarCarga() == true) {
					logger.info("ingresi el archivo");
					preRequisitoTupa.setVUBICACION_ARCHIVO(archivo.getRuta());
					preRequisitoTupa.setVNOMBRE_ARCHIVO(archivo.getNombre());
					preRequisitoTupa.setVEXTENSION(archivo.getExtension());
				}
			}
			
			
			// INSERTAMOS LOS REGISTROS 
		 	mostrarmensaje = principalServicio.guardarPreRequisito(preRequisitoTupa);
			
		}
		
 
		//BUSCAMOS LOS REQUSITOS TUPAC
		cbRequisitos = recursoServicio.cbRequisitos(preRequisitoTupa.getTUPACFK());

        // LISTAMOS LOS REQUISITOS AGREGADOS
		listarPreRequisito = principalServicio.listaPreRequisitos(preRequisitoTupa.getIDPREEXPEDIENTEFK());
		
		
		if(cbRequisitos.size()==listarPreRequisito.size()) {
			//PROCEDEMOS A REGISTRAR EL EXPEDIENTE
			logger.info("========= PROCEDER A REGISTRAR EL EXPEDIENTE");
			
			//GRABAMOS EL EXPEDIENTE
			Long idOficina = Constantes.OficinaMesaPartePk;
			String correlativoExpediente = recursoServicio.numeroExpediente(idOficina);
			formExpediente.setVCODIGO_EXPEDIENTE(correlativoExpediente);
			
			//GRABAMOS EL TUPA EN EXPEDIENTE
			respuesta = principalServicio.guardarExpedienteSimple(formExpediente); 
			
			// INSERTAMOS LOS DETALLES DE LOS DOCUMENTOS ADJUNTOS
			//principalServicio.guardarDetalleArchivosExpedienteTupa(formExpediente);
			
			
			if(respuesta== true) {
				pagina.addObject("codigoexpediente", correlativoExpediente);
			}else {
				pagina.addObject("codigoexpediente", correlativoExpediente);
			}
			
			pagina.setViewName("admin/tramite/externo/respuesta_simple");
			
		}else {
			pagina.setViewName("admin/tramite/externo/agregarprerequisitostupac");
		}
		
		
		pagina.addObject("listarPreRequisito", listarPreRequisito);
		pagina.addObject("cbrequisitos", cbRequisitos);
		pagina.addObject("formExpediente", formExpediente);
		//pagina.setViewName("admin/tramite/externo/agregarprerequisitostupac");
		return pagina;
	}
	
	@GetMapping(value = {"/eliminarequisitotupa"})
	public ModelAndView eliminar_requisito_tupa(HttpServletRequest request, HttpServletResponse res,@RequestParam Long idprexpediente,@RequestParam Long idrequisito) {
		
		ModelAndView pagina = new ModelAndView(); 
		Expediente formExpediente = new Expediente();
		List<Seleccion> cbRequisitos = new ArrayList<>();
		List<PreRequisitoTupa> listarPreRequisito = new ArrayList<PreRequisitoTupa>();
		
		//OBTENEMOS LA INFORMACION DEL PRE-REGISTRO
		formExpediente = principalServicio.preTupacExpediente(idprexpediente);
		
		//BUSCAMOS LOS REQUSITOS TUPAC
		cbRequisitos = recursoServicio.cbRequisitos(formExpediente.getTUPACFK());
		
		//ELIMINAMOS EL ARCHIVO REQUERIMEINTO
		principalServicio.eliminarArchivoRequerimeinto(idprexpediente,idrequisito);

		 // LISTAMOS LOS REQUISITOS AGREGADOS
		listarPreRequisito = principalServicio.listaPreRequisitos(idprexpediente);
 
		pagina.addObject("listarPreRequisito", listarPreRequisito);
		pagina.addObject("cbrequisitos", cbRequisitos);
		pagina.addObject("formExpediente", formExpediente);
		pagina.setViewName("admin/tramite/externo/agregarprerequisitostupac");
		return pagina;
	}
	
	
	@GetMapping(value = { "/nuevo_tramite_tupa/listarrequerimientos" },produces = "application/json" )
	@ResponseBody
	public List<RequisitosTupac>  listarRequerimientos(HttpServletRequest request,HttpServletResponse res,@RequestParam Long idtupac){
		List<RequisitosTupac> listaRequisitosTupac = new  ArrayList<RequisitosTupac>();
		listaRequisitosTupac = principalServicio.listaRequerimientosTupac(idtupac);
		return listaRequisitosTupac;
		
	}
	
	 
	
 
	
}
