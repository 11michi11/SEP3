using System;
using Controllers.Connections;
using Xunit;
using Xunit.Sdk;

namespace Tests
{
    public class TestDatabaseProxy
    {
        [Fact]
        public void TestSearch()
        {
            var proxy = new MockDatabaseProxy();

            var searchTerm = proxy.Search("LoR");
            var book = "[\r\n  {\r\n    \"Title\": \"LoR\",\r\n    \"Author\" ";
            
            throw new XunitException("Search returns List");
        }
    }
}
