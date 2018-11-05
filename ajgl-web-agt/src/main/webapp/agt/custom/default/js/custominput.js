/**
 * --------------------------------------------------------------------
 * jQuery customInput plugin
 * Author: Maggie Costello Wachs maggie@filamentgroup.com, Scott Jehl, scott@filamentgroup.com
 * Copyright (c) 2009 Filament Group 
 * licensed under MIT (filamentgroup.com/examples/mit-license.txt)
 * --------------------------------------------------------------------
 */



jQuery.fn.customInput = function(){
	//自动添加类名
	var polices = $(".m-label-right form > div");
	for(var i = 0; i < polices.length; i++){
		$(polices[i]).find("input").addClass("ipt" + i);
		$(polices[i]).find("label").attr("f","ipt"+ i);
	}

	return $(this).each(function(){	
		if($(this).is('[type=checkbox],[type=radio]')){
			var input = $(this);
			
			// 使用输入的ID得到相关的标签
			var label = $('label[f='+input.attr('class')+']');
			
			// 包裹在一个div输入+标签
			input.add(label).wrapAll('<div class="custom-'+ input.attr('type') +'"></div>');
			
			// 必要的浏览器不支持：hover伪类的标签
			label.hover(
				function(){ $(this).addClass('hover'); },
				function(){ $(this).removeClass('hover'); }
			);

			$(this).click(function(){
			})
			
			//绑定自定义事件，触发它，绑定点击，焦点，模糊事件				
			input.bind('updateState', function(){	
				input.is(':checked') ? label.addClass('checked') : label.removeClass('checked checkedHover checkedFocus'); 
			})
			.trigger('updateState')
			.click(function(){ 
				$('input[name='+ $(this).attr('name') +']').trigger('updateState'); 
			})
			.focus(function(){ 
				label.addClass('focus');
				if(input.is(':checked')){  $(this).addClass('checkedFocus'); } 
			})
			.blur(function(){ label.removeClass('focus checkedFocus'); });
		}

		//显示更多事件




	});
};

