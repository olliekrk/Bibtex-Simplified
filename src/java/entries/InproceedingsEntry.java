package entries;

import entries.general.BibtexEntry;
import parser.BibtexConstraints;
import values.IBibtexValue;

public class InproceedingsEntry extends BibtexEntry {

    @BibtexConstraints(required = true, multiple = true)
    public IBibtexValue author;
    @BibtexConstraints(required = true)
    public IBibtexValue title;
    @BibtexConstraints(required = true)
    public IBibtexValue booktitle;
    @BibtexConstraints(required = true)
    public IBibtexValue year;
    @BibtexConstraints(multiple = true)
    public IBibtexValue editor;
    public IBibtexValue volume;
    public IBibtexValue number;
    public IBibtexValue series;
    public IBibtexValue pages;
    public IBibtexValue address;
    public IBibtexValue month;
    public IBibtexValue organization;
    public IBibtexValue publisher;
    public IBibtexValue note;
    public IBibtexValue key;

    public InproceedingsEntry(String id) {
        super(id);
    }
}
