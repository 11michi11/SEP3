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
        public void Post([FromBody] string value)
        {
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
