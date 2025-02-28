package org.jfree.data.test.MockingTest;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jfree.data.KeyedValues;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.*;

/**
 * Unit tests for DataUtilities using jMock.
 */
public class DataUtilitiesTest {

    private Mockery mockingContext;
    private Values2D mockValues2D;
    private KeyedValues mockKeyedValues;

    @Before
    public void setUp() {
        // Initialize JMock context
        mockingContext = new Mockery();
        mockValues2D = mockingContext.mock(Values2D.class);
        mockKeyedValues = mockingContext.mock(KeyedValues.class);
    }

    /** 
     * Test calculateColumnTotal() with positive values.
     */
    @Test
    public void test1CalculateColumnTotal() {
        mockingContext.checking(new Expectations() {{
            allowing(mockValues2D).getRowCount(); will(returnValue(2));
            allowing(mockValues2D).getValue(0, 0); will(returnValue(7.5));
            allowing(mockValues2D).getValue(1, 0); will(returnValue(2.5));
        }});

        double result = DataUtilities.calculateColumnTotal(mockValues2D, 0);
        assertEquals("Column total should be 10.0", 10.0, result, .000000001d);
    }

    /** 
     * Test calculateColumnTotal() with negative and positive values.
     */
    @Test
    public void test2CalculateColumnTotalNegPos() {
        mockingContext.checking(new Expectations() {{
            allowing(mockValues2D).getRowCount(); will(returnValue(2));
            allowing(mockValues2D).getValue(0, 0); will(returnValue(-5.0));
            allowing(mockValues2D).getValue(1, 0); will(returnValue(10.0));
        }});

        double result = DataUtilities.calculateColumnTotal(mockValues2D, 0);
        assertEquals("Column total should be 5.0", 5.0, result, .000000001d);
    }

    /** 
     * Test calculateColumnTotal() with all negative values.
     */
    @Test
    public void test3CalculateColumnTotalTwoNeg() {
        mockingContext.checking(new Expectations() {{
            allowing(mockValues2D).getRowCount(); will(returnValue(2));
            allowing(mockValues2D).getValue(0, 0); will(returnValue(-3.0));
            allowing(mockValues2D).getValue(1, 0); will(returnValue(-7.0));
        }});

        double result = DataUtilities.calculateColumnTotal(mockValues2D, 0);
        assertEquals("Column total should be -10.0", -10.0, result, .000000001d);
    }

    /** 
     * Test calculateColumnTotal() when row count is zero.
     */
    @Test
    public void test4CalculateColumnTotalZeroRows() {
        mockingContext.checking(new Expectations() {{
            allowing(mockValues2D).getRowCount(); will(returnValue(0));
        }});

        double result = DataUtilities.calculateColumnTotal(mockValues2D, 0);
        assertEquals("Column total should be 0.0", 0.0, result, .000000001d);
    }

    /** 
     * Test calculateColumnTotal() with null data (should throw an exception).
     */
    @Test
    public void testCalculateColumnTotalNull() {
        try {
            DataUtilities.calculateColumnTotal(null, 0);
            fail("Expected an exception to be thrown");
        } catch (IllegalArgumentException | NullPointerException e) {
            assertTrue("Caught expected exception: " + e.getClass().getSimpleName(), 
                       e instanceof IllegalArgumentException || e instanceof NullPointerException);
        }
    }

    /** 
     * Test calculateRowTotal() with positive values.
     */
    @Test
    public void test1CalculateRowTotal() {
        mockingContext.checking(new Expectations() {{
            allowing(mockValues2D).getColumnCount(); will(returnValue(3));
            allowing(mockValues2D).getValue(0, 0); will(returnValue(1.0));
            allowing(mockValues2D).getValue(0, 1); will(returnValue(2.0));
            allowing(mockValues2D).getValue(0, 2); will(returnValue(3.0));
        }});

        double result = DataUtilities.calculateRowTotal(mockValues2D, 0);
        assertEquals("Row total should be 6.0", 6.0, result, .000000001d);
    }

    /** 
     * Test calculateRowTotal() with mixed positive and negative values.
     */
    @Test
    public void test2CalculateRowTotalNegPosValue() {
        mockingContext.checking(new Expectations() {{
            allowing(mockValues2D).getColumnCount(); will(returnValue(2));
            allowing(mockValues2D).getValue(0, 0); will(returnValue(-5.0));
            allowing(mockValues2D).getValue(0, 1); will(returnValue(8.0));
        }});

        double result = DataUtilities.calculateRowTotal(mockValues2D, 0);
        assertEquals("Row total should be 3.0", 3.0, result, .000000001d);
    }

    /** 
     * Test createNumberArray() method.
     */
    @Test
    public void testCreateNumberArray() {
        double[] input = {1.1, 2.2, 3.3};
        Number[] result = DataUtilities.createNumberArray(input);

        assertNotNull("Result array should not be null", result);
        assertEquals("Array length should match", input.length, result.length);
        
        for (int i = 0; i < input.length; i++) {
            assertNotNull("Element should not be null at index " + i, result[i]);
            assertEquals("Element should match at index " + i, input[i], result[i].doubleValue(), .000000001d);
        }
    }

    /** 
     * Test createNumberArray2D() when input is null (should throw an exception).
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumberArray2DNull() {
        DataUtilities.createNumberArray2D(null);
    }

    /** 
     * Test getCumulativePercentages() using mocked KeyedValues.
     */
    @Test
    public void testGetCumulativePercentages() {
        mockingContext.checking(new Expectations() {{
            allowing(mockKeyedValues).getItemCount(); will(returnValue(3));

            allowing(mockKeyedValues).getKey(0); will(returnValue(0));
            allowing(mockKeyedValues).getKey(1); will(returnValue(1));
            allowing(mockKeyedValues).getKey(2); will(returnValue(2));

            allowing(mockKeyedValues).getValue(0); will(returnValue(5.0));
            allowing(mockKeyedValues).getValue(1); will(returnValue(9.0));
            allowing(mockKeyedValues).getValue(2); will(returnValue(2.0));
        }});

        KeyedValues result = DataUtilities.getCumulativePercentages(mockKeyedValues);
        assertNotNull("Result should not be null", result);

        double total = 5.0 + 9.0 + 2.0;
        double[] expectedValues = {
            5.0 / total, 
            (5.0 + 9.0) / total, 
            1.0
        };

        for (int i = 0; i < expectedValues.length; i++) {
            assertNotNull("Value at index " + i + " should not be null", result.getValue(i));
            assertEquals("Value at index " + i + " should match expected cumulative percentage", 
                         expectedValues[i], result.getValue(i).doubleValue(), .000000001d);
        }
    }
    
    

   
}
