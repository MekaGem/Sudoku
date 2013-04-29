package sudoku.structure;

import java.util.ArrayList;
import java.util.List;

public class Map {
	private final List<Integer> numbers = new ArrayList<>();
	private final List<Boolean> locked = new ArrayList<>();
	private final int boxWidth;
	private final int boxHeight;

	public Map(int boxWidth, int boxHeight) {
		this.boxWidth = boxWidth;
		this.boxHeight = boxHeight;
		for (int index = 0; index < getNumberOfCells(); ++index) {
			numbers.add(null);
			locked.add(false);
		}
	}

	public Map(Map map) {
		this(map.getBoxWidth(), map.getBoxHeight());
		for (int row = 0; row < map.getSize(); ++row) {
			for (int column = 0; column < map.getSize(); ++column) {
				set(row, column, map.get(row, column));
				if (map.isLocked(row, column)) {
					lock(row, column);
				}
			}
		}
	}

	public int getBoxWidth() {
		return boxWidth;
	}

	public int getBoxHeight() {
		return boxHeight;
	}

	public int getSize() {
		return boxWidth * boxHeight;
	}

	public int getNumberOfCells() {

		return boxWidth * boxWidth * boxHeight * boxHeight;
	}

	public void clear() {
		for (int row = 0; row < getSize(); ++row) {
			for (int column = 0; column < getSize(); ++column) {
				if (!isLocked(row, column)) {
					set(row, column, null);
				}
			}
		}
	}

	public Box getAppropriateBox(int row, int column) {
		row -= row % boxHeight;
		column -= column % boxWidth;
		return new Box(boxWidth, boxHeight, this, row, column);
	}

	public Row getRow(int row) {
		return new Row(getSize(), this, row);
	}

	public Column getColumn(int column) {
		return new Column(getSize(), this, column);
	}

	public boolean isCorrect(int row, int column) {
		if (get(row, column) == null) {
			return true;
		}

		List<NumbersBlock> blocks = new ArrayList<>();
		blocks.add(getAppropriateBox(row, column));
		blocks.add(getRow(row));
		blocks.add(getColumn(column));

		for (NumbersBlock block : blocks) {
			if (block.getIncorrectNumbers().contains(get(row, column))) {
				return false;
			}
		}

		return true;
	}

	public List<Integer> getAvailableNumbers(int row, int column) {
		if (get(row, column) != null) {
			throw new IllegalStateException("This cell is already set");
		}

		List<Integer> availableNumbers = new ArrayList<>();
		for (int number = 1; number <= getSize(); ++number) {
			availableNumbers.add(number);
		}
		availableNumbers.removeAll(getAppropriateBox(row, column).getUsedNumbers());
		availableNumbers.removeAll(getRow(row).getUsedNumbers());
		availableNumbers.removeAll(getColumn(column).getUsedNumbers());

		return availableNumbers;
	}

	public List<Integer> getFilledCells() {
		List<Integer> emptyCells = new ArrayList<>();
		for (int row = 0; row < getSize(); ++row) {
			for (int column = 0; column < getSize(); ++column) {
				if (get(row, column) != null) {
					emptyCells.add(row * getSize() + column);
				}
			}
		}
		return emptyCells;
	}

	public List<Integer> getEmptyCells() {
		List<Integer> emptyCells = new ArrayList<>();
		for (int row = 0; row < getSize(); ++row) {
			for (int column = 0; column < getSize(); ++column) {
				if (get(row, column) == null) {
					emptyCells.add(row * getSize() + column);
				}
			}
		}
		return emptyCells;
	}

	public void set(int row, int column, Integer number) {
		if (isLocked(row, column)) {
			throw new IllegalStateException("This cell is locked and can't be changed");
		}
		numbers.set(row * getSize() + column, number);
	}

	public Integer get(int row, int column) {
		return numbers.get(row * boxWidth * boxHeight + column);
	}

	public void lock(int row, int column) {
		if (get(row, column) == null) {
			throw new IllegalStateException("This cell is not set yet, it can't be locked");
		}
		this.locked.set(row * getSize() + column, true);
	}

	public void lock() {
		for (int row = 0; row < getSize(); ++row) {
			for (int column = 0; column < getSize(); ++column) {
				if (get(row, column) != null) {
					lock(row, column);
				}
			}
		}
	}

	public boolean isLocked(int row, int column) {
		return locked.get(row * getSize() + column);
	}

	public void print() {
		for (int row = 0; row < getSize(); ++row) {
			for (int column = 0; column < getSize(); ++column) {
				Integer number = numbers.get(row * getSize() + column);
				if (number == null) {
					System.out.print("- ");
				} else {
					System.out.print(number + " ");
				}
			}
			System.out.println();
		}
	}
}
