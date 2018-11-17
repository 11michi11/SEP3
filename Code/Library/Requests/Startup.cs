using System; 
using System.Collections.Generic; 
using System.Linq; 
using System.Threading.Tasks; 
using Microsoft.AspNetCore.Builder; 
using Microsoft.AspNetCore.Hosting; 
using Microsoft.AspNetCore.HttpsPolicy; 
using Microsoft.AspNetCore.Mvc; 
using Microsoft.AspNetCore.Routing; 
using Microsoft.Extensions.Configuration; 
using Microsoft.Extensions.DependencyInjection; 
using Microsoft.Extensions.Logging; 
using Microsoft.Extensions.Options; 

namespace Requests {
    public class Startup {
        public Startup(IConfiguration configuration) {
            Configuration = configuration; 
        }

        public IConfiguration Configuration {get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services) {
            services.AddMvc().SetCompatibilityVersion(CompatibilityVersion.Version_2_1); 
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env) {
            if (env.IsDevelopment()) {
                app.UseDeveloperExceptionPage(); 
            }
            else {
                app.UseHsts(); 
            }

            // commented in order to avoid security issues with "https"
            // app.UseHttpsRedirection();

            // route configuration for advanced search
            app.UseMvc(routes =>
            {
                routes.MapRoute
                (
                    name: "advancedSearch",
                    template: "{action}/{key}/{group}",
                    defaults: new {action = "advancedSearch"},
                    constraints: new {key = @"\d+", group = @"\d+"}
                );
                routes.MapRoute
                (
                    name: "search",
                    template: "{action}/{key}/{group}",
                    defaults: new {action = "search"},
                    constraints: new {key = @"\d+", group = @"\d+"}
                );
            });
        }
    }
}