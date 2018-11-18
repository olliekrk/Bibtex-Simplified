package values;

public interface IBibtexValue {
    boolean isNumber();

    boolean isString();

    int getNumber() throws UnsupportedOperationException;

    String getString();
}
