using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YatchClub.Model;
using YatchClub.Users;

namespace YatchClub.View
{
    interface iMenu
    {
        string AuthenticateField(); // Login fields
        string[] LoginField();      // username and password
        string RegistrationField();

        string MemberField(Member member);
        string SecretaryField(Secretary secretary);
        string TreasurerField(Treasurer treasurer);

        string BoatField(Boat boat);
    }
}
