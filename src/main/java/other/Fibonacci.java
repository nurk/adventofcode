package other;

public class Fibonacci {

    public static void main(String[] args) {
        int x = 0;
        int y = 0;
        int z = 1;
        System.out.println(x);

        while (x < 300) {
            x = z + y;
            z = y;
            y = x;
            System.out.println(x);
        }
    }
}
