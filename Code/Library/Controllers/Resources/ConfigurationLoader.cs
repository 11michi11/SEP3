using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using Newtonsoft.Json;

namespace Controllers.Resources
{
    public class ConfigurationLoader
    {
        private static ConfigurationLoader _instance;

        public byte[] DatabaseHost { get; set; }
        public int DatabasePort { get; set; }
        private ConfigurationLoader()
        {
            var path = Directory.GetParent(System.IO.Directory.GetCurrentDirectory()).ToString();
            LoadConfigurationData(path+"/configuration.txt");  
        }

        public static ConfigurationLoader GetInstance()
        {
            return _instance ?? (_instance = new ConfigurationLoader());
        }

        public void LoadConfigurationData(string fileName)
        {
            try
            {
                using (var stream = new StreamReader(fileName))
                {
                    var json = stream.ReadToEnd();

                    var data = JsonConvert.DeserializeObject<Dictionary<string, object>>(json);

                    DatabaseHost = FromStringToByteArray(data["DatabaseHost"].ToString());
                    DatabasePort = (int) (long) data["DatabasePort"];

                    stream.Close();
                }
            }
            catch (FileNotFoundException e)
            {
                Console.WriteLine("File was not found");
                Console.WriteLine(e);
            }
            catch (Exception ex)
            {
                Console.WriteLine("File could not be read");
                Console.WriteLine(ex);            }
        }

        private byte[] FromStringToByteArray(string text)
        {
            byte[] byteArray = text.Trim('[', ']')
                .Split(',')
                .Select(x => byte.Parse(x))
                .ToArray();

            return byteArray;
        }
        
    }
}