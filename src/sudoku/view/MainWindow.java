package sudoku.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {
	private OptionsPanel optionsPanel = new OptionsPanel(this);
	private GamePanel gamePanel = new GamePanel(this);

	public MainWindow() {
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(new Dimension(800, 600));

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		optionsPanel.setVisible(true);
		add(optionsPanel, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		gamePanel.setVisible(false);
		add(gamePanel, constraints);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().compareTo("Start") == 0) {
			optionsPanel.setVisible(false);
			gamePanel.startGame(optionsPanel.getPuzzle());
			gamePanel.setVisible(true);
			validate();
		} else if (e.getActionCommand().compareTo("Stop") == 0) {
			optionsPanel.setVisible(true);
			gamePanel.setVisible(false);
			validate();
		}
	}
}
