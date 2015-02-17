$(document).ready(function () {
		$( ".validation" ).click( function(){
			var dateformat = "^(19|20)\\d\\d-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$";
			
			$( ".date" ).each(function( index ) {
				if (!$(this).val().match(dateformat) && $(this).val()) {
					$(this).css( "border", "2px solid red");
				}
				else{
					$(this).css( "border", "");	
				}
			});
			
       });
});