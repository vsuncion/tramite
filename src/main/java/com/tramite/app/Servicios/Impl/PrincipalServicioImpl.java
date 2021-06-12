package com.tramite.app.Servicios.Impl;

import java.util.ArrayList;
import java.util.List;

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
import com.tramite.app.Entidades.PreRequisitoTupa;
import com.tramite.app.Entidades.RequisitosTupac;
import com.tramite.app.Entidades.Seleccion;
import com.tramite.app.Entidades.Tupac;
import com.tramite.app.Servicios.FijaServicio;
import com.tramite.app.Servicios.MantenimientoServicio;
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
	
	@Autowired
	private MantenimientoServicio  mantenimientoServicio;

	@Override
	public Persona buscarPersona(int tipoPersona, String vnumero) { 
		return principalDao.buscarPersona(tipoPersona, vnumero);
	}

	@Override
	public MensajeRespuesta guardarPrePersona(PrePersona prePersona) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		
		// VERICAMOS QUE LA PERSONA NO SE REPITA
		
		
		
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

	@Override
	public Long guardarPreTupac(Expediente expediente) { 
		return principalDao.guardarPreTupac(expediente);
	}

	@Override
	public Expediente preTupacExpediente(Long idprexpediente) { 
		return principalDao.preTupacExpediente(idprexpediente);
	}

	@Override
	public MensajeRespuesta guardarPreRequisito(PreRequisitoTupa preRequisitoTupa) { 
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta(); 
		boolean respuesta = principalDao.guardarPreRequisito(preRequisitoTupa);
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
	public List<PreRequisitoTupa> listaPreRequisitos(Long idprexpediente) { 
		return principalDao.listaPreRequisitos(idprexpediente);
	}

	@Override
	public  List<Seleccion> listasTupacRequisitos() { 
		List<Seleccion> listafinal = new ArrayList<Seleccion>();
		List<Tupac> lista = principalDao.listasTupacRequisitos();
		for (Tupac i : lista) {
			Seleccion item = new Seleccion();
			item.setCodigo(i.getTUPACPK());
			item.setEtiqueta(i.getVNOMBRE());
			listafinal.add(item);
		}
		return listafinal;
	}

	@Override
	public PreRequisitoTupa infoPreRequisitoTupa(PreRequisitoTupa preRequisitoTupa) { 
		return principalDao.infoPreRequisitoTupa(preRequisitoTupa);
	}

	@Override
	public void guardarDetalleArchivosExpedienteTupa(Expediente formExpediente) {
		principalDao.guardarDetalleArchivosExpedienteTupa(formExpediente);
		
	}

	@Override
	public void eliminarArchivoRequerimeinto(Long idprexpediente, Long idrequisito) {
		principalDao.eliminarArchivoRequerimeinto(idprexpediente, idrequisito);
	}

	@Override
	public List<RequisitosTupac> listaRequerimientosTupac(Long idtupac) {  
		return mantenimientoServicio.listarRequisitosTupacPorIdTupac(idtupac);
	}

	 

}
