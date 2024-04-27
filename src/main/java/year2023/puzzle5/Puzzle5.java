package year2023.puzzle5;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.*;
import java.util.stream.LongStream;

/**
 * Part A: 51752125
 * Part B: 12634632   (just be patient and brute force it
 */
public class Puzzle5 {

    private static List<AlmanacMap> seedToSoil;
    private static List<AlmanacMap> soilToFertilizer;
    private static List<AlmanacMap> fertilizerToWater;
    private static List<AlmanacMap> waterToLight;
    private static List<AlmanacMap> lightToTemperature;
    private static List<AlmanacMap> temperatureToHumidity;
    private static List<AlmanacMap> humidityToLocation;

    public static void main(String[] args) {
        List<String> input = Utils.getInput("2023/input5.txt");

        seedToSoil = parse(input, "seed-to-soil map:");
        soilToFertilizer = parse(input, "soil-to-fertilizer map:");
        fertilizerToWater = parse(input, "fertilizer-to-water map:");
        waterToLight = parse(input, "water-to-light map:");
        lightToTemperature = parse(input, "light-to-temperature map:");
        temperatureToHumidity = parse(input, "temperature-to-humidity map:");
        humidityToLocation = parse(input, "humidity-to-location map:");

        partA(input);
        partB(input);
    }

    private static void partB(List<String> input) {
        List<Long> seeds = Arrays.stream(StringUtils.substringAfter(input.getFirst(), ": ").split(" "))
                .map(Long::valueOf)
                .toList();

        Set<Long> locations = new HashSet<>();
        for (int i = 0; i < seeds.size(); i = i + 2) {
            Long seed = seeds.get(i);
            Long range = seeds.get(i + 1);

            System.out.println("Started seed: " + seed);
            locations.add(LongStream.rangeClosed(seed, seed + range - 1)
                    .peek(l -> {
                        if (l % 10000000 == 0) {
                            System.out.println("parsed: " + (l - seed));
                        }
                    })
                    .map(Puzzle5::getLocation)
                    .min()
                    .orElseThrow());
            System.out.println("Ended seed: " + seed);
        }

        Long loc = locations.stream()
                .min(Long::compareTo)
                .orElseThrow();

        System.out.println("Part B: " + loc);
    }

    private static void partA(List<String> input) {
        List<Long> seeds = Arrays.stream(StringUtils.substringAfter(input.getFirst(), ": ").split(" "))
                .map(Long::valueOf)
                .toList();

        Long loc = seeds.stream()
                .map(Puzzle5::getLocation)
                .min(Long::compare)
                .orElseThrow();
        System.out.println("Part A: " + loc);
    }

    private static Long getLocation(Long element) {
        Long soil = mapItem(element, seedToSoil);
        Long fertilizer = mapItem(soil, soilToFertilizer);
        Long water = mapItem(fertilizer, fertilizerToWater);
        Long light = mapItem(water, waterToLight);
        Long temperature = mapItem(light, lightToTemperature);
        Long humidity = mapItem(temperature, temperatureToHumidity);
        return mapItem(humidity, humidityToLocation);
    }

    private static Long mapItem(Long element, List<AlmanacMap> almanacMaps) {
        return almanacMaps.stream()
                .filter(map -> map.matches(element))
                .findFirst()
                .map(almanacMap -> almanacMap.getDestination(element))
                .orElse(element);
    }

    private static List<AlmanacMap> parse(List<String> input, String s) {
        List<AlmanacMap> result = new ArrayList<>();

        boolean parse = false;

        for (String string : input) {
            if (string.contains(s)) {
                parse = true;
                continue;
            }

            if (parse && StringUtils.isEmpty(string)) {
                break;
            }

            if (parse) {
                String[] s1 = string.split(" ");
                result.add(new AlmanacMap(Long.valueOf(s1[1]), Long.valueOf(s1[0]), Long.valueOf(s1[2])));
            }
        }

        return result;
    }
}
