package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.multiple;
import static entries.general.BibtexFieldConstraint.none;

public class MiscEntry extends BibtexEntry {

    @BibtexConstraints(multiple = true)
    public IBibtexValue author;
    public IBibtexValue title;
    public IBibtexValue howpublished;
    public IBibtexValue month;
    public IBibtexValue year;
    public IBibtexValue note;
    public IBibtexValue key;

    public MiscEntry(String id) {
        super(id);
    }

//    public IBibtexValue author, title, howpublished, month, year, note, key;

    static {
        for (Field f : IncollectionEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", multiple);
    }
}
