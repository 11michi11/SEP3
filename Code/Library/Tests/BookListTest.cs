using System;
using Models;
using Xunit;

namespace Tests
{
    public class BookListTest
    {
        [Fact]
        public void CreateBookListAddBookTest()
        {
            var booklist = new BookList();
            var book = new Book("ID1","Book", "Author", 2000, "isbn", Category.Drama);
            booklist.AddBook(book);
            Assert.Equal(1, booklist.Size());
        }

        [Fact]
        public void DeleteBookFromListTest()
        {
            var booklist = new BookList();
            var book = new Book("ID1","Book", "Author", 2000, "isbn", Category.Drama);
            booklist.DeleteBook(book);
            Assert.Equal(0, booklist.Size());
        }

        [Fact]
        public void SizeOfBookListTest()
        {
            var booklist = new BookList();
            var book = new Book("ID1","Book", "Author", 2000, "isbn", Category.Drama);
            var booktwo = new Book("ID2","Book", "Author", 2000, "isbn", Category.Drama);
            var bookthree = new Book("ID3","Book", "Author", 2000, "isbn", Category.Drama);
            booklist.AddBook(book);
            booklist.AddBook(booktwo);
            booklist.AddBook(bookthree);
            Assert.Equal(3, booklist.Size());
        }
        
        
    }
}