package year2021.puzzle24;

import util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Puzzle24 {
    public static void main(String[] args) {
        List<String> operations = new ArrayList<>(Utils.getInput("2021/input24.txt", (s) -> s));

        ModelNumber modelNumber = new ModelNumber("99999999999999");


        while (!doSequence(operations, modelNumber)) {
            long number = Long.parseLong(modelNumber.getModelNumber());
            number--;
            while (String.valueOf(number).length() != 14 || String.valueOf(number).contains("0")) {
                number--;
            }
            modelNumber = new ModelNumber(String.valueOf(number));
            System.out.println(modelNumber.getModelNumber());
        }

        System.out.println(modelNumber.getModelNumber());
    }

    private static boolean doSequence(List<String> operations, ModelNumber modelNumber) {
        Map<String, Long> register = new HashMap<>();
        register.put("x", 0L);
        register.put("y", 0L);
        register.put("z", 0L);
        register.put("w", 0L);

        operations.forEach(operation -> {
            String[] split = operation.split(" ");
            Alu alu = Alu.valueOf(split[0]);
            List<Long> numbers = new ArrayList<>();
            for (int i = 1; i < split.length; i++) {
                if (isNumber(split[i])) {
                    numbers.add(Long.parseLong(split[i]));
                } else {
                    numbers.add(register.get(split[i]));
                }
            }
            long result = alu.operation(numbers, modelNumber);
            register.put(split[1], result);
        });

        //System.out.println(register);
        return register.get("z") == 0;
    }

    private static boolean isNumber(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
