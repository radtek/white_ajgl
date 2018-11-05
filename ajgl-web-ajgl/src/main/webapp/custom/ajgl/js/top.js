

$(document).ready(function() {
	$(".c-top-right ul li").each(function(){
		if($.util.isBlank($(this).html())){
			$(this).remove();
		}
	})
});

function jumpOn(){
	window.top.jumpOn();
}

function jumpOff(){
	window.top.jumpOff();
}

function getDsspocxObject(){
	return window.top.document.getElementById("dsspocxObject");
}
