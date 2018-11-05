using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;

namespace interrogationAssist.webHelper
{
    public class winInetHelper
    {
        private const int INTERNET_CONNECTION_MODEM = 1;

        private const int INTERNET_CONNECTION_LAN = 2;

        private const int INTERNET_CONNECTION_PROXY = 4;

        private const int INTERNET_CONNECTION_MODEM_BUSY = 8;

        [DllImport("winInet.dll ")]

        //声明外部的函数： 

        private static extern bool InternetGetConnectedState(
            ref  int Flag,
            int dwReserved
        );

        public static void doCheckWinInet()
        {
            try
            {
                DateTime stratDate = DateTime.Now;
                //int tempWait = 0;
               
                //等20秒网络
                TimeSpan twait = new TimeSpan(60000 * 10000);
                bool isCon = false;
                while (true)
                {
                    try
                    {
                        isCon = checkWinInet();
                        if (isCon) return;
                        if (DateTime.Now - stratDate <= twait)
                        {
                            System.Threading.Thread.Sleep(5000);
                            continue;
                        }
                        else
                        {
                            // MessageBox.Show("timeout :DateTimeNow=" + DateTime.Now.ToLongTimeString() + ",stratDate=" + stratDate.ToLongTimeString() + ",twait=" + twait.Ticks / 10000);
                            // timeOutDo();
                            break;
                        }
                    }
                    catch
                    {
                    }
                }
            }
            catch
            {
            }
        }

        public static bool checkWinInet()
        {
            int Flag = 0;

           // string netStates = "";

            if (!InternetGetConnectedState(ref Flag, 0))
            {
               // Console.WriteLine("no！");
                return false;
            }
            else
            {
                return true;
                /*
                if ((Flag & INTERNET_CONNECTION_MODEM) != 0)

                    netStates += " Connect by MODEM /n";

                if ((Flag & INTERNET_CONNECTION_LAN) != 0)

                    netStates += "Connect by LAN  /n";

                if ((Flag & INTERNET_CONNECTION_PROXY) != 0)

                    netStates += "Connect by PROXY /n";

                if ((Flag & INTERNET_CONNECTION_MODEM_BUSY) != 0)

                    netStates += " MODEM is busy  /n";
                 */

            }

           // Console.WriteLine(netStates);
           // Console.ReadLine(); 
        }
    }
}
