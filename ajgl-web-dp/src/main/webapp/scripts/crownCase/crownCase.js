(function($) {
	"use strict";
	
	var colorArr=["pink","purple","yellow","red","green","normal","blue"];//颜色集合
	$(document).ready(function() {
		onload();
	})
	
	function onload(){
		$.ajax({
			url:context +'/dp/findCrownCaseList.action',
			type:'post',
			// data:{dictionaryType : "ajsq"},
			dataType:'json',
			success:function(successData){
				var date =successData.resultMap.date;//刑事案件总数
				var caseCount =successData.resultMap.caseCount;//刑事案件总数
				var xjList =successData.resultMap.xjList;//刑拘案件集合
				var bgjyList =successData.resultMap.bgjyList;//变更羁押
				var tqdbList =successData.resultMap.tqdbList;//提请逮捕
				var pbList =successData.resultMap.pbList;//批捕
				var ysqsList =successData.resultMap.ysqsList;//移送起诉
				var qbhsList =successData.resultMap.qbhsList; //取保候审
				var jsjzList =successData.resultMap.jsjzList;//监视居住
				$('#showDate').text(date);
				$('#caseCount').text(caseCount);
				$('#xjCount').text(xjList.length);
				$('#bgjyCount').text(bgjyList.length);
				$('#tqdbCount').text(tqdbList.length);
				$('#pbCount').text(pbList.length);
				$('#ysqsCount').text(ysqsList.length);
				$('#qbhsCount').text(qbhsList.length);
				$('#jsjzCount').text(jsjzList.length);
				//将内容添加到页面上
				appendStr(xjList ,"xj" );
				appendStr(bgjyList ,"bgjy" );
				appendStr(tqdbList ,"tqdb" );
				appendStr(pbList ,"pb" );
				appendStr(ysqsList ,"ysqs" );
				appendStr(qbhsList ,"qbhs" );
				appendStr(jsjzList ,"jsjz" );
				
				 changeAllPage();
								
			}
		});
	}
	
	/**
	 * 给指定的div添加切换画面的事件
	 * @returns
	 */
	function changeAllPage(){
	      change("xj");
	      change("bgjy");
	      change("tqdb");
	      change("pb");
	      change("ysqs");
	      change("qbhs");
	      change("jsjz");	      
	}
	
	
	/**
	 * 画面切换
	 * @returns
	 */
	function change(data) {
		var $obj = $('.change'+data);
		var len = $obj.length;
		var i = 0;
		$("#next"+data).click(function() {
			i++;
			if (i == len) {
				i = 0;
			}
			$obj.stop(true, true).hide().eq(i).fadeIn(600);
			return false;
		});
		var MyTime = setInterval(function() {
			$("#next"+data).trigger("click");
		}, 4000);
	}
	
	
	
	
	/**
	 * 拼接要显示的内容
	 * @returns
	 */
	function appendStr(list ,id ){
		var str='<div class="change'+id+'" style="display:block">';
		var k=0;
		for(var i=0;i<list.length;i++){
			if(i>0&&list[i].caseInfo.caseCode==list[i-1].caseInfo.caseCode){
				k--;
			}
			if(i%2==0&&i!=0){ //每过两条切换
				str+='</div><div class="change'+id+'" style="display:none">'; 
			}
			str+='<li class="'+colorArr[k]+'">'+
                     ' <p class="person-name">'+(list[i].suspectName==null?'---':list[i].suspectName)+'</p>'+
                     ' <p>'+(list[i].caseInfo.caseCode==null?'---':list[i].caseInfo.caseCode)+'</p>'+
                     ' <p>'+(list[i].caseInfo.caseName==null?'---':list[i].caseInfo.caseName)+'</p>'+
                     ' <p>'+(list[i].caseInfo.handlingUnit==null?'---':list[i].caseInfo.handlingUnit)+'</p>'+
                     ' <p>'+(list[i].caseInfo.handlingPolice==null?'---':list[i].caseInfo.handlingPolice)+'</p>'+
                     ' <p>'+(list[i].caseInfo.doPeople==null?'---':list[i].caseInfo.doPeople)+'</p>'+
                ' </li>';	
			k++;
			if(k>colorArr.length){//颜色用完了就循环显示
				k=0;
			}
		}
		str+='</div>';
		$('#'+id).append(str);
	}
	
})(jQuery);