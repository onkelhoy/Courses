using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;
using YatchClub.Helper;
using YatchClub.Users;

namespace YatchClub.View
{
    class Menu
    {
        private XmlDocument memberDB;

        public Menu()
        {
            memberDB = FileHandle.GetDocuemnt("member.xml");
            AuthenticateMenu();
        }


        private void AuthenticateMenu()
        {
            if (GetAns("=== Authentication Menu ===\n1. Login\n2. Register\n? ").Equals("1")) LoginMenu();
            else RegistrationMenu();
        }
        private void LoginMenu()
        {
            Console.Clear();
            Console.WriteLine("=== Login Menu ===");
            string username = GetAns("Username: "),
                    password = GetAns("Password: ");
            

            XmlNode memberNode = memberDB.SelectSingleNode(string.Format("//member[username[text() == '{0}'] and password[text() = '{1}']]", username, password));
            if (memberNode != null)
            {
                string type = memberNode.SelectSingleNode("type").InnerText.ToLower();
                if (type.Equals("member")) MemberMenu(memberNode);
                else if (type.Equals("secretary")) SecretaryMenu(memberNode);
                else TreasurerMenu(memberNode);
            }
            else Console.WriteLine("Username or Password is incorrect");
        }
        private void RegistrationMenu()
        {
            string username, email, password;
            Console.Clear();
            Console.Write("=== Register Menu ===\n");
            username = GetAns("Username: ");
            email = GetAns("Email: ");
            password = GetAns("Password: ");

            if(memberDB.SelectSingleNode(string.Format("//member[username[text() = '{0}'] and email[text() = '{1}']]", username, email)) == null)
            {// okay member info does not exist
                XmlNode member = memberDB.CreateElement("member");

                XmlElement usn = memberDB.CreateElement("username");
                usn.InnerText = username;
                XmlElement mail = memberDB.CreateElement("email");
                mail.InnerText = email;
                XmlElement pass = memberDB.CreateElement("password");
                pass.InnerText = password;

                XmlElement name = memberDB.CreateElement("name");
                XmlElement id = memberDB.CreateElement("id");
                id.InnerText = generateId();
                XmlElement identity = memberDB.CreateElement("identity");
                XmlElement type = memberDB.CreateElement("type");
                type.InnerText = "member";
                XmlElement boats = memberDB.CreateElement("boats");


                member.AppendChild(usn);
                member.AppendChild(mail);
                member.AppendChild(pass);
                member.AppendChild(name);
                member.AppendChild(id);
                member.AppendChild(identity);
                member.AppendChild(type);
                member.AppendChild(boats);

                memberDB.DocumentElement.AppendChild(member);
                memberDB.Save("member.xml");

                MemberMenu(member);
            }
            else
            {
                Console.Clear();
                Console.WriteLine("Sorry a member already has this username or email\nPress any key to proceed");
                Console.ReadKey();
                AuthenticateMenu(); //maybe they forgot that they had an account
            }
        }



        private void MemberMenu(XmlNode memberNode)
        {
            Member m = new Member(memberNode);
            Console.WriteLine("=== Member Menu ===\n1. Boat\n2. User information\n3. Calendar");
            string ans = GetAns("? ");

        }
        private void SecretaryMenu(XmlNode secretaryNode)
        {
            Secretary s = new Secretary(secretaryNode);
        }
        private void TreasurerMenu(XmlNode treasurerNode)
        {
            Treasurer t = new Treasurer(treasurerNode);
        }


        private string GetAns(string question)
        {
            Console.Write(question);
            return Console.ReadLine();
        }

        private string generateId()
        {
            char[] abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890#*-_!?".ToCharArray();
            StringBuilder r = new StringBuilder();
            Random rand = new Random();
            for(int i = 0; i < 15; i++)
            {
                r.Append(abc[rand.Next(0, abc.Length)]);
            }
            string ret = r.ToString();
            if (memberDB.SelectSingleNode(string.Format("//id[text() = '{0}']", ret)) != null) return generateId();
            return ret;
        }
    }
}
