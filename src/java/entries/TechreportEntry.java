package entries;

import entries.general.BibtexEntry;
import utilities.BibtexFieldConstraints;
import values.IBibtexValue;

public class TechreportEntry extends BibtexEntry {

    @BibtexFieldConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue title;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue institution;
    @BibtexFieldConstraints(required = true)
    public IBibtexValue year;
    @BibtexFieldConstraints(multiple = true)
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
}
