package org.jfree.data.test.JunitTest;

import static org.junit.Assert.*;
import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.DefaultKeyedValues2D;
import org.junit.*;

public class DataUtilitiesTest {

    private DefaultKeyedValues2D data2D;
    private DefaultKeyedValues keyedValues;

    @Before
    public void setUp() {
        data2D = new DefaultKeyedValues2D();
        data2D.addValue(5.0, 0, 0);
        data2D.addValue(9.0, 1, 0);
        data2D.addValue(2.0, 2, 0);

        keyedValues = new DefaultKeyedValues();
        keyedValues.addValue((Integer) 0, 5.0);
        keyedValues.addValue((Integer) 1, 9.0);
        keyedValues.addValue((Integer) 2, 2.0);
    }

    @Test
    public void testCalculateColumnTotal() {
        double total = DataUtilities.calculateColumnTotal(data2D, 0);
        assertEquals("Column total should be 16", 16.0, total, 0.0001);
    }

    @Test
    public void testCalculateRowTotal() {
        double total = DataUtilities.calculateRowTotal(data2D, 0);
        assertEquals("Row total should be 5", 5.0, total, 0.0001);
    }

    @Test
    public void testCreateNumberArray() {
        double[] input = {1.0, 2.0, 3.0};
        Number[] result = DataUtilities.createNumberArray(input);
        assertArrayEquals(new Number[]{1.0, 2.0, 3.0}, result);
    }

    @Test
    public void testCreateNumberArray2D() {
        double[][] input = {{1.0, 2.0}, {3.0, 4.0}};
        Number[][] result = DataUtilities.createNumberArray2D(input);
        assertArrayEquals(new Number[][]{{1.0, 2.0}, {3.0, 4.0}}, result);
    }

    @Test
    public void testGetCumulativePercentages() {
        DefaultKeyedValues percentages = (DefaultKeyedValues) DataUtilities.getCumulativePercentages(keyedValues);
        assertEquals("Cumulative percentage of key 0", 0.3125, percentages.getValue(0));
        assertEquals("Cumulative percentage of key 1", 0.875, percentages.getValue(1));
        assertEquals("Cumulative percentage of key 2", 1.0, percentages.getValue(2));
    }

    @After
    public void tearDown() {
        data2D = null;
        keyedValues = null;
    }
}
