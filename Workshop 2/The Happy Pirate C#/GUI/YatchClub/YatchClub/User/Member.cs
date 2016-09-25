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
        private string name,
                identity,
                id,
                username,
                password,
                email;



        //constructor, getters and setters
        public Member(XmlNode data)
        {
            username = data.SelectSingleNode("username").InnerText;
            password = data.SelectSingleNode("password").InnerText;
            email = data.SelectSingleNode("email").InnerText;
            name = data.SelectSingleNode("name").InnerText;
            id = data.SelectSingleNode("id").InnerText;
            identity = data.SelectSingleNode("identity").InnerText;

            XmlNodeList xmlboats = data.SelectNodes("//boats/boat");
            foreach(XmlNode boat in xmlboats)
            {
                boats.Add(new Boat(boat, this));
            }
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
