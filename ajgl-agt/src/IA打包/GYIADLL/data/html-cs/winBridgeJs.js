 //windows����
    //IE��url
    function openUrlExplorer(Url) {
        window.external.openUrlExplorer(Url);
    }
    //winform��Ƕie��url
    function openWebForm(formId,formName,Url, formWidth, formHeight)
    {
        window.external.openUrlByWinForm(formId,formName,Url, formWidth, formHeight);
    }
    function reLoadForm() {
        window.external.reLoadForm();
    }
    //���ô����С
    function setWindowSize(x,y) {
        window.external.setWindowSize(x,y);
    }
     //�϶�
    function moveForm(addX,addY) {
        if(addX==0&&addY==0)return;
        window.external.moveForm(addX,addY);
    }
    //����λ��,����������������
    function setFormLocation(X,Y) {
        window.external.setFormLocation(addX,addY);
    }

    //��ȡ�����ļ�
    function getConfig(node,key) {
        return window.external.getConfig(node,key); 
    }
    //��ȡ�����ļ�
    function getConfigAll(node) {
        return window.external.getConfigAllString(node); 
    }
    //���س���
    function hideForm() {
        window.external.hideForm();
    }
    //�رյ�ǰ����
    function exitForm() {
        window.external.exitForm();
    }
    //��������
    function exitProgram() {
        window.external.exitProgram();
    }
    //��ȡ��Ļ��
    function getScreenWidth() {
       return window.external.getScreenWidth();
    }
    //��ȡ��Ļ��
    function getScreenHeight() {
       return window.external.getScreenHeight();
    }
    //��ȡ����������(��������������������)
    function getWorkingAreaWidth() {
       return window.external.getWorkingAreaWidth();
    }
    //��ȡ��������߶�(������������������߶�)
    function getWorkingAreaHeight() {
       return window.external.getWorkingAreaHeight();
    }
    //---
    //menuר��
    //����Сͼ��
    function myStock() {
        window.external.Stock();
    }
    //����Сͼ�������������,��ָ���Ƿ�����ͼ��
    function showNewMessage(msgText, isStock) {
        window.external.showNewMessage(msgText, isStock);
    }
    //---