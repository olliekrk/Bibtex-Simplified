package entries;

import entries.general.BibtexEntry;
import entries.general.BibtexFieldConstraint;
import values.IBibtexValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.*;


public class PhdthesisEntry extends BibtexEntry {

    public PhdthesisEntry(String id) {
        super(id);
    }

    public IBibtexValue author, title, school, year, type, address, month, note, key;

    static {
        Map<String, BibtexFieldConstraint> constraintMap = new HashMap<>();
        for (Field f : PhdthesisEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("year", required);
        constraintMap.put("school", required);
        classConstraints.put(PhdthesisEntry.class, constraintMap);
    }
}
