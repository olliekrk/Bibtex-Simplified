package values;

import exceptions.InvalidPersonException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class PersonValueTest {
    private final String[] testPeople = {
            "Mark B. Spark",
            "Clark Kent",
            "Reynolds|Ryan",
            "Faith von Humanity",
            "Lara de Croft",
            "O. K. Rolik",
            "Potter | Harry",
            "Potter| Harry",
            "Potter |Harry",
            "Potter|Harry",
            "Potter A. Plotter | Harry",
    };
    private final String[] testLastNames = {
            "Spark",
            "Kent",
            "Reynolds",
            "Humanity",
            "Croft",
            "Rolik",
            "Potter",
            "Potter",
            "Potter",
            "Potter",
            "Potter A. Plotter"
    };

    @Test
    public void getLastNameCorrectTest() throws InvalidPersonException {
        for (int i = 0; i < testPeople.length; i++) {
            assertEquals(testLastNames[i], new PersonValue(testPeople[i]).getLastName());
        }
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void incorrectPersonTest() throws InvalidPersonException {
        exception.expect(InvalidPersonException.class);
        exception.expectMessage(new InvalidPersonException("Arnold O. O. O.").getMessage());
        PersonValue pv = new PersonValue("Arnold O. O. O.");
    }
}