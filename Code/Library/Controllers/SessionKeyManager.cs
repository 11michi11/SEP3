using System;
using System.Collections.Generic;

namespace Controllers
{
    public class SessionKeyManager
    {
        private static Dictionary<string, DateTime?> _sessionKeys = new Dictionary<string, DateTime?>();
        
        public static bool IsSkValid(string sessionKey)
        {
            var expirationDate = _sessionKeys[sessionKey];
            if (expirationDate == null)
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
            
        }
    }
}