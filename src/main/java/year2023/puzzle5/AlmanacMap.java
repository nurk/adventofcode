package year2023.puzzle5;

public class AlmanacMap {

    private final Long source;
    private final Long destination;
    private final Long length;

    public AlmanacMap(Long source, Long destination, Long length) {
        this.source = source;
        this.destination = destination;
        this.length = length;
    }

    public boolean matches(Long element) {
        if (element >= source && element < source + length) {
            return true;
        }
        return false;
    }

    public Long getDestination(Long element){
        return destination + (element - source);
    }

    @Override
    public String toString() {
        return "AlmanacMap{" +
                "source=" + source +
                ", destination=" + destination +
                ", length=" + length +
                '}';
    }
}
