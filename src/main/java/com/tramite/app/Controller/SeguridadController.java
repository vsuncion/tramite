package com.tramite.app.Controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller 
public class SeguridadController {
	
	@GetMapping(value = {"/login"})
	public String login(Model model, Principal principal,RedirectAttributes flash,
			@RequestParam(value = "error", required = false) String error ) {
		if(principal !=null) {
			flash.addFlashAttribute("info", "ya iniicio sesion");
			return "redirect:/admin/principal";
		}
		
		if(error!=null) {
			model.addAttribute("error","Error en el login: Usuario o Clave incorrecta");
		}
		return "admin/login";
	}

	@GetMapping(value = "/accesodenagado")
	public String accesoDenegado() {
		return "/admin/accesodenagado";
	}
}
