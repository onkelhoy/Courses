namespace YatchClub
{
    partial class Authenticate
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
            this.login_button = new System.Windows.Forms.Button();
            this.register_button = new System.Windows.Forms.Button();
            this.Authenticate_label = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // login_button
            // 
            this.login_button.Location = new System.Drawing.Point(93, 200);
            this.login_button.Name = "login_button";
            this.login_button.Size = new System.Drawing.Size(252, 55);
            this.login_button.TabIndex = 0;
            this.login_button.Text = "Login";
            this.login_button.UseVisualStyleBackColor = true;
            this.login_button.Click += new System.EventHandler(this.login_button_Click);
            // 
            // register_button
            // 
            this.register_button.Location = new System.Drawing.Point(93, 289);
            this.register_button.Name = "register_button";
            this.register_button.Size = new System.Drawing.Size(252, 55);
            this.register_button.TabIndex = 1;
            this.register_button.Text = "Register";
            this.register_button.UseVisualStyleBackColor = true;
            this.register_button.Click += new System.EventHandler(this.register_button_Click);
            // 
            // Authenticate_label
            // 
            this.Authenticate_label.AutoSize = true;
            this.Authenticate_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Authenticate_label.Location = new System.Drawing.Point(132, 70);
            this.Authenticate_label.Name = "Authenticate_label";
            this.Authenticate_label.Size = new System.Drawing.Size(175, 32);
            this.Authenticate_label.TabIndex = 2;
            this.Authenticate_label.Text = "Authenticate";
            // 
            // Authenticate
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(11F, 24F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.ClientSize = new System.Drawing.Size(431, 429);
            this.Controls.Add(this.Authenticate_label);
            this.Controls.Add(this.register_button);
            this.Controls.Add(this.login_button);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Authenticate";
            this.Text = "Authenticate";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button login_button;
        private System.Windows.Forms.Button register_button;
        private System.Windows.Forms.Label Authenticate_label;
    }
}

