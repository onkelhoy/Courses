using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YatchClub.Model;
using YatchClub.Users;

namespace YatchClub.View
{
    class EngMenu : iMenu
    {
        // login and registration fields
        public string AuthenticateField() {
            return "=== Authentication ===\n1. Login\n2. Register\n?: ";
        }
        public string RegistrationField() {
            return "=== Register Menu ===\n1. Username\n2. Email\n3. Password\n";
        }
        public string[] LoginField() {
            Console.WriteLine("=== Login Menu ===");
            return new string[] { "Username", "Password" };
        }



        public string MemberField(Member member)
        {
            throw new NotImplementedException();
        }

        public string SecretaryField(Secretary secretary)
        {
            throw new NotImplementedException();
        }

        public string TreasurerField(Treasurer treasurer)
        {
            throw new NotImplementedException();
        }

        public string BoatField(Boat boat)
        {
            throw new NotImplementedException();
        }
    }
}
