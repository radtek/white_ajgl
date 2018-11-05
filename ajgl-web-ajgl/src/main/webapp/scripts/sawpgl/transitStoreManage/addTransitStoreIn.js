(function($){
	var makingTable = null ;
	var tempObjectBeanList = null ;
	var inSaveSelfList = null ;
	var storageInCode = "" ;
	$(document).ready(function() {
//		alertmakingCoeSelectPage();
	});
	
	$(document).on("click",".img",function(){
		$.layerAlert.img($(this).attr("src"));
	})
	
	$(document).on("click","#selectCode",function(){
		$("#makingCode").val("");
		alertmakingCoeSelectPage();
	});
	$(document).on("input","#makingCode",function(){
		if($(this).val().length==11){
			setMakingInfo(null,$(this).val());
		};
	});
	
	/**
	 * 取消
	 */
	$(document).on("click","#cancel",function(){
		window.location.href = $.util.fmtUrl(context + "/transitStoreManage/toTransitStoreIn.action");
	});
	
	/**
	 * 办案区使用单选择
	 * @returns
	 */
	function alertmakingCoeSelectPage(){
		window.top.$.layerAlert.dialog({
			content : context +  '/selectMakingCodeAlert/showSelectMakingCodeAlertPage.action',
			pageLoading : true,
			title:"办案区使用单选择",
			width : "850px",
			height : "500px",
			btn:["确定","取消"],
			callBacks:{
				btn1:function(index, layero){					
					var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
					var MakingCode = cm.getMakingCode();
					var MakingId = cm.getMakingId();
					if(!$.util.isBlank(MakingCode)){
						$("#makingCode").val(MakingCode);
						$.laydate.setDate($.date.dateToStr(new Date(), $.laydate.getFmt("#rkDate")), "#rkDate"); 
//						$("#selectCode").hide();
						setMakingInfo(MakingId,MakingCode);//加载内容
					}
					window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
				},
				btn2:function(index, layero){
					window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
				}
			},
			shadeClose : false,
			success:function(layero, index){
				
			},
			initData:{
				
			},
			end:function(){
				
			}
		});
	}
	
	/**
	 * 保存
	 */
	$(document).on("click","#save",function(){
		//验证入库时间
		var rkDate = $.laydate.getTime("#rkDate", "date");
		if($.util.isBlank(rkDate)){
			window.top.$.layerAlert.alert({msg:"请设置入库时间。"});
			return ;
		}
		var transitStorageInBean = getAllField();
		var dataMap = {};
		$.util.objToStrutsFormData(transitStorageInBean, "transitStorageInBean", dataMap);
		save(dataMap);
	});
	
	function save(dataMap){
		$.ajax({
			url:context + '/transitStoreManage/saveTransitStoreManageInfo.action',
			type : 'post',
			dataType : 'json',
			data : dataMap,
			success : function(successData){
				var code = $("#storageInCode").val();
				window.location.href = $.util.fmtUrl(context + "/transitStoreManage/viewTransitStoreIn.action?code="+code);	
				//window.top.$.layerAlert.alert({msg:"保存成功。",icon:1, end:function(){
//					$("#sawqTable").find("input,button,textarea,select").attr("disabled", "disabled");
//					$("#rkDate").addClass("date-disabled");
//					$("#selectCaseCode").attr("disabled", "disabled");
//					$("#save").hide();
//					$("#print").show();
//					$("#cancel").html("返回");
//					$("#formId").val(successData.id);
				//}}) ;
			},
			error:function(errorData){
				$.layerAlert.alert({msg:"保存失败。"}) ;	
			}
		});
	}
	
	function getAllField() {
		var transitStorageInBean = new Object();
		
		transitStorageInBean.harCode = $("#makingCode").val();
		transitStorageInBean.code = $("#storageInCode").val();
		transitStorageInBean.createDate = $.laydate.getTime("#rkDate", "date");
		transitStorageInBean.remark = $("#remark").val();
		transitStorageInBean.transitStoreLocker = $("#transitStoreLocker").val();
		transitStorageInBean.id = $("#formId").val();
		return transitStorageInBean ;
	}
	
	/**
	 * 选择使用单编号后获取暂存物品信息
	 * @returns
	 */
	function setMakingInfo(MakingId,MakingCode){
		if($.util.isBlank(MakingCode)){
			return ;
		}
		$.ajax({
			url:context +'/transitStoreManage/findTransitStoreManageInByMakingId.action?&&code='+MakingCode+'&&id='+MakingId,
			type:'post',
			dataType:'json',
			success:function(successData){
				var bean = successData.tempStorageInInfoBean;
				if(!$.util.isEmpty(successData.msg)){
					$.layerAlert.alert({msg:successData.msg}) ;
				}else{
					if($.util.isBlank(bean)){
						return ;
					}
					tempObjectBeanList = bean.tempObjectBeanList ;
					inSaveSelfList = bean.inSaveSelfList ;
					$("#formId").val(bean.id);
					$("#polices").val(bean.solvePolice);
					storageInCode = bean.storageInCode;
					$("#storageInCode").val(bean.storageInCode);
					$("#rkDate").val(bean.storageInDateTime);
					$("#caseCode").val(bean.caseCode);
					$("#caseName").val(bean.caseName);
					$("#objectOwnerPerson").val(bean.objectOwnerPerson);
					$("#idCard").val(bean.idCard);
					$("#remark").val(bean.remark);
					$("#modifyPerson").val(bean.modifyPerson);
					$("#modifyTime").val(bean.modifyTime);
					$("#makingCode").val(MakingCode);
					dataSet();
					initTransitStoreInDataTable(MakingCode);
				}
			}
		});
	}
	
	function dataSet() {
		$("#qrcode").html("");
		//加载二维码
		$("#qrcode").qrcode({
		    "render": 'image',
		    "size": 130,
		    "color": "#3a3",
		    "background": "white",
		    "text":storageInCode
		});
	}
	
	function initTransitStoreInDataTable(MakingCode){
	    var xh = 1;
	    var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/transitStoreManage/findTransitStoreInFormByCode.action";
		tb.columnDefs = [
			{
				"targets": 0,
     	    	"width": "5%",
     	    	"title": "序号",
     	    	"className":"table-checkbox",
     	    	"data": "id" ,
     	    	"render": function ( data, type, full, meta ) {
     	    		return xh++;
     	    	}
			},
			{
				"targets" : 1,
				"width" : "10%",
				"title" : "物品名称",
				"data" : "objectName",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 2,
				"width" : "8%",
				"title" : "特征",
				"data" : "objectCharacter",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 3,
				"width" : "5%",
				"title" : "本次入库数量",
				"data" : "inThisNum",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 4,
				"width" : "5%",
				"title" : "计量单位",
				"data" : "measureUnit",
				"render" : function(data, type, full, meta) {
					return data;
				}
			},
			{
				"targets" : 5,
				"width" : "10%",
				"title" : "物品图片",
				"data" : "objectPicture",
				"render" : function(data, type, full, meta) {
					return "<img class='img' src='data:image/png;base64," + data + "' height='50'>";
				}
			},
			{
				"targets" : 6,
				"width" : "10%",
				"title" : "备注",
				"data" : "remark",
				"render" : function(data, type, full, meta) {
					return data;
				}
			}
		];
		//是否排序
		tb.ordering = false ;
		tb.paging = false ; 
		tb.info = false ;
		//默认搜索框
		tb.searching = false ;
		//能否改变lengthMenu
		tb.lengthChange = false ;
		//自动TFoot
		tb.autoFooter = false ;
		//自动列宽
		tb.autoWidth = false ;
		//请求参数
		tb.paramsReq = function(d, pagerReq){
			//d["code"] = $("#MakingCode").val();
			d["code"] = MakingCode;
		};
		tb.paramsResp = function(json) {
			var tempObjectBeanList = json.tempObjectBeanList;
			json.data = tempObjectBeanList;
		};
		tb.rowCallback = function(row,data, index) {
				
				
		};
		if(makingTable!=null){
			makingTable.destroy();
		}
		makingTable = $("#makingTable").DataTable(tb);
}

	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		
	});	
})(jQuery);