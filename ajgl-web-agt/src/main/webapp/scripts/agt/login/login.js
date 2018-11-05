
    var hostid=null;
    var win=null;
    var jyName=null;
    var roomNum=null;
    var roomId=null;
    var jyCode=null;//警员编号
    var userName=null;//用户名
    var password=null;//密码
    $(document).ready(function() {
    	onload();
    	$(document).on("click","#loginBtn",function(){
    		loginMeth();//登录方法 
		})
		/**
		 * 关闭按钮
		 */
		$(document).on("click", "#btn-close-window", function(){
//			exitForm();//关闭方法 
			exitProgram();
		});
    });
    
      function loginMeth(){//登录
    	  if(jyName==null||jyName==''){
    		  $.layerAlert.alert({title:"提示",msg:"该用户不存在",icon:5});
    	  }else{
    		  openMenuForm(agtServerIp+"/agt/html-cs/CS-menu.jsp?jyNum="+$.base64.encode(jyName,true)+"&&jyCode="+$.base64.encode(jyCode,true),122, 390);
    		  hideForm();
    		  var a= $('.radioClass .checked').attr("code");
    		  findUserMessage(a);
    	  }
      } 
      
      /**
       * 获取登录人的用户名和密码
       */
      function findUserMessage(code){
    	  $.ajax({
        		url : context + "/agtLogin/findUserMessage.action",
        		type : 'post',
        		dataType : 'json',
        		data:{
        			"code":code
        		},
        		success : function(successData) {
        			var user= successData.resultMap.userName; 
        			var psd= successData.resultMap.password;        			
        			if(user!=null){
        				userName=user;
        			 }else{
        				 userName=code;
        			 }
        			if(psd!=null){
        				password=psd;
        			}else{
        				password=code;
        			}
        			loginDH(); //加载控件
        			setTimeout(function(){
        				onloadSon(hostid,code);
        			},3000)
        		},
        		error : function(errorData) {
        			
        		}
        	});
      }
      
      /**
       * 子页面登录
       * @returns
       */
      function onloadSon(id,code){
      	$.ajax({
      		url : context + "/agtLogin/findOCX.action",
      		type : 'post',
      		dataType : 'json',
      		data:{
      			"ip":id,
      			"code":code
      		},
      		success : function(successData) {
      			
      			var msg=successData.resultMap.alertMsg;
      			if(msg!=null){
      				alert(msg);
      			}else{
      				var trialInfo = successData.resultMap.data; 
      				document.getElementById("dsscocx").UiMainCall(102, trialInfo);
      			}
      		},
      		error : function(errorData) {
      			
      		}
      	});
      }

      function  dsscocx :: NotifyWebTrialStatus(id, stat, strTime){
      	$.ajax({
      		url : context + "/agtLogin/findSyncTrialNoteStat.action",
      		type : 'post',
      		dataType : 'json',
      		data:{
      			"id":id,
      			"stat":stat,
      			"strTime":strTime
      		},
      		success : function(successData) {
      			var bean=successData.resultMap.bean;
      			if(bean.result==false){
      				$.layerAlert.alert({title:"提示",msg:bean.errorMsg});
      			}else{
      				 if(stat==2){
      					updateAskroomAllocationRecord(); //维护分配讯询问室活动记录离开时间
      					exitProgram();
      	          	  }
      			}
      		},
      		error : function(errorData) {
      			
      		}
      	});
      }
    
      function loginDH(){
//    	  setScriptSuppressed(false);
    		var customizeInfo = JSON.stringify({SupportDvr:0, ProductName:'P700', webPort:"80"});
    		try{
    			document.getElementById("dsscocx").SetExtendProperty(customizeInfo);
        		/* cmsIp：大华平台访问ip; * webPort：大华平台访问端口； * loginName：用户登录账号； * loginPass：用户登录账号（明文） */
        		document.getElementById("dsscocx").Login(dhServerIp, dhServerPort, userName, password, "1");
    		}catch(e){
    			alert(e);
    		}
    		
      }
    	function getDsspocxObject(){
    		return document.getElementById("dsscocx");
    	}
    	
    	
    	/**********************小程序加载方法***************************/
    	function getRoomNum() {
    	    roomNum = getConfig("Basic", "RoomNumber");
    	  return roomNum;
    	}
    	//打开menu
        function openMenuForm(Url, formWidth, formHeight)
	    {
	        window.external.openUrlByMenuForm(Url, formWidth, formHeight);
	    }
    	
    	/**
    	 * 开启窗口
    	 */
    	function openWebForm(formId,formName,Url, formWidth, formHeight)
    	{
    	    window.external.openUrlByWinForm(formId,formName,Url, formWidth, formHeight);
    	}
    	
    	//读取配置文件
    	function getConfig(node,key) {
    	    return window.external.getConfig(node,key); 
    	}
    	//读取配置文件
    	function getConfigAll(node) {
    	    return window.external.getConfigAllString(node); 
    	}
    	//设置小图标右键菜单大小
    	function setMenuWindowSize(x, y) {
    		window.external.setWindowSize(x, y);
    	}
      
    //拖动处理
      var oldX=0;
      var oldY=0;
      var addPressEvent=0;
      document.getElementById("divMouseMove").onmousedown = function()
       {
         oldX=event.screenX;
         oldY=event.screenY;
         if (addPressEvent==0) 
         { 
              addPressEvent=1;
              if(document.attachEvent)
                  {
                     document.detachEvent('onmousemove', moveOnMousePress); 
                     document.detachEvent('onmouseup', moveOnMouseUP);
                     document.attachEvent('onmousemove', moveOnMousePress,false); 
                     document.attachEvent('onmouseup', moveOnMouseUP,false);
                  }
                  else
                  {
                     document.removeEventListener('mousemove', moveOnMousePress); 
                     document.removeEventListener('mouseup', moveOnMouseUP);
                     document.addEventListener('mousemove', moveOnMousePress,false); 
                     document.addEventListener('mouseup', moveOnMouseUP,false);
                  }
         }
       }
          var rushCount=0;
          function moveOnMousePress() 
          {
              rushCount++;//降低频率
              if(rushCount>7){
              rushCount=0;
              moveForm(event.screenX-oldX,event.screenY-oldY);
              oldX=event.screenX;
              oldY=event.screenY;
            }
          }
          function moveOnMouseUP() 
          {
                 addPressEvent=0;
                 if(document.attachEvent)
                  {
                     document.detachEvent('onmousemove', moveOnMousePress); 
                     document.detachEvent('onmouseup', moveOnMouseUP);
                  }
                  else
                  {
                     document.removeEventListener('mousemove', moveOnMousePress); 
                     document.removeEventListener('mouseup', moveOnMouseUP);
                  }
            //松开最后执行一次
            moveForm(event.screenX-oldX,event.screenY-oldY);
            oldX=event.screenX;
            oldY=event.screenY;
          }
      //--拖动处理完
       
          //windows方法
          //IE打开url
          function openUrlExplorer(Url) {
              window.external.openUrlExplorer(Url);
          }
          //移动窗口
          function moveForm(addX,addY) {
              if(addX==0&&addY==0)return;
              window.external.moveForm(addX,addY);
          }
          //结束程序
          function exitProgram() {
              window.external.exitProgram();
          }
          //隐藏窗口
          function hideForm() {
              window.external.hideForm();
          }
          
          //关闭窗口
          function exitForm() {
          	window.external.exitForm();
          }
          //设置脚本弹出是否屏蔽
          function setScriptSuppressed(isSuppressed){
        	  window.external.setScriptSuppressed(isSuppressed);
          }
          /**********************小程序加载方法end***************************/
    /**
     * 
     * @returns
     */
    function onload(){
    	setMenuWindowSize(496, 398);
    	roomId=getRoomNum();
    	$.ajax({
    		url : context + '/agtLogin/findData.action',
    		type : "POST",
    		dataType : "json",
    		data:{
    			"roomId":roomId,
    		},
    		success:function(data){
    			//$('#radioDiv').empty();
    			var data=data.resultMap.loginBean;
    			if(data==null){
    				return;
    			}
    			hostid=data.hostID;
    			$('#formCode').text("办案区使用单："+(data.handlingAreaReceiptNum==null?'':data.handlingAreaReceiptNum));
    			$('#roomName').text((data.roomName==null?'':data.roomName));
    			$('#hostPolice').text((data.mainPoliceName==null?'':data.mainPoliceName));
    			var arrName=data.mainPoliceNameLst;//主办民警名称
    			var arrCode=data.mainPoliceNumLst; //主办民警警号
    			var str="";
    			for(var i=0;i<arrName.length;i++){
    				if(i==0){
    					str+=' <div class="col-xs-4">'+
                        '<div class="custom-radio radioClass"><input value="'+(arrCode[i]==null?'':arrCode[i])+'"  type="radio" name="opinions" ><label class="r-name checked" code="'+(arrCode[i]==null?'':arrCode[i])+'" >'+(arrName[i]==null?'':arrName[i])+'</label></div>'+
                     ' </div>';
    					
    				}else{
    					str+=' <div class="col-xs-4">'+
    					'<div class="custom-radio radioClass"><input value="'+(arrCode[i]==null?'':arrCode[i])+'" type="radio" name="opinions" ><label class="r-name "  code="'+(arrCode[i]==null?'':arrCode[i])+'" >'+(arrName[i]==null?'':arrName[i])+'</label></div>'+
    					' </div>';
    				}
    			}
    			$('#radioDiv').append(str);
    			if(arrName.length>0){
    				$('#hostPoliceName').text((arrName[0]==null?'':arrName[0]));
    				$('#hostPoliceCode').text("警号："+(arrCode[0]==null?'':arrCode[0]));
    				jyName=arrName[0];
    				jyCode=arrCode[0];
    			}
    			$('#suspectName').text((data.byQuestioningPeopleName==null?'':data.byQuestioningPeopleName));
    			$('#suspectID').text("身份证："+(data.byQuestioningPeopleIdentifyNum==null?'':data.byQuestioningPeopleIdentifyNum));
    			$('.radioClass').click(function(){
    				var code= $(this).children('input').attr("value");
    				var name=$(this).children('label').text();
    				$('.radioClass label').removeClass("checked");
    				$('.radioClass input').attr("checked",false);
    				$(this).children('label').addClass("checked");
    				$('#hostPoliceName').text((name==null?'':name));
    				$('#hostPoliceCode').text("警号："+(code==null?'':code));
    				jyName=name;
    				jyCode=code;
    			})
    		}
    	});
    }

  //维护分配讯询问室活动记录离开时间
    function updateAskroomAllocationRecord(){
    	$.ajax({
    		url : context + "/agtLogin/updateAskroomAllocationRecord.action?id="+roomId,
    		type : 'post',
    		dataType : 'json',
    		
    		success : function() {

    		}
    	});
    }
