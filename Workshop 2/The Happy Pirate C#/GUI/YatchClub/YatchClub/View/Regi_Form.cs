using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using YatchClub.Helper;

namespace YatchClub.View
{
    public partial class Regi_Form : Form
    {
        private Form parent;
        public Regi_Form(Form parent)
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

        private void registrate_button_Click(object sender, EventArgs e)
        {
            string username = username_textBox.Text;
            string password = password_textBox.Text;
            string mail = email_textBox.Text;

            Authenticate.memberDB.CreateMember(username, password, mail);
        }
    }
}
