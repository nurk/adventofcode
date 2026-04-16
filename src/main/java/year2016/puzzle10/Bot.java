package year2016.puzzle10;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bot {
    @Getter
    private Integer valueOne;
    private Integer valueTwo;
    @Getter
    private final String botId;

    private String lowBot;
    private String highBot;
    private String lowOutput;
    private String highOutput;

    public Bot(String line) {
        if (line.startsWith("BOTID=")) {
            this.botId = StringUtils.substringAfter(line, "BOTID=");
            return;
        }
        this.botId = StringUtils.substringAfter(line, "bot ");
        valueOne = Integer.valueOf(StringUtils.substringBetween(line, "value ", " goes to bot "));
    }

    public void setInstruction(String instruction) {
        String[] lowTarget = StringUtils.substringBetween(instruction, "low to ", " and high to").split(" ");
        String[] highTarget = StringUtils.substringAfter(instruction, "high to ").split(" ");

        if (lowTarget[0].equals("bot")) {
            this.lowBot = lowTarget[1];
        } else {
            this.lowOutput = lowTarget[1];
        }

        if (highTarget[0].equals("bot")) {
            this.highBot = highTarget[1];
        } else {
            this.highOutput = highTarget[1];
        }

    }

    public void performInstruction(Map<String, Bot> bots, Map<String, List<Integer>> outputs) {
        if (!canPerformInstruction(bots)) {
            throw new IllegalArgumentException("Bot " + botId + " cannot perform instruction, missing values or target bots have no free spots");
        }

        if (lowBot != null) {
            bots.get(lowBot).giveValue(getLowValue());
        }

        if (highBot != null) {
            bots.get(highBot).giveValue(getHighValue());
        }

        if (lowOutput != null) {
            if (outputs.containsKey(lowOutput)) {
                outputs.get(lowOutput).add(getLowValue());
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(getLowValue());
                outputs.put(lowOutput, list);
            }
        }

        if (highOutput != null) {
            if (outputs.containsKey(highOutput)) {
                outputs.get(highOutput).add(getHighValue());
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(getHighValue());
                outputs.put(highOutput, list);
            }
        }

        valueTwo = null;
        valueOne = null;
    }

    public void giveValue(Integer value) {
        if (valueOne == null) {
            valueOne = value;
        } else if (valueTwo == null) {
            valueTwo = value;
        } else {
            throw new IllegalArgumentException("Bot " + botId + " already has two values, cannot give value " + value);
        }
    }

    public boolean canPerformInstruction(Map<String, Bot> bots) {
        if (valueTwo != null && valueOne != null) {
            if (lowBot != null && bots.get(lowBot).hasNoFreeSpots()) {
                return false;
            }
            if (highBot != null && bots.get(highBot).hasNoFreeSpots()) {
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean hasNoFreeSpots() {
        return valueOne != null && valueTwo != null;
    }

    private Integer getLowValue() {
        return valueOne < valueTwo ? valueOne : valueTwo;
    }

    private Integer getHighValue() {
        return valueOne > valueTwo ? valueOne : valueTwo;
    }

    public boolean hasValues(Integer one, Integer two) {
        if (valueOne == null || valueTwo == null) {
            return false;
        }
        return (valueOne.equals(one) && valueTwo.equals(two)) || (valueOne.equals(two) && valueTwo.equals(one));
    }

}
