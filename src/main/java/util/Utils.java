package util;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

public class Utils {

    public static Path getInputPath(String fileName) throws URISyntaxException {
        return Paths.get(Utils.class.getClassLoader().getResource(fileName).toURI());
    }

    public static <T> List<T> getInput(String fileName, Function<? super String, T> parser) {
        try {
            return Files.readAllLines(Utils.getInputPath(fileName)).stream().map(parser).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
