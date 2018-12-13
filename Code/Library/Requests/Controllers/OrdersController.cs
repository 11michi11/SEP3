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
    public class OrdersController : ControllerBase
    {
        private readonly LibraryController _libraryController = LibraryController.GetInstance();
       
        private bool CheckSessionKey()
        {
            var sessionKeyFromClient = Request.Cookies["sessionKey"];
            try
            {
                if (sessionKeyFromClient != null && SessionKeyManager.IsSkValid(sessionKeyFromClient))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            catch (SessionKeyInvalidException e)
            {
                return false;
            }
        }
        
        // DELETE orders/orderId
        [HttpDelete]
        [Route("orders/{orderId}")]
        public IActionResult ReturnBook(string orderId)
        {
            if (CheckSessionKey())
            {
                try {
                    _libraryController.ReturnBook(orderId);
                } catch(NullReferenceException ex) {
                    return BadRequest("Order not found");
                }

                return Ok("The book has been returned successfully"); 
            }
            else
            {
                return Unauthorized();
            } 
        }
        
        // GET orders
        [HttpGet]
        [Route("orders")]
        public ActionResult<string> GetOrders()
        {
            if (CheckSessionKey())
            {
                return _libraryController.GetOrders(); 
            }
            else
            {
                return Unauthorized();
            }
        }
    }
}
