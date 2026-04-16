package year2016.puzzle10;

import util.Utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bot 113 is responsible for comparing 61 and 17
 * Part B: 12803
 */
public class Puzzle10 {
    static void main() {
        Map<String, Bot> bots = new HashMap<>();
        Map<String, List<Integer>> outputs = new HashMap<>();

        Integer toFindOne = 61;
        Integer toFindTwo = 17;

        Utils.getInput("2016/input10.txt")
                .stream()
                .sorted(Comparator.reverseOrder())
                .forEach(line -> {
                    if (line.startsWith("value")) {
                        Bot bot = new Bot(line);
                        if (bots.containsKey(bot.getBotId())) {
                            bots.get(bot.getBotId()).giveValue(bot.getValueOne());
                        } else {
                            bots.put(bot.getBotId(), bot);
                        }
                    } else {
                        String botId = line.substring(4, line.indexOf(" gives"));
                        if (bots.containsKey(botId)) {
                            bots.get(botId).setInstruction(line);
                        } else {
                            Bot bot = new Bot("BOTID=" + botId);
                            bot.setInstruction(line);
                            bots.put(botId, bot);
                        }
                    }
                });

        boolean performedInstruction;
        do {
            performedInstruction = false;
            for (Bot bot : bots.values()) {
                if (bot.hasValues(toFindOne, toFindTwo)) {
                    System.out.println("Bot " + bot.getBotId() + " is responsible for comparing " + toFindOne + " and " + toFindTwo);
                }
                if (bot.canPerformInstruction(bots)) {
                    bot.performInstruction(bots, outputs);
                    performedInstruction = true;
                }
            }
        } while (performedInstruction);

        System.out.println("Part B: " + (outputs.get("0").getFirst() * outputs.get("1").getFirst() * outputs.get("2")
                .getFirst()));
    }
}
