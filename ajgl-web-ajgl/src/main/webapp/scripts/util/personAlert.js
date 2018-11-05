$.personAlert = $.personAlert || {};
(function($) {
	"use strict";
	var table = null;// dataTable对象
	var unitBean = null;// 当前选中单位名称
	var mobility = false;// 是否是单位调动
	var arrId=new Array(); //id
	var arrName=new Array(); //name
	$(document).ready(function() {
		intiUnitTree(); // 初始化内部单位树
		initPersonAdminPage();// 初始化页面

		$("#pa_search").on("click", function() {// 查询按钮
			table.draw(true);// 重绘表格
		});
		$("#pa_resetting").on("click", function() {// 重置
			$("#pa_name").val("");// 姓名重置
			$.select2.selectByOrder("#pa_sex", 1, true);
			$.select2.selectByOrder("#pa_status", 1, true);
			$('#pa_unit').iCheck('check');
			table.draw(false);
		});

	});

	/**
	 * 初始化人员管理页面
	 */
	function initPersonAdminPage() {
		// 加载下拉列表
		$
				.ajax({
					url : context + '/alertRule/initPersonManagePage.action',
					async : false,
					type : 'post',
					dataType : 'json',
					data : {},
					success : function(successData) {
						var sexList = new Array();
						sexList.push({
							"code" : "00",
							"name" : "全部"
						});
						$(successData.personAdminPageBean.sexList).each(
								function(i, val) {
									sexList.push(val);
								});
						$.select2.addByList("#pa_sex", sexList, "code", "name");// 添加性别默认值
						var statusList = new Array();
						statusList.push({
							"code" : "00",
							"name" : "全部"
						});
						$(successData.personAdminPageBean.statusList).each(
								function(i, val) {
									statusList.push(val);
								});
						$.select2.addByList("#pa_status", statusList, "code",
								"name");// 添加状态默认值
						$("#pa_unitCode").val(
								successData.personAdminPageBean.unitCode);
						unitBean = {
							"unitId" : successData.personAdminPageBean.unitId,
							"unitCode" : successData.personAdminPageBean.unitCode,
							"unitName" : successData.personAdminPageBean.unitName,// 选择的单位名称
							"unitFullName" : successData.personAdminPageBean.unitName
						}
					},
					error : function(errorData) {

					}
				});

		// 加载表格
		var tb = $.uiSettings.getOTableSettings();
		tb.ajax.url = context + "/alertRule/searchPersonList.action";
		tb.columnDefs = [
				{
					"targets" : 0,
					"title" : '选择',
					"className" : "table-checkbox",
					"data" : "",
					"render" : function(data, type, full, meta) {
						var a = '<input type="checkbox" name="pa_dataTable_trCheckbox" class="icheckbox" userName="'
								+ full.name
								+ '" userId="'
								+ full.personId
								+ '" />';
						return a;
					}
				}, {
					"targets" : 1,
					"title" : "姓名",
					"data" : "name",
					"render" : function(data, type, full, meta) {
						return data;
					}
				}, {
					"targets" : 2,
					"title" : "性别",
					"data" : "sex",
					"render" : function(data, type, full, meta) {
						return data;
					}
				}, {
					"targets" : 3,
					"title" : "警号",
					"data" : "policeNo",
					"render" : function(data, type, full, meta) {
						return data;
					}
				}, {
					"targets" : 4,
					"title" : "所属单位",
					"data" : "unitFullName",
					"render" : function(data, type, full, meta) {
						return data;
					}
				}, {
					"targets" : 5,
					"title" : "状态",
					"data" : "status",
					"render" : function(data, type, full, meta) {
						return data;
					}
				} ];
		tb.ordering = false;
		tb.lengthMenu = [ 10 ], tb.searching = false;
		tb.lengthChange = false;
		tb.autoFooter = false;
		tb.paramsReq = function(d, pagerReq) {// 查询条件回调，初始化和重绘时候都会先执行该回调
			var arr = $.icheck.getChecked("pa_iCheck");// 获取是否选择查询下级单位
			var personBean = {
				"name" : $("#pa_name").val(),
				"sex" : $.select2.val("#pa_sex") == "00" ? "" : $.select2
						.val("#pa_sex"),
				"status" : $.select2.val("#pa_status") == "00" ? "" : $.select2
						.val("#pa_status"),
				"unitCode" : $("#pa_unitCode").val(),
			}
			$.util.objToStrutsFormData(personBean, "personBean", d);
		};
		tb.paramsResp = function(json) {// 处理返回的数据的回调
			addArr();
			json.data = json.personAdminBeanList;
			json.recordsTotal = json.totalNum;
			json.recordsFiltered = json.totalNum;
		};

		tb.rowCallback = function(row, data, index) {// 行绘制完毕之后回调
			$(row).data("rowData", data);
			// 行双击事件
			$(row).on("dblclick", function() {
				var rowData = $(this).data("rowData");
				alertLookPersonPage(rowData);// 调用弹出查看人员页面的方法
			});
		};

		tb.myDrawCallback = function(settings) {// 表格重绘完成之后回调方法
			$("input[name='pa_dataTable_AllCheckbox']").on(
					"ifChanged",
					function(event) {
						if ($("#pa_dataTable tbody tr").length == $.icheck
								.getChecked("pa_dataTable_trCheckbox").length) {

							$.icheck.reverseCheck("pa_dataTable_trCheckbox");
						} else {
							$.icheck.selectCheck("pa_dataTable_trCheckbox");
						}
					});

			// checkBox选中事件
			$("input[name='pa_dataTable_trCheckbox']").on(
					"ifChanged",
					function(event) {
						$("#pa_dataTable tr").css("background-color", "");

						var arr = $.icheck
								.getChecked("pa_dataTable_trCheckbox");
						$(arr).each(
								function(i, val) {
									$(val).parents("tr").css(
											"background-color", "#EEE");
								});
					});

		};
		table = $("#pa_dataTable").DataTable(tb);

	}
	/**
	 * 获取id&&name
	 */
	function addArr(){
		var arrcheck=$('input[type="checkbox"]:checked');
		if(arrcheck.length!=0){
			$('input[type="checkbox"]:checked').each(function(){
				arrId.push($(this).attr("userId"));
				arrName.push($(this).attr("userName"));
			})
		}
	
	}
	
	function getData(){
		addArr()
		if(arrId.length==0){
			$.layerAlert.alert({title:"提示",msg:"至少勾选一项"});
		}
		var data={
				personId:arrId,  //人员id
				personName:arrName    //人员名称
		}
	//	table.draw(true);
		return data;	
	}

	/** 树的设置 */
	var settingIn = {
		view : {
			fontCss : getFontCss
		},
		// 点击+号时查询子节点
		async : {
			enable : true,
			global : false,
			url : context + "/roleUnitTree/initDepartmentTree.action",
			autoParam : [ "id=unitId" ], // 前一个参数 节点id 后一个参数
											// 后台接受的参数（作为查询条件，查询子节点）
			dataFilter : function(treeId, parentNode, childNodes) {
				return childNodes.departmentTree; // inTreeNodesResult为查询出的子节点集合
			}
		},
		// 这块就这样不用修改
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId"
			}
		},
		// 节点单机事件
		callback : {
			onClick : function(event, treeId, treeNode) {
				$("#pa_unitCode").val(treeNode.code);
				unitBean = {
					"unitId" : treeNode.id,
					"unitCode" : treeNode.code,
					"unitName" : treeNode.name,// 选择的单位名称
					"unitFullName" : treeNode.name
				}
				$("#pa_name").val("");// 姓名重置
				$.select2.selectByOrder("#pa_sex", 1, true);
				$.select2.selectByOrder("#pa_status", 1, true);
				$('#pa_unit').iCheck('check');
				table.draw(true);
			}
		}
	};

	function reBuildUnitTree() {
		var treeIn = $.fn.zTree.getZTreeObj("ztree-inUnitTree");
		treeIn.destroy();
		treeIn = $.fn.zTree.init($("#ztree-inUnitTree"), settingIn);
		treeIn.lastSearchValue = "";
		treeIn.nodeSearchList = [];
		treeIn.fontSearchCss = {};
	}
	/** 初始化树 */
	function intiUnitTree() {
		var treeIn = $.fn.zTree.init($("#ztree-inUnitTree"), settingIn);
		treeIn.lastSearchValue = "";
		treeIn.nodeSearchList = [];
		treeIn.fontSearchCss = {};

		$(document).on('focus', '#keyIn', function() {
			var key = $(this);
			focusKey(key);

		});

		$(document).on('blur', '#keyIn', function() {
			var key = $(this);
			blurKey(key);
		});

		$(document).on('keyup change', '#keyIn', function() {
			var key = $(this);
			searchNode(key, "ztree-inUnitTree");
		});
	}
	/** 失去焦点事件 */
	function focusKey(key) {
		if (key.hasClass("empty")) {
			key.removeClass("empty");
		}
	}
	/** 获得焦点事件 */
	function blurKey(key) {
		if (key.get(0).value == "") {
			key.addClass("empty");
		}
	}
	/** 更新节点方法 */
	function updateNodes(highlight, treeId) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var nodeList = zTree.nodeSearchList;
		for (var i = 0, l = nodeList.length; i < l; i++) {
			nodeList[i].highlight = highlight;
			zTree.expandNode(nodeList[i], true, true, true);
			zTree.selectNode(nodeList[i]);
			zTree.updateNode(nodeList[i]);
		}
	}
	/** 查询树节点 */
	function searchNode(key, treeId) {
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
		if (zTree.lastSearchValue === value)
			return;
		zTree.lastSearchValue = value;
		updateNodes(false, treeId);
		zTree.nodeSearchList = zTree.getNodesByParamFuzzy(keyType, value);
		// 将符合条件的 节点 更新
		updateNodes(true, treeId);
	}
	/**节点样式*/
	function getFontCss(treeId, treeNode) {
		return (!!treeNode.highlight) ? {
			color : "#A60000",
			"font-weight" : "bold"
		} : {
			color : "#333",
			"font-weight" : "normal"
		};
	}

	/**
	 * alter自动关闭提示框
	 */
	function alertHint(msg, icon) {
		$.layerAlert.alert({
			msg : msg,
			title : "提示", //弹出框标题
			width : '300px',
			hight : '200px',
			shade : [ 0.5, 'black' ], //遮罩
			icon : icon, //弹出框的图标：0:警告、1：对勾、2：叉、3：问号、4：锁、5：不高兴的脸、6：高兴的脸
			shift : 1, //弹出时的动画效果  有0-6种
		});
	}
	//暴露接口让其他js可以调用
	jQuery.extend($.personAlert, {
		getData:getData
	});
})(jQuery);