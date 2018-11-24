package entries;

import entries.general.BibtexEntry;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.*;

public class BookEntry extends BibtexEntry {

    public IBibtexValue author, editor, title, publisher, year, volume, number, series, address, edition;

    public BookEntry(String id) {
        super(id);
    }

    static {
        for (Field f : BookEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", alternativeMultiple);
        constraintMap.put("editor", alternativeMultiple);
        constraintMap.put("title", required);
        constraintMap.put("publisher", required);
        constraintMap.put("year", required);
    }

    @Override
    public boolean validateEntry() {
        boolean a = author != null || editor != null;
        boolean b = super.validateEntry();
        return a && b;
    }
}
