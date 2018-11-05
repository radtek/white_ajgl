<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<title></title>
<link rel="icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/favicon.ico" />  
<link rel="shortcut icon" type="image/x-icon" href="<%=request.getContextPath()%>/images/favicon.ico" />  
<link rel="bookmark" type="image/x-icon" href="<%=request.getContextPath()%>/images/favicon.ico" /> 	

<link rel="stylesheet" href="<%=request.getContextPath()%>/custom/ajgl/window-widget/anything-slider/css/anythingslider.css">

<link rel="stylesheet" href="<%=request.getContextPath()%>/custom/ajgl/style/utilBasic.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/custom/ajgl/style/frame.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/custom/ajgl/style/branch.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/custom/ajgl/style/map.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/custom/ajgl/js/util.js"></script>
<style>

/********file上传*******/
.upload-control{ padding:20px; border-radius:10px; border:1px solid #aaddee; margin:15px;}
.upload-control p{ color:#999; margin-bottom:15px;}
.upload-control form{ display:inline;}
.upload-control .upload-btn,.upload-control .upload-img,.upload-control .upload-text{ margin-bottom:15px; clear:both;}

.upload-control .btn-find{ width:200px; height:40px;  background:#f0fafe;border-color:#8ad8f7; color:#00aaee; padding:0;position:relative; display:inline-block; *display:inline; zoom:1;}
.upload-control .btn-find span{ font-size:25px; line-height:40px; height:40px; margin:0; color:#00aaee!important; margin:0; float:left; margin-left:40px; margin-top:-2px;}
.upload-control .btn-find p{ color:#4fb3e6; display:inline; margin-left:10px; padding-top:12px;float:left;}
.upload-control .btn-find:hover,.upload-control .btn-find:active{border-color:#00aaee; }
.upload-control .btn-find input{ position:absolute; left:0; top:0; bottom:0; width:200px; height:40px; opacity:0;-moz-opacity:0; filter:alpha(opacity=0); cursor:pointer; z-index:100; background:#d7f1ff; border:none; border:0;}
.upload-control .btn-upload,.upload-control .btn-cancel{ width:200px; height:40px; background:#00aaee; border-color:#00aaee;color:#fff; font-size:14px; }
.upload-control .btn-upload:hover,.upload-control .btn-upload:active{ background:#008fe6; border-color:#008fe6;color:#fff;}
.upload-control .btn-cancel{ margin-left:10px; background:#fff; border-color:#ddd; color:#999; width:80px;}
.upload-control .btn-cancel:hover{border-color:#aaa;}

.upload-control .upload-img img{width:85px; height:80px;}
.upload-control .upload-img .btn{border:0; padding-top:5px; background:#f0f0f0;}
.upload-control .upload-img ul{ margin:0; padding:0; list-style:none;}
.upload-control .upload-img ul li{ float:left; width:100px; height:90px; position:relative;}
.upload-control .upload-img ul li .btn{ position:absolute; right:15px; top:0;}

.upload-control .upload-text .btn{border:0; margin-left:10px; }
.upload-control .upload-text .size{ margin:0 10px; color:#aaa;}
.upload-control .upload-text .schedule{ margin:0 10px}
.upload-control .upload-text .schedule em{ font-weight:bold;}
.upload-control .upload-text .name{color:#333; font-size:12px;}
.upload-control .container-upload-btn-upload{
	margin-left:5px;
}
/********file上传*******/
</style>