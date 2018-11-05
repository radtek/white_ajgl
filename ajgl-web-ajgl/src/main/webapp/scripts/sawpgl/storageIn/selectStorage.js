(function($){
	"use strict";
	
	var frameData = window.top.$.layerAlert.getFrameInitData(window) ;
	var pageIndex = frameData.index ;//当前弹窗index
	var initData = frameData.initData ;
	
	var areaId = initData.areaId;   //选择的物证区ID
	var storageServiceBeans = initData.storageServiceBeans;// 存储位置数量
	
	$(document).ready(function() {	
		
		initPageField();
		
	});
	
	/**
	 * 初始化页面信息
	 * 
	 */
	
	function initPageField() {
		$.ajax({
			url:context +'/storageIn/initDataForStorageSelectPage.action',
			type:'post',
			dataType:'json',
			data:{"storageAreaId":areaId},
			success:function(successData){
				var list = successData.storageList;
				if (null != list && list.length > 0) {
					var trs = "";
					$.each(list, function(i, obj){
						if (storageInfo.indexOf(obj.id) >= 0) {
							if (readonlyType == "read") {
								trs += '<div class="row row-mar"><div class="col-xs-1"> <input type="checkbox" name="check" checked="checked" disabled="disabled" class="icheckbox" value="'+obj.id+'"></div><div class="col-xs-7 location">'+obj.location+'</div><div class="col-xs-4"> 数量：<input type="text" name="sl" class="form-control input-sm valiform-keyup form-val" datatype="n1-6" errormsg="请输入1-6位整数" style="width:60px;" value="'+storageMap[obj.id]+'"></div>	</div>';   
							} else {
								trs += '<div class="row row-mar"><div class="col-xs-1"> <input type="checkbox" name="check" checked="checked" class="icheckbox" value="'+obj.id+'"></div><div class="col-xs-7 location">'+obj.location+'</div><div class="col-xs-4"> 数量：<input type="text" name="sl" class="form-control input-sm valiform-keyup form-val" datatype="n1-6" errormsg="请输入1-6位整数" style="width:60px;" value="'+storageMap[obj.id]+'"></div>	</div>';   
							}
						} else {
							trs += '<div class="row row-mar"><div class="col-xs-1"> <input type="checkbox" name="check" class="icheckbox" value="'+obj.id+'"></div><div class="col-xs-7 location">'+obj.location+'</div><div class="col-xs-4"> 数量：<input type="text" name="sl" class="form-control input-sm valiform-keyup form-val" datatype="n1-6" errormsg="请输入1-6位整数" style="width:60px;"></div>	</div>';   
						}
					});
					$("#lockers").html(trs);
				} else {
					$("#lockers").html("无数据");
				}			
			}
		});
	}
	
	/**
	 * 获取页面信息
	 * @param object
	 */
	function getInfo(){
		var sum = 0.0;
		var storageId = [];
		var storageName = [];
		var storageNum = [];
		var info = new Object();
		var mes = "";
		var arr = $.icheck.getChecked("check");
		var arrAll = $(":input[name='check']");
		if (arr.length < 1) {
			mes = "请选择储物箱。";
			info["mes"] = mes;
			return info;
		} else {
			$.each(arr, function(i, val){
				if(!$.util.isEmpty($(val).val())){
					storageId.push($(val).val());
					storageName.push($(val).parents(".row-mar").find(".location").html());
					var num = $(val).parents(".row-mar").find(":input[name=sl]").val();
					if ($.util.isBlank(num) || num <= 0) {
						if (arrAll.length > 1) {
							mes = "请输入数量。";
							info["mes"] = mes;
							return info;	
						} else {
							storageNum.push(inStorageSum);  //只有一个储物箱时，数量为空则自动填充为本次入库数量。
						}
					} else {
						if (!$.util.isBlank(storageMap[$(val).val()])) {
							if (num < storageMap[$(val).val()]) {
								mes = "入库数量不能少于原来数量。";
								info["mes"] = mes;
								return info;
							}
						}
						sum += parseFloat(num);
						storageNum.push(num); 
					}
				}    		
			});
			if (sum > inStorageSum) {
				mes = "数量合计不能大于本次入库数量。";
			}
			info["mes"] = mes;
			info["storageId"] = storageId;
			info["storageName"] = storageName;
			info["storageNum"] = storageNum;
			return info;
		}
	}
	
	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		getInfo : getInfo
	});	
})(jQuery);