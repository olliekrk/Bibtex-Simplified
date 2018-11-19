package entries.general;

import exceptions.MissingRequiredEntryFieldException;
import utilities.BibtexFieldConstraints;
import values.IBibtexValue;
import values.MultipleValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class BibtexEntryFactory {

    //method to create entry of earlier validated type(class), id and values

    public static BibtexEntry createEntry(Class<? extends BibtexEntry> entryClass, String entryId, Map<String, IBibtexValue> entryValues) throws MissingRequiredEntryFieldException {
        BibtexEntry entry;
        try {
            entry = entryClass.getConstructor(String.class).newInstance(entryId);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            //exception, should not happen because constructor in every entry class exist and is public
            return null;
        }
        Field[] fields = entryClass.getDeclaredFields();
        for (Field field : fields) {
            IBibtexValue value = entryValues.get(field.getName().toLowerCase());
            if (field.isAnnotationPresent(BibtexFieldConstraints.class)) {
                BibtexFieldConstraints constraints = field.getAnnotation(BibtexFieldConstraints.class);
                if (value == null && constraints.required()) {
                    throw new MissingRequiredEntryFieldException();
                }
                if (value != null && constraints.multiple()) {
                    value = new MultipleValue(value);
                }
            }
            if (value != null) {
                try {
                    field.set(entry, value);
                } catch (IllegalAccessException e) {
                    //exception, should not happen because fields of every entry class are public
                }
            }
        }
        return entry;
    }
}
