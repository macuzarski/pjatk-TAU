import org.example.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void testAddition() {
        assertEquals(5, calculator.add(2, 3), "Addition result should be 5");
    }

    @Test
    public void testSubtraction() {
        assertEquals(1, calculator.subtract(4, 3), "Subtraction result should be 1");
    }

    @Test
    public void testMultiplication() {
        assertEquals(12, calculator.multiply(4, 3), "Multiplication result should be 12");
    }

    @Test
    public void testDivision() {
        assertEquals(2.0, calculator.divide(4, 2), "Division result should be 2.0");
    }

    @Test
    public void testDivisionByZero() {
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(4, 0), "Division by zero should throw an exception");
    }

    @Test
    public void testModulus() {
        assertEquals(1, calculator.modulus(7, 3), "Modulus result should be 1");
    }

    @Test
    public void testNegativeAddition() {
        assertEquals(-5, calculator.add(-2, -3), "Addition with negatives should be -5");
    }

    @Test
    public void testMultiplicationByZero() {
        assertEquals(0, calculator.multiply(4, 0), "Multiplying by zero should return 0");
    }

    @Test
    public void testNegativeMultiplication() {
        assertEquals(-12, calculator.multiply(-4, 3), "Multiplication of -4 and 3 should be -12");
    }

    @Test
    public void testDivisionResultPrecision() {
        assertTrue(Math.abs(calculator.divide(10, 3) - 3.3333) < 0.0001, "Division result should be close to 3.3333");
    }
}
