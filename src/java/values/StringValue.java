package values;

public class StringValue implements IBibtexValue {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String getString() {
        return value;
    }
}
