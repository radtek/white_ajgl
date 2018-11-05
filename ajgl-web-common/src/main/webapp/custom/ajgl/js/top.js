
$(document).ready(function() {	
	$(".c-top-right ul li").each(function(){
		if($.util.isBlank($(this).html())){
			$(this).remove();
		}
	})

	
});




