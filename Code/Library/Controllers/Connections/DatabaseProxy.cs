using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using System.Text;
using Models;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace Controllers.Connections
{
    public class DatabaseProxy : IDatabaseProxy
    {
        private readonly byte[] HOST = {127, 0, 0, 1};
        private readonly int PORT = 7777;

        public List<Book> Search(string searchTerm)
        {
            var ar = new Dictionary<string, object> {{"searchTerm", searchTerm}};
            var request = new Request(Request.Operation.Search, ar);

            Console.WriteLine($"Sending request: '{searchTerm}'");
            var response = SendMessage(request);

            var status = GetResponseStatus(response);
            return HandleSearchResponse(response, status);
        }

        public List<Book> AdvancedSearch(string title, string author, int? year, string isbn, Category? category)
        {
            var ar = new Dictionary<string, object>
                {{"title", title}, {"author", author}, {"year", year}, {"isbn", isbn}, {"category", category}};
            var request = new Request(Request.Operation.Search, ar);

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

            var bytes = new byte[1024];
            TcpClient client = new TcpClient("127.0.0.1",7777);  
            NetworkStream ns = client.GetStream();
            
            ns.Write(toSend, 0, toSend.Length);
            
            var bytesRec = ns.Read(bytes, 0, bytes.Length);
            var response = Encoding.ASCII.GetString(bytes, 0, bytesRec);
            return response;
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