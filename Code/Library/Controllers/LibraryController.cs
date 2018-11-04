using System;
using Controllers.Connections;
using Models;

namespace Controllers
{
    public class LibraryController
    {
        private static LibraryController _instance;
        private IDatabaseProxy _proxy;

        private LibraryController ()
        {
            // Mock class used temporarilly
            // _proxy = new DatabaseProxy();
            _proxy = new MockDatabaseProxy();
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
        public string AdvancedSearch(string title, string author, int? year, string isbn, Category? category)
        {
            return _proxy.AdvancedSearch(title,author,year,isbn,category);
        }
    }
}
