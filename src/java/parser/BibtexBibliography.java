package parser;

import entries.general.BibtexEntry;
import values.IBibtexValue;

import java.util.HashMap;
import java.util.Map;

public class BibtexBibliography {
    private Map<String, BibtexEntry> entries = new HashMap<>();

    //contains every @string entry
    private Map<String, IBibtexValue> values = new HashMap<>();

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

}
