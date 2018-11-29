package values;

import exceptions.InvalidPersonException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonValue implements IBibtexValue {

    private static final Pattern[] personPatterns = {
            Pattern.compile("([A-Z].*)\\s*\\|\\s*([A-Za-z]\\S*)"),
            Pattern.compile("([A-Z]\\S*)\\s+([A-Za-z]+\\S*)\\s+([A-Z]\\S*)"),
            Pattern.compile("([A-Z]\\S*)\\s+([A-Z]\\S*)")
    };

    private final String firstName;
    private final String middleName;
    private final String lastName;

    PersonValue(String personData) throws InvalidPersonException {
        Matcher m;
        String first = null;
        String mid = null;
        String last = null;
        for (int i = 0; i < personPatterns.length; i++) {
            m = personPatterns[i].matcher(personData);
            if (m.matches()) {
                if (i == 0) {
                    first = m.group(2);
                    last = m.group(1);
                }
                if (i == 1) {
                    first = m.group(1);
                    mid = m.group(2);
                    last = m.group(3);
                }
                if (i == 2) {
                    first = m.group(1);
                    last = m.group(2);
                }
                this.firstName = first==null? null : first.trim();
                this.middleName = mid==null? null : mid.trim();
                this.lastName = last==null? null : last.trim();
                return;
            }
        }
        throw new InvalidPersonException(personData);
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
        String mid = (middleName == null) ? " " : " " + middleName + " ";
        return firstName + mid + lastName;
    }
}
