(function($){
	var outBackTable = null;
	
	$(document).ready(function() {
		initPlupload();
		$(document).on("click","#selectCode",function(){
			$("#makingCode").val("");
			alertmakingCoeSelectPage();
		});
		
		$(document).on("click",".img",function(){
			$.layerAlert.img($(this).attr("src"));
		})
		
		$(document).on("input","#makingCode",function(){
			if($(this).val().length==11){
				setMakingInfo(null, $("#makingCode").val());
			};
		});
		
	});
	
	/**
	 * 取消
	 */
	$(document).on("click","#cancel",function(){
		window.location.href = $.util.fmtUrl(context + "/transitStorageOutBack/toTransitStorageOutBackRecord.action");
	});
	
	$(document).on("input","#makingCode",function(){
		if($(this).val().length==11){
			setMakingInfo(null,$(this).val());
		};
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
	
	function initPlupload(){
		var setting = $.plupload.getBasicSettings() ;
		setting.container = "container" ; //容器id
		setting.url = context + "/transitStorageOutBack/uploadFile.action";
		setting.controlBtn = {
			container:{
				className:"upload-btn"
			},
			selectBtn:{
				className:"btn btn-primary",
				html:'<span class="glyphicon glyphicon-edit" aria-hidden="true" style="margin-right:10px;"></span>浏览'
			},
			uploadBtn:{
				init:false
			}
		} ;
		setting.finishlistDom = {
			className:"upload-text",
			downloadBtn:{
				init:false
			},
			deleteBtn:{
				init:false
			}
		};
		setting.filelistDom = {
			className:"upload-text"
		};
		setting.totalInfoDom = {
			className:"upload-text"
		};
		setting.customParams = {
			testCustom1:"123",
			testCustom:function(up, file){
				return Math.random() ;
			}
		} ;
		setting.chunk_size = '0' ;
		setting.filters.max_file_size = '15mb';
		setting.filters.mime_types = [{title: "图片类型", extensions: "jpg,JPG,jpeg,JPEG,gif,GIF,png,PNG,bmp,BMP"}];
		setting.filters.prevent_duplicates = true ;
		setting.multi_selection = false;
		setting.multi_file_num = 1 ;
		setting.multi_file_num_callback = function(max_file_size, totalSize){
		};
		setting.callBacks = {
				uploadAllFinish:function(up, finishedFiles){
					save(finishedFiles);
				}
		}
		$.plupload.init(setting) ;
	}

	function save(finishedFiles){
		var fileBeanLst = [];
		var temporaryStorageOutBean = new Object();
		
		var str = "";
		for (var key in finishedFiles) {
			var obj = {};
			obj.id = key;
			fileBeanLst.push(obj);
			str += finishedFiles[key].name + "，";
        }
		if(str != "" && str.indexOf("，") != -1){
			str = str.substr(0, str.length - 1);
		}
		
		temporaryStorageOutBean.papers = str;
		temporaryStorageOutBean.id = $("#formId").val();
		temporaryStorageOutBean.harCode = $("#makingCode").val();
		temporaryStorageOutBean.code = $("#storageOutBackCode").val();
		temporaryStorageOutBean.outStorageTime = $.laydate.getTime("#outBackDate", "date");
		temporaryStorageOutBean.remark = $("#remark").val();
		temporaryStorageOutBean.receivePerson = $("#receivePerson").val();
		
		var dataMap = {};
		$.util.objToStrutsFormData(temporaryStorageOutBean, "temporaryStorageOutBean", dataMap);
		$.util.objToStrutsFormData(fileBeanLst, "fileBeanLst", dataMap);
		$.ajax({
			url:context + '/transitStorageOutBack/saveTransitStorageOutBack.action',
			type : 'post',
			dataType : 'json',
			data : dataMap,
			success : function(successData){
				var code = $("#storageOutBackCode").val();
				window.location.href = $.util.fmtUrl(context + "/transitStorageOutBack/toShowRecord.action?&&code=" + code);
			},
			error:function(errorData){
				$.layerAlert.alert({msg:"保存失败。"}) ;	
			}
		});
	}
	
	/**
	 * 保存
	 */
	$(document).on("click","#save",function(){
		//验证使用单编号
		var makingCode = $("#makingCode").val();
		if($.util.isBlank(makingCode)){
			window.top.$.layerAlert.alert({msg:"请设置使用单编号。"});
			return ;
		}
		//验证使用单是否符合规则
		if(makingCode.length!=11){
			window.top.$.layerAlert.alert({msg:"使用单号填写错误."});
			return ;
		}
		//验证入库时间
		var rkDate = $.laydate.getTime("#outBackDate", "date");
		if($.util.isBlank(rkDate)){
			window.top.$.layerAlert.alert({msg:"请设置入库时间。"});
			return ;
		}
		//验证物品领取人是否填写
		var receivePerson = $("#receivePerson").val();
		if($.util.isBlank(receivePerson)){
			window.top.$.layerAlert.alert({msg:"填写物品领取人。"});
			return ;
		}
		if($.plupload.getUploader("container").files.length == 0){
			window.top.$.layerAlert.alert({msg:"请上传附件！"});
			return;
		}
		$.plupload.start("container");
	});

	/**
	 * 选择使用单编号后获取暂存物品信息
	 * @returns
	 */
	function setMakingInfo(MakingId,MakingCode){
		$.ajax({
			url:context +'/transitStorageOutBack/findTransitStoreManageOutBackByMakingId.action?&&code='+MakingCode+'&&id='+MakingId,
			type:'post',
			dataType:'json',
			success:function(successData){
				var bean = successData.temporaryStorageOutBean;
				if(!$.util.isEmpty(successData.msg)){
					resOut();
					$.layerAlert.alert({msg:successData.msg}) ;
				}else{
					if($.util.isBlank(bean)){
						return ;
					}
					$.laydate.setDate($.date.dateToStr(new Date(), $.laydate.getFmt("#outBackDate")), "#outBackDate"); 
					$("#polices").text(bean.polices);
					var storageOutBackCode = bean.code;
					$("#storageOutBackCode").val(bean.code);
					$("#caseCode").text(bean.caseCode);
					$("#caseName").text(bean.caseName);
					$("#suspectName").text(bean.suspectName);
					$("#suspectId").text(bean.suspectId);
					$("#remark").val(bean.remark);
					$("#inStorageArea").text(bean.inStorageArea);
					$("#storageRack").text(bean.storageRack);
					$("#makingCode").val(MakingCode);
					dataSet(storageOutBackCode);
					initTransitStoreOutBackDataTable(MakingCode);
				}
			}
		});
	}
	function resOut(){
		$("#storageOutBackCode").val('');
		$("#caseCode").text('');
		$("#caseName").text('');
		$("#suspectName").text('');
		$("#suspectId").text('');
		$("#remark").val('');
		$("#inStorageArea").text('');
		$("#storageRack").text('');
		$("#makingCode").val('');
//		$("#receivePerson").val('');
		$("#qrcode").html("");
		$("#polices").text("");
		initTransitStoreOutBackDataTable(null);
//		$.laydate.reset("#outBackDate");
	}
	
	function dataSet(storageOutBackCode) {
		$("#qrcode").html("");
		//加载二维码
		$("#qrcode").qrcode({
		    "render": 'image',
		    "size": 170,
		    "color": "#3a3",
		    "background": "white",
		    "text":storageOutBackCode
		});
	}
	
	function initTransitStoreOutBackDataTable(MakingCode){
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
				"title" : "本次出库返还库数量",
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
			d["code"] = MakingCode;
		};
		tb.paramsResp = function(json) {
			var tempObjectBeanList = json.tempObjectBeanList;
			json.data = tempObjectBeanList;
		};
		tb.rowCallback = function(row,data, index) {
				
				
		};
		if(outBackTable!=null){
			outBackTable.destroy();
		}
		outBackTable = $("#outBackTable").DataTable(tb);
}

	
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, {
		
	});	
})(jQuery);