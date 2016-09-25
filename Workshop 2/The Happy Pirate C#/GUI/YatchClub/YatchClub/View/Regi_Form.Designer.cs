namespace YatchClub.View
{
    partial class Regi_Form
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
            this.password_textBox = new System.Windows.Forms.TextBox();
            this.password_label = new System.Windows.Forms.Label();
            this.username_textBox = new System.Windows.Forms.TextBox();
            this.username_label = new System.Windows.Forms.Label();
            this.email_textBox = new System.Windows.Forms.TextBox();
            this.email_label = new System.Windows.Forms.Label();
            this.back_button = new System.Windows.Forms.Button();
            this.registrate_button = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // password_textBox
            // 
            this.password_textBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.password_textBox.Location = new System.Drawing.Point(56, 184);
            this.password_textBox.Name = "password_textBox";
            this.password_textBox.Size = new System.Drawing.Size(276, 34);
            this.password_textBox.TabIndex = 12;
            // 
            // password_label
            // 
            this.password_label.AutoSize = true;
            this.password_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.password_label.Location = new System.Drawing.Point(51, 156);
            this.password_label.Name = "password_label";
            this.password_label.Size = new System.Drawing.Size(120, 29);
            this.password_label.TabIndex = 11;
            this.password_label.Text = "Password";
            // 
            // username_textBox
            // 
            this.username_textBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.username_textBox.Location = new System.Drawing.Point(56, 95);
            this.username_textBox.Name = "username_textBox";
            this.username_textBox.Size = new System.Drawing.Size(276, 34);
            this.username_textBox.TabIndex = 10;
            // 
            // username_label
            // 
            this.username_label.AutoSize = true;
            this.username_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.username_label.Location = new System.Drawing.Point(51, 67);
            this.username_label.Name = "username_label";
            this.username_label.Size = new System.Drawing.Size(124, 29);
            this.username_label.TabIndex = 9;
            this.username_label.Text = "Username";
            // 
            // email_textBox
            // 
            this.email_textBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.email_textBox.Location = new System.Drawing.Point(55, 273);
            this.email_textBox.Name = "email_textBox";
            this.email_textBox.Size = new System.Drawing.Size(276, 34);
            this.email_textBox.TabIndex = 14;
            // 
            // email_label
            // 
            this.email_label.AutoSize = true;
            this.email_label.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.email_label.Location = new System.Drawing.Point(50, 245);
            this.email_label.Name = "email_label";
            this.email_label.Size = new System.Drawing.Size(74, 29);
            this.email_label.TabIndex = 13;
            this.email_label.Text = "Email";
            // 
            // back_button
            // 
            this.back_button.Location = new System.Drawing.Point(12, 414);
            this.back_button.Name = "back_button";
            this.back_button.Size = new System.Drawing.Size(120, 45);
            this.back_button.TabIndex = 15;
            this.back_button.Text = "Back";
            this.back_button.UseVisualStyleBackColor = true;
            this.back_button.Click += new System.EventHandler(this.back_button_Click);
            // 
            // registrate_button
            // 
            this.registrate_button.Location = new System.Drawing.Point(254, 414);
            this.registrate_button.Name = "registrate_button";
            this.registrate_button.Size = new System.Drawing.Size(120, 45);
            this.registrate_button.TabIndex = 16;
            this.registrate_button.Text = "Registrate";
            this.registrate_button.UseVisualStyleBackColor = true;
            this.registrate_button.Click += new System.EventHandler(this.registrate_button_Click);
            // 
            // Regi_Form
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(11F, 24F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.ClientSize = new System.Drawing.Size(386, 471);
            this.Controls.Add(this.registrate_button);
            this.Controls.Add(this.back_button);
            this.Controls.Add(this.email_textBox);
            this.Controls.Add(this.email_label);
            this.Controls.Add(this.password_textBox);
            this.Controls.Add(this.password_label);
            this.Controls.Add(this.username_textBox);
            this.Controls.Add(this.username_label);
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Regi_Form";
            this.Text = "Registration";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox password_textBox;
        private System.Windows.Forms.Label password_label;
        private System.Windows.Forms.TextBox username_textBox;
        private System.Windows.Forms.Label username_label;
        private System.Windows.Forms.TextBox email_textBox;
        private System.Windows.Forms.Label email_label;
        private System.Windows.Forms.Button back_button;
        private System.Windows.Forms.Button registrate_button;
    }
}