package year2020.puzzle16;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Puzzle16 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> input = new ArrayList<>(Files.readAllLines(Utils.getInputPath("2020/input16.txt")));

        Map<Key, ValidRanges> validRanges = new LinkedHashMap<>();
        ValidRanges allValidRanges = new ValidRanges();

        setupValidRanges(input, validRanges, allValidRanges);

        List<List<Integer>> nearbyTickets = getNearbyTickets(input);

        List<Integer> yourTicket = getYourTicket(input);

        List<List<Integer>> validNearbySeats = solveA(allValidRanges, nearbyTickets);

        solveB(validRanges, yourTicket, validNearbySeats);

    }

    // this is a mess!!!!
    private static void solveB(Map<Key, ValidRanges> validRanges, List<Integer> yourTicket, List<List<Integer>> validNearbySeats) {
        validNearbySeats.add(yourTicket);
        for (Map.Entry<Key, ValidRanges> entry : validRanges.entrySet()) {
            ValidRanges currentRange = entry.getValue();
            for (int i = 0; i < yourTicket.size(); i++) {
                boolean foundError = false;
                for (List<Integer> validNearbySeat : validNearbySeats) {
                    if (!currentRange.isValid(validNearbySeat.get(i))) {
                        foundError = true;
                        break;
                    }
                }
                if (!foundError) {
                    System.out.println("found match " + i + " " + entry.getKey());
                    entry.getKey().addPosition(i);
                } else {
                    System.out.println("found error " + i + " " + entry.getKey());
                }
            }
        }


        Set<Key> keys = validRanges.keySet();
        while (hasElementsWithMoreThanOnePosition(keys)) {
            for (Key key : keys) {
                if (key.getValidPosition().size() == 1) {
                    removePosition(keys, key.getValidPosition().get(0));
                }
            }
        }
        for (Key stringIntegerTuple : keys) {
            System.out.println(stringIntegerTuple);
        }

        long multiple = 1;
        for (Key stringIntegerTuple : keys) {
            if (StringUtils.startsWithIgnoreCase(stringIntegerTuple.getKey(), "departure")) {
                System.out.println(stringIntegerTuple);
                multiple *= yourTicket.get(stringIntegerTuple.getValidPosition().get(0));
            }
        }

        System.out.println(multiple);
    }

    public static void removePosition(Set<Key> keys, int pos) {
        for (Key key : keys) {
            key.removePosition(pos);
        }
    }

    public static boolean hasElementsWithMoreThanOnePosition(Set<Key> keys) {
        for (Key key : keys) {
            if (key.getValidPosition().size() > 1) {
                return true;
            }
        }
        return false;
    }

    private static List<List<Integer>> solveA(ValidRanges allValidRanges, List<List<Integer>> nearbyTickets) {
        int invalidSum = 0;
        List<List<Integer>> validNearbySeats = new ArrayList<>();

        for (List<Integer> number : nearbyTickets) {
            boolean foundInvalid = false;
            for (Integer subNumber : number) {
                if (!allValidRanges.isValid(subNumber)) {
                    invalidSum += subNumber;
                    foundInvalid = true;
                }

            }
            if (!foundInvalid) {
                validNearbySeats.add(number);
            }
        }

        System.out.println(invalidSum);
        return validNearbySeats;
    }

    private static List<Integer> getYourTicket(List<String> input) {
        boolean seenYourTicket = false;
        List<Integer> yourTicket = new ArrayList<>();
        for (String s : input) {
            if (StringUtils.equalsIgnoreCase(s, "your ticket:")) {
                seenYourTicket = true;
                continue;
            }
            if (seenYourTicket) {
                yourTicket.addAll(Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
                break;
            }
        }
        return yourTicket;
    }

    private static List<List<Integer>> getNearbyTickets(List<String> input) {
        boolean seenNearbyTickets = false;
        List<List<Integer>> nearbyTickets = new ArrayList<>();
        for (String s : input) {
            if (StringUtils.equalsIgnoreCase(s, "nearby tickets:")) {
                seenNearbyTickets = true;
                continue;
            }
            if (seenNearbyTickets) {
                nearbyTickets.add(Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
            }
        }
        return nearbyTickets;
    }

    private static void setupValidRanges(List<String> input, Map<Key, ValidRanges> validRanges, ValidRanges allValidRanges) {
        for (String s : input) {
            if (StringUtils.equalsIgnoreCase(s, "your ticket:")) {
                break;
            }

            if (StringUtils.isNotEmpty(s)) {
                validRanges.put(new Key(StringUtils.substringBefore(s, ":")), new ValidRanges(s));
                allValidRanges.addLine(s);
            }
        }
    }
}
