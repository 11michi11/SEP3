using System;
using System.Collections.Generic;
using Controllers.Connections;
using Xunit;

namespace Tests
{
    public class RequestTest
    {
        [Fact]
        public void ToJSONTest()
        {
            var ar = new Dictionary<string, object> {{"searchTerm", "Tolkien"}};
            var request = new Request(Request.Operation.LibrarySearch, ar);
            string requestJSON = "{\"operation\":\"LibrarySearch\",\"args\":{\"searchTerm\":\"Tolkien\"}}";
            Console.Write(request.ToJSON());
            Assert.Equal(requestJSON, request.ToJSON());
        }
    }
}