using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;

namespace Controllers
{
    public class SessionKeyManager
    {
        private const string Url = "https://localhost:8080/checkSK/";
        private static Dictionary<string, DateTime?> _sessionKeys = new Dictionary<string, DateTime?>();
        private static readonly string LIBRARY_ID = "ce78ef57-77ec-4bb7-82a2-1a78d3789aef";
        
        public static bool IsSkValid(string sessionKey)
        {
            DateTime? expirationDate;
            try
            {
                 expirationDate = _sessionKeys[sessionKey];
            }catch(Exception e)
            {
                expirationDate = CheckInBookService(sessionKey);
                _sessionKeys.Add(sessionKey, expirationDate);
            }
            
            var now = DateTime.Now;
            var compareValue = Nullable.Compare(expirationDate, now);
            return compareValue > 0;
        }

        private static DateTime CheckInBookService(string sessionKey)
        {
            try
            {
                var response = MakeRequest(Url + sessionKey + "/" + LIBRARY_ID);
                var date = DateTime.ParseExact(response, "yyyy MMM dd HH:mm:ss", null);
                return date;
            }
            catch (ArgumentNullException ae)
            {
                Console.Write(ae);
                throw new SessionKeyInvalidException("Session can not be authenticated");
            }
            catch (FormatException fe)
            {
                Console.Write(fe);
                throw new SessionKeyInvalidException("Invalid expiration data format");
            }
            
        }

        private static string MakeRequest(string url)
        {
            ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
            request.ServerCertificateValidationCallback = delegate { return true; };

            ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };
            try
            {
                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            
                Stream dataStream = response.GetResponseStream();
                StreamReader reader = new StreamReader(dataStream);
                string responseFromServer = reader.ReadToEnd();

                return responseFromServer;
            }
            catch (WebException e)
            {
                throw new SessionKeyInvalidException("Session is invalid");
            }
           
        }

//        private static async Task<string> MakeRequest(string url)
//        {
//            using (HttpClient client = new HttpClient())
//            {
//                using (HttpResponseMessage response = await client.GetAsync(url))
//                {
//                    using (HttpContent content = response.Content)
//                    {
//                        string myContent = await content.ReadAsStringAsync();
//                        if (response.StatusCode == HttpStatusCode.Unauthorized)
//                        {
//                            throw new SessionKeyInvalidException("Session is invalid");
//                        }
//                        return myContent;
//                    } 
//                }
//            }
//        }
    }

    public class SessionKeyInvalidException : Exception
    {
        public SessionKeyInvalidException(string msg) : base(msg){}
    }
}