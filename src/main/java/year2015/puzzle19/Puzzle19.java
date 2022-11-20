package year2015.puzzle19;

import org.apache.commons.lang3.StringUtils;
import util.Utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Puzzle19 {
    public static void main(String[] args) {
        List<String> replacements = new ArrayList<>(Utils.getInput("2015/input19.txt"));

        Set<String> partA = new HashSet<>();
        String target = "CRnCaSiRnBSiRnFArTiBPTiTiBFArPBCaSiThSiRnTiBPBPMgArCaSiRnTiMgArCaSiThCaSiRnFArRnSiRnFArTiTiBFArCaCaSiRnSiThCaCaSiRnMgArFYSiRnFYCaFArSiThCaSiThPBPTiMgArCaPRnSiAlArPBCaCaSiRnFYSiThCaRnFArArCaCaSiRnPBSiRnFArMgYCaCaCaCaSiThCaCaSiAlArCaCaSiRnPBSiAlArBCaCaCaCaSiThCaPBSiThPBPBCaSiRnFYFArSiThCaSiRnFArBCaCaSiRnFYFArSiThCaPBSiThCaSiRnPMgArRnFArPTiBCaPRnFArCaCaCaCaSiRnCaCaSiRnFYFArFArBCaSiThFArThSiThSiRnTiRnPMgArFArCaSiThCaPBCaSiRnBFArCaCaPRnCaCaPMgArSiRnFYFArCaSiThRnPBPMgAr";
        //String target = "HOHOHO";

        replacements.forEach(replacement -> {
            String[] split = replacement.split(" => ");
            Pattern patters = Pattern.compile(split[0]);
            Matcher matcher = patters.matcher(target);
            while (matcher.find()) {
                partA.add(StringUtils.substring(target, 0, matcher.start()) + split[1] + StringUtils.substring(target,
                        matcher.end(),
                        target.length()));
            }
        });

        System.out.println(partA.size());

        // stolen from https://www.reddit.com/r/adventofcode/comments/3xflz8/comment/cy4h7ji/?utm_source=share&utm_medium=web2x&context=3
        long uppers = Arrays.stream(target.split(""))
                .filter(StringUtils::isAllUpperCase)
                .count();
        long rns = Arrays.stream(target.replaceAll("Rn", "|").split(""))
                .filter(s -> s.equals("|"))
                .count();
        long ars = Arrays.stream(target.replaceAll("Ar", "|").split(""))
                .filter(s -> s.equals("|"))
                .count();
        long ys = Arrays.stream(target.split(""))
                .filter(s -> s.equals("Y"))
                .count();

        System.out.println(uppers - rns - ars - 2 * ys - 1);

    }
}
