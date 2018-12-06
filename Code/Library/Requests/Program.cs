using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Controllers;
using Controllers.Connections;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;

namespace Requests
{
    public class Program
    {
        public static void Main(string[] args)
        {
            // temporarilly used mocked class
            LibraryController.GetInstance().SetDatabaseProxy(new DatabaseProxy());
//            LibraryController.GetInstance().SetDatabaseProxy(new MockDatabaseProxy());
            CreateWebHostBuilder(args).Build().Run();
//            var msg = SessionKeyManager.IsSkValid("3b89713c-b3f7-4e78-845b-367f0a038f67");
//            Console.Write(msg);
        }

        public static IWebHostBuilder CreateWebHostBuilder(string[] args) =>
            WebHost.CreateDefaultBuilder(args)
                .UseStartup<Startup>();
    }
}
