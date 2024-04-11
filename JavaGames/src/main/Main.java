package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		JGGame_1();

	}
	
	public static void JGGame_1() {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("JG Game_1");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		gamePanel.startGameThread();
	}

}
