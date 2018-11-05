




(function($){
	"use strict";
	var fullSearch = false;
	$(document).ready(function() {	
	
		$.jcade.initWidget() ;
		$.util.initWidget() ;
		initDisableControl() ;
		initBtUrl() ;
		initTooltip() ;
		
		$(".nav-ceng-out").hover(function(){
			$(this).find(".nav-ceng").show();
		},function(){
			$(this).find(".nav-ceng").hide();	 
	    });
		
		//页签初始化
		$("#tabs").tabs();
		//右侧高级查询开始
		$(".advanced-btn").click(function() {
		    $(".advanced-query").slideDown(500);
			$(".basic-query").hide();
			$(".advanced-btn-box").show();
			fullSearch = true;
		});
		$(".advanced-btn-up").click(function(){
			$(".advanced-query").slideUp(500);
			$(".basic-query").show();
			$(".advanced-btn-box").hide();
			fullSearch = false;
		});
		//右侧高级查询结束
	});
	
	function initIcheck(){
	  //icheck初始化
	  $('input.icheckbox').iCheck({
		  checkboxClass: 'icheckbox_square-green',
		  radioClass: 'iradio_square-green',
		  increaseArea: '20%' // optional
	  });    
	  $('input.icheckradio').iCheck({
		  checkboxClass: 'icheckbox_square-green',
		  radioClass: 'iradio_square-green',
		  increaseArea: '20%' // optional
	  });  
	}
	
	function initSelect2(){
		$(".select2").select2({
			width:"100%",
			placeholder:"请选择"
		}) ;
		$(".select2-noSearch").select2({
			minimumResultsForSearch: Infinity,
			width:"100%",
			placeholder:"请选择"
		}) ;
		$(".select2-multiple").select2({
			width:"100%",
			placeholder:"请选择"
		}) ;	
	}
	
	function initInputLength(){
		var setting = $.globalSettings.inputDefaultLength ;
		$("input[type=text].default").each(function(i, val){
			var st = $(val).attr("maxlength") ;
			if(!(st!=null&&st!=undefined&&st.length>0)){
				$(val).attr("maxlength", setting) ;
			}			
		});
	}	
		
	function initDateRange(){
		$("div.dateRange").each(function(i, val){
			var div = $(this) ;
			var fmt = $.laydate.getFmt(div) ;
			
			var start = div.find(".laydate-start") ;
			var end = div.find(".laydate-end") ;
			
			var startId = start.attr("id") ;
			var endId = end.attr("id") ;
			
			$.laydate.setDateObj(startId, {
				format: fmt, //日期格式
		        istime: true, //是否开启时间选择
		        isclear: true, //是否显示清空
		        istoday: true, //是否显示今天
		        issure: true, //是否显示确认
		        festival: true, //是否显示节日
		        fixed: true, //是否固定在可视区域
		        zIndex: 99999999, //css z-index
		        choose: function(dates){ //选择好日期的回调
		        	var endObj = $.laydate.getDateObj(endId) ;
		        	endObj.min = dates ; 
		        	endObj.start = dates ; 
		        }					
			}) ; 
			
			$.laydate.setDateObj(endId, {
				format: fmt, //日期格式
		        istime: true, //是否开启时间选择
		        isclear: true, //是否显示清空
		        istoday: true, //是否显示今天
		        issure: true, //是否显示确认
		        festival: true, //是否显示节日
		        fixed: true, //是否固定在可视区域
		        zIndex: 99999999, //css z-index
		        choose: function(dates){ //选择好日期的回调
		        	var endObj = $.laydate.getDateObj(startId) ;
		        	endObj.max = dates ; 
		        }						
			}) ; 
			

			
			$(document).on('click', "#"+startId, function () {			
				var div = $(this).parents(".dateRange") ;
				var fmt = $.laydate.getFmt(div) ;
				var dateObj = $.laydate.getDateObj(startId) ;
				dateObj.format = fmt ;
				
				laydate(dateObj);
			});
			
			$(document).on('click', "#"+endId, function () {	
				var div = $(this).parents(".dateRange") ;
				var fmt = $.laydate.getFmt(div) ;
				var dateObj = $.laydate.getDateObj(endId) ;
				dateObj.format = fmt ;
				
				laydate(dateObj);
			});
			
		});
		
		
		$(document).on('change', ".laydate-range", function () {		
			var div = $(this).parents(".dateRange") ;
			div.find(".laydate-start").val("") ;
			div.find(".laydate-end").val("") ;
		});
	
	}
	
	function initLaydate(){
		$("div.laydate").each(function(i, val){
			var div = $(this) ;
			var fmt = $.laydate.getFmt(div) ;
			
			var ldv = div.find(".laydate-value") ;
			
			var ldvId = ldv.attr("id") ;
			
			$.laydate.setDateObj(ldvId, {
				format: fmt, //日期格式
		        istime: true, //是否开启时间选择
		        isclear: true, //是否显示清空
		        istoday: true, //是否显示今天
		        issure: true, //是否显示确认
		        festival: true, //是否显示节日
		        fixed: true, //是否固定在可视区域
		        zIndex: 99999999, //css z-index
		        choose: function(dates){ //选择好日期的回调
		        	
		        }				
				
				
			}) ;
			
			$(document).on('click', "#"+ldvId, function () {			
				var div = $(this).parents(".laydate") ;
				var fmt = $.laydate.getFmt(div) ;
				
				var dateObj = $.laydate.getDateObj(ldvId) ;
				dateObj.format = fmt ;
				
				laydate(dateObj);
			});
			
		});
		
		
		$(document).on('change', ".laydate-fmt", function () {		
			var div = $(this).parents(".laydate") ;
			div.find(".laydate-value").val("") ;
		});
	
	}
	
	function initTabs(){
		$(".uiTabs").tabs() ;
	}
	
	function initDisableControl(){
		$(document).on('click', '.disableControl', function () {	
			$(this).attr("disabled",true); 
		});
	}
	
	function initBtUrl(){
		$(document).on('click', '.btUrl', function () {	
			var href = $(this).attr("myHref") ;
			href = $.util.fmtUrl(href) ;
			location.href = href ;
		});
	}
	
	function initTooltip(){
		$.tooltips.initTooltips(document) ;
		$.tooltips.initSelectableToolTips() ;
	}
	/**
	 * 暴露本js方法，让其它js可调用
	 */
	jQuery.extend($.common, { 
		isFullConditionSearch:function(){
			return fullSearch;
		}
	});	
	
})(jQuery);	