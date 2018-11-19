using System.Collections.Generic;
using Models;

namespace Controllers.Connections
{
    public interface IDatabaseProxy
    {
         List<Book> Search(string searchTerm);
         List<Book> AdvancedSearch(string title, string author, int? year, string isbn, Category? category);
        void CreateBook(Book book);
        void DeleteBook(string bookid);
        string BookDetails(string isbn);
    }
}