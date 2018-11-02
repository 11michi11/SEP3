using System.Collections.Generic;

namespace Models
{
    public class BookList
    {
        private List<Book> books;

        public BookList()
        {
            books = new List<Book>();
        }

        public void addBook(Book book)
        {
            books.Add(book);
        }

        public void deleteBook(Book book)
        {
            books.Remove(book);
        }
    }
}