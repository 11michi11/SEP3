using System.IO;
using Controllers.Resources;
using Xunit;

namespace Tests
{
    public class ConfigurationLoaderTest
    {
        [Fact]
        public void LoadConfigurationTest()
        {
            // loading the instance of loader .. from constructor there is a bad path to file coz it is a path from "ConfigurationLoader" class
            var loader = ConfigurationLoader.GetInstance();
            // getting the correct path from this test
            var path = Directory.GetParent(Directory.GetParent(Directory.GetParent(Directory.GetParent(System.IO.Directory.GetCurrentDirectory()).ToString()).ToString()).ToString());
            loader.LoadConfigurationData(path+"/configuration.txt");
            
            var host = new byte[] {127, 0, 0, 1};
            var port = 7777;
            
            Assert.Equal(host,loader.DatabaseHost);
            Assert.Equal(port,loader.DatabasePort);
        }
    }
}