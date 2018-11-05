
(function($){
	var index;
	var id;
	var p$;
	$(document).ready(function() {
		var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
		index = frameData.index ;
		id = frameData.initData.id;
		p$ = frameData.initData.p$;
		var harId = frameData.initData.harId;
		initBox(harId);
	});
	function initBox(harId){
		    var obj=new Object();
		    obj.harId = harId;
			var objJson = $.util.toJSONString(obj);
			$.ajax({
				url: context + "/locker/findEmptyAndHarLocker.action",
				type:'post',
				dataType:'json',
				contentType : "application/json; charset=utf-8",
				data:objJson,
				success:function(successData){
					$('#boxDiv').empty();
					list=successData.lockerBeanList;
					var k=0;
					var str='';
					if(list.length==0){
						
					}else{
						str+='<div><div class="col-xs-4">'
							+'<div class="row alert alert-default">'
							+'<h2 class="box-storage-tt" style="display:none;">'+list[0].areaCode+'-'+list[0].lockerCode+'</h2>'
							+'<div class="box-storage-list"><ul>'
							+'<li class="boxEvent" code="'+list[0].code+'" id="'+list[0].id+'"><h2>'+list[0].name+'</h2>';
							
						if(list[0].status==$.common.Constant.CWGZT_SYZ()){
							str+='<a  openId="'+list[0].code+'" class="open" title="开箱"></a>';
							str+='<span class="state-mark state-busy"></span></li>';
						}else if(list[0].status==$.common.Constant.CWGZT_KX()){
							str+='<a  openId="'+list[0].code+'" class="open" title="开箱"></a>';
							str+='<span class="state-mark state-free"></span></li>';						
						}else{
							str+='<span class="state-mark state-disable"></span></li>';//不可用
						}
						for(var i=1;i<list.length;i++){
							if(list[i].areaCode==list[i-1].areaCode){
								if(i%20!=0){
									str+='<li class="boxEvent" code="'+list[i].code+'" id="'+list[i].id+'"><h2>'+list[i].name+'</h2>';
									if(list[i].status==$.common.Constant.CWGZT_SYZ()){
										str+='<a  openId="'+list[i].code+'" class="open" title="开箱"></a>';
										str+='<span class="state-mark state-busy"></span></li>';
									}else if(list[i].status==$.common.Constant.CWGZT_KX()){
										str+='<a  openId="'+list[i].code+'" class="open" title="开箱"></a>';
										str+='<span class="state-mark state-free"></span></li>';							
									}else{
										str+='<span class="state-mark state-disable"></span></li>';//不可用
									}
								}else{
									k++;
									str+='</ul></div></div></div>';
									if(k/3==0){
										str+='</div><div class="row alert-default">';
									}
									str+='<div class="col-xs-4">'
										+'<div class="row alert alert-default">'
										+'<h2 class="box-storage-tt" style="display:none;">'+list[i].areaCode+'-'+list[i].lockerCode+'</h2>'
										+'<div class="box-storage-list"><ul>';
									str+='<li class="" code="'+list[i].code+'" id="'+list[i].id+'"><h2>'+list[i].name+'</h2>';
									if(list[i].status==$.common.Constant.CWGZT_SYZ()){
										str+='<a  openId="'+list[i].code+'" class="open" title="开箱"></a>';
										str+='<span class="state-mark state-busy"></span></li>';
									}else if(list[i].status==$.common.Constant.CWGZT_KX()){
										str+='<a  openId="'+list[i].code+'" class="open" title="开箱"></a>';
										str+='<span class="state-mark state-free"></span></li>';							
									}else{
										str+='<span class="state-mark state-disable"></span></li>';//不可用
									}
								}
							}else{
								str+='</ul></div></div></div>'; //关闭上一个分区
								str+='</div><div class="row alert-default">';  //开启下一个分区
								str+='<div class="col-xs-4">'   //开启分柜
									+'<div class="row alert alert-default">'
									+'<h2 class="box-storage-tt" style="display:none;">'+list[i].areaCode+'</h2>'
									+'<div class="box-storage-list"><ul>';
								str+='<li class="boxEvent" code="'+list[i].code+'" id="'+list[i].id+'"><h2>'+list[i].lockerCode+'</h2>';//开启箱体
								if(list[i].status==$.common.Constant.CWGZT_SYZ()){
									str+='<a  openId="'+list[i].code+'" class="open" title="开箱"></a>';
									str+='<span class="state-mark state-busy"></span></li>';
								}else if(list[i].status==$.common.Constant.CWGZT_KX()){
									str+='<a  openId="'+list[i].code+'" class="open" title="开箱"></a>';
									str+='<span class="state-mark state-free"></span></li>';							
								}else{
									str+='<span class="state-mark state-disable"></span></li>';//不可用
								}
							}
						}
						str+='</ul></div></div></div></div>';
						$('#boxDiv').append(str);
					}
				
				},
				error:function(errorData){
					
				}
			});
	}
	
	$(document).on("click",".boxEvent",function() {
		var boxId = $(this).attr("id");
		var boxName = $($(this).children("h2")[0]).text();
		var boxCode = $(this).attr("code");
		p$.common.setBox(id,boxId,boxName,boxCode,index);
	});
})(jQuery);

