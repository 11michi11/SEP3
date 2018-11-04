using System;
using Controllers.Connections;
using Xunit;

namespace Tests
{
    public class TestDatabaseProxy
    {
        [Fact]
        public void TestSearch()
        {
            var proxy = new MockDatabaseProxy();

            string searchTerm = proxy.Search("LoR");
            var book = "[\r\n  {\r\n    \"Title\": \"LoR\",\r\n    \"Author\" ";
            Assert.Equal(book, searchTerm);
        }
    }
}
