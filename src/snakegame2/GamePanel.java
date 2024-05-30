package snakegame2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener {
	
	private ImageIcon snaketitle = new ImageIcon(ClassLoader.getSystemResource("icons/snaketitle.jpg"));
	private Image rightHead;
	private Image leftHead;
	private Image upHead;
	private Image downHead;
	private Image dot;
	private Image apple;
	
	private int x[] = new int[750];
	private int y[] = new int[750];
	private int dots = 3;
	private final int DOT_SIZE = 25;
	
	private int ax[] = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
	private int ay[] = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};
	private Random random = new Random();
	
	private int apple_x;
	private int apple_y;
	
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	
	private Timer timer;
	private int delay = 140;
	
	private int score = 0;
	private boolean gameover = false;
	
	GamePanel()
	{
		addKeyListener(new TAdapter());
		setFocusable(true);
		loadImage();
		initGame();
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		g.setColor(Color.WHITE);
		g.drawRect(20, 10, 852, 56);
		snaketitle.paintIcon(this, g, 21, 11);
		g.drawRect(20, 74, 852, 576);
		g.setColor(Color.BLACK);
		g.fillRect(21, 75, 851, 575);
		
		draw(g);
		gameOver(g);
		
	}
	
	public void gameOver(Graphics g)
	{
		if(gameover)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("SAN_SERIF", Font.BOLD, 50));
			g.drawString("GAME OVER!", 300, 300);
			
			g.setFont(new Font("SAN_SERIF", Font.PLAIN, 30));
			g.drawString("Press SPACE to Restart", 300, 350);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		g.drawString("SCORE: " + score, 750, 32);
		g.drawString("LENGTH: " + dots, 750, 52);
	}
	
	public void loadImage()
	{
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/leftmouth.png"));
		leftHead = i1.getImage();
		
		ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("icons/rightmouth.png"));
		rightHead = i2.getImage();
		
		ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icons/upmouth.png"));
		upHead = i3.getImage();
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/downmouth.png"));
		downHead = i4.getImage();
		
		ImageIcon i5 = new ImageIcon(ClassLoader.getSystemResource("icons/snakeimage.png"));
		dot = i5.getImage();
		
		ImageIcon i6 = new ImageIcon(ClassLoader.getSystemResource("icons/enemy.png"));
		apple = i6.getImage();
	}
	
	public void initGame()
	{
		for(int i=0;i<dots;i++)
		{
			y[i] = 100;
			x[i] = 100 - i * DOT_SIZE;
		}
		locateApple();
		timer = new Timer(140, this);
		timer.start();
	}
	
	public void locateApple()
	{
		apple_x = ax[random.nextInt(34)];
		apple_y = ay[random.nextInt(23)];
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(apple, apple_x, apple_y, this);
		if(right)
		{
			g.drawImage(rightHead, x[0], y[0], this);
		}
		if(left)
		{
			g.drawImage(leftHead, x[0], y[0], this);
		}
		if(up)
		{
			g.drawImage(upHead, x[0], y[0], this);
		}
		if(down)
		{
			g.drawImage(downHead, x[0], y[0], this);
		}
		
		if(x[0] > 850)
		{
			x[0] = 20;
		}
		if(x[0] < 20)
		{
			x[0] = 850;
		}
		if(y[0] > 625)
		{
			y[0] = 75;
		}
		if(y[0] < 75)
		{
			y[0] = 625;
		}
		
		for(int i=1;i<dots;i++)
		{
			g.drawImage(dot, x[i], y[i], this);
		}
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void move()
	{
		for(int i=dots;i>0;i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		if(right)
		{
			x[0] += DOT_SIZE;
		}
		if(left)
		{
			x[0] -= DOT_SIZE;
		}
		if(up)
		{
			y[0] -= DOT_SIZE;
		}
		if(down)
		{
			y[0] += DOT_SIZE;
		}
		
	}
	
	public void checkApple()
	{
		if(x[0] == apple_x && y[0] == apple_y)
		{
			dots++;
			locateApple();
			score++;
		}
	}
	
	public void checkCollision()
	{
		for(int i=dots;i>0;i--)
		{
			if(dots > 3 && x[0] == x[i] && y[0] == y[i])
			{
				timer.stop();
				gameover = true;
			}
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		checkCollision();
		checkApple();
		move();
		repaint();
	}
	
	private void restart()
	{
		gameover = false;
		score = 0;
		dots = 3;
		left = false;
		right = true;
		up = false;
		down = false;
		timer.stop();
		initGame();
	}
	
	public class TAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e) 
		{
			if(e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				restart();
			}
			
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_LEFT && (!right))
			{
				left = true;
				up = false;
				down = false;
			}
			if(key == KeyEvent.VK_RIGHT && (!left))
			{
				right = true;
				up = false;
				down = false;
			}
			if(key == KeyEvent.VK_UP && (!down))
			{
				up = true;
				right = false;
				left = false;
			}
			if(key == KeyEvent.VK_DOWN && (!up))
			{
				down = true;
				right = false;
				left = false;
			}
		}
	}
}
