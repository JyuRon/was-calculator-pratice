package org.example.calc;

import org.example.calc.newCalc.*;

import java.util.List;

public class Calculator {

    private static final List<NewArithmeticOperator> arithmeticOperations = List.of(
            new AdditionOperator(), new SubtractionOperator(), new MultiplicationOperator(), new DivisionOperator()
    );

    public static int calculate(PositiveNumber operand1, String operator, PositiveNumber operand2) {
//        return ArithmeticOperator.calculate(operand1, operator, operand2);
        return arithmeticOperations.stream()
                .filter(arithmeticOperator -> arithmeticOperator.supports(operator))
                .map(arithmeticOperator -> arithmeticOperator.calculate(operand1, operand2))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
