using System;
using System.Runtime.CompilerServices;
using System.Runtime.Serialization;


namespace Models
{
    [DataContract]
    public class Book
    {
        [DataMember]
        public string Title {get; set;}
        [DataMember]
        public string Author {get; set;}
        [DataMember]
        public int Year {get; set;}
        [DataMember]
        public string Isbn {get; set;}
        [DataMember]
        public Category Category {get; set;}

        public Book(string title, string author, int year, string isbn, Category category)
        {
            Title = title;
            Author = author;
            Year = year;
            Isbn = isbn;
            Category = category;
        }
        
        public override string ToString()
        {
            return "title: " + Title + " author: " + Author;
            //+ "author: " + Author + "year: " + Year + "ISBN: " + Isbn + "category: "+ Category
        }
    }
}
