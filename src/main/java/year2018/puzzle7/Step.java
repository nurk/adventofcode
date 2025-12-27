package year2018.puzzle7;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

public class Step implements Comparable<Step> {

    @Getter
    private final String name;
    private final Set<Step> previousSteps = new HashSet<>();
    @Getter
    @Setter
    private boolean finished = false;
    private final int duration;
    private int timeWorked = 0;
    @Setter
    @Getter
    private int numberOfWorkers = 0;

    public Step(String name) {
        this.name = name;
        this.duration = 0;
    }

    public Step(String name, int baseDuration) {
        this.name = name;
        this.duration = baseDuration + (name.charAt(0) - 'A' + 1);
    }

    public void addPreviousStep(Step step) {
        previousSteps.add(step);
    }

    public boolean canBeStarted() {
        return previousSteps.stream().allMatch(Step::isFinished) && !finished && numberOfWorkers == 0;
    }

    public void addWorker() {
        numberOfWorkers++;
    }

    public boolean tick() {
        timeWorked += numberOfWorkers;
        if (timeWorked >= duration) {
            finished = true;
        }

        return finished;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Step step = (Step) o;
        return name.equals(step.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Step{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Step o) {
        return this.name.compareTo(o.name);
    }
}
