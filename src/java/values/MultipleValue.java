package values;

import exceptions.InvalidPersonException;
import exceptions.ParsingException;

import java.util.Arrays;
import java.util.Objects;

public class MultipleValue implements IBibtexValue {

    private final IBibtexValue[] values;

    public MultipleValue(IBibtexValue value) throws ParsingException {
        //TODO: szukanie po nazwiskach autorów, ogólnie formatowanie nazwisk, szukanie po kategoriach
        this.values = Arrays
                .stream(value.getString().split("\\|"))
                .map(String::trim)
                .map(personData -> {
                    try {
                        return new PersonValue(personData);
                    } catch (InvalidPersonException e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toArray(IBibtexValue[]::new);
        if (values.length == 0) {
            throw new ParsingException("BiBTeX parser could not find at least one valid person.");
        }
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

}
