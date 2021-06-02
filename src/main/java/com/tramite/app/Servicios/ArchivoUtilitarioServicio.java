package com.tramite.app.Servicios;

import org.springframework.web.multipart.MultipartFile;

import com.tramite.app.Entidades.Archivos;

public interface ArchivoUtilitarioServicio {
	Archivos cargarArchivo(MultipartFile file,String nombreArchivo); 
	String copiar(MultipartFile archivo, String nomArchivo, String rutaRaiz); 
}
