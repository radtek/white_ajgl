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
    public partial class WebForm : MyJsBridgeForm
    {
        public static bool flag = false;
        public MainForm myMainForm = null;
   
    
        public WebForm()
        {
            InitializeComponent();
            if (Program.contentMode.ToLower() == "ie")
            {
                this.webBrowser1 = new System.Windows.Forms.WebBrowser();
                this.SuspendLayout();
                this.webBrowser1.Dock = System.Windows.Forms.DockStyle.Fill;
                this.webBrowser1.ObjectForScripting = jbh;
               // this.webBrowser1.ScriptErrorsSuppressed = true;//!如果这句放在Controls.Add后面会导致圆角画出现错误?!
              //  this.Controls.Add(this.webBrowser1);
                //this.webBrowser1.ScriptErrorsSuppressed = true;
                this.webBrowser1.TabIndex = 0;
                this.webBrowser1.NewWindow += new System.ComponentModel.CancelEventHandler(this.webBrowser1_NewWindow);
                this.Controls.Add(this.webBrowser1);
                //！如果这句放在Controls.Add前据说ocx控件不能加载了，和上面问题冲突了！！
              //  this.webBrowser1.ScriptErrorsSuppressed = true;//把这两句放到load里似乎不影响画圆角，
              //  this.webBrowser1.ScrollBarsEnabled = false;
             
                 // this.webBrowser1.ScriptErrorsSuppressed = true;
                  //this.webBrowser1.ScriptErrorsSuppressed = false;
                 // this.webBrowser1.ScrollBarsEnabled = false;
              
                this.ResumeLayout(false);
              
              //  this.webBrowser1.Navigating += new System.Windows.Forms.WebBrowserNavigatingEventHandler(this.webBrowser1_Navigating);
              //  this.webBrowser1.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.webBrowser1_DocumentCompleted);  
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
        public string loadUrl = "file:///D:/zo/gongzuo/贵阳经开/interrogationAssist/interrogationAssist/bin/Debug/frame-cs/html-cs/CS-login.html";

        public override void setWindowSize(int x, int y)
        {
            base.setWindowSize(x, y);
            SetReion();
        }

        private void webForm_Load(object sender, EventArgs e)
        {
            if (MenuForm.isTest)
            {
                this.SizeChanged += new System.EventHandler(this.WebForm_SizeChanged);
                this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Sizable;
            }
            openWebFormDelegate = new openWebForm(this.openWebFormByUrl);
            this.webBrowser1.ScriptErrorsSuppressed = true;
            this.webBrowser1.ScrollBarsEnabled = false;
            if (wbh != null)
                wbh.init(webBrowser1, MainForm.webErrHtml);
            if (jbh != null)
                jbh.init(webBrowser1, this);
            //setIEcomp();
          
            loadByUrl();
          //  webBrowser1.MouseMove += new MouseEventHandler(control_MouseMove);
　　　　　//　webBrowser1.MouseDown += new MouseEventHandler(control_MouseDown);
　　　　　//　webBrowser1.MouseUp += new MouseEventHandler(control_MouseUp);
         
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
      //  static int ooo = 0;
        #region 重写方法 画圆角
        protected override void OnInvalidated(InvalidateEventArgs e)
        {
            //if(ooo!=1)
              SetReion();
          //  ooo = 1;
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
             // webBrowser1.Url = new Uri(loadUrl);
                if (Program.contentMode.ToLower() == "ie" && webBrowser1!=null)
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

        public void openWebFormByUrl(String formId, String type, String formName, String url, int x, int y)
        {
            if (myMainForm == null) return;
            if (type.ToLower() == Program.ExplorerMode.winWeb.ToString().ToLower())
            {
                myMainForm.openUrlByWinForm(formId, formName, url, x, y);
            }
            else if (type.ToLower() == Program.ExplorerMode.winMenu.ToString().ToLower())
            {
                myMainForm.openUrlByMenuForm(url, x, y);
            }
            else
            {
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
            if (myMainForm != null) 
            {
                myMainForm.removeChildForm(this.Tag.ToString());
                myMainForm = null;
            }
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
         
        }

        private void WebForm_FormClosed(object sender, FormClosedEventArgs e)
        {

        }

        private void webBrowser1_NewWindow(object sender, CancelEventArgs e)
        {
          //  String tempId=DateTime.Now.ToString("yyyyMMddHHmmssfff");
           //  string url = ((System.Windows.Forms.WebBrowser)sender).StatusText;
       // webBrowser1.Navigate(url);

           // openUrlByForm(tempId,Program.ExplorerMode.winWeb.ToString(),myMainForm==null?myMainForm.Name:"",url,)
           // e.Cancel = true;
        }

      

        private void button1_Click_1(object sender, EventArgs e)
        {
            loadUrl = "http://www.baidu.com";
            loadByUrl();
        }

    
    }

}
