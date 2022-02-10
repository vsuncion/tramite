package com.tramite.app.Servicios.Impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile; 
import com.tramite.app.Entidades.Archivos;
import com.tramite.app.Servicios.ArchivoUtilitarioServicio;
import com.tramite.app.utilitarios.Constantes;
import com.tramite.app.utilitarios.ConstantesArchivos; 
 
 

@Service
public class ArchivoUtilitarioServicioImpl implements ArchivoUtilitarioServicio {
	
	@Value("${rutaArchivo}")
	private String rutaRaiz;
	
	//Logger logger = LoggerFactory.getLogger(getClass());
	private static final Logger logger = Logger.getLogger(ArchivoUtilitarioServicioImpl.class);

	@Override
	@Transactional(readOnly = true)
	public Archivos cargarArchivo(MultipartFile file, String nombreArchivo) {
		Archivos archivo = new Archivos();
		String estadoCopiado = "";
		try {
			if (!file.isEmpty()) {
				archivo.setNombre(nombreArchivo);
				archivo.setRuta(ConstantesArchivos.getObtenerRutaCarpetas());
				archivo.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
				archivo.setRutacompleta(rutaRaiz + archivo.getRuta());
				archivo.setNombreExtension(archivo.getNombre() + "."+ archivo.getExtension());
				archivo.setRutaNombreArchivo(archivo.getRutacompleta() + archivo.getNombreExtension());
				
				// 1. COPIAR EL ARCHIVO AL REPOSITORIO
				estadoCopiado = copiar(file, archivo.getNombre() + "."+ archivo.getExtension(), rutaRaiz);
				if (estadoCopiado.equals("OK")) { // 2. VERIFICAR SI EXISTE ERROR AL COPIAR EL ARCHIVO
					archivo.setVerificarCarga(true);
					archivo.setMensaje(Constantes.C_ARCHIVO_CARGA_CORRECTA);
				} else {
					archivo.setVerificarCarga(false);
					archivo.setMensaje(Constantes.C_ARCHIVO_CARGA_INCORRECTA);
				}

				// 3. VERIFICACION DE LA EXISTENCIA DEL ARCHIVO EN  LA RUTA
				if (ConstantesArchivos.existeFileEnRuta(archivo.getRutaNombreArchivo())) { 
					archivo.setVerificarUbicacion(true);
					archivo.setMensaje(Constantes.C_ARCHIVO_VALIDACION_UBICACION_CORRECTA);
				} else {
					archivo.setVerificarUbicacion(false);
					archivo.setMensaje(Constantes.C_ARCHIVO_VALIDACION_UBICACION_INCORRECTA);
				}
				
			}
			
		} catch (Exception e) {
			archivo.setVerificarCarga(false);
			archivo.setVerificarUbicacion(false);
			archivo.setMensaje(e.getMessage().concat(":").concat(e.getCause().getMessage()));
		}
		return archivo;
	}

	@Override
	@Transactional(readOnly = true)
	public String copiar(MultipartFile archivo, String nomArchivo, String rutaRaiz) {
		String ruta_guardar =rutaRaiz+ ConstantesArchivos.getObtenerRutaCarpetas() +  nomArchivo;

		File rutaFile = new File(rutaRaiz);
		try {

			if (rutaFile.exists()) {
				File directorio = new File(
						rutaRaiz + System.getProperty("file.separator") + ConstantesArchivos.getObtenerRutaCarpetas());
				directorio.mkdirs(); // CREAR DIRECTORIO
			}

			if (!ConstantesArchivos.isEmptyFile(archivo)) {// Verificar que el archivo no sea vacio
				File localFile = new File(ruta_guardar);
				FileOutputStream os = null;
				try {
					os = new FileOutputStream(localFile);
					os.write(archivo.getBytes());
				} catch (IOException e) {
					logger.error("Error al guardar archivo en disco>>" + e);
					return "ERROR";
				} finally {
					if (os != null)
						os.close();
				}

			} else {
				return "ERROR";
			}
		} catch (IOException e1) {
			logger.error("BUS copiar>>>>" + this.getClass().getName(), e1);
			return "ERROR";
		}

		return "OK";
	}

	 

	 

}
