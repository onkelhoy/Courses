using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using YatchClub.Users;

namespace YatchClub.Model
{
    class Boat
    {
        // private variables
        private Berth berth;
        private string type;
        private int lenght;

        //constructor, getters and setters
        public Boat(XmlNode data, Member member)
        {
            type = data.Attributes["type"].InnerText;
            try {
                lenght = Convert.ToInt32(data.Attributes["length"].InnerText);
            }
            catch
            {
                lenght = -1;
            }
            berth = new Berth(this, member);
        }

        // print out boat info
        public string toString()
        {
            return string.Format("Type: {0}, Length: {1}", type, lenght);
        }
    }
}
