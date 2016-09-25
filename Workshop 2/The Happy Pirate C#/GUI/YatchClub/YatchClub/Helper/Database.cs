using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace YatchClub.Helper
{
    public class Database
    {
        static FileHandle memberDB, calendarDB, berthResorvationDB;

        Database()
        {
            memberDB = new FileHandle("member");
            calendarDB = new FileHandle("calendar");
            berthResorvationDB = new FileHandle("berthResorvation");
        }
    }
}
