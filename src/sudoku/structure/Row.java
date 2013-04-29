package sudoku.structure;

public class Row extends NumbersBlock {
	private final int width;

	public Row(int width, Map map, int row) {
		this.width = width;
		for (int column = 0; column < width; ++column) {
			numbers.add(map.get(row, column));
		}
	}
}
