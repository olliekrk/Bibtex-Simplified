package values;

import java.util.Arrays;

public class MultipleValue implements IBibtexValue {

    private final IBibtexValue[] values;

    public MultipleValue(IBibtexValue[] values) {
        this.values = values;
    }

    public MultipleValue(IBibtexValue value){
        this.values = Arrays
                .stream(value.getString().split("\\|"))
                .map(String::trim)
                .map(StringValue::new)
                .toArray(IBibtexValue[]::new);
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

    //creates arrays of values, when value can be multiple (authors,editors), FACTORY PATTERN!
    //created second constructor instead of this
    public static IBibtexValue readMutipleValue(IBibtexValue value) {
        IBibtexValue[] values = Arrays
                .stream(value.getString().split("\\|"))
                .map(String::trim)
                .toArray(IBibtexValue[]::new);
        return new MultipleValue(values);
    }
}
