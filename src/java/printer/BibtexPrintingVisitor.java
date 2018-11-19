package printer;

import entries.general.BibtexEntry;
import entries.general.BibtexEntryType;
import entries.general.BibtexVisitor;
import parser.BibtexBibliography;
import values.IBibtexValue;
import values.MultipleValue;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;

public class BibtexPrintingVisitor implements BibtexVisitor {

    private final int FIELD_NAME_WIDTH;
    private final int FIELD_VALUE_WIDTH;
    private final char sign;
    private final String separator;
    private final String entryDataFormat;
    private final String entryTypeIdFormat;

    public BibtexPrintingVisitor(char sign, int nameWidth, int valueWidth) {
        this.FIELD_NAME_WIDTH = nameWidth;
        this.FIELD_VALUE_WIDTH = valueWidth;
        this.sign = sign;
        this.separator = String.join("", Collections.nCopies(FIELD_VALUE_WIDTH + FIELD_NAME_WIDTH + 3, "" + sign)) + '\n';
        //TODO: change below Formats
        this.entryDataFormat = sign + " %-" + FIELD_NAME_WIDTH + "s " + sign + "%-" + FIELD_VALUE_WIDTH + "s" + sign + '\n';
        this.entryTypeIdFormat = sign + " %-" + FIELD_NAME_WIDTH + "s " + String.join("", Collections.nCopies(FIELD_VALUE_WIDTH, "" + sign)) + '\n';
    }

    @Override
    public void visit(BibtexEntry bibtexEntry) {
        StringBuilder table = new StringBuilder();
        table.append(separator);

        String entryType = BibtexEntryType.findEntryType(bibtexEntry.getClass()).toUpperCase();
        String entryId = bibtexEntry.getId();
        String entryRow = String.format(entryTypeIdFormat, entryType + "(" + entryId + ")");
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

                    for (int i = 0; i < stringValues.length; i++) {
                        String fieldValueRow = String.format(entryDataFormat, "", stringValues[i]);
                        table.append(fieldValueRow);
                    }

                } else {
                    String firstRow = String.format(entryDataFormat, fieldName, value.getString());
                    table.append(firstRow);
                }
            }
        }
        System.out.println(table.toString());
    }

    @Override
    public void visit(BibtexBibliography bibtexBibliography) {

    }
}
