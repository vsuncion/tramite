package com.tramite.app.utilitarios;

import java.awt.Color;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StylesExcel {

 
	
	public static XSSFCellStyle estiloVisitasCabeceraCentrada(XSSFWorkbook workbook) {
		
		XSSFCellStyle  style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		
		font.setFontName("Calibri");
		font.setBold(true);
		
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillForegroundColor(new XSSFColor(Color.ORANGE));
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		//style.setFillForegroundColor(new  XSSFColor(Color.BLACK));
		return style;
	}
	
	public static XSSFCellStyle estiloVisitasContenidoCentrado(XSSFWorkbook workbook) {
		XSSFCellStyle  style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		
		font.setFontName("Calibri");
		style.setFont(font);
		
		style.setAlignment(HorizontalAlignment.CENTER);
		
		return style;
	}
	
	public static XSSFCellStyle estiloVisitasRealizadas(XSSFWorkbook workbook) {
		XSSFCellStyle  style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		
		font.setFontName("Calibri");
		font.setBold(true);
		
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillForegroundColor(new XSSFColor(Color.CYAN));
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
		
	}
	
	public static XSSFCellStyle estiloVentasTiendasSubtitulos(XSSFWorkbook workbook) {
		XSSFCellStyle  style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		
		font.setFontName("Calibri");
		 
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.RIGHT);
		
		return style;
	}


}
