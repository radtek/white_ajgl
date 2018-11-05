using System;
using System.Configuration;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using AlSkin.AlClass;
using AlSkin.AlForm;

namespace interrogationAssist
{
    public partial class WebForm1 : MainForm
    {
        public static bool flag = false;

       // private System.Windows.Forms.WebBrowser webBrowser1;
      //  WebBrowserHelper wbh;
       // private WebKit.WebKitBrowser browser;

        public WebForm1()
        {
            InitializeComponent();
            isTest = true;
            if (Program.contentMode.ToLower() == "ie")
            {
                this.webBrowser1 = new System.Windows.Forms.WebBrowser();
                this.SuspendLayout();
                this.webBrowser1.Dock = System.Windows.Forms.DockStyle.Fill;
                this.panel1.Controls.Add(this.webBrowser1);
              //  this.webBrowser1.Navigating += new System.Windows.Forms.WebBrowserNavigatingEventHandler(this.webBrowser1_Navigating);
             //   this.webBrowser1.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.webBrowser1_DocumentCompleted);
              //  this.webBrowser1.StatusTextChanged += new System.EventHandler(this.webBrowser1_StatusTextChanged);
               // this.Controls.Add(this.webBrowser1);
              //  this.webBrowser1.ScriptErrorsSuppressed = true;
                this.webBrowser1.ObjectForScripting = jbh;
               // base.webBrowser1 = this.webBrowser1;
                //this.webBrowser1.ScriptErrorsSuppressed = true;
                this.ResumeLayout(false);
             
            }
            else if (Program.contentMode.ToLower() == "chrome")
            {

                /*
                this.browser = new WebKit.WebKitBrowser();
                this.browser.Dock = DockStyle.Fill;
                this.panel1.Controls.Add(this.browser);
                Environment.SetEnvironmentVariable("WEBKIT_IGNORE_SSL_ERRORS", "1");*/

              //  this.Controls.Add(browser);
               // this.browser.GetScriptManager.ScriptObject = this;  
               // browser.GetWebViewAsObject
            }
          //  wbh = new WebBrowserHelper();
           // wbh.init(webBrowser1, MainForm.webErrHtml);
           // jbh.init(webBrowser1, this);
        }
     
        public string loadUrl = "file:///D:/zo/gongzuo/贵阳经开/interrogationAssist/interrogationAssist/bin/Debug/frame-cs/html-cs/CS-login.html";


        private void webForm_Load(object sender, EventArgs e)
        {
            if (wbh != null)
                wbh.init(webBrowser1, MainForm.webErrHtml);
            if (jbh != null)
                jbh.init(webBrowser1, this);

            this.webBrowser1.ScriptErrorsSuppressed = false;
          
            this.webBrowser1.ScrollBarsEnabled = false;
            if (MenuForm.isTest)
            {
                this.SizeChanged += new System.EventHandler(this.WebForm_SizeChanged);
                this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Sizable;
            }
            //setIEcomp();
           // webBrowser1.ObjectForScripting = this;
            loadUrl = textBox1.Text;
            loadByUrl();
          //  webBrowser1.MouseMove += new MouseEventHandler(control_MouseMove);
　　　　　//　webBrowser1.MouseDown += new MouseEventHandler(control_MouseDown);
　　　　　//　webBrowser1.MouseUp += new MouseEventHandler(control_MouseUp);
         
        }
        public void setIEcomp()
        {
            try
            {
                String appname = System.Diagnostics.Process.GetCurrentProcess().ProcessName + ".exe";
                Microsoft.Win32.RegistryKey RK8 = Microsoft.Win32.Registry.LocalMachine.OpenSubKey("Software\\Microsoft\\Internet Explorer\\Main\\FeatureControl\\FEATURE_BROWSER_EMULATION", Microsoft.Win32.RegistryKeyPermissionCheck.ReadWriteSubTree);
                int value9 = 9999;
                int value8 = 8888;
                Version ver = webBrowser1.Version;
                int value = value9;
                try
                {
                    string[] parts = ver.ToString().Split('.');
                    int vn = 0;
                    int.TryParse(parts[0], out vn);
                    if (vn != 0)
                    {
                        if (vn == 9)
                            value = value9;
                        else
                            value = value8;
                    }
                }
                catch
                {
                    value = value9;
                }
                //Setting the key in LocalMachine  
                if (RK8 != null)
                {
                    try
                    {
                        RK8.SetValue(appname, value, Microsoft.Win32.RegistryValueKind.DWord);
                        RK8.Close();
                    }
                    catch (Exception ex)
                    {
                        //MessageBox.Show(ex.Message);  
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);  
            }
        }  
        //拖拽

        private static bool isMouseDown = false;
　　　　private static Point mouseOffset;
        /// 鼠标按下之时，保存鼠标相对于窗体的位置
　　　/// </summary>
　　　　/// <param name="sender"></param>
　　　/// <param name="e"></param>
        private void control_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                Control control = sender as Control;
                int offsetX = -e.X;
                int offsetY = -e.Y;
                //判断是窗体还是控件，从而改进鼠标相对于窗体的位置
                if (!(control is System.Windows.Forms.Form))
                {
                    offsetX = offsetX - control.Left;
                    offsetY = offsetY - control.Top;
                }
                //判断窗体有没有标题栏，从而改进鼠标相对于窗体的位置
                if (this.FormBorderStyle != FormBorderStyle.None)
                {
                    offsetX = offsetX - SystemInformation.FrameBorderSize.Width;
                    offsetY = offsetY - SystemInformation.FrameBorderSize.Height - SystemInformation.CaptionHeight;
                }
                mouseOffset = new Point(offsetX, offsetY);
                isMouseDown = true;
            }
        }

        /// 移动鼠标的时候改变窗体位置
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private  void control_MouseMove(object sender, MouseEventArgs e)
        {
            if (isMouseDown)
            {
                Point mouse = Control.MousePosition;
                mouse.Offset(mouseOffset.X, mouseOffset.Y);
                this.Location = mouse;
            }
        }

        /// 松开鼠标的时候，重设事件
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private static void control_MouseUp(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                isMouseDown = false;
            }
        }
        //---

        #region 重写方法 画圆角
        protected override void OnInvalidated(InvalidateEventArgs e)
        {
          //  SetReion();
            base.OnInvalidated(e);
        }

        /// <summary>
        /// 给窗口圆角
        /// </summary>
        protected void SetReion()
        {
          //  if (MenuForm.isTest) return;
            // Rgn = Win32.CreateRoundRectRgn(5, 5, ClientRectangle.Width - 4, ClientRectangle.Height - 4, _RgnRadius, _RgnRadius);
          //  Rgn = Win32.CreateRoundRectRgn(1, 1, ClientRectangle.Width - 1, ClientRectangle.Height - 1, _RgnRadius, _RgnRadius);
          //  Win32.SetWindowRgn(this.Handle, Rgn, true);
        }
        #endregion

        private int _RgnRadius = 6;//设置窗口圆角
        private int Rgn;

        int iii = 0;
        public void loadByUrl() 
        {
           // loadUrl = "http://www.baidu.com";
          
          //  iii++;
           // if (iii > 3)
           // {
           //     loadUrl = "http://www.sina.com.cn/";
          //  }
            if (InvokeRequired)
            {
                this.Invoke(new InNoneOutVoidDelegate(loadByUrl));
                return;
            }
            else
            {
                if (Program.contentMode.ToLower() == "ie" && webBrowser1 != null)
                {
                    webBrowser1.Navigate(new Uri(loadUrl));
                }
                    /*
                else if (Program.contentMode.ToLower() == "chrome" && browser != null)
                {
                    browser.Navigate(loadUrl);
                }*/
            }
           // webBrowser1.Refresh();
         
           // webBrowser1.Navigate(loadUrl);
           // webBrowser1.Navigate();
        }
      


        public void openUrlExplorer(String url)
        {
            if (InvokeRequired)
            {
                this.Invoke(new InNoneOutVoidDelegate(loadByUrl));
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
          
        }

        public void moveForm(int addX, int addY)
        {
            if (addX == 0 && addY == 0) return;
            this.Location = new Point(this.Location.X + addX, this.Location.Y + addY);
        }

        public void exitForm()
        {
            if (InvokeRequired)
            {
                this.Invoke(new InNoneOutVoidDelegate(loadByUrl));
                return;
            }
            else
            {
                this.Close();
            }
           
        }


       

     


        private SkinForm skin;


        private void WebForm_VisibleChanged(object sender, EventArgs e)
        {
           // if (skin == null)
          //  {
            //    skin = new SkinForm(this);
           //     this.Owner = skin;
           //     skin.Show();
          //  }
        }

        private void button1_Click(object sender, EventArgs e)
        {

        }

        private void WebForm_Shown(object sender, EventArgs e)
        {
            if (skin == null)
            {
            //    skin = new SkinForm(this);
                //不设置这个阴影会跑上来
             //   this.Owner = skin;
             //   skin.Show();
                //不设置空会关闭异常
             //   this.Owner = null;
            }
        }



        private void WebForm_SizeChanged(object sender, EventArgs e)
        {
            this.Text = Width + "|" + Height;
        }

        private void WebForm_FormClosing(object sender, FormClosingEventArgs e)
        {
           
          //  if (menuForm != null) 
          //  {
           //     menuForm.removeChildForm(this.Tag.ToString());
          //      menuForm = null;
          //  }
            if (wbh != null)
            {
                wbh.unInit();
                wbh = null;
            }
        }

        private void WebForm_FormClosed(object sender, FormClosedEventArgs e)
        {

        }

     


        public delegate void InNoneOutVoidDelegate();

        private void button1_Click_1(object sender, EventArgs e)
        {
            loadUrl = "http://www.baidfddugdsdgr.com";
            loadByUrl();
        }

        private void button1_Click_2(object sender, EventArgs e)
        {
            loadUrl = textBox1.Text;
            loadByUrl();
        }

        private void button2_Click(object sender, EventArgs e)
        {

            loadUrl = "http://192.168.19.105/zentao1";
            loadByUrl();
            //webBrowser1.Refresh();
            webBrowser1.Stop();
           // notifyIcon1.ShowBalloonTip(5000, "提示", "正在初始化,请稍候再试...", ToolTipIcon.Info);
        }

        private void button3_Click(object sender, EventArgs e)
        {
            openUrlByMenuForm("http://www.baidfddugdsdgr.com", 120, 390);
        }
    }

}
