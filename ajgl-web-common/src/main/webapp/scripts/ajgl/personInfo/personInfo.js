$.common = $.common || {}; 
(function($) {
	"use strict";
	var loginName = "";
	$(document).ready(function() {
		 $.ajax({
        	 url:context +'/personInfoImage/findCurrentPersonInfo.action',
             async:false,
     		 type:'post',
     		 dataType:'json',
     		 success:function(data){
     			loginName = data.personAdminBean.userName;
     			initLookPersonPage(data.personAdminBean);
     		 }	
         })	
		$(".pa_lookPage_close").on("click", function() {// 关闭
			window.top.$.layerAlert.closeWithLoading(); // 关闭弹窗
		});
	});
	/**
	 * 新建
	 */
	$(document).on('click','#changePassword',function(){
		changePassWord();
	});
	function changePassWord(){
		window.top.$.layerAlert.dialog({
 			content :context+'/personInfoImage/changePassword.action',
 			pageLoading : true,
 			width : "400px",
 			height : "300px",
 			title: "修改密码",
 			shadeClose : false,
 			btn:["保存","取消"],
 			initData:{
 				loginName:loginName
 			},
 			callBacks:{
 				btn1:function(index, layero){
					var cm = window.top.frames["layui-layer-iframe"+index].$.common ;
					cm.save(index);
				},
				btn2:function(index, layero){
					window.top.$.layerAlert.closeWithLoading(index); //关闭弹窗
				}
			},
 			end:function(){
    		}
 		});
	}
	/**
	 * 初始化人员信息查看页面
	 * 
	 * @param personAdminBean
	 *            选择行人员对象数据
	 */
	function initLookPersonPage(personAdminBean) {
		if ($.util.exist(personAdminBean)) {// 不为空
			$("#pa_lookPage_name").html(
					personAdminBean.name == null ? "" : personAdminBean.name);
			$("#pa_lookPage_sex").html(
					personAdminBean.sex == null ? "" : personAdminBean.sex);
			$("#pa_lookPage_job").html(
					personAdminBean.job == null ? "" : personAdminBean.job);
			$("#pa_lookPage_policeNo").html(
					personAdminBean.policeNo == null ? ""
							: personAdminBean.policeNo);
			$("#pa_lookPage_statusCardNo").html(
					personAdminBean.statusCardNo == null ? ""
							: personAdminBean.statusCardNo);
			$("#pa_lookPage_nationality").html(
					personAdminBean.nationality == null ? ""
							: personAdminBean.nationality);
			$("#pa_lookPage_politicsType").html(
					personAdminBean.politicsType == null ? ""
							: personAdminBean.politicsType);
			$("#pa_lookPage_diploma").html(
					personAdminBean.diploma == null ? ""
							: personAdminBean.diploma);
			$("#pa_lookPage_unit").html(
					personAdminBean.unitFullName == null ? ""
							: personAdminBean.unitFullName);
			$("#pa_lookPage_movePhone").html(
					personAdminBean.movePhone == null ? ""
							: personAdminBean.movePhone);
			$("#pa_lookPage_officePhone").html(
					personAdminBean.officePhone == null ? ""
							: personAdminBean.officePhone);
			$("#pa_lookPage_status").html(
					personAdminBean.status == null ? ""
							: personAdminBean.status);
			$("#loginName").html("用户名："+personAdminBean.userName+ '<button class="btn btn-xs btn-info" style=" margin-left: 20px;" id = "changePassword">修改登录密码</button>');
		}
	}
})(jQuery);