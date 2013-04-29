package sudoku.structure;

import java.util.*;

public abstract class NumbersBlock {
	protected final List<Integer> numbers = new ArrayList<>();

	public boolean verify() {
		Set<Integer> used = new HashSet<Integer>();
		int count = 0;
		for (Integer number : numbers) {
			if (number != null) {
				used.add(number);
				count++;
			}
		}
		return (used.size() == count);
	}

	public List<Integer> getAvailableNumbers() {
		List<Integer> availableNumbers = new ArrayList<>();
		for (int number = 1; number <= numbers.size(); ++number) {
			availableNumbers.add(number);
		}

		for (Integer number : numbers) {
			if (number != null) {
				availableNumbers.remove(number);
			}
		}
		return availableNumbers;
	}

	public List<Integer> getUsedNumbers() {
		List<Integer> usedNumbers = new ArrayList<>();
		for (Integer number : numbers) {
			if (number != null) {
				usedNumbers.add(number);
			}
		}
		return usedNumbers;
	}

	public List<Integer> getIncorrectNumbers() {
		HashMap<Integer, Integer> used = new HashMap<>();
		for (int number = 1; number <= numbers.size(); ++number) {
			used.put(number, 0);
		}
		for (Integer number : numbers) {
			if (number != null) {
				used.put(number, used.get(number) + 1);
			}
		}
		List<Integer> incorrect = new ArrayList<>();
		for (Integer number : used.keySet()) {
			if (used.get(number) > 1) {
				incorrect.add(number);
			}
		}
		return incorrect;
	}
}
