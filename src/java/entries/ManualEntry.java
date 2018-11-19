package entries;

import entries.general.BibtexEntry;
import utilities.BibtexFieldConstraints;
import values.IBibtexValue;

public class ManualEntry extends BibtexEntry {

    @BibtexFieldConstraints(required = true)
    public IBibtexValue title;
    @BibtexFieldConstraints(multiple = true)
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
