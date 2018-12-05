using System;
using System.Collections.Generic;

namespace Controllers
{
    public class SessionKeyManager
    {
        private static Dictionary<string, DateTime> _session_keys;

        
        
        public static bool IsSkValid(string sessionKey)
        {
            if (_session_keys[sessionKey] != null)
            {
                return true;
            }
            _session_keys.Add(sessionKey,CheckInBookService(sessionKey));
            return true;
        }

        private static DateTime CheckInBookService(string sessionKey)
        {
            return DateTime.Now;
        }
    }
}