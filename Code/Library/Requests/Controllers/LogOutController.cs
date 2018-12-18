using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Runtime.InteropServices;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Models;
using Controllers;
using Microsoft.AspNetCore.Cors;
using Newtonsoft.Json;
using Newtonsoft.Json.Schema;

namespace Requests.Controllers
{
    [EnableCors("AllowSpecificOrigin")]
    [ApiController]
    public class LogOutController : ControllerBase
    {
    
        // DELETE logOut
        [HttpDelete]
        [Route("logOut")]
        public IActionResult LogOut()
        {
            const string key = "sessionKey";
            var cookie = new Cookie(key, Request.Cookies[key]) {Domain = "localhost"};
            SessionKeyManager.LogOut(cookie);
            return Ok("Logged out");
        }
    }
}
