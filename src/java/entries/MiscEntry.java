package entries;

import entries.general.BibtexEntry;
import entries.general.BibtexFieldConstraint;
import values.IBibtexValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.multiple;
import static entries.general.BibtexFieldConstraint.none;

public class MiscEntry extends BibtexEntry {

    public MiscEntry(String id) {
        super(id);
    }

    public IBibtexValue author, title, howpublished, month, year, note, key;

    static {
        Map<String, BibtexFieldConstraint> constraintMap = new HashMap<>();
        for (Field f : MiscEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", multiple);
        classConstraints.put(MiscEntry.class, constraintMap);
    }
}
