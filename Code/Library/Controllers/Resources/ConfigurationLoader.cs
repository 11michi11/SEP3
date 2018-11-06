using System;
using System.Collections.Generic;
using System.IO;
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
            LoadConfigurationData("../../configuration.txt");  
        }

        public static ConfigurationLoader GetInstance()
        {
            return _instance ?? (_instance = new ConfigurationLoader());
        }

        private void LoadConfigurationData(string fileName)
        {
            try
            {
                using (var stream = new StreamReader(fileName))
                {
                    var json = stream.ReadToEnd();
                    
                    var data = JsonConvert.DeserializeObject<Dictionary<string, object>>(json);

                    DatabaseHost =  data["DatabaseHost"] as byte[];
                    DatabasePort = (int)(long) data["DatabasePort"];
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("File could not be read");
                Console.WriteLine(e);
            }
        }
        
    }
}