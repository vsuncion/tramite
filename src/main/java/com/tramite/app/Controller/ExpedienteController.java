package com.tramite.app.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/expediente")
public class ExpedienteController {

	Logger logger = LoggerFactory.getLogger(getClass());

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
