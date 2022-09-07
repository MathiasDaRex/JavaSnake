package snakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel implements ActionListener{
 
	private final int BOARD_WIDTH = 600;
	private final int BOARD_HEIGHT = 600;
	private final int DOT_SIZE = 25;
	private final int ALL_DOTS = (BOARD_WIDTH*BOARD_HEIGHT)/DOT_SIZE;
	private final int DELAY = 100;
	
	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];
	
	int bodyDots = 5;
	int applesEaten;
	int appleX;
	int appleY;
	public char direction = 'R';
	boolean running = false;
	
	Timer timer;
	Random random;
	
	public GameBoard() {
		random = new Random();
		this.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(running) {
			// grid for development
			/*
			for (int i = 0; i < BOARD_HEIGHT/DOT_SIZE; i++) {
				g.drawLine(i*DOT_SIZE, 0, i*DOT_SIZE, BOARD_HEIGHT);
				g.drawLine(0, i*DOT_SIZE, BOARD_WIDTH, i*DOT_SIZE);
			}
			*/
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, DOT_SIZE, DOT_SIZE);
			
			for(int i = 0; i < bodyDots; i++) {
				if(i==0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], DOT_SIZE, DOT_SIZE);
				} else {
					g.setColor(new Color(50,170,0));
					// enable for rainbow body
					// g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], DOT_SIZE, DOT_SIZE);
				}
			}
			
			g.setColor(Color.red);
			g.setFont(new Font("Comic sans MS",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+applesEaten, (BOARD_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
		} else {
			gameOver(g);
		}
	}
	
	public void newApple() {
		appleX = random.nextInt(BOARD_WIDTH/DOT_SIZE)*DOT_SIZE;
		appleY = random.nextInt(BOARD_HEIGHT/DOT_SIZE)*DOT_SIZE;
	}
	
	public void move() {
		for (int i = bodyDots; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - DOT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + DOT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - DOT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + DOT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyDots++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		// checking head collision with body
		for(int i = bodyDots; i>0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false;
			}
		}
		// Check if head collides with left border
		if(x[0] < 0) {
			running = false;
		}
		// Check if head collides with right border
		if(x[0] > BOARD_WIDTH) {
			running = false;
		}
		// Check if head collides with top border
		if(y[0] < 0) {
			running = false;
		}
		// Check if head collides with bottom border
		if(y[0] > BOARD_HEIGHT) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
		
	}
	
	public void gameOver(Graphics g) {
		// Score
		g.setColor(Color.red);
		g.setFont(new Font("Comic sans MS",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+applesEaten, (BOARD_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

		// Game over text
		g.setColor(Color.red);
		g.setFont(new Font("Comic sans MS",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (BOARD_WIDTH - metrics2.stringWidth("Game Over"))/2, BOARD_HEIGHT/2);
	}
	
	public class MyKeyAdapter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
		
	}
}

