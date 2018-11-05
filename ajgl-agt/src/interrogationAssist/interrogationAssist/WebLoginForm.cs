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
    public partial class WebLogin : Form
    {
        public static bool flag = false;

        public WebLogin()
        {

            InitializeComponent();
           
        }
        public string loadUrl = "file:///D:/zo/gongzuo/贵阳经开/interrogationAssist/interrogationAssist/bin/Debug/frame-cs/html-cs/CS-login.html";//515,456


        private void webForm_Load(object sender, EventArgs e)
        {
            //setIEcomp();
            webBrowser1.ObjectForScripting = this;
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
            SetReion();
            base.OnInvalidated(e);
        }

        /// <summary>
        /// 给窗口圆角
        /// </summary>
        protected void SetReion()
        {
            // Rgn = Win32.CreateRoundRectRgn(5, 5, ClientRectangle.Width - 4, ClientRectangle.Height - 4, _RgnRadius, _RgnRadius);
            Rgn = Win32.CreateRoundRectRgn(0, 0, ClientRectangle.Width - 0, ClientRectangle.Height - 0, _RgnRadius, _RgnRadius);
            Win32.SetWindowRgn(this.Handle, Rgn, true);
        }
        #endregion

        private int _RgnRadius = 6;//设置窗口圆角
        private int Rgn;


        public void loadByUrl() 
        {
            webBrowser1.Url = new Uri(loadUrl);
        }

        private void webBrowser1_DocumentCompleted(object sender, WebBrowserDocumentCompletedEventArgs e)
        {
          //  if (webBrowser1.ReadyState != WebBrowserReadyState.Complete) return;
          //  Size szb = new Size(webBrowser1.Document.Body.OffsetRectangle.Width,
          //      webBrowser1.Document.Body.OffsetRectangle.Height);//网页尺寸
            //this.Size = szb;
          //  webBrowser1.Size = szb;
         //  webBrowser1.Invalidate();
             if (this.webBrowser1.ReadyState == WebBrowserReadyState.Complete)
            {
               // this.webBrowser1.Document.Body.s.SetAttribute( "scroll","no");
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
                this.Owner = skin;
                skin.Show();
            }
        }



        private void WebForm_SizeChanged(object sender, EventArgs e)
        {
            this.Text = Width + "|" + Height;
        }

    }

}
