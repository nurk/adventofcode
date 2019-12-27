package year2019.puzzle12;

public class Puzzle12A {
    public static void main(String[] args) {
        /*Moon moon1 = new Moon(-1, 0, 2);
        Moon moon2 = new Moon(2, -10, -7);
        Moon moon3 = new Moon(4, -8, 8);
        Moon moon4 = new Moon(3, 5, -1);*/

        Moon moon1 = new Moon(-4, -9, -3);
        Moon moon2 = new Moon(-13, -11, 0);
        Moon moon3 = new Moon(-17, -7, 15);
        Moon moon4 = new Moon(-16, 4, 2);

        for (int i = 0; i < 1000; i++) {
            moon1.applyGravity(moon2, moon3, moon4);
            moon2.applyGravity(moon1, moon3, moon4);
            moon3.applyGravity(moon2, moon1, moon4);
            moon4.applyGravity(moon2, moon3, moon1);
            moon1.applyVelocity();
            moon2.applyVelocity();
            moon3.applyVelocity();
            moon4.applyVelocity();

            /*System.out.println(moon1);
            System.out.println(moon2);
            System.out.println(moon3);
            System.out.println(moon4);*/
        }

        System.out.println(moon1.getEnergy() + moon2.getEnergy() + moon3.getEnergy() + moon4.getEnergy());
    }
}
