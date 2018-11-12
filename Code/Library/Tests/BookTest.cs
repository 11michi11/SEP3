using Models;
using Xunit;

namespace Tests
{
    public class BookTest
    {
        [Fact]
        public void CreateBookTest()
        {
            var book = new Book("LoR", "Tolkien", 1954, "isbn", Category.Fantasy);
            Assert.Equal("title: LoR author: Tolkien", book.ToString());
            Assert.NotNull(book);
        }
        
        [Fact]
        public void ToStringBookTest()
        {
            var book = new Book("LoR", "Tolkien", 1954, "isbn", Category.Fantasy);
            Assert.Equal("title: LoR author: Tolkien", book.ToString());
        }
        
        
    }
}