using System;
using System.Collections.Generic;
using System.IO;
using System.Runtime.Serialization.Json;
using System.Text;
using Models;
using Newtonsoft.Json;

namespace Controllers.Connections
{
    public class MockDatabaseProxy : IDatabaseProxy
    {
        List<Book> books = new List<Book>();

        public MockDatabaseProxy()
        {
            books.Add(new Book("Got", "Miska", 2015, "ISBN1", Category.Drama));
            books.Add(new Book("LoR", "Miska", 2015, "ISBN1", Category.Drama));
            books.Add(new Book("Blabla", "Miska", 2015, "ISBN2", Category.ScienceFiction));
            books.Add(new Book("LoR", "Tolkien", 2015, "ISBN3", Category.Fantasy));
        }

        public string Search(string searchTerm)
        {
            List<Book> tempBooks = books.FindAll(x => x.Title.Equals(searchTerm) || x.Author.Equals(searchTerm));
            string json = JsonConvert.SerializeObject(tempBooks, Formatting.Indented);
            Console.WriteLine(json);
            return json;
        }

        public string AdvancedSearch(string title, string author, int? year, string isbn, Category? category)
        {
            List<Book> tempBooks = books.FindAll(x =>
                    (title == null || x.Title.Equals(title)) && (author == null || x.Author.Equals(author)) &&
                    (year == null || x.Year.Equals(year)) && (isbn == null || x.Isbn.Equals(isbn)) &&
                    (category == null || x.Category.Equals(category)));

            string json = JsonConvert.SerializeObject(tempBooks, Formatting.Indented);
            Console.WriteLine(json);
            return json;
        }
    }
}