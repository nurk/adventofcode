package year2023.puzzle3;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class Place {

    private final String id;
    private final String value;

    public Place(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public boolean isNumeric() {
        return StringUtils.isNumeric(value);
    }

    public boolean isSymbol() {
        return !isNumeric() && !".".equals(value);
    }

    public boolean isGear() {
        return "*".equals(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Place place = (Place) o;

        return id.equals(place.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
