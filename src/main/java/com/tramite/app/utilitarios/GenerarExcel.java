package com.tramite.app.utilitarios;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.HojaRuta;
import com.tramite.app.Servicios.ExpedienteServicio;

@Service
public class GenerarExcel {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ExpedienteServicio  expedienteServicio;
	
	public XSSFSheet reporteHojaRuta(XSSFWorkbook libro,String anio,String codigodocumento) {
		
		Expediente infoExpediente = new Expediente();
		List<HojaRuta> listaHoja = new ArrayList<HojaRuta>();
		XSSFSheet pestania = libro.createSheet("HOJARUTAS");
		
		try {
			
			infoExpediente = expedienteServicio.infoExpedienteCodigo(anio, codigodocumento);
			if(infoExpediente!=null) {
				listaHoja = expedienteServicio.infoHojaRuta(anio, codigodocumento);
				if(listaHoja.size()>0) {
					// CABCERA DE REPORTE
					 XSSFRow cabeceraTitulo = pestania.createRow(0);
					 pestania.setColumnWidth(0, 2000);
					 pestania.setColumnWidth(1, 5000);
					 pestania.setColumnWidth(2, 5000);
					 pestania.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
					//cabeceraTitulo.createCell(0).setCellValue("REPORTE DE VISITAS DESDE "+pfechainicio+" HASTA "+pfechafin);
					//cabeceraTitulo.getCell(0).setCellStyle(StylesExcel.estiloVisitasCabeceraCentrada(libro)); 
				}
				
				
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return pestania;
	}
	
}
