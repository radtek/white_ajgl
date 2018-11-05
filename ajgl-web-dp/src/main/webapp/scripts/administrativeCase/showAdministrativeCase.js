(function($) {
	"use strict";
	
	var colorArr=["pink","purple","yellow","red","green","normal","blue"];//颜色集合
	$(document).ready(function() {
		onload();
	})
	
	function onload(){
		$.ajax({
			url:context +'/dp/findAdministrativeCaseList.action',
			type:'post',
			// data:{dictionaryType : "ajsq"},
			dataType:'json',
			success:function(successData){
				var date =successData.resultMap.date;//日期
				var caseCount =successData.resultMap.caseCount;//行政案件总数
				var xzjlList =successData.resultMap.xzjlList;//行政拘留案件
				var sqjdList =successData.resultMap.sqjdList;//社区戒毒
				var qzjdList =successData.resultMap.qzjdList;//强制戒毒
				$('#showDate').text(date);
				$('#caseCount').text(caseCount);
				
				$('#xzjlCount').text(xzjlList.length);
				$('#sqjdCount').text(sqjdList.length);
				$('#qzjdCount').text(qzjdList.length);
				//将内容添加到页面上
				appendStr(xzjlList ,"xzjl" );
				appendStr(sqjdList ,"sqjd" );
				appendStr(qzjdList ,"qzjd" );
				
				 changeAllPage();
								
			}
		});
	}
	
	/**
	 * 给指定的div添加切换画面的事件
	 * @returns
	 */
	function changeAllPage(){
	      change("xzjl");
	      change("sqjd");
	      change("qzjd");
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
			if(i%4==0&&i!=0){ //每过三条切换
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