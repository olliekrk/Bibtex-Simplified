package values;

public class NumberValue implements IBibtexValue {
    private final int value;

    public NumberValue(int value) {
        this.value = value;
    }

    @Override
    public boolean isNumber() {
        return true;
    }

    @Override
    public boolean isString() {
        return false;
    }

    @Override
    public int getNumber() throws UnsupportedOperationException {
        return value;
    }

    @Override
    public String getString() {
        return Integer.toString(value);
    }
}
