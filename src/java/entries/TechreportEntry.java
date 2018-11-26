package entries;

import entries.general.BibtexEntry;
import entries.general.BibtexFieldConstraint;
import values.IBibtexValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.*;

public class TechreportEntry extends BibtexEntry {

    public TechreportEntry(String id) {
        super(id);
    }

    public IBibtexValue author, title, institution, year, editor, volume, number, series, address, month, organization, publisher, note, key;

    static {
        Map<String, BibtexFieldConstraint> constraintMap = new HashMap<>();
        for (Field f : TechreportEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("institution", required);
        constraintMap.put("year", required);
        constraintMap.put("editor", multiple);
        classConstraints.put(TechreportEntry.class, constraintMap);
    }
}
