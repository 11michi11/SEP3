package communication;

import java.util.HashMap;
import java.util.Map;

public class Search implements RequestArgs {

    private String searchTerm;

    public Search(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public Map<String, Object> getArguments() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("searchTerm", searchTerm);
        return arguments;
    }
}
