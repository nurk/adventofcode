package year2025.puzzle11;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Device {
    @Getter
    private final String input;
    @Getter
    private final List<String> outputs;

    public Device(String line) {
        input = StringUtils.substringBefore(line, ": ");
        outputs = List.of(StringUtils.substringAfter(line, ": ").trim().split(" "));
    }

    public Device(String input, List<String> outputs) {
        this.input = input;
        this.outputs = outputs;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Device device = (Device) o;
        return input.equals(device.input) && outputs.equals(device.outputs);
    }

    @Override
    public int hashCode() {
        int result = input.hashCode();
        result = 31 * result + outputs.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Device{" +
                "input='" + input + '\'' +
                ", outputs=" + outputs +
                '}';
    }
}
