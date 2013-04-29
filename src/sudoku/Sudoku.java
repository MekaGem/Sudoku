package sudoku;

import sudoku.create.BacktrackingPuzzleCreator;
import sudoku.generate.RandomGenerator;
import sudoku.solve.RecursiveSolver;
import sudoku.view.MainWindow;

public class Sudoku {
	public static void main(String[] args) {
		RandomGenerator generator = new RandomGenerator();
		RecursiveSolver solver = new RecursiveSolver();
		BacktrackingPuzzleCreator puzzleCreator = new BacktrackingPuzzleCreator();


//		puzzle = puzzleCreator.createPuzzle(generator.generate(), solver);

//		generator.setBoxWidth(4);
//		generator.setBoxHeight(4);
//		puzzleCreator.setDensity(0.4);
//
//		Map map = generator.generate();
//		Map puzzle = puzzleCreator.createPuzzle(map, solver);
//		puzzle.print();

		MainWindow mainWindow = new MainWindow();
		mainWindow.setVisible(true);
	}
}
