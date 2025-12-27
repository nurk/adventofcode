package year2017.puzzle8;

import java.util.Map;

public class Instruction {

    private final String operationRegister;
    private final String operation;
    private final long operationValue;
    private final String conditionRegister;
    private final String conditionOperator;
    private final long conditionValue;

    public Instruction(String line) {
        String[] parts = line.split(" if ");
        String[] operationParts = parts[0].split(" ");
        String[] conditionParts = parts[1].split(" ");
        this.operationRegister = operationParts[0];
        this.operation = operationParts[1];
        this.operationValue = Long.parseLong(operationParts[2]);
        this.conditionRegister = conditionParts[0];
        this.conditionOperator = conditionParts[1];
        this.conditionValue = Long.parseLong(conditionParts[2]);
    }

    public void performInstruction(Map<String, Long> registers) {
        registers.putIfAbsent(operationRegister, 0L);
        registers.putIfAbsent(conditionRegister, 0L);

        long conditionRegisterValue = registers.get(conditionRegister);
        boolean conditionMet = switch (conditionOperator) {
            case ">" -> conditionRegisterValue > conditionValue;
            case "<" -> conditionRegisterValue < conditionValue;
            case ">=" -> conditionRegisterValue >= conditionValue;
            case "<=" -> conditionRegisterValue <= conditionValue;
            case "==" -> conditionRegisterValue == conditionValue;
            case "!=" -> conditionRegisterValue != conditionValue;
            default -> throw new IllegalArgumentException("Unknown operator: " + conditionOperator);
        };

        if (conditionMet) {
            long currentValue = registers.get(operationRegister);
            if (operation.equals("inc")) {
                registers.put(operationRegister, currentValue + operationValue);
            } else if (operation.equals("dec")) {
                registers.put(operationRegister, currentValue - operationValue);
            } else {
                throw new IllegalArgumentException("Unknown operation: " + operation);
            }
        }
    }

    @Override
    public String toString() {
        return "Instruction{" +
                "operationRegister='" + operationRegister + '\'' +
                ", operation='" + operation + '\'' +
                ", operationValue=" + operationValue +
                ", conditionRegister='" + conditionRegister + '\'' +
                ", conditionOperator='" + conditionOperator + '\'' +
                ", conditionValue=" + conditionValue +
                '}';
    }
}
