package printer;

import entries.general.BibtexEntry;
import entries.general.BibtexVisitor;
import parser.BibtexBibliography;
import values.IBibtexValue;
import values.MultipleValue;
import values.PersonValue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static entries.general.BibtexEntryType.findEntryType;

/**
 * Class which is a part of Visitor design pattern.
 * It is a visitor class that is responsible for printing data stored in {@link BibtexBibliography} in a form of tables.
 */
public class BibtexPrintingVisitor implements BibtexVisitor {

    /**
     * Row that separates two next {@link BibtexEntry} tables
     */
    private final String separator;
    /**
     * String format of an every row present in created table
     */
    private final String rowFormat;

    /**
     * Constructor which sets up format of a table, i.e. width of table cells and sign of which borders will be made.
     *
     * @param sign       sign of which borders of tables will be made
     * @param nameWidth  width of cells which contain name of a field or name of an entry
     * @param valueWidth width of cells which contain value of a field
     */
    public BibtexPrintingVisitor(char sign, int nameWidth, int valueWidth) {
        this.separator = String.join("", Collections.nCopies(valueWidth + nameWidth + 6, "" + sign)) + '\n';
        this.rowFormat = sign + " %-" + nameWidth + "s " + sign + " %-" + valueWidth + "s" + sign + '\n';
    }

    /**
     * Prints a table containing data from a single {@link BibtexEntry}.
     *
     * @param bibtexEntry entry for which table will be printed
     * @see BibtexVisitor
     */
    @Override
    public void visit(BibtexEntry bibtexEntry) {
        StringBuilder table = new StringBuilder();
        table.append(separator);

        String entryType = findEntryType(bibtexEntry.getClass()).toUpperCase();
        String entryId = bibtexEntry.getId();
        String entryRow = String.format(rowFormat, entryType + " (" + entryId + ")", " ");
        table.append(entryRow);

        Field[] fields = bibtexEntry.getClass().getDeclaredFields();

        for (Field field : fields) {
            IBibtexValue value = null;
            try {
                value = (IBibtexValue) field.get(bibtexEntry);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (value != null) {
                String fieldName = field.getName();

                if (value instanceof MultipleValue) {
                    IBibtexValue[] values = ((MultipleValue) value).getValues();

                    String[] stringValues = Arrays
                            .stream(values)
                            .map(IBibtexValue::getString)
                            .toArray(String[]::new);

                    String fieldValueRow;
                    for (int i = 0; i < stringValues.length; i++) {
                        String firstColumn = (i == 0) ? fieldName : "";
                        fieldValueRow = String.format(rowFormat, firstColumn, stringValues[i]);
                        table.append(fieldValueRow);
                    }

                } else {
                    String firstRow = String.format(rowFormat, fieldName, value.getString());
                    table.append(firstRow);
                }
            }
        }
        System.out.println(table.toString());
    }

    /**
     * Prints tables for every single entry stored in given bibliography.
     * Firstly prints all @STRING entries, then prints every other type entries.
     *
     * @param bibliography bibliography from which data will be printed
     */
    @Override
    public void visit(BibtexBibliography bibliography) {

        bibliography.getAllValues().forEach(this::printString);

        bibliography.getAllEntries().values().forEach(this::visit);

    }

    /**
     * Prints tables for those entries stored in given bibliography, which
     * match filters given as an second parameter.
     *
     * @param bibtexBibliography bibliography to be visited
     * @param filters            map in which keys are names of fields which are included in filter operation
     */
    @Override
    public void visit(BibtexBibliography bibtexBibliography, Map<String, List<String>> filters) {
        Stream<BibtexEntry> entries = bibtexBibliography.getAllEntries().values().stream();
        List<String> authorFilter = filters.get("names");
        List<String> categoryFilter = filters.get("categories");
        if (authorFilter != null && !authorFilter.isEmpty()) {
            entries = entries.filter(e -> byAuthorFromList(e, authorFilter));
        }
        if (categoryFilter != null && !categoryFilter.isEmpty()) {
            entries = entries.filter(e -> categoryFilter.contains(findEntryType(e.getClass())));
        }
        entries.forEach(this::visit);
    }

    /**
     * Method which returns boolean value depending on whether given entry's author
     * is on the list of authors - i. e. if the entry matches given authors filter.
     *
     * @param entry        entry which is checked
     * @param authorFilter list of authors' last names
     * @return true if entry's author is on the filter list, otherwise false
     */
    private boolean byAuthorFromList(BibtexEntry entry, List<String> authorFilter) {

        Field authorField;

        try {
            authorField = entry.getClass().getField("author");
        } catch (NoSuchFieldException e) {
            return false;
        }

        MultipleValue authors = null;
        try {
            authors = (MultipleValue) authorField.get(entry);
        } catch (IllegalAccessException e) {
            //unlikely to happen as fields in entry classes are public
            e.printStackTrace();
        }
        return authors != null && Arrays.stream(authors.getValues()).map(PersonValue::getLastName).anyMatch(authorFilter::contains);
    }

    /**
     * Method used during visiting whole bibliography, to print @STRING entries data
     * contained in visited bibliography.
     *
     * @param id    id of a @STRING entry
     * @param value value of a @STRING entry
     */
    private void printString(String id, IBibtexValue value) {
        StringBuilder table = new StringBuilder();
        table.append(separator);
        String stringRow = String.format(rowFormat, "@string " + "(" + id + ")", value.getString());
        table.append(stringRow);
        System.out.println(table.toString());
    }
}
