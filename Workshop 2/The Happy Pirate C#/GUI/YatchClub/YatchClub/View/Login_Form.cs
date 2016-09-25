using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
using YatchClub.Helper;

namespace YatchClub.View
{
    public partial class Login_Form : Form
    {
        private Form parent;
        public Login_Form(Form parent)
        {
            this.parent = parent;
            this.Show();
            parent.Hide();
            this.Closed += (s, args) => parent.Close();


            InitializeComponent();
        }

        private void back_button_Click(object sender, EventArgs e)
        {
            this.Hide();
            parent.Show();
        }

        private void login_button_Click(object sender, EventArgs e)
        {
            if (Authenticate.memberDB.Doc.SelectSingleNode(
                string.Format("//member[username[text() = '{0}'] and password[text() = '{1}']]"
                , username_textBox.Text, password_textBox.Text)) != null)
            {
                //show member menu
                MessageBox.Show("yes");
            }
            else MessageBox.Show("no");
        }
    }
}
