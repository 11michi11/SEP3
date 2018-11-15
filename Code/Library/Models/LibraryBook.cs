
using System.Runtime.Serialization;

namespace Models
{
    [DataContract]
    public class LibraryBook{
        
        [DataMember]
        public Book Book;
        [DataMember]
        public string Id;
        [DataMember]
        public bool Available;
    
        public LibraryBook(Book book, string id, bool available) {
            Book = book;
            Id = id;
            Available = available;
        }

        public override string ToString()
        {
            //return $"BookId: {Id}\n{Book}\nAvailable: {Available}";
            return "title: " + Book.Title + " id: " + Id + " availability: " + Available;
        }

        public override bool Equals(object obj)
        {
            if (!(obj is LibraryBook)) return false;
            var other = (LibraryBook) obj;
            return Book.Equals(other.Book) && Id.Equals(other.Id) && Available == other.Available;
        }
    }
}