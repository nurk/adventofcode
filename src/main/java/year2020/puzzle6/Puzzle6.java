package year2020.puzzle6;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Puzzle6 {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<Group> groups = setup();

        int sum = 0;

        for (Group group : groups) {
            sum += group.getNumberOfUniqueYesAnswers();
        }

        System.out.println(sum);

        int sumPart2 = 0;

        for (Group group : groups) {
            sumPart2 += group.getNumberOfAnswersEveryoneAnsweredYesTo();
        }

        System.out.println(sumPart2);
    }

    private static List<Group> setup() throws IOException, URISyntaxException {
        List<String> data = new ArrayList<>(Files.readAllLines(Utils.getInputPath("2020/input6.txt")));

        List<Group> groups = new ArrayList<>();
        Group group = new Group();
        for (String datum : data) {
            if (StringUtils.isEmpty(datum)) {
                groups.add(group);
                group = new Group();
            } else {
                group.addPerson(datum);
            }
        }
        groups.add(group);
        return groups;
    }
}
