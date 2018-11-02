using System;
using Controllers.Connections;

namespace Controllers
{
    public class LibraryController
    {
        private static LibraryController _instance;
        private IDatabaseProxy _proxy;

        private LibraryController ()
        {
            _proxy = new DatabaseProxy();
        }

        public static LibraryController GetInstance() {
            if (_instance == null)
                _instance = new LibraryController();
            return _instance;
        }

        public string Search(string searchTerm)
        {
            return _proxy.Search(searchTerm);
        }
        public string AdvancedSearch(string title, string author, int year, string isbn)
        {
            return _proxy.AdvancedSearch(title,author,year,isbn);
        }
    }
}
