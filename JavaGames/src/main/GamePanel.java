package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	// 화면 세팅
	final int orihinalTileSize = 16; // 16x16 tile
	final int scale = 3;
	
	// 기준 타일 크기
	final int tileSize = orihinalTileSize * scale ; // 48x48 tile
	
	// 스크린 크기
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	
	// keyPress
	KeyHandler keyH = new KeyHandler();
	
	Thread gameThread;
	
	// 케릭터 세팅
	int positionX = 100;
	int positionY = 100;
	int move = 5;
	
	// 화면 갱신
	int FPS = 60;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		// key
		this.addKeyListener(keyH);
		this.setFocusable(true); // with this, this GamePanel can be "focused" to receive key input
		
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	@Override
	public void run() {
		
		long aTime;
		long bTime;
		long oneFrame = 1/FPS;
		long sleepTime;
		
		// game loof;	
		while(gameThread != null) {
			
			//aTime = System.currentTimeMillis();
			aTime = System.nanoTime();
			
			// position update
			update();
			// draw 화면 정보 
			repaint();
			//bTime = System.currentTimeMillis();
			bTime = System.nanoTime();
			
			sleepTime = oneFrame - (bTime - aTime);
			
			sleepTime = sleepTime*1000000;
			System.out.println("aTime: "+aTime);
			System.out.println("bTime: "+bTime);
			System.out.println("oneFrame: "+oneFrame);
			System.out.println("sleepTime: "+sleepTime);
			if(sleepTime<=0) {
				sleepTime =0;
			}
			
			try {
				gameThread.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	// 1 update : 위치 업데이트
	public void update() {
		if(keyH.upPress) {
			positionY -= move; // 좌상단 좌표 (0,0)
		}
		if(keyH.downPress) {
			positionY += move;
		}
		if(keyH.leftPress) {
			positionX -= move;
		}
		if(keyH.rigthPress) {
			positionX += move;
		}
	}
	
	// 2 draw : 화면 정보 repaint()
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		g2.fillRect(positionX, positionY, tileSize, tileSize);
		g2.dispose();
				
	}
}
