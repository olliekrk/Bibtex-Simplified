package entries;

import entries.general.BibtexEntry;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.*;

public class MasterthesisEntry extends BibtexEntry {

    public MasterthesisEntry(String id) {
        super(id);
    }

    public IBibtexValue author, title, school, year, type, address, month, note, key;

    static {
        for (Field f : MasterthesisEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("school", required);
        constraintMap.put("year", required);
    }
}
