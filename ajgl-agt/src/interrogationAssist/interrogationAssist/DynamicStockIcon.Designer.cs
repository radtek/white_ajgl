
namespace interrogationAssist
{
    partial class DynamicStockIcon
    {
        /// <summary>
        /// 必需的设计器变量。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 清理所有正在使用的资源。
        /// </summary>
        /// <param name="disposing">如果应释放托管资源，为 true；否则为 false。</param>
        protected override void Dispose(bool disposing)
        {
            if(disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows 窗体设计器生成的代码

        /// <summary>
        /// 设计器支持所需的方法 - 不要
        /// 使用代码编辑器修改此方法的内容。
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(DynamicStockIcon));
            this.stockIcon = new System.Windows.Forms.NotifyIcon(this.components);
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.flicker = new System.Windows.Forms.Button();
            this.cease = new System.Windows.Forms.Button();
            this.stocktimer = new System.Windows.Forms.Timer(this.components);
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // stockIcon
            // 
            this.stockIcon.Icon = ((System.Drawing.Icon)(resources.GetObject("stockIcon.Icon")));
            this.stockIcon.Text = "notifyIcon1";
            this.stockIcon.Visible = true;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.cease);
            this.groupBox1.Controls.Add(this.flicker);
            this.groupBox1.Location = new System.Drawing.Point(27,34);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(200,100);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "操作类型";
            // 
            // flicker
            // 
            this.flicker.Location = new System.Drawing.Point(25,36);
            this.flicker.Name = "flicker";
            this.flicker.Size = new System.Drawing.Size(52,23);
            this.flicker.TabIndex = 0;
            this.flicker.Text = "闪烁";
            this.flicker.UseVisualStyleBackColor = true;
            this.flicker.Click += new System.EventHandler(this.flicker_Click);
            // 
            // cease
            // 
            this.cease.Location = new System.Drawing.Point(121,36);
            this.cease.Name = "cease";
            this.cease.Size = new System.Drawing.Size(52,23);
            this.cease.TabIndex = 1;
            this.cease.Text = "停止";
            this.cease.UseVisualStyleBackColor = true;
            this.cease.Click += new System.EventHandler(this.cease_Click);
            // 
            // stocktimer
            // 
            this.stocktimer.Interval = 400;
            this.stocktimer.Tick += new System.EventHandler(this.stocktimer_Tick);
            // 
            // DynamicStockIcon
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F,12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(261,203);
            this.Controls.Add(this.groupBox1);
            this.Name = "DynamicStockIcon";
            this.Text = "动态系统托盘图标";
            this.groupBox1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.NotifyIcon stockIcon;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Button cease;
        private System.Windows.Forms.Button flicker;
        private System.Windows.Forms.Timer stocktimer;
    }
}

