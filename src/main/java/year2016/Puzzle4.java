package year2016;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class Puzzle4 {
    public static void main(String[] args) throws URISyntaxException, IOException {

        System.out.println(new Room("aaaaa-bbb-z-y-x-123[abxyz]").isValidRoom());

        System.out.println(new Room("a-b-c-d-e-f-g-h-987[abcde]").isValidRoom());
        System.out.println(new Room("not-a-real-room-404[oarel]").isValidRoom());
        System.out.println(new Room("totally-real-room-200[decoy]").isValidRoom());
        System.out.println(new Room("qzmt-zixmtkozy-ivhz-343[kj;l]").decrypt());

        AtomicReference<Long> totalSectorIds = new AtomicReference<>(0L);

        Utils.getInput("2016/input4.txt").forEach(
                line -> {
                    Room room = new Room(line);
                    if ("northpole object storage".equals(room.decrypt())) {
                        System.out.println("northpole object storage: " + room.getSectorId());
                    }
                    if (room.isValidRoom()) {
                        totalSectorIds.updateAndGet(v -> v + room.getSectorId());
                    }
                }
        );

        System.out.println(totalSectorIds.get());

    }

    public static class Room {
        private final String checksum;
        private final Integer sectorId;
        private final String encryptedRoomName;
        private Map<String, Integer> characters = new HashMap<>();
        private static final String[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".split("");

        public Room(String name) {
            String sectorIdAndChecksum = StringUtils.substringAfterLast(name, "-");
            sectorId = Integer.valueOf(StringUtils.substringBefore(sectorIdAndChecksum, "["));
            checksum = StringUtils.removeEnd(StringUtils.substringAfterLast(sectorIdAndChecksum, "["), "]");
            AtomicReference<String> codes = new AtomicReference<>("");
            encryptedRoomName = StringUtils.substringBeforeLast(name, "-");
            Arrays.stream(StringUtils.split(StringUtils.substringBeforeLast(name, "-"), "-")).forEach(
                    part -> codes.set(codes + part)
            );

            parseCodes(codes.get());

        }

        private void parseCodes(String codes) {
            Map<String, Integer> unsortedCharacters = new TreeMap<>();
            Arrays.stream(codes.split("")).forEach(
                    s -> {
                        if (unsortedCharacters.containsKey(s)) {
                            Integer i = unsortedCharacters.get(s);
                            unsortedCharacters.put(s, ++i);
                        } else {
                            unsortedCharacters.put(s, 1);
                        }
                    }
            );

            characters =
                    unsortedCharacters.entrySet().stream()
                            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));
        }

        public Integer getSectorId() {
            return sectorId;
        }

        public boolean isValidRoom() {
            int i = 0;
            String[] split = checksum.split("");
            for (String s : characters.keySet()) {
                if (!s.equals(split[i])) {
                    return false;
                }
                i++;
                if (i == 5) {
                    return true;
                }
            }

            return true;
        }

        public String decrypt() {
            return Arrays.stream(encryptedRoomName.split(""))
                    .map(letter -> {
                                if ("-".equals(letter)) {
                                    return " ";
                                } else {
                                    return ALPHABET[(Arrays.binarySearch(ALPHABET, letter) + sectorId) % 26];
                                }
                            }
                    )
                    .collect(Collectors.joining());
        }
    }
}
