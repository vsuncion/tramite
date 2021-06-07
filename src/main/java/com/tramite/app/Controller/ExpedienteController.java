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
import com.tramite.app.Entidades.Archivos;
import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Entidades.EstadoDocumento;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.HojaRuta;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.MovimientoExpediente;
import com.tramite.app.Entidades.Oficinas;
import com.tramite.app.Entidades.Seleccion;
import com.tramite.app.Entidades.Usuarios;
import com.tramite.app.Servicios.ArchivoUtilitarioServicio;
import com.tramite.app.Servicios.ExpedienteServicio;
import com.tramite.app.Servicios.RecursoServicio;
import com.tramite.app.utilitarios.Constantes;
import com.tramite.app.utilitarios.ConstantesArchivos;
import com.tramite.app.utilitarios.GenerarExcel;

@Controller
@RequestMapping("/admin/expediente")
@PreAuthorize("hasAnyRole('ROLE_SEGUIMIENTO_TRAMITE','ROLE_ATENCION_PUBLICO')")
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
		List<Bandeja> listaBandeja = new ArrayList<Bandeja>();
		Bandeja formBandeja = new Bandeja();
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		Authentication autch = SecurityContextHolder.getContext().getAuthentication();
		Usuarios usuario = new Usuarios();
		//EstadoDocumento estadoDocumento = new EstadoDocumento();
		//Oficinas oficina = new Oficinas();
		// INSERTAR MOVIMIENTO
		logger.info("==============" + idmovimiento);
	 
		usuario = recursoServicio.infoUsuario(autch.getName());

		Long idoficina = usuario.getNOFICINAFK();
		
		mostrarmensaje = expedienteServicio.recibirExpediente(idmovimiento);
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

	@GetMapping(value = { "/registroexpc" })
	public ModelAndView registroExpedientec() {

		logger.info("======================= INFO 1 registroexpc================");
		ModelAndView pagina = new ModelAndView();
		pagina.setViewName("admin/registroexp_c");
		return pagina;
	}

	@GetMapping(value = { "/registroexps" })
	public ModelAndView registroExpedientes() {

		logger.info("======================= INFO 2 registroexps===============");
		ModelAndView pagina = new ModelAndView();
		pagina.setViewName("admin/registroexp_s");
		return pagina;
	}

	@GetMapping(value = { "/seguimientoexp" })
	public ModelAndView seguimientoExpedientes() {

		logger.info("======================= INFO 3 seguimientoexp================");
		ModelAndView pagina = new ModelAndView();
		pagina.setViewName("admin/seguimientoexp");
		return pagina;
	}

}
