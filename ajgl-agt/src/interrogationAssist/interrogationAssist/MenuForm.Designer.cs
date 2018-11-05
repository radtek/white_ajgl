namespace interrogationAssist
{
    partial class MenuForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MenuForm));
            this.webBrowser1 = new System.Windows.Forms.WebBrowser();
            this.stocktimer = new System.Windows.Forms.Timer(this.components);
            this.stockIcon = new System.Windows.Forms.NotifyIcon(this.components);
         
            this.SuspendLayout();
         
            // 
            // stocktimer
            // 
            this.stocktimer.Interval = 400;
            this.stocktimer.Tick += new System.EventHandler(this.stocktimer_Tick);
            // 
            // stockIcon
            // 
            this.stockIcon.Icon = ((System.Drawing.Icon)(resources.GetObject("stockIcon.Icon")));
            this.stockIcon.Text = " ";
            this.stockIcon.Visible = true;
            this.stockIcon.Click += new System.EventHandler(this.flicker_Click);
        
            // 
            // MenuForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(287, 300);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "MenuForm";
            this.Text = "办案通";
            this.Deactivate += new System.EventHandler(this.MenuForm_Deactivate);
            this.Load += new System.EventHandler(this.MenuForm_Load);
            this.Shown += new System.EventHandler(this.MenuForm_Shown);
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.MenuForm_FormClosed);
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.MenuForm_FormClosing);
            this.ResumeLayout(false);

        }

        #endregion

       
        private System.Windows.Forms.Timer stocktimer;
        private System.Windows.Forms.NotifyIcon stockIcon;
    }
}