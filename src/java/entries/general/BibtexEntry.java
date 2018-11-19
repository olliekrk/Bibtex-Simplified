package entries.general;

import exceptions.MissingRequiredEntryFieldException;
import utilities.BibtexFieldConstraints;
import values.IBibtexValue;
import values.MultipleValue;

import java.lang.reflect.Field;
import java.util.Map;

public abstract class BibtexEntry {

    private final String entryType;
    private final String id;

    public BibtexEntry(String entryType, String id) {
        this.entryType = entryType;
        this.id = id;
    }

    public String getEntryType() {
        return entryType;
    }

    public String getId() {
        return id;
    }

    //method to create entry, FACTORY PATTERN!
    public void insertValues(Map<String, IBibtexValue> entryValues) throws MissingRequiredEntryFieldException {

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {

            IBibtexValue value = entryValues.get(field.getName());
            if (field.isAnnotationPresent(BibtexFieldConstraints.class)) {
                BibtexFieldConstraints constraints = field.getAnnotation(BibtexFieldConstraints.class);
                if (value == null && constraints.required()) {
                    //exception
                    throw new MissingRequiredEntryFieldException();
                }
                if (!(value == null) && constraints.multiple()) {
                    value = new MultipleValue(value);
                }
            }
            if (!(value == null)) {
                try {
                    field.set(this, value);
                } catch (IllegalAccessException e) {
                    //exception, should not happen as every entry field is public
                    e.printStackTrace();
                }
            }
        }
    }
}
