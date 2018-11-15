using System;
using System.Net.Http;
using Controllers.Connections;
using Xunit;
using Newtonsoft.Json;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Models;
using Controllers;

namespace Tests
{
    public class LibraryControllerTest
    {
        private readonly LibraryController _controller;

        public LibraryControllerTest()
        {
            _controller = LibraryController.GetInstance();
            _controller.SetDatabaseProxy(new MockDatabaseProxy());
        }
        
        [Fact]
        public void TestSearch()
        {
            var booksExpected = new List<Book>
            {
                new Book("LoR", "Miska", 2015, "ISBN1", Category.Drama),
                new Book("LoR", "Tolkien", 2015, "ISBN3", Category.Fantasy)
            };
           var booksActual = _controller.Search("LoR");

            Assert.Equal(booksExpected,booksActual);
        }

        [Fact]
        public void TestAdvancedSearch()
        {
            var booksExpected = new List<Book>
            {
                new Book("Got", "Miska", 2015, "ISBN1", Category.Drama),
                new Book("LoR", "Miska", 2015, "ISBN1", Category.Drama)
            };
            var author = "Miska";
            var category = Category.Drama;
                        
            var booksActual = _controller.AdvancedSearch(null,author,null,null,category);

            Assert.Equal(booksExpected,booksActual);
        }

        [Fact]
        public void TestSearchNoResult()
        {
            var booksExpected = new List<Book>();
            var searchTerm = "Michal";
                    
            var booksActual = _controller.Search(searchTerm);

            Assert.Equal(booksExpected,booksActual);
        }
        
        [Fact]
        public void TestAdvancedSearchNoResult()
        {
            var booksExpected = new List<Book>();
            var author = "Miska";
            var category = Category.Children;
                        
            var booksActual = _controller.AdvancedSearch(author,null,null,null,category);
            Assert.Equal(booksExpected,booksActual);
        }

        [Fact]
        public void TestBookDetails()
        {
        var book = new Book("Got", "Miska", 2015, "ISBN1", Category.Drama);
        var libraryBookExpected = new LibraryBook(book, "id", true);
        
        var libraryBookActual = _controller.BookDetails("id");
        
        Assert.Equal(libraryBookExpected,libraryBookActual);
        }
    }
}