(function($){
	"use strict";
	$(document).ready(function() {	
		/**
		 * 单位树搜索按钮点击事件
		 */
		$(document).on("click",".selectTreeUnit",function(){
			showMenu();
		});
	});
	
	/**
	 * 选择单位树setting
	 */
	var settingSelect = {
		view: {
			fontCss: getFontCss
		},
		async: {
			enable: true,
			global: false,
			url:context+"/unitTree/initUnitTree.action",
			autoParam:["id=code"],
			dataFilter: function(treeId, parentNode, childNodes) {
				childNodes.unitTree[0].open = true;
				return childNodes.unitTree;
			}
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pId"
			}
		},
		callback:{
			onClick : function(event, treeId, treeNode) {
				$("#unitName").val(treeNode.name);
				$("#unit").val(treeNode.id);
				//$.lockerList.onload();
				hideMenu();
			},
			onAsyncSuccess:selFirst
		}
	};
	
	function selFirst(){
		var zTree = $.fn.zTree.getZTreeObj('ztree-unitSelect');//获取ztree对象  
        var nodes= zTree.getNodes();
        var firstNode = nodes[0];
        zTree.selectNode(firstNode);//选择点  
        zTree.setting.callback.onClick(null, zTree.setting.treeId, firstNode);//调用事件  
        $.lockerList.onload(); 
    }
	
	/**
	 * 初始化单位树
	 */
	function intiSelectUnitTree() {
		var treeSelect = $.fn.zTree.init($("#ztree-unitSelect"), settingSelect);
		treeSelect.lastSearchValue = "" ;
		treeSelect.nodeSearchList = [] ;
		treeSelect.fontSearchCss = {} ;
		
		$(document).on('focus', '#keySelect', function () {
			var key = $(this) ;
			 focusKey(key) ;
			
		});
		
		$(document).on('blur', '#keySelect', function () {
			var key = $(this) ;
			blurKey(key) ;
		});		
		
		$(document).on('keyup change', '#keySelect', function () {
			var key = $(this) ;
			searchNode(key, "ztree-unitSelect") ;
		});
		//selFirst();
	}
	
	/**
	 * 失去焦点事件
	 */
	function focusKey(key) {
		if (key.hasClass("empty")) {
			key.removeClass("empty");
		}
	}
	
	/**
	 * 获得焦点事件
	 */
	function blurKey(key) {
		if (key.get(0).value == "") {
			key.addClass("empty");
		}
	}
	
	/**
	 * 搜索节点
	 */
	function searchNode(key, treeId){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var value = $.trim(key.get(0).value);
		if (value === "") {
			var nodes = zTree.getNodesByParam("isHidden", true);
			zTree.showNodes(nodes);
			zTree.expandAll(false);
			return;
		}
		var keyType = "name";
		if (key.hasClass("empty")) {
			value = "";
		}
		if (zTree.lastSearchValue === value) return;
		zTree.lastSearchValue = value ;
		updateNodes(false, treeId); 
		
		
		zTree.nodeSearchList = zTree.getNodesByParamFuzzy(keyType, value);
		//将符合条件的  节点  更新
		updateNodes(true, treeId);
	}
	
	/**
	 * 更新节点方法
	 */
	function updateNodes(highlight, treeId) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var nodeList = zTree.nodeSearchList ;
		for( var i=0, l=nodeList.length; i<l; i++) {
			nodeList[i].highlight = highlight;
			zTree.expandNode(nodeList[i], true, true, true);
			zTree.selectNode(nodeList[i]);
			zTree.updateNode(nodeList[i]);
		}
	}
	
	/**
	 * 节点样式
	 */
	function getFontCss(treeId, treeNode){
		return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
	}
	
	/**
	 * 显示单位树
	 */
	function showMenu() {
		var obj = $("#unitName");
		var oOffset = $("#unitName").offset();
		$("#menuContent").css({left:oOffset.left + "px", top:oOffset.top + obj.outerHeight() + "px"}).slideDown("fast");
		$("body").on("mousedown", onBodyDown);
	}
	/**
	 * 隐藏单位树
	 */
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").off("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	
	/**
	 * 显示操作记录
	 * @param opertionRecord 操作记录table元素id
	 */
	function showOperateRecord(opertionRecord) {
		$.ajax({
			url:context +'/operateRecord/searchOperateRecordByTargetId.action',
			cache:false,
			global:false,
			type:'post',
			dataType:'json',
			data:{id : $("#harId").val()},
			success:function(successData){
				$("#" + opertionRecord + " tbody").empty();
				var orbLst = successData.orbLst;
				if(orbLst != null){
					var num = orbLst.length;
					for(var i in orbLst){
						var str = "<tr><td>" + num + "</td>"
							+ "<td>" + orbLst[i].operateContent + "</td>"
							+ "<td>" + orbLst[i].operator + "</td>"
							+ "<td>" + orbLst[i].operateTimeStr + "</td>";
						if(orbLst[i].noteType == "1"){
							if(!$.util.isBlank(orbLst[i].noteText)){
								str += "<td style='min-width: 150px'>" + orbLst[i].noteText + "<br/>";
							}else{
								str += "<td style='min-width: 150px'>";
							}
							for(var j in orbLst[i].attLst){
								str += "<a href='###' class='dlorf' id=" + orbLst[i].attLst[j].id + ">" + orbLst[i].attLst[j].name + "</a>";
								if(j < orbLst[i].attLst.length - 1){
									str += "<br/>";
								}
							}
							str += "</td></tr>";
						}else{
							str += "<td>" + orbLst[i].noteText + "</td></tr>";
						}
						$("#" + opertionRecord + " tbody").append(str);
						num--;
					}
				}
				$(document).on("click",".dlorf",function(){
					window.open(context + "/handlingAreaReceipt/downloadFile.action?attachmentId="+ $(this).attr("id"));					
				})
			}
		});
	}
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		intiSelectUnitTree : intiSelectUnitTree,
		showOperateRecord : showOperateRecord,
	});	
})(jQuery);