package parser;

import entries.general.BibtexEntry;
import entries.general.BibtexVisitableElement;
import entries.general.BibtexVisitor;
import values.IBibtexValue;
import values.StringValue;

import java.util.HashMap;
import java.util.Map;

public class BibtexBibliography implements BibtexVisitableElement {

    //contains every known-type @ entry

    private Map<String, BibtexEntry> entries = new HashMap<>();

    //contains every @string entry

    private Map<String, IBibtexValue> values = new HashMap<>();

    public BibtexBibliography() {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (String m : months) {
            this.addValue(m.substring(0, 3).toLowerCase(), new StringValue(m));
        }
        String[] companies = {"The OX Association for Computing Machinery", " Symposium on the Theory of Computing"};
        String[] companiesShort = {"ACM", "STOC"};
        this.addValue(companiesShort[0], new StringValue(companies[0]));
        this.addValue(companiesShort[1], new StringValue(companies[1]));
    }

    public BibtexEntry addEntry(BibtexEntry entry) {
        BibtexEntry previous = entries.put(entry.getId().toLowerCase(), entry);
        return previous;
    }

    public IBibtexValue addValue(String valueId, IBibtexValue value) {
        IBibtexValue previous = values.put(valueId.toLowerCase(), value);
        return previous;
    }

    public boolean containsEntry(String entryId) {
        return entries.containsKey(entryId.toLowerCase());
    }

    public boolean containsValue(String valueId) {
        return values.containsKey(valueId.toLowerCase());
    }

    public BibtexEntry getEntry(String entryId) {
        return entries.get(entryId.toLowerCase());
    }

    public IBibtexValue getValue(String valueId) {
        return values.get(valueId.toLowerCase());
    }

    public Map<String, BibtexEntry> getAllEntries() {
        return this.entries;
    }

    public Map<String, IBibtexValue> getAllValues() {
        return this.values;
    }

    @Override
    public void accept(BibtexVisitor visitor) {
        visitor.visit(this);
    }
}
