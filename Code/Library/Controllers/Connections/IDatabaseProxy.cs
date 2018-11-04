using Models;

namespace Controllers.Connections
{
    public interface IDatabaseProxy
    {
         string Search(string searchTerm);
         string AdvancedSearch(string title, string author, int? year, string isbn, Category? category);
    }
}