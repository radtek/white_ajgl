<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<!-- 公用常量页面 -->
<%@include file="/WEB-INF/view/util/constant.jsp"%>
</head>
<body>


				<!--悬浮操作层-->
				<div class="fixed-a">
					<div class="m-ui-title1">
						<h1>预警规则</h1>
						<div class="m-ui-caozuobox">
						<!-- <button class="btn btn-success" id="addRules">新增</button> -->
							<button class="btn btn-primary" id="configuration">配置</button>
							<button class="btn btn-primary" id="refresh">
								<span class="glyphicon glyphicon-refresh"></span>刷新
							</button>
							<button class="btn btn-success" id="enabled">启用</button>
							<button class="btn btn-danger" id="disable">停用</button>
						</div>
					</div>
				</div>
				<!--悬浮操作层-->
				<div id="c-center-right-content-block">
					<div id="c-center-right-content-bar">
						<div class="list-group" id="select">
							
						</div>
					</div>
					<div id="c-center-right-content-con">
						<div class="right-inner" style="padding-top: 30px; display: none;" id="body">
						
							<!--------------表单----------->
							<div style="margin: 0 auto;">
							<div class="row row-mar">
									<div class="col-xs-3">
										<label class="control-label">预警名称：</label>
									</div>
									<div class="col-xs-3">
										<input id="name" value="" class="form-control input-sm" size="20">
									</div>
									<div class="col-xs-2">
										<label class="control-label">预警编码：</label>
									</div>
									<div class="col-xs-3">
										<input id="code" value="" class="form-control input-sm" size="20">
									</div>
							</div>
								<div class="row row-mar">
									<div class="col-xs-3">
										<label class="control-label">预警方式：</label>
									</div>
									<div class="col-xs-8">
										<div class="m-icheck-group">
											<p class="col-xs-4">
												<input type="checkbox" checked="checked" class="icheckbox" value="<%= Constant.YJFS_XTXX%>" name="yjfs">系统消息提示
											</p>
											<p class="col-xs-4">
												<input type="checkbox" checked="checked" class="icheckbox" value="<%= Constant.YJFS_PDA%>" name="yjfs">PDA
											</p>
											<p class="col-xs-4">
												<input type="checkbox" class="icheckbox" value="<%= Constant.YJFS_DX%>" name="yjfs" id="message"> 短信
											</p>
										</div>
									</div>
								</div>
								<div class="row row-mar">
									<div class="col-xs-3">
										<label class="control-label">消息展示方式：</label>
									</div>
									<div class="col-xs-8">
										<div class="m-icheck-group">
											<p class="col-xs-4">
												右下角浮窗
											</p>
											<p class="col-xs-6">
												浮出时间：<input type="text" id="showTime" style="width: 50px" value="" size="11">秒
											</p>
										</div>
									</div>
								</div>
								<div class="row row-mar">
									<div class="col-xs-3">
										<label class="control-label">预警功能说明：</label>
									</div>
									<div class="col-xs-8">
										<textarea id="functionExplain" disabled="disabled" style="resize: none;" class="form-control input-sm" ></textarea>
									</div>
								</div>
								<div class="row row-mar">
									<div class="col-xs-3">
										<label class="control-label">触发方式：</label>
									</div>
									<div class="col-xs-8">
										<div class="row m-icheck-group" style="margin-bottom: 10px;">
											<p class="col-xs-4">
												<input type="radio" class="icheckradio" value="<%= Constant.CFFS_DZ%>" name="chufa" id="chufa1">动作触发
											</p>
											<p class="col-xs-4">
												<input type="radio" class="icheckradio" value="<%= Constant.CFFS_XT%>" name="chufa" id="chufa2" checked="checked">系统自动
											</p>
										</div>
									</div>
								</div>
								<div class="row row-mar">
									<div class="col-xs-3">
										<label class="control-label">预警时间方案：</label>
									</div>
									<div class="col-xs-8">
										<div class="row m-icheck-group" style="margin-bottom: 10px;">
											<p class="col-xs-4">
												<input type="radio" class="icheckradio" value="tql" name="yjsj" id="tql" checked="checked">提前量
											</p>
											<p class="col-xs-4">
												<input type="radio" class="icheckradio" value="sjd" name="yjsj" id="sjd">时间点
											</p>
										</div>

										<div class="alert alert-info" style="padding: 10px; display: block;" id="tqlBody">
											<div class="row">
												<p class="col-xs-4 m-label-left">预警提前时间：</p>
												<p class="col-xs-2 input-group" style="width: 120px;">
													<input class="form-control input-sm" id="yjtqsj" value="" size="11"><span
														class="input-group-addon">分钟</span>
												</p>
											</div>
										</div>
										 <div class="alert alert-info" style="padding:10px; display: none;" id="sjdBody">
										 <div class="row row-mar">
						                       <p class="col-xs-4 m-label-left">每日第一次触发消息时间</p>
						                       <p class="col-xs-3 input-group"><select style="width: 60px" class="select" id="oneHour"></select>:<select  style="width: 60px" class="select" id="oneMinute"></select></p>
						                     </div> 
						                     <div class="row row-mar">
						                       <p class="col-xs-4 m-label-left">每日第二次触发消息时间</p>
						                       <p class="col-xs-3 input-group"><select style="width: 60px" class="select" id="twoHour"></select>:<select  style="width: 60px" class="select" id="twoMinute"></select></p>
						                     </div> 
						                     <div class="row row-mar">
						                       <p class="col-xs-4 m-label-left">每日第三次触发消息时间</p>
						                       <p class="col-xs-3 input-group"><select style="width: 60px" class="select" id="threeHour"></select>:<select  style="width: 60px" class="select" id="threeMinute"></select></p>
						                     </div> 
										 <!--  
					                     <div class="row row-mar">
					                       <p class="col-xs-4 m-label-left">每日第一次触发消息时间</p>
					                       <div id="one" class="input-group laydate" style="width: 150px">
												<input type="hidden" class="laydate-fmt dateFmt" value="info@HH:mm" /> 
												<input id="gatherTime_input" class="form-control laydate-value" readonly="readonly" > 
												<span class="laydate-value-span input-group-addon m-ui-addon"> 
													<span class="glyphicon glyphicon-calendar" aria-hidden="true" style="font-size: 16px;"></span>
												</span>
											</div>
					                     </div> 
					                     <div class="row row-mar">
					                       <p class="col-xs-4 m-label-left">每日第二次触发消息时间</p>
					                       <div id="two" class="input-group laydate"
												style="width: 150px">
												<input type="hidden" class="laydate-fmt dateFmt"
													value="info@HH:mm" /> <input
													id="gatherTime_input"
													class="form-control laydate-value"
													readonly="readonly" > <span
													class="laydate-value-span input-group-addon m-ui-addon"> <span class="glyphicon glyphicon-calendar" aria-hidden="true"
													style="font-size: 16px;"></span>
												</span>
											</div>
					                     </div> 
					                     <div class="row row-mar">
					                       <p class="col-xs-4 m-label-left">每日第三次触发消息时间</p>
					                       <div id="three" class="input-group laydate"
												style="width: 150px">
												<input type="hidden" class="laydate-fmt dateFmt"
													value="info@HH:mm" /> <input
													id="gatherTime_input"
													class="form-control laydate-value"
													readonly="readonly" > <span
													class="laydate-value-span input-group-addon m-ui-addon"> <span
													class="glyphicon glyphicon-calendar" aria-hidden="true"
													style="font-size: 16px;"></span>
												</span>
											</div>
					                     </div> 
					                     -->
					                    </div> 
									</div>
								</div>
								<div style="display: none" id="timeAndStart">
									<div class="row row-mar" >
										<div class="col-xs-3">
											<label class="control-label">更新时间：</label>
										</div>
										<div class="col-xs-3">
											<input class="form-control input-sm" readonly="readonly" value=""
												disabled>
										</div>
									</div>
									<div class="row row-mar">
										<div class="col-xs-3">
											<label class="control-label">状态：</label>
										</div>
										<div class="col-xs-3">
											<input class="form-control input-sm" readonly="readonly"  value="" disabled>
										</div>
									</div>
								</div>
							</div>
							<div class="m-ui-commitbox"
								style="margin-top: 30px; padding-top: 30px;">
								<button class="btn btn-primary btn-lg" id="save">保存</button>
								<button class="btn btn-default btn-lg" id="cancel">取消</button>
							</div>
						</div>
						<div class="right-inner" style="padding-top: 30px; display: none;" id="infoBody">
							<div style="margin: 0 auto;">
							<div class="row row-mar">
									<div class="col-xs-3">
										<label class="control-label">预警名称：</label>
									</div>
									<div class="col-xs-3">
										<input id="updateName" value="" class="form-control input-sm" size="20" readonly>
									</div>
									<div class="col-xs-2">
										<label class="control-label">预警编码：</label>
									</div>
									<div class="col-xs-3">
										<input id="updateCode" value="" class="form-control input-sm" size="20" readonly>
									</div>
							</div>
								<div class="row row-mar">
									<div class="col-xs-3">
										<label class="control-label">预警方式：</label>
									</div>
									<div class="col-xs-8">
										<div class="m-icheck-group">
											<p class="col-xs-4">
												<input type="checkbox" id="updateXtxxtx" value=<%= Constant.YJFS_XTXX%> class="icheckbox" name="updateYjfs">系统消息提示
											</p>
											<p class="col-xs-4">
												<input type="checkbox" id="updatePda" value=<%= Constant.YJFS_PDA%> class="icheckbox" name="updateYjfs">PDA
											</p>
											<p class="col-xs-4">
												<input type="checkbox" id="updateDx" disabled="disabled" value=<%= Constant.YJFS_DX%> class="icheckbox" name="updateYjfs"> 短信
											</p>
										</div>
									</div>
								</div>
								<div class="row row-mar">
									<div class="col-xs-3">
										<label class="control-label">消息展示方式：</label>
									</div>
									<div class="col-xs-8">
										<div class="m-icheck-group">
											<p class="col-xs-4">
												右下角浮窗
											</p>
											<p class="col-xs-6">
												浮出时间：<input type="text" id="updateShowTime" style="width: 50px" size="11" value="">秒
											</p>
										</div>
									</div>
								</div>
								<div class="row row-mar">
									<div class="col-xs-3">
										<label class="control-label">预警功能说明：</label>
									</div>
									<div class="col-xs-8">
										<textarea id="updateFunctionExplain" disabled="disabled" style="resize: none;" class="form-control input-sm" rows="3" ></textarea>
									</div>
								</div>
								<div class="row row-mar" id="fzcwpcsDiv">
									<div class="col-xs-3">
										<label class="control-label">触发方式：</label>
									</div>
									<div class="col-xs-8">
										<div class="row m-icheck-group" style="margin-bottom: 10px;">
											<p class="col-xs-4">
												<input type="radio" class="icheckradio" value="<%= Constant.CFFS_DZ%>" name="updateChufa" id="updateChufa1">动作触发
											</p>
											<p class="col-xs-4">
												<input type="radio" class="icheckradio" value="<%= Constant.CFFS_XT%>" checked="checked" name="updateChufa" id="updateChufa2">系统自动
											</p>
										</div>
									</div>
								</div>
								
						<!-- 暂存物品超时未返还开始 -->
								<div class="row row-mar" id="zcwpcsDiv" style="display: none">
									<div class="col-xs-2">
										<label class="control-label">触发方式：</label>
									</div>
									<div class="col-xs-10">
										<div class="row m-icheck-group"
											style="width: 400px; margin-bottom: 5px;">
											<p class="col-xs-12 row-mar">
												<input type="radio" class="icheckradio" value="<%= Constant.CFFS_DZ%>" name="updateChufaByzc" id="updateChufa1Byzc"> 动作触发
											</p>
											<p class="col-xs-4">
												<input type="radio" class="icheckradio" value="<%= Constant.CFFS_XT%>" checked="checked" name="updateChufaByzc" id="updateChufa2Byzc"> 系统自动
											</p>
											<p class="col-xs-8">
												预警提前时间：&nbsp;<input class="form-control input-sm  m-inline" id="updateYjtqsjByzc" size="11" value=""
													style="width: 60px;"> 分钟
											</p>
										</div>
										<div class="alert alert-info"
											style="width: 70%; min-width: 620px;">
											<h4 class="font14 m-bold">消息接收设置：</h4>
											<p class="m-line"></p>
											<p class="row-mar">
												消息接收用户/角色
												<button class="btn btn-xs btn-bordered" id="selUser1">选择用户</button>
												<button class="btn btn-xs btn-bordered" id="selRole1">选择角色</button>
											</p>
											<p class="row-mar" id="user1">
												<span class="color-gray">用户：</span>
											</p>
											<p id="role1" >
												<span class="color-gray">角色：</span>
											</p>
										</div>
									</div>
								</div>
						<!-- 暂存物品超时未返还结束 -->

					<!-- 退侦后补充侦查到期提  begin-->
					<div class="row row-mar" id="tzBtn" style="display: none">
						<div class="col-xs-3">
							<label class="control-label">预警时间方案：</label>
						</div>
						<div class="col-xs-8">

							<p class="row-mar">
								<span style="display: inline-block; width:180px;">一次退侦补充侦查周期：</span>&nbsp;&nbsp;&nbsp;&nbsp;<input id="oneDay"
									class="form-control input-sm  m-inline" style="width: 60px;">
								天 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
								 <span
									style="display: inline-block; width: 100px" >预警提前时间：</span><input  id="oneWarning"
									class="form-control input-sm  m-inline" style="width: 60px;">
								天 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</p>
							<div class="row row-mar">
								<p class="col-xs-4 m-label-left">一次触发消息时间:</p>
								<p class="col-xs-3 input-group">
									<select style="width: 60px" class="select" id="updateOneHourTZ"></select>:<select
										style="width: 60px" class="select" id="updateOneMinuteTZ"></select>
								</p>
							</div>

							<p class="row-mar">
								<span style="display: inline-block; width: 180px;">二次退侦补充侦查周期：</span>&nbsp;&nbsp;&nbsp;&nbsp;<input  id="twoDay"
									class="form-control input-sm  m-inline" style="width: 60px;">
								天 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
									style="display: inline-block; width: 100px;">预警提前时间：</span><input   id="twoWarning"
									class="form-control input-sm  m-inline" style="width: 60px;">
								天 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</p>

							<div class="row row-mar">
								<p class="col-xs-4 m-label-left">二次触发消息时间:</p>
								<p class="col-xs-3 input-group">
									<select style="width: 60px" class="select" id="updateTwoHourTZ"></select>:<select
										style="width: 60px" class="select" id="updateTwoMinuteTZ"></select>
								</p>
							</div>

						</div>
					</div>
			<!-- 退侦后补充 侦查到期提  end -->






					<div class="row row-mar" id="otherDiv" >
									<div class="col-xs-3">
										<label class="control-label">预警时间方案：</label>
									</div>
									<div class="col-xs-8">
										<div class="row m-icheck-group" style="margin-bottom: 10px;">
											<p class="col-xs-4">
												<input type="radio" checked="checked" value="tql" class="icheckradio" name="updateYjsj"
													id="updateTql">提前量
											</p>
											<p class="col-xs-4">
												<input type="radio" class="icheckradio" value="sjd" name="updateYjsj"
													id="updateSjd">时间点
											</p>
										</div>

										<div class="alert alert-info" style="padding: 10px; display: block;" id="updateTqlBody">
											<div class="row">
												<p class="col-xs-4 m-label-left">预警提前时间：</p>
												<p class="col-xs-2 input-group" style="width: 120px;">
													<input class="form-control input-sm" id="updateYjtqsj" size="11" value=""><span
														class="input-group-addon">分钟</span>
												</p>
											</div>
										</div>
										 <div class="alert alert-info" style="padding:10px; display: none;" id="updateSjdBody">
					                   		<div class="row row-mar">
						                       <p class="col-xs-4 m-label-left">每日第一次触发消息时间</p>
						                       <p class="col-xs-3 input-group"><select style="width: 60px" class="select" id="updateOneHour"></select>:<select  style="width: 60px" class="select" id="updateOneMinute"></select></p>
						                     </div> 
						                     <div class="row row-mar">
						                       <p class="col-xs-4 m-label-left">每日第二次触发消息时间</p>
						                       <p class="col-xs-3 input-group"><select style="width: 60px" class="select" id="updateTwoHour"></select>:<select  style="width: 60px" class="select" id="updateTwoMinute"></select></p>
						                     </div> 
						                     <div class="row row-mar">
						                       <p class="col-xs-4 m-label-left">每日第三次触发消息时间</p>
						                       <p class="col-xs-3 input-group"><select style="width: 60px" class="select" id="updateThreeHour"></select>:<select  style="width: 60px" class="select" id="updateThreeMinute"></select></p>
						                     </div> 
					                   		
					                    <!--  
					                     <div class="row row-mar">
					                       <p class="col-xs-4 m-label-left">每日第一次触发消息时间</p>
					                       <div id="updateOne" class="input-group laydate"
												style="width: 150px">
												<input type="hidden" class="laydate-fmt dateFmt"
													value="info@HH:mm" /> <input
													id="gatherTime_input"
													class="form-control laydate-value"
													readonly="readonly" > <span
													class="laydate-value-span input-group-addon m-ui-addon"> <span
													class="glyphicon glyphicon-calendar" aria-hidden="true"
													style="font-size: 16px;"></span>
												</span>
											</div>
					                     </div> 
					                     <div class="row row-mar">
					                       <p class="col-xs-4 m-label-left">每日第二次触发消息时间</p>
					                       <div id="updateTwo" class="input-group laydate"
												style="width: 150px">
												<input type="hidden" class="laydate-fmt dateFmt"
													value="info@HH:mm" /> <input
													id="gatherTime_input"
													class="form-control laydate-value"
													readonly="readonly" > <span
													class="laydate-value-span input-group-addon m-ui-addon"> <span
													class="glyphicon glyphicon-calendar" aria-hidden="true"
													style="font-size: 16px;"></span>
												</span>
											</div>
					                     </div> 
					                     <div class="row row-mar">
					                       <p class="col-xs-4 m-label-left">每日第三次触发消息时间</p>
					                       <div id="updateThree" class="input-group laydate"
												style="width: 150px">
												<input type="hidden" class="laydate-fmt dateFmt"
													value="info@HH:mm" /> <input
													id="gatherTime_input"
													class="form-control laydate-value"
													readonly="readonly" > <span
													class="laydate-value-span input-group-addon m-ui-addon"> <span
													class="glyphicon glyphicon-calendar" aria-hidden="true"
													style="font-size: 16px;"></span>
												</span>
											</div>
					                     </div> 
					                     -->
					                    </div> 
					                      
									</div>
								</div>
							</div>
							<div class="m-ui-commitbox"
								style="margin-top: 30px; padding-top: 30px;">
								<button class="btn btn-primary btn-lg" id="update">保存</button>
								<button class="btn btn-default btn-lg" id="updateCancel">取消</button>
							</div>
						</div>
						<div class="right-inner" style="padding-top: 30px;" id="lookBody">
						</div>
					</div>

				</div>
				<!--结束-->
</body>
</html>