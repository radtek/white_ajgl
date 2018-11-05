$.roleAlert = $.roleAlert || {};
(function($) {
    "use strict";
	var table;
	var arrId=new Array(); //id
	var arrName=new Array(); //name
	$(document).ready(function() {
		initTable();
		initStatus();
	});
	var number;		//当前弹出层
	jQuery.extend($.common, { 
		setTestObj2:function(index){
			number=index;	//从父页面获取当前弹出层   用于关闭
		},
	});	
	function initStatus(){
		$.ajax({
			url : context + "/alertRule/findDictionaryItemsByType.action",
			type : "POST",
			data :{
				"dictionaryType":dictionaryType,
			},
			dataType : "json",
			success : function(data) {
				$.select2.addByList("#statu", data.dictionaryItemLst, "code", "name");
			},
		});
	}
	/*
	 * 生成表格
	 */
	function resetRole(){
		$.select2.val("#statu","all",true);
		$("#roleName").val("");
		table.draw(true);
	}
	function initTable(){
	var tb = $.uiSettings.getOTableSettings() ;
	tb.ajax.url = context+"/alertRule/queryRole.action";
	tb.columnDefs = [
		{ 
			"targets": 0,
			"title": "选择",
			"className":"table-checkbox",
			"data": "id" ,
			"render": function ( data, type, full, meta ) {		
					  var a = '<input type="checkbox" name="testTr" class="icheckbox" roleId="'+full.id+'" roleName="'+full.roleName+'" />' ;
				      return a;
			}
		},
		{ 
			"targets": 1,
			"title": "角色名称",
			"data": "roleName" ,
			"render": function ( data, type, full, meta ) {								  
				      return data;
			}
		},
		{ 
			"targets": 2,
			"title": "编码",
			"data": "roleCode" ,
			"render": function ( data, type, full, meta ) {		        	
				      return data;
			}
		},
		{ 
			"targets": 3,
			"title": "状态",
			"data": "status" ,
			"render": function ( data, type, full, meta ) {		        	
				      return data;
			}
		}
	                 
	] ;
	 tb.paging = true ; 
	 tb.lengthMenu = [ 10 ];
	 tb.lengthChange = false;
	 tb.searching = false ;
	 tb.ordering = false ;
	 tb.paramsReq = function(d, pagerReq){
		var roleBean = new Object();
		roleBean.roleName=$("#roleName").val();
		if($("#statu").val() == "all"){
			roleBean.status="";
		}else{
			roleBean.status=$("#statu").val();
		}
		//object转化成dataMap传到d里
		$.util.objToStrutsFormData(roleBean, "roleBean", d) ;
	 } ;	
	 tb.paramsResp=function(json){
		 addArr();
	 json.data=json.roleBeanList;
	 json.recordsFiltered=json.totalNum;
	 json.recordsTotal=json.totalNum;
	 };
	 tb.rowCallback = function( row, data, index ) {
		
     };
     table = $("#roleTabulation").DataTable(tb);
	}
	$(document).on('click','#resetRole',function(){
		resetRole();
	});
 	
 	
 	/**
	 * 查询
	 */
	$(document).on('click','#queryRole',function(){
		
				table.draw(true);
	});
	/**
	 * 获取id&&name
	 */
	function addArr(){
		var arrcheck=$('input[type="checkbox"]:checked');
		if(arrcheck.length!=0){
			$('input[type="checkbox"]:checked').each(function(){
				arrId.push($(this).attr("roleId"));
				arrName.push($(this).attr("roleName"));
			})
		}
	
	}
	
	function getData(){
		addArr()
		if(arrId.length==0){
			$.layerAlert.alert({title:"提示",msg:"至少勾选一项"});
		}
		var data={
				roleId:arrId,  //角色id
				roleName:arrName    //角色名称
		}
		return data;
	}
	
	//暴露接口让其他js可以调用
	jQuery.extend($.roleAlert, {
		getData:getData
	});
})(jQuery);