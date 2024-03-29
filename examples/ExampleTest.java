package examples;

import org.junit.Test;
import static org.junit.Assert.*;

@Test
public class ExampleTest {

    @Test
    public void additionTest() {
        @Test
        Example example = new Example();
        assertEquals(8, example.add(5, 3));
    }

    @Test
    public void testSubtract() {
        Example example = new Example();
        assertEquals(5, example.subtract(8, 3));
    }

    @Test
    public void testMultiply() {
        Example example = new Example();
        assertEquals(24, example.multiply(4, 6));
    }

    @Test
    public void testDivide() {
        Example example = new Example();
        assertEquals(5.0, example.divide(10, 2), 0.001);
    }

    @Test
    public void testPower() {
        Example example = new Example();
        assertEquals(8.0, example.power(2, 3), 0.001);
    }

    @Test
    public void testMax() {
        Example example = new Example();
        assertEquals(9, example.max(7, 9));
    }

    @Test
    public void testMin() {
        Example example = new Example();
        assertEquals(2, example.min(5, 2));
    }

    @Test
    public void testAbs() {
        Example example = new Example();
        assertEquals(10, example.abs(-10));
    }

    @Test
    public void testIsEven() {
        Example example = new Example();
        assertTrue(example.isEven(8));
        assertFalse(example.isEven(7));
    }

    @Test
    public void testIsOdd() {
        Example example = new Example();
        assertTrue(example.isOdd(7));
        assertFalse(example.isOdd(8));
    }
}
