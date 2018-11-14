using System;
using System.Net.Http;
using Controllers.Connections;
using Xunit;
using Newtonsoft.Json;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Models;


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
                new Book("LoR", "Miska", 2015, "ISBN1", Category.Drama),
                new Book("LoR", "Tolkien", 2015, "ISBN3", Category.Fantasy)
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
                new Book("Got", "Miska", 2015, "ISBN1", Category.Drama),
                new Book("LoR", "Miska", 2015, "ISBN1", Category.Drama)
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
    }
}