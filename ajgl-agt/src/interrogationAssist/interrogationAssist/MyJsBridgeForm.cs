using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;
using System.Diagnostics;
using interrogationAssist.webHelper;

namespace interrogationAssist
{
    public partial class MyJsBridgeForm : Form
    {
        /*
         看下怎么支持post
传值支持,暂时简单的string,int
尝试实现多窗口session同步
js单独提出来,做几个简单效果例子
menu窗口记录存储
任意窗口可触发menu闪动
等待效果和404
配置应用图标,大包前下载个图标替换工具就可以修改了
配置程序名称
安装写注册表64位,32位-o
试试非ie内核-bug过多，还是使用ie至少稳定 */

        public static bool isTest = false;
        public static bool isShowErrHtml = true;
        public static bool isShowNavigatMessageBox = true;
        protected System.Windows.Forms.WebBrowser webBrowser1;
        // private WebKit.WebKitBrowser browser;
        protected TimeOutHelper wbh;
        protected JsBridgeHelper jbh; 
        public MyJsBridgeForm()
        {
            InitializeComponent();
            wbh = new TimeOutHelper();
            jbh = new JsBridgeHelper();
            
        }
/*
        public void openUrlExplorer(String url)
        {
            if (InvokeRequired)
            {
                this.Invoke(new InNoneOutVoidDelegate(openUrlExplorer));
                return;
            }
            else
            {
                if (url == null || url == "")
                {
                    //url = "http://www.baidu.com";
                    MessageBox.Show("无法打开目标！");
                }
                System.Diagnostics.Process.Start("explorer.exe", url);
            }

        }*/

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
            if (addX == 0 && addY == 0) return;
            this.Location = new Point(this.Location.X + addX, this.Location.Y + addY);
        }

        public virtual void setFormLocation(int X, int Y)
        {
            if (X == 0 && Y == 0) return;
            this.Location = new Point(X, Y);
        }

        public virtual void setWindowSize(int x, int y)
        {
            //this.MaximumSize = new System.Drawing.Size(x, y);
            //改变大小时修正下位置,使感觉相对位置不变
            int addX = x-this.Size.Width;
            int addY = y-this.Size.Height;
            this.Size = new Size(x, y);
            this.Location = new Point(this.Location.X - addX / 2, this.Location.Y - addY / 2);
           // this.Width = x;
           // this.Height = y;
        }

        public virtual void hideForm()
        {
            if (InvokeRequired)
            {
                this.Invoke(new InNoneOutVoidDelegate(hideForm));
                return;
            }
            else
            {
                this.Hide();
            }

        }

        public virtual void exitForm()
        {
            if (InvokeRequired)
            {
                this.Invoke(new InNoneOutVoidDelegate(exitForm));
                return;
            }
            else
            {
                this.Close();
            }

        }

        public virtual void exitProgram()
        {
            if (InvokeRequired)
            {
                this.Invoke(new InNoneOutVoidDelegate(exitForm));
                return;
            }
            else
            {
                this.Close();
                Process current = Process.GetCurrentProcess();
                Process[] processes = Process.GetProcessesByName(current.ProcessName);
                foreach (Process process in processes)
                {
                    if (process.Id == current.Id)
                    {
                        process.Kill();
                    }
                }   
            }

        }

        public virtual void messageBoxWindows(string message)
        {
            MessageBox.Show(message);
        }

        public void Stock()
        {
            if (stockDelegate == null) return;
            stockDelegate();

        }

        public void showNewMessage(string text, bool isStockIcon)
        {
            if (showNewMessageDelegate == null) return;
            showNewMessageDelegate(text, isStockIcon);

        }

        public virtual String getConfigAllString(string node)
        {
            List<string> tampList = new List<string>();
            if (File.Exists(Program.configFilePath))
            {
                ReadINI RI = new ReadINI(Program.configFilePath);
                try
                {
                    tampList = RI.GetSectionAll(node);

                    if (tampList == null || tampList.Count == 0)
                    {
                        MessageBox.Show("缺少Basic配置！");
                    }
                }
                catch
                {
                    MessageBox.Show("读取Config.ini文件错误！");
                }
            }
            else
            {
                MessageBox.Show("找不到Config.ini文件！");
            }
            // 暂时解决不了传数组接收问题，先用;分割传递
            string allStr = "";
            for (int i = 0; i < tampList.Count; i++)
            {
                allStr += tampList[i] + ";";
            }
            return allStr;
        }

        public virtual String getConfig(string node, string key)
        {
            String tempStr = "";
            if (File.Exists(Program.configFilePath))
            {
                ReadINI RI = new ReadINI(Program.configFilePath);
                try
                {
                    tempStr = RI.GetValue(node, key);
                }
                catch
                {
                    MessageBox.Show("读取Config.ini文件错误！");
                }
            }
            else
            {
                MessageBox.Show("找不到Config.ini文件！");
            }
            return tempStr;
        }

        public virtual int getScreenWidth()
        {
            return Screen.PrimaryScreen.Bounds.Width;
        }

        public virtual int getScreenHeight()
        {
            return Screen.PrimaryScreen.Bounds.Height;
        }

        public virtual int getWorkingAreaWidth()
        {
            return Screen.PrimaryScreen.WorkingArea.Width;
        }

        public virtual int getWorkingAreaHeight()
        {
            return Screen.PrimaryScreen.WorkingArea.Height;
        }

        public virtual void setScriptSuppressed(bool isSuppressed)
        {
            this.webBrowser1.ScriptErrorsSuppressed = isSuppressed;
          //  MessageBox.Show("设置:" + this.webBrowser1.ScriptErrorsSuppressed.ToString());
        }

       
         

        public virtual void openUrlByForm(String formId, String formType, String formName, String url, int x, int y) 
        {
            try
            {
                if (openWebFormDelegate != null)
                {
                    openWebFormDelegate(formId, formType, formName, url, x, y);
                }
            }
            catch (Exception ex) 
            {
                Console.WriteLine(ex.Message);
            }
        }

        public virtual void reLoadForm()
        {
            try
            {
                if (webBrowser1 != null && wbh != null && wbh.beforeErrUrl != null)
                {
                    webBrowser1.Navigate(wbh.beforeErrUrl);
                  //  webBrowser1.Navigate(new Uri("http://192.168.19.105/zentao"));
                   
                }
                else 
                {
                }
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
        }

        public virtual void openUrlByWinForm(String formId, String formName, String url, int x, int y)
        {
            /*
            if (url == null || url == "" || formId == null || formId == "")
            {
                //url = "http://www.baidu.com";
                MessageBox.Show("无法打开目标！");
                return;
            }*/
        
                openUrlByForm(formId, "winWeb", formName, url, x, y);
            /*
            if (dic_form != null)
            {
                if (dic_form.ContainsKey(formId) && !dic_form[formId].IsDisposed)
                {
                    WebForm tempForm = dic_form[formId];
                    tempForm.loadUrl = url;
                    tempForm.WindowState = FormWindowState.Normal;
                    tempForm.Width = x;
                    tempForm.Height = y;
                    tempForm.loadByUrl();
                    tempForm.Visible = true;
                }
                else
                {
                    WebForm tempForm = new WebForm();
                    tempForm = new WebForm();
                    tempForm.myJsBridgeForm = this;
                    tempForm.Text = formName;
                    tempForm.Tag = formId;
                    tempForm.loadUrl = url;
                    tempForm.Width = x;
                    tempForm.Height = y;
                    tempForm.Show();
                    dic_form.Add(formId, tempForm);
                }
            }*/
        }


        public virtual void openUrlByMenuForm(String url, int x, int y)
        {
            //MessageBox.Show(url);
            openUrlByForm("menu", "winMenu", "", url, x, y);
            /*
            string formId = "menu";
            string formName = "办案通";
            if (url == null || url == "" || formId == null || formId == "")
            {
                //url = "http://www.baidu.com";
                MessageBox.Show("无法打开目标！");
                return;
            }
            if (menuForm != null)
            {
                menuForm.loadUrl = url;
                //tempForm.WindowState = FormWindowState.Normal;
                menuForm.Width = x;
                menuForm.Height = y;
                menuForm.loadByUrl();
                // tempForm.Visible = true;
            }
            else 
            {
                menuForm = new MenuWebForm();
                menuForm.Text = formName;
                menuForm.Tag = formId;
                menuForm.loadUrl = url;
                menuForm.Width = x;
                menuForm.Height = y;
                menuForm.Show();
            }*/
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="wb"></param>
        /// <returns>0未完成 1完成 -1错误</returns>
        protected int checkLoadWebEnd(WebBrowser wb) 
        {
            //有iframe时不太准确
            if (wb == null) return -1;
            for (int i = 0,j=0; i < 10; i++)
            {
                if (wb.ReadyState == WebBrowserReadyState.Complete)
                {
                    if (!wb.IsBusy)
                    {
                        string sUrl = wb.Url.ToString();
                        if (sUrl.Contains("res")) //这是判断没有网络的情况下                             
                        {
                            return -1;
                        }
                        else
                        {
                            //每50毫秒验证1次
                            if (j >= 9)
                            {
                                return 1;
                            }
                            else
                            {
                                j++;
                            }
                        }
                    }
                    else
                    {
                        return 0;
                    }
                }
                Delay(50);  
            }
            return 0;
        }

        private void Delay(int Millisecond) //延迟系统时间，但系统又能同时能执行其它任务；  
        {
            DateTime current = DateTime.Now;
            while (current.AddMilliseconds(Millisecond) > DateTime.Now)
            {
                Application.DoEvents();//转让控制权              
            }
            return;
        }  




        public delegate void InNoneOutVoidDelegate();

        public delegate void openWebForm(String formId,String type, String formName, String url, int x, int y);
        public delegate void ShowNewMessageDelegate(string text, bool isStockIcon);
        public openWebForm openWebFormDelegate;
        public InNoneOutVoidDelegate stockDelegate;
        public ShowNewMessageDelegate showNewMessageDelegate;
    }
}
