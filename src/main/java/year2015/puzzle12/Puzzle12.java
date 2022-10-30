package year2015.puzzle12;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.Utils;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

public class Puzzle12 {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(-?[0-9]+)");

    public static void main(String[] args) throws JsonProcessingException {
        String input = Utils.getInput("2015/input12.txt").get(0);

        int sumPartA = 0;
        Matcher matcher = NUMBER_PATTERN.matcher(input);
        while (matcher.find()) {
            sumPartA = sumPartA + Integer.parseInt(matcher.group());
        }
        System.out.println(sumPartA); //119433


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(input);

        AtomicInteger sumSecondSolution = new AtomicInteger();
        sum(jsonNode, sumSecondSolution, false);
        System.out.println(sumSecondSolution.get()); //119433

        sumSecondSolution.set(0);
        sum(jsonNode, sumSecondSolution, true);
        System.out.println(sumSecondSolution.get()); //68466
    }

    private static void sum(JsonNode jsonNode, AtomicInteger sum, boolean checkForReds) {
        boolean hasRed = StreamSupport.stream(jsonNode.spliterator(), false)
                .anyMatch(innerNode -> innerNode.asText().equals("red") && !jsonNode.isArray());

        if (!(checkForReds && hasRed)) {
            jsonNode.iterator().forEachRemaining(innerNode -> {
                if (innerNode.isInt()) {
                    sum.getAndAdd(innerNode.asInt());
                }
                sum(innerNode, sum, checkForReds);
            });
        }
    }
}
