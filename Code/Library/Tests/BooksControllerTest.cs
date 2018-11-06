using System;
using System.Net.Http;
using Controllers.Connections;
using Xunit;
using Newtonsoft.Json;
using System.Collections.Generic;
using Models;

namespace Tests
{
    public class BooksControllerTest
    {
        [Fact]
        public void TestSearch()
        {
            var books = new List<Book>();
            books.Add(new Book("LoR", "Miska", 2015, "ISBN1", Category.Drama));
            books.Add(new Book("LoR", "Tolkien", 2015, "ISBN3", Category.Fantasy));

            HttpClient client = new HttpClient();
            var searchTerm = "LoR";

            // var str = await client.GetStringAsync($"http://localhost:5000/api/books/{searchTerm}").ToString();
            
            // var booksFromService = JsonConvert.DeserializeObject<List<Book>>(str);

            // Assert.Equal(books,booksFromService);
        }
    }
}
