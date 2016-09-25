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
        public static XmlDocument GetDocuemnt(string name)
        {
            XmlDocument xmlDoc = new XmlDocument();
            try
            {
                xmlDoc.Load(name);
            }
            catch
            {
                System.Reflection.Assembly a = System.Reflection.Assembly.GetExecutingAssembly();
                try
                {
                    xmlDoc.Load(a.GetManifestResourceStream("YatchClub.Content." + name));
                }
                catch (Exception)
                {
                    Console.WriteLine("\n========== ERROR ==========\n");
                    Console.WriteLine("xml file-name is not correct!");
                }
            }

            return xmlDoc;
        }
    }
}
