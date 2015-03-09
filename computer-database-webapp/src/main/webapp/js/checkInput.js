$(document).ready(function () {
	var isIntroducedValid = true;
	var isDiscontinuedValid = true;

	$('#introduced').on('input', function() {    
	       if (!$(this).val().match(strings['DateRegex']) && $(this).val() != "") {
	           $(this).css( "border", "2px solid red");
	           isIntroducedValid = false;
	       }
	       else{
	        isIntroducedValid = true;
	           $(this).css( "border", "");  
	       }
	       checkValid()
	});

	$('#discontinued').on('input', function() {        
	       if (!$(this).val().match(strings['DateRegex']) && $(this).val() != "") {
	           $(this).css( "border", "2px solid red");
	           isIntroducedValid = false;
	       }
	       else{
	        isIntroducedValid = true;
	           $(this).css( "border", "");  
	       }
	       checkValid();
	});

	function checkValid(){
	if (isIntroducedValid && isDiscontinuedValid)
	$(".validation").removeClass( "disabled" );
	else
	           $(".validation").addClass( "disabled" );
	}
	});