package org.jfree.data.test.JunitTest;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;

public class RangeTest {

    private Range range;
    private Range rangeNegative;
    private Range rangePositive;

    @Before
    public void setUp() throws Exception {
        range = new Range(-5, 5);
        rangeNegative = new Range(-10, -1);
        rangePositive = new Range(1, 10);
    }


    @Test
    public void testGetLowerBound() {
        assertEquals(-5, range.getLowerBound(), 0.0001);
    }

    @Test
    public void testGetUpperBound() {
        assertEquals(5, range.getUpperBound(), 0.0001);
    }

    @Test
    public void testGetLength() {
        assertEquals(10, range.getLength(), 0.0001);
    }

    @Test
    public void testGetCentralValue() {
        assertEquals(0, range.getCentralValue(), 0.0001);
    }

    @Test
    public void testContains() {
        assertTrue(range.contains(0));
        assertFalse(range.contains(-10));
        assertFalse(range.contains(10));
    }

    @Test
    public void testConstrain() {
        assertEquals(0, range.constrain(0), 0.0001);
        assertEquals(-5, range.constrain(-10), 0.0001);
        assertEquals(5, range.constrain(10), 0.0001);
    }

    @Test
    public void testIntersects() {
        assertTrue(range.intersects(-3, 3));
        assertTrue(range.intersects(-5, 0));
        assertFalse(range.intersects(-10, -6));
        assertFalse(range.intersects(6, 10));
    }

    @Test
    public void testGetCentralValueForDifferentRanges() {
        assertEquals(0, range.getCentralValue(), 0.0001);

        assertEquals(-5.5, rangeNegative.getCentralValue(), 0.0001);

        assertEquals(5.5, rangePositive.getCentralValue(), 0.0001);

        Range singlePointRange = new Range(3, 3);
        assertEquals(3, singlePointRange.getCentralValue(), 0.0001);
    }

    
    @Test
    public void testExpandToInclude() {
        Range expanded = Range.expandToInclude(range, 10);
        assertEquals(10, expanded.getUpperBound(), 0.0001);

        expanded = Range.expandToInclude(range, -10);
        assertEquals(-10, expanded.getLowerBound(), 0.0001);
    }

    @Test
    public void testExpand() {
        Range expanded = Range.expand(range, 0.1, 0.2);
        assertEquals(-6, expanded.getLowerBound(), 0.0001);
        assertEquals(6, expanded.getUpperBound(), 0.0001);
    }

    @Test
    public void testShift() {
        Range shifted = Range.shift(range, 2);
        assertEquals(-3, shifted.getLowerBound(), 0.0001);
        assertEquals(7, shifted.getUpperBound(), 0.0001);
    }

    @Test
    public void testShiftWithZeroCrossing() {
        Range shifted = Range.shift(range, -10, false);
        assertEquals(0, shifted.getLowerBound(), 0.0001);
    }

    @Test
    public void testEquals() {
        Range sameRange = new Range(-5, 5);
        assertTrue(range.equals(sameRange));

        Range differentRange = new Range(-10, 10);
        assertFalse(range.equals(differentRange));
    }

    @Test
    public void testHashCode() {
        Range sameRange = new Range(-5, 5);
        assertEquals(range.hashCode(), sameRange.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("Range[-5.0,5.0]", range.toString());
    }
}
