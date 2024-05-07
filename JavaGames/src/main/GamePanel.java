package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// 화면 세팅
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	
	// 기준 타일 크기
	public final int tileSize = originalTileSize * scale ; // 48x48 tile
	
	// 스크린 크기
	public final int maxScreenCol = 16; // tile 갯수
	public final int maxScreenRow = 12; 
	public final int screenWidth = tileSize * maxScreenCol; // tile 갯수 * tile 사이즈
	public final int screenHeight = tileSize * maxScreenRow;
	
	// WORLD SET
	public final int maxWorldCol = 50;	// tile 갯수
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;	// tile 갯수 * tile 사이즈
	public final int worldHeight = tileSize * maxWorldRow;
	
	// TileManager
	TileManager tileM = new TileManager(this);
	// keyPress
	KeyHandler keyH = new KeyHandler();
	// Thread
	Thread gameThread;
	// player
	public Player player = new Player(this, keyH);
	
	
	// 화면 갱신
	int FPS = 60;
		
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		this.setName("name");
		
		// keyboard press
		this.addKeyListener(keyH);
		this.setFocusable(true); // with this, this GamePanel can be "focused" to receive key input
		
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();	// call run()
		
	}

	@Override
	public void run() {
		double drawInterver = 1000000000/FPS; // nanoTime일때 : 초(s)/FPS
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterver; // 경과시간을 drawInterver로 나눈후 누적. 최종 1이상 될때 update하기 위함
			timer += (currentTime - lastTime); // 경과시간을 누적
			lastTime = currentTime;
			
			
			if(delta >= 1) {
				update();
				repaint();
				delta --;
				drawCount++;
			}
			
			if(timer >= 1000000000) {	// 1초 이상이 되었을 때 update횟수를 체크 
				//System.out.println("FPS: "+drawCount);
				drawCount = 0;
				timer = 0;
			}
			
			
		}
	}
	
	
	// 1 update : 위치 업데이트
	public void update() {
		player.update();
	}
	
	// 2 draw : 화면 정보 갱신 repaint()
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		tileM.draw(g2);		// 타일 드로우
		player.draw(g2);	// 케릭터 드로우
		g2.dispose();
				
	}
}
