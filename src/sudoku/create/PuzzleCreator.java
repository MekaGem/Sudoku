package sudoku.create;

import sudoku.solve.Solver;
import sudoku.structure.Map;

public interface PuzzleCreator {
	public Map createPuzzle(Map map, Solver solver);
}
