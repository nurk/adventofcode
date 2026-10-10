package year2021.puzzle16;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Packet {

    private long version;
    private long typeId;
    private long literalValue;
    private final List<Packet> subPackets = new ArrayList<>();

    public long getVersionSum() {
        long sum = version;
        for (Packet subPacket : subPackets) {
            sum += subPacket.getVersionSum();
        }
        return sum;
    }


    private static Long binaryToLong(String binaryInput) {
        return Long.parseLong(binaryInput, 2);
    }

    public static Pair<String, Packet> parsePacket(String binaryInput) {
        long version = binaryToLong(binaryInput.substring(0, 3));
        long typeId = binaryToLong(binaryInput.substring(3, 6));

        Packet packet = new Packet();
        packet.version = version;
        packet.typeId = typeId;

        if (typeId == 4) {
            // Literal value packet
            StringBuilder literalBinary = new StringBuilder();
            int index = 6;
            while (true) {
                String group = binaryInput.substring(index, index + 5);
                literalBinary.append(group.substring(1));
                index += 5;
                if (group.charAt(0) == '0') {
                    break;
                }
            }
            packet.literalValue = binaryToLong(literalBinary.toString());
            return Pair.of(binaryInput.substring(index), packet);
        } else {
            // Operator packet
            char lengthTypeId = binaryInput.charAt(6);
            if (lengthTypeId == '0') {
                long totalLengthInBits = binaryToLong(binaryInput.substring(7, 22));
                String subPacketsBinary = binaryInput.substring(22, 22 + (int) totalLengthInBits);
                while (!subPacketsBinary.isEmpty()) {
                    Pair<String, Packet> result = parsePacket(subPacketsBinary);
                    packet.subPackets.add(result.getRight());
                    subPacketsBinary = result.getLeft();
                }
                return Pair.of(binaryInput.substring(22 + (int) totalLengthInBits), packet);
            } else {
                long numberOfSubPackets = binaryToLong(binaryInput.substring(7, 18));
                String subPacketsBinary = binaryInput.substring(18);
                for (int i = 0; i < numberOfSubPackets; i++) {
                    Pair<String, Packet> result = parsePacket(subPacketsBinary);
                    packet.subPackets.add(result.getRight());
                    subPacketsBinary = result.getLeft();
                }
                return Pair.of(subPacketsBinary, packet);
            }
        }
    }

    public long evaluate() {
        return switch ((int) typeId) {
            case 0 -> subPackets.stream().mapToLong(Packet::evaluate).sum();
            case 1 -> subPackets.stream().mapToLong(Packet::evaluate).reduce(1, (a, b) -> a * b);
            case 2 -> subPackets.stream().mapToLong(Packet::evaluate).min().orElseThrow();
            case 3 -> subPackets.stream().mapToLong(Packet::evaluate).max().orElseThrow();
            case 4 -> literalValue;
            case 5 -> subPackets.get(0).evaluate() > subPackets.get(1).evaluate() ? 1 : 0;
            case 6 -> subPackets.get(0).evaluate() < subPackets.get(1).evaluate() ? 1 : 0;
            case 7 -> subPackets.get(0).evaluate() == subPackets.get(1).evaluate() ? 1 : 0;
            default -> throw new IllegalStateException("Unexpected value: " + typeId);
        };
    }
}
