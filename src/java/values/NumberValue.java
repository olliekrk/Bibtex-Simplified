package values;

public class NumberValue implements IBibtexValue {
    private final int value;

    public NumberValue(int value) {
        this.value = value;
    }

    @Override
    public String getString() {
        return Integer.toString(value);
    }
}
