package com.tramite.app.Servicios.Impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.tramite.app.Servicios.FijaServicio;
import com.tramite.app.utilitarios.Constantes;

@Service
public class FijaServicioImpl implements FijaServicio {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${mail.from.email}")
	private String xFrom;
	
	@Value("${nombrePropetario}")
	private String xPropetario;
	
	@Value("${urlConfirmacionRegistro}")
	private String urlConfirmacionRegistro;
	
	@Override
	public String cuerpoCorreo(String nombreSolicitante, String codigoValidacion) {
		 String xcontenido = null;
		 xcontenido ="<html><body>";
		 xcontenido+="<p style=margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:'Calibri',sans-serif;'><span style='font-size: 12px; font-family: Arial, Helvetica, sans-serif;'>Estimados(a):</span></p>";
		 xcontenido+="<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:'Calibri',sans-serif;'><span style='font-family: Arial, Helvetica, sans-serif;'><span style='font-size: 12px;'>El siguiente correo es para confirmar su registro en el siguiente enlace : "+urlConfirmacionRegistro.concat(codigoValidacion)+"</span></span></p>";
		 xcontenido+="</ul></body></html>";
		 
		return xcontenido;
	}
	
	
	@Override
	public void enviarCorreo(String correoDestino,String cuerpo) {
		 MimeMessage message = javaMailSender.createMimeMessage();
		 try {
			message.setSubject("VERIFICACION DE USUARIO, "+xPropetario);
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setFrom(xFrom);
			//helper.setTo(lsCorreos.split(","));
			helper.setTo(correoDestino);
			helper.setText(cuerpo, true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			logger.error("CORREO ***********************"); 
			logger.error("Mensaje de Error: " +e.getMessage());
			logger.error("Fin ***************************************************************");
		}
		
	}


	 

}
