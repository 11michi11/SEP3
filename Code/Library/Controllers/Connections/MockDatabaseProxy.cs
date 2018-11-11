using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.Serialization.Json;
using System.Text;
using Controllers.Resources;
using Models;
using Newtonsoft.Json;

namespace Controllers.Connections
{
    public class MockDatabaseProxy : IDatabaseProxy
    {
        List<Book> books = new List<Book>();
        // only for manual testing file path to configuration.txt file, can be deleted
        private readonly byte[] HOST = ConfigurationLoader.GetInstance().DatabaseHost;
        private readonly int PORT = ConfigurationLoader.GetInstance().DatabasePort;

        public MockDatabaseProxy()
        {
            books.Add(new Book("id1","Got", "Miska", 2015, "ISBN1", Category.Drama));
            books.Add(new Book("id2","LoR", "Miska", 2015, "ISBN1", Category.Drama));
            books.Add(new Book("id3","HarryPotter", "Miska", 2015, "ISBN2", Category.ScienceFiction));
            books.Add(new Book("id4","LoR", "Tolkien", 2015, "ISBN3", Category.Fantasy));
            books.Add(new Book("id5","BookToDelete","Unknown",2222,"ISBN 1-1-1",Category.Children));
        }

        public List<Book> Search(string searchTerm)
        {
            List<Book> tempBooks = books.FindAll(x => x.Title.Equals(searchTerm) || x.Author.Equals(searchTerm));
            string json = JsonConvert.SerializeObject(tempBooks, Formatting.Indented);
            Console.WriteLine(json);
            return tempBooks;
        }

        public List<Book> AdvancedSearch(string title, string author, int? year, string isbn, Category? category)
        {
            List<Book> tempBooks = books.FindAll(x =>
                    (title == null || x.Title.Equals(title)) && (author == null || x.Author.Equals(author)) &&
                    (year == null || x.Year.Equals(year)) && (isbn == null || x.Isbn.Equals(isbn)) &&
                    (category == null || x.Category.Equals(category)));

            string json = JsonConvert.SerializeObject(tempBooks, Formatting.Indented);
            Console.WriteLine(json);
            return tempBooks;
        }

        public void CreateBook(Book book)
        {
            books.Add(book);
        }

        public void DeleteBook(string id)
        {
            var book = books.Find(b => b.Id.Equals(id)); 
            if(book!=null) {
                books.Remove(book);
            } else {
                throw new System.NullReferenceException("Book not found.");
            }         
           
        }
    }
}