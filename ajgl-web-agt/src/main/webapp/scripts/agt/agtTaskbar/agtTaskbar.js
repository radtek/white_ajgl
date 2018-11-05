(function($) {
	"use strict";
    var hostid=null;
    $(document).ready(function() {
    	onload();
    	$(document).on("click","#messageAlert",function(){//消息提醒
    		window.open(context + '/agtTaskbar/messageAlert.action?formCode='+formCode);
    		//messageAlert();
		})
		$(document).on("click","#suspectMessage",function(){//嫌疑人信息完善
			window.open(context + '/agtTaskbar/suspectMessage.action?formId='+formId);
			//suspectMessage();
		})
		$(document).on("click","#result",function(){//研判结果查看
			window.open(context + '/agtTaskbar/result.action?formId='+formId);
			//result();
		})
    });
    
    /**
     * 加载信息
     * @returns
     */
    function onload(){
    	$.ajax({
    		url : context + '/agtTaskbar/findMessageCount.action',
    		type : "POST",
    		dataType : "json",
    		data:{
    			"formCode":formCode,
    		},
    		success:function(data){
    			var count=data.resultMap.count;
    			if(count==0){
    				$('#messageCount').hide();
    			}
    			$('#messageCount').text(count);
    		}
    	});
    }
})(jQuery);