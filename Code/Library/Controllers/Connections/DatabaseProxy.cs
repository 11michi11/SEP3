using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using Models;

namespace Controllers.Connections
{
    public class DatabaseProxy : IDatabaseProxy
    {
        private readonly NetworkStream _stream;
        private TcpClient _client;
        
        public DatabaseProxy()
        {
            byte[] adr = {127,0,0,1};
            _client = new TcpClient("localhost",5000);
            _client.Connect(new IPEndPoint(new IPAddress(adr),5000 ));
            _stream = _client.GetStream();
        }
        
        public string Search(string searchTerm)
        {
            byte[] request = Encoding.ASCII.GetBytes(searchTerm);
            _stream.Write(request,0,request.Length);
            Console.WriteLine($"Request was sent: '{searchTerm}'");
            throw new System.NotImplementedException();
        }
        public string AdvancedSearch(string title, string author, int year, string isbn, Category category)
        {
            throw new System.NotImplementedException();
        }
    }
}