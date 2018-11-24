package entries;

import entries.general.BibtexEntry;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.*;

public class ArticleEntry extends BibtexEntry {

    public IBibtexValue author, title, journal, year, volume, number, pages, month, note, key;

    public ArticleEntry(String id) {
        super(id);
    }

    static {
        for (Field f : ArticleEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("journal", required);
        constraintMap.put("year", required);
    }

}
