package com.tramite.app.Servicios.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.tramite.app.Datos.PrincipalDao;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.PrePersona; 
import com.tramite.app.Servicios.FijaServicio;
import com.tramite.app.Servicios.PrincipalServicio;
import com.tramite.app.utilitarios.AutoGenerados;
import com.tramite.app.utilitarios.Constantes; 

@Service
public class PrincipalServicioImpl implements PrincipalServicio {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PrincipalDao  principalDao;
	
	@Autowired
	private FijaServicio  fijaServicio;

	@Override
	public Persona buscarPersona(int tipoPersona, String vnumero) { 
		return principalDao.buscarPersona(tipoPersona, vnumero);
	}

	@Override
	public MensajeRespuesta guardarPrePersona(PrePersona prePersona) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		//GENERAMOS LOS CORRELATIVOS 
		prePersona.setVCODIGOACTIVACION(AutoGenerados.getCorrelativoArchivo());
		
		respuesta = principalDao.guardarPrePersona(prePersona);
		
		if(respuesta==true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto+", Se le envio un correo a "+prePersona.getVCORREO());
			
			//ENVIAMOS CORREO
			String  cuerpo = "";
			 cuerpo=fijaServicio.cuerpoCorreo(prePersona.getVNOMBRE()+" "+prePersona.getVAPEMATERNO()+" "+prePersona.getVAPEMATERNO(), prePersona.getVCODIGOACTIVACION());
			 fijaServicio.enviarCorreo(prePersona.getVCORREO(), cuerpo);
		}else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}
		return mostrarmensaje;
	}

	@Override
	public MensajeRespuesta activarRegistroPrePersona(String codigoActivacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public MensajeRespuesta confirmacionCodigoActivacion(String codigoActivacion) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta(); 
		boolean respuesta = false;
		respuesta = principalDao.confirmacionCodigoActivacion(codigoActivacion);
		if(respuesta==true) {
			mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);
		}else {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
		}
		return mostrarmensaje;
	}

	@Override
	public Persona busquedaSolicitante(Expediente expediente) { 
		return principalDao.busquedaSolicitante(expediente);
	}

	@Override
	public boolean guardarExpedienteSimple(Expediente expediente) {
		return principalDao.guardarExpedienteSimple(expediente);
	}

	 

}
