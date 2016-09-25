using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using YatchClub.Model;

namespace YatchClub.Users
{
    class Member
    {
        //private variables
        private List<Boat> boats = new List<Boat>();
        private string firstname,
                lastname,
                personnumber,
                id,
                email;



        //constructor, getters and setters
        public Member(XmlNode data)
        {

        }
        public string getId() { return id; }


        // printing out info
        public string verboseInfo()
        {
            return "";
        }
        public string compactInfo()
        {
            return "";
        }
    }
}
