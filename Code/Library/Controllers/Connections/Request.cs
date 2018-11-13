using System;
using System.Collections.Generic;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace Controllers.Connections
{
    public class Request
    {
        [JsonProperty]
        [JsonConverter(typeof(StringEnumConverter))]
        private Operation operation;
        [JsonProperty]
        private readonly Dictionary<string, object> args;
        
        public enum Operation{Search, AdvancedSearch,AddBook, DeleteBook
        }

        public Request(Operation operation, Dictionary<string, object> args)
        {
            this.operation = operation;
            this.args = args;
        }

        public string ToJSON()
        {
            return JsonConvert.SerializeObject(this);
        }
    }
}