package examples;

public class Example {
    
    // Method 1: Addition
    public int add(int a, int b) {
        return a + b;
    }
    
    // Method 2: Subtraction
    public int subtract(int a, int b) {
        return a - b;
    }
    
    // Method 3: Multiplication
    public int multiply(int a, int b) {
        return a * b;
    }
    
    // Method 4: Division
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }
    
    // Method 5: Power
    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }
    
    // Method 6: Maximum of two numbers
    public int max(int a, int b) {
        return Math.max(a, b);
    }
    
    // Method 7: Minimum of two numbers
    public int min(int a, int b) {
        return Math.min(a, b);
    }
    
    // Method 8: Absolute value
    public int abs(int a) {
        return Math.abs(a);
    }
    
    // Method 9: Check if a number is even
    public boolean isEven(int number) {
        return number % 2 == 0;
    }
    
    // Method 10: Check if a number is odd
    public boolean isOdd(int number) {
        return number % 2 != 0;
    }
}
