package parser;

import entries.general.BibtexEntry;
import entries.general.BibtexVisitableElement;
import entries.general.BibtexVisitor;
import values.IBibtexValue;
import values.StringValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Container for parsed BibTeX data. Contains parsed BibTeX entries and separately @strings.
 * Bibliography is being created by {@link BibtexParser} class.
 */
public class BibtexBibliography implements BibtexVisitableElement {

    /**
     * Map which contains parsed BibTeX entries.
     * Keys are entries' IDs and values are corresponding entries objects.
     *
     * @see BibtexEntry
     */
    private Map<String, BibtexEntry> entries = new HashMap<>();

    /**
     * Map which contains parsed @STRING values.
     * Keys are strings' IDs and values are those strings.
     *
     * @see IBibtexValue
     * @see StringValue
     */
    private Map<String, IBibtexValue> values = new HashMap<>();

    /**
     * Constructor, adds default @STRING values to the bibliography such as months and company names.
     */
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

    /**
     * Method that adds {@link BibtexEntry} to bibliography.
     * If there already existed entry with the same ID as added one, this method returns it.
     * Otherwise it returns null.
     *
     * @param entry entry to be added to bibliography
     * @return previous entry which had the same ID or null value
     */
    public BibtexEntry addEntry(BibtexEntry entry) {
        BibtexEntry previous = entries.put(entry.getId().toLowerCase(), entry);
        return previous;
    }

    /**
     * Method that adds @STRING entry to bibliography.
     * If there already existed @STRING with the same ID as added one, this method returns it.
     * Otherwise it returns null.
     *
     * @param valueId ID of @STRING to be added to bibliography
     * @param value   value of @STRING to be added to bibliography
     * @return previous value which had the same ID or null value
     */
    public IBibtexValue addValue(String valueId, IBibtexValue value) {
        IBibtexValue previous = values.put(valueId.toLowerCase(), value);
        return previous;
    }

    /**
     * Method to check whether there exists entry that has given ID.
     *
     * @param entryId ID of entry to be checked
     * @return true if there exists such entry, otherwise false
     */
    public boolean containsEntry(String entryId) {
        return entries.containsKey(entryId.toLowerCase());
    }

    /**
     * Method to check whether there exists @STRING that has given ID.
     *
     * @param valueId ID of @STRING to be checked
     * @return true if there exists such @STRING, otherwise false
     */
    public boolean containsValue(String valueId) {
        return values.containsKey(valueId.toLowerCase());
    }

    /**
     * Method to get entry, from bibliography, that has given ID
     *
     * @param entryId ID of entry
     * @return entry that has given entry ID if there exists such in bibliography, otherwise null
     */
    public BibtexEntry getEntry(String entryId) {
        return entries.get(entryId.toLowerCase());
    }

    /**
     * Method to get @STRING, from bibliography, that has given ID
     *
     * @param valueId ID of @STRING
     * @return value of a @STRING that has given ID if there exists such in bibliography, otherwise null
     */
    public IBibtexValue getValue(String valueId) {
        return values.get(valueId.toLowerCase());
    }

    /**
     * Method to get map of all entries in bibliography.
     *
     * @return Map of all entries in bibliography
     */
    public Map<String, BibtexEntry> getAllEntries() {
        return this.entries;
    }

    /**
     * Method to get map of all @STRINGs in bibliography.
     *
     * @return Map of all @STRINGs in bibliography
     */
    public Map<String, IBibtexValue> getAllValues() {
        return this.values;
    }

    /**
     * Method used in Visitor design pattern.
     *
     * @param visitor visitor which performs visit operation on this element
     * @see BibtexVisitor
     */
    @Override
    public void accept(BibtexVisitor visitor) {
        visitor.visit(this);
    }
}
