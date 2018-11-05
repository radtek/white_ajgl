(function($) {
	"use strict";
    var hostid=null;
    $(document).ready(function() {
    	onload();
    	/**
		 * 关闭按钮
		 */
		$(document).on("click", "#btn-close-window", function(){
			exitForm();//关闭方法 
		});
    });
    
  //拖动处理
    var oldX=0;
    var oldY=0;
    var addPressEvent=0;
    document.getElementById("divMouseMove").onmousedown = function()
     {
       oldX=event.screenX;
       oldY=event.screenY;
       if (addPressEvent==0) 
       { 
            addPressEvent=1;
            if(document.attachEvent)
                {
                   document.detachEvent('onmousemove', moveOnMousePress); 
                   document.detachEvent('onmouseup', moveOnMouseUP);
                   document.attachEvent('onmousemove', moveOnMousePress,false); 
                   document.attachEvent('onmouseup', moveOnMouseUP,false);
                }
                else
                {
                   document.removeEventListener('mousemove', moveOnMousePress); 
                   document.removeEventListener('mouseup', moveOnMouseUP);
                   document.addEventListener('mousemove', moveOnMousePress,false); 
                   document.addEventListener('mouseup', moveOnMouseUP,false);
                }
       }
     }
        var rushCount=0;
        function moveOnMousePress() 
        {
            rushCount++;//降低频率
            if(rushCount>7){
            rushCount=0;
            moveForm(event.screenX-oldX,event.screenY-oldY);
            oldX=event.screenX;
            oldY=event.screenY;
          }
        }
        function moveOnMouseUP() 
        {
               addPressEvent=0;
               if(document.attachEvent)
                {
                   document.detachEvent('onmousemove', moveOnMousePress); 
                   document.detachEvent('onmouseup', moveOnMouseUP);
                }
                else
                {
                   document.removeEventListener('mousemove', moveOnMousePress); 
                   document.removeEventListener('mouseup', moveOnMouseUP);
                }
          //松开最后执行一次
          moveForm(event.screenX-oldX,event.screenY-oldY);
          oldX=event.screenX;
          oldY=event.screenY;
        }
    //--拖动处理完
        //windows方法
        //IE打开url
        function openUrlExplorer(Url) {
            window.external.openUrlExplorer(Url);
        }
        function moveForm(addX,addY) {
            if(addX==0&&addY==0)return;
            window.external.moveForm(addX,addY);
        }
        function exitForm() {
            window.external.exitForm();
        }
    
    /**
     * 加载信息
     * @returns
     */
    function onload(){
    	$.ajax({
    		url : context + '/agtTaskbar/findMessageAlert.action',
    		type : "POST",
    		dataType : "json",
    		data:{
    			"formCode":formCode,
    		},
    		success:function(data){
    			var result=data.resultMap.result;
    			var str='';
    			for(var i=0;i<result.length;i++){
    				str+=' <tr>'+
	    					'<td>'+result[i].messageAlertTime+'</td>'+
	    					' <td>'+result[i].messageAlertContent+'</td>'+
	    				' </tr>';
    			}
    			$('#messageAlert').append(str);
    		}
    	});
    }
})(jQuery);