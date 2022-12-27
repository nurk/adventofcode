package year2015.puzzle20;

public class Puzzle20 {

    public static void main(String[] args) {
        int presents = 29000000;
        int maxElves = presents / 10;

        int[] houses = new int[maxElves];

        for (int elf = 1; elf < maxElves; elf++) {
            for (int atHouseNumber = elf; atHouseNumber < maxElves; atHouseNumber += elf) {
                houses[atHouseNumber] += elf * 10;
            }
        }

        int finalHouse = 0;
        for (int houseNumber = 0; houseNumber < maxElves; houseNumber++) {
            if (houses[houseNumber] >= presents) {
                finalHouse = houseNumber;
                break;
            }
        }

        System.out.println("Part A: " + finalHouse);

        houses = new int[maxElves];

        for (int elf = 1; elf < maxElves; elf++) {
            for (int atHouseNumber = elf; atHouseNumber <= elf * 50 && atHouseNumber < maxElves; atHouseNumber += elf) {
                houses[atHouseNumber] += elf * 11;
            }
        }

        finalHouse = 0;
        for (int houseNumber = 0; houseNumber < maxElves; houseNumber++) {
            if (houses[houseNumber] >= presents) {
                finalHouse = houseNumber;
                break;
            }
        }
        System.out.println("Part B: " + finalHouse);
    }
}
