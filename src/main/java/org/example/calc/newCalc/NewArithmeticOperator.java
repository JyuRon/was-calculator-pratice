package org.example.calc.newCalc;

public interface NewArithmeticOperator {
    boolean supports(String operator);
    int calculate(PositiveNumber operand1, PositiveNumber operand2);


}
