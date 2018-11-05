using System;
using System.Configuration;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.IO;

namespace interrogationAssist
{
    public partial class MenuForm : MainForm
    {
        public static bool flag = false;
        public static bool startflag = false;
        public MainForm myMainForm = null;

       // WebBrowserHelper wbh;
        public MenuForm()
        {
           // isTest = Convert.ToBoolean(ConfigurationManager.AppSettings["isTest"]);
            InitializeComponent();
            if (Program.contentMode.ToLower() == "ie")
            {
                this.webBrowser1 = new System.Windows.Forms.WebBrowser();
                this.SuspendLayout();
                this.webBrowser1.Dock = System.Windows.Forms.DockStyle.Fill;
                this.webBrowser1.TabIndex = 0;
                this.Controls.Add(this.webBrowser1);
                this.webBrowser1.ObjectForScripting = jbh;
               // this.webBrowser1.ScriptErrorsSuppressed = true;
              //  this.webBrowser1.ScrollBarsEnabled = false;
              
                this.ResumeLayout(false);
               // this.webBrowser1.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.webBrowser1_DocumentCompleted);
                //this.webBrowser1.Navigating += new System.Windows.Forms.WebBrowserNavigatingEventHandler(this.webBrowser1_Navigating);
               // this.webBrowser1.DocumentCompleted += new System.Windows.Forms.WebBrowserDocumentCompletedEventHandler(this.webBrowser1_DocumentCompleted);
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
     
        string roomNum = "";
        string menuUrl = "";

        private void MenuForm_Load(object sender, EventArgs e)
        {
            base.mainFormLoad();
            stockDelegate = new InNoneOutVoidDelegate(this.Stock);
            showNewMessageDelegate = new ShowNewMessageDelegate(this.showNewMessage);
          //  wbh = new WebBrowserHelper();
            if (wbh != null)
                wbh.init(webBrowser1, MainForm.menuErrHtml);
            if (jbh != null)
                jbh.init(webBrowser1, this);
            this.webBrowser1.ScriptErrorsSuppressed = true;
            this.webBrowser1.ScrollBarsEnabled = false;
           // string str1 = System.Diagnostics.Process.GetCurrentProcess().MainModule.FileName;
           // webBrowser1.ObjectForScripting = this;
           // webBrowser1.ScriptErrorsSuppressed = true;
       
       
             //this.WindowState = FormWindowState.Maximized;
            // this.MaximumSize = new System.Drawing.Size(100, 100);


           // loginUrl = Convert.ToString(ConfigurationManager.AppSettings["loginUrl"]);
           // roomNum = Convert.ToString(ConfigurationManager.AppSettings["roomNum"]);
         
           // this.menuX = 100;
           // this.menuY = 100;
            stockIcon.Text = Program.getMyTextClassByIndex(0);
            this.Location = new Point(0 - this.Width, 0 - this.Height);
         
           
        }


        private void MenuForm_Shown(object sender, EventArgs e)
        {
            this.Visible = false;
            webBrowser1.Url = new Uri(mainUrl);
           // webBrowser2.Url = new Uri(loginUrl);
            this.TopMost = true;
          
        }

      
       

        private void stocktimer_Tick(object sender, EventArgs e)
        {
            if (flag == false)
            {
                stockIcon.Icon = Properties.Resources.icon_desk48;
                flag = true;
            }
            else
            {
                stockIcon.Icon = Properties.Resources._2;
                flag = false;
            }
        }

      

        // 首先在js中定义被c#调用的方法:
        //function  Messageaa(message)
        //{
        //    alert(message);
        //}

        //在c#调用js方法Messageaa
        private void button1_Click(object sender, EventArgs e)
        {
            // 调用JavaScript的messageBox方法，并传入参数
            object[] objects = new object[1];
            objects[0] = "c#diao javascript";
            webBrowser1.Document.InvokeScript("Messageaa", objects);
        }


     

        public void Stock()
        {
            if (!startflag)
            {
                stocktimer.Start();
                startflag = true;
            }
           
        }

        public void showNewMessage(string text, bool isStockIcon)
        {
            if (text != null)
                stockIcon.Text = text;
            if (isStockIcon)
            {
                if (!startflag)
                {
                    stocktimer.Start();
                    startflag = true;
                }
            }

        }

        private void flicker_Click(object sender, EventArgs e)
        {
            if (startflag)
            {
                stockIcon.Text = Program.getMyTextClassByIndex(0);
                stocktimer.Stop();
                startflag = false;
                stockIcon.Icon = Properties.Resources.icon_desk48;
            }
            //延时 300先记录位置
            int x = System.Windows.Forms.Cursor.Position.X - this.Width;
            int y = System.Windows.Forms.Cursor.Position.Y - this.Height;

            int result = checkLoadWebEnd(webBrowser1);
            if (result == -1)
            {
                MessageBox.Show(Program.getMyTextClassByIndex(5));
                exitProgram();
                return;
            }
            else if (result == 0)
            {
                stockIcon.ShowBalloonTip(3000, Program.getMyTextClassByIndex(7), Program.getMyTextClassByIndex(6), ToolTipIcon.Info);
                //MessageBox.Show("正在加载请稍候再试...");
                return;
            }
            if (x < Screen.PrimaryScreen.WorkingArea.X)
            {
                x = Screen.PrimaryScreen.WorkingArea.X + 2;
            }
            if (x > Screen.PrimaryScreen.WorkingArea.Width - this.Width)
            {
                x = Screen.PrimaryScreen.WorkingArea.Width - 2 - this.Width;
            }
            if (y < Screen.PrimaryScreen.WorkingArea.Y)
            {
                y = Screen.PrimaryScreen.WorkingArea.Y + 2;
            }
            if (y > Screen.PrimaryScreen.WorkingArea.Height - this.Height)
            {
                y = Screen.PrimaryScreen.WorkingArea.Height - 2 - this.Height;
            }
            this.Location = new Point(x, y);
            this.Visible = true;
            this.BringToFront();
            //this.Focus();
            this.Activate();
        }

      

        private void MenuForm_Deactivate(object sender, EventArgs e)
        {
           this.Visible = false;  
        }

        private void MenuForm_FormClosed(object sender, FormClosedEventArgs e)
        {
            setAutoStart();
            stocktimer.Stop();
            stockIcon.Visible = false;
        }

      

        private void WebForm_SizeChanged(object sender, EventArgs e)
        {
            this.Text = Width + "|" + Height;
        }

        private void button1_Click_1(object sender, EventArgs e)
        {
          
        }

        private void MenuForm_FormClosing(object sender, FormClosingEventArgs e)
        {
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
           // if (dic_form != null)
          //  {
          //      dic_form.Clear();
          //      dic_form = null;
          //  }
        }

    }
}