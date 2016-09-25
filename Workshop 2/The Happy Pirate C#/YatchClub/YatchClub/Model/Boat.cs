using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace YatchClub.Model
{
    class Boat
    {
        // private variables
        private Berth berth;
        private string type;
        private int lenght;

        //constructor, getters and setters
        public Boat(XmlElement data) {

        }

        // print out boat info
        public string toString() {
            return "";
        }
    }
}
