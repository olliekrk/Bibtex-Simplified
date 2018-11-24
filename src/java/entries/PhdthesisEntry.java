package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.none;
import static entries.general.BibtexFieldConstraint.required;
import static entries.general.BibtexFieldConstraint.requiredMultiple;


public class PhdthesisEntry extends BibtexEntry {

    @BibtexConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexConstraints(required = true)
    public IBibtexValue title;
    @BibtexConstraints(required = true)
    public IBibtexValue school;
    @BibtexConstraints(required = true)
    public IBibtexValue year;
    public IBibtexValue type;
    public IBibtexValue address;
    public IBibtexValue month;
    public IBibtexValue note;
    public IBibtexValue key;

    public PhdthesisEntry(String id) {
        super(id);
    }

//    public IBibtexValue author, title, school, year, type, address, month, note, key;

    static {
        for (Field f : IncollectionEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("year", required);
        constraintMap.put("school", required);
    }
}
