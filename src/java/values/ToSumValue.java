package values;

import java.util.Arrays;

public class ToSumValue implements IBibtexValue {
    private final IBibtexValue[] values;

    public ToSumValue(IBibtexValue[] values) {
        this.values = values;
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
        return Arrays.stream(values)
                .map(IBibtexValue::getString)
                .reduce("", ((a, b) -> a + b));
    }
}
