package entries.general;

import exceptions.MissingRequiredEntryFieldException;
import exceptions.ParsingException;
import parser.BibtexBibliography;
import values.IBibtexValue;
import values.MultipleValue;
import values.StringValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.*;

/**
 * Factory method design pattern class used to create {@link BibtexEntry}
 */
public class BibtexEntryFactory {

    /**
     * Method used to create entry of earlier validated type (class), id and values given as arguments.
     * It checks whether entry to be created contains every field which is required.
     * If it does not, this method returns null and notifies about this fact by printing it to the console.
     *
     * @param entryClass   class of which entry should be created
     * @param entryId      id of an entry to be created
     * @param entryValues  map containing names of fields as keys and corresponding {@link IBibtexValue} as values in this map
     * @param bibliography bibliography used to access cross-referenced entries
     * @return null if entry is invalid, otherwise {@link BibtexEntry} object of class given as first argument
     */
    public static BibtexEntry createEntry(Class<? extends BibtexEntry> entryClass, String entryId, Map<String, IBibtexValue> entryValues, BibtexBibliography bibliography) {

        BibtexEntry entry;

        try {
            entry = entryClass.getConstructor(String.class).newInstance(entryId);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            //exception, should not happen because constructor of every entry class exists and is public
            e.printStackTrace();
            return null;
        }

        Field[] fields = entryClass.getDeclaredFields();

        for (Field field : fields) {
            if (field.getType().equals(IBibtexValue.class)) {
                IBibtexValue value = entryValues.get(field.getName().toLowerCase());
                BibtexFieldConstraint constraint = entry.getConstraintMap().get(field.getName());
                if (value != null) {
                    if (constraint == multiple || constraint == requiredMultiple || constraint == alternativeMultiple) {
                        try {
                            value = new MultipleValue(value);
                        } catch (ParsingException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                    }
                    try {
                        field.set(entry, value);
                    } catch (IllegalAccessException e) {
                        //exception, should not happen because fields of every entry class are public
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }

        //cross-referencing
        if (entryValues.containsKey("crossref")) {
            String refersToID = entryValues.get("crossref").getString().toLowerCase();
            BibtexEntry refersTo = bibliography.getEntry(refersToID);

            for (Field field : fields) {
                try {
                    if (field.get(entry) == null) {
                        field.set(entry, field.get(refersTo));
                    }
                } catch (IllegalAccessException e) {
                    //unlikely to happen
                    e.printStackTrace();
                }
            }
        }

        try {
            entry.validateEntry();
        } catch (MissingRequiredEntryFieldException | IllegalAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return entry;
    }
}
