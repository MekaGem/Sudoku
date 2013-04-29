package sudoku.solve;

import sudoku.structure.Map;

import java.util.List;

public class RecursiveSolver implements Solver {
	private int startSolving(Map map, int index) {
		if (index == map.getNumberOfCells()) {
			return 1;
		}

		int row = index / map.getSize();
		int column = index % map.getSize();

		if (map.get(row, column) != null) {
			return startSolving(map, index + 1);
		}

		int result = 0;
		List<Integer> availableNumbers = map.getAvailableNumbers(row, column);
		for (Integer number : availableNumbers) {
			map.set(row, column, number);
			result += startSolving(map, index + 1);
			if (result > 1) {
				return result;
			}
			map.set(row, column, null);
		}
		return result;
	}

	@Override
	public int findSolutions(Map map) {
		return startSolving(map, 0);
	}
}
