using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace YatchClub.Helper
{
    class FileHandle
    {
        private XmlDocument doc;
        public XmlDocument Doc { get { return doc; } }

        public FileHandle(string name)
        {
            doc = new XmlDocument();
            try
            {
                doc.Load(name+"DB.xml");
            } catch
            {
                doc.AppendChild(doc.CreateXmlDeclaration("1.0", "utf-8", "no"));
                doc.AppendChild(doc.CreateElement(name + "s"));
                doc.Save(name + "DB.xml");
            }
        }

        public void CreateMember(string username, string password, string email)
        {
            XmlNode member = doc.CreateElement("member");

            XmlElement usn = doc.CreateElement("username");
            usn.InnerText = username;
            XmlElement mail = doc.CreateElement("email");
            mail.InnerText = email;
            XmlElement pass = doc.CreateElement("password");
            pass.InnerText = password;

            XmlElement name = doc.CreateElement("name");
            XmlElement id = doc.CreateElement("id");
            id.InnerText = generateId();
            XmlElement identity = doc.CreateElement("identity");
            XmlElement type = doc.CreateElement("type");
            type.InnerText = "member";
            XmlElement boats = doc.CreateElement("boats");


            member.AppendChild(usn);
            member.AppendChild(mail);
            member.AppendChild(pass);
            member.AppendChild(name);
            member.AppendChild(id);
            member.AppendChild(identity);
            member.AppendChild(type);
            member.AppendChild(boats);

        }

        private string generateId()
        {
            char[] abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890#*-_!?".ToCharArray();
            StringBuilder r = new StringBuilder();
            Random rand = new Random();
            for (int i = 0; i < 15; i++)
            {
                r.Append(abc[rand.Next(0, abc.Length)]);
            }
            string ret = r.ToString();
            if (doc.SelectSingleNode(string.Format("//id[text() = '{0}']", ret)) != null) return generateId();
            return ret;
        }
    }
}
