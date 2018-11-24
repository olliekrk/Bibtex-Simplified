package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.*;

public class BookletEntry extends BibtexEntry {

    @BibtexConstraints(required = true)
    public IBibtexValue title;
    @BibtexConstraints(multiple = true)
    public IBibtexValue author;
    public IBibtexValue howpublished;
    public IBibtexValue address;
    public IBibtexValue month;
    public IBibtexValue year;
    public IBibtexValue note;
    public IBibtexValue key;

    public BookletEntry(String id) {
        super(id);
    }

    //public IBibtexValue title, author, howpublished, address, month, year, note, key;

    static {
        for (Field f : IncollectionEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("title", required);
        constraintMap.put("author", multiple);
    }
}
