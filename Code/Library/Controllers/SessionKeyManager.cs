using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using Controllers.Resources;

namespace Controllers
{
    public class SessionKeyManager
    {
        private static readonly string Url = ConfigurationLoader.GetInstance().BookServiceURL;
        private static Dictionary<string, DateTime?> _sessionKeys = new Dictionary<string, DateTime?>();
        private static readonly string LIBRARY_ID = ConfigurationLoader.GetInstance().LibraryID;
        
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
                var response = MakeRequest(Url + "checkSK/" + sessionKey + "/" + LIBRARY_ID,null);
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

        private static string MakeRequest(string url,Cookie cookie)
        {
            ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };
            HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
            request.ServerCertificateValidationCallback = delegate { return true; };
            if (cookie != null)
            {
                request.CookieContainer = new CookieContainer();
                request.CookieContainer.Add(cookie);
                request.Method = "DELETE";
            }

            ServicePointManager.ServerCertificateValidationCallback = delegate { return true; };
            try
            {
                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
            
                Stream dataStream = response.GetResponseStream();
                StreamReader reader = new StreamReader(dataStream);
                string responseFromServer = reader.ReadToEnd();
                Console.WriteLine("Sending '"+request.Method+"' request to BookService");

                return responseFromServer;
            }
            catch (WebException e)
            {
                if (cookie == null)
                 throw new SessionKeyInvalidException("Session is invalid");
                throw new Exception("Cannot connect to BookService");
            }
        }

        public static void LogOut(Cookie cookie)
        {
            _sessionKeys.Remove(cookie.Name);
            MakeRequest(Url + "logOut",cookie);
        }
    }

    public class SessionKeyInvalidException : Exception
    {
        public SessionKeyInvalidException(string msg) : base(msg){}
    }
}