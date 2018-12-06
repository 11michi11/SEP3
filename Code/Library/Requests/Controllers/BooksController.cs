using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Runtime.InteropServices;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Models;
using Controllers;
using Newtonsoft.Json;
using Newtonsoft.Json.Schema;

namespace Requests.Controllers
{
    [ApiController]
    public class BooksController : ControllerBase
    {
        private readonly LibraryController _libraryController = LibraryController.GetInstance();
       
        // GET search?searchTerm=Tolkien
        [HttpGet]
        [Route("cookies")]
        public ActionResult<string> Cookies()
        {
            var cookies = Request.Cookies;
            var clientCookieValue = Request.Cookies["client_cookie"];
//            var clientCookie = Request.Headers["client_cookie"].SingleOrDefault();
//            string clientCookieValue = clientCookie["client_cookie"].Value;
//            
//            HttpResponseMessage response = Request.CreateResponse("Client cookie said - " + clientCookieValue);
//
            return Ok("Your cookie value: " + clientCookieValue);
        }
        
        // GET search?searchTerm=Tolkien
        [HttpGet]
        [Route("search")]
        public ActionResult<List<Book>> Search(string searchTerm)
        {
            return _libraryController.Search(searchTerm);
        }

        // GET advancedSearch?author=Tolkien&year=2000
        [HttpGet]
        [Route("advancedSearch")]
        public ActionResult<List<Book>> AdvancedSearch(string title, string author, int? year, string isbn, Category? category) {
            return _libraryController.AdvancedSearch(title, author,year, isbn,category);
        }

        // GET bookDetails/isbn
        [HttpGet]
        [Route("bookDetails/{isbn}")]
        public ActionResult<string> BookDetails(string isbn)
        {
            //get session key from cookie
            Console.WriteLine(SessionKeyManager.IsSkValid("dd382d29-1cb5-4e34-baac-e7aa79cb0d51"));
            return _libraryController.BookDetails(isbn);
        }


        // POST book
        [HttpPost]
        [Route("book")]
        public IActionResult CreateBook([FromBody] Book book)
        {
            //for checking if book can be created
            if(!ModelState.IsValid) {
                return BadRequest(ModelState);
            }
            _libraryController.CreateBook(book);
            return Ok("Book created successfully");
            // return CreatedAtRoute("GetBook", new {id = book.Id}, book);
        }

        // DELETE book/id
        [HttpDelete]
        [Route("book/{id}")]
        public IActionResult DeleteBook(string id)
        {
            try {
            _libraryController.DeleteBook(id);
            } catch(NullReferenceException ex) {
                return BadRequest("Book not found");
            }

            return Ok("Book deleted"); 
        }
    }
}
