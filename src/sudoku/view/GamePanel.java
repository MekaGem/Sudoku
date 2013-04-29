package sudoku.view;

import sudoku.structure.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class GamePanel extends JPanel implements ActionListener {
	private SudokuPanel sudokuPanel = new SudokuPanel(this);
	private JButton stopButton = new JButton("Stop");
	private JLabel timerPanel = new JLabel("");
	private long time;
	private Timer timer;
	private boolean finished;

	public GamePanel(MainWindow mainWindow) {
		setLayout(new GridBagLayout());

		timer = new Timer((int) (1000.0 / 60.0), this);
		timer.setActionCommand("Timer");
		timer.start();

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;

		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.gridheight = 1;
		add(sudokuPanel, constraints);

		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;

		constraints.gridx = 0;
		timerPanel.setHorizontalAlignment(JLabel.CENTER);
		Font font = timerPanel.getFont();
		timerPanel.setFont(new Font(font.getName(), Font.BOLD, 40));
		add(timerPanel, constraints);

		constraints.gridx = 1;
		stopButton.addActionListener(mainWindow);
		stopButton.setActionCommand("Stop");
		add(stopButton, constraints);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!finished) {
			long timeDiff = System.currentTimeMillis() - time;
			long hours = TimeUnit.MILLISECONDS.toHours(timeDiff);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff));
			long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDiff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDiff));
			long milliseconds = timeDiff - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(timeDiff));

			String timeString = String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, milliseconds);
			timerPanel.setText(timeString);
		}
		sudokuPanel.repaint();
		validate();
	}

	public void startGame(Map map) {
		finished = false;
		sudokuPanel.restart(map);
		time = System.currentTimeMillis();
	}

	public void stopGame() {
		finished = true;
	}
}
