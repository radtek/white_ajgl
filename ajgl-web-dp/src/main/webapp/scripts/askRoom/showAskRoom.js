(function($) {
	"use strict";
	$(document).ready(function() {
		initData();
		setInterval(function(){
			initData();
	     },300*1000);//五分钟刷新一次页面
		$(document).on("click",".sydname",function(){
			var harId= $(this).attr("harId");
			var harCode= $(this).attr("harCode");
			jumpDetails(harId,harCode);
		})
	})
	
	/**
	 * 添加滚动事件
	 * @returns
	 */
	function scrollEvent(){
		 var TStable = $('#tableScroll table tbody');
		 setInterval(function(){
	         var TStr =  $('#tableScroll tbody tr');
	         TStr.eq(0).remove();
	         TStable.append(TStr.eq(0));
	         $('#tableScroll tbody tr:odd td').css('background','#0c0c11');
	         $('#tableScroll tbody tr:even td').css('background','#273d62');
	     },3000);
	     $('#tableScroll tbody tr:odd td').css('background','#0c0c11');
	}
	
	/**
	 * 跳转到详情页面
	 * 
	 * @returns
	 */
	function jumpDetails(harId,harCode){
		$.util.topWindow().$.layerAlert.dialog({
			content : context +  '/dp/showPersonDetails.action',
			pageLoading : true,
			title:"人员详情",
			width : "100%",
			height : "100%",
			btn:["返回"],
			callBacks:{
				btn1:function(index, layero){
					$.util.topWindow().$.layerAlert.closeWithLoading(index);
				}
			},
			shadeClose : false,
			success:function(layero, index){
				// 弹窗打开成功后执行的操作
			},
			initData:{  // 传递给弹窗界面的参数
				"harCode" : harCode,
				"harId" : harId
			},
			end:function(){ // 关闭弹窗后执行的操作
				
			}
		});
	}
	/**
	 * 加载数据
	 * 
	 * @returns
	 */
	function initData(){
		$.ajax({
			url:context +'/dp/findAskRoomList.action',
			type:'post',
			// data:{dictionaryType : "ajsq"},
			dataType:'json',
			success:function(successData){
				var askRoomList=successData.resultMap.askRoomList;  // 询问室list
				var waitingRoomList=successData.resultMap.waitingRoomList; // 待问室list
				var ysRoomService=successData.resultMap.ysRoomService; // 约束室list
				var today=successData.resultMap.today;        // 今天
				var tomorrow=successData.resultMap.tomorrow;// 明天
				var suspectsList=successData.resultMap.suspectsList;// 嫌疑人列表
				var  startTime=successData.resultMap.startTime; // 一周开始时间
				var  endTime=successData.resultMap.endTime;  // 一周结束时间
				var  keyList=successData.resultMap.keyList;  // 一周里的数据
																// 图表使用key 的集合
				var  valueList=successData.resultMap.valueList;  // 一周里的数据
																	// 图表使用
																	// value 的集合
				var  nowDateLong=successData.resultMap.nowDateLong;  // 一周里的数据
																		// 图表使用
																		// value
																		// 的集合
				appendAskRoom(askRoomList); // 询问室展示
				appendWaitingRoom(waitingRoomList,ysRoomService); // 待问室$约束室
				appendCaseList(suspectsList,nowDateLong);// 嫌疑人列表
				appendChart(keyList,valueList);// 加载图表
				$('#tomorrow').text($.date.timeToStr(parseInt(tomorrow),"yyyy-MM-dd HH:mm"));
				$('#today').text($.date.timeToStr(parseInt(today),"yyyy-MM-dd HH:mm"));
				$('#week').text($.date.timeToStr(parseInt(startTime),"yyyy-MM-dd HH:mm")+'-'+$.date.timeToStr(parseInt(endTime),"yyyy-MM-dd HH:mm"));
			}
		});
	}
	
	/**
	 * 加载图表
	 * 
	 * @returns
	 */
	function appendChart(keyList,valueList){
		
		 Highcharts.theme = {
				  colors: ["#ff0022", "#ff9900", "#ffde00", "#7cb5ec", "#fff", "#b7b6e9", "#eeaaee",
				    "#55BF3B", "#DF5353", "#7798BF", "#aaeeee"],
				    
				  chart: {
				    backgroundColor:null,
				    style: {
				      fontFamily: "Microsoft Yahei, sans-serif",
				    },
				    plotBorderColor: '#606063'
				  },
				  
				title: {
				    style: {
				      color: '#E0E0E3',
				      textTransform: 'uppercase',
				      fontSize: '20px'
				    }
				  },
				  subtitle: {
				    style: {
				      color: '#E0E0E3',
				      textTransform: 'uppercase',
				      fontSize: '16px'
				    }
				  },
				  xAxis: {
				    gridLineColor: '#707073',
				    labels: {
				      style: {
				        color: '#E0E0E3'
				      }
				    },
				    lineColor: '#707073',
				    minorGridLineColor: '#505053',
				    tickColor: '#707073',
				    title: {
				      style: {
				        color: '#A0A0A3'
				      }
				    }
				  },
				  yAxis: {
				    gridLineColor: '#707073',
				    labels: {
				      style: {
				        color: '#E0E0E3'
				      }
				    },
				    lineColor: '#707073',
				    minorGridLineColor: '#505053',
				    tickColor: '#707073',
				    tickWidth: 1,
				    title: {
				      style: {
				        color: '#fff'
				      }
				    }
				  },
				  tooltip: {
				    backgroundColor: 'rgba(0, 0, 0, 0.85)',
				    style: {
				      color: '#F0F0F0',
				      fontSize: '16px'
				      
				    }
				  },
				  plotOptions: {
				    series: {
				      dataLabels: {
				        color: '#B0B0B3'
				      },
				      marker: {
				        lineColor: '#333'
				      }
				    },
				    boxplot: {
				      fillColor: '#505053'
				    },
				    candlestick: {
				      lineColor: 'white'
				    },
				    errorbar: {
				      color: 'white'
				    }
				  },
				  legend: {
				    backgroundColor: '#132643',
				    itemStyle: {
				      color: '#aaa',
				    },
				    itemHoverStyle: {
				      color: '#fff'
				    },
				    itemHiddenStyle: {
				      color: '#ccc'
				    }
				  },
				  credits: {
				    style: {
				      color: '#fff'
				    }
				  },
				  labels: {
				    style: {
				      color: '#fff'
				    }
				  },
				  drilldown: {
				    activeAxisLabelStyle: {
				      color: '#F0F0F3'
				    },
				    activeDataLabelStyle: {
				      color: '#F0F0F3'
				    }
				  },
				  navigation: {
				    buttonOptions: {
				      symbolStroke: '#DDDDDD',
				      theme: {
				        fill: '#505053'
				      }
				    }
				  },
				  // scroll charts
				  rangeSelector: {
				    buttonTheme: {
				      fill: '#505053',
				      stroke: '#000000',
				      style: {
				        color: '#CCC'
				      },
				      states: {
				        hover: {
				          fill: '#707073',
				          stroke: '#000000',
				          style: {
				            color: 'white'
				          }
				        },
				        select: {
				          fill: '#000003',
				          stroke: '#000000',
				          style: {
				            color: 'white'
				          }
				        }
				      }
				    },
				    inputBoxBorderColor: '#505053',
				    inputStyle: {
				      backgroundColor: '#333',
				      color: 'silver'
				    },
				    labelStyle: {
				      color: 'silver'
				    }
				  },
				  navigator: {
				    handles: {
				      backgroundColor: '#666',
				      borderColor: '#AAA'
				    },
				    outlineColor: '#CCC',
				    maskFill: 'rgba(255,255,255,0.1)',
				    series: {
				      color: '#7798BF',
				      lineColor: '#A6C7ED'
				    },
				    xAxis: {
				      gridLineColor: '#505053'
				    }
				  },
				  scrollbar: {
				    barBackgroundColor: '#808083',
				    barBorderColor: '#808083',
				    buttonArrowColor: '#CCC',
				    buttonBackgroundColor: '#606063',
				    buttonBorderColor: '#606063',
				    rifleColor: '#FFF',
				    trackBackgroundColor: '#404043',
				    trackBorderColor: '#404043'
				  },
				  // special colors for some of the
				  legendBackgroundColor: 'rgba(0, 0, 0, 0.5)',
				  background2: '#505053',
				  dataLabelsColor: '#B0B0B3',
				  textColor: '#C0C0C0',
				  contrastTextColor: '#F0F0F3',
				  maskColor: 'rgba(255,255,255,0.3)'    
				};
				// Apply the theme
				Highcharts.setOptions(Highcharts.theme);
		
		
// var arrCount=[];
// for(var i=0;i<valueList.length;i++){
// arrCount.push(parseInt(valueList[i]));
// }
		Highcharts.theme = {
				colors : [ "#55d5ff", "#ff0022", "#00cb5b", "#fff", "#55BF3B",
						"#DF5353", "#7798BF", "#aaeeee" ],

			}
		// Apply the theme
		Highcharts.setOptions(Highcharts.theme);
		                                                             
		    $('#highchart-bar').highcharts({                                          
		        chart: {                                                          
		        },                                                                
		        title: null,                                                                
		        xAxis: {                                                          
		            categories: keyList
		        }, 
		        yAxis: {
		        	allowDecimals:false,
		            title: {
		                text: '嫌疑人数量'}   
		        },
		        legend: {
		            enabled: false
		        },                                                               
		        tooltip: {                                                        
		            formatter: function() {                                       
		                var s;                                                    
		                if (this.point.name) { // the pie chart
		                    s = ''+                                               
		                        this.point.name +': '+ this.y +' fruits';         
		                } else {                                                  
		                    s = ''+                                               
		                        this.x  +': '+ this.y;                            
		                }                                                         
		                return s;                                                 
		            }                                                             
		        },                                                               
		        series: [{
		            borderWidth:0,// 去掉默认图形的白色边框
		            type: 'column',                                               
		            name: '嫌疑人',                                                 
		            data: valueList,
		              dataLabels: {
		                enabled: true,
		                rotation: -0,
		                color: '#FFFFFF',
		                align: 'right',
		                format: '{point.y}', // one decimal
		                y: 5, // 10 pixels down from the top
		                style: {
		                    fontSize: '12px',
		                    fontFamily: 'Verdana, sans-serif',
		                    fontWeight:'normal'
		                }
		            }                                         
		        },
		 ]                                                                
		    });                           
	}
	/**
	 * 案件列表
	 * 
	 * @returns
	 */
	function  appendCaseList(list,longDate){		
		$('#suspectsTable').empty();
		var str='';
		var dwCount=0;
		var jxzCount=0;
		var ywcCount=0;
		var menCount=0;
		var womenCount=0;
		for(var i=0;i<list.length;i++){
			str+='<tr>';
			if((longDate-list[i].enterIntoTime)>=82800000&&(longDate-list[i].enterIntoTime)<=84600000){
				str+='<td width="3%"><span class="icon-overtime time-23"></span></td>';
			}else if((longDate-list[i].enterIntoTime)>=84600000){
				str+='<td width="3%"><span class="icon-overtime time-24"></span></td>';
			}else{
				str+='<td width="3%"></td>';
			}
			str+='<td width="13%"><a class="sydname" harCode="'+list[i].harCode+'" harId="'+list[i].id+'" >'+list[i].byQuestioningPeopleName+'</a> ';
			if(list[i].status=='待问'){
				dwCount++;
				str+='<span class="event-state e-red">'+list[i].status+'</span></td>';
			}
			if(list[i].status=='进行中'){
				jxzCount++;
				str+='<span class="event-state e-green">'+list[i].status+'</span></td>';
			}
			if(list[i].status=='已完成'){
				str+='<span class="event-state e-blue">'+list[i].status+'</span></td>';
				ywcCount++;
				if(list[i].sex==$.common.Constant.XB_NU()){
					womenCount++;
				}
				if(list[i].sex==$.common.Constant.XB_NAN()){
					menCount++;
				}
			}
			str+='<td width="20%">'+list[i].lawCase+'</td>';
			if(list[i].sex==$.common.Constant.XB_NAN()){
				str+='<td width="4%">男</td>';
			}else if(list[i].sex==$.common.Constant.XB_NU()){
				str+='<td width="4%">女</td>';
			}else{
				str+='<td width="4%">未知</td>';
			}
			str+='<td width="13%">'+$.date.timeToStr(parseInt(list[i].enterIntoTime), "yyyy-MM-dd HH:mm")+'</td>'+
				'<td width="13%">'+$.date.timeToStr(parseInt(list[i].enterIntoTime)+86400000, "yyyy-MM-dd HH:mm")+'</td>'+
				'<td width="15%">'+list[i].sponsorUnitCode+'</td>'+
				'<td width="10%">'+list[i].mainPolices+'</td>'+
				'<td width="9%">'+list[i].causeOfLawCase+'</td>'+
			'</tr>';
			
		}
		$('#suspectsTable').append(str);
		$('#waitingCount').text(dwCount);// 代问人数
		$('#runningCount').text(jxzCount);// 进行中人数
		$('#finishCount').text(ywcCount);// 已完成
		$('#manCount').text(menCount); // 男 人数
		$('#womenCount').text(womenCount); // 女 人数
		$('#sumCount').text(list.length); // 共 人数
		$('#countBtn').text(list.length); // 共 人数
		scrollEvent();
	}
	
	/**
	 * 待问室添加
	 * 
	 * @returns
	 */
	function appendWaitingRoom(dwList,ysList){
		var str='';
		if(dwList.length!=0){
			str+='<div class="room-group normal" style="display: block;">'+
			     '<h2 class="title1 text-center" style="margin-bottom: 20px">待问室</h2>'+
			     '<ul class="c-room-list c-room-list-house">';
			for(var i=0;i<dwList.length;i++){
				if(i%3==0&&i!=0){  // 三个为一组
					str+='</ul></div>';
					str+='<div class="room-group normal" style="display: none;">'+
					'<h2 class="title1 text-center" style="margin-bottom: 20px">待问室</h2>'+
					'<ul class="c-room-list c-room-list-house">';					      
				}
				str+='<li style = "height:50px">'+dwList[i].name+'<p class="c-red">共'+dwList[i].number+'人</p></li>';
			}
			str+='</ul></div>';
			if(ysList.length!=0){
				str+='<div class="room-group  normal" style="display: none;">'+
			     '<h2 class="title1 text-center" style="margin-bottom: 20px">约束室</h2>'+
			     '<ul class="c-room-list c-room-list-house">';
				for(var i=0;i<ysList.length;i++){
					if(i%3==0&&i!=0){  // 三个为一组
						str+='</ul></div>';
						str+='<div class="room-group normal" style="display: none;">'+
						'<h2 class="title1 text-center" style="margin-bottom: 20px">约束室</h2>'+
						'<ul class="c-room-list c-room-list-house">';					      
					}
					str+='<li style = "height:50px">'+ysList[i].name+'<p class="c-red">共'+ysList[i].number+'人</p></li>';
				}
				str+='</ul></div>';
			}
		}else if(ysList.length!=0){
			str+='<div class="room-group normal" style="display: block;">'+
		     '<h2 class="title1 text-center" style="margin-bottom: 20px">约束室</h2>'+
		     '<ul class="c-room-list c-room-list-house">';
			for(var i=0;i<ysList.length;i++){
				if(i%3==0&&i!=0){  // 三个为一组
					str+='</ul></div>';
					str+='<div class="room-group normal" style="display: none;">'+
					'<h2 class="title1 text-center" style="margin-bottom: 20px">约束室</h2>'+
					'<ul class="c-room-list c-room-list-house">';					      
				}
				str+='<li style = "height:50px">'+ysList[i].name+'<p class="c-red">共'+ysList[i].number+'人</p></li>';
			}
			str+='</ul></div>';
		}
		$('#room-group-change').append(str);
		changePage();// 添加切换事件
		
	}
	
	function changePage() {
		var $obj = $('#room-group-change .room-group');
		var len = $obj.length;
		var i = 0;
		$("#next").click(function() {
			i++;
			if (i == len) {
				i = 0;
			}
			$obj.stop(true, true).hide().eq(i).fadeIn(600);
			return false;
		});
// $("#previous").click(function() {
// i--;
// if (i == -1) {
// i = len - 1;
// }
// $obj.stop(true, true).hide().eq(i).fadeIn(600);
// return false;
// }); //每2秒，自动切换。触发".next"的click事件
		var MyTime = setInterval(function() {
			$("#next").trigger("click");
		}, 4000);
	}
	
	/**
	 * 添加询问室div
	 * 
	 * @param list
	 * @returns
	 */
	function appendAskRoom(list){
		var str='';
		$('#xwsList').empty();
		for(var i=0;i<list.length;i++){
			if(list[i].status==$.common.Constant.SYZT_SYZ()){// 使用中的状态
				str+='<li class="c-busy">'+list[i].name+'</li>';
			}else if(list[i].status==$.common.Constant.SYZT_KX()){
				str+='<li class="c-free">'+list[i].name+'</li>';
			}else{
				str+='<li style="background-color:#999">'+list[i].name+'</li>';
			}
		}
		$('#xwsList').append(str);
	}
	
})(jQuery);