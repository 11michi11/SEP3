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
//            Console.Write("Waiting");
//            System.Threading.Thread.Sleep(5000);
//            var msg = SessionKeyManager.IsSkValid("74e1f8f3-73f9-41d0-bce8-6efa9f704acd");
//            Console.Write(msg);
        }

        public static IWebHostBuilder CreateWebHostBuilder(string[] args) =>
            WebHost.CreateDefaultBuilder(args)
                .UseStartup<Startup>();
    }
}
