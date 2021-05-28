package com.tramite.app.utilitarios;

public class Constantes {
	
	// NOMBRE DE TABLAS
	public static final String tablaTrabajadores ="TRABAJADOR";
	public static final String tablaInformacion ="INFORMACION";
	public static final String tablaOficinas="OFICINA";
	public static final String tablaTipoDocumentos="TIPO_DOCUMENTO";
	public static final String tablaTipoTramite="TIPO_TRAMITE";
	public static final String tablaEstadoDocumento="ESTADO_DOCUMENTO";
	public static final String tablaProfesion="PROFESION";
	public static final String tablaRequisitos="REQUISITO";
	public static final String tablaTupac="TUPAC";
	public static final String tablaRequisitosTupac="REQUISITO_TUPAC";
	public static final String tablaPersona="PERSONA";
	public static final String tablaPerfil="PERFIL";
	public static final String tablaUsuario="USUARIO";
	public static final String tablaUsuarioPerfil="USUARIO_PERFIL";
	public static final String tablaFeriados="FERIADO";
	public static final String tablaPrePersona="PRE_PERSONA";
	public static final String tablaPersonaNatural="PERSONA_NATURAL";
	public static final String tablaPersonaJuridica="PERSONA_JURIDICA";
	
	public static int estadoDesactivado = 0;
	public static final String estadoDesactivadoLetras = "DESACTIVO";
	public static int estadoActivado = 1;
	public static final String estadoActivadoLetras = "ACTIVO";
	public static final int[] listaEstadoRegistro= {0,1};
	public static final int[] listaTipoDias={1,2};
	public static final int[] listaTipoDocumentoRegistro={1,2}; // 1 =DNI , 2=CARNET EXTRANJERIA
	public static final String[] listaBusquedaTrabajador={"NOMBRE","APELLIDO_PATERNO","APELLIDO_MATERNO","DNI"};
	public static final String listaBusquedaTrabajadorNombre="NOMBRE";
	public static final String listaBusquedaTrabajadorAPELLIDO_PATERNO="APELLIDO_PATERNO";
	public static final String listaBusquedaTrabajadorAPELLIDO_MATERNO="APELLIDO_MATERNO";
	public static final String listaBusquedaTrabajadorDNI="DNI";
	
	
	public static final String estadoTipoDiasLaborables = "Dias Laborables";
	public static final String estadoTipoDiasCalendario = "Dias Calendario";
	public static final String accionSI="SI";
	public static final String accionNO="SI";
	public static final String codigoMensajeOK="OK";
	public static final String codigoMensajeERROR="ERROR";
	public static final String mensajeRegistroOK="Registro Correcto";
	public static final String mensajeRegistroError="Error al Registrar";
	public static final String mensajeRegistroDuplicado="El Registro $NOMBRE$ se encuentra Duplicado";
	public static final String  TipoDocumentoRegistroDNI ="DNI";
	public static final String  TipoDocumentoRegistroCarnetExtranjeria="CARNET EXTRANJERIA";
	
	public static final int transaccionCorrecta = 0;
	public static final int transaccionIncorrecta = 1;
	public static final int transaccionSinAccion = 2;
	
	public static final String transaccionCorrectaTexto = "OK";
	public static final String transaccionIncorrectaTexto = "ERROR"; 
	public static final int[] listaTipoPersona= {1,2}; // 1 NATURAL / 2 JURIDICA
	public static final int tipoPersonaNatural=1;
	public static final int tipoPersonaJuridica=2;
	public static final String tipoPersonaTexto="PERSONA NATURAL";
	public static final String tipoPersonaJuridicaTexto="PERSONA JURIDICA";

}
