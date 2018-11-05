using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Drawing;
using System.Diagnostics;
using System.IO;

namespace interrogationAssist.webHelper
{
    public class JsBridgeHelper
    {
        protected System.Windows.Forms.WebBrowser webBrowser1;
        MyJsBridgeForm myJsBridgeForm;

        public JsBridgeHelper()
        {
        }

        public void init(System.Windows.Forms.WebBrowser webBrowser1, MyJsBridgeForm myJsBridgeForm)
        {
            this.webBrowser1 = webBrowser1;
            this.myJsBridgeForm = myJsBridgeForm;
        }

        public void unInit()
        {
            this.webBrowser1 = null;
            this.myJsBridgeForm = null;
        }


        public virtual void openUrlExplorer(String url)
        {
            if (url == null || url == "")
            {
                //url = "http://www.baidu.com";
                MessageBox.Show("无法打开目标！");
            }
            System.Diagnostics.Process.Start("explorer.exe", url);
        }

        public virtual void moveForm(int addX, int addY)
        {
            if (myJsBridgeForm == null) return;
            myJsBridgeForm.moveForm(addX, addY);
        }

        public virtual void setFormLocation(int X, int Y)
        {
            if (myJsBridgeForm == null) return;
            myJsBridgeForm.setFormLocation(X, Y);
        }

        public virtual void setWindowSize(int x, int y)
        {
            if (myJsBridgeForm == null) return;
            myJsBridgeForm.setWindowSize(x, y);
        }

        public virtual void hideForm()
        {
            if (myJsBridgeForm == null) return;
            myJsBridgeForm.hideForm();
        }

        public virtual void exitForm()
        {
            if (myJsBridgeForm == null) return;
             myJsBridgeForm.exitForm();
        }

        public virtual void exitProgram()
        {
            if (myJsBridgeForm == null) return;
            myJsBridgeForm.exitProgram();
        }

        public virtual void messageBoxWindows(string message)
        {
            MessageBox.Show(message);
        }

        public void Stock()
        {
            if (myJsBridgeForm == null) return;
            myJsBridgeForm.Stock();

        }

        public void showNewMessage(string text, bool isStockIcon)
        {
            if (myJsBridgeForm == null) return;
            myJsBridgeForm.showNewMessage(text, isStockIcon);

        }

        public virtual String getConfigAllString(string node)
        {
            if (myJsBridgeForm == null) return "";
            return myJsBridgeForm.getConfigAllString(node);
        }

        public virtual String getConfig(string node, string key)
        {
            if (myJsBridgeForm == null) return "";
            return myJsBridgeForm.getConfig(node, key);
        }

        public virtual int getScreenWidth()
        {
            if (myJsBridgeForm == null) return -1;
            return myJsBridgeForm.getScreenWidth();
        }

        public virtual int getScreenHeight()
        {
            if (myJsBridgeForm == null) return -1;
            return myJsBridgeForm.getScreenHeight();
        }

        public virtual int getWorkingAreaWidth()
        {
            if (myJsBridgeForm == null) return -1;
            return myJsBridgeForm.getWorkingAreaWidth();
        }

        public virtual int getWorkingAreaHeight()
        {
            if (myJsBridgeForm == null) return -1;
            return myJsBridgeForm.getWorkingAreaHeight();
        }

        public virtual void setScriptSuppressed(bool isSuppressed)
        {
            if (myJsBridgeForm == null) return ;
          //  MessageBox.Show("开始设置" + isSuppressed.ToString());
            myJsBridgeForm.setScriptSuppressed(isSuppressed);
        }



        public virtual void openUrlByForm(String formId, String formType, String formName, String url, int x, int y)
        {
            if (myJsBridgeForm == null) return;
             myJsBridgeForm.openUrlByForm( formId,  formType,  formName,  url,  x,  y);
        }

        public virtual void reLoadForm()
        {
            if (myJsBridgeForm == null) return;
            myJsBridgeForm.reLoadForm();
        }

        public virtual void openUrlByWinForm(String formId, String formName, String url, int x, int y)
        {
            if (myJsBridgeForm == null) return;
            myJsBridgeForm.openUrlByWinForm( formId,  formName,  url,  x,  y);
        }


        public virtual void openUrlByMenuForm(String url, int x, int y)
        {
            if (myJsBridgeForm == null) return;
            myJsBridgeForm.openUrlByMenuForm( url, x, y);
        }
    }
}
