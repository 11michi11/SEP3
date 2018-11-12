
using System.Runtime.Serialization;
using Newtonsoft.Json;


namespace Models
{
    //[DataContract]
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
            return "title: " + Title + " author: " + Author + " year: " + Year + " ISBN: " + Isbn + " category: "+ Category;
            
        }

        public override bool Equals(object obj)
        {
            if (!(obj is Book)) return false;
            var other = (Book) obj;
            return Isbn.Equals(other.Isbn) && Title.Equals(other.Title) && Author.Equals(other.Author) &&
                   Year.Equals(other.Year) && Category.Equals(other.Category);
        }

        
    }
}
