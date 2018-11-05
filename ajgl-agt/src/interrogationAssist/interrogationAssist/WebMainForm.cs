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
    public partial class WebMainForm : MainForm
    {
        public static bool flag = false;
        public MyJsBridgeForm myJsBridgeForm = null;
        public string loadUrl = "file:///D:/zo/gongzuo/贵阳经开/interrogationAssist/interrogationAssist/bin/Debug/frame-cs/html-cs/CS-login.html";


       // WebBrowserHelper wbh; 
      //  private WebKit.WebKitBrowser browser;

        public WebMainForm()
        {
            isTest = Convert.ToBoolean(ConfigurationManager.AppSettings["isTest"]);
            InitializeComponent();
            if (Program.contentMode.ToLower() == "ie")
            {
                this.webBrowser1 = new System.Windows.Forms.WebBrowser();
                this.SuspendLayout();
                this.webBrowser1.Dock = System.Windows.Forms.DockStyle.Fill;
                this.webBrowser1.ObjectForScripting = jbh;
                this.webBrowser1.TabIndex = 0;
                this.Controls.Add(this.webBrowser1);
             
              
                this.ResumeLayout(false);
               
            }
            else if (Program.contentMode.ToLower() == "chrome")
            {
                /*
                this.browser = new WebKit.WebKitBrowser();
                this.browser.Dock = DockStyle.Fill;
                this.Controls.Add(browser);*/
                // browser.
                // this.browser.GetScriptManager.ScriptObject = this;  
                // browser.GetWebViewAsObject
            }
          
        }


        public override void setWindowSize(int x, int y)
        {
            base.setWindowSize(x, y);
            SetReion();
        }

        private void webForm_Load(object sender, EventArgs e)
        {
            base.mainFormLoad();
           // wbh = new WebBrowserHelper();
            this.webBrowser1.ScriptErrorsSuppressed = true;
            this.webBrowser1.ScrollBarsEnabled = false;
            if (wbh != null)
                wbh.init(webBrowser1, MainForm.webErrHtml);
            if (jbh != null)
                jbh.init(webBrowser1, this);
           
            if (MyJsBridgeForm.isTest)
            {
                this.SizeChanged += new System.EventHandler(this.WebForm_SizeChanged);
                this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Sizable;
            }
            this.Text = Program.getMyTextClassByIndex(0);
            //setIEcomp();
          //  webBrowser1.ObjectForScripting = this;
            loadUrl = mainUrl;
          //  wbh.beforeErrUrl = new Uri(mainUrl);
            loadByUrl();
         
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
            SetReion();
            base.OnInvalidated(e);
        }

        /// <summary>
        /// 给窗口圆角
        /// </summary>
        protected void SetReion()
        {
            if (MenuForm.isTest) return;
            // Rgn = Win32.CreateRoundRectRgn(5, 5, ClientRectangle.Width - 4, ClientRectangle.Height - 4, _RgnRadius, _RgnRadius);
            Rgn = Win32.CreateRoundRectRgn(1, 1, ClientRectangle.Width - 1, ClientRectangle.Height - 1, _RgnRadius, _RgnRadius);
            Win32.SetWindowRgn(this.Handle, Rgn, true);
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
                }/*
                else if (Program.contentMode.ToLower() == "chrome" && browser != null)
                {
                    browser.Navigate(loadUrl);
                }*/
            }
           // webBrowser1.Refresh();
         
           // webBrowser1.Navigate(loadUrl);
           // webBrowser1.Navigate();
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
            //loadByUrl();
            if (skin == null)
            {
               skin = new SkinForm(this);
                //不设置这个阴影会跑上来
                this.Owner = skin;
                skin.Show();
                //不设置空会关闭异常
               //this.Owner = null;
            }
        }



        private void WebForm_SizeChanged(object sender, EventArgs e)
        {
            this.Text = Width + "|" + Height;
        }

        private void WebForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            this.Owner = null;
            base.mainFormClosing();
            if (wbh != null)
            {
                wbh.unInit();
                wbh = null;
            }
            if (jbh != null)
            {
                jbh.unInit();
                jbh = null;
            }
         
            //if (myJsBridgeForm != null) 
           // {
           //     myJsBridgeForm.romoveChildForm(this.Tag.ToString());
           //     myJsBridgeForm = null;
           // }
         
        }

        private void WebForm_FormClosed(object sender, FormClosedEventArgs e)
        {

        }

      

        private void button1_Click_1(object sender, EventArgs e)
        {
            loadUrl = "http://www.baidu.com";
            loadByUrl();
        }

    
    }

}
