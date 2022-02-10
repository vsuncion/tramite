package com.tramite.app.Servicios.Impl;

import java.util.ArrayList;
import java.util.List;
import com.tramite.app.excepciones.ResultadoException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.tramite.app.Datos.PrincipalDao;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.MensajeRespuesta;
import com.tramite.app.Entidades.Persona;
import com.tramite.app.Entidades.PersonaJuridica;
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
	
	//Logger logger = LoggerFactory.getLogger(getClass());
	private static final Logger logger = Logger.getLogger(PrincipalServicioImpl.class);
	
	@Autowired
	private PrincipalDao  principalDao;
	
	@Autowired
	private FijaServicio  fijaServicio;
	
	@Autowired
	private MantenimientoServicio  mantenimientoServicio;

	@Override
	public Persona buscarPersona(PrePersona prePersona) {
		return principalDao.buscarPersona(prePersona);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta guardarPrePersona(PrePersona prePersona) {
		boolean respuesta = false;
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		//GENERAMOS LOS CORRELATIVOS 
		prePersona.setVCODIGOACTIVACION(AutoGenerados.getCorrelativoArchivo());

		try {
			respuesta = principalDao.guardarPrePersona(prePersona);
			if(respuesta==true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto+", Se le envio un correo para confirmar a "+prePersona.getVCORREO()+", por favor confirme el correo");

				//ENVIAMOS CORREO
				String  cuerpo = "";
				cuerpo=fijaServicio.cuerpoCorreo(prePersona.getVNOMBRE()+" "+prePersona.getVAPEMATERNO()+" "+prePersona.getVAPEMATERNO(), prePersona.getVCODIGOACTIVACION());
				fijaServicio.enviarCorreo(prePersona.getVCORREO(), cuerpo);
			}else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}

		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}


		return mostrarmensaje;
	}

	@Override
	public MensajeRespuesta activarRegistroPrePersona(String codigoActivacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta confirmacionCodigoActivacion(String codigoActivacion) {
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();
		boolean respuesta = false;

		try {
			respuesta = principalDao.confirmacionCodigoActivacion(codigoActivacion);
			if(respuesta==true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);
			}else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());

		}
 
		return mostrarmensaje;
	}

	@Override
	@Transactional(readOnly = true)
	public Persona busquedaSolicitante(Expediente expediente) {
		Persona buscarPersona = null;
		try {
			buscarPersona = principalDao.busquedaSolicitante(expediente);
		} catch (Exception e) {
			logger.info("error busquedaSolicitante = "+e.getMessage());
		}
		return buscarPersona;

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public boolean guardarExpedienteSimple(Expediente expediente) {

		try {
			return principalDao.guardarExpedienteSimple(expediente);
		} catch (Exception e) {
			logger.info("ERROR :"+e.getMessage());
			return false;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Long guardarPreTupac(Expediente expediente) {
		Long idRespuesta = 0L;
		try {
			idRespuesta= principalDao.guardarPreTupac(expediente);
		} catch (Exception e) {
			logger.info("ERROR :"+e.getMessage());
			idRespuesta=0L;
		}
		return idRespuesta;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Expediente preTupacExpediente(Long idprexpediente)
	{
		Expediente expediente = new Expediente();
		try {
			expediente =principalDao.preTupacExpediente(idprexpediente);
		} catch (Exception e) {
			logger.info("ERROR :"+e.getMessage());
			e.printStackTrace();
		}
		return  expediente;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public MensajeRespuesta guardarPreRequisito(PreRequisitoTupa preRequisitoTupa) { 
		MensajeRespuesta mostrarmensaje = new MensajeRespuesta();

		boolean respuesta = false;
		try {
			respuesta = principalDao.guardarPreRequisito(preRequisitoTupa);
			if(respuesta==true) {
				mostrarmensaje.setCodigo(Constantes.transaccionCorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionCorrectaTexto);
			}else {
				mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
				mostrarmensaje.setMensaje(Constantes.transaccionIncorrectaTexto);
			}
		} catch (Exception e) {
			mostrarmensaje.setCodigo(Constantes.transaccionIncorrecta);
			mostrarmensaje.setMensaje(e.getMessage());
		}
		return mostrarmensaje; 
	}

	@Override
	@Transactional(readOnly = true)
	public List<PreRequisitoTupa> listaPreRequisitos(Long idprexpediente) { 
		return principalDao.listaPreRequisitos(idprexpediente);
	}

	@Override
	@Transactional(readOnly = true)
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
	@Transactional(readOnly = true)
	public PreRequisitoTupa infoPreRequisitoTupa(PreRequisitoTupa preRequisitoTupa) {
		PreRequisitoTupa buscarPreRequisitoTupa = new PreRequisitoTupa();
		try {
			buscarPreRequisitoTupa = principalDao.infoPreRequisitoTupa(preRequisitoTupa);
		} catch (Exception e) {
			logger.info("error infoPreRequisitoTupa = "+e.getMessage());
		}
		return buscarPreRequisitoTupa;

	}

	@Override
	@Transactional(readOnly = true)
	public void guardarDetalleArchivosExpedienteTupa(Expediente formExpediente) {

		try {
			principalDao.guardarDetalleArchivosExpedienteTupa(formExpediente);
		} catch (Exception e) {
			logger.info("error guardarDetalleArchivosExpedienteTupa = "+e.getMessage());
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void eliminarArchivoRequerimeinto(Long idprexpediente, Long idrequisito) {

		try {
			principalDao.eliminarArchivoRequerimeinto(idprexpediente, idrequisito);
		} catch (Exception e) {
			logger.info("ERROR :"+e.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<RequisitosTupac> listaRequerimientosTupac(Long idtupac) {  
		return mantenimientoServicio.listarRequisitosTupacPorIdTupac(idtupac);
	}

	@Override
	@Transactional(readOnly = true)
	public PersonaJuridica buscarPersonaJuridicaDuplicada(PrePersona prePersona) {

		PersonaJuridica personaJuridica = null;
		try {
			personaJuridica = principalDao.buscarPersonaJuridicaDuplicada(prePersona);
		} catch (Exception e) {
			logger.info("error buscarPersonaJuridicaDuplicada ="+e.getMessage());
		}
		return personaJuridica;

	}

	@Override
	@Transactional(readOnly = true)
	public PrePersona buscarPrepersona(PrePersona prePersona) {
		PrePersona buscarPrePersona = null;
		try {
			buscarPrePersona = principalDao.buscarPrepersona(prePersona);
		} catch (Exception e) {
			logger.info("error buscarPrepersona ="+e.getMessage());
		}
		return buscarPrePersona;
	}

	@Override
	public PrePersona buscarPrepersonaDuplicada(PrePersona prePersona) {
		PrePersona buscarPrePersona = new PrePersona();
		try {
			buscarPrePersona = principalDao.buscarPrepersonaDuplicada(prePersona);
		} catch (Exception e) {
			logger.info("error buscarPrepersonaDuplicada"+e.getMessage());
		}
		return buscarPrePersona;
	}

}
