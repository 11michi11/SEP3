using System;
using System.Runtime.CompilerServices;
using System.Runtime.Serialization;


namespace Models
{
    [DataContract]
    public class Book
    {
        [DataMember]
        public string Id {get;set;}
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

        public Book(string id, string title, string author, int year, string isbn, Category category)
        {
            Id = id;
            Title = title;
            Author = author;
            Year = year;
            Isbn = isbn;
            Category = category;
        }
        
        public override string ToString()
        {
            return "id: " + Id + "title: " + Title + " author: " + Author + " year: " + Year + " ISBN: " + Isbn + " category: "+ Category;
            
        }

        public override bool Equals(object obj)
        {
            if (!(obj is Book)) return false;
            var other = (Book) obj;
            return Id.Equals(other.Id) && Isbn.Equals(other.Isbn) && Title.Equals(other.Title) && Author.Equals(other.Author) &&
                   Year.Equals(other.Year) && Category.Equals(other.Category);
        }

        
    }
}
