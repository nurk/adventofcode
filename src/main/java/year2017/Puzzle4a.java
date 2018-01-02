package year2017;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class Puzzle4a {

    public static void main(String... args) throws IOException, URISyntaxException {
        AtomicInteger total = new AtomicInteger();
        AtomicInteger totalValid = new AtomicInteger();
        Files.readAllLines(Paths.get(Puzzle4a.class.getClassLoader().getResource("2017/input4a.txt").toURI())).forEach(
                line -> {
                    total.getAndIncrement();
                    String[] split = StringUtils.split(line, " ");
                    if(split.length == Sets.newHashSet(split).size()){
                        totalValid.getAndIncrement();
                    }
                }
        );

        System.out.println("Total " + total.get());
        System.out.println("Total valid " + totalValid.get());
    }
}
