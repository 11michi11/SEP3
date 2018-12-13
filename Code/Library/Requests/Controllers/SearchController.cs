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
    public class SearchController : ControllerBase
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
        
        // GET search?searchTerm=Tolkien
        [HttpGet]
        [Route("search")]
        public ActionResult<List<Book>> Search(string searchTerm)
        {
            if (CheckSessionKey())
            {
                return _libraryController.Search(searchTerm); 
            }
            else
            {
                return Unauthorized();
            }
        }

        // GET advancedSearch?author=Tolkien&year=2000
        [HttpGet]
        [Route("advancedSearch")]
        public ActionResult<List<Book>> AdvancedSearch(string title, string author, int? year, string isbn, Category? category) {
            if (CheckSessionKey())
            {
                return _libraryController.AdvancedSearch(title, author,year, isbn,category);
            }
            else
            {
                return Unauthorized();
            }
        }

        // GET bookDetails/isbn
        [HttpGet]
        [Route("bookDetails/{isbn}")]
        public ActionResult<string> BookDetails(string isbn)
        {
            if (CheckSessionKey())
            {
                return _libraryController.BookDetails(isbn);
            }
            else
            {
                return Unauthorized();
            }
        }
    }
}
