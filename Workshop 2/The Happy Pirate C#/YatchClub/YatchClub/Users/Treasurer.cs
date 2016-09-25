using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace YatchClub.Users
{
    class Treasurer : Member
    {
        public Treasurer(XmlNode data) : base(data)
        {

        }
    }
}
