package responses;

import items.ItemSelect;
import java.util.*;

/**
 * Created by educacion on 23/11/2017.
 */
public class ItemSelect2Response {

    List<ItemSelect> results;

    public List<ItemSelect> getResults() {
        return results;
    }

    public void setResults(List<ItemSelect> results) {
        this.results = results;
    }

    public ItemSelect2Response(List<ItemSelect> results) {
        this.results = results;
    }
}
