using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace Models
{
    // added in order to have this enum as string instead of it's numeric value after serialization
    // maybe not needed, it was added for temporal testing
    [JsonConverter(typeof(StringEnumConverter))]
    public enum Category
    {
        Fantasy, ScienceFiction, Criminal, Science, Drama, Children, Horror, Poetry        
    }
}