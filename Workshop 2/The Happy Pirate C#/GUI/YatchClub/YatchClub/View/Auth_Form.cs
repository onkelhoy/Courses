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
using YatchClub.View;

namespace YatchClub
{
    public partial class Authenticate : Form
    {
        public Authenticate()
        {
            InitializeComponent();
            
        }

        private void login_button_Click(object sender, EventArgs e)
        {
            Login_Form login = new Login_Form(this);
        }

        private void register_button_Click(object sender, EventArgs e)
        {
            Regi_Form login = new Regi_Form(this);
        }
    }
}
