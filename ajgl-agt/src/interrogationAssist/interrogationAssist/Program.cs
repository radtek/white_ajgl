using System;
using System.Configuration;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;
using System.IO;
using System.Diagnostics;

namespace interrogationAssist
{
    static class Program
    {
        public static String configFilePath = Application.StartupPath + @"\Config.ini";
        public static ExplorerMode explorerMode;//winWeb,winMenu
        public static string contentMode = "IE";//Chrome,IE//Chrome内核第三方控件bug很多,没法使用,暂时不支持Chrome
        static List<myText.myTextClass> l_myTextClass;
        public enum ExplorerMode { winWeb, winMenu, nodata };
        /// <summary>
        /// 应用程序的主入口点。
        /// </summary>
        [STAThread]
        static void Main(string[] args)
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            if (checkIsRun()) 
            {
                MessageBox.Show("程序已在运行!");
                return;
            }
            if (args.Length == 0)
            {
            }
            else
            {
                bool isCheck=false;
                for(int i=0;i<args.Length;i++)
                {
                    if(args[i].ToLower()=="checkinternet")
                    {
                         isCheck=true;
                        break;
                    }
                }
                if(isCheck)
                    webHelper.winInetHelper.doCheckWinInet();
            }
             //   MessageBox.Show(args[0] + args[1]);
          //  return;
           // setAutoStart();

        //   webHelper.winInetHelper.doCheckWinInet();
         //   MenuForm myForm = new MenuForm();
            readJsonText();
            readMode();
            Form startForm = createStartForm();
            if (startForm != null) 
            {
               Application.Run(startForm);
            }
            
           // myForm.Visible = false;
        //
           //Application.Run(new WebForm1());
        }

        public static void setAutoStart() 
        {
            try
            {
                //程序运行位置
                string R_startPath = Application.ExecutablePath;
                //对应于HKEY_LOCAL_MACHINE主键
                Microsoft.Win32.RegistryKey R_local = Microsoft.Win32.Registry.CurrentUser;//当前用户;//Microsoft.Win32.Registry.LocalMachine;//全局用户

                //开机自动运行
                Microsoft.Win32.RegistryKey R_run = R_local.CreateSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run");
                R_run.SetValue("interrogationAssist", R_startPath + " checkInternet");
                // R_run.SetValue("interrogationAssist", false);//取消开机启动
                R_run.Close();
                R_local.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private static bool checkIsRun() 
        {
            Process current = Process.GetCurrentProcess();
            Process[] processes = Process.GetProcessesByName(current.ProcessName);
            foreach (Process process in processes)
            {
                if (process.ProcessName == current.ProcessName && process.Id!= current.Id)
                {
                  //  MessageBox.Show(process.ProcessName);
                    return true;
                }
            }
            return false;
        }

        public static void setNoAutoStart()
        {
            try
            {
                //程序运行位置
                string R_startPath = Application.ExecutablePath;
                //对应于HKEY_LOCAL_MACHINE主键
                Microsoft.Win32.RegistryKey R_local = Microsoft.Win32.Registry.CurrentUser;//当前用户;//Microsoft.Win32.Registry.LocalMachine;//全局用户

                //开机自动运行
                Microsoft.Win32.RegistryKey R_run = R_local.CreateSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run");
                //R_run.SetValue("interrogationAssist", R_startPath);
                R_run.SetValue("interrogationAssist", false);//取消开机启动
                R_run.Close();
                R_local.Close();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }
        private static Form createStartForm() 
        {
            //检查是否配置了测试启动
            Form textForm = checkTestStartForm();
            if (textForm != null) 
            {
                return textForm;
            }

            if (explorerMode == null || explorerMode == ExplorerMode.nodata)
            {
                return null;
            }
            else if (explorerMode == ExplorerMode.winMenu) 
            {
                MenuForm myForm = new MenuForm();
                return myForm;
            }
            else if (explorerMode == ExplorerMode.winWeb)
            {
                WebMainForm myForm = new WebMainForm();
                return myForm;
            }
            return null;
        }
        private static Form checkTestStartForm()
        {
            try
            {
                string startMode = Convert.ToString(ConfigurationManager.AppSettings["startMode"]);
                //  if (startMode.ToLower().Equals("editText"))
                //  {

                //  }
                // else
                if (startMode.ToLower().Equals("testweb"))
                {
                    return (new WebForm1());
                }
            }
            catch (Exception ex) 
            {
                
            }
            return null;
        }

       
        private static void readMode() 
        {

            if (File.Exists(configFilePath))
            {
                ReadINI RI = new ReadINI(configFilePath);
                try
                {
                    String tempStr = RI.GetValue("WindowData", "ExplorerMode");
                    if (tempStr == null || tempStr == "")
                    {
                        MessageBox.Show("缺少BaseUrl配置项！"); 
                        return ;
                    }
                  //  return explorerMode;

                    if (tempStr == ExplorerMode.winMenu.ToString()) 
                    {
                        explorerMode = ExplorerMode.winMenu;
                    }
                    else if (tempStr == ExplorerMode.winWeb.ToString())
                    {
                        explorerMode = ExplorerMode.winWeb;
                    }
                    else 
                    {
                        explorerMode = ExplorerMode.nodata;
                    }

                }
                catch
                {
                    MessageBox.Show("读取Config.ini文件错误！");
                    return ;
                }
                try
                {
                    contentMode = RI.GetValue("[WindowData]", "ContentMode");
                    if (contentMode == null || contentMode == "")
                    {
                        contentMode = "IE";
                        return;
                    }
                    //  return explorerMode;
                }
                catch
                {
                   // MessageBox.Show("读取Config.ini文件错误！");
                    return;
                }
            }
            else
            {
                MessageBox.Show("找不到Config.ini文件！");
                return ;
            }
        }

        private static void readJsonText()
        {
            l_myTextClass = new List<myText.myTextClass>();
            string jsonPath =Application.StartupPath + Convert.ToString(ConfigurationManager.AppSettings["jsonTextPath"]);
            if (File.Exists(jsonPath))
            {
                string jsonStr=File.ReadAllText(jsonPath);
                l_myTextClass = JsonHelper.Deserialize<List<myText.myTextClass>>(jsonStr);
            }
           
        }

        public static String getMyTextClassByIndex(int index)
        {
            try
            {
                if (l_myTextClass != null && index > -1)
                {
                    return l_myTextClass[index].Text;
                }
            }
            catch 
            {
            }
            return "";
        }
        /*
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
        */
    }
}
