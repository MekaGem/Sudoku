package sudoku.generate;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomGenerator implements Generator {
	private int boxWidth = 3;
	private int boxHeight = 3;
	private Random random = new Random();

	public void setBoxWidth(int boxWidth) {
		if (boxWidth < 1) {
			throw new IllegalArgumentException("Box width should be positive");
		}
		this.boxWidth = boxWidth;
	}

	public void setBoxHeight(int boxHeight) {
		if (boxHeight < 1) {
			throw new IllegalArgumentException("Box height should be positive");
		}
		this.boxHeight = boxHeight;
	}

	private boolean startGenerating(sudoku.structure.Map map, int index) {
		if (index == map.getNumberOfCells()) {
			return true;
		}

		int row = index / map.getSize();
		int column = index % map.getSize();

		List<Integer> availableNumbers = map.getAvailableNumbers(row, column);
		Collections.shuffle(availableNumbers, random);
		for (Integer number : availableNumbers) {
			map.set(row, column, number);
			if (startGenerating(map, index + 1)) {
				return true;
			}
			map.set(row, column, null);
		}
		return false;
	}

	@Override
	public sudoku.structure.Map generate() {
		sudoku.structure.Map map = new sudoku.structure.Map(boxWidth, boxHeight);
		startGenerating(map, 0);
		return map;
	}
}
