using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Models;
using Controllers;
using Newtonsoft.Json;

namespace Requests.Controllers
{
    [ApiController]
    public class BooksController : ControllerBase
    {
        private readonly LibraryController _libraryController = LibraryController.GetInstance();
       
        // GET search?searchTerm=Tolkien
        [HttpGet]
        [Route("search")]
        public ActionResult<List<Book>> Search(string searchTerm)
        {
            return _libraryController.Search(searchTerm);
        }

        // GET advancedSearch?year=2000
        [HttpGet]
        [Route("advancedSearch")]
        public ActionResult<List<Book>> AdvancedSearch(string title, string author, int? year, string isbn, Category? category) {
            return _libraryController.AdvancedSearch(title, author,year, isbn,category);
        }

        // GET bookDetails/isbn
        [HttpGet]
        [Route("bookDetails/{isbn}")]
        public ActionResult<string> BookDetails(string isbn) {
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
