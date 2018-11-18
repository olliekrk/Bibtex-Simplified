package values;

public class StringValue implements IBibtexValue {
    private final String value;

    public StringValue(String value) {
        this.value = value;
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
        return value;
    }
}
