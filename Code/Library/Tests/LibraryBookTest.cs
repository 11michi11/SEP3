using Models;
using Xunit;

namespace Tests
{
    public class LibraryBookTest
    {
        [Fact]
        public void CreateLibraryBookTest()
        {
            var book = new Book("Game of Thrones", "Martin", 2015, "isbn", Category.Fantasy);
            var detialbook = new LibraryBook(book, "id", true);
            Assert.Equal("title: Game of Thrones id: id availability: True", detialbook.ToString());
        }
    }
}