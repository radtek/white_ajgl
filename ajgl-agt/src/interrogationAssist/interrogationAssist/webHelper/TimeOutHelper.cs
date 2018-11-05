using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace interrogationAssist
{
    public class TimeOutHelper
    {
        System.Windows.Forms.WebBrowser webBrowser;
        System.Threading.Thread timeOutCheckThread;
        bool isRunCheck = false;
        String errHtml = "";
        String[] errCheckText;
        public Uri beforeErrUrl = null;
        bool isNavigatErr = false;//是否正在跳转错误页，防止没有错误页无限跳转

        public TimeOutHelper() 
        {
        }

       

        public void init(System.Windows.Forms.WebBrowser webBrowser,String errHtml)
        {
            unInit();
            this.webBrowser = webBrowser;
            this.errHtml = errHtml;
            this.webBrowser.StatusTextChanged += new System.EventHandler(this.webBrowser1_StatusTextChanged);
            this.webBrowser.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.webBrowser1_DocumentCompleted);
            this.webBrowser.Navigating += new System.Windows.Forms.WebBrowserNavigatingEventHandler(this.webBrowser1_Navigating);
            errCheckText = Program.getMyTextClassByIndex(8).Split('|');
        }

        private void webBrowser1_StatusTextChanged(object sender, EventArgs e)
        {
            if (webBrowser != null)
            {
                if (webBrowser.StatusText.Contains("res:////"))
                {
                  //  MessageBox.Show("resErr :StatusText=" + webBrowser.StatusText);
                    if (!isNavigatErr)
                    {
                        webBrowser.Stop();
                        endErr();
                    }
                }
            }

        }

        private void webBrowser1_Navigating(object sender, WebBrowserNavigatingEventArgs e)
        {
            try
            {
                try
                {
                    if (MyJsBridgeForm.isShowNavigatMessageBox)
                    {
                        MessageBox.Show(e.Url.ToString());
                    }
                }
                catch
                {
                }
              //注意file本地不会触发这个事件,不确定，可能会触发
                String tempStr = e.Url.ToString().Substring(0, 4).ToLower();
                if (tempStr.ToLower().IndexOf("file") > -1 || tempStr.ToLower().IndexOf("res") > -1) 
                {
                     isNavigatErr=true;
                     return;
                }
                else
                {
                    isNavigatErr=false;
                }
                if (tempStr.IndexOf("http") < 0)
                {
                    return;
                }
                //记录url
                beforeErrUrl = e.Url;
               // if (wbh != null)
                //{
                 //   wbh.startCheck();
                startCheck();
               // }
            }
            catch
            {
            }
        }


        private void webBrowser1_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
            if (this.webBrowser.ReadyState == WebBrowserReadyState.Complete)
            {

               // if (wbh != null)
               // {
                // wbh.endCheck();
                endCheck();
               // }
                // this.webBrowser1.Document.Body.s.SetAttribute( "scroll","no");
            }

        }


        public void startCheck() 
        {
          
            isRunCheck = false;
            if (timeOutCheckThread != null)
            {
                timeOutCheckThread.Abort();
            }
            timeOutCheckThread = new System.Threading.Thread(checkTimeOut);
            isRunCheck = true;
            timeOutCheckThread.Start();
        }

        public void endCheck()
        {
            isRunCheck = false;
            if (timeOutCheckThread != null)
            {
                timeOutCheckThread.Abort();
            }
            endErrCheck();
        }

        private void endErrCheck()
        {
           // MessageBox.Show("title=" + webBrowser.DocumentTitle + ",Url" + webBrowser.Url.ToString());
          //  if (webBrowser.DocumentTitle.IndexOf("404") > -1 ||
          //      webBrowser.DocumentTitle.IndexOf("错误") > -1 ||
          //       webBrowser.DocumentTitle.IndexOf("异常") > -1||
          //       webBrowser.DocumentTitle.IndexOf("已取消") > -1||
          //       webBrowser.Url.ToString().IndexOf("res:")>-1
           //    )
            if (checkErrText(webBrowser.DocumentTitle) ||
               webBrowser.Url.ToString().IndexOf("res:") > -1
              )
            {
               
                timeOutDo();
            }
        }

        private bool checkErrText(string checkText) 
        {
            if (errCheckText == null) return false;
            for (int i = 0; i < errCheckText.Length; i++) 
            {
                if(checkText.IndexOf(errCheckText[i]) > -1)
                {
                    return true;
                }
            }
            return false;
        }

        private void endErr()
        {
            isRunCheck = false;
            if (timeOutCheckThread != null)
            {
                timeOutCheckThread.Abort();
            }
            MessageBox.Show("endErr");
            timeOutDo();
        }

        public void unInit()
        {
            isRunCheck = false;
            try
            {
                if (timeOutCheckThread != null)// && timeOutCheckThread.IsAlive) 
                {
                    timeOutCheckThread.Abort();
                    timeOutCheckThread = null;
                }
            }
            catch 
            {
            }
            try
            {
                if (webBrowser != null)
                {
                    webBrowser.StatusTextChanged -= this.webBrowser1_StatusTextChanged;
                    this.webBrowser.DocumentCompleted -= this.webBrowser1_DocumentCompleted;
                    this.webBrowser.Navigating -= this.webBrowser1_Navigating;
                }
            }
            catch
            {
            }
            webBrowser = null;

        }

        private void checkTimeOut ()
        {
            try
            {
                DateTime stratDate = DateTime.Now;
                int tempWait = 0;
                if (Program.explorerMode == Program.ExplorerMode.winMenu)
                {
                    tempWait = MainForm.menuTimeOutMillisecond;
                }
                else if (Program.explorerMode == Program.ExplorerMode.winWeb)
                {
                    tempWait = MainForm.webTimeOutMillisecond;
                }
                TimeSpan twait = new TimeSpan(tempWait*10000);

                while (isRunCheck)
                {
                    if (DateTime.Now - stratDate  <= twait)
                    {
                        System.Threading.Thread.Sleep(200);
                        continue;
                    }
                    else 
                    {
                       // MessageBox.Show("timeout :DateTimeNow=" + DateTime.Now.ToLongTimeString() + ",stratDate=" + stratDate.ToLongTimeString() + ",twait=" + twait.Ticks / 10000);
                       // timeOutDo();
                        break;
                    }
                }
            }
            catch
            {
            }
        }

        private void timeOutDo()
        {
            if (webBrowser.InvokeRequired)
            {
                webBrowser.Invoke(new MyJsBridgeForm.InNoneOutVoidDelegate(timeOutDo));
                return;
            }
            else
            {
              //  webBrowser.Stop();
                if (MyJsBridgeForm.isShowErrHtml)
                {
                    webBrowser.Navigate(errHtml);
                }
               
            }

        }

    }
}
