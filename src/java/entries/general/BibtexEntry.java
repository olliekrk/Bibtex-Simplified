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

    public void insertValues(Map<String, IBibtexValue> entryValues) throws MissingRequiredEntryFieldException {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(BibtexFieldConstraints.class)) {
                BibtexFieldConstraints constraints = field.getAnnotation(BibtexFieldConstraints.class);
                IBibtexValue value = entryValues.get(field.getName());
                if (value == null) {
                    if (constraints.required()) {
                        throw new MissingRequiredEntryFieldException();
                    }
                } else if (constraints.multiple()) {
                    value = MultipleValue.readMutipleValue(value);
                    try {
                        field.set(this,value);
                    } catch (IllegalAccessException e) {
                        //exception
                    }
                }
            }
        }
    }
}
