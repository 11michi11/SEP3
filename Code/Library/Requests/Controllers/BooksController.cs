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
        private LibraryController _libraryController = LibraryController.GetInstance();
        

        // GET api/values
        [HttpGet]
        public string Get()
        {
            return Search(searchTerm);
        }


        // GET api/values/5
        [HttpGet("{id}")]
        public ActionResult<string> Get(int id)
        {
            return "value";
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

        private string AdvanceSearch(string searchTerm)
        {
           return _libraryController.AdvanceSearch(searchTerm);
        }
    }
}
