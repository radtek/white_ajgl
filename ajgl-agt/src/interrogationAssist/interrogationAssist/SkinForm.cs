using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Runtime.InteropServices;
using System.Drawing.Imaging;
using System.Drawing.Drawing2D;
using AlSkin.AlForm;
using AlSkin.AlClass;

namespace interrogationAssist
{
    //绘图层
    partial class SkinForm : Form
    {
        //控件层
        private Form Main;
        private const int addsize = 2;
        //带参构造
        public SkinForm(Form main)
        {
            //将控制层传值过来
            this.Main = main;
            InitializeComponent();
            //置顶窗体
            //Main.TopMost = 
                        TopMost = Main.TopMost;
            Main.BringToFront();
                       
                       //是否在任务栏显示
                       ShowInTaskbar = false;
                       //无边框模式
                       FormBorderStyle = FormBorderStyle.None;
                       //设置绘图层显示位置
                       this.Location = new Point(Main.Location.X-1 - addsize / 2, Main.Location.Y - addsize / 2);
                       //设置ICO
                       Icon = Main.Icon;
                       ShowIcon = Main.ShowIcon;
                       //设置大小
                       Width = Main.Width + addsize;
                       Height = Main.Height + addsize;
                       //设置标题名
                       Text = Main.Text;
                       //绘图层窗体移动
                       Main.LocationChanged  += new EventHandler(Main_LocationChanged);
                       Main.SizeChanged  += new EventHandler(Main_SizeChanged);
                       Main.VisibleChanged  += new EventHandler(Main_VisibleChanged);
                       Main.FormClosed += new FormClosedEventHandler(Main_FormClosed);
                           
                       //还原任务栏右键菜单
                       //CommonClass.SetTaskMenu(Main);
                       //加载背景
                       SetBits(Width, Height);
                       //窗口鼠标穿透效果
                       CanPenetrate();
                        
        }

        #region 初始化
        private void Init() {
            //置顶窗体
           // TopMost = Main.TopMost;
           // Main.BringToFront();
                      //  Main.Focus();
                      //  Main.Activate();
            //是否在任务栏显示
            ShowInTaskbar = false;
            //无边框模式
            FormBorderStyle = FormBorderStyle.None;
            //设置绘图层显示位置
                        this.Location = new Point(Main.Location.X - addsize / 2, Main.Location.Y - addsize / 2);
            //设置ICO
            Icon = Main.Icon;
            ShowIcon = Main.ShowIcon;
            //设置大小
                        Width = Main.Width + addsize;
                        Height = Main.Height + addsize;
            //设置标题名
            Text = Main.Text;
            //绘图层窗体移动
            Main.LocationChanged  += new EventHandler(Main_LocationChanged);
            Main.SizeChanged  += new EventHandler(Main_SizeChanged);
            Main.VisibleChanged  += new EventHandler(Main_VisibleChanged);
            //还原任务栏右键菜单
            //CommonClass.SetTaskMenu(Main);
            //加载背景
                        SetBits(Main.Width + addsize, Main.Height + addsize);
            //窗口鼠标穿透效果
            CanPenetrate();
        }
        #endregion

        #region 还原任务栏右键菜单
        protected override CreateParams CreateParams
        {
            get
            {
                CreateParams cParms = base.CreateParams;
                cParms.ExStyle |= 0x00080000; // WS_EX_LAYERED
                return cParms;
            }
        }
        public class CommonClass
        {
            [DllImport("user32.dll", EntryPoint = "GetWindowLong", CharSet = CharSet.Auto)]
            static extern int GetWindowLong(HandleRef hWnd, int nIndex);
            [DllImport("user32.dll", EntryPoint = "SetWindowLong", CharSet = CharSet.Auto)]
            static extern IntPtr SetWindowLong(HandleRef hWnd, int nIndex, int dwNewLong);
            public const int WS_SYSMENU = 0x00080000;
            public const int WS_MINIMIZEBOX = 0x20000;
            public static void SetTaskMenu(Form form)
            {
                int windowLong = (GetWindowLong(new HandleRef(form, form.Handle), -16));
                SetWindowLong(new HandleRef(form, form.Handle), -16, windowLong | WS_SYSMENU | WS_MINIMIZEBOX);
            }
        }
        #endregion

        #region 减少闪烁
        private void SetStyles()
        {
            SetStyle(
                ControlStyles.UserPaint |
                ControlStyles.AllPaintingInWmPaint |
                ControlStyles.OptimizedDoubleBuffer |
                ControlStyles.ResizeRedraw |
                ControlStyles.DoubleBuffer, true);
            //强制分配样式重新应用到控件上
            UpdateStyles();
            base.AutoScaleMode = AutoScaleMode.None;
        }
        #endregion

        #region 控件层相关事件
        //移动主窗体时
        void Main_LocationChanged(object sender, EventArgs e)
        {
            Location = new Point(Main.Left-1 - addsize / 2 , Main.Top - addsize / 2);
        }

        //主窗体大小改变时
        void Main_SizeChanged(object sender, EventArgs e) {
            //设置大小
                        Width = Main.Width + addsize + 2;//不知道原因一SizeChange就少了一点
                        Height = Main.Height + addsize + 2;
                        SetBits(Width, Height);
                        SetReion();
        }

        //主窗体显示或隐藏时
        void Main_VisibleChanged(object sender, EventArgs e)
        {
            this.Visible = Main.Visible;
        }
        void Main_FormClosed(object sender, FormClosedEventArgs e)
        {
               this.Close();
       }
        #endregion

        #region 使窗口有鼠标穿透功能
        /// <summary>
        /// 使窗口有鼠标穿透功能
        /// </summary>
        private void CanPenetrate()
        {
            int intExTemp = Win32.GetWindowLong(this.Handle, Win32.GWL_EXSTYLE);
            int oldGWLEx = Win32.SetWindowLong(this.Handle, Win32.GWL_EXSTYLE, Win32.WS_EX_TRANSPARENT | Win32.WS_EX_LAYERED);
        }
        #endregion

        #region 不规则无毛边方法
        public void SetBits(int x,int y) {
            //绘制绘图层背景
            Bitmap bitmap = new Bitmap(x,y);
            Rectangle _BacklightLTRB = new Rectangle(20, 20, 20, 20);//窗体光泽重绘边界
            Graphics g = Graphics.FromImage(bitmap);
            g.SmoothingMode = SmoothingMode.HighQuality; //高质量
            g.PixelOffsetMode = PixelOffsetMode.HighQuality; //高像素偏移质量
                        ImageDrawRect.DrawRect(g, Properties.Resources.bgbk2, ClientRectangle, Rectangle.FromLTRB(_BacklightLTRB.X, _BacklightLTRB.Y, _BacklightLTRB.Width, _BacklightLTRB.Height), 1, 1);
 
            if (!Bitmap.IsCanonicalPixelFormat(bitmap.PixelFormat) || !Bitmap.IsAlphaPixelFormat(bitmap.PixelFormat))
                throw new ApplicationException("图片必须是32位带Alhpa通道的图片。");
            IntPtr oldBits = IntPtr.Zero;
            IntPtr screenDC = Win32.GetDC(IntPtr.Zero);
            IntPtr hBitmap = IntPtr.Zero;
            IntPtr memDc = Win32.CreateCompatibleDC(screenDC);
 
            try {
                Win32.Point topLoc = new Win32.Point(Left, Top);
                Win32.Size bitMapSize = new Win32.Size(Width, Height);
                Win32.BLENDFUNCTION blendFunc = new Win32.BLENDFUNCTION();
                Win32.Point srcLoc = new Win32.Point(0, 0);
 
                hBitmap = bitmap.GetHbitmap(Color.FromArgb(0));
                oldBits = Win32.SelectObject(memDc, hBitmap);
 
                blendFunc.BlendOp = Win32.AC_SRC_OVER;
                blendFunc.SourceConstantAlpha = Byte.Parse("255");
                blendFunc.AlphaFormat = Win32.AC_SRC_ALPHA;
                blendFunc.BlendFlags = 0;
 
                Win32.UpdateLayeredWindow(Handle, screenDC, ref topLoc, ref bitMapSize, memDc, ref srcLoc, 0, ref blendFunc, Win32.ULW_ALPHA);
            } finally {
                if (hBitmap != IntPtr.Zero) {
                    Win32.SelectObject(memDc, oldBits);
                    Win32.DeleteObject(hBitmap);
                }
                Win32.ReleaseDC(IntPtr.Zero, screenDC);
                Win32.DeleteDC(memDc);
            }
        }
        #endregion

        private void SkinForm_Shown(object sender, EventArgs e)
        {
            //因为加载阴影瞬间遮盖主窗体，所以先把主窗体放在最前，加载完再改回去
           // bool tempData = Main.TopMost;
           // Main.TopMost = true;
           // Init();
           // Main.TopMost = tempData;
        }


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
            Rgn = Win32.CreateRoundRectRgn(0, 0, ClientRectangle.Width -0, ClientRectangle.Height -0, _RgnRadius, _RgnRadius);
            Win32.SetWindowRgn(this.Handle, Rgn, true);
        }

        private int _RgnRadius = 8;//设置窗口圆角
        private int Rgn;
        #endregion

        private void SkinForm_FormClosing(object sender, FormClosingEventArgs e)
        {

        }
    }
           
}