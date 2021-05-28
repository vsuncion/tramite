
function listarProvincias(url,formulario,objeto,iddepa){
	var idForm = formulario;
	 $("#cbprovincia").empty();
	 $("#cbprovincia").append('<option value="0"  class="letras_select_texto">[ SELECCIONE ]</option>');
	
	jQuery.ajax({
		url : url+iddepa,
		type : 'get',
		success : function(info) {
			 
			$.each( info, function( key, value ) {
			    $("#cbprovincia").append('<option value="'+info[key].provincia_id_pk+'"  class="letras_select_texto">['+info[key].provincia_id_pk+'] '+info[key].vnombre+'</option>'); 
			});
		},
                error:function(){
                	alert("error")
                }
	});	
}

function listarDistritos(url,formulario,objeto,iddepa,idprov){
	var idForm = formulario;
	 $("#cbdistrito").empty();
	 $("#cbdistrito").append('<option value="0" class="letras_select_texto">[ SELECCIONE ]</option>');
	if(iddepa!=0){
		
		jQuery.ajax({
			url : url+iddepa+'/'+idprov,
			type : 'get',
			success : function(info) {
				 
				$.each( info, function( key, value ) {
				    $("#cbdistrito").append('<option value="'+info[key].distrito_id_pk+'" class="letras_select_texto">['+info[key].distrito_id_pk+'] '+info[key].vnombre+'</option>'); 
				});
			}
		});
	}
	
}
 
