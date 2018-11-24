package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

import java.lang.reflect.Field;

import static entries.general.BibtexFieldConstraint.*;

public class TechreportEntry extends BibtexEntry {

    @BibtexConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexConstraints(required = true)
    public IBibtexValue title;
    @BibtexConstraints(required = true)
    public IBibtexValue institution;
    @BibtexConstraints(required = true)
    public IBibtexValue year;
    @BibtexConstraints(multiple = true)
    public IBibtexValue editor;
    public IBibtexValue volume;
    public IBibtexValue number;
    public IBibtexValue series;
    public IBibtexValue address;
    public IBibtexValue month;
    public IBibtexValue organization;
    public IBibtexValue publisher;
    public IBibtexValue note;
    public IBibtexValue key;

    public TechreportEntry(String id) {
        super(id);
    }

//    public IBibtexValue author, title, institution, year, editor, volume, number, series, address, month, organization, publisher, note, key;

    static {
        for (Field f : IncollectionEntry.class.getDeclaredFields()) {
            constraintMap.put(f.getName(), none); //by default there are no constraints of a field
        }
        constraintMap.put("author", requiredMultiple);
        constraintMap.put("title", required);
        constraintMap.put("institution", required);
        constraintMap.put("year", required);
        constraintMap.put("editor",multiple);
    }
}
