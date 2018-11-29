package values;

import exceptions.ParsingException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MultipleValueTest {

    @Test(expected = ParsingException.class)
    public void zeroValidPeopleTest() throws ParsingException {
        StringValue testData = new StringValue("AAA and BBB B. B. BBB and CCC|");
        MultipleValue multiple = new MultipleValue(testData);
    }

    @Test(expected = ParsingException.class)
    public void zeroPeopleTest() throws ParsingException {
        StringValue testData = new StringValue("");
        MultipleValue multiple = new MultipleValue(testData);
    }

    @Test
    public void singlePersonTest() throws ParsingException {
        StringValue testData = new StringValue("Aaaa B. Cccc");
        MultipleValue multiple = new MultipleValue(testData);
    }

    @Test
    public void singleValidPersonTest() throws ParsingException {
        StringValue testData = new StringValue("Aaaa B. Cccc and Dddd and Eeee and ");
        MultipleValue multiple = new MultipleValue(testData);
        assertEquals(1, multiple.getValues().length);
        assertEquals("Aaaa B. Cccc", multiple.getValues()[0].getString());
    }

    @Test
    public void fewValidPeopleTest() throws ParsingException {
        StringValue testData = new StringValue("Aaa|Bbb and C and D. E. Fff and Ggg|Hhh and Iii");
        MultipleValue multiple = new MultipleValue(testData);
        assertEquals(3, multiple.getValues().length);
        assertEquals("Bbb Aaa", multiple.getValues()[0].getString());
        assertEquals("D. E. Fff", multiple.getValues()[1].getString());
        assertEquals("Hhh Ggg", multiple.getValues()[2].getString());
    }
}