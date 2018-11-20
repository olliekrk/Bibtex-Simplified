package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

public class ManualEntry extends BibtexEntry {

    @BibtexConstraints(required = true)
    public IBibtexValue title;
    @BibtexConstraints(multiple = true)
    public IBibtexValue author;
    public IBibtexValue organization;
    public IBibtexValue address;
    public IBibtexValue edition;
    public IBibtexValue month;
    public IBibtexValue year;
    public IBibtexValue note;
    public IBibtexValue key;

    public ManualEntry(String id) {
        super(id);
    }
}
