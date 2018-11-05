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
    public partial class MainForm : MyJsBridgeForm
    {
        public static bool flag = false;
        public static bool startflag = false;
        public static string webErrHtml = Application.StartupPath + @"\html-cs\CS-error.html";
        public static string menuErrHtml = Application.StartupPath + @"\html-cs\CS-menu-error.html";
        //public static string mainTitleName = "";
        public static int webTimeOutMillisecond = 10000;
        public static int menuTimeOutMillisecond = 15000;


        public MainForm()
        {
            try
            {
                isTest = Convert.ToBoolean(ConfigurationManager.AppSettings["isTest"]);
                isShowErrHtml = Convert.ToBoolean(ConfigurationManager.AppSettings["isShowErrHtml"]);
                isShowNavigatMessageBox = Convert.ToBoolean(ConfigurationManager.AppSettings["isShowNavigatMessageBox"]);
            }
            catch
            {
            }
            InitializeComponent();
          
           
        }

        protected string mainUrl = "";

        protected void mainFormLoad()
        {
           // string str1 = System.Diagnostics.Process.GetCurrentProcess().MainModule.FileName;
          
            //在应用程序选项卡中点击程序集信息，然后勾选“使程序集COM可见”。
           // setIEcomp();
            openWebFormDelegate =new openWebForm(this.openWebForm);
           
            if (File.Exists(Program.configFilePath))
            {

                ReadINI RI = new ReadINI(Program.configFilePath);
                try
                {
                    mainUrl = RI.GetValue("Basic", "BaseUrl");

                    if (mainUrl == null || mainUrl == "")
                    {
                        MessageBox.Show(Program.getMyTextClassByIndex(1));
                        this.Close();
                    }
                }
                catch
                {
                    MessageBox.Show(Program.getMyTextClassByIndex(2));
                    this.Close();
                }
                /*
                try
                {
                    mainTitleName = RI.GetValue("WindowData", "TitleName");

                    if (mainTitleName == null || mainTitleName == "")
                    {
                        mainTitleName = " ";
                    }
                }
                catch
                {
                }*/

                try
                {
                    webErrHtml = RI.GetValue("WindowData", "WebErrHtml");

                    if (webErrHtml == null || webErrHtml == "")
                    {
                        webErrHtml = Application.StartupPath + @"\html-cs\CS-error.html";
                    }
                    else
                    {
                        webErrHtml = Application.StartupPath + webErrHtml;
                    }
                }
                catch
                {
                }

                try
                {
                    menuErrHtml = RI.GetValue("WindowData", "MenuErrHtml");

                    if (menuErrHtml == null || menuErrHtml == "")
                    {
                        menuErrHtml = Application.StartupPath + @"\html-cs\CS-menu-error.html";
                    }
                    else
                    {
                        menuErrHtml = Application.StartupPath + menuErrHtml;
                    }
                }
                catch
                {
                }




                try
                {
                    webTimeOutMillisecond = Convert.ToInt32(RI.GetValue("WindowData", "WebTimeOutMillisecond"));

                }
                catch
                {
                }

                try
                {
                    menuTimeOutMillisecond = Convert.ToInt32(RI.GetValue("WindowData", "MenuTimeOutMillisecond"));

                }
                catch
                {
                }

            }
            else
            {
                MessageBox.Show(Program.getMyTextClassByIndex(3));
                this.Close();
            }
            setAutoStart();
      
          //  this.Location = new Point(0 - this.Width, 0 - this.Height);
           // this.Hide();
            if (isTest){
              
                this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Sizable;
            }
           
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="value">ie8=8000,8888强制ie8;ie9=9000,9999强制ie9;ie10=10000;改成很大默认找可用最高ie版本</param>
        /// 
        public void setIEcomp(int value)
        {
            try
            {
                String appname = System.Diagnostics.Process.GetCurrentProcess().ProcessName + ".exe";

                // object testvalue = Microsoft.Win32.Registry.LocalMachine.OpenSubKey(@"HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Internet Explorer").GetValue("Version").ToString();

                Microsoft.Win32.RegistryKey mainKey = Microsoft.Win32.Registry.LocalMachine;
                Microsoft.Win32.RegistryKey subKey = mainKey.OpenSubKey(@"SOFTWARE\Microsoft\Internet Explorer");
                object testvalue = subKey.GetValue("Version");

                Microsoft.Win32.RegistryKey RK8 = Microsoft.Win32.Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Internet Explorer\\Main\\FeatureControl\\FEATURE_BROWSER_EMULATION", Microsoft.Win32.RegistryKeyPermissionCheck.ReadWriteSubTree);
                // Microsoft.Win32.RegistryKey RK8 = Microsoft.Win32.Registry.LocalMachine.OpenSubKey("Software\\Microsoft\\Internet Explorer\\Main\\FeatureControl\\FEATURE_BROWSER_EMULATION", Microsoft.Win32.RegistryKeyPermissionCheck.ReadWriteSubTree);
                int value9 = 9999;
                int value8 = 8888;
              //  Version ver = null;// webBrowser1.Version;
                //int value = value9;
                try
                {
                    string[] parts = testvalue.ToString().Split('.');
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

        public void setIEcomp()
        {
            try
            {
                String appname = System.Diagnostics.Process.GetCurrentProcess().ProcessName + ".exe";

              // object testvalue = Microsoft.Win32.Registry.LocalMachine.OpenSubKey(@"HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Internet Explorer").GetValue("Version").ToString();

                Microsoft.Win32.RegistryKey mainKey = Microsoft.Win32.Registry.LocalMachine;
                Microsoft.Win32.RegistryKey subKey = mainKey.OpenSubKey(@"SOFTWARE\Microsoft\Internet Explorer");
                object testvalue = subKey.GetValue("Version");

                Microsoft.Win32.RegistryKey RK8 = Microsoft.Win32.Registry.CurrentUser.OpenSubKey("Software\\Microsoft\\Internet Explorer\\Main\\FeatureControl\\FEATURE_BROWSER_EMULATION", Microsoft.Win32.RegistryKeyPermissionCheck.ReadWriteSubTree);
               // Microsoft.Win32.RegistryKey RK8 = Microsoft.Win32.Registry.LocalMachine.OpenSubKey("Software\\Microsoft\\Internet Explorer\\Main\\FeatureControl\\FEATURE_BROWSER_EMULATION", Microsoft.Win32.RegistryKeyPermissionCheck.ReadWriteSubTree);
                int value9 = 9999;
                int value8 = 8888;
                Version ver = null;// webBrowser1.Version;
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

        protected void setAutoStart()
        {
            if (File.Exists(Program.configFilePath))
            {
                try
                {
                    ReadINI RI = new ReadINI(Program.configFilePath);


                    bool isAutoStart = Convert.ToBoolean(RI.GetValue("WindowData", "AutoStart"));
                    if (isAutoStart)
                    {
                        Program.setAutoStart();
                    }
                    else
                    {
                        Program.setNoAutoStart();
                    }

                }
                catch
                {
                    Program.setAutoStart();
                }
            }
            else
            {

            }
        }

        //打开窗口维护
        public static Dictionary<string, WebForm> dic_form = new Dictionary<string, WebForm>();
        public void removeChildForm(string formId)
        {
            if (formId == null) return;
            try
            {
                if (dic_form.ContainsKey(formId))
                {
                    dic_form.Remove(formId);
                }
            }
            catch (Exception ex)
            {

            }
        }

        //menu窗体维护
        protected static MenuWebForm menuForm = null;
        public void romoveMenuForm()
        {
            menuForm = null;
        }

       
      
        protected void mainFormClosing()
        {
            if (dic_form != null)
            {
                dic_form.Clear();
                dic_form = null;
            }
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="formId"></param>
        /// <param name="type">winMenu,winWeb</param>
        /// <param name="formName"></param>
        /// <param name="url"></param>
        /// <param name="x"></param>
        /// <param name="y"></param>
        public void  openWebForm(String formId, String type, String formName, String url, int x, int y) 
        {
            if (type.ToLower() == Program.ExplorerMode.winWeb.ToString().ToLower())
            {
                openUrlByWinForm(formId, formName, url, x, y);
            }
            else if (type.ToLower() == Program.ExplorerMode.winMenu.ToString().ToLower())
            {
                openUrlByMenuForm(url,x,y);
            }
            else 
            {
            }
        }

        public void openUrlByWinForm(String formId, String formName, String url, int x, int y)
        {
            if (url == null || url == "" || formId == null || formId == "")
            {
                //url = "http://www.baidu.com";
                MessageBox.Show(Program.getMyTextClassByIndex(4));
                return;
            }
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
                   // tempForm.myMainForm = this;
                }
                else
                {
                    WebForm tempForm = new WebForm();
                    //tempForm = new WebForm();
                    tempForm.myMainForm = this;
                    tempForm.Text = formName;
                    tempForm.Tag = formId;
                    tempForm.loadUrl = url;
                    tempForm.Width = x;
                    tempForm.Height = y;
                    tempForm.Show();
                    dic_form.Add(formId, tempForm);
                }
            }
        }

        public void openUrlByMenuForm(String url, int x, int y)
        {
            string formId = "menu";
            string formName = Program.getMyTextClassByIndex(0);
            if (url == null || url == "" || formId == null || formId == "")
            {
                //url = "http://www.baidu.com";
                MessageBox.Show(Program.getMyTextClassByIndex(4));
                return;
            }
            if (Program.explorerMode == null || Program.explorerMode == Program.ExplorerMode.nodata || Program.explorerMode == Program.ExplorerMode.winWeb)
            {
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
                    menuForm.myMainForm = this;
                    menuForm.Text = formName;
                    menuForm.Tag = formId;
                    menuForm.loadUrl = url;
                    menuForm.Width = x;
                    menuForm.Height = y;
                    menuForm.Show();
                }
            }
            else if (Program.explorerMode == Program.ExplorerMode.winMenu)
            {
                WebMainForm wmf = (WebMainForm)this;
                if (wmf != null)
                {
                    wmf.loadUrl = url;
                    //tempForm.WindowState = FormWindowState.Normal;
                    wmf.Width = x;
                    wmf.Height = y;
                    wmf.loadByUrl();
                    // tempForm.Visible = true;
                }
                
            }
           
          
        }

      

    }
}