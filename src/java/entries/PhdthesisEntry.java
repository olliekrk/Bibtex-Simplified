package entries;

import entries.general.BibtexEntry;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.*;


public class PhdthesisEntry extends BibtexEntry {

    public PhdthesisEntry(String id) {
        super(id);
    }

    public IBibtexValue author, title, school, year, type, address, month, note, key;

    static {
        for (Field f : PhdthesisEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("year", required);
        constraintMap.put("school", required);
    }
}
