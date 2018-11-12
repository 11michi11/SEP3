
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
    public bool Avaliable;

    public LibraryBook(Book book, string id, bool avaliable) {
        Book = book;
        Id = id;
        Avaliable = avaliable;
        }
    
    }
}