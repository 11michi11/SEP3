namespace Controllers.Connections
{
    public interface IDatabaseProxy
    {
         public string Search(string searchTerm);
         public string AdvancedSearch(string title, string author, int year, string isbn);
    }
}