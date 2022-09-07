package snakeGame;

import javax.swing.JFrame;

public class GameFrame extends JFrame{

	public GameFrame() {
		this.add(new GameBoard());
		this.setTitle("Snakey");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
}
