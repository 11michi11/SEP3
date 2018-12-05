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
            wait
            var msg = SessionKeyManager.IsSkValid("bb1551aa-f498-4a17-8b58-f25b3da19a64");
            Console.Write(msg);
        }

        public static IWebHostBuilder CreateWebHostBuilder(string[] args) =>
            WebHost.CreateDefaultBuilder(args)
                .UseStartup<Startup>();
    }
}
