package snakegame2;

import java.awt.Color;

import javax.swing.*;

public class Main {
	public static void main(String args[])
	{
		JFrame frame = new JFrame("Snake Game");
		frame.setBounds(300, 65, 905, 700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GamePanel panel = new GamePanel();
		panel.setBackground(Color.DARK_GRAY);
		frame.add(panel);
		
		
		frame.setVisible(true);
	}
}
