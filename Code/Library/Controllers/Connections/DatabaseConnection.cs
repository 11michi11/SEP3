using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Text.RegularExpressions;
using Controllers.Resources;
using Models;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace Controllers.Connections
{
    public class DatabaseConnection : IDatabaseProxy
    {
        private readonly byte[] HOST = ConfigurationLoader.GetInstance().DatabaseHost;
        private readonly int PORT = ConfigurationLoader.GetInstance().DatabasePort;
        private readonly string LIBRARY_ID = ConfigurationLoader.GetInstance().LibraryID;

        public List<Book> Search(string searchTerm)
        {
            var ar = new Dictionary<string, object> {{"searchTerm", searchTerm}, {"libraryid", LIBRARY_ID}};
            var request = new Request(Request.Operation.LibrarySearch, ar);

            Console.WriteLine($"Sending request: '{searchTerm}'");
            var response = SendMessage(request);

            var status = GetResponseStatus(response);
            return HandleSearchResponse(response, status);
        }

        public List<Book> AdvancedSearch(string title, string author, int? year, string isbn, Category? category)
        {
            var ar = new Dictionary<string, object>
            {
                {"title", title}, {"author", author}, {"year", year}, {"isbn", isbn}, {"category", category},
                {"libraryid", LIBRARY_ID}
            };
            var request = new Request(Request.Operation.LibraryAdvancedSearch, ar);

            Console.WriteLine($"Sending request: '{request.ToJSON()}'");
            var response = SendMessage(request);

            var status = GetResponseStatus(response);
            return HandleSearchResponse(response, status);
        }

        private List<Book> HandleSearchResponse(string response, ResponseStatus status)
        {
            switch (status)
            {
                case ResponseStatus.OK:
                    return GetContent<List<Book>>(response);
                case ResponseStatus.Error:
                    String errorMsg = GetContent<string>(response);
                    throw new SearchException("Database returned error: " + errorMsg);
                default:
                    throw new SearchException("Unknown response status: " + status);
            }
        }

        protected static T GetContent<T>(string response)
        {
            return JObject.Parse(response)["content"].ToObject<T>();
        }

        protected static string GetContent(string response)
        {
            var content = JObject.Parse(response)["content"].ToString();
            content = Regex.Replace(content, @"\r\n  ", "");
            content = Regex.Replace(content, @"\r\n", "");
            content = Regex.Replace(content, @"  ", "");
            content = Regex.Replace(content, @"   ", "");
            content = Regex.Replace(content, "\": \"", "\":\"");
            content = Regex.Replace(content, "\": {", "\":{");
            content = Regex.Replace(content, "\": ", "\":");
            return content;
        }
        
        protected static ResponseStatus GetResponseStatus(string json)
        {
            var jToken = JObject.Parse(json)["status"];
            Enum.TryParse(jToken.Value<string>(), out ResponseStatus status);
            return status;
        }

        private string SendMessage(Request request)
        {
            // Data buffer for incoming data.  
            var toSend = Encoding.ASCII.GetBytes(request.ToJSON() + "\n");

            var bytes = new byte[10240];
            TcpClient client = new TcpClient("127.0.0.1", 7777);
            NetworkStream ns = client.GetStream();

            ns.Write(toSend, 0, toSend.Length);

            var bytesRec = ns.Read(bytes, 0, bytes.Length);
            var response = Encoding.ASCII.GetString(bytes, 0, bytesRec);
            return response;
        }

        public void CreateBook(Book book)
        {
            var ar = new Dictionary<string, object>
                {{"library", true}, {"id", LIBRARY_ID}, {"book", book}};

            var request = new Request(Request.Operation.AddBook, ar);

            Console.WriteLine($"Sending request: '{request.ToJSON()}'");
            var response = SendMessage(request);

            var status = GetResponseStatus(response);
            Console.Write(status);
        }

        public void DeleteBook(string bookid)
        {
            var ar = new Dictionary<string, object>
                {{"library", true}, {"id", LIBRARY_ID}, {"bookid", bookid}};

            var request = new Request(Request.Operation.DeleteBook, ar);

            Console.WriteLine($"Sending request: '{request.ToJSON()}'");
            var response = SendMessage(request);

            var status = GetResponseStatus(response);
            Console.Write(status);
        }

        public string BookDetails(string isbn)
        {
            var ar = new Dictionary<string, object>
                {{"libraryid", LIBRARY_ID}, {"isbn", isbn}};

            var request = new Request(Request.Operation.LibraryBookDetails, ar);

            Console.WriteLine($"Sending request: '{request.ToJSON()}'");
            var response = SendMessage(request);

            var status = GetResponseStatus(response);
            Console.WriteLine(response);
            return GetContent(response);
        }

        public void ReturnBook(string orderId)
        {
            var ar = new Dictionary<string, object>
                {{"orderid", orderId}};

            var request = new Request(Request.Operation.ReturnBook, ar);

            Console.WriteLine($"Sending request: '{request.ToJSON()}'");
            var response = SendMessage(request);

            var status = GetResponseStatus(response);
            Console.Write(status);
        }

        public string GetOrders()
        {
            var ar = new Dictionary<string, object>
                {{"libraryid", LIBRARY_ID}};

            var request = new Request(Request.Operation.LibraryOrders, ar);

            Console.WriteLine($"Sending request: '{request.ToJSON()}'");
            var response = SendMessage(request);

            var status = GetResponseStatus(response);
            Console.WriteLine(response);
            return GetContent(response);
        }

        protected enum ResponseStatus
        {
            OK,
            Error
        }

        public class ServerOfflineException : Exception
        {
            public ServerOfflineException(String msg) : base(msg)
            {
            }
        }


        public class SearchException : Exception
        {
            public SearchException(String msg) : base(msg)
            {
            }
        }
    }
}