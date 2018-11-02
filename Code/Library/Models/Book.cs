using System;
using System.Runtime.CompilerServices;
using Models.obj;

namespace Models
{
    public class Book
    {
        private string title {get; set;}
        private string author {get; set;}
        private int year {get; set;}
        private string isbn {get; set;}
        private Category category {get; set;}

        public Book(string title, string author, int year, string isbn, Category category)
        {
            this.title = title;
            this.author = author;
            this.year = year;
            this.isbn = isbn;
            this.category = category;
        }

        public string toString()
        {
            return "title: " + title + "author: " + author + "year: " + year + "ISBN: " + isbn + "category: "+ category;
        }
    }
}
