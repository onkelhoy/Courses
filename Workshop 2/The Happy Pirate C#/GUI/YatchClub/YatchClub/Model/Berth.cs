using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using YatchClub.Users;

namespace YatchClub.Model
{
    class Berth
    {
        private int fee; // the cost of berth

        public Berth(Boat boat, Member member)
        {
            // create a berth based on boat and have the members info
        }

        public Berth(XmlElement data)
        {
            // create a berth based on data (secretary)
        }
    }
}
