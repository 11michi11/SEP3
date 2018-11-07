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

        public void AddBook(Book book)
        {
            books.Add(book);
        }

        public void DeleteBook(Book book)
        {
            books.Remove(book);
        }

        public int Size()
        {
            return books.Count;
        }
    }
}