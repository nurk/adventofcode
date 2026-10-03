package year2018.puzzle12;

public class Rule {
    String pattern;
    String result;

    public Rule(String line) {
        String[] parts = line.split(" => ");
        this.pattern = parts[0];
        this.result = parts[1];
    }

    @Override
    public String toString() {
        return "Rule{" +
                "pattern='" + pattern + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
