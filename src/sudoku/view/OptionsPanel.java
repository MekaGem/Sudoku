package sudoku.view;

import sudoku.create.BacktrackingPuzzleCreator;
import sudoku.generate.RandomGenerator;
import sudoku.solve.RecursiveSolver;
import sudoku.structure.Map;

import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel {
	private static final String[] sizeVariants = {"3", "4"};

	private JComboBox widthBox;
	private JComboBox heightBox;
	private JSlider densitySlider;
	private JButton startButton;

	public OptionsPanel(MainWindow mainWindow) {
		setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1;
		constraints.ipadx += 10;
		constraints.ipady += 10;

		/**
		 * Width
		 */
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(new JLabel("Box width", JLabel.CENTER), constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		widthBox = new JComboBox<>(sizeVariants);
		add(widthBox, constraints);

		/**
		 * Height
		 */
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(new JLabel("Box height", JLabel.CENTER), constraints);

		constraints.gridx = 1;
		constraints.gridy = 1;
		heightBox = new JComboBox<>(sizeVariants);
		add(heightBox, constraints);

		/**
		 * Density
		 */
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(new JLabel("Density", JLabel.CENTER), constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		densitySlider = new JSlider(0, 1000);
		add(densitySlider, constraints);

		/**
		 * Start button
		 */
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 3;
		startButton = new JButton("Start");
		startButton.setActionCommand("Start");
		startButton.addActionListener(mainWindow);
		add(startButton, constraints);
	}

	private int getBoxWidth() {
		return Integer.valueOf((String) widthBox.getSelectedItem());
	}

	private int getBoxHeight() {
		return Integer.valueOf((String) heightBox.getSelectedItem());
	}

	private double getDensity() {
		if (getBoxWidth() * getBoxHeight() == 9) {
			return 0.34 + 0.66 * (densitySlider.getValue() / 1000.0);
		} else if (getBoxWidth() * getBoxHeight() == 12) {
			return 0.45 + 0.55 * (densitySlider.getValue() / 1000.0);
		} else {
			return 0.55 + 0.45 * (densitySlider.getValue() / 1000.0);
		}
	}

	public Map getPuzzle() {
		RandomGenerator generator = new RandomGenerator();
		RecursiveSolver solver = new RecursiveSolver();
		BacktrackingPuzzleCreator creator = new BacktrackingPuzzleCreator();

		generator.setBoxWidth(getBoxWidth());
		generator.setBoxHeight(getBoxHeight());
		creator.setDensity(getDensity());

		return creator.createPuzzle(generator.generate(), solver);
	}
}
