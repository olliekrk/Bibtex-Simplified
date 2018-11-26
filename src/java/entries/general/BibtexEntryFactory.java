package entries.general;

import exceptions.MissingRequiredEntryFieldException;
import exceptions.ParsingException;
import values.IBibtexValue;
import values.MultipleValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static entries.general.BibtexFieldConstraint.*;

public class BibtexEntryFactory {

    //method to create entry of earlier validated type(class), id (format) and values (format)
    //still, it has to be checked whether entry contains every field that is required

    public static BibtexEntry createEntry(Class<? extends BibtexEntry> entryClass, String entryId, Map<String, IBibtexValue> entryValues) throws MissingRequiredEntryFieldException {

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

        if (!entry.validateEntry()) {
            //exception, entry will not be created
            throw new MissingRequiredEntryFieldException(entry);
        }

        return entry;
    }
}
