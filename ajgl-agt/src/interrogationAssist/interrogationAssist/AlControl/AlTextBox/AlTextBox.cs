//作者：阿龙(Along)
//QQ号：646494711
//QQ群：57218890
//网站：http://www.8timer.com
//博客：http://www.cnblogs.com/Along729/
//声明：未经作者许可，任何人不得发布出售该源码，请尊重别人的劳动成果，谢谢大家支持
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using AlSkin.AlClass;

namespace AlSkin.AlControl.AlTextBox
{
    public partial class AlTextBox : UserControl
    {
        #region 声明
        private Bitmap _TextBoxBackImg = ImageObject.GetResBitmap("AlSkin.AlSkinImg.AlTextBoxImg.Textbox.png");
        private State state = State.Normal;
        private bool _Isico = false;
        private Bitmap _Ico;
        private Padding _IcoPadding=new Padding(3,3,0,0);
        //枚鼠标状态
        private enum State
        {
            Normal = 1,
            MouseOver = 2,
            MouseDown = 3,
            Disable = 4,
            Default = 5
        }
        #endregion

        #region 构造
        public AlTextBox()
        {
            InitializeComponent();
            this.SetStyle(ControlStyles.UserPaint, true);
            this.SetStyle(ControlStyles.DoubleBuffer, true);
            this.SetStyle(ControlStyles.AllPaintingInWmPaint, true);
            this.SetStyle(ControlStyles.SupportsTransparentBackColor, true);
            this.SetStyle(ControlStyles.StandardDoubleClick, false);
            this.SetStyle(ControlStyles.Selectable, true);
            this.BackColor = Color.Transparent;
        }
        #endregion

        #region 属性
        [Category("阿龙自定义属性"), Description("与控件关联的文本")]
        public string text
        {
            get
            {
                return BaseText.Text;
            }
            set
            {
                BaseText.Text = value;
            }
        }
       
        [Category("阿龙自定义属性"), Description("输入最大字符数")]
        public int MaxLength
        {
            get { return BaseText.MaxLength; }
            set { BaseText.MaxLength = value; }       
        
        }
      
        [Category("阿龙自定义属性"), Description("与控件关联的文本")]
        public new string Text
        {
            get
            {
                return BaseText.Text;
            }
            set
            {
                BaseText.Text = value;
            }
        }

        [Category("阿龙自定义属性"), Description("将控件设为密码显示")]
        public bool IsPass
        {
            get
            {
                return BaseText.UseSystemPasswordChar;
            }
            set
            {
                BaseText.UseSystemPasswordChar = value;
            }
        }

        [Category("阿龙自定义属性"), Description("密码显示字符")]
        public char PassChar
        {
            get
            {
                return BaseText.PasswordChar;
            }
            set
            {
                BaseText.PasswordChar = value;
            }
        }

        [Category("阿龙自定义属性"), Description("将控件设为多行文本显示")]
        public bool Multiline
        {
            get
            {
                return BaseText.Multiline;
            }
            set
            {
                BaseText.Multiline = value;
                if (value)
                { BaseText.Height = this.Height - 6; }
                else
                {
                    base.Height = 22;
                    BaseText.Height = 16;
                    this.Invalidate();
                }

            }
        }

        [Category("阿龙自定义属性"), Description("设置控件中文本字体")]
        public Font font
        {
            get
            {
                return BaseText.Font;
            }
            set
            {
                BaseText.Font = value;
            }
        }

        [Category("阿龙自定义属性"), Description("将控件设为只读")]
        public bool ReadOnly
        {
            get
            {
                return BaseText.ReadOnly;
            }
            set
            {
                BaseText.ReadOnly = value;
            }
        }

        [Category("阿龙自定义属性"), Description("多行文本的编辑行")]
        public String[] lines
        {
            get
            {
                return BaseText.Lines;
            }
            set
            {
                BaseText.Lines = value;
            }
        }

        [Category("阿龙自定义属性"), Description("是否显示图标")]
        public bool Isico
        {
            get
            {
                return _Isico;
            }
            set
            {
                _Isico = value;
                if (value)
                {
                    if (_Ico != null)
                    {
                        BaseText.Location = new Point(_IcoPadding.Left + _Ico.Width, 3);
                        BaseText.Width = BaseText.Width - _IcoPadding.Left - _Ico.Width;
                    }
                    else
                    {
                        BaseText.Location = new Point(25, 3);
                        BaseText.Width = BaseText.Width - 25;
                    }
                }
                this.Invalidate();
            }
        }

        [Category("阿龙自定义属性"), Description("图标文件")]
        public Bitmap Ico
        {
            get
            {
                return _Ico;
            }
            set
            {
                _Ico = value;
            }
        }

        [Category("阿龙自定义属性"), Description("控件内部间距，图标文件")]
        public Padding IcoPadding
        {
            get { return _IcoPadding; }
            set {
                _IcoPadding = value;
                this.Invalidate();
            }
        }
        #endregion

        #region 委托
        public event EventHandler IcoOnclick;
        #endregion

        #region 方法
        protected override void OnPaint(PaintEventArgs e)
        {
            Rectangle rc = this.ClientRectangle;
            Graphics g = e.Graphics;
            ImageDrawRect.DrawRect(g, _TextBoxBackImg, rc, Rectangle.FromLTRB(10, 10, 10, 10), (int)state, 5);
            if (_Isico)
            {
                if (_Ico != null)
                {
                    g.DrawImage(_Ico,new Point(_IcoPadding.Left,_IcoPadding.Top));
                }                            
            }            
            base.OnPaint(e);
        }

        private void AlTextBox_Resize(object sender, EventArgs e)
        {
            if (this.Height > 22)
            {
                Multiline = true;
            }
            else
            {
                this.Height = 22;
                Multiline = false;
            }
        }

        private void NotifyIcoOnclick()
        {
            if (IcoOnclick != null)
            {
                IcoOnclick(this, EventArgs.Empty);
            }
        }

        public void AppendText(string ss)
        {
            BaseText.AppendText(ss);
        }

        private void BaseText_MouseEnter(object sender, EventArgs e)
        {
            state = State.MouseOver;
            this.Invalidate();
        }

        private void BaseText_MouseLeave(object sender, EventArgs e)
        {
            state = State.Normal;
            this.Invalidate();
        }

        private void AlTextBox_MouseUp(object sender, MouseEventArgs e)
        {
            if (_Ico != null)
            {
                if (new Rectangle(_IcoPadding.Left, _IcoPadding.Top, _Ico.Width, _Ico.Height).Contains(e.X,e.Y))
                {
                    NotifyIcoOnclick();
                }
            }
        }

        private void AlTextBox_MouseEnter(object sender, EventArgs e)
        {
            state = State.MouseOver;
            this.Invalidate();
        }

        private void AlTextBox_MouseLeave(object sender, EventArgs e)
        {
            state = State.Normal;
            this.Invalidate();
        }
        #endregion
    }
}
