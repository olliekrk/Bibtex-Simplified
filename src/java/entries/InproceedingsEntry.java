package entries;

import entries.general.BibtexEntry;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.*;

public class InproceedingsEntry extends BibtexEntry {

    public InproceedingsEntry(String id) {
        super(id);
    }

    public IBibtexValue author, title, booktitle, year, editor, volume, number, series, pages, address, month, organization, publisher, note, key;

    static {
        for (Field f : InproceedingsEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("booktitle", required);
        constraintMap.put("year", required);
        constraintMap.put("editor", multiple);
    }
}
