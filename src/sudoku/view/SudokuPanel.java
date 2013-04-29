package sudoku.view;

import sudoku.structure.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SudokuPanel extends JPanel implements MouseListener {
	private GamePanel gamePanel;
	private Map map;
	private int sx = 0;
	private int sy = 0;
	private int size = 1;

	private boolean finished;
	private boolean isChoosing;
	private Point toChoose;

	public SudokuPanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		addMouseListener(this);
	}

	private void check() {
		if (map.getEmptyCells().size() != 0) {
			return;
		}
		for (int row = 0; row < map.getSize(); ++row) {
			for (int column = 0; column < map.getSize(); ++column) {
				if (!map.isCorrect(row, column)) {
					return;
				}
			}
		}

		gamePanel.stopGame();
		finished = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!isChoosing && !finished) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (isIntoSudoku(e.getPoint())) {
					Point cell = getSudokuPosition(getMousePosition());
					if (!map.isLocked(cell.y, cell.x)) {
						isChoosing = true;
						toChoose = getSudokuPosition(e.getPoint());
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (isChoosing && !finished) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (isIntoSudoku(e.getPoint())) {
					Point cell = getSudokuPosition(getMousePosition());
					int row = toChoose.y - toChoose.y % map.getBoxHeight();
					int column = toChoose.x - toChoose.x % map.getBoxWidth();
					if (cell.x >= column && cell.x < column + map.getBoxWidth() &&
							cell.y >= row && cell.y < row + map.getBoxHeight()) {
						int number = (cell.y - row) * map.getBoxWidth() + (cell.x - column) + 1;
						map.set(toChoose.y, toChoose.x, number);
						check();
					}
				}
				isChoosing = false;
			} else if (e.getButton() == MouseEvent.BUTTON2) {
				isChoosing = false;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	private boolean isIntoSudoku(Point p) {
		if (p == null) {
			return false;
		}
		return (p.x >= sx && p.x < sx + size &&
				p.y >= sy && p.y < sy + size);
	}

	private Point getSudokuPosition(Point p) {
		if (!isIntoSudoku(p)) {
			return null;
		}

		Point result = new Point();

		for (int column = 1; column <= map.getSize(); ++column) {
			int x = (size * column) / map.getSize();
			if (p.x < sx + x) {
				result.x = column - 1;
				break;
			}
		}

		for (int row = 1; row <= map.getSize(); ++row) {
			int y = (size * row) / map.getSize();
			if (p.y < sy + y) {
				result.y = row - 1;
				break;
			}
		}

		return result;
	}

	private void drawNumber(Graphics2D g2, int row, int column) {
		if (map.get(row, column) == null) {
			return;
		}

		if (!map.isCorrect(row, column)) {
			g2.setColor(Color.RED);
		} else if (map.isLocked(row, column)) {
			g2.setColor(Color.BLACK);
		} else {
			g2.setColor(Color.GRAY);
		}

		int cx = sx + (size * (2 * column + 1)) / (2 * map.getSize());
		int cy = sy + (size * (2 * row + 1)) / (2 * map.getSize());
		String text = String.valueOf(map.get(row, column));
		int textWidth = g2.getFontMetrics().stringWidth(text);
		int textHeight = g2.getFontMetrics().getHeight();

		g2.drawString(text, cx - textWidth / 2, cy + textHeight / 3);
	}

	private void drawChoosingNumbers(Graphics2D g2, int firstRow, int firstColumn) {
		g2.setColor(Color.WHITE);

		int number = 1;
		for (int row = firstRow; row < firstRow + map.getBoxHeight(); ++row) {
			for (int column = firstColumn; column < firstColumn + map.getBoxWidth(); ++column) {
				int cx = sx + (size * (2 * column + 1)) / (2 * map.getSize());
				int cy = sy + (size * (2 * row + 1)) / (2 * map.getSize());
				String text = String.valueOf(number);
				int textWidth = g2.getFontMetrics().stringWidth(text);
				int textHeight = g2.getFontMetrics().getHeight();

				g2.drawString(text, cx - textWidth / 2, cy + textHeight / 3);

				number++;
			}
		}
	}

	private void drawGrid(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		g2.drawRect(sx, sy, size, size);

		Point mouse = getMousePosition();
		if (!isChoosing && isIntoSudoku(mouse)) {
			Point p = getSudokuPosition(mouse);
			if (p == null) {
				throw new IllegalStateException("P is null");
			}
			int x1, y1, x2, y2;
			x1 = (size * p.x) / map.getSize();
			y1 = (size * p.y) / map.getSize();
			x2 = (size * (p.x + 1)) / map.getSize();
			y2 = (size * (p.y + 1)) / map.getSize();
			g2.setColor(Color.LIGHT_GRAY);
			g2.fillRect(sx + x1, sy + y1, x2 - x1, y2 - y1);
		}

		g2.setColor(Color.BLACK);
		for (int row = 1; row < map.getSize(); ++row) {
			if (row % map.getBoxHeight() == 0) {
				g2.setStroke(new BasicStroke(3));
			} else {
				g2.setStroke(new BasicStroke(1));
			}
			int y = (size * row) / map.getSize();
			g2.drawLine(sx, sy + y, sx + size, sy + y);
		}

		for (int column = 1; column < map.getSize(); ++column) {
			if (column % map.getBoxWidth() == 0) {
				g2.setStroke(new BasicStroke(3));
			} else {
				g2.setStroke(new BasicStroke(1));
			}
			int x = (size * column) / map.getSize();
			g2.drawLine(sx + x, sy, sx + x, sy + size);
		}

		for (int row = 0; row < map.getSize(); ++row) {
			for (int column = 0; column < map.getSize(); ++column) {
				drawNumber(g2, row, column);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		int w = getWidth() - 1;
		int h = getHeight() - 1;
		size = Math.min(w, h);
		sx = (w - size) / 2;
		sy = (h - size) / 2;

		Font font = g2.getFont();
		g2.setFont(new Font(font.getName(), Font.BOLD, size / (3 * map.getSize())));
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(3));

		drawGrid(g2);

		if (isChoosing) {
			int row = toChoose.y - toChoose.y % map.getBoxHeight();
			int column = toChoose.x - toChoose.x % map.getBoxWidth();

			int x = sx + (size * column) / map.getSize();
			int y = sy + (size * row) / map.getSize();
			int width = size / map.getBoxHeight();
			int height = size / map.getBoxWidth();

			g2.setColor(Color.DARK_GRAY);
			g2.fillRect(x, y, width, height);

			if (isIntoSudoku(getMousePosition())) {
				Point cell = getSudokuPosition(getMousePosition());
				if (cell.x >= column && cell.x < column + map.getBoxWidth() &&
						cell.y >= row && cell.y < row + map.getBoxHeight()) {
					int x1, y1, x2, y2;
					x1 = sx + (size * cell.x) / map.getSize();
					y1 = sy + (size * cell.y) / map.getSize();
					x2 = sx + (size * (cell.x + 1)) / map.getSize();
					y2 = sy + (size * (cell.y + 1)) / map.getSize();
					g2.setColor(Color.GRAY);
					g2.drawRect(x1, y1, x2 - x1, y2 - y1);
				}
				drawChoosingNumbers(g2, row, column);
			}
		}
	}

	public void restart(Map map) {
		isChoosing = false;
		finished = false;
		this.map = map;
		map.clear();
	}
}
