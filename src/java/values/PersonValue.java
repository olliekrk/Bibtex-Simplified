package values;

import exceptions.InvalidPersonException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonValue implements IBibtexValue {

    private static final Pattern personPattern1 = Pattern.compile("([A-Z][^\\s]+)\\s+([A-Za-z]+[^\\s]*)\\s+([A-Z][^\\s]+)");
    public static final Pattern personPattern2 = Pattern.compile("([A-Z][^\\s]+)\\s+([A-Z][^\\s]+)");

    private final String firstName;
    private final String middleInitial;
    private final String lastName;

    public PersonValue(String personData) throws InvalidPersonException {
        Matcher m = personPattern1.matcher(personData);
        if (m.matches()) {
            this.firstName = m.group(1);
            this.middleInitial = m.group(2);
            this.lastName = m.group(3);
        } else {
            m = personPattern2.matcher(personData);
            if (m.matches()) {
                this.firstName = m.group(1);
                this.lastName = m.group(2);
                this.middleInitial = null;
            } else throw new InvalidPersonException(personData);
        }
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public int getNumber() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getString() {
        String mid = (middleInitial == null) ? " " : " " + middleInitial + " ";
        return firstName + mid + lastName;
    }
}
