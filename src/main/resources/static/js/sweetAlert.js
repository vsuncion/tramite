function verificarMantenimiento(datos){
	switch(datos.codigo) {
	  case 0:
		    Swal.fire({
				  icon: 'success',
				  title: 'Correcto',
				  text: datos.mensaje 
				});
		    break;
		    
	    
	  case 1:
			   Swal.fire({
				  icon: 'error',
				  title: 'Ocurrio un Error',
				  text: datos.mensaje 
				 });
		       break;
		       
		       
	   case 2:
	    console.log('sin accion')
	    break;
	  default:
	    console.log('..')
	}
 } 
 
 
 function funeliminartag(btnDelete,nombretag){
       if (btnDelete.dataset.confirmed=="true") {
         return true;
       
    } else {
        // Ask the user to confirm/cancel the action
        event.preventDefault();
        Swal.fire({
			  title: 'Desactivar ?',
			  text: "Desea Desactivar ("+nombretag+") !",
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Confirmar',
			  cancelButtonText: 'Cancelar'
			}).then((result) => {
			
			  if (result.isConfirmed) {
			    btnDelete.dataset.confirmed = true; 
			  }else{
			     btnDelete.dataset.confirmed = false;
			  }
			})
	        .then(function () { 
	            console.log('***********'+btnDelete.dataset.confirmed)
	           if(btnDelete.dataset.confirmed=="true"){
	             btnDelete.dataset.confirmed = true;  
	             btnDelete.click();
	           }else{
	             btnDelete.dataset.confirmed = false;
	           }
	           
	        }).catch(function (reason) { 
	            return false;
	        });
	    }
 }
 
 
  function funeliminarpublicacion(btnDelete,titulopublicacion){
       if (btnDelete.dataset.confirmed=="true") {
         return true; 
       
    } else {
        // Ask the user to confirm/cancel the action
        event.preventDefault();
        Swal.fire({
			  title: 'Inactivar Publicación?',
			  text: "Desea Inactivar la publicación("+titulopublicacion+") !",
			  icon: 'warning',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Confirmar',
			  cancelButtonText: 'Cancelar'
			}).then((result) => {
			
			  if (result.isConfirmed) {
			    btnDelete.dataset.confirmed = true; 
			  }else{
			     btnDelete.dataset.confirmed = false;
			  }
			})
	        .then(function () { 
	            console.log('***********'+btnDelete.dataset.confirmed)
	           if(btnDelete.dataset.confirmed=="true"){
	             btnDelete.dataset.confirmed = true;  
	             btnDelete.click();
	           }else{
	             btnDelete.dataset.confirmed = false;
	           }
	           
	        }).catch(function (reason) { 
	            return false;
	        });
	    }
 }
 
 
  function funactivarpublicaciontag(btnDelete,titulopublicacion,codigo){
       if (btnDelete.dataset.confirmed=="true") {
         return true; 
       
    } else {
        // Ask the user to confirm/cancel the action
        event.preventDefault();
        Swal.fire({
			  title: titulopublicacion,
			  text: "DESEA "+titulopublicacion+" EL CODIGO "+codigo,
			  icon: 'info',
			  showCancelButton: true,
			  confirmButtonColor: '#3085d6',
			  cancelButtonColor: '#d33',
			  confirmButtonText: 'Confirmar',
			  cancelButtonText: 'Cancelar'
			}).then((result) => {
			
			  if (result.isConfirmed) {
			    btnDelete.dataset.confirmed = true; 
			  }else{
			     btnDelete.dataset.confirmed = false;
			  }
			})
	        .then(function () { 
	            console.log('***********'+btnDelete.dataset.confirmed)
	           if(btnDelete.dataset.confirmed=="true"){
	             btnDelete.dataset.confirmed = true;  
	             btnDelete.click();
	           }else{
	             btnDelete.dataset.confirmed = false;
	           }
	           
	        }).catch(function (reason) { 
	            return false;
	        });
	    }
 }