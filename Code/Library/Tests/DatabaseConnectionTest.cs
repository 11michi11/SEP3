using System;
using System.Collections.Generic;
using Controllers.Connections;
using Models;
using Xunit;

namespace Tests
{
    public class DatabaseConnectionTest : DatabaseConnection
    {
        //Run only with DB running
        [Fact]
        public void SearchTest()
        {
            var list = new List<Book>
            {
                //id not checked in db, just put in order to build
                new Book("Lord of the Rings: Fellowship of the Ring", "J.R.R Tolkien", 1954, "978-83-8116-1",
                    Category.Fantasy)
            };
            Assert.Equal(list, Search("Tolkien"));
        }
        
        [Fact]
        public void AdvancedSearchTest()
        {
            var list = new List<Book>
            {
                //id not checked in db, just put in order to build
                new Book("Lord of the Rings: Fellowship of the Ring", "J.R.R Tolkien", 1954, "978-83-8116-1",
                    Category.Fantasy)
            };
            Assert.Equal(list, AdvancedSearch("Lord", "author", 0, "", Category.Children));
        }
        
        [Fact]
        public void GetResponsStatusTest()
        {
            string response =
                "{\"status\":\"OK\",\"content\":[{\"isbn\":\"978-83-8116-1\",\"title\":\"Lord of the Rings: Fellowship of the Ring\",\"author\":\"J.R.R Tolkien\",\"year\":1954,\"category\":\"Fantasy\"}]}";
            Assert.Equal(GetResponseStatus(response),ResponseStatus.OK);
        }
        
        [Fact]
        public void GetContentTestListOfBooks()
        {
            var list = new List<Book>
            {
                //id not checked in db, just put in order to build
                new Book("Lord of the Rings: Fellowship of the Ring", "J.R.R Tolkien", 1954, "978-83-8116-1",
                    Category.Fantasy)
            };
            string response =
                "{\"status\":\"OK\",\"content\":[{\"isbn\":\"978-83-8116-1\",\"title\":\"Lord of the Rings: Fellowship of the Ring\",\"author\":\"J.R.R Tolkien\",\"year\":1954,\"category\":\"Fantasy\"}]}";
            Assert.Equal(GetContent<List<Book>>(response),list);
        }
        
        [Fact]
        public void GetContentTestError()
        {
            var msg = "Error";
            string response =
                "{\"status\":\"Error\",\"content\":\"Error\"}";
            Assert.Equal(GetContent<string>(response),msg);
        }

        [Fact]
        public void AddBookTest()
        {
            var book = new Book("newTitle","newAuthor",2017,"newIsbn", Category.Science);
            CreateBook(book);
        }
        
        [Fact]
        public void DeleteBookTest()
        {
           DeleteBook("fe993ec5-6d7b-4244-aa7d-0665b64e0e37");
        }

        [Fact]
        public void BookDetailsTest()
        {
            var json = "{\"book\":{\"isbn\":\"978-83-246-7758-0\",\"title\":\"Core Java\",\"author\":\"Cay S. Horstmann, Gary Cornell\",\"year\":2014,\"category\":\"Science\"},\"libraryBooks\":[{\"bookid\":\"196690e8-d620-49cb-b404-d049bd25b6de\",\"available\":true},{\"bookid\":\"efea4877-ff0a-44f6-96da-2d9294428c79\",\"available\":true}]}";
            Assert.Equal(json, BookDetails("978-83-246-7758-0"));
        }
    }
}