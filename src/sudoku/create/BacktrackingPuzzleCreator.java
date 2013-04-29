package sudoku.create;

import sudoku.solve.Solver;
import sudoku.structure.Map;

import java.util.List;
import java.util.Random;

public class BacktrackingPuzzleCreator implements PuzzleCreator {
	private double density = 0.5;
	private Random random = new Random();

	public void setDensity(double density) {
		if (density < 0 || density > 1) {
			throw new IllegalArgumentException("Density should be between 0 and 1 inclusive");
		}
		this.density = density;
	}

	public Map tryToCreate(Map map, Solver solver) {
		Map result = new Map(map);
		int cellsToClear = (int) (map.getNumberOfCells() * (1.0 - density));

		for (int iteration = 0; iteration < cellsToClear; ++iteration) {
			List<Integer> cells = result.getFilledCells();
			int index = cells.get(random.nextInt(cells.size()));

			int row = index / result.getSize();
			int column = index % result.getSize();
			int number = result.get(row, column);

			result.set(row, column, null);

			if (solver.findSolutions(new Map(result)) > 1) {
				result.set(row, column, number);
				return null;
			}
		}
		result.lock();
		return result;
	}

	@Override
	public Map createPuzzle(Map map, Solver solver) {
		Map result = null;
		while (result == null) {
			result = tryToCreate(map, solver);
		}
		return result;
	}
}
