(function($) {
	"use strict";
	$(document).ready(function() {
		$(document).on('click','#crownCaseBtn',function(){//刑事案件
			window.location.href=context+'/dp/showCrownCase.action';
		})
		$(document).on('click','#administrativeCaseBtn',function(){//行政案件
			window.location.href=context+'/dp/showAdministrativeCase.action';
		})
		$(document).on('click','#askRoomBtn',function(){//询问室
			window.location.href=context+'/dp/showAskRoom.action';
		})
		$(document).on('click','#personDetailsBtn',function(){//详情
			window.location.href=context+'/dp/showPersonDetails.action';
		})
	})
})(jQuery);