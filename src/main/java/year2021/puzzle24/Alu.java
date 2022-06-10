package year2021.puzzle24;

import java.util.List;

public enum Alu {
    inp {
        @Override
        public long operation(List<Long> numbers, ModelNumber modelNumber) {
            return modelNumber.getInput();
        }
    },
    add {
        @Override
        public long operation(List<Long> numbers, ModelNumber modelNumber) {
            return numbers.get(0) + numbers.get(1);
        }
    },
    mul {
        @Override
        public long operation(List<Long> numbers, ModelNumber modelNumber) {
            return numbers.get(0) * numbers.get(1);
        }
    },
    div {
        @Override
        public long operation(List<Long> numbers, ModelNumber modelNumber) {
            return numbers.get(0) / numbers.get(1);
        }
    },
    mod {
        @Override
        public long operation(List<Long> numbers, ModelNumber modelNumber) {
            return numbers.get(0) % numbers.get(1);
        }
    },
    eql {
        @Override
        public long operation(List<Long> numbers, ModelNumber modelNumber) {
            return numbers.get(0).equals(numbers.get(1)) ? 1 : 0;
        }
    };

    public abstract long operation(List<Long> numbers, ModelNumber modelNumber);
}
