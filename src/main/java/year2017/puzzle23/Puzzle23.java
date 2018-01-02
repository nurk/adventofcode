package year2017.puzzle23;

import year2017.puzzle23.instructions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Puzzle23 {

    public static void main(String[] args) {
        HashMap<String, Integer> registers = new HashMap<>();
        registers.put("a", 0);
        registers.put("b", 0);
        registers.put("c", 0);
        registers.put("d", 0);
        registers.put("e", 0);
        registers.put("f", 0);
        registers.put("g", 0);
        registers.put("h", 0);

        List<Instruction> instructions = new ArrayList<>();
        instructions.add(new Set( "b", "65"));
        instructions.add(new Set( "c", "b"));
        instructions.add(new Jnz( "a", "2"));
        instructions.add(new Jnz( "1", "5"));
        instructions.add(new Mul( "b", "100"));
        instructions.add(new Sub( "b", "-100000"));
        instructions.add(new Set( "c", "b"));
        instructions.add(new Sub( "c", "-17000"));
        instructions.add(new Set( "f", "1"));
        instructions.add(new Set( "d", "2"));
        instructions.add(new Set( "e", "2"));
        instructions.add(new Set( "g", "d"));
        instructions.add(new Mul( "g", "e"));
        instructions.add(new Sub( "g", "b"));
        instructions.add(new Jnz( "g", "2"));
        instructions.add(new Set( "f", "0"));
        instructions.add(new Sub( "e", "-1"));
        instructions.add(new Set( "g", "e"));
        instructions.add(new Sub( "g", "b"));
        instructions.add(new Jnz( "g", "-8"));
        instructions.add(new Sub( "d", "-1"));
        instructions.add(new Set( "g", "d"));
        instructions.add(new Sub( "g", "b"));
        instructions.add(new Jnz( "g", "-13"));
        instructions.add(new Jnz( "f", "2"));
        instructions.add(new Sub( "h", "-1"));
        instructions.add(new Set( "g", "b"));
        instructions.add(new Sub( "g", "c"));
        instructions.add(new Jnz( "g", "2"));
        instructions.add(new Jnz( "1", "3"));
        instructions.add(new Sub( "b", "-17"));
        instructions.add(new Jnz( "1", "-23"));

        int instructionPosition = 0;

        while(instructionPosition >= 0 && instructionPosition < instructions.size()){
            instructionPosition = instructionPosition + instructions.get(instructionPosition).performReturningInstuctionOffset(registers);
            System.out.println(registers);
        }

        System.out.println(Mul.timesExecuted);
    }
}
