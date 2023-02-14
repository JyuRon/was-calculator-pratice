package org.example.calc.newCalc;

public class PositiveNumber {
    private final int value;

    public PositiveNumber(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if(isNegativeNumver(value)){
            throw new IllegalArgumentException("0또는 음수를 전달할 수 없습니다.");
        }
    }

    private boolean isNegativeNumver(int value) {
        return value<=0;
    }

    public int toInt(){
        return value;
    }
}
