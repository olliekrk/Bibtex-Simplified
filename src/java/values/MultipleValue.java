package values;

import java.util.Arrays;

public class MultipleValue implements IBibtexValue {


    private final IBibtexValue[] values;
    private final String delimiter;

    public MultipleValue(IBibtexValue[] values, String delimiter) {
        this.values = values;
        this.delimiter = delimiter;
    }

    public IBibtexValue[] getValues() {
        return values;
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
        return null;
    }

    public static IBibtexValue readMutipleValue(IBibtexValue value) {
        IBibtexValue[] values = Arrays
                .stream(value.getString().split("\\|"))
                .map(String::trim)
                .toArray(IBibtexValue[]::new);
        return new MultipleValue(values, "|");
    }
}
