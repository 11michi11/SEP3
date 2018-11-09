using System;
using System.Net.Http;
using Controllers.Connections;
using Xunit;
using Newtonsoft.Json;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Models;
using System.Text;

namespace Tests
{
    public class BooksControllerTest
    {
        private readonly HttpClient _client;

        public BooksControllerTest()
        {
           _client = new HttpClient();

        }
        
        [Fact]
        public void TestSearch()
        {
            var books = new List<Book>
            {
                new Book("id2","LoR", "Miska", 2015, "ISBN1", Category.Drama),
                new Book("id4","LoR", "Tolkien", 2015, "ISBN3", Category.Fantasy)
            };
            var searchTerm = "LoR";
            var str = _client.GetStringAsync($"http://localhost:5000/api/books/{searchTerm}").GetAwaiter().GetResult();
            
            var booksFromService = JsonConvert.DeserializeObject<List<Book>>(str);

            Assert.Equal(books,booksFromService);
        }

        [Fact]
        public void TestAdvancedSearch()
        {
            var books = new List<Book>
            {
                new Book("id1","Got", "Miska", 2015, "ISBN1", Category.Drama),
                new Book("id2","LoR", "Miska", 2015, "ISBN1", Category.Drama)
            };
            var author = "Miska";
            var category = Category.Drama;
            
            var str = _client.GetStringAsync($"http://localhost:5000/api/books?author={author}&category={category}").GetAwaiter().GetResult();
            
            var booksFromService = JsonConvert.DeserializeObject<List<Book>>(str);

            Assert.Equal(books,booksFromService);
        }

        [Fact]
        public void TestSearchNoResult()
        {
            var books = new List<Book>();
            var searchTerm = "Michal";
            var str = _client.GetStringAsync($"http://localhost:5000/api/books/{searchTerm}").GetAwaiter().GetResult();
            
            var booksFromService = JsonConvert.DeserializeObject<List<Book>>(str);

            Assert.Equal(books,booksFromService);
        }
        
        [Fact]
        public void TestAdvancedSearchNoResult()
        {
            var books = new List<Book>();
            var author = "Miska";
            var category = Category.Children;
            
            var str = _client.GetStringAsync($"http://localhost:5000/api/books?author={author}&category={category}").GetAwaiter().GetResult();
            
            var booksFromService = JsonConvert.DeserializeObject<List<Book>>(str);

            Assert.Equal(books,booksFromService);
        }

        [Fact]
        public void TestCreateBook()
        {
        var book = new Book(null,"HarryPotter","Daniel",2000,"ISBN 2",Category.Science);
        var json = JsonConvert.SerializeObject(book);
        var httpContent = new StringContent(json,Encoding.UTF7,"application/json");
        var str = _client.PostAsync("http://localhost:5000/api/books",httpContent);
        
        Assert.Equal("Book created succesfully",str.ToString());
        }
    }
}
