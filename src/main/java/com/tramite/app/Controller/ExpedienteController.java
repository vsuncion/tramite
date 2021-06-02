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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tramite.app.Entidades.Bandeja;
import com.tramite.app.Servicios.ExpedienteServicio;

@Controller
@RequestMapping("/admin/expediente")
public class ExpedienteController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ExpedienteServicio expedienteServicio;
	
	@GetMapping(value = { "/listarBandeja" })
	public ModelAndView listarBandeja(HttpServletRequest request,HttpServletResponse res) {
		ModelAndView pagina = new ModelAndView();
		List<Bandeja> listaBandeja = new ArrayList<Bandeja>();
		Bandeja formBandeja = new Bandeja();
		
		Long oficina =1L;
		Long estadodocumento =1L;
		listaBandeja = expedienteServicio.listarBandeja(oficina, estadodocumento);
		
		
		pagina.addObject("formBandeja",formBandeja);
		pagina.addObject("listaBandeja",listaBandeja);
		pagina.setViewName("admin/bandeja/listar");
		return pagina;
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
