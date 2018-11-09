using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Models;
using Controllers;

namespace Requests.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class BooksController : ControllerBase
    {
        private readonly LibraryController _libraryController = LibraryController.GetInstance();
       
        // GET api/books/searchTerm
        [HttpGet("{searchTerm}")]
        public ActionResult<List<Book>> Search(string searchTerm)
        {
            return _libraryController.Search(searchTerm);
        }

        [HttpGet]
        public ActionResult<List<Book>> AdvancedSearchRequest(string title, string author, int? year, string isbn, Category? category) {
            return _libraryController.AdvancedSearch(title, author,year, isbn,category);
        }

        // POST api/values
        [HttpPost]
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

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public IActionResult DeleteBook(string id)
        {
            try {
            _libraryController.DeleteBook(id);
            } catch(System.NullReferenceException ex) {
                return BadRequest("Book not found");
            }

            return Ok("Book deleted"); 
        }
    }
}
