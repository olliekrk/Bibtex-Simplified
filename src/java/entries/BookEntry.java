package entries;

import entries.general.BibtexEntry;
import entries.general.BibtexFieldConstraint;
import exceptions.MissingRequiredEntryFieldException;
import values.IBibtexValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.*;

public class BookEntry extends BibtexEntry {

    public IBibtexValue author, editor, title, publisher, year, volume, number, series, address, edition;

    public BookEntry(String id) {
        super(id);
    }

    static {
        Map<String, BibtexFieldConstraint> constraintMap = new HashMap<>();
        for (Field f : BookEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", alternativeMultiple);
        constraintMap.put("editor", alternativeMultiple);
        constraintMap.put("title", required);
        constraintMap.put("publisher", required);
        constraintMap.put("year", required);
        classConstraints.put(BookEntry.class, constraintMap);
    }

    @Override
    public void validateEntry() throws MissingRequiredEntryFieldException, IllegalAccessException {
        super.validateEntry();
        if (author == null && editor == null) {
            throw new MissingRequiredEntryFieldException(this.getId(), "author", "editor");
        }
    }
}
