 //windows方法
    //IE打开url
    function openUrlExplorer(Url) {
        window.external.openUrlExplorer(Url);
    }
    //winform内嵌ie打开url
    function openWebForm(formId,formName,Url, formWidth, formHeight)
    {
        window.external.openUrlByWinForm(formId,formName,Url, formWidth, formHeight);
    }
    function reLoadForm() {
        window.external.reLoadForm();
    }
    //设置窗体大小
    function setWindowSize(x,y) {
        window.external.setWindowSize(x,y);
    }
     //拖动
    function moveForm(addX,addY) {
        if(addX==0&&addY==0)return;
        window.external.moveForm(addX,addY);
    }
    //设置位置,参数窗体左上坐标
    function setFormLocation(X,Y) {
        window.external.setFormLocation(addX,addY);
    }

    //读取配置文件
    function getConfig(node,key) {
        return window.external.getConfig(node,key); 
    }
    //读取配置文件
    function getConfigAll(node) {
        return window.external.getConfigAllString(node); 
    }
    //隐藏程序
    function hideForm() {
        window.external.hideForm();
    }
    //关闭当前窗口
    function exitForm() {
        window.external.exitForm();
    }
    //结束程序
    function exitProgram() {
        window.external.exitProgram();
    }
    //获取屏幕宽
    function getScreenWidth() {
       return window.external.getScreenWidth();
    }
    //获取屏幕高
    function getScreenHeight() {
       return window.external.getScreenHeight();
    }
    //获取工作区域宽度(不包含任务栏的区域宽度)
    function getWorkingAreaWidth() {
       return window.external.getWorkingAreaWidth();
    }
    //获取工作区域高度(不包含任务栏的区域高度)
    function getWorkingAreaHeight() {
       return window.external.getWorkingAreaHeight();
    }
    //---
    //menu专用
    //闪动小图标
    function myStock() {
        window.external.Stock();
    }
    //设置小图标鼠标悬浮文字,并指定是否闪动图标
    function showNewMessage(msgText, isStock) {
        window.external.showNewMessage(msgText, isStock);
    }
    //---