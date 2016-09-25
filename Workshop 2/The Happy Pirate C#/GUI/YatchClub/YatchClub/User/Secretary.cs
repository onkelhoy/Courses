using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using YatchClub.Model;

namespace YatchClub.Users
{
    class Secretary : Member
    {
        private List<Berth> berthAssignments = new List<Berth>();
        public Secretary(XmlNode data) : base(data)
        {

        }
    }
}
