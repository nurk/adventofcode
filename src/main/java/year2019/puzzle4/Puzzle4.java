package year2019.puzzle4;

public class Puzzle4 {

    public static void main(String[] args) {
        Password pp = new Password(111122);
        System.out.println(pp.isValidB());

        int validA = 0;
        int validB = 0;
        for (int i = 264793; i <= 803935; i++) {
            Password p = new Password(i);
            if (p.isValidA()) {
                validA++;
            }
            if (p.isValidB()) {
                validB++;
            }
        }
        System.out.println("Valid A: " + validA);
        System.out.println("Valid B: " + validB);
    }
}
