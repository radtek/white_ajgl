<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%--
	版权: Copyright 2017 dahua Tech. Co. Ltd. All Rights Reserved. 
	描述: TODO
	作者: 28245
	创建时间：2017-3-1
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">
<html>
<head>
<script type="text/javascript" src="/itc/include/script/JQuery_zTree_v3.0/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="/itc/include/script/json2.js"></script>
</head>
<body onload="login()">
<object id="dsspocxObject" classid="CLSID:1D1A0ACA-3A53-4589-88B8-94A0DDC47EA6" onreadystatechange="" style="width:100%;height:690px"></object>

<script type="text/javascript">
    $(function(){
        /* setTimeout(function(){
                    login();
                }, 500); */
                
        $("#read").click(function(){
            var id = document.getElementById("dsspocxObject").GetDevAndBrandIDForThird();
            $("#readId").html(id);
        });
    });
               
    function login(){
        var customizeInfo = JSON.stringify({SupportDvr:0, ProductName:'P700', webPort:80});
        try{
                document.getElementById("dsspocxObject").SetExtendProperty(customizeInfo);
           
                document.getElementById("dsspocxObject").Login("172.6.5.135", 9000, "system", "123456", "1");
            } catch(e){
            }
    }
    
    z
</script>
<input type="button" value="读取手环" id="read"/>
<span id="readId"></span>
</body>
</html>