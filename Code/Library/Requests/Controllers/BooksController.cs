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
        

        // GET api/values
        [HttpGet]
        public string Get()
        {
            throw new NotImplementedException();
            // return Search(searchTerm);
        }


        // GET api/values/searchTerm
        [HttpGet("{searchTerm}")]
        public ActionResult<string> Get(string searchTerm)
        {
            return Search(searchTerm);
        }
        
        // GET api/values/title/author/year/isbn/category
        [HttpGet("{title}/{author}/{year}/{isbn}")]
        public ActionResult<string> Get(string title, string author, int year, string isbn)
        {
            return AdvancedSearch(title, author,year, isbn);
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
        
        private string Search(string searchTerm)
        {
           return _libraryController.Search(searchTerm);
        }

        private string AdvancedSearch(string title, string author, int year, string isbn)
        { 
           return _libraryController.AdvancedSearch(title, author,year, isbn);
        }
    }
}
