package year2017.puzzle12;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Program {
    @Getter
    private final String id;
    private final String input;
    private final List<Program> groups = new ArrayList<>();

    public Program(String line) {
        //2 <-> 0, 3, 4
        this.input = line;
        this.id = line.split(" <-> ")[0];
    }

    public void init(Map<String, Program> programs) {
        String[] groupIds = input.split(" <-> ")[1].split(", ");
        for (String groupId : groupIds) {
            groups.add(programs.get(groupId));
        }
    }

    public boolean isInGroup(String targetId, List<String> visited) {
        if (id.equals(targetId)) {
            return true;
        }
        visited.add(id);
        for (Program group : groups) {
            if (!visited.contains(group.getId())) {
                if (group.isInGroup(targetId, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Program program = (Program) o;
        return id.equals(program.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
