/**
 * --------------------------------------------------------------------
 * jQuery customInput plugin
 * Author: Maggie Costello Wachs maggie@filamentgroup.com, Scott Jehl, scott@filamentgroup.com
 * Copyright (c) 2009 Filament Group 
 * licensed under MIT (filamentgroup.com/examples/mit-license.txt)
 * --------------------------------------------------------------------
 */



jQuery.fn.customInput = function(){
	//�Զ��������
	var polices = $(".m-label-right form > div");
	for(var i = 0; i < polices.length; i++){
		$(polices[i]).find("input").addClass("ipt" + i);
		$(polices[i]).find("label").attr("f","ipt"+ i);
	}

	return $(this).each(function(){	
		if($(this).is('[type=checkbox],[type=radio]')){
			var input = $(this);
			
			// ʹ�������ID�õ���صı�ǩ
			var label = $('label[f='+input.attr('class')+']');
			
			// ������һ��div����+��ǩ
			input.add(label).wrapAll('<div class="custom-'+ input.attr('type') +'"></div>');
			
			// ��Ҫ���������֧�֣�hoverα��ı�ǩ
			label.hover(
				function(){ $(this).addClass('hover'); },
				function(){ $(this).removeClass('hover'); }
			);

			$(this).click(function(){
			})
			
			//���Զ����¼������������󶨵�������㣬ģ���¼�				
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

		//��ʾ�����¼�




	});
};

