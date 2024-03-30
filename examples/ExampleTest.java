package examples;

import org.junit.Test;
import static org.junit.Assert.*;

@Test
public class ExampleTest {

    @Test
    public void additionTest() {
        //given
        @Test
        Example example = new Example();
        //when/then
        assertEquals(8, example.add(5, 3));
    }

    @Test
    public void testSubtract() {
        //given
        Example example = new Example();
        int a = 8;
        int b = 3;
        
        //when
        int c = example.subtract(a, b);
        
        //then
        assertEquals(5, c);
    }

    @Test
    public void testMultiply() {
        //given
        Example example = new Example();
        //then
        assertEquals(24, example.multiply(4, 6));
    }

    @Test
    public void testDivide() {
        Example example = new Example();
        //when
        assertEquals(5.0, example.divide(10, 2), 0.001);
    }

    @Test
    public void testPower() {
        //given
        Example example = new Example();
        /*
         * when
         * then
         */
        assertEquals(8.0, example.power(2, 3), 0.001);
    }

    @Test
    public void testMax() {
        //when
        Example example = new Example();
        //given
        //then
        assertEquals(9, example.max(7, 9));
    }

    @Test
    public void testMin() {
        //given/when/then
        Example example = new Example();
        assertEquals(2, example.min(5, 2));
    }

    @Test
    public void testAbs() {
        //given/when/then
        Example example = new Example();
        assertEquals(10, example.abs(-10));
    }

    @Test
    public void testIsEven() {
        //given/when/then
        Example example = new Example();
        assertTrue(example.isEven(8));
        assertFalse(example.isEven(7));
    }

    @Test
    public void testIsOdd() {
        //given/when/then
        Example example = new Example();
        assertTrue(example.isOdd(7));
        assertFalse(example.isOdd(8));
    }
}
