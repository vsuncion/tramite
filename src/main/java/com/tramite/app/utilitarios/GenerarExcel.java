package com.tramite.app.utilitarios;

import java.util.List;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.tramite.app.Entidades.Expediente;
import com.tramite.app.Entidades.HojaRuta;
import com.tramite.app.Entidades.ReporteExpediente;

@Service
public class GenerarExcel {
	Logger logger = LoggerFactory.getLogger(getClass());

	public XSSFSheet reporteHojaRuta(XSSFWorkbook libro, Expediente infoExpediente, List<HojaRuta> listaHojaRuta) {

		XSSFSheet pestania = libro.createSheet("HOJARUTAS");

		try {

			// CABCERA DE REPORTE
			XSSFRow cabeceraTitulo = pestania.createRow(0);
			pestania.setColumnWidth(0, 2000);
			pestania.setColumnWidth(1, 5000);
			pestania.setColumnWidth(2, 6000);
			pestania.setColumnWidth(3, 5000);
			pestania.setColumnWidth(4, 5000);
			pestania.setColumnWidth(5, 5000);
			pestania.setColumnWidth(6, 5000);
			pestania.setColumnWidth(7, 7000);
			pestania.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
			cabeceraTitulo.createCell(0)
					.setCellValue("HOJA DE RUTA DEL EXPEDIENTE " + infoExpediente.getVCODIGO_EXPEDIENTE());
			cabeceraTitulo.getCell(0).setCellStyle(StylesExcel.estiloVisitasCabeceraCentrada(libro));

			// CABCERA DE REPORTE
			XSSFRow fila1 = pestania.createRow(1);
			fila1.createCell(0).setCellValue("ITEM");
			fila1.getCell(0).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(1).setCellValue("TIPO DOCUMENTO");
			fila1.getCell(1).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(2).setCellValue("FECHA EN OFICINA");
			fila1.getCell(2).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(3).setCellValue("OFICINA DE ORIGEN");
			fila1.getCell(3).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(4).setCellValue("ESTADO DOCUMENTO");
			fila1.getCell(4).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(5).setCellValue("OFICINA DE DESTINO");
			fila1.getCell(5).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(6).setCellValue("FECHA RECEPCION");
			fila1.getCell(6).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(7).setCellValue("OBSERVACION");
			fila1.getCell(7).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));

			int row = 2;

			for (HojaRuta i : listaHojaRuta) {
				XSSFRow dataRow = pestania.createRow(row++);
				dataRow.createCell(0).setCellValue(i.getNITEM());
				dataRow.createCell(1).setCellValue(i.getTIPO_DOCUMENTO());
				dataRow.createCell(2).setCellValue(i.getVFECHAOFICINA());
				dataRow.createCell(3).setCellValue(i.getOFICINA_ORIGEN());
				dataRow.createCell(4).setCellValue(i.getESTADO_DOCUMENTO());
				dataRow.createCell(5).setCellValue(i.getOFICINA_DESTINO());
				dataRow.createCell(6).setCellValue(i.getVFECHARECEPCION());
				dataRow.createCell(7).setCellValue(i.getVOBSERVACION());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return pestania;
	}

	public XSSFSheet reporteEstadoDocumentos(XSSFWorkbook libro, List<ReporteExpediente> listaDocumentos) {
		XSSFSheet pestania = libro.createSheet("EXPEDIENTE_DOCUMENTOS");

		try {

			// CABCERA DE REPORTE
			XSSFRow cabeceraTitulo = pestania.createRow(0);
			pestania.setColumnWidth(0, 2000);
			pestania.setColumnWidth(1, 5000);
			pestania.setColumnWidth(2, 6000);
			pestania.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
			cabeceraTitulo.createCell(0).setCellValue("ESTADO DOCUMENTOS");
			cabeceraTitulo.getCell(0).setCellStyle(StylesExcel.estiloVisitasCabeceraCentrada(libro));

			// CABCERA DE REPORTE
			XSSFRow fila1 = pestania.createRow(1);
			fila1.createCell(0).setCellValue("ITEM");
			fila1.getCell(0).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(1).setCellValue("ESTADO DOCUMENTO");
			fila1.getCell(1).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(2).setCellValue("CANTIDAD");
			fila1.getCell(2).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));

			int row = 2;

			for (ReporteExpediente i : listaDocumentos) {
				XSSFRow dataRow = pestania.createRow(row++);
				dataRow.createCell(0).setCellValue(i.getNITEM());
				dataRow.createCell(1).setCellValue(i.getVESTADOCUEMNTO());
				dataRow.createCell(2).setCellValue(i.getNCANTIDAD());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return pestania;

	}

	public XSSFSheet reporteEstadoDocumentoOficinas(XSSFWorkbook libro, List<ReporteExpediente> listaDocumentoOficina) {
		XSSFSheet pestania = libro.createSheet("EXPEDIENTE_OFICINA");

		try {

			// CABCERA DE REPORTE
			XSSFRow cabeceraTitulo = pestania.createRow(0);
			pestania.setColumnWidth(0, 2000);
			pestania.setColumnWidth(1, 5000);
			pestania.setColumnWidth(2, 6000);
			pestania.setColumnWidth(3, 5000);

			pestania.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
			cabeceraTitulo.createCell(0).setCellValue("EXPEDIENTES POR OFICINA");
			cabeceraTitulo.getCell(0).setCellStyle(StylesExcel.estiloVisitasCabeceraCentrada(libro));

			// CABCERA DE REPORTE
			XSSFRow fila1 = pestania.createRow(1);
			fila1.createCell(0).setCellValue("ITEM");
			fila1.getCell(0).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(1).setCellValue("NOMBRE OFICINA");
			fila1.getCell(1).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(2).setCellValue("ESTADO DOCUMENTO");
			fila1.getCell(2).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(3).setCellValue("CANTIDAD");
			fila1.getCell(3).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));

			int row = 2;

			for (ReporteExpediente i : listaDocumentoOficina) {
				XSSFRow dataRow = pestania.createRow(row++);
				dataRow.createCell(0).setCellValue(i.getNITEM());
				dataRow.createCell(1).setCellValue(i.getVNOMBREOFICINA());
				dataRow.createCell(2).setCellValue(i.getVESTADOCUEMNTO());
				dataRow.createCell(3).setCellValue(i.getNCANTIDAD());
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return pestania;
	}
	
	
	public XSSFSheet buscarexpedientesinternorptexportar(XSSFWorkbook libro,List<Expediente> listaExpediente) {
		XSSFSheet pestania = libro.createSheet("EXPEDIENTE_OFICINA");
		
		try {
			// CABCERA DE REPORTE
			XSSFRow cabeceraTitulo = pestania.createRow(0);
			pestania.setColumnWidth(0, 2000);
			pestania.setColumnWidth(1, 5000);
			pestania.setColumnWidth(2, 6000);
			pestania.setColumnWidth(3, 10000);	
			pestania.setColumnWidth(4, 10000);
			pestania.setColumnWidth(5, 11000);
			pestania.setColumnWidth(6, 11000);
			
			pestania.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
			cabeceraTitulo.createCell(0).setCellValue("EXPEDIENTES INTERNO POR OFICINA");
			cabeceraTitulo.getCell(0).setCellStyle(StylesExcel.estiloVisitasCabeceraCentrada(libro));
			
			// CABCERA DE REPORTE
			XSSFRow fila1 = pestania.createRow(1);
			fila1.createCell(0).setCellValue("ITEM");
			fila1.getCell(0).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro)); 
			fila1.createCell(1).setCellValue("FECHA REGISTRO");
			fila1.getCell(1).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(2).setCellValue("EXPEDIENTE");
			fila1.getCell(2).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(3).setCellValue("ASUNTO");
			fila1.getCell(3).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(4).setCellValue("USUARIO");
			fila1.getCell(4).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro)); 
			fila1.createCell(5).setCellValue("OFICINA ORIGEN");
			fila1.getCell(5).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			fila1.createCell(6).setCellValue("OFICINA ACTUAL");
			fila1.getCell(6).setCellStyle(StylesExcel.estiloVisitasRealizadas(libro));
			 
			
			int row = 2;
			
			for (Expediente i : listaExpediente) {
				XSSFRow dataRow = pestania.createRow(row++);
				dataRow.createCell(0).setCellValue(i.getNITEM());
				dataRow.createCell(1).setCellValue(i.getVDFECREGISTRO());
				dataRow.createCell(2).setCellValue(i.getVCODIGO_EXPEDIENTE());
				dataRow.createCell(3).setCellValue(i.getVASUNTO());
				dataRow.createCell(4).setCellValue(i.getUSUARIO_OFICINA());
				dataRow.createCell(5).setCellValue(i.getVNOMBRE_OFICINA_ORIGEN());
				dataRow.createCell(6).setCellValue(i.getVNOMBRE_OFICINA_ACTUAL());
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return pestania;
	}
	
	
	

}
