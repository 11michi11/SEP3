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
    public class BooksController : ControllerBase
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

        // POST book
        [HttpPost]
        [Route("book")]
        public IActionResult CreateBook([FromBody] Book book)
        {
            if (CheckSessionKey())
            {
                //for checking if book can be created
                if(!ModelState.IsValid) {
                    return BadRequest(ModelState);
                }
                _libraryController.CreateBook(book);
                return Ok("Book created successfully");
            }
            else
            {
                return Unauthorized();
            }
        }

        // DELETE book/id
        [HttpDelete]
        [Route("book/{id}")]
        public IActionResult DeleteBook(string id)
        {
            if (CheckSessionKey())
            {
                try {
                    _libraryController.DeleteBook(id);
                } catch(NullReferenceException ex) {
                    return BadRequest("Book not found");
                }

                return Ok("Book deleted"); 
            }
            else
            {
                return Unauthorized();
            }   
        }
    }
}
