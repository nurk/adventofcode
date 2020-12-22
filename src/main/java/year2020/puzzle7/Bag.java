package year2020.puzzle7;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bag {

    private final String color;
    private final Map<String, Integer> contents = new HashMap<>();

    public Bag(String definition) {
        definition = StringUtils.removeEnd(definition, ".");
        color = StringUtils.removeEnd(StringUtils.substringBefore(definition, " contain "), " bags");

        if (!StringUtils.substringAfter(definition, " contain ").equals("no other bags")) {
            String[] content = StringUtils.splitByWholeSeparator(StringUtils.substringAfter(definition, " contain "),
                    ", ");
            for (String s : content) {
                contents.put(
                        StringUtils.remove(StringUtils.remove(StringUtils.substringAfter(s, " "), " bags"), " bag"),
                        Integer.parseInt(StringUtils.substringBefore(s, " ")));
            }
        }
    }

    public boolean callHoldColor(String testColor) {
        return (contents.containsKey(testColor));
    }

    public String getColor() {
        return color;
    }

    public long countBags(List<Bag> bags) {
        long innerbags = 1;
        for (Map.Entry<String, Integer> entry : contents.entrySet()) {
            innerbags = innerbags + entry.getValue() * findBag(bags, entry.getKey()).countBags(bags);
        }

        return innerbags;
    }

    private Bag findBag(List<Bag> bags, String color) {
        for (Bag bag : bags) {
            if (bag.getColor().equals(color)) {
                return bag;
            }
        }
        return null;
    }
}
