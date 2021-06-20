package com.tramite.app.Controller;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList; 
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tramite.app.Entidades.ArchivoMovimiento;
import com.tramite.app.Entidades.ArchivoTupac;
import com.tramite.app.Entidades.Archivos;
import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Entidades.EstadoDocumento;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.HojaRuta;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.MovimientoExpediente;
import com.tramite.app.Entidades.Oficinas;
import com.tramite.app.Entidades.ReporteExpediente;
import com.tramite.app.Entidades.Seleccion;
import com.tramite.app.Entidades.Usuarios;
import com.tramite.app.Servicios.ArchivoUtilitarioServicio;
import com.tramite.app.Servicios.ExpedienteServicio;
import com.tramite.app.Servicios.RecursoServicio;
import com.tramite.app.utilitarios.Constantes;
import com.tramite.app.utilitarios.ConstantesArchivos;
import com.tramite.app.utilitarios.Fechas;
import com.tramite.app.utilitarios.GenerarExcel;

@Controller
@RequestMapping("/admin/expediente")
@PreAuthorize("hasAnyRole('ROLE_SEGUIMIENTO_TRAMITE','ROLE_ATENCION_PUBLICO','ROLE_REPORTES')")
public class ExpedienteController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RecursoServicio recursoServicio;

	@Autowired
	private ExpedienteServicio expedienteServicio;

	@Autowired
	private ArchivoUtilitarioServicio archivoUtilitarioServicio;
	@Autowired
	private GenerarExcel generarExcel;

	@Value("${rutaArchivo}")
	private String rutaRaiz;

	@GetMapping(value = { "/listarBandeja" })
	public ModelAndView listarBandeja(HttpServletRequest request, HttpServletResponse res,
			@RequestParam Long idestado) {
		ModelAndView pagina = new ModelAndView();
		List<Bandeja> listaBandeja = new ArrayList<Bandeja>();
		Bandeja formBandeja = new Bandeja();
		Authentication autch = SecurityContextHolder.getContext().getAuthentication();
		Usuarios usuario = new Usuarios();
		Oficinas oficina = new Oficinas();
		EstadoDocumento estadoDocumento = new EstadoDocumento();

		if (autch == null) {
			pagina.setViewName("redirect:/login?logout");
			return pagina;
		}

		logger.info("===========" + idestado);
		usuario = recursoServicio.infoUsuario(autch.getName());

		Long idoficina = usuario.getNOFICINAFK();

		listaBandeja = expedienteServicio.listarBandeja(idoficina, idestado);
		oficina = recursoServicio.infoOficina(idoficina);
		estadoDocumento = recursoServicio.infoEstadoDocumento(idestado);
		
		pagina.addObject("formBandeja", formBandeja);
		pagina.addObject("listaBandeja", listaBandeja);
		pagina.addObject("flagestadodocumento", idestado);
		pagina.addObject("estadoDocumento", estadoDocumento);
		pagina.addObject("nombreoficina", oficina.getVNOMBRE());
		pagina.setViewName("admin/bandeja/listar");
		return pagina;
	}

	@GetMapping(value = { "/recibirExpediente" })
	public ModelAndView recibirExpediente(HttpServletRequest request, HttpServletResponse res,
			@RequestParam Long idmovimiento,@RequestParam Long idestado) {
		ModelAndView pagina = new ModelAndView();
		//List<Bandeja> listaBandeja = new ArrayList<Bandeja>();
		//Bandeja formBandeja = new Bandeja();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		Authentication autch = SecurityContextHolder.getContext().getAuthentication();
		Usuarios usuario = new Usuarios();
		MovimientoExpediente infoMovimiento = new MovimientoExpediente();
		//EstadoDocumento estadoDocumento = new EstadoDocumento();
		//Oficinas oficina = new Oficinas();
		// INSERTAR MOVIMIENTO
		logger.info("==============" + idmovimiento);
	 
		usuario = recursoServicio.infoUsuario(autch.getName());
		
		//INFORMACION DEL MOVIMIENTO
		infoMovimiento = expedienteServicio.infoMovimientoIdexpediente(idmovimiento);

		Long idoficina = usuario.getNOFICINAFK();
		
		mostrarmensaje = expedienteServicio.recibirExpediente(idmovimiento,idoficina,infoMovimiento.getNIDEXPEDIENTEFK());
		/*
		listaBandeja = expedienteServicio.listarBandeja(idoficina, Constantes.ESTADO_DOCUMENTO_RECIBIDO);
		oficina = recursoServicio.infoOficina(idoficina);
		estadoDocumento = recursoServicio.infoEstadoDocumento(idestado);

		pagina.addObject("formBandeja", formBandeja);
		pagina.addObject("listaBandeja", listaBandeja);
		pagina.addObject("flagestadodocumento", idestado);
		pagina.addObject("estadoDocumento", estadoDocumento);
		pagina.addObject("nombreoficina", oficina.getVNOMBRE());
		*/
		pagina.addObject("mostrarmensaje", mostrarmensaje);
		pagina.setViewName("admin/bandeja/confirmacion");
		return pagina;
	}

	@GetMapping(value = { "/atenderexpediente" })
	public ModelAndView atenderExpediente(HttpServletRequest request, HttpServletResponse res,
			@RequestParam Long idexpediente, @RequestParam Long idmovimiento) {

		logger.info("==============" + idexpediente + "****" + idmovimiento);

		Expediente formExpediente = new Expediente();
		ModelAndView pagina = new ModelAndView();
		MovimientoExpediente infoMovimiento = new MovimientoExpediente();
		List<Seleccion> listaOfinas = new ArrayList<Seleccion>();
		List<Seleccion> listaEstadoDocumentos = new ArrayList<Seleccion>();
		Oficinas infoficina = new Oficinas();
		Authentication autch = SecurityContextHolder.getContext().getAuthentication();
		Usuarios usuario = new Usuarios();

		usuario = recursoServicio.infoUsuario(autch.getName());
		Long codigooficinaorigen = usuario.getNOFICINAFK();

		// INFORMACION EXPEDIENTE
		formExpediente = expedienteServicio.infoExpediente(idexpediente);

		// INFORMACION MOVIMIENTO
		infoMovimiento = expedienteServicio.infoMovimiento(idexpediente, idmovimiento);
		formExpediente.setNIDMOVIMIENTOFK(infoMovimiento.getNIDMOVIMIENTOPK());
		formExpediente.setOFICINA_ORIGENFK(codigooficinaorigen);

		listaOfinas = recursoServicio.cbOficinasAtender(codigooficinaorigen);
		listaEstadoDocumentos = recursoServicio.cbAccionesAtender();
		infoficina = recursoServicio.infoOficina(codigooficinaorigen);

		pagina.addObject("formExpediente", formExpediente);
		pagina.addObject("infoMovimiento", infoMovimiento);
		pagina.addObject("listaOfinas", listaOfinas);
		pagina.addObject("listaEstadoDocumentos", listaEstadoDocumentos);
		pagina.addObject("infoficina", infoficina);
		pagina.addObject("letras_archivado", Constantes.LETRAS_ESTADO_DOCUMENTO_ARCHIVADO);
		pagina.setViewName("admin/bandeja/atender_simple");
		return pagina;

	}

	@PostMapping(value = { "/grabarAtenderTramiteSimple" })
	public ModelAndView grabarAtenderTramiteSimple(@ModelAttribute Expediente formExpediente,
			HttpServletRequest request, HttpServletResponse res,
			@RequestParam("varchivosubida") MultipartFile farchvio) {
		ModelAndView pagina = new ModelAndView();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

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

		mostrarmensaje = expedienteServicio.responderExpediente(formExpediente);
		
		pagina.addObject("mostrarmensaje", mostrarmensaje);
		pagina.setViewName("admin/bandeja/confirmacion");
		return pagina;
	}

	@GetMapping(value = { "/descargarprincipal" })
	public void descargarPrincipal(HttpServletRequest request, HttpServletResponse res,
			@RequestParam Long idexpediente) {

		Expediente formExpediente = new Expediente();
		String ruta = "";

		try {
			// INFORMACION EXPEDIENTE
			formExpediente = expedienteServicio.infoExpediente(idexpediente);
			
			if (formExpediente != null) {
				ruta = rutaRaiz + formExpediente.obtenerRutaAbsoluArchivo();
				res.setHeader("Content-Disposition", "attachment; filename=" + formExpediente.getVNOMBRE_ARCHIVO() + "."
						+ formExpediente.getVEXTENSION());
				res.getOutputStream().write(Files.readAllBytes(Paths.get(ruta)));
			}
		} catch (Exception e) {

		}

	}
	
	@GetMapping(value = { "/descargaradjuntorespuesta" })
	public void descargarAdjuntoRespuesta(HttpServletRequest request, HttpServletResponse res,
			@RequestParam Long idexpediente, @RequestParam Long idoficina,@RequestParam String nombrearchivo) {
		ArchivoMovimiento infoMovimiento = new ArchivoMovimiento();
		String ruta = "";
		
		try {
			infoMovimiento = expedienteServicio.infoMovimientoArchivoRespuesta(idexpediente, idoficina,nombrearchivo);
			if(infoMovimiento!=null) {
				ruta = rutaRaiz + infoMovimiento.obtenerRutaAbsoluArchivo();
				res.setHeader("Content-Disposition", "attachment; filename=" + infoMovimiento.getVNOMBRE_ARCHIVO() + "."
						+ infoMovimiento.getVEXTENSION());
				res.getOutputStream().write(Files.readAllBytes(Paths.get(ruta)));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/*
	@PostMapping(value = {"/hojaruta"})
	public ModelAndView hojaRuta(HttpServletRequest request, HttpServletResponse res,@ModelAttribute HojaRuta formHojaRuta) {
		List<HojaRuta> listaHoja = new ArrayList<HojaRuta>();
		ModelAndView pagina = new ModelAndView();
		
		listaHoja = expedienteServicio.infoHojaRuta(formHojaRuta.getANIO(), formHojaRuta.getVCODIGOEXPEDIENTE());
		
		return pagina;
	}*/
	
	@GetMapping(value = {"/listahojaruta"})
	public ModelAndView listarHojaRuta(HttpServletRequest request, HttpServletResponse res,@RequestParam int idestado,@RequestParam Long idexpediente) {
		ModelAndView pagina = new ModelAndView();	
		List<HojaRuta> listaHoja = new ArrayList<HojaRuta>();
		Expediente infoExpediente = new Expediente();
		HojaRuta formHojaRuta = new HojaRuta();
		
		infoExpediente = expedienteServicio.infoExpedienteId(idexpediente);
		
		listaHoja = expedienteServicio.infoHojaRutaIdExpediente(idexpediente);
		
		String[] vcodigoexpediente = infoExpediente.getVCODIGO_EXPEDIENTE().split("-");
		String anio = vcodigoexpediente[1];
		String vcodigoexp  = vcodigoexpediente[2];
		
		//String anio =    infoExpediente.getVCODIGO_EXPEDIENTE().substring(2, 6);
		
		pagina.addObject("anio",anio);
		pagina.addObject("vcodigoexp",vcodigoexp);
		pagina.addObject("idestado",idestado);
		pagina.addObject("formHojaRuta",formHojaRuta);
		pagina.addObject("infoExpediente",infoExpediente);
		pagina.addObject("listaHoja",listaHoja);
		pagina.setViewName("admin/bandeja/hojaruta");
		return pagina;
 
		
	}
	
	
	@GetMapping(value = {"/exportarhojaruta"})
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
	
	
	@GetMapping(value = {"/descargaradjntos"})
	public ModelAndView descargarequerimientostupa(HttpServletRequest request, HttpServletResponse res,@RequestParam Long idexpediente,@RequestParam Long idmovimiento) {
		ModelAndView pagina = new ModelAndView();
		Expediente infoExpediente = new Expediente();
		List<ArchivoTupac> listaTupacArchivos = new ArrayList<ArchivoTupac>();
		
		//OBTENEMOS INFORMACION DEL EXPEDIENTE
		infoExpediente = expedienteServicio.infoExpedienteId(idexpediente);
		listaTupacArchivos = expedienteServicio.listarArchivosTupa(idexpediente);
		
		pagina.addObject("pidexpediente",idexpediente);
		pagina.addObject("pidmovimiento",idmovimiento);
		pagina.addObject("infoExpediente",infoExpediente);
		pagina.addObject("listatupacarchivos",listaTupacArchivos);
		pagina.setViewName("admin/bandeja/descargartupa");
		return pagina;
	}
	
	
	@GetMapping(value = {"/archivosadjuntos"})
	public void archivosAdjuntos(HttpServletRequest request, HttpServletResponse res,@RequestParam Long idexpediente,@RequestParam Long idarchivorequisito) {
		
		ArchivoTupac info = new ArchivoTupac();
		String ruta="";
		
		try {
			info = expedienteServicio.infoArchivoTupa(idexpediente, idarchivorequisito);
			if(info!=null) {
				ruta = rutaRaiz + info.obtenerRutaAbsoluArchivo();
				res.setHeader("Content-Disposition", "attachment; filename=" + info.getVNOMBRE_ARCHIVO() + "."
						+ info.getVEXTENSION());
				res.getOutputStream().write(Files.readAllBytes(Paths.get(ruta)));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}

	@GetMapping(value = { "/registroexpc" })
	public ModelAndView registroExpedientec(HttpServletRequest request, HttpServletResponse res) {

		logger.info("======================= INFO 1 registroexpc================");
		ModelAndView pagina = new ModelAndView();
		Expediente formExpediente = new Expediente();
		List<Seleccion> cbTipoDocumento = new ArrayList<Seleccion>();
		List<Seleccion> cbOficinas = new ArrayList<Seleccion>();
		
		Authentication autch = SecurityContextHolder.getContext().getAuthentication();
		Usuarios usuario = new Usuarios();
		usuario = recursoServicio.infoUsuario(autch.getName());
		formExpediente.setNTIPOPERSONA(Long.valueOf(Constantes.tipoPersonaNatural));
		formExpediente.setPERSONAFK(usuario.getNIDPERSONAFK());
		formExpediente.setVNOMBRE(usuario.getVNOMBRE());
		formExpediente.setVAPELLIDO_PATERNO(usuario.getVAPEPATERNO());
		formExpediente.setVAPELLIDO_MATERNO(usuario.getVAPEMATERNO());
		
		cbTipoDocumento = recursoServicio.cbTipoDocuemnto();
		String vnombre_oficina = usuario.getVOFICINA();
		
		cbOficinas = recursoServicio.cbOficinasAtender(usuario.getNOFICINAFK());
		
		pagina.addObject("cbOficinas", cbOficinas);
		pagina.addObject("vnombre_oficina", vnombre_oficina);
		pagina.addObject("cbTipoDocumento", cbTipoDocumento);
		pagina.addObject("formExpediente", formExpediente);
		pagina.setViewName("admin/tramite/interno/simple_old");
		return pagina;
	}
	
	
	@PostMapping(value = {"/grabartramitesimpleinterno"})
	public ModelAndView grabarTramiteSimpleInterno(HttpServletRequest request, HttpServletResponse res,Expediente formExpediente,@RequestParam("varchivosubida") MultipartFile farchvio) {
		logger.info("======================= GRABAR ================");
		ModelAndView pagina = new ModelAndView();
		boolean respuesta = false;
		
		
		Authentication autch = SecurityContextHolder.getContext().getAuthentication();
		Usuarios usuario = new Usuarios();
		usuario = recursoServicio.infoUsuario(autch.getName());
		
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
		String correlativoExpediente = recursoServicio.numeroExpediente(usuario.getNOFICINAFK());
		formExpediente.setVCODIGO_EXPEDIENTE(correlativoExpediente);
		formExpediente.setOFICINA_ORIGENFK(usuario.getNOFICINAFK());
		formExpediente.setPERSONAFK(usuario.getNIDPERSONAFK());
		formExpediente.setNTIPOPERSONA(Long.valueOf(Constantes.tipoPersonaNatural));
		
		respuesta = expedienteServicio.guardarExpedienteSimpleInterno(formExpediente);
		
		pagina.addObject("codigoexpediente",correlativoExpediente);
		pagina.setViewName("admin/tramite/interno/respuesta_simple");
		return pagina;	
	}
	
	
	@GetMapping(value = {"/actualizarclave"})
	public ModelAndView actualizarClave(HttpServletRequest request, HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		Usuarios formusuario = new Usuarios();
		Authentication autch = SecurityContextHolder.getContext().getAuthentication();
		Usuarios usuario = new Usuarios();

		usuario = recursoServicio.infoUsuario(autch.getName());
		formusuario.setVUSUARIO(usuario.getUsername());
		
		pagina.addObject("formusuario",formusuario); 
		pagina.setViewName("admin/usuario/cambioclave");
		return pagina;	
	}
	
	
	@PostMapping(value = {"/grabaractualizarclave"})
	public ModelAndView grabarActualizarClave(HttpServletRequest request, HttpServletResponse res,Usuarios formusuario) {
		ModelAndView pagina = new ModelAndView();
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta(); 
		Authentication autch = SecurityContextHolder.getContext().getAuthentication(); 
		Usuarios usuario = new Usuarios();
		usuario = recursoServicio.infoUsuario(autch.getName());
		
		formusuario.setVUSUARIO(usuario.getVUSUARIO());
		respuesta = expedienteServicio.actualizarClave(formusuario);
		
		
		formusuario.setVCLAVE("");
		pagina.addObject("formusuario",formusuario); 
		pagina.addObject("mostrarmensaje", mostrarmensaje);
		pagina.setViewName("admin/usuario/cambioclave");
		return pagina;	
	}
	
	
	@GetMapping(value = {"/rptestadodocumento"})
	public ModelAndView reportEstadoDocumento(HttpServletRequest request, HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
        Expediente formexpediente = new Expediente();
        List<Seleccion> listaEstadoDocumentos = new ArrayList<Seleccion>();
        List<ReporteExpediente> listaReporte = new ArrayList<ReporteExpediente>();
        
        Long idestadoInicial = 0L;
        listaReporte = expedienteServicio.listaExpedientesPorEstadoDocuemnto(idestadoInicial);
        listaEstadoDocumentos = recursoServicio.listaEstadoDocumentos();
        
        pagina.addObject("listaReporte", listaReporte);
        pagina.addObject("listaEstadoDocumentos", listaEstadoDocumentos);
		pagina.addObject("formexpediente",formexpediente);
		pagina.setViewName("admin/reportes/rptestadoDocumento");
		return pagina;
	}
	
	
	@PostMapping(value = {"/buscareportedocumentoestado"})
	public ModelAndView buscaReporteDocumentoEstado(HttpServletRequest request, HttpServletResponse res,Expediente formexpediente) {
		ModelAndView pagina = new ModelAndView();
		List<ReporteExpediente> listaReporte = new ArrayList<ReporteExpediente>();
		 List<Seleccion> listaEstadoDocumentos = new ArrayList<Seleccion>();
		 
		listaReporte = expedienteServicio.listaExpedientesPorEstadoDocuemnto(formexpediente.getNESTADODOCUMENTOFK());
		
		listaEstadoDocumentos = recursoServicio.listaEstadoDocumentos();
		
		
		pagina.addObject("listaReporte", listaReporte);
		pagina.addObject("listaEstadoDocumentos", listaEstadoDocumentos);
		pagina.addObject("formexpediente",formexpediente);
		pagina.setViewName("admin/reportes/rptestadoDocumento");
		return pagina;
	}
	
	
	@GetMapping(value = {"/rptoficinaestadodocumento"})
	public ModelAndView reportOficinaEstadoDocumento(HttpServletRequest request, HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		Expediente formexpediente = new Expediente();
		List<ReporteExpediente> listaReporte = new ArrayList<ReporteExpediente>();
		List<Seleccion> listaOfinas = new ArrayList<Seleccion>();
		
		formexpediente.setOFICINA_ORIGENFK(0L); 
		listaReporte = expedienteServicio.listaExpedientesPorOficina(formexpediente);
		listaOfinas = recursoServicio.cbOficinasReportes();
		
		 
		
		pagina.addObject("listaOfinas", listaOfinas);
		pagina.addObject("listaReporte", listaReporte);
		pagina.addObject("formexpediente",formexpediente); 
		pagina.setViewName("admin/reportes/rptestadoOficina");
		return pagina;
	}
	
	
	@PostMapping(value = {"/buscareportedocumentooficina"})
	public ModelAndView buscaReporteDocumentoOficina(HttpServletRequest request, HttpServletResponse res,Expediente formexpediente) {
		ModelAndView pagina = new ModelAndView(); 
		List<ReporteExpediente> listaReporte = new ArrayList<ReporteExpediente>();
		List<Seleccion> listaOfinas = new ArrayList<Seleccion>();
		
		
		 
		listaReporte = expedienteServicio.listaExpedientesPorOficina(formexpediente);
		listaOfinas = recursoServicio.cbOficinasReportes();
		
		pagina.addObject("listaOfinas", listaOfinas);
		pagina.addObject("listaReporte", listaReporte);
		pagina.addObject("formexpediente",formexpediente); 
		pagina.setViewName("admin/reportes/rptestadoOficina");
		return pagina;
	}
	
	
	@GetMapping(value = {"/rptestadodocumento/exportarexelestadodocumentos","/buscareportedocumentoestado/exportarexelestadodocumentos"})
	public void  exportarExelEstadoDocumentos(HttpServletRequest request, HttpServletResponse res,@RequestParam Long idestadodocumento) {
		XSSFWorkbook libro = new XSSFWorkbook(); 
		List<ReporteExpediente> listaReporte = new ArrayList<ReporteExpediente>(); 
		
		try { 
			listaReporte = expedienteServicio.listaExpedientesPorEstadoDocuemnto(idestadodocumento); 
			if(listaReporte.size()>0) {
				//GENERAMOS LA HOJA DE RUTA
				generarExcel.reporteEstadoDocumentos(libro, listaReporte);
				String nombreReporte = "ESTADO_DOCUMENTOS" +".xlsx";
				 res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				 res.setHeader("Content-Disposition", "attachment; filename=" + nombreReporte);
				 
				 ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
				 libro.write(outByteStream);
				 byte[] outArray = outByteStream.toByteArray();
				 OutputStream outStream = res.getOutputStream();
				 outStream.write(outArray);
				 outStream.flush();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@GetMapping(value = {"/rptoficinaestadodocumento/rptoficina","/buscareportedocumentooficina/rptoficina"})
   public void  exportarExelEstadoDocumentoOficinas(HttpServletRequest request, HttpServletResponse res,@RequestParam Long idoficina, @RequestParam(required = false) String vfechainicio,
			@RequestParam(required = false) String vfechafin) {
		
		XSSFWorkbook libro = new XSSFWorkbook(); 
		List<ReporteExpediente> listaReporte = new ArrayList<ReporteExpediente>(); 
		Expediente formexpediente = new Expediente();  
		try {
			
			 if(!"".equals(vfechainicio) && !"".equals(vfechafin)) {
				 formexpediente.setDFECHAINICIO(Fechas.convertStringToDate(vfechainicio)); 
				 formexpediente.setDFECHAFIN(Fechas.convertStringToDate(vfechafin));
			 }
			 
			formexpediente.setOFICINA_ORIGENFK(idoficina);
			listaReporte = expedienteServicio.listaExpedientesPorOficina(formexpediente);
			if(listaReporte.size()>0) {
				//GENERAMOS LA HOJA DE RUTA 
				generarExcel.reporteEstadoDocumentoOficinas(libro, listaReporte);
				String nombreReporte = "REPORTE_OFICINAS" +".xlsx";
				 res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				 res.setHeader("Content-Disposition", "attachment; filename=" + nombreReporte);
				 
				 ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
				 libro.write(outByteStream);
				 byte[] outArray = outByteStream.toByteArray();
				 OutputStream outStream = res.getOutputStream();
				 outStream.write(outArray);
				 outStream.flush();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
  
}
