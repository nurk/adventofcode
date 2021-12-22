package year2021.puzzle22;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

class BoardB {
    private static final List<Volume> placedVolumes = new ArrayList<>();

    public void doStep(String step) {
        String[] split = StringUtils.split(step, " ");

        boolean on = split[0].equals("on");
        String[] coords = StringUtils.split(split[1], ",");
        Volume addedVolume = new Volume(getFromTo(coords[0]), getFromTo(coords[1]), getFromTo(coords[2]), on);

        doStep(addedVolume);
    }

    private void doStep(Volume addedVolume) {
        List<Volume> volumesToBePlaced = new ArrayList<>();
        if (addedVolume.isOn()) {
            volumesToBePlaced.add(addedVolume);
        }
        placedVolumes.forEach(placedVolume -> {
            if (placedVolume.intersects(addedVolume)) {
                volumesToBePlaced.add(placedVolume.getIntersection(addedVolume, !placedVolume.isOn()));
            }
        });
        placedVolumes.addAll(volumesToBePlaced);
    }

    public long countOn() {
        return placedVolumes.stream()
                .mapToLong(Volume::getVolume)
                .sum();
    }

    private Pair<Integer, Integer> getFromTo(String s) {
        String[] split = StringUtils.split(StringUtils.substringAfter(s, "="), "..");
        int a = Integer.parseInt(split[0]);
        int b = Integer.parseInt(split[1]);
        return Pair.of(a, b);
    }
}
