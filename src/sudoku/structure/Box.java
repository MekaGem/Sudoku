package sudoku.structure;

import java.util.List;

public class Box extends NumbersBlock {
	private final int width;
	private final int height;

	public Box(int width, int height, Map map, int firstRow, int firstColumn) {
		this.width = width;
		this.height = height;
		for (int row = firstRow; row < firstRow + height; ++row) {
			for (int column = firstColumn; column < firstColumn + width; ++column) {
				numbers.add(map.get(row, column));
			}
		}
	}

	public Box(int width, int height, List<Integer> numbers) {
		this.width = width;
		this.height = height;
		if (numbers.size() != width * height) {
			String text = "There should be " + (width * height) + " numbers, not " + numbers.size();
			throw new IllegalArgumentException(text);
		}
		this.numbers.addAll(numbers);
	}

	public Integer get(int row, int column) {
		return numbers.get(row * width + column);
	}
}
