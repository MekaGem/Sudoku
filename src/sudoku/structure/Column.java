package sudoku.structure;

public class Column extends NumbersBlock {
	private final int height;

	public Column(int height, Map map, int column) {
		this.height = height;
		for (int row = 0; row < height; ++row) {
			numbers.add(map.get(row, column));
		}
	}
}
